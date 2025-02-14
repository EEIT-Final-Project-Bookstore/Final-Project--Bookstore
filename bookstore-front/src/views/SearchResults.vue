<template>
  <div>
    <h1>「{{ keyword }}」的搜尋結果</h1>
    <div class="search-container">
      <div class="filter-container">
        <!-- 作者篩選 -->
        <div class="filter-card" :class="{ collapsed: collapsedFilters.author }">
          <h3 @click="toggleFilter('author')">依作者篩選</h3>
          <ul>
            <li v-for="author in authors" :key="author.authorId" @click="filterByAuthor(author.authorName)" class="filter-item">
              {{ author.authorName }}
            </li>
          </ul>
        </div>

        <!-- 類別篩選 -->
        <div class="filter-card" :class="{ collapsed: collapsedFilters.category }">
          <h3 @click="toggleFilter('category')">依分類篩選</h3>
          <ul>
            <li v-for="category in categories" :key="category.categoryId" @click="filterByCategory(category.categoryName)" class="filter-item">
              {{ category.categoryName }}
            </li>
          </ul>
        </div>

        <!-- 出版社篩選 -->
        <div class="filter-card" :class="{ collapsed: collapsedFilters.publisher }">
          <h3 @click="toggleFilter('publisher')">依出版社篩選</h3>
          <ul>
            <li v-for="publisher in publishers" :key="publisher.publisherId" @click="filterByPublisher(publisher.publisherName)" class="filter-item">
              {{ publisher.publisherName }}
            </li>
          </ul>
        </div>
      </div>

      <!-- 搜尋結果列表 -->
      <div class="results-section">
        <!-- 每頁顯示的筆數 -->
        <ResultsRows
          :choices="[2, 3, 4, 5, 7, 20]"
          :total="total"
          v-model="rows"
          @change="onRowsChange"
        />

        <!-- 搜尋結果 -->
        <ul class="search-results">
          <li v-for="result in results" :key="result.bookId" class="search-card">
            <!-- 書籍圖片可以點擊 -->
            <router-link :to="{ name: 'BookDetail', params: { id: result.bookId } }">
              <img :src="result.imageUrl" :alt="result.bookName" class="book-image clickable" />
            </router-link>

            <!-- 書籍資訊 -->
            <div class="book-info">
              <router-link :to="{ name: 'BookDetail', params: { id: result.bookId } }" class="book-title">
                {{ result.bookName }}
              </router-link>
              <p class="book-info"><strong>作者：</strong> {{ result.authorName }}</p>
              <p class="book-info"><strong>出版社：</strong> {{ result.publisherName }}</p>
              <p class="book-price">價格：NT$ {{ result.price }}</p>
            </div>

            <!-- 操作按鈕 -->
            <div class="action-buttons">
              <button>📌 加入收藏</button>
              <button>🛒 加入購物車</button>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <!-- 分頁區塊 -->
    <div class="pagination-container">
      <VuePaginate
        v-model="page"
        :page-count="pages"
        :page-range="3"
        :margin-pages="2"
        :click-handler="handlePageClick"
        prev-text="« 上一頁"
        next-text="下一頁 »"
        container-class="pagination"
        page-class="page-item"
        active-class="active-page"
        disabled-class="disabled-page"
      />
    </div>
  </div>
</template>



<script>
import axios from "axios";
import ResultsRows from "../components/ResultsRows.vue";
import VuePaginate from "vuejs-paginate-next";

