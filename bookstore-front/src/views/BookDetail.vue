<template>
  <div class="page-container">
    <div class="book-detail" v-if="book">
      <div class="top-section">
        <div class="book-image">
          <img :src="book.imageUrl" alt="書籍封面" />
        </div>
        <div class="book-info">
          <h1>{{ book.bookName }}</h1>
          <p>
            <strong>分類：</strong>
            <span
              class="link"
              @click="
                $router.push({
                  name: 'SearchResults',
                  params: { keyword: book.categoryName },
                  query: { type: 'category' },
                })
              "
            >
              {{ book.categoryName }}
            </span>
            >
            <span
              class="link"
              @click="
                $router.push({
                  name: 'SearchResults',
                  params: { keyword: book.subcategoryName },
                  query: { type: 'subcategory' },
                })
              "
            >
              {{ book.subcategoryName }}
            </span>
          </p>
          <p>
            <strong>作者：</strong>
            <span
              class="link"
              @click="
                $router.push({
                  name: 'SearchResults',
                  params: { keyword: book.authorName },
                  query: { type: 'author' },
                })
              "
            >
              {{ book.authorName }}
            </span>
          </p>
          <p>
            <strong>出版社：</strong>
            <span
              class="link"
              @click="
                $router.push({
                  name: 'SearchResults',
                  params: { keyword: book.publisherName },
                  query: { type: 'publisher' },
                })
              "
            >
              {{ book.publisherName }}
            </span>
          </p>
          <p><strong>出版日期：</strong>{{ book.publishDate }}</p>
          <p><strong>庫存：</strong>{{ book.stock }}</p>
        </div>
        <div class="price-cart">
          <p><strong>定價：</strong>NT$ {{ book.price }}</p>
          <button class="cart-button" @click="addToCart">加入購物車</button>
        </div>
      </div>
      <div class="author-description">
        <h2>作者介紹</h2>
        <p
          v-if="book.authorDescription"
          v-html="formatDescription(book.authorDescription)"
        ></p>
        <p v-else>目前沒有作者介紹。</p>
      </div>

      <div class="book-description">
        <h2>商品簡介</h2>
        <!-- 0218 -->
        <p v-html="formatDescription(book.description)"></p>
      </div>

      <div class="book-reviews">
        <h2>書籍評論</h2>
        <div v-if="currentCustomerId" class="add-review">
          <h3>新增評論</h3>
          <form @submit.prevent="addReview">
            <div>
              <label>評分：</label>
              <div class="rating-stars">
                <span
                  v-for="star in 5"
                  :key="star"
                  class="star"
                  :class="{ filled: star <= newReview.rating }"
                  @click="newReview.rating = star"
                  >★
                </span>
              </div>
            </div>
            <div>
              <label for="content">內容：</label>
              <textarea v-model="newReview.content" id="content" required></textarea>
            </div>
            <button type="submit">提交評論</button>
          </form>
        </div>
        <ul v-if="reviews.length > 0" class="review-list">
          <li v-for="review in reviews" :key="review.reviewID">
            <p>
              <strong>顧客：</strong>
              {{ review.customer.customerName }}
            </p>
            <p>
              <strong>評分：</strong> {{ "★".repeat(review.rating)
              }}{{ "☆".repeat(5 - review.rating) }}
            </p>
            <p>
              <strong>評論內容：</strong>
              {{ review.content }}
            </p>
            <p>
              <strong>評論時間：</strong>
              {{ formatDateTime(review.reviewTime) }}
            </p>
          </li>
        </ul>
        <p v-else>目前還沒有評論。</p>
      </div>
    </div>

    <div v-else class="loading">
      <p>正在載入書籍資料...</p>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/authStore";
import { useCartStore } from "@/stores/cartStore";
import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

export default {
  name: "BookDetail",
  data() {
    return {
      book: null,
      reviews: [],
      newReview: { rating: null, content: "" },
      currentCustomerId: sessionStorage.getItem("customerID") || null, // ✅ 改成從 sessionStorage 讀取
      bookId: Number(this.$route.params.id),
    };
  },
  methods: {
    async initializePage() {
      try {
        await this.fetchBookDetails();
        if (this.book) {
          await this.fetchReviews();
        }
      } catch (error) {
        console.error("初始化失敗：", error.message);
      }
    },
    async fetchBookDetails() {
      try {
        const response = await axiosapi.get(`/api/books/${this.bookId}`);
        this.book = response.data;
      } catch (error) {
        console.error("無法取得書籍資料", error);
      }
    },
    async fetchBookData() {
      const id = this.$route.params.id;
      const data = await fetchBookDetail(id);
      if (data) {
        this.book = { ...data };
      }
    },
    async addToCart() {
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
        if (!this.bookId || isNaN(this.bookId)) {
          Swal.fire({
            icon: "error",
            title: "錯誤",
            text: "無效的書籍 ID",
          });
          return;
        }
        const response = await axiosapi.post("/api/cart/add", {
          customerId: authStore.customerId,
          bookId: this.bookId,
          quantity: 1,
        });
        if (response.data?.cartId) {
          localStorage.setItem("cartId", response.data.cartId);
          const cartStore = useCartStore();
          await cartStore.fetchCartItems();
          Swal.fire({
            icon: "success",
            title: "成功",
            text: response.data.message,
          });
        } else {
          Swal.fire({
            icon: "error",
            title: "錯誤",
            text: response.data.message || "加入購物車失敗",
          });
        }
      } catch (error) {
        console.error("加入購物車時發生錯誤:", error);
        Swal.fire({
          icon: "error",
          title: "發生錯誤",
          text: "發生錯誤，請稍後再試。",
        });
      }
    },
    async fetchReviews() {
      try {
        const bookId = this.book?.bookId || this.book?.id;
        if (!bookId) throw new Error("缺少書籍 ID，無法加載評論！");

        const response = await axiosapi.get(`/reviews`, { params: { bookId } });
        this.reviews = response.data.filter((review) => review.book.bookId === bookId);
      } catch (error) {
        console.error("Error fetching reviews:", error.response?.data || error.message);
      }
    },

    async addReview() {
      if (this.newReview.rating && this.newReview.content) {
        try {
          const bookId = this.book?.bookId || this.book?.id;
          if (!bookId) throw new Error("無法找到書籍 ID，無法提交評論！");

          const reviewData = {
            rating: this.newReview.rating,
            content: this.newReview.content,
            book: { bookId },
            customer: { customerID: this.currentCustomerId },
          };

          const response = await axiosapi.post(`/reviews`, reviewData);
          this.reviews.unshift(response.data);
          this.newReview = { rating: null, content: "" };
        } catch (error) {
          console.error("新增評論失敗：", error.response?.data || error.message);
          alert("提交評論失敗，請稍後再試！");
        }
      } else {
        alert("請填寫完整的評論內容！");
      }
    },
    formatDateTime(datetime) {
      if (!datetime) return "";
      const date = new Date(datetime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    },
    formatDescription(description) {
        if (!description) return "";  // 避免空值錯誤
        return description.replace(/\\n/g, "").replace(/\n/g, "<br>");
    }
  },
  mounted() {
    this.initializePage();
  },
};
</script>

