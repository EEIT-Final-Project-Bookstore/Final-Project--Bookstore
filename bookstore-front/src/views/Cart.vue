<template>
    <h1>🛒 購物車明細</h1>
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
    <!-- 舊版 無pinia:
        <h1>🛒 購物車明細</h1>
        <div>
            <div class="cart-table-container">
            <CartTable
                :cart-items="cartItems"
                @update-quantity="updateQuantity" 
                @remove-item="removeItem"
                @select-items="selectItems">
            </CartTable>
            </div>
            <div class="cart-actions-container" v-if="cartItems && cartItems.length > 0">
                <div class="cart-actions">
                    <button @click="removeSelectedItems" :disabled="!selectedItems.length" class="delete-selected-btn">🗑️ 刪除選取項目</button>
                    <button @click="clearCart" class="clear-cart-btn">🗑️ 清空購物車</button>
                    <input v-model="couponCode" placeholder="輸入優惠碼" class="coupon-input" />
                    <button @click="applyCoupon" class="apply-coupon-btn">
                        🎫 套用優惠券
                    </button>
                    <button  v-if="discountAmount > 0" @click="removeCoupon" class="remove-coupon-btn">
                        ❌
                    </button>
                </div>
            </div>
            <div v-if="cartItems && cartItems.length > 0">
                <CartSummary
                    :cartItems="cartItems" 
                    :total-items="totalItems"
                    :cart-items-count="cartItemsCount"
                    :total-price="totalPrice"
                    :discount-amount="discountAmount"
                    :final-price="finalPrice"
                    :coupon-code="couponCode"
                    @update:couponCode="(value) => couponCode = value"
                    @apply-coupon="applyCoupon"
                    @remove-coupon="removeCoupon">
                </CartSummary>
            </div>
        </div>
    -->
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

