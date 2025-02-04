<template>
  <div class="app">
    <header class="site-header">
      <nav class="nav-bar">
        <ul>
          <li><router-link to="/">首頁</router-link></li>
          <li><router-link to="/login">會員登入</router-link></li>
          <li><router-link to="/register">加入會員</router-link></li>
          <li><router-link to="/customers">查詢會員</router-link></li>
          <li><router-link to="/modify">修改會員資料</router-link></li>
          <li><router-link to="/login/admin">管理員登入</router-link></li>
          <li><router-link to="/admins">查詢管理員</router-link></li>
          <li><router-link to="/navbar">登入狀態</router-link></li>
        </ul>
      </nav>
    </header>
    <transition name="fade">
      <router-view />
    </transition>
    <footer class="site-footer">
      <p>© 2025 三民網路書店. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
