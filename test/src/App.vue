<template>
  <div>
    <!-- 喇叭按鈕 -->
    <button @click="openModal" class="notification-button">
      喇叭
    </button>

    <!-- 模態框 -->
    <div v-if="isModalOpen" class="modal-overlay">
      <div class="modal">
        <!-- 標題與已讀全部按鈕 -->
        <div class="modal-header">
          <h3>系統通知</h3>
          <button @click="markAllAsRead" class="mark-all-read-button">已讀全部</button>
        </div>

        <!-- 通知列表 -->
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

        <!-- 關閉按鈕 -->
        <div class="modal-footer">
          <button @click="closeModal" class="close-button">關閉</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
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
    // 獲取通知列表
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
    // 標記所有通知為已讀
    async markAllAsRead() {
      const customerID = sessionStorage.getItem("customerID"); // 從 sessionStorage 獲取 customerID
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
          this.fetchNotifications(); // 更新通知列表
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
