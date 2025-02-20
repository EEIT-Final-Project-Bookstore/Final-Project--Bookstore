<template>
  <div class="container mx-auto py-4">
    <h1 class="text-2xl font-bold mb-4"></h1>
    <table class="w-full table-auto border-collapse border border-gray-300">
      <thead>
        <tr>
          <th class="border border-gray-300 px-4 py-2">評論 ID</th>
          <th class="border border-gray-300 px-4 py-2">顧客 ID</th>
          <th class="border border-gray-300 px-4 py-2">書籍名稱</th> <!-- ✅ 顯示書籍名稱 -->
          <th class="border border-gray-300 px-4 py-2">評分</th>
          <th class="border border-gray-300 px-4 py-2" style="width: 350px;">內容</th>
          <th class="border border-gray-300 px-4 py-2">評論時間</th>
          <th class="border border-gray-300 px-4 py-2">狀態</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="review in reviewsWithBookName" :key="review.reviewID">
          <td class="border border-gray-300 px-4 py-2">{{ review.reviewID }}</td>
          <td class="border border-gray-300 px-4 py-2">{{ review.customer.username }}</td> <!-- 顯示 username -->
          <td class="border border-gray-300 px-4 py-2">{{ review.bookName }}</td> <!-- ✅ 顯示書籍名稱 -->
          <td class="border border-gray-300 px-4 py-2">
            {{ '★'.repeat(review.rating) }}{{ '☆'.repeat(5 - review.rating) }}
          </td>
          <td class="border border-gray-300 px-4 py-2">{{ review.content }}</td>
          <td class="border border-gray-300 px-4 py-2">{{ review.reviewTime }}</td>
          <td class="border border-gray-300 px-4 py-2 flex space-x-2">
            <button @click="editReview(review)" class="px-2 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700">
              修改
            </button>
            <button @click="deleteReview(review.reviewID)"
              class="px-2 py-1 bg-red-600 text-white rounded-md hover:bg-red-700">
              刪除
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- 修改評論模態框 -->
    <div v-if="isEditModalOpen" class="modal-overlay">
      <div class="modal" style="display: block !important; visibility: visible !important;">
        <h3 class="text-xl font-bold mb-4">修改評論</h3>
        <form @submit.prevent="updateReview">
          <div class="mb-4">
            <label for="editRating" class="block text-sm font-medium">評分</label>
            <div class="star-rating">
              <span v-for="rating in 5" :key="'edit-' + rating" class="text-2xl cursor-pointer"
                @click="currentReview.rating = rating" :class="{
                  'text-yellow-500': rating <= currentReview.rating,
                  'text-gray-300': rating > currentReview.rating,
                }">
                ★
              </span>
            </div>
          </div>
          <div class="mb-4">
            <label for="editContent" class="block text-sm font-medium">內容</label>
            <textarea v-model="currentReview.content" id="editContent" required
              class="mt-1 block w-full border-gray-300 rounded-md"></textarea>
          </div>
          <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">保存修改</button>
          <button @click="closeEditModal"
            class="ml-2 px-4 py-2 bg-gray-600 text-white rounded-md hover:bg-gray-700">取消</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios";

