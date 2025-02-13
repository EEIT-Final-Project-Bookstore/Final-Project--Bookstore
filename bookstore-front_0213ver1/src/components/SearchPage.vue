<template>
  <div class="search-page" >
    <h1 class="title">🔍 Bookstore Search</h1>

    <!-- 🔎 搜尋輸入框 -->
    
      <div class="search-bar-container" ref="searchContainer">
        <i class="fa-solid fa-magnifying-glass search-icon"></i>
        <input 
        type="text" 
        placeholder="輸入書名、作者..." 
        class="search-bar" 
        v-model="keyword" 
        @focus="showHistory = true"
        @click.stop="showHistory = true" 
      />
        <button class="search-btn" @click="search">搜尋</button>
      </div>
  

    <!-- 📝 搜尋紀錄 -->
    <li v-for="(history, index) in searchHistory" :key="index" class="history-item">
      <span @click="searchFromHistory(history)" style="flex-grow: 1">
        {{ history }}
      </span>
      <button class="delete-btn" @click="deleteHistory(index)">🗑</button>
    </li>

    <!-- 🔽 查詢類型下拉選單 -->
    <div class="filter-container">
      <label for="searchType">搜尋類型：</label>
      <select v-model="searchType" class="filter-select">
        <option value="title">📖 書名</option>
        <option value="author">✍️ 作者</option>
        <option value="category">📂 類別</option>
        <option value="publisher">🏢 出版社</option>
        <option value="subcategory">📌 子類別</option>
      </select>
    </div>

     <!-- 搜尋結果 -->
     <div v-if="showHistory && searchHistory.length" class="search-history"  @click.self="showHistory = false">
        <ul>
          <li v-for="result in results" :key="result.id">{{ result.name }}</li>
        </ul>
      </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      keyword: "", 
      searchHistory: [], 
      searchType: "title", 
      results: [], 
      showHistory: false, 
    };
  },
  created() {
    this.loadSearchHistory();
  },watch: {
  showHistory(newVal) {
    if (!newVal) {
      console.log("🔄 搜尋紀錄 UI 應該關閉");
    }
  }
},
mounted() {
  document.addEventListener("mousedown", this.handleClickOutside);
},
beforeUnmount() {
  document.removeEventListener("mousedown", this.handleClickOutside);
},$route() {
    console.log("🔄 路由變更，關閉搜尋紀錄");
    this.showHistory = false;
  },beforeRouteLeave(to, from, next) {
  console.log("🔄 離開當前頁面，關閉搜尋紀錄");
  this.showHistory = false;
  next();
},
  methods: {
    async search() {
      const trimmedKeyword = this.keyword.trim();
      if (!trimmedKeyword) {
        alert("請輸入搜尋關鍵字!");
        return;
      }

      this.saveSearchHistory(trimmedKeyword);
      this.$router.push({
        name: "SearchResults",
        params: { keyword: trimmedKeyword },
        query: { type: this.searchType },
      });

      try {
        const response = await axios.get("http://localhost:8080/api/search", {
          params: {
            keyword: trimmedKeyword,
            type: this.searchType,
          },
        });
        this.results = response.data;
      } catch (error) {
        console.error("❌ API 查詢失敗:", error);
      }
    },

    searchFromHistory(historyKeyword) {
      this.keyword = historyKeyword;
      this.$nextTick(() => {
        this.search();
      });
    },

    saveSearchHistory(newKeyword) {
      const history = this.getCookie("searchHistory") ? JSON.parse(this.getCookie("searchHistory")) : [];
      if (!history.includes(newKeyword)) {
        history.push(newKeyword);
        if (history.length > 10) history.shift();
        document.cookie = `searchHistory=${JSON.stringify(history)};path=/;max-age=31536000`;
        this.loadSearchHistory();
      }
    },

    deleteHistory(index) {
      const history = this.getCookie("searchHistory") ? JSON.parse(this.getCookie("searchHistory")) : [];
      history.splice(index, 1);
      document.cookie = `searchHistory=${JSON.stringify(history)};path=/;max-age=31536000`;
      this.loadSearchHistory();
    },

    clearAllHistory() {
      document.cookie = "searchHistory=;path=/;max-age=0";
      this.searchHistory = [];
    },
  
    loadSearchHistory() {
      const history = this.getCookie("searchHistory");
      this.searchHistory = history ? JSON.parse(history) : [];
    },
  
    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(";").shift();
    },
    hideHistory() {
      this.showHistory = false;
this.$nextTick(() => {
  console.log("🔄 Vue 更新完成，showHistory:", this.showHistory);
});
    },
   
    handleClickOutside(event) {
  if (!this.$refs.searchContainer || this.$refs.searchContainer.contains(event.target)) {
    return; // 點擊在搜尋框內，保持打開
  }
  
  console.log("🛑 點擊發生在搜尋區塊外，關閉搜尋紀錄");
  this.showHistory = false;
}



},
  
};
</script>

<style scoped>
/* 🌟 搜尋框與標題固定 */
.search-page {
  text-align: center;
  position: relative; /* 保持內容不會亂動 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start; /* 讓標題和搜尋框保持靠上 */
  height: 250px; /* 給固定高度，避免搜尋紀錄影響 */
  padding-top: 20px;
}

/* 🔍 搜尋標題 */
.title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 15px;
  position: relative; /* 避免與搜尋框疊在一起 */
  text-align: center;
}


/* 🔍 搜尋框 */
.search-bar-container {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 讓搜尋框內部的按鈕與輸入框保持對齊 */
  background: white;
  border-radius: 30px;
  padding: 12px 20px; 
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  max-width: 600px;
  width: 100%;
  font-size: 1.2rem;
  position: relative;
}

.search-bar-container:focus-within {
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.2);
}

.search-bar {
  border: none;
  outline: none;
  flex: 1; /* 讓搜尋框填滿，但不影響按鈕 */
  font-size: 20px;
  background: none;
  padding: 10px;
  min-width: 0; /* 確保不會超過 flex 內部空間 */
}


.search-btn {
  background-color: #007bff;
  color: white;
  padding: 12px 20px;
  font-size: 18px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.3s;
  flex-shrink: 0; /* 確保按鈕大小不會被壓縮 */
  margin-left: 10px; /* 增加與搜尋框的間距 */
}

.search-btn:hover {
  background-color: #0056b3;
}

/* 🔽 搜尋紀錄 */
.search-history {
  position: absolute; /* 讓搜尋紀錄依附於搜尋框 */
  top: calc(100% + 5px); /* 讓搜尋紀錄緊貼搜尋框底部 */
  left: 50%;
  transform: translateX(-50%);
  width: inherit;
  background: white;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;  /* ✅ 確保搜尋紀錄在最上層 */
  pointer-events: auto; /* ✅ 防止無法點擊 */
}

.search-history ul {
  pointer-events: auto; /* ✅ 確保內部的 <li> 可以被點擊 */
}

.search-bar:focus ~ .search-history,
.search-container:focus-within .search-history {
  display: block;
}

.history-item {
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  font-size: 18px;
  transition: background 0.3s;
  color: black;
  background: white; /* ✅ 讓每條紀錄也是白底 */
  width: 100%; /* ✅ 讓每個項目也維持與 `.search-history` 相同的寬度 */
  box-sizing: border-box; /* ✅ 確保 padding 不會影響整體寬度 */
}

.history-item:hover {
  background: #f1f1f1;
}

.delete-btn {
  background: none;
  border: none;
  font-size: 22px;
  cursor: pointer;
  color: black;
}


</style>
