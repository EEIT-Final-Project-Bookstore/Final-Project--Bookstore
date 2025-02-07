<template>
  <div class="admin-container">
    <!-- 頁頭 -->
    <header class="header">
      <div class="header-content">
        <h1><i class="fas fa-bell"></i> 通知管理</h1>
      </div>
    </header>

    <div class="main-content">
      <!-- 新增通知 -->
      <div class="card">
        <h2>新增通知</h2>
        <div class="input-grid">
          <input
            v-model="newNotification.notificationTypeID"
            type="number"
            placeholder="通知類型ID"
            class="input short-input"
          />
          <input
            v-model="newNotification.customerID"
            type="number"
            placeholder="顧客ID"
            class="input short-input"
          />
          <input
            v-model="newNotification.content"
            type="text"
            placeholder="通知內容"
            class="input"
          />
          <button @click="createNotification" class="btn btn-primary">
            新增通知
          </button>
        </div>
      </div>

      <!-- 篩選通知 -->
      <div class="card">
        <div class="filter-section">
          <div class="search-box">
            <i class="fas fa-search search-icon"></i>
            <input
              v-model="filterCustomerID"
              type="number"
              placeholder="輸入顧客ID以篩選"
              class="input short-input search-input"
            />
          </div>
          <div class="button-group">
            <button @click="filterNotifications" class="btn btn-primary">
              篩選
            </button>
            <button @click="resetFilter" class="btn btn-secondary">
              重置
            </button>
          </div>
        </div>
      </div>

      <!-- 通知表格 -->
      <div class="card table-card" v-if="notifications.length">
        <div class="table-responsive">
          <table>
            <thead>
              <tr>
                <th>通知ID</th>
                <th>通知類型</th>
                <th>顧客ID</th>
                <th>內容</th>
                <th>狀態</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="notification in notifications" :key="notification.notificationID">
                <td>{{ notification.notificationID }}</td>
                <td>{{ notification.notificationType.notificationType }}</td>
                <td>{{ notification.customer.customerID }}</td>
                <td>{{ notification.content }}</td>
                <td>
                  <span :class="['status-badge', 
                    notification.status.detailedStatus === '未讀' ? 'status-unread' : 'status-read'
                  ]">
                    {{ notification.status.detailedStatus }}
                  </span>
                </td>
                <td>
                  <button @click="openEditModal(notification)" class="btn btn-icon">
                    <i class="fas fa-edit"></i> 修改
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div v-else class="card empty-state">
        <p>没有通知</p>
      </div>

      <!-- 修改通知模態框 -->
      <div v-if="isEditModalOpen" class="modal-overlay">
        <div class="modal no-scroll">
          <div class="modal-header">
            <h3>修改通知</h3>
            <button @click="closeEditModal" class="btn-close">
              <i class="fas fa-times"></i>
            </button>
          </div>
          <div class="modal-body">
            <input
              v-model="editNotification.notificationID"
              type="number"
              disabled
              placeholder="通知ID"
              class="input short-input disabled"
            />
            <input
              v-model="editNotification.notificationTypeID"
              type="number"
              placeholder="通知類型ID"
              class="input short-input"
            />
            <input
              v-model="editNotification.customerID"
              type="number"
              disabled
              placeholder="顧客ID（不可修改）"
              class="input short-input disabled"
            />
            <input
              v-model="editNotification.content"
              type="text"
              placeholder="通知內容"
              class="input"
            />
          </div>
          <div class="modal-footer">
            <button @click="updateNotification" class="btn btn-primary">
              保存修改
            </button>
            <button @click="closeEditModal" class="btn btn-secondary">
              取消
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
import Swal from "sweetalert2"; // 引入 SweetAlert2

