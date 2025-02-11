<template>
    <div class="category-overview">
      <h1>森途書店/中文書籍</h1>
  
      <div v-for="(books, categoryKey) in booksByCategory" :key="categoryKey" class="category-section">
        <h2>{{ categoryMapping[categoryKey] || categoryKey }}</h2>
        <div class="book-list">
          <div v-for="book in books.slice(0, 4)" :key="book.bookId" class="book-item">
            <router-link :to="`/book/${book.bookId}`">
              <img :src="book.imageUrl || defaultImage" alt="書籍封面" class="book-cover" />
              <p class="book-title">{{ book.bookName }}</p>
              <p class="book-price">NT$ {{ book.price }}</p>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "SelectedCategories",
    data() {
      return {
        booksByCategory: {},
        defaultImage: "https://via.placeholder.com/150",
        categoryMapping: {
          "親子共讀": "📖 親子共讀",
          "休閒生活": "🍵 休閒生活",
          "語言學習": "🗣 語言學習",
          "文學作品": "📚 文學作品"
        }
      };
    },
    async mounted() {
      try {
        const response = await axios.get("http://localhost:8080/api/books/selected-categories");
        this.booksByCategory = response.data;
      } catch (error) {
        console.error("獲取分類書籍失敗：", error);
      }
    }
  };
  </script>
  
  <style scoped>
  .category-overview {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    text-align: center;
  }
  
  .category-section {
    margin-bottom: 40px;
  }
  
  .category-section h2 {
    font-size: 22px;
    text-align: left;
    margin-bottom: 10px;
    padding-left: 10px;
    border-left: 5px solid #6200ea;
  }
  
  .book-list {
    display: flex;
    gap: 15px;
    justify-content: flex-start;
    overflow-x: auto;
    padding: 10px 0;
  }
  
  .book-item {
    width: 180px;
    background: white;
    padding: 10px;
    border-radius: 5px;
    text-align: center;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .book-cover {
    width: 100%;
    height: 250px;
    object-fit: cover;
    border-radius: 5px;
  }
  
  .book-title {
    font-size: 16px;
    font-weight: bold;
    margin: 5px 0;
  }
  
  .book-price {
    font-size: 18px;
    color: red;
    font-weight: bold;
  }
  </style>
  