export default {
  components: {
    VuePaginate,
    ResultsRows,
  },
  props: ["keyword"],
  data() {
    return {
      collapsedFilters: {
        author: false,
        category: false,
        publisher: false
      },
      authors: [], // 作者列表
      categories: [], // 類別列表
      publishers: [], // 出版社列表
      page:1,//
      results: [], // 搜尋結果
      total: 0, // 總書籍數量
      rows: 3, // 每頁顯示的數量
      current: 1, // 當前頁碼
      pages: 0, // 總頁數
      isLoading: false, // 加載狀態
    };
  },
  created() {
 

  this.fetchAuthorsAndBooks(); // 加載作者列表
  this.fetchCategories(); // 加載類別列表
  this.fetchPublishers(); // 初始化時獲取出版社列表
  this.fetchResults(); // 關鍵字確定後執行搜尋
  }  
,
  methods: {
    toggleFilter(filter) {
      this.collapsedFilters[filter] = !this.collapsedFilters[filter];
    },
    handlePageClick(page) {
  
  if (typeof page === "number" && !isNaN(page)) {
    this.current = page; // 確認頁碼
    this.fetchResults();
  } else {
    console.error("Invalid page number:", page);
  }
},
// 加載作者列表
// 獲取作者列表
fetchAuthorsAndBooks() {

    // 1️⃣ 發送 API 請求以獲取所有作者
    axios
      .get("http://localhost:8080/api/authors") // 假設這是返回作者的 API
      .then((response) => {
        if (Array.isArray(response.data)) {
          this.authors = response.data;
        } else {
          console.error("❌ API 回應格式錯誤，無法解析作者列表:", response.data);
        }
      })
      .catch((error) => {
        console.error("❌ 加載作者列表失敗:", error);
      });
  },
 // 獲取類別列表
 fetchCategories() {
      axios
        .get("http://localhost:8080/api/categories")
        .then((response) => {
          if (Array.isArray(response.data)) {
            this.categories = response.data;
          } else {
            console.error("❌ 無法解析類別列表:", response.data);
          }
        })
        .catch((error) => console.error("❌ 加載類別列表失敗:", error));
    },
  // 點擊作者篩選書籍
  filterByAuthor(authorName) {

    // 2️⃣ 發送 API 請求以篩選該作者的書籍
    axios
      .get("http://localhost:8080/api/search/by-author-name", {
        params: { authorName },
      })
      .then((response) => {
        if (Array.isArray(response.data)) {
          this.results = response.data;
          this.total = response.data.length;
          this.pages = Math.ceil(this.total / this.rows);
          console.log("📌 API 回傳書籍資料:", this.results);  // <-- 確認書籍是否有資料
        } else {
          console.error("❌ API 回應格式錯誤，無法解析書籍列表:", response.data);
        }
      })
      .catch((error) => {
        console.error("❌ 加載篩選結果失敗:", error);
      });
  }, // 點擊類別篩選書籍
    filterByCategory(categoryName) {
      axios
        .get("http://localhost:8080/api/search/by-category-name", {
          params: { categoryName },
        })
        .then((response) => {
          if (Array.isArray(response.data)) {
            this.results = response.data;
            this.total = response.data.length;
            this.pages = Math.ceil(this.total / this.rows);
          } else {
            console.error("❌ 無法解析書籍列表:", response.data);
          }
        })
        .catch((error) => console.error("❌ 加載篩選結果失敗:", error));
    },fetchPublishers() {
    axios
      .get("http://localhost:8080/api/publishers")
      .then((response) => {
        if (Array.isArray(response.data)) {
          this.publishers = response.data;
        } else {
          console.error("❌ 無法解析出版社列表:", response.data);
        }
      })
      .catch((error) => console.error("❌ 加載出版社列表失敗:", error));
  },

  filterByPublisher(publisherName) {
    console.log("🏢 選擇出版社:", publisherName);
    axios
      .get("http://localhost:8080/api/search/by-publisher-name", {
        params: { publisherName },
      })
      .then((response) => {
        if (Array.isArray(response.data)) {
          this.results = response.data;
          this.total = response.data.length;
          this.pages = Math.ceil(this.total / this.rows);
        } else {
          console.error("❌ 無法解析書籍列表:", response.data);
        }
      })
      .catch((error) => console.error("❌ 加載篩選結果失敗:", error));
  },

fetchResults() {
  if (this.isLoading) return;
  this.isLoading = true;

  if (!this.keyword) {
    console.error("❌ 關鍵字為空，請輸入搜尋詞");
    this.isLoading = false;
    return;
  }
  let apiUrl = "http://localhost:8080/api/search";
  let params = {
    keyword: this.keyword,
    page: this.current,  // ✅ 讓後端處理分頁
    size: this.rows      // ✅ 讓後端控制每頁筆數
  };

  // ✅ 根據查詢類型設置 API 和參數
  switch (this.$route.query.type) {
    case "title":
      params = { keyword: this.keyword };
      break;
    case "author":
      apiUrl += "/by-author-name";
      params = { authorName: this.keyword };
      break;
    case "publisher":
      apiUrl += "/by-publisher-name";
      params = { publisherName: this.keyword };
      break;
    case "category":
      apiUrl += "/by-category-name";
      params = { categoryName: this.keyword };
      break;
    case "subcategory":
      apiUrl += "/by-subcategory-name";
      params = { subcategoryName: this.keyword };
      break;
    default:
      console.error("❌ 無效的查詢類型:", this.$route.query.type);
      this.isLoading = false;
      return;
  }

  
  const currentPage = Number(this.current) || 1;
  const rowsPerPage = Number(this.rows) || 3;
  const start = (currentPage - 1) * rowsPerPage;
  const end = start + rowsPerPage;


  // 發送 API 請求
  axios
    .get(apiUrl, { params })
    .then((response) => {
      if (Array.isArray(response.data)) {
        this.results = response.data.slice(start, end);
        this.total = response.data.length;
        this.pages = Math.ceil(this.total / rowsPerPage);
      } else {
        this.results = [];
        this.total = 0;
        this.pages = 0;
      }

    

      
    })
    .catch((error) => {
      console.error("❌ API 錯誤:", error);
    })
    .finally(() => {
      this.isLoading = false;
    });
}

,
    onRowsChange(newRows) {
     
      this.current = 1; // 當行數變更時重置到第一頁
      this.fetchResults();
    }
},watch: {
  keyword(newKeyword) {
   
    this.fetchResults();
  }
}


    }
    

