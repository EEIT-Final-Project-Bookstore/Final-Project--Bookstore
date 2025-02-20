<template>
  <div class="bookList-container">
    <div class="login-content">
      <h1>書籍列表</h1>

      <!-- 新增書籍按鈕 -->
      <button @click="showAddBookModal" class="btn-add">新增書籍</button>

      <!-- 書籍列表表格 -->
      <table class="book-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>書名</th>
            <th>價格</th>
            <th>庫存</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="book in paginatedBooks" :key="book.bookId">
            <td>{{ book.bookId }}</td>
            <td>{{ book.bookName }}</td>
            <td>{{ book.price }}</td>
            <td>{{ book.stock }}</td>
            <td>
              <button class="btn-edit" @click="editBook(book)">修改</button>
              <button class="btn-delete" @click="deleteBook(book.bookId)">刪除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 分頁控制 -->
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1 || totalPages === 1">
          上一頁
        </button>
        <span>第 {{ currentPage }} 頁 / 共 {{ Math.max(1, totalPages) }} 頁</span>
        <!-- ✅ 修正顯示問題 -->
        <button
          @click="nextPage"
          :disabled="currentPage >= totalPages || totalPages === 1"
        >
          下一頁
        </button>
      </div>

      <!-- 新增 / 編輯書籍的 Modal 彈窗 -->
      <div v-if="isEditModalVisible" class="modal">
        <div class="modal-content">
          <h3>{{ editForm.bookId ? "編輯書籍" : "新增書籍" }}</h3>
          <form @submit.prevent="saveBook">
            <!-- 第 1 行：書名 / 作者 -->
            <div class="modal-row">
              <div class="form-group">
                <label for="bookName">書名</label>
                <input v-model="editForm.bookName" type="text" id="bookName" required />
              </div>
              <div class="form-group">
                <label for="authorId">作者</label>
                <select v-model="editForm.authorId" id="authorId">
                  <option
                    v-for="author in authors"
                    :key="author.authorId"
                    :value="author.authorId"
                  >
                    {{ author.authorName }}
                  </option>
                </select>
              </div>
            </div>
            <!-- 第 2 行：出版社 / 語言 -->
            <div class="modal-row">
              <div class="form-group">
                <label for="publisherId">出版社</label>
                <select v-model="editForm.publisherId" id="publisherId">
                  <option
                    v-for="publisher in publishers"
                    :key="publisher.publisherId"
                    :value="publisher.publisherId"
                  >
                    {{ publisher.publisherName }}
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label for="languageId">語言</label>
                <select v-model="editForm.languageId" id="languageId">
                  <option
                    v-for="language in languages"
                    :key="language.languageId"
                    :value="language.languageId"
                  >
                    {{ language.language }}
                  </option>
                </select>
              </div>
            </div>
            <!-- 第 3 行：子分類 / 價格 -->
            <div class="modal-row">
              <div class="form-group">
                <label for="subcategoryId">子分類</label>
                <select v-model="editForm.subcategoryId" id="subcategoryId">
                  <option
                    v-for="subcategory in subcategories"
                    :key="subcategory.subcategoryId"
                    :value="subcategory.subcategoryId"
                  >
                    {{ subcategory.subcategoryName }}
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label for="price">價格</label>
                <input v-model="editForm.price" type="number" id="price" required />
              </div>
            </div>
            <!-- 第 4 行：庫存 / 出版日期 -->
            <div class="modal-row">
              <div class="form-group">
                <label for="stock">庫存</label>
                <input v-model="editForm.stock" type="number" id="stock" required />
              </div>
              <div class="form-group">
                <label for="publishDate">出版日期</label>
                <input
                  v-model="editForm.publishDate"
                  type="date"
                  id="publishDate"
                  required
                />
              </div>
            </div>
            <!-- 第 5 行：封面圖片 / (可再放其他欄位) -->
            <div class="modal-row">
              <div class="form-group">
                <label for="imageUrl">封面圖片網址</label>
                <input v-model="editForm.imageUrl" type="text" id="imageUrl" />
              </div>
              <!-- 可以保留空白, 或放其他欄位 -->
              <div class="form-group"><!-- 留空或加其他欄位 --></div>
            </div>
            <!-- 描述 (單獨一整行) -->
            <div class="form-group">
              <label for="description">描述</label>
              <textarea
                v-model="editForm.description"
                id="description"
                rows="8"
              ></textarea>
            </div>
            <div class="form-actions">
              <button type="submit" class="btn-save">保存</button>
              <button type="button" class="btn-cancel" @click="closeEditModal">
                取消
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

const API_URL = import.meta.env.VITE_API_URL; // ✅ 從 .env 讀取 API URL

