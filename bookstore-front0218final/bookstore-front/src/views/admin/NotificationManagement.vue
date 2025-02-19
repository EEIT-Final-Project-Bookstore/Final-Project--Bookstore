<template>
  <div class="admin-container">
    <header class="header">
      <div class="header-content"></div>
    </header>

    <div class="main-content">
      <!-- 新增通知 -->
      <div class="card">
        <!-- <h2>新增通知</h2> -->
        <div class="input-grid">
          <!-- ✅ 選擇通知類型 -->
          <select v-model="newNotification.notificationTypeID" class="input short-input">
            <option value="" disabled>請選擇通知類型</option>
            <option
              v-for="type in notificationTypes"
              :key="type.notificationTypeID"
              :value="type.notificationTypeID"
            >
              {{ type.notificationType }}
            </option>
          </select>

          <!-- ✅ 選擇顧客 -->
          <select v-model="newNotification.customerID" class="input short-input">
            <option value="" disabled>請選擇顧客</option>
            <option
              v-for="customer in customers"
              :key="customer.customerID"
              :value="customer.customerID"
            >
              {{ customer.username }}
            </option>
          </select>

          <input
            v-model="newNotification.content"
            type="text"
            placeholder="通知內容"
            class="input"
          />
          <button @click="createNotification" class="btn btn-primary">新增通知</button>
        </div>
      </div>
      <div class="filter-section">
        <input
          v-model="filterUsername"
          type="text"
          class="input-username"
          placeholder="輸入顧客名稱篩選"
        />
        <button @click="resetFilter" class="btn btn-secondary">重置</button>
      </div>

      <!-- 通知表格 -->
      <div class="card table-card" v-if="notifications.length">
        <div class="table-responsive">
          <table>
            <thead>
              <tr>
                <th>通知ID</th>
                <th>通知類型</th>
                <th>顧客</th>
                <th>內容</th>
                <th>狀態</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="notification in paginatedNotifications"
                :key="notification.notificationID"
              >
                <td>{{ notification.notificationID }}</td>
                <td>{{ notification.notificationType.notificationType }}</td>
                <td>{{ notification.customer.username }}</td>
                <td>{{ notification.content }}</td>
                <td>
                  <span
                    :class="[
                      'status-badge',
                      notification.status.detailedStatus === '未讀'
                        ? 'status-unread'
                        : 'status-read',
                    ]"
                  >
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

        <!-- 分頁控制 -->
        <div class="pagination">
          <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">
            上一頁
          </button>
          <span>第 {{ currentPage }} 頁 / 共 {{ totalPages }} 頁</span>
          <button
            @click="changePage(currentPage + 1)"
            :disabled="currentPage === totalPages"
          >
            下一頁
          </button>
        </div>
      </div>

      <!-- 修改通知模態框 -->
      <div v-if="isEditModalOpen" class="modal-overlay">
        <div class="modal">
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
              class="input short-input disabled"
            />

            <!-- ✅ 修改通知類型 -->
            <select
              v-model="editNotification.notificationTypeID"
              class="input short-input"
            >
              <option
                v-for="type in notificationTypes"
                :key="type.notificationTypeID"
                :value="type.notificationTypeID"
              >
                {{ type.notificationType }}
              </option>
            </select>

            <!-- ✅ 顯示顧客名稱 -->
            <input
              v-model="editNotification.username"
              type="text"
              readonly
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
            <button @click="updateNotification" class="btn btn-primary">保存修改</button>
            <button @click="closeEditModal" class="btn btn-secondary">取消</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2"; // 引入 SweetAlert2
