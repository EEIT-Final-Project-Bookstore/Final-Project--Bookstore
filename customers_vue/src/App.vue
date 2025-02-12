<template>
  <div class="app">
    <!-- 頂部導覽列 -->
    <header class="site-header">
      <!-- 左側LOGO / 網站名稱 -->
      <div class="header-left">
        <h1 class="site-title">
          森途書店 <span>Sentu</span>
        </h1>
      </div>
      <!-- 中間搜尋框 -->
      <div class="header-center">
        <input type="text" placeholder="搜尋" class="search-bar"/>
        <img src="./image/search.png" height="30px">
      </div>
      <!-- 導覽選單 -->
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
              <img src="./image/home.png" height="20px" />首頁
            </router-link>
          </li>
          <!-- 通知 -->
          <li>
            <a href="#" @click.prevent="openModal">
              <img src="./image/volume.png" height="20px">通知
            </a>
          </li>
          <!-- 登入會員 / 修改會員 （依登入狀態切換）-->
          <li v-if="!authStore.isAuthenticated">
            <router-link to="/login">
              <img src="./image/user.png" height="20px" />登入會員
            </router-link>
          </li>
          <li v-else>
            <router-link to="/modify">
              <img src="./image/user.png" height="20px" />修改會員
            </router-link>
          </li>
          <!-- 購物車 -->
          <li>
            <router-link to="/cart">
              <img src="./image/shopping-cart.png" height="20px" />購物車
            </router-link>
          </li>
        </ul>
      </nav>
    </header>

    <!-- 主要內容 (路由切換) -->
    <transition name="fade">
      <router-view />
    </transition>

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
    <footer class="site-footer"></footer>
  </div>
</template>

<script>
import Swal from "sweetalert2"; // ✅ 引入 SweetAlert2
import { useAuthStore } from "@/stores/authStore"; // 引入 authStore

export default {
  name: "App",
  data() {
    return {
      isModalOpen: false,
      notifications: [],
    };
  },
  computed: {
    // 將 authStore 放入 computed，方便在模板使用
    authStore() {
      return useAuthStore();
    },
  },
  methods: {
    // ======== 登出方法 ========
    logout() {
      this.authStore.logout();
    },

    // ======== 打開模態框並獲取通知 ========
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
    // ======== 關閉模態框 ========
    closeModal() {
      this.isModalOpen = false;
    },
    // ======== 獲取通知 ========
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

      const apiUrl = `http://192.168.23.112:8080/admin/notifications/customer/${customerID}`;
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
    // ======== 標記所有通知為已讀 ========
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
          `http://192.168.23.112:8080/admin/notifications/mark-all-as-read/${customerID}`,
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
@import url("https://fonts.googleapis.com/css2?family=LXGW+WenKai+TC&family=Sour+Gummy:ital,wght@0,100..900;1,100..900&display=swap");

/* 讓頁面佔滿瀏覽器高度，確保 footer 能貼底 */
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
  width: 350px;
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

/* 通知框相關 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明背景 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: #fff;
  width: 400px;
  max-width: 80%;
  padding: 20px;
  border-radius: 8px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-body ul {
  padding: 0;
  list-style: none;
}

.unread {
  font-weight: bold;
  color: red;
}
</style>