</script>
<style >
/* 📌 調整主搜尋結果區塊，使篩選區在左側，書籍列表在右側 */
.search-container {
  display: flex;
  align-items: flex-start;
  gap: 20px;
}
/* 📌 過濾搜尋區塊美化 */
.filter-container {
  width: 250px;
  background: #ffffff; /* 白色背景，對比清楚 */
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  flex-shrink: 0;
  font-family: Arial, sans-serif;
}

/* 📌 讓可折疊標題變得清楚 */
.filter-card h3 {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin: 0;
  padding: 10px 15px;
  border-bottom: 2px solid #007bff;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f5f5f5;
}

/* 🔽 讓箭頭有翻轉效果 */
.filter-card h3::after {
  content: "▲";
  font-size: 12px;
  transition: transform 0.3s;
}

/* ✅ 點擊後展開效果 */
.filter-card.collapsed h3::after {
  transform: rotate(180deg);
}

/* 📦 隱藏選項（折疊效果） */
.filter-card.collapsed ul {
  display: none;
}

/* ✅ 篩選選項樣式 */
.filter-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  font-size: 14px;
  color: #555;
  cursor: pointer;
  transition: background 0.3s ease, color 0.3s ease;
  border-bottom: 1px solid #ddd; /* 分隔線 */
}

/* 🔍 滑鼠懸停效果 */
.filter-item:hover {
  background: #007bff;
  color: white;
}

/* 🎯 美化滾動條 */
.filter-card ul::-webkit-scrollbar {
  width: 6px;
}

.filter-card ul::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 3px;
}

.filter-card ul::-webkit-scrollbar-track {
  background: #f8f8f8;
}




/* 📚 書籍結果區塊 */
.results-section {
  flex-grow: 1;
}

/* 📖 書籍列表（改為橫向列表） */
.search-results {
  display: flex;
  flex-direction: column; /* 讓書籍橫向排列 */
  gap: 15px;
  padding: 0;
  list-style: none;
}

/* 📚 單本書籍卡片 */
.search-card {
  display: flex;
  align-items: center;
  background: white;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  padding: 15px;
  transition: transform 0.3s ease-in-out;
  text-align: left;
}

/* ✅ 書籍卡片懸停效果 */
.search-card:hover {
  transform: scale(1.02);
}

/* 📷 書籍圖片 */
.book-image {
  width: 120px;
  height: 160px;
  object-fit: cover;
  border-radius: 10px;
  flex-shrink: 0;
}

/* 📋 書籍資訊 */
.book-info {
  flex-grow: 1;
  margin-left: 15px;
}

/* 📖 書名 */
.book-title {
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
  text-decoration: none;
}

.book-title:hover {
  text-decoration: underline;
}

/* 📚 其他書籍資訊 */
.book-author,
.book-publisher,
.book-price {
  font-size: 0.9rem;
  color: #666;
}

/* 💰 價格強調 */
.book-price {
  font-size: 1rem;
  color: #d9534f;
  font-weight: bold;
}

/* 🎯 操作按鈕 */
.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-left: 15px;
}

.action-buttons button {
  background: #007bff;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

.action-buttons button:hover {
  background: #0056b3;
}

/* 🔽 分頁樣式 */
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  padding: 10px 0;
  border-top: 1px solid #ddd;
  background-color: #f9f9f9;
}

.pagination {
  display: flex;
  list-style: none;
  padding: 0;
}

.page-item {
  margin: 0 5px;
  cursor: pointer;
  border: 1px solid #ddd;
  padding: 8px 14px;
  border-radius: 5px;
  background-color: white;
  color: #333;
  transition: background 0.3s, color 0.3s;
}

.page-item:hover {
  background-color: #007bff;
  color: white;
}

.active-page {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

.disabled-page {
  pointer-events: none;
  opacity: 0.6;
}

.clickable {
  cursor: pointer; /* 變成手型游標 */
  transition: transform 0.2s ease-in-out;
}

.clickable:hover {
  transform: scale(1.05); /* 滑鼠懸停時稍微放大 */
}



</style>