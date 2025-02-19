<template>
  <div class="app">
    <!-- 管理員介面 -->
    <div
      v-if="authStore.isAuthenticated && authStore.adminAccount"
      class="admin-container"
    >
      <header class="site-header">
        <!-- 左右 LOGO + 資訊列 -->
        <div class="header-top">
          <div class="logo-box">
            <router-link to="/backstage/management" class="homepage-link">
              <h1 class="site-title">森途書店後臺管理系統 <span>Sentu</span></h1>
            </router-link>
          </div>
          <nav class="admin-nav">
            <ul>
              <li v-if="authStore.adminAccount" class="welcome-admin">
                歡迎回來，{{ authStore.adminAccount }}
              </li>
              <li v-if="authStore.isAuthenticated">
                <button class="admin-logout-btn" @click="logout">登出</button>
              </li>
            </ul>
          </nav>
        </div>
      </header>

      <main class="admin-main">
        <router-view />
      </main>
      <!-- Footer -->
      <footer class="site-footer"></footer>
    </div>

    <!-- 一般使用者介面 -->
    <div v-else class="user-container">
      <header class="site-header">
        <!-- 左右 LOGO + 資訊列 -->
        <div class="header-top">
          <div class="logo-box">
            <router-link to="/" class="homepage-link">
              <h1 class="site-title">森途書店 <span>Sentu</span></h1>
            </router-link>
          </div>
          <nav class="user-nav">
            <ul>
              <li v-if="authStore.isAuthenticated">
                <span class="user-admin-name">
                  歡迎回來,
                  <span v-if="authStore.adminAccount"
                    ><br />管理員{{ authStore.adminAccount }}</span
                  >
                  <span v-else-if="authStore.customerName">{{authStore.customerName}}</span>
                </span>
                <button @click="logout">登出</button>
              </li>
              <li>
                <router-link to="/">
                  <el-icon><HomeFilled /></el-icon> 首頁
                </router-link>
              </li>
              <li>
                <a href="#" @click.prevent="openModal">
                  <el-icon><BellFilled /></el-icon> 通知
                </a>
              </li>
              <li v-if="!authStore.isAuthenticated">
                <router-link to="/login">
                  <el-icon><User /></el-icon> 登入會員
                </router-link>
              </li>
              <li v-else>
                <router-link to="/modify">
                  <el-icon><EditPen /></el-icon> 修改會員
                </router-link>
              </li>
              <li>
                <router-link to="/cart">
                  <el-badge
                    v-if="authStore.customerId && cartStore.cartItemsCount > 0"
                    :value="cartStore.cartItemsCount"
                    effect="dark"
                    class="item"
                  >
                    <el-icon><ShoppingCart /></el-icon> 購物車
                  </el-badge>
                  <span v-else>
                    <el-icon><ShoppingCart /></el-icon> 購物車
                  </span>
                </router-link>
              </li>
              <li>
                <router-link to="/coupon/list">
                  <el-icon><Ticket /></el-icon> 我的優惠券
                </router-link>
              </li>
              <li>
                <router-link to="/order/list">
                  <el-icon><List /></el-icon> 我的訂單
                </router-link>
              </li>
              <li>
                <router-link to="/backstage/management">
                  <el-icon><Platform /></el-icon> 後台管理
                </router-link>
              </li>
            </ul>
          </nav>
        </div>
        <!-- 搜尋欄 -->
        <div class="header-bottom">
          <SearchPage />
        </div>
        <!-- 分類列 -->
        <div class="category-container">
          <nav class="category-nav">
            <ul>
              <li><router-link to="/selected-categories">中文</router-link></li>
              <li><router-link to="/lifestyle">外文</router-link></li>
              <li><router-link to="/simplified">簡體</router-link></li>
              <!-- <li><router-link to="/ebooks">電子書</router-link></li> -->
              <li><router-link to="/kids">親子</router-link></li>
              <li><router-link to="/stationery">文具選物</router-link></li>
              <li><router-link to="/comics">漫畫</router-link></li>
              <li><router-link to="/textbooks">教科考用</router-link></li>
            </ul>
          </nav>
        </div>
      </header>

      <!-- 用 main 撐開中間空間 -->
      <main class="user-main">
        <router-view />
      </main>

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
                  :class="{
                    unread: notification.status.statusId === 12,
                    read: notification.status.statusId === 11,
                  }"
                >
                  <strong>{{ notification.type || "未分類" }}：</strong>
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

      <footer class="site-footer">
        <div class="footer-container">
          <!-- Company Info -->
          <div class="footer-section">
            <h3>關於我們</h3>
            <p>
             我們致力於打造一個舒適且便利的購物體驗，讓用戶能夠快速瀏覽、搜尋、下單，並透過書評分享與交流進一步探索各種書籍的魅力。
            </p>
          </div>

          <div class="footer-section">
            <h3>快速連結</h3>
            <ul>
              <li><router-link to="/about">常見問題</router-link></li>
              <li><router-link to="/contact">異業合作</router-link></li>
              <li><router-link to="/privacy">隱私政策</router-link></li>
              <li><router-link to="/terms">服務條款</router-link></li>
            </ul>
          </div>

          <div class="footer-section">
            <h3>聯絡我們</h3>
            <p>電話: 02-1234-5678</p>
            <p>電子郵件: eeit195.bookstore@bookstore.com</p>
          </div>
        </div>

        <div class="footer-bottom">
          <p>&copy; 2025 森途網路書店. 版權所有。</p>
        </div>
      </footer>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import SearchPage from "./components/SearchPage.vue";