export default {
  name: "BookList",
  data() {
    return {
      // 書籍資料
      books: [],
      currentPage: 1, // 當前頁數
      perPage: 7, // 每頁顯示 7 筆
      // 下拉選單資料
      authors: [],
      publishers: [],
      languages: [],
      subcategories: [],

      // 控制 Modal 彈窗顯示
      isEditModalVisible: false,

      // 用於編輯/新增的表單資料
      editForm: {
        bookId: null,
        bookName: "",
        authorId: null,
        publisherId: null,
        languageId: null,
        subcategoryId: null,
        price: 0,
        stock: 0,
        description: "",
        publishDate: "",
        imageUrl: "",
      },
    };
  },
  computed: {
    // 計算總頁數
    totalPages() {
      return Math.max(1, Math.ceil(this.books.length / this.perPage));
    },
    // 計算目前頁面的書籍
    paginatedBooks() {
      const start = (this.currentPage - 1) * this.perPage;
      const end = start + this.perPage;
      return this.books.slice(start, end);
    },
  },
  created() {
    // 進入頁面時，載入書籍與下拉選單資料
    this.fetchBooks();
    this.fetchDropdownData();
  },
  methods: {
    // 取得書籍列表
    async fetchBooks() {
      try {
        const response = await axios.get(`${API_URL}/api/books`);
        this.books = response.data;
      } catch (error) {
        console.error("獲取書籍失敗", error);
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    // 取得作者、出版社、語言、子分類等下拉選單資料
    async fetchDropdownData() {
      try {
        const [authors, publishers, languages, subcategories] = await Promise.all([
          axios.get(`${API_URL}/api/authors`),
          axios.get(`${API_URL}/api/publishers`),
          axios.get(`${API_URL}/api/languages`),
          axios.get(`${API_URL}/api/subcategories`),
        ]);
        this.authors = authors.data;
        this.publishers = publishers.data;
        this.languages = languages.data;
        this.subcategories = subcategories.data;
      } catch (error) {
        console.error("獲取下拉選單資料失敗", error);
      }
    },

    // 顯示 "新增書籍" 的彈窗
    showAddBookModal() {
      this.isEditModalVisible = true;
      // 重置表單資料
      this.editForm = {
        bookId: null,
        bookName: "",
        authorId: null,
        publisherId: null,
        languageId: null,
        subcategoryId: null,
        price: 0,
        stock: 0,
        description: "",
        publishDate: "",
        imageUrl: "",
      };
    },

    // 顯示 "編輯書籍" 的彈窗
    editBook(book) {
      this.isEditModalVisible = true;
      // 複製點擊到的書籍資料
      this.editForm = { ...book };
    },

    // 新增 / 更新書籍
    async saveBook() {
      try {
        if (this.editForm.bookId) {
          // 更新書籍
          await axios.put(`${API_URL}/api/books/${this.editForm.bookId}`, this.editForm);
          Swal.fire({
            title: "更新成功",
            icon: "success",
          });
        } else {
          // 新增書籍
          await axios.post(`${API_URL}/api/books`, this.editForm);
          Swal.fire({
            title: "新增成功",
            icon: "success",
          });
        }
        // 關閉 Modal 並重新載入書籍
        this.isEditModalVisible = false;
        this.fetchBooks();
      } catch (error) {
        console.error("儲存書籍失敗", error);
        Swal.fire({
          title: "儲存失敗",
          text: "請檢查輸入資料或稍後再試！",
          icon: "error",
        });
      }
    },

    // 關閉彈窗
    closeEditModal() {
      this.isEditModalVisible = false;
    },

    // 刪除書籍
    async deleteBook(bookId) {
      Swal.fire({
        title: "確定刪除該書籍？",
        text: "刪除後將無法復原",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "是",
        cancelButtonText: "否",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await axios.delete(`${API_URL}/api/books/${bookId}`);
            Swal.fire({
              title: "刪除成功",
              icon: "success",
            });
            this.fetchBooks();
          } catch (error) {
            console.error("刪除書籍失敗", error);
            Swal.fire({
              title: "刪除失敗",
              text: "無法刪除該書籍資料！",
              icon: "error",
            });
          }
        }
      });
    },
  },
};
</script>

<style scoped>
.bookList-container {
  font-family: Arial, sans-serif;
  margin: 0 auto;
  padding: 20px;
  max-width: 1200px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
/* 書籍列表表格樣式 */
.book-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}
.book-table th,
.book-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
.book-table th {
  background-color: #f4f4f4;
  font-weight: bold;
}
.book-table tr:nth-child(even) {
  background-color: #f9f9f9;
}
.book-table tr:hover {
  background-color: #f1f1f1;
}

/* 按鈕樣式 */
.btn-add {
  background-color: #007bff;
  color: white;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 10px;
}
.btn-edit {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.btn-delete {
  background-color: #dc3545;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* Modal 樣式 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}
.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px; /* 原本 width: 400px; 太小，改大一點 */
  width: 800px;
  max-height: 90vh; /* 避免視窗過小時彈窗超過螢幕，可配合 overflow: auto */
  overflow-y: auto;
} /* 將每行欄位並排 */
.modal-row {
  display: flex;
  gap: 20px; /* 欄位間距 */
  margin-bottom: 15px;
} /* 可讓每個欄位 flex:1; 使兩個欄位平分寬度 */
.modal-row .form-group {
  flex: 1;
}
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  display: block;
  margin-bottom: 5px;
}
.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
.form-actions {
  display: flex;
  justify-content: space-between;
}
.btn-save {
  background-color: #28a745;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.btn-cancel {
  background-color: #6c757d;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* 分頁樣式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
}
.pagination button {
  background-color: #007bff;
  color: white;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 0 5px;
}
.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
.pagination span {
  font-size: 16px;
  font-weight: bold;
}
</style>
