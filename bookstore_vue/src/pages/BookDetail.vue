<template>
  <div class="page-container">
    <div class="book-detail">
      <!-- 上方：三欄式布局 -->
      <div class="top-section">
        <!-- 左側：書籍封面 -->
        <div class="book-image">
          <img :src="book.coverImage || defaultImage" alt="書籍封面" />
        </div>

        <!-- 中間：書籍基本資訊 -->
        <div class="book-info">
          <h1>{{ book.title }}</h1>
          <p><strong>ISBN：</strong>{{ book.isbn }}</p>
          <p><strong>作者：</strong>{{ book.author }}</p>
          <p><strong>出版社：</strong>{{ book.publisher }}</p>
          <p><strong>出版日期：</strong>{{ book.publishDate }}</p>
          <p><strong>裝訂 / 頁數：</strong>平裝 / {{ book.pages }} 頁</p>
          <p><strong>庫存：</strong>{{ book.stock }}</p>
        </div>

        <!-- 右側：操作按鈕 -->
        <div class="price-cart">
          <p><strong>定價：</strong>NT$ {{ book.price }}</p>
          <p><strong>優惠價：</strong>{{ book.discount }} 折 NT$ {{ discountedPrice }}</p>
          <button @click="addToCart">加入購物車</button>
          <button @click="toggleFavorite">
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
  </div>
</template>

<script>
import defaultImage from "../assets/default-book-cover.png";
import { fetchBookDetail } from "@/services/bookService";
import { useRoute } from "vue-router";

export default {
  name: "BookDetail",
  data() {
    return {
      book: {}, // 儲存書籍詳細資料
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
      const id = route.params.id; // 從路由參數中獲取書籍 ID
      const data = await fetchBookDetail(id);
      this.book = data;
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
