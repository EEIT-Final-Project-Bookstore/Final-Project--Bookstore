<template>
    <h1>🛒 購物車明細</h1>
    <div>
        <div>  <!-- 如果購物車有商品 -->
        <CartTable
            :cart-items="cartItems"
            @update-quantity="updateQuantity" 
            @remove-item="removeItem"
            @select-items="selectItems">
        </CartTable>
        </div>

<!-- *待做事項:
  - pinia儲存顧客購物車資料 -> 因為刪除書籍後，重新整理網頁又回來了(沒有真的在資料庫刪掉購物車table中書籍)，要用pinia抓customerId和cartId(改動態取得)
  - 點選書籍圖片和名字可以連到該書的網頁(最好是圖片和名字變成一整個方塊可以點選，或是分開就好)；點選每筆訂單編號可以連到該訂單明細頁面
  - 可加上訂單分頁功能
  - (如有多餘時間，可加上每頁上面"導覽連結"，像是: 首頁>會員資料>修改會員資料 -> Breadcrumbs)
 -->
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
</template>
    
<script setup>
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
    item.quantity += delta;
    if (item.quantity < 1) item.quantity = 1;
    item.subtotal = item.quantity * item.price; // 更新小計

    // 發送請求到後端更新數量
    try {
        const response = await axiosapi.put(`/api/cart/update`, {
            customerId: customerId,  // 顧客 ID
            bookId: item.bookId,     // 書籍 ID
            quantity: item.quantity  // 新的數量
        });
        if (response.status === 200) {
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
        max-width: 1000px;
        width: 90%; /* 設定寬度與 CartTable.vue 一致 */
        justify-content: space-evenly;
        padding: 10px;
        border: 2px solid #f3f3f3;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        gap: 10px;
    }
    .cart-actions button {
        padding: 8px 8px;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    .delete-selected-btn {
        width: 200px;
        background-color: #f9665c; /* 橘色背景 */
        color: #fff;
        font-weight: bold;
    }
    .delete-selected-btn:disabled {
        background-color: #ccc;
        cursor: not-allowed;
    }
    .clear-cart-btn {
        background-color: #f9665c; /* 紅色背景 */
        color: #fff;
        font-weight: bold;
        width: 180px;
    }
    .clear-cart-btn:hover {
        background-color: #fc4242;
    }
    .coupon-input {
        flex: 1;
        width: 300px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        padding-left: 10px;
    }
    .apply-coupon-btn {
        width: 180px;
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