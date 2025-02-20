<template>
    <div class="cart-table-container">
        <div v-if="cartStore.cartItems.length > 0">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>
                            <input type="checkbox" :checked="allSelected" @change="selectAllHandler">
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
                    <tr v-for="(item, index) in cartStore.cartItems" :key="item.bookId">
                        <td>
                            <input type="checkbox"  :checked="item.selected"  @change="updateSelectedItems(item)">
                        </td>
                        <td>
                            <router-link :to="{ name: 'book-link', params: { id: item.bookId } }">
                                <img :src="item.imageUrl" alt="book image" class="product-image">
                            </router-link>
                        </td>
                        <td>
                            <router-link :to="{ name: 'book-link', params: { id: item.bookId } }" class="bookname-link">
                                {{ item.bookName }}
                            </router-link>
                        </td>
                        <td class="quantity-btn">
                            <div class="quantity-box">
                                <button @click="cartStore.updateQuantity(item, -1)">-</button>
                                {{ item.quantity }}
                                <button @click="cartStore.updateQuantity(item, 1)">+</button>
                            </div>
                        </td>
                        <td>${{ item.price }}</td>
                        <td>${{ item.subtotal }}</td>
                        <td>
                            <button @click="cartStore.removeItem(index)" class="remove-button">🗑️</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div v-else class="no-cartitems-block">您的購物車目前沒有商品！去逛逛吧!</div>
    </div>
</template>

<script setup>
import { computed } from "vue";
import { useCartStore } from "@/stores/cartStore";

const cartStore = useCartStore();

const allSelected = computed(() => cartStore.cartItems.every(item => item.selected));

// selectAllHandler 用來控制全選與取消全選
const selectAllHandler = () => {
  const newState = !allSelected.value;
  cartStore.cartItems.forEach(item => {
    item.selected = newState;  // 全選或取消全選
  });
};

// updateSelectedItems 用來更新單個商品選擇狀態
const updateSelectedItems = (item) => {
  item.selected = !item.selected;  // 切換單個商品選擇狀態
};
</script>

<style scoped>
    .cart-table {
        width: 80%;
        margin: auto;
        background: white;
        border-radius: 5px;
        border: 1px solid rgb(223, 219, 219);
        border-bottom: 0;
        /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); */
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
        font-size: 18px;
    }
    table th {  /* 表格內header背景色 */
        background-color: #b1daa5;
    }
    /* 選取框樣式 */
    input[type="checkbox"] {
        transform: scale(1.3); /* 選取框放大1.3倍 */
        /* margin: 0; */
    }
    .bookname-link {
        text-decoration: none;
        color: #333;
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
        background-color: rgb(228, 228, 230);
    }
    tbody tr:hover {  /* 鼠標移到表格中每行上面的背景色*/
        background-color: #d5e7cd;
    }
    .no-cartitems-block {
        width: 1000px;
        border: 2px solid #9fca90;
        border-radius: 5px;
        display: flex;
        justify-content: center;
        margin: auto;
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
        background-color: white;
        width: 30px;
        height: 25px;
        font-size: large;
        padding: 0;
        padding-bottom: 10px;
        border-radius: 8px;
    }
    tbody tr:nth-child(2n) .remove-button{  /* 偶數 row 垃圾桶按鈕顏色 */
        background-color: rgb(241, 241, 244);
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