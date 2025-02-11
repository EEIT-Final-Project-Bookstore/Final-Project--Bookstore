<template>
  <div class="app">
    <!-- 頂部導覽列 -->
    <header class="site-header">
      <div class="header-left">
        <h1 class="site-title">
          森途書店 <span>Sentu</span>
        </h1>
      </div>
      <div class="header-center">
        <input type="text" placeholder="搜尋" class="search-bar" />
      </div>
      <nav class="nav-bar">
        <ul>
          <li><router-link to="/">首頁</router-link></li>
          <li><a href="#" @click.prevent="openModal">通知</a></li>
          <li><router-link to="/login">會員登入</router-link></li>
          <li><router-link to="/coupon/list">我的優惠券</router-link></li>
          <li><router-link to="/cart">🛒購物車</router-link></li>
          <li><router-link to="/order/list">我的訂單</router-link></li>
          <li><router-link to="/coupon/management">後台管理</router-link></li>
        </ul>
      </nav>
    </header>
    <!-- 主要內容 (路由切換) -->
    <!-- <transition name="fade"> -->
      <router-view />
    <!-- </transition> -->
    <!-- 通知模態框 -->
    <div v-if="isModalOpen" class="modal-overlay">
      <div class="modal">
        <div class="modal-header">
          <h3>系統通知</h3>
          <button @click="markAllAsRead" class="mark-all-read-button">全部已讀</button>
        </div>
        <div class="modal-body">
          <div v-if="notifications.length">
            <ul>
              <li
                v-for="notification in notifications"
                :key="notification.notificationID"
                :class="{ unread: notification.status.statusId !== 11 }"
              >
                {{ notification.content }}
              </li>
            </ul>
          </div>
          <p v-else>目前沒有通知。</p>
        </div>
        <div class="modal-footer">
          <button @click="closeModal" class="close-button">關閉</button>
        </div>
      </div>
    </div>

    <!-- 頁尾 -->
    <footer class="site-footer">
      <div class="footer-col">
        <h3>森途書店</h3>
        <p>聯絡方式</p>
        <p>eeit195.bookstore@gmail.com</p>
      </div>
      <div class="footer-col">
        <h3>創立理念</h3>
        <p>
          知識像森林🌳。森途書店（Sentu）是一家以線上購書為核心，並提供書籍評論的網路書店。我們致力於打造一個舒適且便利的購物體驗，讓用戶能夠快速瀏覽、搜尋、下單，並透過書評分享與交流進一步探索各種書籍的魅力。
        </p>
      </div>
      <div class="footer-bottom">
        <p>© 2025 All Rights Reserved By Free Html Templates</p>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      isModalOpen: false,
      notifications: [],
    };
  },
  methods: {
    // 打開模態框並獲取通知
    openModal() {
      this.isModalOpen = true;
      this.fetchNotifications();
    },
    // 關閉模態框
    closeModal() {
      this.isModalOpen = false;
    },
    // 獲取通知
    async fetchNotifications() {
      const customerID = sessionStorage.getItem("customerID");
      if (!customerID) {
        alert("未檢測到用戶登錄，請先登錄！");
        return;
      }
      const apiUrl = `http://localhost:8080/admin/notifications/customer/${customerID}`;
      try {
        const response = await fetch(apiUrl, { method: "GET" });
        if (!response.ok) {
          throw new Error(`HTTP 錯誤！狀態碼: ${response.status}`);
        }
        const data = await response.json();
        this.notifications = data.map((notification) => ({
          notificationID: notification.notificationID,
          content: notification.content,
          status: notification.status || { statusId: null },
        }));
      } catch (error) {
        console.error("獲取通知時發生錯誤：", error);
        alert("伺服器錯誤，請稍後再試！");
      }
    },
    // 標記所有通知為已讀
    async markAllAsRead() {
      const customerID = sessionStorage.getItem("customerID");
      if (!customerID) {
        alert("未檢測到用戶登錄，請先登錄！");
        return;
      }
      try {
        const response = await fetch(
          `http://localhost:8080/admin/notifications/mark-all-as-read/${customerID}`,
          {
            method: "PUT",
          }
        );
        if (response.ok) {
          alert("所有通知已標記為已讀！");
          this.notifications = this.notifications.map((notification) => ({
            ...notification,
            status: { ...notification.status, statusId: 11 },
          }));
        } else {
          alert("標記失敗，請稍後再試！");
        }
      } catch (error) {
        console.error("標記通知為已讀時發生錯誤：", error);
        alert("伺服器錯誤，請稍後再試！");
      }
    },
  },
};
</script>

<style scoped>
/* @import url('https://fonts.googleapis.com/css2?family=LXGW+WenKai+TC&family=Sour+Gummy:ital,wght@0,100..900;1,100..900&display=swap'); */
/* 整體容器 */
.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #fff;
  color: #0D0D0D;
  font-family: "Sour Gummy", serif;
}
/* Header 區域 */
.site-header {
  background-color: #B2BFAC;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 40px; /* 根據需求調整 */
}
.header-left .site-title {
  margin: 0;
  font-size: 2.5rem;
  color: #404040;
}
.header-center .search-bar {
  border: 1px solid #88888C;
  padding: 5px;
  width: 350px;
  border-radius: 5px;
}
/* 導覽列 */
.nav-bar ul {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}
.nav-bar li {
  margin-left: 20px;
}
.nav-bar a {
  text-decoration: none;
  color: #0D0D0D;
  font-size: 1.15rem;
}
/* 通知模態框 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal {
  background: #fff;
  border-radius: 15px;
  width: 450px;
  padding: 20px;
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3);
  animation: fadeIn 0.3s ease-in-out;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}
.modal-header h3 {
  font-size: 1.5rem;
  color: #4A4A4A;
}
.mark-all-read-button {
  background: #8A2BE2;
  color: #fff;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  font-size: 0.9rem;
  cursor: pointer;
}
.mark-all-read-button:hover {
  background: #5a1e99;
}
.modal-body {
  margin-top: 15px;
  max-height: 300px;
  overflow-y: auto;
  font-size: 1rem;
  line-height: 1.5;
}
.modal-body ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
.modal-body li {
  padding: 10px;
  margin-bottom: 10px;
  background: #F9FAFB;
  border-radius: 8px;
  border-left: 4px solid #8A2BE2;
}
.modal-body li.unread {
  font-weight: bold;
  background: #F4F4FF;
}
.modal-footer {
  text-align: right;
  margin-top: 15px;
}
.close-button {
  background: #FF5A5F;
  color: #fff;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  font-size: 0.9rem;
  cursor: pointer;
}
.close-button:hover {
  background: #cc4a4a;
}
/* Footer 區域 */
.site-footer {
  background-color: #B2BFAC;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin: 0;
  padding: 0;
  margin-top: auto;
}
.footer-col {
  flex: 1;
  min-width: 200px;
  margin: 10px;
}
.footer-bottom {
  text-align: center;
  width: 100%;
  margin-top: 20px;
  color: #404040;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>



<!--
<template>
   <div class="app">
    <div>
      <NavigationBar></NavigationBar>
    </div>
  <RouterView></RouterView>
    <footer class="site-footer">
      <p>© 2025 森途書店. All rights reserved.</p>
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
/* .site-footer {
  height: 300px;
} */
.site-footer p{
  padding-top: 25px;
}
</style>
-->