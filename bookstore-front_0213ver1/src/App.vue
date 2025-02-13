<template>
  <div class="app">
    <!-- 頂部導覽列 -->
    <header class="site-header">
		<div class="header-left">
			<h1 class="site-title">
			森途書店 <span>Sentu</span>
			</h1>
		</div>
      <!-- 中間搜尋框 -->
		<div class="header-center">
			<input type="text" placeholder="搜尋" class="search-bar"/>
			<el-icon><Search /></el-icon>
			<!-- <img src="./image/search.png" height="30px"> -->
		</div>
		<nav class="nav-bar">
			<ul>
			<!-- 首頁 + [已登入時顯示 "歡迎, XXX" 與登出按鈕] -->
				<li>
					<!-- 僅在已登入時顯示 -->
					<span v-if="authStore.isAuthenticated" style="margin-left: 10px;">
					歡迎, {{ authStore.customerName }}
					<button @click="logout">登出</button>
					</span>
					<p style="padding-right: 20px;"></p>
					<router-link to="/">
						<el-icon><HomeFilled /></el-icon>
					首頁
					</router-link>
				</li>
				<!-- 通知 -->
				<li>
					<a href="#" @click.prevent="openModal">
						<el-icon><BellFilled /></el-icon>通知
					</a>
				</li>
				<!-- 登入會員 / 修改會員 （依登入狀態切換）-->
				<li v-if="!authStore.isAuthenticated">
					<router-link to="/login">
						<el-icon><User /></el-icon>
					登入會員
					</router-link>
				</li>
				<li v-else>
					<router-link to="/modify">
						<el-icon><EditPen /></el-icon>
					修改會員
					</router-link>
				</li>
				<li>
					<router-link to="/cart">
						<el-badge v-if="authStore.customerId && cartStore.cartItemsCount > 0" :value="cartStore.cartItemsCount" effect="dark" class="item">
							<el-icon><ShoppingCart /></el-icon>購物車
						</el-badge>
						<span v-else>
							<el-icon><ShoppingCart /></el-icon>購物車
						</span>
					</router-link>
				</li>
				<li>
					<router-link to="/coupon/list">
						<el-icon><Ticket /></el-icon>我的優惠券
					</router-link>
				</li>
				<li>
					<router-link to="/order/list">
						<el-icon><List /></el-icon>我的訂單
					</router-link>
				</li>
				<li>
					<router-link to="/backstage/management">
						<el-icon><Platform /></el-icon>
						後台管理
					</router-link>
				</li>
			</ul>
		</nav>
		<!-- <nav class="nav-bar">
			<ul>
				<li><router-link to="/">首頁</router-link></li>
				<li><a href="#" @click.prevent="openModal">通知</a></li>
				<li><router-link to="/login">會員登入</router-link></li>
				<li><router-link to="/coupon/list">我的優惠券</router-link></li>
				<li><router-link to="/cart">
					<el-badge v-if="authStore.customerId && cartStore.cartItemsCount > 0" :value="cartStore.cartItemsCount" effect="dark" class="item">
					<el-icon><ShoppingCart /></el-icon>購物車
					</el-badge>
					<span v-else>
						<el-icon><ShoppingCart /></el-icon>購物車
					</span>
				</router-link></li>
				<li><router-link to="/order/list">我的訂單</router-link></li>
				<li><router-link to="/backstage/management">後台管理</router-link></li>
			</ul>
		</nav> -->
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
import Swal from "sweetalert2";
import { ElBadge } from 'element-plus';
import { HomeFilled, EditPen, User, Ticket, BellFilled, ShoppingCart, List, Search, Platform } from '@element-plus/icons-vue'
import { useCartStore } from "@/stores/cartStore";
import { useAuthStore } from "./stores/authStore";

