<template>
  <div>
    <h1>Search Results for "{{ keyword }}"</h1>
    <div>
      <!-- 每頁顯示的行數選擇 -->
      <ResultsRows
        :choices="[2, 3, 4, 5, 7, 20]"
        :total="total"
        v-model="rows"
        @change="onRowsChange"
      />
      <!-- 搜尋結果列表 -->
      <ul>
        <li v-for="book in results" :key="book.bookId">
          <img :src="book.imageUrl" :alt="book.bookName" width="100" />
          <router-link :to="{ name: 'BookDetail', params: { id: book.bookId } }">
            <h3>{{ book.bookName }}</h3>
          </router-link>
          <p><strong>Author:</strong> {{ book.author }}</p>
          <p><strong>Publisher:</strong> {{ book.publisher }}</p>
          <p><strong>Publish Date:</strong> {{ book.publishDate }}</p>
          <p><strong>Price:</strong> {{ book.price }}</p>
        </li>
      </ul>
    </div>

    <!-- 分頁按鈕放置在頁面底部 -->
    <div class="pagination-container">
      <VuePaginate
        v-model="page"
        :page-count="pages"
        :page-range="3"
        :margin-pages="2"
        :click-handler="handlePageClick"
        :prev-text="'Prev'"
        :next-text="'Next'"
        :container-class="'pagination'"
        :page-class="'page-item'"
        :active-class="'active-page'"
        :disabled-class="'disabled-page'"
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
      results: [], // 搜尋結果
      total: 0, // 總書籍數量
      rows: 3, // 每頁顯示的數量
      current: 1, // 當前頁碼
      pages: 0, // 總頁數
      isLoading: false, // 加載狀態
    };
  },
  created() {
    this.fetchResults();
  },
  methods: {
    handlePageClick(page) {
  console.log("Page clicked:", page);
  if (typeof page === "number" && !isNaN(page)) {
    this.current = page; // 確認頁碼
    console.log("Updated current page to:", this.current);
    this.fetchResults();
  } else {
    console.error("Invalid page number:", page);
  }
},

fetchResults() {
  if (this.isLoading) return;
  this.isLoading = true;

  const currentPage = Number(this.current) || 1;
  const rowsPerPage = Number(this.rows) || 3;
  const start = (currentPage - 1) * rowsPerPage;
  const end = start + rowsPerPage;

  axios
    .get(`/api/search`, { params: { keyword: this.keyword } })
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
    .catch((error) => console.error("API Error:", error))
    .finally(() => (this.isLoading = false));
}
,
    onRowsChange(newRows) {
      console.log("Rows changed to:", newRows);
      this.rows = newRows;
      this.current = 1; // 當行數變更時重置到第一頁
      this.fetchResults();
    }
}

    }
    

</script>
<style >
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center; /* 水平居中 */
  padding: 10px 0;
  border-top: 1px solid #ddd; /* 與上方區域分隔 */
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
  padding: 5px 10px;
  border-radius: 5px;
  background-color: white;
  color: #333;
}

.page-item:hover {
  background-color: #e6e6e6;
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

</style> 