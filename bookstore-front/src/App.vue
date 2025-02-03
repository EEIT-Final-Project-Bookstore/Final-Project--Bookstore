<template>
   <div class="app">
    <div>
    <NavigationBar></NavigationBar>
  </div>
  <RouterView></RouterView>
    <!-- <header class="site-header">
      <nav class="nav-bar">
        <ul>
          <li><router-link to="/">首頁</router-link></li>
          <li><router-link to="/login">會員登入</router-link></li>
          <li><router-link to="/register">加入會員</router-link></li>
          <li><router-link to="/customers">查詢會員</router-link></li>
          <li><router-link to="/modify">修改會員資料</router-link></li>
          <li><router-link to="/cart">購物車</router-link></li>
        </ul>
      </nav>
    </header>
    <transition name="fade">
      <router-view />
    </transition> -->
    <footer class="site-footer">
      <p>© 2025 三民網路書店. All rights reserved.</p>
    </footer>
  </div>
</template>
  
<script setup>
import NavigationBar from './views/NavigationBar.vue';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import { RouterView } from 'vue-router';

  
</script>
  
<style>

</style>