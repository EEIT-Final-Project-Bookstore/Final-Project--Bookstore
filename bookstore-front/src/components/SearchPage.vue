<template>
	<div class="search-page">
	  	<!-- 搜尋容器（含搜尋類型下拉、輸入框、搜尋按鈕） -->
		<div class="search-bar-container" ref="searchContainer">
			<!-- 下拉：搜尋類型 -->
			<select v-model="searchType" class="filter-select">
				<option value="title">📖 書名</option>
				<option value="author">✍️ 作者</option>
				<option value="category">📂 類別</option>
				<option value="publisher">🏢 出版社</option>
				<option value="subcategory">📌 子類別</option>
			</select>
			<input
				type="text"
				placeholder="輸入書名、作者..."
				class="search-bar"
				v-model="keyword"
				@focus="showHistory = true"
				@click.stop="showHistory = true"
				@keyup.enter="search"
			/>
			<!-- 搜尋按鈕 -->
			<button class="search-btn" @click="search">搜尋</button>
			<div
				v-if="showHistory && (searchHistory.length || results.length)"
				class="search-history"
				@click.self="showHistory = false"
			>
				<h3>查詢紀錄</h3>
				<ul v-if="searchHistory.length" class="history-list">
					<li
					v-for="(history, index) in searchHistory"
					:key="index"
					class="history-item"
					>
					<span @click="searchFromHistory(history)" class="history-text">
						{{ history }}
					</span>
					<button class="delete-btn" @click="deleteHistory(index)">🗑</button>
					</li>
				</ul>
		
				<!-- API 搜尋結果 -->
				<ul v-if="results.length" class="api-results">
					<li v-for="result in results" :key="result.id" class="result-item">
					{{ result.name }}
					</li>
				</ul>
			</div>
		</div>
	</div>
</template>
  
<script>
  import axiosapi from "@/plugins/axios.js";
  
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
	},
	mounted() {
	  document.addEventListener("mousedown", this.handleClickOutside);
	},
	beforeUnmount() {
	  document.removeEventListener("mousedown", this.handleClickOutside);
	},
	// 路由切換時關閉紀錄UI
	$route() {
	  this.showHistory = false;
	},
	beforeRouteLeave(to, from, next) {
	  this.showHistory = false;
	  next();
	},
	methods: {
		// 執行搜尋
		async search() {
			const trimmedKeyword = this.keyword.trim();
			if (!trimmedKeyword) {
			return;
			}
			this.saveSearchHistory(trimmedKeyword);
			// 切換到搜尋結果頁面
			this.$router.push({
			name: "SearchResults",
			params: { keyword: trimmedKeyword },
			query: { type: this.searchType },
			});
			// 呼叫 API 取得結果
			try {
			const response = await axiosapi.get("/api/search", {
				params: { keyword: trimmedKeyword, type: this.searchType },
			});
			this.results = response.data;
			} catch (error) {
			console.error("❌ API 查詢失敗:", error);
			}
		},
	
		// 點擊歷史紀錄後，直接帶入搜尋
		searchFromHistory(historyKeyword) {
			this.keyword = historyKeyword;
			this.$nextTick(() => {
			this.search();
			});
		},
	
		// 將新搜尋關鍵字存入 Cookie
		saveSearchHistory(newKeyword) {
			const history = this.getCookie("searchHistory")
			? JSON.parse(this.getCookie("searchHistory"))
			: [];
			if (!history.includes(newKeyword)) {
			history.push(newKeyword);
			if (history.length > 10) history.shift(); // 最多保留10筆
			document.cookie = `searchHistory=${JSON.stringify(
				history
			)};path=/;max-age=31536000`;
			this.loadSearchHistory();
			}
		},
	
		// 刪除某筆歷史
		deleteHistory(index) {
			const history = this.getCookie("searchHistory")
			? JSON.parse(this.getCookie("searchHistory"))
			: [];
			history.splice(index, 1);
			document.cookie = `searchHistory=${JSON.stringify(
			history
			)};path=/;max-age=31536000`;
			this.loadSearchHistory();
		},
	
		// 全刪
		clearAllHistory() {
			document.cookie = "searchHistory=;path=/;max-age=0";
			this.searchHistory = [];
		},
	
		// 載入 Cookie 中的搜尋紀錄
		loadSearchHistory() {
			const history = this.getCookie("searchHistory");
			this.searchHistory = history ? JSON.parse(history) : [];
		},
	
		// 取得 Cookie
		getCookie(name) {
			const value = `; ${document.cookie}`;
			const parts = value.split(`; ${name}=`);
			if (parts.length === 2) return parts.pop().split(";").shift();
		},
	
		// 點擊外部，關閉下拉 UI
		handleClickOutside(event) {
			if (
			!this.$refs.searchContainer ||
			this.$refs.searchContainer.contains(event.target)
			) {
			return; // 點擊在搜尋框內
			}
			this.showHistory = false;
		},
	},
  };
</script>
  
<style scoped>
	/* 主要的搜尋容器 */
	.search-bar-container {
		display: flex;
		align-items: center;
		background: #fffffe;
		border-radius: 15px;
		padding: 10px 15px;
		box-shadow: 0px 3px 8px rgba(0, 0, 0, 0.1);
		transition: 0.3s;
		width: 85%;
		max-width: 700px;
		margin: 0 auto;
		position: relative;
	}
	/* 搜尋類型下拉 */
	.filter-select {
		border: none;
		outline: none;
		background: none;
		font-size: 16px;
		margin-right: 8px;
		cursor: pointer;
	}
	/* 文字輸入框 */
	.search-bar {
		border: none;
		outline: none;
		flex: 1; /* 撐滿剩餘空間 */
		font-size: 16px;
		padding: 5px;
		background: none;
	}
	/* 搜尋按鈕 */
	.search-btn {
		width: 150px;
		background-color:#9eb197;
		color: white;
		padding: 8px 15px;
		font-size: 15px;
		font-weight: bold;
		border: none;
		border-radius: 10px;
		cursor: pointer;
		transition: background 0.3s;
		margin-left: 8px;
	}
	.search-btn:hover {
		background-color: #9acea6;
	}
	/* 歷史紀錄 + API 結果統一用 search-history 下拉容器 */
	.search-history {
		position: absolute;
		top: 60px; /* 與 .search-bar-container 高度錯開 */
		left: 0;
		right: 0;
		background: white;
		border-radius: 10px;
		box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.15);
		z-index: 100;
		max-height: 250px;
		overflow-y: auto;
		padding: 10px;
	}
	/* 歷史紀錄列表 */
	.history-list {
		list-style: none;
		margin: 0;
		padding: 0;
	}
	.history-item {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 6px 0;
		font-size: 16px;
	}
	.history-item:hover {
		background: #f9f9f9;
	}
	.history-text {
		cursor: pointer;
		flex-grow: 1;
	}
	/* 刪除歷史按鈕 */
	.delete-btn {
		background: #d4d4d4;
		/* width: 100px; */
		border: none;
		font-size: 16px;
		cursor: pointer;
		/* color: #666; */
	}
	/* API 結果列表 */
	.api-results {
		list-style: none;
		margin: 10px 0 0;
		padding: 0;
		border-top: 1px solid #eee;
	}
	.result-item {
		padding: 6px 0;
		font-size: 16px;
		color: #333;
	}
	.result-item:hover {
		background: #f4f4f4;
	}
</style>
  