<template>
    <div>
      <h1>通知管理</h1>
  
      <!-- 新增通知 -->
      <h2>新增通知</h2>
      <div>
        <input v-model="newNotification.notificationTypeID" type="number" placeholder="通知类型ID" />
        <input v-model="newNotification.customerID" type="number" placeholder="顾客ID" />
        <input v-model="newNotification.content" type="text" placeholder="通知内容" />
        <button @click="createNotification">新增通知</button>
      </div>
  
      <!-- 篩選通知 -->
      <h2>所有通知</h2>
      <div>
        <input
          v-model="filterCustomerID"
          type="number"
          placeholder="輸入顧客ID以篩選"
        />
        <button @click="filterNotifications">篩選</button>
        <button @click="resetFilter" style="margin-left: 10px;">重置</button>
      </div>
  
      <!-- 通知表格 -->
      <div v-if="notifications.length">
        <table border="1">
          <thead>
            <tr>
              <th>通知ID</th>
              <th>通知类型</th>
              <th>顾客ID</th>
              <th>内容</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="notification in notifications" :key="notification.notificationID">
              <td>{{ notification.notificationID }}</td>
              <td>{{ notification.notificationType.notificationType }}</td>
              <td>{{ notification.customer.customerID }}</td>
              <td>{{ notification.content }}</td>
              <td>{{ notification.status.detailedStatus }}</td>
              <td>
                <button @click="openEditModal(notification)">修改</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        <p>没有通知</p>
      </div>
  
      <!-- 修改通知模態框 -->
      <div v-if="isEditModalOpen" class="modal-overlay">
        <div class="modal">
          <h3>修改通知</h3>
          <input v-model="editNotification.notificationID" type="number" disabled placeholder="通知ID" />
          <input v-model="editNotification.notificationTypeID" type="number" placeholder="通知类型ID" />
          <input v-model="editNotification.customerID" type="number" disabled placeholder="顾客ID（不可修改）" />
          <input v-model="editNotification.content" type="text" placeholder="通知内容" />
          <div class="modal-footer">
            <button @click="updateNotification">保存修改</button>
            <button @click="closeEditModal">取消</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
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
            alert("新增通知成功！");
            this.fetchNotifications();
            this.newNotification = { notificationTypeID: "", customerID: "", content: "" };
          } else {
            alert("新增通知失败，请检查输入！");
          }
        } catch (error) {
          console.error("新增通知时发生错误：", error);
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
            alert("无法获取通知！");
          }
        } catch (error) {
          console.error("获取通知时发生错误：", error);
        }
      },
  
      // 根據顧客ID篩選通知
      filterNotifications() {
        if (!this.filterCustomerID) {
          alert("請輸入顧客ID！");
          return;
        }
        this.notifications = this.allNotifications.filter(
          (notification) =>
            notification.customer.customerID === parseInt(this.filterCustomerID)
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
            alert("更新通知成功！");
            this.fetchNotifications(); // 刷新通知列表
            this.closeEditModal(); // 關閉模態框
          } else {
            alert("更新通知失败，请检查输入！");
          }
        } catch (error) {
          console.error("更新通知时发生错误：", error);
        }
      },
    },
    mounted() {
      this.fetchNotifications();
    },
  };
  </script>
  
  <style scoped>
  /* 表格樣式 */
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  table th,
  table td {
    padding: 8px;
    text-align: left;
  }
  
  /* 模態框樣式 */
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
    background: white;
    padding: 20px;
    border-radius: 10px;
    width: 300px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
  
  .modal-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
  }
  </style>