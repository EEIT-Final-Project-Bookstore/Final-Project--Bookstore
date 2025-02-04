<template>
    <div>
        <h1>{{ book.bookName }}</h1>
    <img :src="book.imageUrl" :alt="book.imageDescription" width="200" />
    <p><strong>Description:</strong> {{ book.description }}</p>
    <p><strong>Price:</strong> {{ book.price }}</p>
    <p><strong>Stock:</strong> {{ book.stock }}</p>
    <p><strong>Publisher:</strong> {{ book.publisher }}</p>
    <p><strong>Publish Date:</strong> {{ book.publishDate }}</p>
    <p><strong>Category:</strong> {{ book.category }}</p>
    <p><strong>Subcategory:</strong> {{ book.subcategory }}</p>
    <p><strong>Added to Shelf:</strong> {{ book.shelfTime }}</p>
    <p><strong>Last Updated:</strong> {{ book.lastUpdated }}</p>
    <router-link to="/">Back to Search</router-link>
      
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    props: ["id"], // 從路由參數中接收 id
    data() {
      return {
        book: {}, // 書籍詳細數據
      };
    },
    created() {
      this.fetchBookDetails();
    },
    methods: {
      fetchBookDetails() {
        axios
          .get(`/api/book/${this.id}`)
          .then((response) => {
            this.book = response.data; // 設置書籍數據
          })
          .catch(() => {
            alert("Failed to fetch book details.");
          });
      },
    },
  };
  </script>
  