export default {
  data() {
    return {
      newNotification: {
        notificationTypeID: "",
        customerID: "",
        content: "",
      },
      editNotification: {
        notificationID: "",
        notificationTypeID: "",
        customerID: "",
        content: "",
      },
      notifications: [], // 存放所有通知
      allNotifications: [], // 存放完整通知列表，用於重置篩選
      filterCustomerID: "", // 篩選用的顧客ID
      isEditModalOpen: false,
    };
  },
  methods: {
    // 新增通知
    async createNotification() {
      try {
        const response = await fetch("http://localhost:8080/admin/notifications/create", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            notificationType: { notificationTypeID: this.newNotification.notificationTypeID },
            customer: { customerID: this.newNotification.customerID },
            content: this.newNotification.content,
          }),
        });

        if (response.ok) {
          Swal.fire({
            icon: "success",
            title: "新增通知成功！",
            showConfirmButton: false,
            timer: 1500,
          });
          this.fetchNotifications();
          this.newNotification = { notificationTypeID: "", customerID: "", content: "" };
        } else {
          Swal.fire({
            icon: "error",
            title: "新增通知失敗！",
            text: "請檢查輸入！",
          });
        }
      } catch (error) {
        console.error("新增通知時發生錯誤：", error);
        Swal.fire({
          icon: "error",
          title: "伺服器錯誤",
          text: "請稍後再試！",
        });
      }
    },

    // 獲取所有通知
    async fetchNotifications() {
      try {
        const response = await fetch("http://localhost:8080/admin/notifications/all");
        if (response.ok) {
          const data = await response.json();
          this.notifications = data;
          this.allNotifications = data; // 保存完整數據
        } else {
          Swal.fire({
            icon: "error",
            title: "無法加載通知！",
          });
        }
      } catch (error) {
        console.error("獲取通知時發生錯誤：", error);
        Swal.fire({
          icon: "error",
          title: "伺服器錯誤",
          text: "請稍後再試！",
        });
      }
    },

    // 根據顧客ID篩選通知
    filterNotifications() {
      if (!this.filterCustomerID) {
        Swal.fire({
          icon: "warning",
          title: "請輸入顧客ID！",
        });
        return;
      }
      this.notifications = this.allNotifications.filter(
        (notification) => notification.customer.customerID === parseInt(this.filterCustomerID)
      );
    },

    // 重置篩選
    resetFilter() {
      this.filterCustomerID = "";
      this.notifications = this.allNotifications;
    },

    // 打開修改模態框
    openEditModal(notification) {
      this.isEditModalOpen = true;
      this.editNotification = {
        notificationID: notification.notificationID,
        notificationTypeID: notification.notificationType.notificationTypeID,
        customerID: notification.customer.customerID,
        content: notification.content,
      };
    },

    // 關閉修改模態框
    closeEditModal() {
      this.isEditModalOpen = false;
      this.editNotification = {
        notificationID: "",
        notificationTypeID: "",
        customerID: "",
        content: "",
      };
    },

    // 更新通知
    async updateNotification() {
      try {
        const response = await fetch(
          `http://localhost:8080/admin/notifications/update/${this.editNotification.notificationID}`,
          {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              notificationID: this.editNotification.notificationID,
              notificationType: { notificationTypeID: this.editNotification.notificationTypeID },
              customer: { customerID: this.editNotification.customerID },
              content: this.editNotification.content,
            }),
          }
        );

        if (response.ok) {
          Swal.fire({
            icon: "success",
            title: "更新通知成功！",
            showConfirmButton: false,
            timer: 1500,
          });
          this.fetchNotifications(); // 刷新通知列表
          this.closeEditModal(); // 關閉模態框
        } else {
          Swal.fire({
            icon: "error",
            title: "更新通知失敗！",
            text: "請檢查輸入！",
          });
        }
      } catch (error) {
        console.error("更新通知時發生錯誤：", error);
        Swal.fire({
          icon: "error",
          title: "伺服器錯誤",
          text: "請稍後再試！",
        });
      }
    },
  },
  mounted() {
    this.fetchNotifications();
  },
};
</script>
  
  <style scoped>
/* 去除模態框滾動條 */
.modal.no-scroll .modal-body {
  overflow: hidden;
}

/* 縮短輸入框 */
.short-input {
  max-width: 200px;
}

.admin-container {
  min-height: 100vh;
  background-color: #f3f4f6;
}

.header {
  background-color: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 1rem 0;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.header h1 {
  font-size: 1.5rem;
  font-weight: 600;
  color: #111827;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.main-content {
  max-width: 1200px;
  margin: 2rem auto;
  padding: 0 1rem;
}

.card {
  background: white;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}

.input-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
  margin-top: 1rem;
}

.input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 0.375rem;
  outline: none;
  transition: all 0.2s;
}

.input:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.input.disabled {
  background-color: #f3f4f6;
  cursor: not-allowed;
}

.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 0.375rem;
  font-weight: 500;
  transition: all 0.2s;
  cursor: pointer;
  border: none;
}

.btn-primary {
  background-color: #3b82f6;
  color: white;
}

.btn-primary:hover {
  background-color: #2563eb;
}

.btn-secondary {
  background-color: #f3f4f6;
  color: #374151;
}

.btn-secondary:hover {
  background-color: #e5e7eb;
}

.btn-icon {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #3b82f6;
  background: none;
  padding: 0.5rem;
}

.btn-icon:hover {
  color: #2563eb;
  background-color: #f3f4f6;
}

.filter-section {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.search-box {
  flex: 1;
  position: relative;
}

.search-icon {
  position: absolute;
  left: 1rem;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
}

.search-input {
  padding-left: 2.5rem;
}

.button-group {
  display: flex;
  gap: 0.5rem;
}

.table-responsive {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th {
  background-color: #f9fafb;
  padding: 0.75rem 1rem;
  text-align: left;
  font-weight: 500;
  color: #6b7280;
  text-transform: uppercase;
  font-size: 0.75rem;
}

td {
  padding: 1rem;
  border-bottom: 1px solid #e5e7eb;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  font-size: 0.875rem;
}

.status-unread {
  background-color: #fee2e2;
  color: #991b1b;
}

.status-read {
  background-color: #d1fae5;
  color: #065f46;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 50;
}

.modal {
  background: white;
  border-radius: 0.5rem;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.modal-body {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.modal-footer {
  padding: 1rem 1.5rem;
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
  border-top: 1px solid #e5e7eb;
}

.btn-close {
  background: none;
  border: none;
  color: #6b7280;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 0.375rem;
}

.btn-close:hover {
  background-color: #f3f4f6;
}

.empty-state {
  text-align: center;
  color: #6b7280;
  padding: 3rem 1.5rem;
}

@media (max-width: 640px) {
  .filter-section {
    flex-direction: column;
  }
  
  .button-group {
    width: 100%;
  }
  
  .btn {
    flex: 1;
  }
}
</style>