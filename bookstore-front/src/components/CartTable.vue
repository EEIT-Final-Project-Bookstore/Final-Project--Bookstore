<template>
    <div>
        <div v-if="cartItems && cartItems.length > 0">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>
                            <label for="">
                                <input type="checkbox" v-model="selectAll" @change="toggleSelectAll">
                            </label>
                        </th>
                        <th>圖片</th>
                        <th>商品</th>
                        <th>數量</th>
                        <th>售價</th>
                        <th>小計</th>
                        <th>移除</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in cartItems" :key="item.bookName">
                        <td>
                            <label for="" class="item-checkbox">
                                <input type="checkbox" v-model="item.selected" @change="selectItem(item)">
                            </label>
                        </td>
                        <td><img :src="item.imageUrl" alt="book image" class="product-image"></td>
                        <td>{{ item.bookName }}</td>
                        <td class="quantity-btn">
                            <div class="quantity-box">
                                <button @click="$emit('update-quantity', item, -1)">-</button>
                                {{ item.quantity }}
                                <button @click="$emit('update-quantity', item, 1)">+</button>
                            </div>
                        </td>
                        <td>${{ item.price }}</td>
                        <td>${{ item.subtotal }}</td>
                        <td>
                            <button @click="$emit('remove-item', item.bookName)" class="remove-button">🗑️</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div v-else class="no-cartitems-block"> <!-- 如果購物車沒商品 -->
            <b>您的購物車中，目前並無任何商品！</b>
        </div>
    </div>
</template>
    
<script setup>
import { ref } from 'vue';

const selectAll = ref(false);

const props = defineProps({
  cartItems: {
    type: Array,
    required: true,
  },
});

const emits = defineEmits(['update-quantity', 'remove-item']);

function toggleSelectAll() {  // 切換全選或取消全選
    props.cartItems.forEach(item => {
        item.selected = selectAll.value;
    });
}
function selectItem() {
  selectAll.value = props.cartItems.every(item => item.selected);
}
</script>

<style>
    /* .cart-container {
        max-width: 950px;
        margin: 0 auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    } */
    .cart-table {
        width: 90%;
        margin: auto;
        background: white;
        border-radius: 5px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }
    table {
        width: 100%;
        margin: 15px 0;
        border-collapse: collapse;
        font-weight: bold;
    }
    table th, table td {
        padding: 10px;
        text-align: center; /* 水平居中 */
        vertical-align: middle; /* 垂直居中 */
    }
    table th {
        background-color: rgb(162, 178, 248);
    }
    /* 選取框樣式 */
    input[type="checkbox"] {
        transform: scale(1.3); /* 選取框放大1.3倍 */
        /* margin: 0; */
    }
    /* 按鈕設置: */
    .quantity-btn {
        width: 100px;
        justify-content: center; /* 水平置中 */
        align-items: center; /* 垂直置中 */
        height: 100%;
    }
    .quantity-btn button {
        width: 30px;
        height: 30px;
        font-size: 18px;
        padding: 0;
        border: 0;
        /* background-color: #f0f0f0; */
        cursor: pointer;
    }
    tbody tr:nth-child(2n+1) .quantity-btn button{ /* 奇數row +/-按鈕背景色 */
        background-color: #f0f0f0;
    }
    tbody tr:nth-child(2n) .quantity-btn button{  /* 偶數row +/-按鈕背景色 */
        background-color: rgb(229, 229, 233);
    }
    tbody tr:nth-child(2n+1) .quantity-box {  /* 奇數row 整個數量容器邊框顏色 */
        border: 1px solid #ebe9e9;
    }
    tbody tr:nth-child(2n) .quantity-box{  /* 偶數row 整個數量容器邊框顏色 */
        border: 1px solid #cdcdcf;
    }
    /* 包裹數量按鈕和數字的容器 */
    .quantity-box {
        width: 85px;
        display: flex;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
        gap: 8px; /* 按鈕和數字間距 */
        background-color: #fff;  /*數量的數字部分*/
    }
    tbody tr:nth-child(2n+1) {  /* 奇數row顏色 */
        background-color: #fff;
    }
    tbody tr:nth-child(2n) {  /* 偶數row顏色 */
        background-color: rgb(241, 241, 244);
    }
    tbody tr:hover {
        background-color: #c2d7fd;
    }
    .no-cartitems-block {
        width: 1000px;
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
    .product-image {
        width: 80px;
        height: 100px;
        object-fit: fill;
        border-radius: 5px;
    }
    .remove-button {
        border: none;
        background-color: rgb(242, 237, 237);
        width: 30px;
        height: 25px;
        font-size: large;
        padding: 0;
        padding-bottom: 10px;
        border-radius: 8px;
    }
    .action-buttons {
        display: flex;
        justify-content: center;
        gap: 15px;
        margin-top: 20px;
    }
    /* 去除表格外圍邊框，僅保留內部邊框 */
    table {
        border: none;
    }
    table td, table th {
        border: none;
    }
</style>