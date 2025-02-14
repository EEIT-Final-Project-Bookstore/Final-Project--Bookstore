<template>
    <div class="book-info-card">
      <h1>{{ title }}</h1>
      <p><strong>分類：</strong>{{ category }} > {{ subcategory }}</p>
      <p><strong>作者：</strong>{{ author }}</p>
      <p><strong>出版社：</strong>{{ publisher }}</p>
      <p><strong>出版日期：</strong>{{ publishDate }}</p>
   
    </div>
  </template>
  
  <script>
  export default {
    props: {
      title: String,
      isbn: String,
      author: String,
      publisher: String,
      publishDate: String,
      pages: Number,
    },
  };
  </script>
  
  <style scoped>
  .book-info-card {
    font-size: 14px;
    line-height: 1.5;
  }
  </style>
  