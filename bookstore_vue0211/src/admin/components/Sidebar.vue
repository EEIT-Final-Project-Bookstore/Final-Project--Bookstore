<template>
    <aside class="sidebar">
      <nav>
        <router-link to="/">📊 儀表板</router-link>
        <router-link to="books">📚 書籍管理</router-link>
      </nav>
    </aside>
  </template>
  
  <style scoped>
  .sidebar {
    width: 200px;
    background-color: #f4f4f4;
    padding: 20px;
  }
  nav a {
    display: block;
    padding: 10px;
    text-decoration: none;
    color: #333;
  }
  nav a:hover {
    background-color: #ddd;
  }
  </style>
  