<template>
  <div>
    <nav class="bg-gray-800 p-4 text-white">
      <div class="container mx-auto flex justify-between items-center">
        <h1 class="text-xl font-bold">System Dashboard</h1>
        <div class="flex space-x-4">
          <!-- 喇叭按鈕 -->
          <button @click="openNotificationModal" class="px-4 py-2 bg-orange-500 rounded-md hover:bg-orange-600">
            🛎 系統通知
          </button>
          <!-- 書籍評論按鈕 -->
          <button @click="navigateTo('reviews')" class="px-4 py-2 bg-blue-600 rounded-md hover:bg-blue-700">
            書籍評論管理
          </button>
          <!-- 通知管理按鈕 -->
          <button @click="navigateTo('notification-management')" class="px-4 py-2 bg-green-500 rounded-md hover:bg-green-600">
            通知管理
          </button>
        </div>
      </div>
    </nav>

    <!-- 動態內容顯示區域 -->
    <div>
      <router-view></router-view>
    </div>

    <!-- 通知模態框 -->
    <div v-if="isNotificationModalOpen" class="modal-overlay">
      <div class="modal">
        <div class="modal-header">
          <h3>系統通知</h3>
          <button @click="markAllAsRead" class="mark-all-read-button">全部已讀</button>
        </div>
        <div class="modal-body">
          <div
            v-for="notification in notifications"
            :key="notification.notificationID"
            class="notification-item"
            :class="{ unread: notification.status.statusID === 4 }"
          >
            通知內容: {{ notification.content }}
          </div>
        </div>
        <div class="modal-footer">
          <button @click="closeNotificationModal" class="close-button">關閉</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isNotificationModalOpen: false,
      notifications: [],
    };
  },
  methods: {
    navigateTo(routeName) {
      this.$router.push({ name: routeName });
    },
    openNotificationModal() {
      this.isNotificationModalOpen = true;
      this.fetchNotifications();
    },
    closeNotificationModal() {
      this.isNotificationModalOpen = false;
    },
    async fetchNotifications() {
      const customerID = sessionStorage.getItem("customerID"); // 從 sessionStorage 獲取 customerID
      if (!customerID) {
        alert("未檢測到用戶登錄，請先登錄！");
        return;
      }
      try {
        const response = await fetch(`/admin/notifications/customer/${customerID}`);
        if (response.ok) {
          this.notifications = await response.json();
          console.log("通知列表加載成功:", this.notifications);
        } else {
          alert("獲取通知失敗，請稍後再試！");
        }
      } catch (error) {
        console.error("獲取通知時發生錯誤：", error);
        alert("伺服器錯誤，請稍後再試！");
      }
    },
    async markAllAsRead() {
      const customerID = sessionStorage.getItem("customerID");
      if (!customerID) {
        alert("未檢測到用戶登錄，請先登錄！");
        return;
      }
      try {
        const response = await fetch(`/admin/notifications/mark-all-as-read/${customerID}`, {
          method: "PUT",
        });
        if (response.ok) {
          alert("所有通知已標記為已讀！");
          this.fetchNotifications();
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
/* 喇叭按鈕樣式 */
.notification-button {
  background-color: #f39c12;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
}

/* 模態框覆蓋層 */
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

/* 模態框內容 */
.modal {
  background: white;
  width: 300px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 模態框標題與按鈕 */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
}

.mark-all-read-button {
  background: #28a745;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 3px;
}

/* 模態框通知列表 */
.modal-body {
  max-height: 200px;
  overflow-y: auto;
  padding: 10px;
}

.notification-item {
  padding: 5px;
  margin-bottom: 5px;
  border-bottom: 1px solid #ccc;
  background: #e6e6e6;
}

.notification-item.unread {
  background: #fff8dc;
  font-weight: bold;
}

/* 模態框關閉按鈕 */
.modal-footer {
  padding: 10px;
  text-align: center;
}

.close-button {
  background: #dc3545;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
}
</style>
