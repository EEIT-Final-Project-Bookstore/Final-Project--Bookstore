<template>
    <div class="cart-summary-wrapper" v-if="cartStore.cartItems.length > 0">
        <div class="notice-block">
            <p><el-icon><WarnTriangleFilled /></el-icon>購物車注意事項</p>
            <ul>
                <li>> 每筆訂單限用一張優惠券。</li>
                <li>> 請確保於前往付款前再次確認訂單內容。</li>
                <li>> 訂單成立後不可修改商品。</li>
                <li>> 點擊下方"<strong>前往付款</strong>"按鈕並確認前往，將立即建立1筆新訂單，並前往綠界支付頁面*。</li>
                <ul>*如需更改或取消訂單，請在點擊"前往付款"後返回，至我的訂單頁面進行操作</ul>
            </ul>
        </div>
        <div class="cart-summary">
            <p><span>{{ cartStore.cartItems.length }}</span> 項商品, 共 <span>{{ cartStore.totalItems }}</span> 件</p>
            <p>購物車總金額：<span>${{ cartStore.totalPrice }}</span></p>
            <p>折扣金額：<span>${{ cartStore.discountAmount }}</span></p>
            <hr>
            <p class="final-price">應付金額：<span>${{ cartStore.finalPrice }}</span></p>
        </div>
    </div>
        <div class="checkout-btn-container" v-if="cartStore.cartItems.length > 0">
            <button class="checkout-btn" @click="proceedToCheckout">
                前往付款
            </button>
        </div>
</template>

<script setup>
import { useCartStore } from "@/stores/cartStore";
import { useAuthStore } from "@/stores/authStore";
import { WarnTriangleFilled } from '@element-plus/icons-vue'
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios.js";

const cartStore = useCartStore();
const authStore = useAuthStore();

// 前往綠界付款
const proceedToCheckout = () => {
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
                .then(async (response) => {
                    console.log("訂單創建成功，返回資料:", response.data);
                    const orderId = response.data.orderId;
            // // 清空購物車
            // await cartStore.clearCart();
                    // 如果訂單創建成功，繼續發送請求來取得綠界支付表單:
                    axiosapi.get(`/ecpay/checkout/${orderId}`)
                        .then((formResponse) => {
                            const formHtml = formResponse.data;
                            // 動態創建並插入表單
                            const formContainer = document.createElement("div");
                            formContainer.innerHTML = formHtml;
                            // 將表單插入頁面
                            document.body.appendChild(formContainer);
                            // 自動提交表單，跳轉到綠界付款頁面
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
.cart-summary-wrapper {
    display: flex;
    flex-direction: row;
}
.cart-summary span {
    font-weight: bold;
}
.notice-block {
    width: 38%;  /* 跟下面 .cart-summary 的 width 要一樣寬 */
    margin-left: auto;
    background: white;
    padding: 18px;
    padding-top: 8px;
    border: 1px solid rgb(223, 219, 219);
    border-top: 0;
    border-right: 0;
    font-size: 17px;
}
.notice-block p {
    font-weight: bold;
    margin-bottom: 10px;
}
.notice-block ul {
    list-style-type: none;
    padding-left: 20px;
}
.notice-block ul li {
    margin: 5px 0;
}
.cart-summary {
    width: 38%;  /* 跟上面 .notice-block 的 width 要一樣寬 */
    background: white;
    margin-right: auto;
    padding: 17px;
    border: 1px solid rgb(223, 219, 219);
    border-top: 0;
    text-align: right;
    font-size: 17px;
}
.cart-summary p {
    margin: 10px 0;
}
/* 最終價格 */
.final-price {
    font-size: 18px;
    font-weight: bold;
    color: #d9534f;
}
/* 前往付款按鈕 */
.checkout-btn-container {
    display: flex;
    justify-content: center;
    width: 100%;
}
.checkout-btn {
    width: 80%;  /* 跟 .cart-summary 和 .notice-block 寬度對齊 */
    padding: 10px;
    font-size: 18px;
    color: white;
    background-color: #2d91fc;
    text-align: center;
    border: 0;
    margin-bottom: 10px;
    border-radius: 5px;
    cursor: pointer;
}
.checkout-btn:hover {
    background-color: #1e72cc;
}
</style>