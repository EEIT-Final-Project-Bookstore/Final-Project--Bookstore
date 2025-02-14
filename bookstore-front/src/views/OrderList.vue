<template>
    <h2>📌 我的訂單</h2>
    <div class="order-container">
        <table v-if="paginatedOrders.length" class="order-table">
            <!-- orderStore.orders.length 改成分頁版本 -->
            <thead>
                <tr>
                    <th>訂單編號</th>
                    <th>訂單狀態</th>
                    <th>訂購日期</th>
                    <th>交易金額</th>
                    <th>操作</th>
                    <!-- <th>發票號碼</th> -->
                </tr>
            </thead>
            <tbody>
                <tr v-for="order in paginatedOrders" :key="order.orderId">
                    <!-- order in orderStore.orders 改成分頁版本 -->
                    <td>
                        <!-- 讓訂單編號變成可超連結 -->
                        <router-link :to="`/order/detail/${order.orderId}`">
                            {{ order.orderId }}
                        </router-link>
                    </td>
                    <td>{{ order.detailedStatus === '已付款' ? '✅ 已付款' : (order.detailedStatus === '待付款' ? '⏳ 待付款' : '❌ 已取消') }}</td>
                    <td>{{ order.orderCreationTime }}</td>
                    <td>${{ order.finalAmount }}</td>
                    <td>
                        <button v-if="order.detailedStatus === '待付款'" @click="orderStore.cancelOrder(order.orderId)">
                            取消訂單
                        </button>
                        <button v-if="order.detailedStatus === '待付款'" @click="payExistingOrder(order)">
                            前往付款
                        </button>
                    </td>
                    <!-- <td>{{ order.invoiceNumber || "尚未開立" }}</td> -->
                </tr>
            </tbody>
        </table>
        <div v-else class="no-order-list-block">目前沒有訂單紀錄</div>
        <!-- 分頁控制 -->
  </div>
        <div v-if="orderStore.orders.content && orderStore.orders.content.length" class="pagination">
            <button @click="prevPage" :disabled="currentPage === 1">上一頁</button>
            <span>第 {{ currentPage }} 頁 / 共 {{ totalPages }} 頁</span>
            <button @click="nextPage" :disabled="currentPage === totalPages">下一頁</button>
        </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from '@/stores/authStore';
import { useCartStore } from "@/stores/cartStore";
import { useOrderStore } from '@/stores/orderStore';
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios.js";

const router = useRouter();
const authStore = useAuthStore();
const cartStore = useCartStore();
const orderStore = useOrderStore();

//分頁:
const currentPage = ref(1);  // 預設在第?頁
const pageSize = ref(6); // 每頁顯示?筆訂單，可自行調整

const totalPages = computed(() => {
    return orderStore.orders.content ? Math.ceil(orderStore.orders.content.length / pageSize.value) : 0;
});

const paginatedOrders = computed(() => {
    if (orderStore.orders && orderStore.orders.content) {
        const start = (currentPage.value - 1) * pageSize.value;
        return orderStore.orders.content.slice(start, start + pageSize.value);
    }
    return []; // 若無內容，回傳空陣列
});

const nextPage = () => {
    if (currentPage.value < totalPages.value) {
        currentPage.value++;
    }
};

const prevPage = () => {
    if (currentPage.value > 1) {
        currentPage.value--;
    }
};

