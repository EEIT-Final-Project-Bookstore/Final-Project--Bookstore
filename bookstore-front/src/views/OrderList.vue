<template>
    <h2>我的訂單</h2>
    <div class="order-container">
        <table v-if="orders.length" class="order-table">
            <thead>
                <tr>
                    <th>訂單編號</th>
                    <th>訂單狀態</th>
                    <th>訂購日期</th>
                    <th>交易金額</th>
                    <th>發票號碼</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="order in orders" :key="order.orderId">
                    <td>
                        <!-- 讓訂單編號變成可超連結 -->
                        <router-link :to="`/order/detail/${order.orderId}`">
                            {{ order.orderId }}
                        </router-link>
                    </td>
                    <td>{{ order.detailedStatus }}</td>
                    <td>{{ order.orderCreationTime }}</td>
                    <td>${{ order.finalAmount }}</td>
                    <td>{{ order.invoiceNumber || "尚未開立" }}</td>
                </tr>
            </tbody>
        </table>
        <p v-else class="no-order-list-block">目前沒有訂單紀錄</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axiosapi from "@/plugins/axios.js";

const orders = ref([]);
const customerId = 1; // 應該從 pinia 登入資訊取得

onMounted(async () => {
    try {
        const response = await axiosapi.get(`/api/order/list/${customerId}`);
        orders.value = response.data;
        console.log(response.data);
    } catch (error) {
        console.error("獲取訂單資訊錯誤:", error);
    }
});
</script>

<style>
    .order-container {
        max-width: 1000px;
        margin: 0 auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
        text-align: center;
        color: #333;
        display: block;
        font-size: 2em;
        margin-block-start: 0.67em;
        margin-block-end: 0.67em;
        margin-inline-start: 0px;
        margin-inline-end: 0px;
        font-weight: bold;
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
    }
    table th {
        background-color: rgb(162, 178, 248);
    }
    /* 去除表格外圍邊框，僅保留內部邊框 */
    table {
        border: none;
    }
    table td, table th {
        border: none;
    }
    tbody tr:nth-child(2n+1) {
        background-color: #fff;
    }
    tbody tr:nth-child(2n) {
        background-color: rgb(221, 221, 222);
    }
    tbody tr:hover {
        background-color: #b7cffc;
    }
    .no-order-list-block {
        width: 950px;
        border: 2px solid rgb(56, 163, 250);
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
</style>