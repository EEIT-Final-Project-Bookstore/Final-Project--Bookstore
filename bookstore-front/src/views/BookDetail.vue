<template>
    <div class="page-container">
		<div v-if="book" class="book-detail">
			<!-- 上方：三欄式布局 -->
			<div class="top-section">
			<!-- 左側：書籍封面 -->
			<div class="book-image">
				<img :src="book.imageUrl" alt="書籍封面" />
			</div>
	
			<!-- 中間：書籍基本資訊 -->
			<div class="book-info">
				<h1>{{ book.bookName }}</h1>
				<p><strong>作者：</strong>{{ book.authorName }}</p>
				<p><strong>出版社：</strong>{{ book.publisherName }}</p>
				<p><strong>出版日期：</strong>{{ book.publishDate }}</p>
				<p><strong>庫存：</strong>{{ book.stock }}</p>
			</div>
	
			<!-- 右側：操作按鈕 -->
			<div class="price-cart">
					<p><strong>定價：</strong>NT$ {{ book.price }}</p>
					<button class="cart-button" @click="addToCart">加入購物車</button>
					<button class="favorite-button" @click="toggleFavorite">
						{{ isFavorited ? "取消收藏" : "加入收藏" }}
					</button>
				</div>
			</div>
	
			<!-- 下方：商品簡介 -->
			<div class="book-description">
				<h2>商品簡介</h2>
				<p>{{ book.description }}</p>
			</div>
		</div>
	 	<!-- 資料載入中 -->
		<div v-else class="loading">
		<p>正在載入書籍資料...</p>
		</div>
  	</div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const route = useRoute();
const authStore = useAuthStore();

const book = ref(null);
const isFavorited = ref(false);
const bookId = ref(Number(route.params.id));

const fetchBookDetails = async () => {
    try {
        const response = await axiosapi.get(`/api/books/${bookId.value}`);
        book.value = response.data;
    } catch (error) {
        console.error('無法取得書籍資料', error);
    }
};

const addToCart = async () => {
    try {
        const quantity = 1;
        const authStore = useAuthStore();

        if (!bookId.value || isNaN(bookId.value)) {
            Swal.fire({
                icon: 'error',
                title: '錯誤',
                text: '無效的書籍 ID',
            });
            return;
        }

        const response = await axiosapi.post('/api/cart/add', {
            customerId: authStore.customerId,
            bookId: bookId.value,
            quantity,
        });

        // 檢查 response.data 是否包含 cartId
        if (response.data && response.data.cartId) {
            localStorage.setItem("cartId", response.data.cartId);
            Swal.fire({
                icon: 'success',
                title: '成功',
                text: response.data.message,
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: '錯誤',
                text: response.data.message || "加入購物車失敗",
            });
        }
    } catch (error) {
        console.error("加入購物車時發生錯誤:", error);
        Swal.fire({
            icon: 'error',
            title: '發生錯誤',
            text: '發生錯誤，請稍後再試。',
        });
    }
};

const toggleFavorite = () => {
  isFavorited.value = !isFavorited.value;
};

onMounted(fetchBookDetails);
</script>
  
<style scoped>
  /* 整體容器，限制寬度並居中 */
  .page-container {
    max-width: 1200px; /* 設置最大寬度 */
    margin: 0 auto; /* 水平居中 */
    padding: 20px;
  }
  
  /* 上方三欄區域 */
  .book-detail {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .top-section {
    display: flex;
    gap: 20px;
  }
  
  /* 左側圖片 */
  .book-image {
    flex: 1; /* 左側佔比 */
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .book-image img {
    width: 200px;
    height: auto;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  /* 中間書籍資訊 */
  .book-info {
    flex: 2; /* 中間佔比 */
    font-size: 16px;
    line-height: 1.6;
  }
  
  /* 右側價格與按鈕 */
  .price-cart {
    flex: 1; /* 右側佔比 */
    background-color: #f8f8f8;
    padding: 10px;
    border: 1px solid #ddd;
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  button {
    padding: 10px;
    background-color: #6200ea;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
  }
  
  button:hover {
    background-color: #3700b3;
  }
  
  /* 下方商品簡介 */
  .book-description {
    margin-top: 20px;
    background-color: #f9f9f9;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  
  .book-description h2 {
    margin-bottom: 10px;
    font-size: 18px;
  }
  
  .book-description p {
    font-size: 14px;
    line-height: 1.5;
  }
</style>
  