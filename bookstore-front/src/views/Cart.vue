<template>
    <h1>🛒 我的購物車</h1>
    <div class="whole-cart-container">
        <CartTable />
        <div class="cart-actions-container" v-if="cartStore.cartItems.length > 0">
            <div class="cart-actions">
                <button @click="cartStore.removeSelectedItems" :disabled="!hasSelectedItems" class="delete-selected-btn">
                🗑️ 刪除選取項目
                </button>
                <button @click="clearCart" class="clear-cart-btn">🗑️ 清空購物車</button>
                <input v-model="cartStore.couponCode" placeholder="輸入優惠碼" class="coupon-input" />
                <button @click="cartStore.applyCoupon" class="apply-coupon-btn">
                    🎫 套用優惠券
                </button>
                <button  v-if="cartStore.discountAmount > 0" @click="cartStore.removeCoupon" class="remove-coupon-btn">
                    ❌
                </button>
            </div>
        </div>
        <CartSummary />
    </div>
</template>
    
<script setup>
import { onMounted, computed, ref } from "vue";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";
import { useAuthStore } from "@/stores/authStore";
import { useCartStore } from "@/stores/cartStore";
import CartTable from '@/components/CartTable.vue';
import CartSummary from '@/components/CartSummary.vue';

const router = useRouter();
const authStore = useAuthStore();
const cartStore = useCartStore();

const couponCode = ref('');

// 計算屬性：動態取得 customerId 和 cartId
const customerId = computed(() => authStore.customerId);
const cartId = computed(() => authStore.cartId);

//如果沒有登入，會自動導向登入頁面
onMounted(() => {
    if (!customerId.value) {
        router.push('/login');
        return;
    }
    cartStore.fetchCartItems();
});

 // 當頁面加載時，檢查是否有已儲存的優惠碼
onMounted(() => {
    const savedCouponCode = localStorage.getItem("couponCode");
    if (savedCouponCode) {
    couponCode.value = savedCouponCode;
    }
});

// 計算是否有選取的商品
const hasSelectedItems = computed(() => cartStore.cartItems.some(item => item.selected));

const clearCart = async () => {
  const result = await Swal.fire({
    title: '確定要清空購物車嗎？',
    text: '此操作無法復原！',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: '是的，清空',
    cancelButtonText: '取消',
  });
  if (result.isConfirmed) {
    await cartStore.clearCart(customerId.value, cartId.value);
  }
};
</script>

<style scoped>
    h1 {
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
    button {
        cursor: pointer;
    }
    .cart-actions-container {
        display: flex;
        justify-content: center;
        margin-top: 0;
    }
    .cart-actions {
        display: flex;
        width: 79%; /* 設定寬度與 CartTable.vue 一致 */
        justify-content: space-evenly;
        padding: 7px;
        border: 1px solid rgb(223, 219, 219);
        /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
        gap: 15px;
    }
    .cart-actions button {
        padding: 8px 8px;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    .delete-selected-btn {
        width: 210px;
        background-color: rgb(236, 90, 80); /* 橘色背景 */
        color: white;
        font-weight: bold;
    }
    .delete-selected-btn:hover {
        background-color: #fa4242;
    }
    .delete-selected-btn:disabled {
        background-color: #ccc;
        color: #ffffff;
        cursor: not-allowed;
    }
    .clear-cart-btn {
        background-color: rgb(236, 90, 80); /* 紅色背景 */
        color: #fff;
        font-weight: bold;
        width: 200px;
    }
    .clear-cart-btn:hover {
        background-color: #fc4242;
    }
    .coupon-input {
        flex: 1;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        padding-left: 10px;
    }
    .apply-coupon-btn {
        width: 200px;
        background-color: #28a745;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 15px;
        font-weight: bold;
    }
    .apply-coupon-btn:hover {
        background-color: #218838;
    }
    /* X 按鈕 (移除優惠券) */
    .remove-coupon-btn {
        width: 40px;
        background: #ddd;
        border: none;
        color: red;
        text-align: center;
        /* font-size: 17px;
        cursor: pointer; */
    }
    .remove-btn:hover {
        color: darkred;
    }
</style>