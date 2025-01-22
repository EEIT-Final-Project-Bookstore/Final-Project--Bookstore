<template>
    <div class="book-management">
      <h1>書籍管理</h1>
      <!-- 搜尋區 -->
      <div class="search-bar">
        <input v-model="searchQuery" placeholder="搜尋書籍..." />
        <button @click="searchBooks">搜尋</button>
      </div>
  
      <!-- 書籍列表 -->
      <table class="book-table">
        <thead>
          <tr>
            <th>書籍名稱</th>
            <th>ISBN</th>
            <th>作者</th>
            <th>出版社</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="book in filteredBooks" :key="book.id">
            <td>{{ book.title }}</td>
            <td>{{ book.isbn }}</td>
            <td>{{ book.author }}</td>
            <td>{{ book.publisher }}</td>
            <td>
              <button @click="editBook(book.id)">編輯</button>
              <button @click="deleteBook(book.id)">刪除</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <!-- 新增書籍 -->
      <button @click="openAddBookModal">新增書籍</button>
  
      <!-- 書籍表單彈窗 -->
      <div v-if="showModal" class="modal">
        <h2>{{ isEditing ? '編輯書籍' : '新增書籍' }}</h2>
        <form @submit.prevent="handleSubmit">
          <label>書籍名稱：</label>
          <input v-model="form.title" required />
          <label>ISBN：</label>
          <input v-model="form.isbn" required />
          <label>作者：</label>
          <input v-model="form.author" required />
          <label>出版社：</label>
          <input v-model="form.publisher" required />
          <button type="submit">{{ isEditing ? '更新' : '新增' }}</button>
          <button type="button" @click="closeModal">取消</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        books: [], // 書籍資料
        searchQuery: "", // 搜尋關鍵字
        showModal: false, // 控制表單彈窗
        isEditing: false, // 是否為編輯模式
        form: {
          id: null,
          title: "",
          isbn: "",
          author: "",
          publisher: "",
        },
      };
    },
    computed: {
      filteredBooks() {
        // 根據搜尋關鍵字過濾書籍
        return this.books.filter(
          (book) =>
            book.title.includes(this.searchQuery) ||
            book.isbn.includes(this.searchQuery)
        );
      },
    },
    methods: {
      searchBooks() {
        // 搜尋功能，這裡可以整合後端 API
        console.log("搜尋書籍：", this.searchQuery);
      },
      openAddBookModal() {
        this.showModal = true;
        this.isEditing = false;
        this.resetForm();
      },
      editBook(id) {
        const book = this.books.find((b) => b.id === id);
        if (book) {
          this.form = { ...book };
          this.isEditing = true;
          this.showModal = true;
        }
      },
      deleteBook(id) {
        // 刪除書籍邏輯
        this.books = this.books.filter((book) => book.id !== id);
        console.log("刪除書籍 ID：", id);
      },
      closeModal() {
        this.showModal = false;
        this.resetForm();
      },
      handleSubmit() {
        if (this.isEditing) {
          // 編輯書籍
          const index = this.books.findIndex((b) => b.id === this.form.id);
          if (index !== -1) {
            this.books.splice(index, 1, { ...this.form });
            console.log("更新書籍：", this.form);
          }
        } else {
          // 新增書籍
          this.form.id = Date.now(); // 簡單生成 ID
          this.books.push({ ...this.form });
          console.log("新增書籍：", this.form);
        }
        this.closeModal();
      },
      resetForm() {
        // 重置表單
        this.form = {
          id: null,
          title: "",
          isbn: "",
          author: "",
          publisher: "",
        };
      },
    },
  };
  </script>
  
  <style scoped>
  .book-management {
    padding: 20px;
  }
  
  .search-bar {
    margin-bottom: 20px;
  }
  
  .book-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  .book-table th,
  .book-table td {
    border: 1px solid #ddd;
    padding: 10px;
  }
  
  .modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: white;
    padding: 20px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }
  </style>
  