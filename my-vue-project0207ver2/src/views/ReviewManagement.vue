<template>
  <div class="container mx-auto py-4">
    <h1 class="text-2xl font-bold mb-4">All Reviews</h1>

    <table class="w-full table-auto border-collapse border border-gray-300">
      <thead>
        <tr>
          <th class="border border-gray-300 px-4 py-2">評論 ID</th>
          <th class="border border-gray-300 px-4 py-2">顧客 ID</th>
          <th class="border border-gray-300 px-4 py-2">書籍 ID</th>
          <th class="border border-gray-300 px-4 py-2">評分</th>
          <th class="border border-gray-300 px-4 py-2">內容</th>
          <th class="border border-gray-300 px-4 py-2">評論時間</th>
          <th class="border border-gray-300 px-4 py-2">狀態</th>
        </tr>
      </thead>
      <tbody>
  <tr v-for="review in reviews" :key="review.reviewID">
    <td class="border border-gray-300 px-4 py-2">{{ review.reviewID }}</td>
    <td class="border border-gray-300 px-4 py-2">{{ review.customer.customerID }}</td>
    <td class="border border-gray-300 px-4 py-2">{{ review.book.bookId }}</td>
    <td class="border border-gray-300 px-4 py-2">
      {{ '★'.repeat(review.rating) }}{{ '☆'.repeat(5 - review.rating) }}
    </td>
    <td class="border border-gray-300 px-4 py-2">{{ review.content }}</td>
    <td class="border border-gray-300 px-4 py-2">{{ review.reviewTime }}</td>
    <td class="border border-gray-300 px-4 py-2 flex space-x-2">
      <button
        @click="editReview(review)"
        class="px-2 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700"
      >
        修改
      </button>
      <button
        @click="deleteReview(review.reviewID)"
        class="px-2 py-1 bg-red-600 text-white rounded-md hover:bg-red-700"
      >
        刪除
      </button>
    </td>
  </tr>
</tbody>

    </table>

    <!-- 修改評論模態框 -->
    <div v-if="isEditModalOpen" class="modal-overlay">
      <div class="modal">
        <h3 class="text-xl font-bold mb-4">修改評論</h3>
        <form @submit.prevent="updateReview">
          <div class="mb-4">
  <label for="editRating" class="block text-sm font-medium">Rating:</label>
  <div class="star-rating">
    <span
      v-for="rating in 5"
      :key="'edit-' + rating"
      class="text-2xl cursor-pointer"
      @click="currentReview.rating = rating"
      :class="{
        'text-yellow-500': rating <= currentReview.rating,
        'text-gray-300': rating > currentReview.rating,
      }"
    >
      ★
    </span>
  </div>
</div>


          <div class="mb-4">
            <label for="editContent" class="block text-sm font-medium">內容</label>
            <textarea
              v-model="currentReview.content"
              id="editContent"
              required
              class="mt-1 block w-full border-gray-300 rounded-md"
            ></textarea>
          </div>
          <button
            type="submit"
            class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
          >
            保存修改
          </button>
          <button
            @click="closeEditModal"
            class="ml-2 px-4 py-2 bg-gray-600 text-white rounded-md hover:bg-gray-700"
          >
            取消
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2"; // 引入 SweetAlert2

export default {
  data() {
    return {
      reviews: [],
      isEditModalOpen: false,
      currentReview: {
        reviewID: null,
        rating: 0,
        content: "",
      },
    };
  },
  methods: {
    async fetchReviews() {
      try {
        const response = await fetch("http://localhost:8080/reviews");
        if (!response.ok) throw new Error("Failed to fetch reviews");
        const text = await response.text();
        this.reviews = text ? JSON.parse(text) : [];
      } catch (error) {
        alert(error.message);
      }
    },
    async deleteReview(reviewID) {
      try {
        const response = await fetch(
          `http://localhost:8080/reviews/${reviewID}`,
          { method: "DELETE" }
        );
        if (!response.ok) throw new Error("Failed to delete review");
        
        // SweetAlert success notification
        Swal.fire({
          icon: "success",
          title: "刪除成功",
          text: "評論已被成功刪除！",
          timer: 2000,
          showConfirmButton: false,
        });

        this.fetchReviews();
      } catch (error) {
        alert(error.message);
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
        const response = await fetch(
          `http://localhost:8080/reviews/${this.currentReview.reviewID}`,
          {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              rating: this.currentReview.rating,
              content: this.currentReview.content,
            }),
          }
        );
        if (!response.ok) throw new Error("Failed to update review");

        // SweetAlert success notification
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
        alert(error.message);
      }
    },
    closeEditModal() {
      this.isEditModalOpen = false;
      this.currentReview = { reviewID: null, rating: 0, content: "" };
    },
  },
  mounted() {
    this.fetchReviews();
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 400px;
}
.star-rating {
  display: inline-flex; /* 保持星星水平排列 */
}
.text-yellow-500 {
  color: gold; /* 選中的金色 */
}
.text-gray-300 {
  color: lightgray; /* 未選中的灰色 */
}
.text-2xl {
  font-size: 2rem; /* 調整星星大小 */
}
.cursor-pointer {
  cursor: pointer;
  transition: color 0.3s ease; /* 增加過渡效果 */
}
</style>