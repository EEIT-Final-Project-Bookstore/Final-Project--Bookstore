<template>
    <h2 class="order-title">訂單明細</h2>
    <div class="order-detail-container">
        <div v-if="order">
            <div class="order-info">
                <p><strong>📌 訂單編號：</strong>{{ order.orderId }}</p>
                <p><strong>🔖 訂單狀態：</strong>{{ order.detailedStatus }}</p>
                <p><strong>📅 訂購日期：</strong>{{ order.orderCreationTime }}</p>
                <p><strong>💰 交易金額：</strong>${{ order.finalAmount }}</p>
                <p><strong>🧾 發票號碼：</strong>{{ order.invoiceNumber || "尚未開立" }}</p>
            </div>
            <h3 class="order-subtitle">🛒 訂購商品明細</h3>
            <table class="order-detail-table">
                <thead>
                    <tr>
                        <th>書籍圖片</th>
                        <th>書籍名稱</th>
                        <th>數量</th>
                        <th>單價</th>
                        <th>小計</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in order.items" :key="item.detailId">
                        <td>
                            <img :src="item.imageUrl" alt="書籍圖片" class="book-image" />
                        </td>
                        <td>{{ item.bookName }}</td>
                        <td>{{ item.quantity }}</td>
                        <td>${{ item.price }}</td>
                        <td>${{ item.subtotal }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <p v-else class="loading-text">載入中...</p>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axiosapi from "@/plugins/axios.js";

const route = useRoute();
const orderId = route.params.orderId; // 取得 URL 參數中的 orderId
const order = ref(null);

onMounted(async () => {
    try {
        const response = await axiosapi.get(`/api/order/detail/${orderId}`);
        order.value = response.data;
    } catch (error) {
        console.error("獲取訂單明細錯誤:", error);
    }
});
</script>

<style scoped>
    .order-detail-container {
        max-width: 1000px;
        margin: 20px auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }
    /* 訂單標題 */
    .order-title {
        text-align: center;
        font-size: 28px;
        font-weight: bold;
        color: #333;
        margin-bottom: 10px;
    }
    /* 訂單資訊 */
    .order-info p {
        font-size: 16px;
        color: #555;
        margin: 5px 0;
    }
    /* 小標題 */
    .order-subtitle {
        font-size: 20px;
        font-weight: bold;
        color: #444;
        margin-top: 20px;
        border-bottom: 2px solid #ddd;
        padding-bottom: 5px;
    }
    /* 表格 */
    .order-detail-table {
        width: 100%;
        border-collapse: collapse;
        background: #fff;
    }
    .order-detail-table th,
    .order-detail-table td {
        text-align: center;
        padding: 12px;
        border-bottom: 1px solid #ddd;
    }
    .order-detail-table th {
        background-color: #f8f8f8;
        font-size: 18px;
    }
    .order-detail-table td {
        font-size: 16px;
        color: #444;
    }
    .book-image {
        width: 80px;
        height: 100px;
        object-fit: fill;
        border-radius: 5px;
    }
    /* "載入中"文字 */
    .loading-text {
        text-align: center;
        font-size: 16px;
        color: #888;
        margin-top: 20px;
    }
</style>