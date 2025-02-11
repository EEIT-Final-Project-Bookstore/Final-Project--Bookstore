<template>
    <div id="admin-app">
      <AdminHeader />  <!-- 頂部導航列 -->
      <div class="admin-layout">
        <Sidebar />   <!-- 左側選單 -->
        <main>
          <router-view />  <!-- 主要內容 -->
        </main>
      </div>
    </div>
  </template>
  
  <script>
  import AdminHeader from "./components/AdminHeader.vue";
  import Sidebar from "./components/Sidebar.vue";
  
  export default {
    name: "AdminApp",
    components: {
      AdminHeader,
      Sidebar,
    },
  };
  </script>
  
  <style scoped>
  .admin-layout {
    display: flex;
  }
  main {
    flex-grow: 1;
    padding: 20px;
  }
  </style>
  