<style scoped>
/* 整體容器，限制寬度並居中 */
.page-container {
  max-width: 1200px;
  margin: 0 auto;
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
  flex-wrap: wrap;
  gap: 40px;
  height: 400px;
  align-items: flex-start;
  justify-content: flex-start;
}
/* 左側圖片 */
.book-image {
  flex: 1; /* 左側佔比 */
  /* display: flex; */
  /* max-width: 400px; */
  min-width: 200px;
  justify-content: center;
  align-items: center;
}
.book-image img {
  width: 100%;
  height: auto;
  /* border: 1px solid #ccc; */
  border-radius: 5px;
  position: relative; /* 🔹 確保圖片不會覆蓋其他元素 */
  z-index: 1;
}
/* 中間書籍資訊 */
.book-info {
  flex: 2; /* ✅ 增加比例，讓資訊區塊變寬 */
  font-size: 17px;
  /* line-height: 1.6; */
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 600px; /* 🔹 讓內容左對齊 */
}
/* 書籍標題 (h1) 調整對齊 */
.book-info h1 {
  font-size: 26px;
  color: #333;
  margin-bottom: 10px;
}
/* 右側價格與按鈕 */
.price-cart {
  flex: 1; /* 右側佔比 */
  background-color: #f8f8f8;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
  /* display: flex; */
  flex-direction: column;
  justify-content: center;
  /* gap: 10px; */
  font-weight: bold;
  padding-top: 5px;
}
.price-cart p {
  font-size: 18px;
  margin-bottom: 5px;
}
.cart-button {
  background-color: #2e995b;
  color: white;
  font-size: 17px;
  border: none;
  padding: 7px;
  margin-top: 8px;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}
.cart-button:hover {
  background-color: #57bb48;
}
/* 商品簡介 */
.book-description {
  /* margin-top: 20px; */
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
  font-size: 16px;
  line-height: 1.5;
  white-space: pre-line; /* ✅ 讓 \n 轉換為換行 */
}
.rating-stars {
  display: flex;
  gap: 5px;
}
.star {
  font-size: 24px;
  cursor: pointer;
}
.star.filled {
  color: gold;
}
.star:not(.filled) {
  color: lightgray;
}
.link {
  color: #007bff; /* 藍色文字 */
  text-decoration: underline; /* 顯示下劃線 */
  cursor: pointer; /* 滑鼠顯示為手型 */
}
.link:hover {
  color: #0056b3; /* 更深的藍色，顯示 hover 效果 */
}
/* 書籍評論區塊樣式 */
.book-reviews {
  margin-top: 20px;
  background-color: #f9f9f9;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
.book-reviews h2 {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
  color: #333;
}
/* 評論列表 */
.review-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.review-list li {
  margin-bottom: 20px;
  padding: 15px;
  background-color: white;
  border-radius: 8px;
  border: 1px solid #ddd;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.05);
}
/* 顧客名稱 */
.review-list li p strong {
  color: #6200ea;
  font-weight: bold;
}
/* 評分星星 */
.review-list li p:nth-child(2) {
  font-size: 16px;
  color: gold;
  margin: 5px 0;
}
/* 評論內容 */
.review-list li p:nth-child(3) {
  margin: 10px 0;
  line-height: 1.6;
  color: #333;
}
/* 評論時間 */
.review-list li p:nth-child(4) {
  font-size: 12px;
  color: #888;
  text-align: right;
}
/* 新增評論表單 */
.add-review {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.05);
}
.add-review h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}
.add-review label {
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 5px;
  display: block;
  color: #555;
}
.add-review textarea {
  width: 100%;
  height: 80px;
  margin-top: 5px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}
.add-review button {
  background-color: #6200ea;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
}
.add-review button:hover {
  background-color: #3700b3;
}
.author-description {
  background-color: #f9f9f9;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: left;
  margin-top: 20px;
}
.author-description h2 {
  text-align: center;
  font-size: 18px;
}
.author-description p {
  font-size: 16px;
  line-height: 1.5;
  white-space: pre-line; /* ✅ 讓 \n 轉換為換行 */
}
</style>
