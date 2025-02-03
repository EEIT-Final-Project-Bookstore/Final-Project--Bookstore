<template>
	<div class="payment-result-container">
	  <h2 class="payment-result-title">付款結果</h2>
	  <div v-if="latestOrder" class="payment-info">
		<p><strong>訂單編號：</strong>{{ latestOrder.orderId }}</p>
		<p><strong>訂單狀態：</strong>{{ latestOrder.statusName }}</p>
		<p><strong>付款時間：</strong>{{ latestOrder.paymentTime }}</p>
		<p><strong>交易金額：</strong>${{ latestOrder.tradeAmt }}</p>
	  </div>
	  <p v-else class="loading-message">載入中...</p>
	  <button @click="goToOrderList"  class="order-list-button">查看全部訂單</button>
	</div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import axiosapi from '@/plugins/axios.js';
import { useRouter } from "vue-router";

const router = useRouter();
const latestOrder = ref(null);
const customerId = 1;  // 假設從 pinia 登入資訊獲取

onMounted(async () => {
  try {
    const response = await axiosapi.get(`/ecpay/paymentResult/${customerId}`);
    latestOrder.value = response.data;
	console.log(response.data);
	
  } catch (error) {
    console.error("取得訂單失敗:", error);
  }
});

const goToOrderList = () => {
  router.push(`/orderList`);
};
</script>

<style>
	.payment-result-container {
		max-width: 600px;
		margin: 0 auto;
		margin-top: 20px;
		padding: 20px;
		background-color: #efeeee;
		border-radius: 8px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	}

	.payment-result-title {
		font-size: 2rem;
		text-align: center;
		margin-bottom: 20px;
		color: #333;
	}

	.payment-info p {
		font-size: 1.1rem;
		margin: 10px 0;
		color: #555;
	}

	.payment-info strong {
		color: #333;
	}

	.loading-message {
		text-align: center;
		color: #999;
		font-style: italic;
	}

	.order-list-button {
		display: block;
		width: 100%;
		padding: 12px;
		margin-top: 20px;
		background-color: #007bff;
		color: white;
		font-size: 1.1rem;
		text-align: center;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.3s ease;
	}

	.order-list-button:hover {
		background-color: #0056b3;
	}
</style>