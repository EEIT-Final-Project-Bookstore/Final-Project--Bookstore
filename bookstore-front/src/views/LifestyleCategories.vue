<template>
  <div class="category-overview">
    <h2>森途書店 / 外文書籍</h2>

    <!-- 📌 篩選條件 -->
    <div class="search-container">
      <div class="filter-container">
        <!-- 🔽 熱門作者 -->
        <div class="filter-card" :class="{ collapsed: collapsedFilters.author }">
          <h3 @click="toggleFilter('author')">熱門作者</h3>
          <ul>
            <li
              v-for="author in authors"
              :key="author.authorId"
              @click="navigateToSearch('author', author.authorName)"
              class="filter-item"
            >
              {{ author.authorName }}
            </li>
          </ul>
        </div>

        <!-- 🔽 熱門分類 -->
        <div class="filter-card" :class="{ collapsed: collapsedFilters.category }">
          <h3 @click="toggleFilter('category')">熱門分類</h3>
          <ul>
            <li
              v-for="category in categories"
              :key="category.categoryId"
              @click="navigateToSearch('category', category.categoryName)"
              class="filter-item"
            >
              {{ category.categoryName }}
            </li>
          </ul>
        </div>

        <!-- 🔽 熱門出版社 -->
        <div class="filter-card" :class="{ collapsed: collapsedFilters.publisher }">
          <h3 @click="toggleFilter('publisher')">熱門出版社</h3>
          <ul>
            <li
              v-for="publisher in publishers"
              :key="publisher.publisherId"
              @click="navigateToSearch('publisher', publisher.publisherName)"
              class="filter-item"
            >
              {{ publisher.publisherName }}
            </li>
          </ul>
        </div>
      </div>

      <!-- 📌 書籍列表 -->
      <div class="results-section">
        <div
          v-for="(books, categoryKey) in booksByCategory"
          :key="categoryKey"
          class="category-section"
        >
          <h2>{{ categoryMapping[categoryKey] || categoryKey }}</h2>
          <div class="book-list">
            <div v-for="book in books.slice(0, 5)" :key="book.bookId" class="book-item">
              <router-link :to="`/book/${book.bookId}`">
                <img
                  :src="book.imageUrl || defaultImage"
                  alt="書籍封面"
                  class="book-cover"
                />
                <p class="book-title">{{ book.bookName }}</p>
              </router-link>
              <p class="book-price">NT$ {{ book.price }}</p>
              <button class="cart-button" @click="addToCart(book.bookId)">
                加入購物車
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios";
import { useAuthStore } from "@/stores/authStore";
import { useCartStore } from "@/stores/cartStore";

export default {
  name: "LifestyleCategories",
  data() {
    return {
      booksByCategory: {},
      authors: [],
      categories: [],
      publishers: [],
      collapsedFilters: {
        author: false,
        category: false,
        publisher: false,
      },
      defaultImage:
        "https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg",
      categoryMapping: {
        生活風格: "🏡 生活風格",
        藝術設計: "🎨 藝術設計",
        醫療保健: "🏥 醫療保健",
      },
    };
  },
  async mounted() {
    try {
      const [
        bookResponse,
        authorsResponse,
        categoriesResponse,
        publishersResponse,
      ] = await Promise.all([
        axiosapi.get("/api/books/lifestyle-categories"),
        axiosapi.get("/api/authors"),
        axiosapi.get("/api/categories"),
        axiosapi.get("/api/publishers"),
      ]);
      this.booksByCategory = bookResponse.data;
      this.authors = authorsResponse.data;
      this.categories = categoriesResponse.data;
      this.publishers = publishersResponse.data;
    } catch (error) {
      console.error("獲取資料失敗：", error);
    }
  },
  methods: {
    toggleFilter(filter) {
      this.collapsedFilters[filter] = !this.collapsedFilters[filter];
    },
    navigateToSearch(type, keyword) {
      console.log("🔍 導航到搜尋結果頁面，類型:", type, "關鍵字:", keyword);
      if (!type || !keyword || typeof keyword !== "string" || keyword.trim() === "") {
        console.error("❌ 缺少必要參數或 keyword 無效:", { type, keyword });
        return;
      }
      this.$router.push({
        name: "SearchResults",
        params: { keyword: keyword.trim() },
        query: { type },
      });
    },
    async addToCart(bookId) {
      try {
        const authStore = useAuthStore();
        if (!authStore.customerId) {
          Swal.fire({
            icon: "warning",
            title: "請先登入",
            text: "您需要登入才能加入購物車。",
          });
          return;
        }
        const bookResponse = await axiosapi.get(`/api/books/${bookId}`);
        if (bookResponse.data?.stock <= 0) {
          Swal.fire({ icon: "warning", title: "庫存不足", text: "該商品目前已缺貨。" });
          return;
        }
        const response = await axiosapi.post("/api/cart/add", {
          customerId: authStore.customerId,
          bookId,
          quantity: 1,
        });
        if (response.data?.cartId) {
          localStorage.setItem("cartId", response.data.cartId);
          const cartStore = useCartStore();
          await cartStore.fetchCartItems();
          Swal.fire({ icon: "success", title: "成功", text: response.data.message });
        } else {
          Swal.fire({
            icon: "error",
            title: "錯誤",
            text: response.data.message || "加入購物車失敗",
          });
        }
      } catch (error) {
        console.error("加入購物車時發生錯誤:", error);
        Swal.fire({ icon: "error", title: "發生錯誤", text: "發生錯誤，請稍後再試。" });
      }
    },
  },
};
</script>

<style scoped>
.category-overview {
  max-width: 1800px;
  margin: 0 auto;
  text-align: center;
}
.category-section {
  margin-bottom: 20px;
  padding-left: 80px;
}
.category-section h2 {
  font-size: 22px;
  text-align: left;
  padding-left: 10px;
  border-left: 5px solid #6200ea;
}
.book-list {
  width: 1400px;
  display: flex;
  gap: 40px;
  justify-content: left;
  overflow-x: auto;
  padding: 10px 0;
}
.book-item {
  width: calc(100% / 6 - 20px);
  min-height: 360px;
  background: rgb(255, 255, 255);
  padding: 10px;
  border-radius: 5px;
  text-align: center;
  border: 1px solid #ddd;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.book-cover {
  width: 100%;
  height: 250px;
  object-fit: contain;
  border-radius: 5px;
}
.book-title {
  font-size: 17px;
  font-weight: bold;
  margin: 15px 0;
  margin-bottom: 5px;
  color: rgb(37, 37, 37);
  text-decoration: none;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* ✅ 限制最多顯示 2 行 */
  line-clamp: 2; /* ✅ 添加標準屬性 */
  -webkit-box-orient: vertical;
  text-overflow: ellipsis; /* 超出省略號 */
}
.book-title:hover {
  text-decoration: underline; /* 滑鼠懸停時加底線 */
}
.book-price {
  font-size: 18px;
  color: rgb(196, 31, 31);
  font-weight: bold;
}
.cart-button {
  width: 80%;
  padding: 8px;
  font-size: 16px;
  background-color: #6200ea;
  color: white;
  margin: auto;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}
</style>
