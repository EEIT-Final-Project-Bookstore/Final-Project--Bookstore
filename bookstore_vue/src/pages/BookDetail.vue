<template>
  <div class="page-container">
    <div v-if="book" class="book-detail">
      <!-- 上方：三欄式布局 -->
      <div class="top-section">
        <!-- 左側：書籍封面 -->
        <div class="book-image">
          <img :src="book.coverImage || defaultImage" alt="書籍封面" />
        </div>

        <!-- 中間：書籍基本資訊 -->
        <div class="book-info">      
          <h1>{{ book.title }}</h1>    
          <p><strong>分類：</strong>{{ book.categoryName }} > {{ book.subcategoryName }}</p>
          <p><strong>作者：</strong>{{ book.author }}</p>
          <p><strong>出版社：</strong>{{ book.publisher }}</p>
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

<script>
import defaultImage from "../assets/getImage.webp";
import { fetchBookDetail } from "@/services/bookService";
import { useRoute } from "vue-router";

export default {
  name: "BookDetail",
  data() {
    return {
      book: null, // 初始為null，等API載入
      isFavorited: false, // 收藏狀態
      defaultImage, // 預設圖片
    };
  },
  computed: {
    discountedPrice() {
      // 計算優惠價
      return (this.book.price * (this.book.discount / 100)).toFixed(2);
    },
  },
  methods: {
    async fetchBookData() {
      // 獲取書籍資料
      const route = useRoute();
      const id = route.params.id; // 取得網址上的書籍 ID
      console.log("當前書籍 ID:", id); // 🔍 看看 id 是什麼
      const data = await fetchBookDetail(id);
      
      if (data) {
      console.log("獲取的書籍資料:", data);
      console.log("書籍封面圖片:", data.coverImage);

      this.book = { ...data };
    } else {
      console.error("無法獲取書籍詳細資料");
    }
    },
    addToCart() {
      alert("購物車功能尚未完成");
    },
    toggleFavorite() {
      this.isFavorited = !this.isFavorited;
    },
  },
  mounted() {
    this.fetchBookData(); // 載入頁面時獲取數據
  },
};

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
  gap: 30px;
}

.top-section {
  display: flex;
  gap: 40px;
  align-items: flex-start;/* 🔹 確保所有內容從頂部開始對齊 */
}

/* 左側圖片 */
.book-image {
  flex: 1; /* 左側佔比 */
  display: flex;
  justify-content: center;
  align-items: center;
}
.book-image img {
  width: 220px;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* 中間書籍資訊 */
.book-info {
  flex: 2; /* 中間佔比 */
  font-size: 16px;
  line-height: 1.6;
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 🔹 讓內容垂直置中 */
  align-items: flex-start; /* 🔹 讓內容左對齊 */
}
/* 🔹 書籍標題 (h1) 調整對齊 */
.book-info h1 {
  margin-top: 0; /* 🔹 確保標題不會因為 margin 而上移 */
  font-size: 24px;
}

/* 右側價格與按鈕 */
.price-cart {
  flex: 1; /* 右側佔比 */
  background-color: #f8f8f8;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 🔹 讓價格資訊在區塊內置中 */
  gap: 15px;
}

.price-cart p {
  font-size: 18px;
  margin-bottom: 10px;
}

/* 🔹 更新：按鈕樣式 */
.cart-button,
.favorite-button {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  background-color: #6200ea;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s ease;
}

/* 🔹 更新：不同按鈕的顏色 */
.cart-button:hover {
  background-color: #3700b3;
}

.favorite-button {
  background-color: #9c27b0;
}

.favorite-button:hover {
  background-color: #7b1fa2;
}

/* 下方商品簡介 */
.book-description {
  margin-top: 20px;
  background-color: #f9f9f9;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: left;
}

.book-description h2 {
  text-align: center;
  font-size: 18px;
}

.book-description p {
  font-size: 14px;
  line-height: 1.5;
}
</style>
