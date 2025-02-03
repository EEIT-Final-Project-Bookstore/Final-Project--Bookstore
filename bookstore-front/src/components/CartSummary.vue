<template>
    <div class="cart-summary">
        <p>{{ cartItemsCount }} 項商品, 共 {{ totalItems }} 件</p>
        <p>購物車總金額：${{ totalPrice }}</p>
        <p>折扣金額：${{ discountAmount }}</p>
        <hr>
        <p class="final-price">應付金額：${{ finalPrice }}</p>
        <hr>
        <button class="checkout-btn" @click="proceedToCheckout">前往付款</button>
    </div>
</template>

<script setup>
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios.js';

const cartId = 1;    // 需更改成動態取得顧客登入帳號的cartId -> pinia?

// 定義 props
const props = defineProps({
    cartItems: Array,
    totalItems: Number,
    cartItemsCount: Number,
    totalPrice: Number,
    discountAmount: Number,
    finalPrice: Number,
    couponCode: String,
    selectedItems: Array,
});

// 定義 emit 事件 (必須使用 defineEmits)
const emit = defineEmits(['apply-coupon', 'remove-coupon']);

// 更新優惠碼
const updateCouponCode = (value) => {
  emit('update:couponCode', value);  // 正確觸發事件
};

// 前往綠界付款
const proceedToCheckout = () => {
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
                cartId,
                couponCode: props.couponCode,
            };
            // 發送 POST 請求創建訂單
            axiosapi.post("/api/order/create", orderData)
                .then((response) => {
                    console.log("訂單創建成功，返回資料:", response.data);
                    const finalAmount = response.data.finalAmount;
                    console.log("最終金額:", finalAmount);
                    // 如果訂單創建成功，繼續發送下面 GET 請求來取得綠界支付表單
                    const orderId = response.data.orderId;
                    axiosapi.get(`/ecpay/checkout/${orderId}`)
                        .then((formResponse) => {
                            const formHtml = formResponse.data;
                            // 動態創建並插入表單
                            const formContainer = document.createElement("div");
                            formContainer.innerHTML = formHtml;
                            // 將表單插入頁面
                            document.body.appendChild(formContainer);
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

<style>
    .cart-summary {
        width: 90%;
        max-width: 1000px;
        margin: auto;
        margin-top: 0;
        background: white;
        padding: 19px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        text-align: right;
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
    .checkout-btn {
        width: 100%;
        padding: 12px;
        font-size: 16px;
        color: white;
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    .checkout-btn:hover {
        background-color: #0056b3;
    }
    /* .actions button {
        padding: 10px 20px;
        font-size: 16px;
        color: white;
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    .actions button:hover {
        background-color: #0056b3;
    } */
</style>