/* 舊版 (無pinia)
import { ref, computed, onMounted } from "vue";
import axiosapi from '@/plugins/axios.js';
import Swal from 'sweetalert2';
import CartTable from '@/components/CartTable.vue';
import CartSummary from '@/components/CartSummary.vue';

const cartItems = ref([]);
const customerId = 1;   // 需更改成動態取得顧客登入帳號的customerId -> pinia?
const cartId = 1;       // 需更改成動態取得顧客登入帳號的cartId -> pinia?
const discountAmount = ref(0);
const couponCode = ref("");

const props = defineProps({
    cartItems: Array,
})

const selectedItems = computed(() => {
    return cartItems.value.filter(item => item.selected);
});

// 計算屬性
const totalItems = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
);
const cartItemsCount = computed(() => cartItems.value.length);
const totalPrice = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.subtotal, 0)
);
const finalPrice = computed(() => totalPrice.value - discountAmount.value);

// 取得購物車項目
onMounted(async () => {
    try {
        const response = await axiosapi.get(`/api/cart/view/${customerId}`);
        cartItems.value = response.data.map(item => ({   //舊版(無Pinia):
            ...item, 
            subtotal: item.quantity * item.price, // 初始化小計
            selected: false
        }));
        console.log('Cart items loaded:', cartItems.value);
    } catch (error) {
        console.error('Failed to load cart items:', error);
    }
});

// 更新數量
async function updateQuantity(item, delta) {
    // 更新前端顯示的數量和小計 -> 舊版(無Pinia):
    const newQuantity = item.quantity + delta;
    if (newQuantity < 1) return; // 防止數量變成 0 或負數
    // if (newQuantity < 1) item.quantity = 1;
    item.subtotal = item.quantity * item.price; // 更新小計

    // 發送請求到後端更新數量
    try {
        const response = await axiosapi.put(`/api/cart/update`, {
            customerId: customerId,  // 顧客 ID
            bookId: item.bookId,     // 書籍 ID
            quantity: item.quantity  // 新的數量
        });
        if (response.status === 200) {
            item.quantity = newQuantity;
            item.subtotal = newQuantity * item.price;  // 更新小計
            console.log("數量已更新到後端");
        } else {
            console.error("更新後端數量時出錯");
        }
    } catch (error) {
        console.error("數量更新失敗:", error);
        // 在失敗時，可以選擇恢復為舊的數量或顯示錯誤提示 -> 舊版(無Pinia):
        item.quantity -= delta; // 恢復數量
        item.subtotal = item.quantity * item.price; // 恢復小計
    }
}

// 刪除項目
async function removeItem(bookName) {
    // 查找該項目的 ID
    const item = cartItems.value.find(item => item.bookName === bookName);
    if (!item) return;
    // 發送請求到後端刪除這本書籍
    try {
        const response = await axiosapi.delete(`/api/cart/delete`, {
            data: {
                customerId: customerId,  // 顧客 ID
                bookId: item.bookId      // 書籍 ID
            }
        });
        if (response.status === 200) {
            console.log("項目已從購物車中刪除");
            cartItems.value = cartItems.value.filter(item => item.bookName !== bookName);
        } else {
            console.error("刪除後端項目時出錯");
        }
    } catch (error) {
        console.error("刪除項目失敗:", error);
    }
}

// 選取項目
function selectItems(item) {
    item.selected = !item.selected;
}

// 刪除選取的項目
async function removeSelectedItems() {
    const selectedItems = cartItems.value.filter(item => item.selected);
    if (selectedItems.length === 0) return;
    try {
        // 遍歷選中的項目，逐一刪除
        for (let item of selectedItems) {
            await axiosapi.delete(`/api/cart/delete`, {
                data: {
                    customerId,
                    bookId: item.bookId
                }
            });
        }
        // 更新本地 cartItems，刪除選中的項目
        cartItems.value = cartItems.value.filter(item => !item.selected);
        Swal.fire("成功刪除選取的項目", "", "success");
    } catch (error) {
        console.error("刪除選取項目失敗:", error);
        Swal.fire("刪除失敗", "請稍後再試", "error");
    }
}

// 清空購物車
function clearCart() {
    Swal.fire({
        title: "確定清空購物車？",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "是",
        cancelButtonText: "否",
    }).then (async (result) => {
        if (result.isConfirmed) {
            try {
                await axiosapi.delete(`/api/cart/clear/${customerId}`);
                cartItems.value = [];  //gpt刪掉這行，改下一行
                // props.cartItems.splice(0, props.cartItems.length); // 清空列表
                Swal.fire("已清空購物車", "", "success");
            } catch (error) {
                console.error("清空購物車失敗:", error);
            }
        }
    })
}

// 套用優惠券
const applyCoupon = async () => {
  try {
    const response = await axiosapi.post('/api/coupon/apply', {
        cartId,
        couponCode: couponCode.value,
    });
    if (response.data.valid) {
        discountAmount.value = response.data.discountAmount;
        Swal.fire({
            title: '成功',
            text: response.data.message,
            icon: 'success',
            confirmButtonText: '確定'
        });
    } else {
        Swal.fire({
            title: '失敗',
            text: response.data.message,
            icon: 'error',
            confirmButtonText: '確定'
        });
    }
  } catch (error) {
    Swal.fire('錯誤', '無法套用優惠券，請稍後再試', 'error');
  }
};

// 移除優惠券
async function removeCoupon() {
  try {
    const response = await axiosapi.delete('/api/coupon/remove', {
      data: {
        cartId,
        couponCode: couponCode.value,  // 傳遞當前優惠券代碼
      },
    });
    if (response.data.valid) {
      // 移除優惠券後，重置折扣金額與優惠券代碼
      discountAmount.value = 0;  // 清除折扣金額
      couponCode.value = '';

      Swal.fire({
        title: '成功',
        text: response.data.message,
        icon: 'success',
        confirmButtonText: '確定',
      });
    } else {
      Swal.fire({
        title: '失敗',
        text: response.data.message,
        icon: 'error',
        confirmButtonText: '確定',
      });
    }
  } catch (error) {
    console.error('移除優惠券失敗', error);
    Swal.fire({
      title: '錯誤',
      text: '移除優惠券時發生錯誤，請稍後再試',
      icon: 'error',
      confirmButtonText: '確定',
    });
  }
}
*/
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