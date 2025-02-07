<template>
    <div>
      <h1>Bookstore Search</h1>
  
      <!-- 搜尋輸入框 -->
      <input 
        v-model="keyword" 
        type="text" 
        placeholder="Enter keyword to search"
      />
      <!-- 查詢類型下拉選單 -->
      <select v-model="searchType">
        <option value="title">Title</option>
        <option value="author">Author</option>
        <option value="category">Category</option>
        <option value="publisher">Publisher</option>
        <option value="subcategory">Subcategory</option> <!-- 新增子分類選項 -->
      </select>
      <button @click="search">Search</button>
  
      <!-- 搜尋紀錄 -->
      <div v-if="searchHistory.length">
        <h2>Search History</h2>
        <ul>
          <li v-for="(history, index) in searchHistory" :key="index">
            <button @click="searchFromHistory(history)">{{ history }}</button>
            <button @click="deleteHistory(index)">Delete</button>
          </li>
        </ul>
        <button @click="clearAllHistory">Clear All History</button>
      </div>

      <!-- 搜尋結果 -->
      <div v-if="results.length">
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
        keyword: "", // 綁定輸入框的數據
        searchHistory: [], // 搜尋紀錄
        searchType: "title", // 綁定下拉選單的數據
        results: [], // 搜尋結果
      };
    },
    created() {
      this.loadSearchHistory(); // 載入搜尋紀錄
    },
    methods: {
      // 搜尋並儲存關鍵字
      async search() {
  const trimmedKeyword = this.keyword.trim();
  if (!trimmedKeyword) {
    alert("Please enter a keyword!");
    return;
  }

  console.log("🔍 搜尋條件:", { keyword: trimmedKeyword, type: this.searchType });

  this.saveSearchHistory(trimmedKeyword);
  this.$router.push({ 
    name: "SearchResults", 
    params: { keyword: trimmedKeyword }, 
    query: { type: this.searchType } // ✅ 確保 `type` 有正確帶入
  });

  try {
    const response = await axios.get(`http://localhost:8080/api/search`, {
      params: {
        keyword: trimmedKeyword,
        type: this.searchType,
      },
    });
    console.log("✅ API 回應:", response.data);
    this.results = response.data;
    this.searchHistory.push(`${this.searchType}: ${trimmedKeyword}`);
    console.log("發送 API 查詢:", `http://localhost:8080/api/search?keyword=${this.keyword}&type=${this.searchType}`);

  } catch (error) {
    console.error("❌ API 查詢失敗:", error);
  }
}
,
  
      // 從歷史紀錄中搜尋
      searchFromHistory(historyKeyword) {
        this.keyword = historyKeyword;
        this.search();
      },
  
      // 儲存搜尋紀錄到 Cookie
      saveSearchHistory(newKeyword) {
        const history = this.getCookie("searchHistory")
          ? JSON.parse(this.getCookie("searchHistory"))
          : [];
        if (!history.includes(newKeyword)) {
          history.push(newKeyword);
          if (history.length > 10) history.shift(); // 限制最多保存 10 條
          document.cookie = `searchHistory=${JSON.stringify(history)};path=/;max-age=31536000`; // 保存 1 年
          this.loadSearchHistory(); // 更新本地記錄
        }
      },
  
      // 刪除單條搜尋紀錄
      deleteHistory(index) {
        const history = this.getCookie("searchHistory")
          ? JSON.parse(this.getCookie("searchHistory"))
          : [];
        history.splice(index, 1); // 刪除指定索引的紀錄
        document.cookie = `searchHistory=${JSON.stringify(history)};path=/;max-age=31536000`;
        this.loadSearchHistory(); // 更新本地記錄
      },
  
      // 清空所有搜尋紀錄
      clearAllHistory() {
        document.cookie = "searchHistory=;path=/;max-age=0"; // 刪除 Cookie
        this.searchHistory = []; // 清空本地記錄
      },
  
      // 載入搜尋紀錄
      loadSearchHistory() {
        const history = this.getCookie("searchHistory");
        this.searchHistory = history ? JSON.parse(history) : [];
      },
  
      // 獲取 Cookie 值
      getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(";").shift();
      },
    },
  };
  </script>