//如果沒有登入，會自動導向登入頁面
onMounted(async () => {
    if (!authStore.customerId) {
        router.push('/login');
        return;
    }
    await orderStore.fetchOrders(authStore.customerId);
    console.log("訂單資料：", orderStore.orders);
});
const payExistingOrder = (order) => {
    if (!authStore.cartId) {
        Swal.fire({
            title: "購物車錯誤",
            text: "無法獲取購物車資訊，請稍後再試。",
            icon: "error",
        });
        return;
    }
    Swal.fire({
        title: "確定前往綠界支付結帳頁面？",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "是",
        cancelButtonText: "否",
    }).then((result) => {
        if (result.isConfirmed) {
            // 創建訂單請求的資料
            const orderData = {
                cartId: authStore.cartId,  // 使用 cartStore 的 cartId
                couponCode: cartStore.couponCode, // 從 Pinia 取得優惠券
            };
            // 發送 POST 請求創建訂單
            axiosapi.post("/api/order/create", orderData)
                .then((response) => {
                    console.log("訂單創建成功，返回資料:", response.data);
                    const newOrderId = response.data.orderId;
                    const finalAmount = response.data.finalAmount; // 假設後端返回了最終金額，包含優惠券
                    // 更新前端顯示最終金額（包含優惠券）
                    cartStore.updatefinalAmount(finalAmount); // 假設 cartStore 有提供 updateFinalPrice 方法
                    authStore.orderId = newOrderId; // 儲存新訂單ID到authStore
                    if (authStore.orderId) {
                        // 取消原來的訂單
                        axiosapi.post(`/api/order/cancel/${order.orderId}`)
                            .then(() => {
                                console.log("原訂單已取消");
                            })
                            .catch((error) => {
                                console.error("取消原訂單失敗", error);
                            });
                    }
                    // 如果訂單創建成功，繼續發送 GET 請求來取得綠界支付表單
                    axiosapi.get(`/ecpay/checkout/${newOrderId}`)
                        .then((formResponse) => {
                            const formHtml = formResponse.data;
                            // 動態創建並插入表單
                            const formContainer = document.createElement("div");
                            formContainer.innerHTML = formHtml;
                            // 將表單插入頁面
                            document.body.appendChild(formContainer);
                            // 清除 localStorage 中的優惠券資料
                            localStorage.removeItem("couponCode");
                            localStorage.removeItem("discountAmount");
                            cartStore.couponCode = '';
                            // 自動提交表單
                            formContainer.querySelector("form").submit();
                        })
                        .catch((error) => {
                            console.error("付款頁面載入失敗", error);
                            Swal.fire({
                                title: "發生錯誤",
                                text: "無法載入付款頁面，請稍後再試。",
                                icon: "error",
                            });
                        });
                })
                .catch((error) => {
                    console.error("訂單創建失敗", error);
                    Swal.fire({
                        title: "訂單創建失敗",
                        text: "請稍後再試。",
                        icon: "error",
                    });
                });
        }
    });
};
</script>

<style scoped>
h2 {
    text-align: center;
    color: #333;
    display: block;
    font-size: 28px;
    margin-block-start: 0.67em;
    margin-block-end: 0.67em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    font-weight: bold;
    margin-bottom: 10px;
}
.order-container {
    width: 75%;
    margin: 0 auto;
    padding: 10px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
.order-table {
    width: 100%;
    border-collapse: collapse;
    font-weight: bold;
    margin-bottom: 20px;
}
table th, table td {
    padding: 10px;
    text-align: center; /* 水平居中 */
    vertical-align: middle; /* 垂直居中 */
    border: none;
}
table th {
    font-size: 18px;
    background-color: #b1daa5;
}
/* 去除表格外圍邊框，僅保留內部邊框 */
table {
    border: none;
}
table td {
    font-size: 19px;
}
tbody tr:nth-child(2n+1) {
    background-color: #fff;
}
tbody tr:nth-child(2n) {
    background-color: rgb(228, 228, 230);
}
tbody tr:hover {
    background-color: #d5e7cd;
}
.no-order-list-block {
    width: 100%;
    border: 2px solid #9fca90;
    border-radius: 5px;
    text-align: center;
    display: flex;
    justify-content: center;
    margin: 0 auto;
    padding: 30px;
    background-color: #e5f3fe;
    color: #333;
    font-size: 1.1rem;
    font-weight: bold;
}
.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 10px;
    margin-bottom: 10px;
}
.pagination button {
    margin: 0 10px;
    padding: 5px 10px;
    font-size: 16px;
    cursor: pointer;
    border: 1px solid #ccc;
    border-radius: 6px;
    background-color: #f8f8f8;
}
.pagination button:disabled {
    cursor: not-allowed;
    opacity: 0.5;
}
</style>