import axiosapi from "./plugins/axios";
import { useAuthStore } from "./stores/authStore";
import { useCartStore } from "@/stores/cartStore";
// 若有使用 Icon，需要引入
import {
  HomeFilled,
  BellFilled,
  User,
  EditPen,
  ShoppingCart,
  Ticket,
  List,
  Platform,
} from "@element-plus/icons-vue";

export default {
  name: "App",
  data() {
    return {
      isModalOpen: false,
      notifications: [],
    };
  },
  components: {
    SearchPage,
    HomeFilled,
    BellFilled,
    User,
    EditPen,
    ShoppingCart,
    Ticket,
    List,
    Platform,
  },
  setup() {
    const cartStore = useCartStore();
    const authStore = useAuthStore();
    return { cartStore, authStore };
  },
  computed: {
    authStore() {
      return useAuthStore();
    },
  },
  methods: {
    // 登出後轉跳首頁
    logout() {
      this.authStore.logout();
      this.$router.push("/");
    },
    // 打開通知模態框
    openModal() {
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
      this.isModalOpen = true;
      this.fetchNotifications();
    },
    closeModal() {
      this.isModalOpen = false;
    },
    // 從後端抓取通知
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
      try {
        const response = await axiosapi.get(
          `/admin/notifications/customer/${customerID}`
        );
        console.log("後端回傳的通知資料：", response.data); // ✅ 檢查 API 回應是否有 notificationType

        this.notifications = response.data.map((notification) => ({
          notificationID: notification.notificationID,
          type: notification.notificationType
            ? notification.notificationType.notificationType
            : "未分類", // ✅ 解析通知類型
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
        await axiosapi.put(`/admin/notifications/mark-all-as-read/${customerID}`);
        Swal.fire({
          icon: "success",
          title: "成功！",
          text: "所有通知已標記為已讀！",
          confirmButtonText: "確定",
        });
        this.notifications = this.notifications.map((n) => ({
          ...n,
          status: { ...n.status, statusId: 11 },
        }));
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
    // 進入畫面時初始化 Auth 狀態
    mounted() {
      this.authStore.initializeAuth();
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=LXGW+WenKai+TC&family=Sour+Gummy:ital,wght@0,100..900;1,100..900&display=swap");

/* =============== Sticky Footer 關鍵排版 =============== */
/* 1. 讓最外層(含管理員/使用者)撐滿一個視窗高度 */
.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 畫面至少佔滿整個視窗 */
  margin-top: 80px;
  padding: 0;
}

/* 2. 管理員容器 & 使用者容器：也用 flex 直向排，footer 才能被擠到下面 */
.admin-container,
.user-container {
  display: flex;
  flex-direction: column;
  flex: 1; /* 中間空間自動撐大 */
}

/* 3. 中間區域 (main) 用 flex: 1 撐開，footer就會吸底 */
.admin-main {
  flex: 1;
  padding-top: 0;
}
.user-main {
  flex: 1;
  padding-top: 130px;
  background-color: #f9f9f9;
}

/* =============== 原先管理員介面按鈕 =============== */
.welcome-admin {
  margin-top: 5px;
  font-size: 18px;
}
.admin-logout-btn {
  background-color: #bdd3b6;
  color: #080808;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  padding: 8px 12px;
  cursor: pointer !important;
}
.admin-logout-btn:hover {
  background-color: #45a049;
}

/* =============== Header & Footer & 其他既有樣式 =============== */
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}

.site-header {
  position: fixed; /* 固定在頁面頂部 */
  top: 0; /* 讓 header 貼齊頁面頂端 */
  left: 0;
  width: 100%; /* 讓 header 撐滿整個寬度 */
  background-color: #FAEBD7;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 1000; /* 確保 header 在最上層，不會被其他內容蓋住 */
}

.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
}
.homepage-link {
  text-decoration: none;
}
.logo-box .site-title {
  margin: 0;
  font-size: 2rem;
  color: #808000;
  font-family: "Sour Gummy", serif;
}
.user-nav ul {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}
.admin-nav ul {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}
.user-admin-name {
  width: 100px;
  font-size: 19px;
}
.user-nav li {
  display: flex;
  align-items: center;
}
.user-nav a {
  text-decoration: none;
  color: #0d0d0d;
  font-size: 1.15rem;
  display: flex;
  align-items: center;
}
.user-nav a:hover {
  color: #98b10d;
}
.user-nav button {
  width: 80px;
  background-color: #a6c29d;
  color: black;
  font-size: 17px;
  border: none;
  border-radius: 5px;
  padding: 3px;
}
/* 搜尋框那行背景 */
.header-bottom {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  background-color: #FAEBD7;
  width: 80%;
  margin: 0 auto;
}
.category-container {
  width: 100%;
  border-bottom: 2px solid #ddd;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 0;
  background-color: #d7e4aa;
}
.category-nav ul {
  list-style: none;
  display: flex;
  justify-content: center;
  gap: 60px;
  padding: 0;
  margin: 0;
  flex-wrap: wrap;
}
.category-nav a {
  text-decoration: none;
  color: #333;
  font-size: 18px;
  font-weight: bold;
  padding: 15px 30px;
  transition: color 0.2s ease-in-out, background-color 0.2s ease-in-out;
  border-radius: 8px;
  min-width: 160px;
  text-align: center;
}
.category-nav a:hover {
  color: #f7f1f1;
  background-color: #8a48ec;
}
@media (max-width: 768px) {
  .category-nav ul {
    flex-wrap: wrap;
    gap: 20px;
  }
  .category-nav a {
    font-size: 1rem;
    padding: 10px 15px;
  }
}

/* .site-footer {
  background-color: #d9d9d9;
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px;
  height: 80px; 
} */

/* Footer Styling */
.site-footer {
  background-color: #f5dfc3;
  color: rgb(19, 19, 19);
  padding: 40px 0;
  /* margin-top: 20px; */
}

.footer-container {
  display: flex;
  justify-content: space-between;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-section {
  flex: 2;
  padding: 0 20px;
  margin-left: 30px;
}

.footer-section h3 {
  font-size: 19px;
  margin-bottom: 10px;
}

.footer-section p,
.footer-section ul {
  font-size: 18px;
  line-height: 1.6;
}

.footer-section ul {
  list-style-type: none;
  padding: 0;
}

.footer-section ul li {
  margin-bottom: 8px;
}

.footer-section ul li a {
  color: #000000;
  text-decoration: none;
}

.footer-section ul li a:hover {
  text-decoration: underline;
}

.footer-bottom {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  border-top: 1px solid #34495e;
  padding-top: 10px;
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
  color: #4a4a4a;
}

.mark-all-read-button {
  background: #8a2be2;
  color: #fff;
  width: 100px;
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

/* 未讀通知 (橘色背景) */
.modal-body li.unread {
  font-weight: bold;
  background: #ffedcc; /* 淡橘色背景 */
  border-left: 6px solid #ff8800; /* 橘色左邊條 */
  padding: 12px;
  margin-bottom: 10px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 10px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
}

/* 已讀通知 (白色背景) */
.modal-body li.read {
  font-weight: normal;
  background: #fff;
  border-left: 6px solid #cccccc; /* 灰色左邊條 */
  padding: 12px;
  margin-bottom: 10px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 10px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.05);
}

.modal-footer {
  text-align: right;
  margin-top: 15px;
}

.close-button {
  background: #ff5a5f;
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