export default {
  name: "App",
  data() {
    return {
      isModalOpen: false,
      notifications: [],
    };
  },
  setup() {
    const cartStore = useCartStore();
    const authStore = useAuthStore();
    return { cartStore, authStore }; // 將 cartStore, authStore 返回給網頁使用
  },
  computed: {
    // 將 authStore 放入 computed，方便在模板使用
    authStore() {
      return useAuthStore();
    },
  },
  methods: {
	logout() {
      this.authStore.logout();
    },
    // 打開模態框並獲取通知
    openModal() {
		const customerID = sessionStorage.getItem("customerID");
      if (!customerID) {
        // 顯示 SweetAlert2 提示
        Swal.fire({
          icon: "warning",
          title: "未檢測到用戶登錄",
          text: "請先登入以查看通知！",
          confirmButtonText: "確定",
          confirmButtonColor: "#3085d6",
        });
        return; // 阻止模態框顯示
      }
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
        Swal.fire({
          icon: "warning",
          title: "未檢測到用戶登錄",
          text: "請先登入以查看通知！",
          confirmButtonText: "確定",
          confirmButtonColor: "#3085d6",
        });
        return;
      }
      const apiUrl = `http://192.168.23.87:8080/admin/notifications/customer/${customerID}`;
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
        Swal.fire({
          icon: "error",
          title: "伺服器錯誤",
          text: "請稍後再試！",
          confirmButtonText: "確定",
        });
      }
    },
    // 標記所有通知為已讀
    async markAllAsRead() {
		const customerID = sessionStorage.getItem("customerID");
      if (!customerID) {
        Swal.fire({
          icon: "warning",
          title: "未檢測到用戶登錄",
          text: "請先登入！",
          confirmButtonText: "確定",
          confirmButtonColor: "#3085d6",
        });
        return;
      }
      try {
        const response = await fetch(
          `http://192.168.23.87:8080/admin/notifications/mark-all-as-read/${customerID}`,
          {
            method: "PUT",
          }
        );
        if (response.ok) {
          Swal.fire({
            icon: "success",
            title: "成功！",
            text: "所有通知已標記為已讀！",
            confirmButtonText: "確定",
          });
          this.notifications = this.notifications.map((notification) => ({
            ...notification,
            status: { ...notification.status, statusId: 11 },
          }));
        } else {
          Swal.fire({
            icon: "error",
            title: "標記失敗",
            text: "請稍後再試！",
            confirmButtonText: "確定",
          });
        }
      } catch (error) {
        console.error("標記通知為已讀時發生錯誤：", error);
        Swal.fire({
          icon: "error",
          title: "伺服器錯誤",
          text: "請稍後再試！",
          confirmButtonText: "確定",
        });
      }
    },
  },
  // 當元件掛載時，初始化 Auth 狀態
  mounted() {
    this.authStore.initializeAuth();
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=LXGW+WenKai+TC&family=Sour+Gummy:ital,wght@0,100..900;1,100..900&display=swap');
/* 整體容器 */
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
.app {
  display: flex;
  flex-direction: column;
  /* 撐滿整個螢幕高度，讓 footer 用 margin-top: auto 貼底 */
  min-height: 100vh;
  background-color: #ffffff; /* 主背景色 */
  color: #0d0d0d; /* 主要文字與標題色 */
  font-family: "Chocolate Classical Sans", serif;
}
/* Header區域 */
.site-header {
  background-color: #fff; /* 次要背景色 */
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
/* LOGO或網站名稱 */
.header-left .site-title {
  margin: 0;
  font-size: 2.5rem;
  color: #808000; /* 主要文字色 */
  font-family: "Sour Gummy", serif;
}
.header-center {
  display: flex;
  align-items: center; /* 讓搜尋框與圖標垂直置中對齊 */
}

.header-center img {
  height: 30px; /* 與搜尋框高度一致 */
  object-fit: contain;
  margin: 0; /* 確保圖標與搜尋框垂直對齊 */
}
/* 搜尋框 */
.header-center .search-bar {
  border: 1px solid #88888c; /* 按鈕與邊框色 */
  padding: 5px;
  width: 300px;
  height: 30px;
  background-color: #ffffff;
  color: #404040; /* 次要文字色 */
  margin-right: 10px;
  border-radius: 5px;
}
/* 導覽列 */
.nav-bar ul {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
  height: 40px; /* 設定與黑線對齊的高度 */
  align-items: center; /* 讓圖標和文字垂直置中 */
}
.nav-bar li {
  display: flex;
  align-items: center;
  margin-left: 20px;
}
.nav-bar img {
  margin-right: 5px; /* 圖標和文字之間的間距 */
  vertical-align: middle; /* 讓圖片與文字對齊 */
}
.nav-bar a {
  text-decoration: none;
  color: #0d0d0d;
  font-size: 1.15rem;
  display: flex;
  align-items: center; /* 確保內部文字和圖標一致垂直置中 */
  height: 100%;
}
/* 轉場淡入淡出 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
/* Footer區域 */
.site-footer {
  background-color: #d9d9d9; /* 次要背景色 */
  display: flex;
  /* 讓三個區塊在同一行排列 */
  justify-content: space-around;
  align-items: center;
  padding: 10px;
  margin-top: auto; /* 讓Footer貼底 */
  height: 80px;
}
.footer-col {
  text-align: center;
}
.footer-logo {
  width: 80px; /* 藍色框：Logo大小可自行調整 */
  height: auto;
  display: block;
  margin: 0 auto;
}
.footer-store-name {
  margin: 5px 0 0;
  font-size: 1.2rem;
}
.footer-col p {
  color: #404040; /* 次要文字色 */
  margin: 5px 0;
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