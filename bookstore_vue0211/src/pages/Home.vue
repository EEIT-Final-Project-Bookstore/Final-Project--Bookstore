<template>
    <div class="home">
      <h1>歡迎來到網路書店</h1>
      <p>這是首頁，你可以開始瀏覽書籍。</p>
      <router-link to="/selected-categories">探索書籍分類</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: "Home",
  };
  </script>
  
  <style scoped>
  .home {
    text-align: center;
    padding: 50px;
  }
  </style>
  