export default {
    data() {
        return {
            reviews: [],
            isEditModalOpen: false,
            books: [], // ✅ 存儲所有書籍
            currentReview: {
                reviewID: null,
                rating: 0,
                content: "",
            },
        };
    },
    computed: {
        // ✅ 替換 BookID 為 BookName
        reviewsWithBookName() {
            return this.reviews.map(review => {
                const book = this.books.find(b => b.bookId === review.book.bookId);
                return {
                    ...review,
                    bookName: book ? book.bookName : "未知書籍" // 找不到時顯示"未知書籍"
                };
            });
        }
    },
    methods: {
      async fetchReviews() {
            try {
                const response = await axiosapi.get("/reviews");
                this.reviews = response.data || [];
            } catch (error) {
                Swal.fire("錯誤", error.response?.data?.message || "無法加載評論", "error");
            }
        },
        async fetchBooks() {
            try {
                const response = await axiosapi.get("/api/books");
                this.books = response.data || [];
            } catch (error) {
                Swal.fire("錯誤", error.response?.data?.message || "無法加載書籍", "error");
            }
        },
        async deleteReview(reviewID) {
            try {
                await axiosapi.delete(`/reviews/${reviewID}`);
                    Swal.fire({
                    icon: "success",
                    title: "刪除成功",
                    text: "評論已被成功刪除！",
                    timer: 2000,
                    showConfirmButton: false,
                });
                this.fetchReviews();
            } catch (error) {
                alert(error.response?.data?.message || "Failed to delete review");
            }
        },
        editReview(review) {
            this.currentReview = {
                reviewID: review.reviewID,
                rating: review.rating,
                content: review.content,
            };
            this.isEditModalOpen = true;
        },
        async updateReview() {
            try {
                await axiosapi.put(`/reviews/${this.currentReview.reviewID}`, {
                    rating: this.currentReview.rating,
                    content: this.currentReview.content,
                });
                Swal.fire({
                    icon: "success",
                    title: "修改成功",
                    text: "評論已被成功修改！",
                    timer: 2000,
                    showConfirmButton: false,
                });
                this.closeEditModal();
                this.fetchReviews();
            } catch (error) {
                alert(error.response?.data?.message || "Failed to update review");
            }
        },
        closeEditModal() {
            this.isEditModalOpen = false;
            this.currentReview = { reviewID: null, rating: 0, content: "" };
        },
    },
    mounted() {
        this.fetchReviews();
        this.fetchBooks(); 
    },
};
</script>

<style scoped>
/* ✅ 全局樣式 */
.container {
  max-width: 1300px;
  margin: 0 auto;
  padding: 20px;
}

/* ✅ 標題 */
h1 {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}


/* ✅ 表格樣式 */
table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}


th,
td {
  padding: 12px;
  border: 1px solid #ddd;
  text-align: center;
}


/* ✅ 標題列 */
thead {
  background-color: #3B82F6;
  color: white;
  font-size: 1rem;
}


/* ✅ 奇偶行交錯背景 */
tbody tr:nth-child(even) {
  background: #F9FAFB;
}


tbody tr:nth-child(odd) {
  background: #FFFFFF;
}


/* ✅ 評論星星樣式 */
.star-rating {
  display: flex;
  justify-content: center;
}


.star-rating span {
  font-size: 1.3rem;
}


/* ✅ 操作按鈕樣式 */
button {
  padding: 8px 12px;
  font-size: 0.9rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}


button:hover {
  transform: scale(1.05);
}


button:active {
  transform: scale(0.95);
}


/* ✅ 修改按鈕 */
.bg-blue-600 {
  background-color: #2563eb;
  color: white;
}


.bg-blue-600:hover {
  background-color: #1e40af;
}


/* ✅ 刪除按鈕 */
.bg-red-600 {
  background-color: #dc2626;
  color: white;
}


.bg-red-600:hover {
  background-color: #991b1b;
}


/* ✅ 模態框背景 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);


  /* ✅ 確保 .modal 保持居中 */
  display: flex;
  align-items: center;
  justify-content: center;


  z-index: 9999;
}


/* ✅ 模態框設計 */
.modal {
  background: white !important;
  width: 90% !important;
  max-width: 450px !important;
  padding: 20px !important;
  border-radius: 12px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.3) !important;
  animation: fadeIn 0.3s ease-in-out;
  z-index: 99999 !important;


  /* ✅ 強制置中 */
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}




.modal h3 {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 15px;
}


/* ✅ 評分星星 */
.star-rating {
  display: flex;
  justify-content: center;
  gap: 5px;
}


.star-rating span {
  font-size: 2rem;
  cursor: pointer;
  transition: color 0.3s ease-in-out;
}


.star-rating .text-yellow-500 {
  color: gold;
}


.star-rating .text-gray-300 {
  color: lightgray;
}


/* ✅ 輸入框 */
textarea {
  width: 95%;
  height: 80px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
}


/* ✅ 動畫效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }


  to {
    opacity: 1;
  }
}


/* ✅ 響應式設計 */
@media (max-width: 768px) {
  table {
    font-size: 0.8rem;
  }


  th,
  td {
    padding: 8px;
  }


  .modal {
    width: 95%;
  }
}
</style>