import axiosapi from "@/plugins/axios";

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
        username: "",
        content: "",
      },
      notificationTypes: [],
      customers: [],
      notifications: [],
      isEditModalOpen: false,
      filterUsername: "", // ✅ 用於篩選顧客名稱
      currentPage: 1, // ✅ 當前分頁
      itemsPerPage: 5, // ✅ 每頁顯示 5 筆
    };
  },
  computed: {
    filteredNotifications() {
      if (!this.filterUsername.trim()) {
        return this.notifications;
      }
      const searchText = this.filterUsername.trim().toLowerCase();
      return this.notifications.filter((notification) =>
        notification.customer.username.toLowerCase().startsWith(searchText)
      );
    },
    paginatedNotifications() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredNotifications.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredNotifications.length / this.itemsPerPage);
    },
  },
  methods: {
    changePage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    resetFilter() {
      this.filterUsername = "";
      this.currentPage = 1; // ✅ 重置分頁到第一頁
    },

    applyFilter() {
      // 觸發篩選條件，computed 會自動重新計算
    },
    resetFilter() {
      this.filterUsername = ""; // 清空篩選條件
    },
    // 新增通知
    // 取得通知類型
    async fetchNotificationTypes() {
      try {
        const response = await axiosapi.get("/api/notification-types");
        this.notificationTypes = response.data;
      } catch (error) {
        Swal.fire("錯誤", "無法加載通知類型，請稍後再試！", "error");
      }
    },

    // 取得顧客清單
    async fetchCustomers() {
      try {
        const response = await axiosapi.get("/api/customers");
        this.customers = response.data;
      } catch (error) {
        Swal.fire("錯誤", "無法加載顧客清單，請稍後再試！", "error");
      }
    },

    // 取得所有通知
    async fetchNotifications() {
      try {
        const response = await axiosapi.get("/admin/notifications/all");
        this.notifications = response.data;
      } catch (error) {
        Swal.fire("錯誤", "無法加載通知，請稍後再試！", "error");
      }
    },

    // 創建通知
    async createNotification() {
      if (
        !this.newNotification.notificationTypeID ||
        !this.newNotification.customerID ||
        !this.newNotification.content.trim()
      ) {
        Swal.fire("警告", "請輸入完整內容！", "warning");
        return;
      }
      try {
        const payload = {
          notificationType: {
            notificationTypeID: this.newNotification.notificationTypeID,
          },
          customer: { customerID: this.newNotification.customerID },
          content: this.newNotification.content,
        };
        await axiosapi.post("/admin/notifications/create", payload);

        Swal.fire("成功", "通知已建立！", "success");
        this.newNotification = { notificationTypeID: "", customerID: "", content: "" };
        this.fetchNotifications();
      } catch (error) {
        Swal.fire("錯誤", "創建通知失敗，請稍後再試！", "error");
      }
    },

    // 打開修改模態框
    openEditModal(notification) {
      console.log("📢 打開 modal！", notification);
      this.isEditModalOpen = true;
      this.editNotification = {
        notificationID: notification.notificationID,
        notificationTypeID: notification.notificationType.notificationTypeID,
        customerID: notification.customer.customerID,
        username: notification.customer.username,
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
        await axiosapi.put(
          `/admin/notifications/update/${this.editNotification.notificationID}`,
          {
            notificationID: this.editNotification.notificationID,
            notificationType: {
              notificationTypeID: this.editNotification.notificationTypeID,
            },
            customer: { customerID: this.editNotification.customerID },
            content: this.editNotification.content,
          }
        );

        Swal.fire({
          icon: "success",
          title: "更新通知成功！",
          showConfirmButton: false,
          timer: 1500,
        });
        this.fetchNotifications(); // 刷新通知列表
        this.closeEditModal(); // 關閉模態框
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
    this.fetchNotificationTypes();
    this.fetchCustomers();
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

.main-content {
  max-width: 1400px;
  margin: auto;
  margin-top: 10px;
  padding: 0 1rem;
}

.card {
  width: 1200px;
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
  width: 90%;
  padding: 0.75rem 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 0.375rem;
  outline: none;
  transition: all 0.2s;
  font-size: 17px;
}
.input-username {
  width: 80%;
  padding: 0.75rem 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 0.375rem;
  outline: none;
  transition: all 0.2s;
  font-size: 17px;
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
  font-size: 17px;
}

.btn-primary {
  background-color: #3b82f6;
  color: white;
  width: 150px;
  margin-left: 10px;
}

.btn-primary:hover {
  background-color: #2563eb;
}

.btn-secondary {
  background-color: #f3f4f6;
  color: #374151;
  width: 15%;
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
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);

  /* ✅ 讓 `.modal` 居中顯示 */
  display: flex;
  align-items: center;
  justify-content: center;

  z-index: 9999;
}

.modal {
  display: block !important;
  visibility: visible !important;
  background: white !important;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  z-index: 99999 !important;
  width: 90% !important;
  max-width: 450px !important;
  /* 限制最大寬度 */
  max-height: 80vh !important;
  /* ✅ 限制最大高度 */
  overflow-y: auto !important;
  /* ✅ 超過時允許滾動 */
  padding: 20px !important;
  border-radius: 12px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.3) !important;
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

.filter-section {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 1rem;
  background: white;
  padding: 1rem;
  border-radius: 0.5rem;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 1.5rem;
}

.filter-section .input {
  flex: 1;
  max-width: 300px;
  padding: 0.75rem;
  border: 1px solid #e5e7eb;
  border-radius: 0.375rem;
  outline: none;
  transition: all 0.2s;
  font-size: 1rem;
}

.filter-section .input:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.filter-section .btn {
  padding: 0.75rem 1rem;
  border-radius: 0.375rem;
  font-weight: 500;
  transition: all 0.2s;
  cursor: pointer;
  border: none;
  font-size: 1rem;
}

.filter-section .btn-primary {
  background-color: #3b82f6;
  color: white;
}

.filter-section .btn-primary:hover {
  background-color: #2563eb;
}

.filter-section .btn-secondary {
  background-color: #f3f4f6;
  color: #374151;
}

.filter-section .btn-secondary:hover {
  background-color: #e5e7eb;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  font-size: 16px;
}

.pagination button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 8px 12px;
  margin: 0 5px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.pagination button:hover {
  background-color: #0056b3;
}

.pagination button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.pagination .current-page {
  font-weight: bold;
  color: #333;
  padding: 0 10px;
}
</style>
