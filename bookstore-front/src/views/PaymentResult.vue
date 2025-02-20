<template>
	<div class="payment-result-container">
		<h2 class="payment-result-title">付款結果</h2>
		<div v-if="orderStore.latestOrder" class="payment-info">
			<p><strong>訂單編號：</strong>{{ orderStore.latestOrder.orderId }}</p>
			<p><strong>訂單狀態：</strong>{{ orderStore.latestOrder.statusName }}</p>
			<p><strong>付款時間：</strong>{{ orderStore.latestOrder.paymentTime }}</p>
			<p><strong>交易金額：</strong>${{ orderStore.latestOrder.tradeAmt }}</p>
		</div>
		<p v-else class="loading-message">載入中...</p>
		<button @click="goToOrderList"  class="order-list-button">查看全部訂單</button>
	</div>
</template>

<script setup>
import { onMounted, watch } from 'vue';
import { useRouter } from "vue-router";
import { useAuthStore } from '@/stores/authStore';
import { useCartStore } from "@/stores/cartStore";
import { useOrderStore } from '@/stores/orderStore';

const router = useRouter();
const authStore = useAuthStore();
const cartStore = useCartStore();
const orderStore = useOrderStore();

onMounted(async () => {
  if (authStore.customerId) {
    await orderStore.fetchLatestOrder(authStore.customerId);
  }
});

// 監聽訂單狀態變化，若狀態為 "已付款"，才會清空購物車
watch(() => orderStore.latestOrder?.statusName, (newStatus) => {
  if (newStatus === "已付款") {
    cartStore.clearCart(); // 清空購物車
  }
});

const goToOrderList = () => {
  router.push(`/order/list`);
};
</script>

<style>
	.payment-result-container {
		width: 800px;
		margin: auto;
		margin-top: 20px;
		margin-bottom: 20px;
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