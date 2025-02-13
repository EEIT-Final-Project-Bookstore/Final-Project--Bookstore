<template>
    <div class="book-management">
      <h1>書籍管理</h1>
      <button @click="showAddBookForm">新增書籍</button>
      
      <!-- 書籍列表 -->
      <table>
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
          <tr v-for="book in books" :key="book.bookId">
            <td>{{ book.bookId }}</td>
            <td>{{ book.bookName }}</td>
            <td>{{ book.price }}</td>
            <td>{{ book.stock }}</td>
            <td>
              <button @click="editBook(book)">編輯</button>
              <button @click="deleteBook(book.bookId)">刪除</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <!-- 新增 / 編輯 書籍的表單 -->
      <div v-if="isEditing" class="edit-form">
        <h2>{{ editingBook.bookId ? "編輯書籍" : "新增書籍" }}</h2>
        <form @submit.prevent="saveBook">
          <label>書名:</label>
          <input v-model="editingBook.bookName" required />
          
          <label>作者:</label>
          <select v-model="editingBook.authorId">
            <option v-for="author in authors" :key="author.authorId" :value="author.authorId">
              {{ author.authorName }}
            </option>
          </select>
  
          <label>出版社:</label>
          <select v-model="editingBook.publisherId">
            <option v-for="publisher in publishers" :key="publisher.publisherId" :value="publisher.publisherId">
              {{ publisher.publisherName }}
            </option>
          </select>
  
          <label>語言:</label>
          <select v-model="editingBook.languageId">
            <option v-for="language in languages" :key="language.languageId" :value="language.languageId">
              {{ language.language }}
            </option>
          </select>
  
          <label>子分類:</label>
          <select v-model="editingBook.subcategoryId">
            <option v-for="subcategory in subcategories" :key="subcategory.subcategoryId" :value="subcategory.subcategoryId">
              {{ subcategory.subcategoryName }}
            </option>
          </select>
  
          <label>價格:</label>
          <input type="number" v-model="editingBook.price" required />
  
          <label>庫存:</label>
          <input type="number" v-model="editingBook.stock" required />
  
          <label>描述:</label>
          <textarea v-model="editingBook.description"></textarea>
  
          <label>出版日期:</label>
          <input type="date" v-model="editingBook.publishDate" required />
  
          <label>封面圖片網址:</label>
          <input v-model="editingBook.imageUrl" />
  
          <button type="submit">儲存</button>
          <button type="button" @click="cancelEdit">取消</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        books: [],
        authors: [],
        publishers: [],
        languages: [],
        subcategories: [],
        isEditing: false,
        editingBook: {
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
    methods: {
      async fetchBooks() {
        try {
          const response = await axios.get("http://localhost:8080/api/books");
          this.books = response.data;
        } catch (error) {
          console.error("獲取書籍失敗", error);
        }
      },
      async fetchDropdownData() {
        try {
          const [authors, publishers, languages, subcategories] = await Promise.all([
            axios.get("http://localhost:8080/api/authors"),
            axios.get("http://localhost:8080/api/publishers"),
            axios.get("http://localhost:8080/api/languages"),
            axios.get("http://localhost:8080/api/subcategories"),
          ]);

          console.log("Languages:", languages.data); // 🛠️ 確保 API 有返回數據

          this.authors = authors.data;
          this.publishers = publishers.data;
          this.languages = languages.data;
          this.subcategories = subcategories.data;
        } catch (error) {
          console.error("獲取下拉選單資料失敗", error);
        }
      },
      showAddBookForm() {
        this.isEditing = true;
        this.editingBook = {
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
      editBook(book) {
        this.isEditing = true;
        this.editingBook = { ...book };
      },
      async saveBook() {
        try {
          if (this.editingBook.bookId) {
            await axios.put(`http://localhost:8080/api/books/${this.editingBook.bookId}`, this.editingBook);
          } else {
            await axios.post("http://localhost:8080/api/books", this.editingBook);
          }
          this.isEditing = false;
          this.fetchBooks();
        } catch (error) {
          console.error("儲存書籍失敗", error);
        }
      },
      cancelEdit() {
        this.isEditing = false;
      },
      async deleteBook(bookId) {
        if (confirm("確定要刪除這本書嗎？")) {
          try {
            await axios.delete(`http://localhost:8080/api/books/${bookId}`);
            this.fetchBooks();
          } catch (error) {
            console.error("刪除書籍失敗", error);
          }
        }
      },
    },
    mounted() {
      this.fetchBooks();
      this.fetchDropdownData();
    },
  };
  </script>
  