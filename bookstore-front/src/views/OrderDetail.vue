<template>
    <h2 class="order-title">🧾 訂單明細</h2>
    <div class="order-detail-container">
        <div v-if="orderStore.orderDetail">
            <div class="order-info">
                <p><span>📌 訂單編號：</span>{{ orderStore.orderDetail.orderId }}</p>
                <p><span>🔖 訂單狀態：</span>{{ orderStore.orderDetail.detailedStatus }}</p>
                <p><span>📅 訂購日期：</span>{{ orderStore.orderDetail.orderCreationTime }}</p>
                <p><span>💰 交易金額：</span>${{ orderStore.orderDetail.finalAmount }}</p>
                <p>📦 <span>{{ totalItems }}</span> 項商品, 共 <span>{{ totalQuantity }}</span> 件</p>
                <!-- <p><span>🧾 發票號碼：</span>{{ order.invoiceNumber || "尚未開立" }}</p> -->
            </div>
            <h3 class="order-subtitle">🛍️ 商品明細</h3>
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
                    <tr v-for="item in orderStore.orderDetail.items" :key="item.detailId">
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
import { onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { useOrderStore } from "@/stores/orderStore";

const route = useRoute();
const orderStore = useOrderStore();

onMounted(async () => {
    await orderStore.fetchOrderDetail(route.params.orderId);
    console.log("orderstore:", orderStore);
});

// 計算商品種類數 (總共幾項不同的商品)
const totalItems = computed(() => {
    return orderStore.orderDetail?.items?.length || 0;
});

// 計算總件數 (所有商品加總)
const totalQuantity = computed(() => {
    return orderStore.orderDetail?.items?.reduce((sum, item) => sum + item.quantity, 0) || 0;
});
</script>

<style scoped>
/* 訂單標題 */
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
    margin-bottom: 0;
}
.order-detail-container {
    width: 80%;
    margin: 20px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
/* 訂單資訊 */
.order-info p {
    font-size: 18px;
    color: #555;
    margin: 5px 0;
}
.order-info span {
    font-weight: bold;
    color: black;
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
    font-size: 18px;
    font-weight: 600;
    color: #383838;
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