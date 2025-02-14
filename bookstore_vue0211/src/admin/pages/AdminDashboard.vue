<template>
    <div class="admin-dashboard">
      <h1>📌 管理後台</h1>
      <p>歡迎來到管理系統，請選擇要管理的內容。</p>
  
      <div class="admin-links">
        <router-link to="books">📚 書籍管理</router-link>
      </div>
      <router-view></router-view> <!-- 🚨 這行很重要 -->
    </div>
  </template>
  
  <script>
  export default {
    name: "AdminDashboard",
  };
  </script>
  
  <style scoped>
  .admin-dashboard {
    text-align: center;
    padding: 40px;
  }
  .admin-links {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    gap: 15px;
  }
  .admin-links a {
    text-decoration: none;
    font-size: 18px;
    background: #6200ea;
    color: white;
    padding: 10px 15px;
    border-radius: 5px;
    transition: 0.3s;
  }
  .admin-links a:hover {
    background: #3700b3;
  }
  </style>
  