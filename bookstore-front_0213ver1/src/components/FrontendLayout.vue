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
        <!-- 📌 這裡不變，確保搜尋框還在 header 內 -->
        <SearchPage />
      </div>
      <nav class="nav-bar">
        <ul>
          <li><router-link to="/">首頁</router-link></li>
          <li><a href="#" @click.prevent="openModal">通知</a></li>
          <li><router-link to="/login">登入帳號</router-link></li>
          <li><router-link to="/cart">購物車</router-link></li>
          <li><router-link to="/order/list">我的訂單</router-link></li>
        </ul>
      </nav>
    </header>

    <router-view v-slot="{ Component }">
  <transition name="fade" mode="out-in">
    <component :is="Component" />
  </transition>
</router-view>


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
        <h3>森圖書店</h3>
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
import Swal from "sweetalert2"; // ✅ 引入 SweetAlert2


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
          `http://localhost:8080/admin/notifications/mark-all-as-read/${customerID}`,
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
};
</script>






<style scoped>
@import url('https://fonts.googleapis.com/css2?family=LXGW+WenKai+TC&family=Sour+Gummy:ital,wght@0,100..900;1,100..900&display=swap');




/* 整體容器 */
.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #fff;
  color: #0D0D0D;
  font-family: "Sour Gummy", serif;
}




/* 📌 固定 Header，但不影響搜尋紀錄 */
.site-header {
  background-color: #b2bfac;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 200px; /* 設定固定高度 */
  position: relative;
  z-index: 10;
}





.header-left .site-title {
  margin: 0;
  font-size: 2.5rem;
  color: #404040;
}




/* 🔍 搜尋框仍在 Header 內 */
.header-center {
  display: flex;
  justify-content: center;
  flex-grow: 1;
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
