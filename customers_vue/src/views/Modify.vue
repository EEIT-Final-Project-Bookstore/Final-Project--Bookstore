<template>
  <div class="customerList-container">
    <h1>修改會員資料</h1>
    <div class="customer-details">
      <button class="btn-edit" @click="toggleEditMode">
        {{ isEditMode ? "取消修改" : "修改資料" }}
      </button>
      <table class="customer-table">
        <tbody>
          <tr>
            <th>姓名</th>
            <td>
              <span v-if="!isEditMode">{{ customer.customerName }}</span>
              <input
                v-else
                v-model="editableCustomer.customerName"
                type="text"
                class="editable-input"
              />
            </td>
          </tr>
          <tr>
            <th>帳號</th>
            <td>{{ customer.username }}</td>
          </tr>
          <tr>
            <th>密碼</th>
            <td>
              <span v-if="!isEditMode">{{ customer.password }}</span>
              <div v-else>
                <input
                  v-model="editableCustomer.password"
                  type="text"
                  placeholder="新密碼"
                  class="editable-input"
                />
                <input
                  v-model="confirmPassword"
                  type="text"
                  placeholder="確認新密碼"
                  class="editable-input"
                />
              </div>
            </td>
          </tr>
          <tr>
            <th>電子郵件</th>
            <td>{{ customer.email }}</td>
          </tr>
          <tr>
            <th>電話</th>
            <td>
              <span v-if="!isEditMode">{{ customer.phoneNumber }}</span>
              <input
                v-else
                v-model="editableCustomer.phoneNumber"
                type="text"
                class="editable-input"
              />
            </td>
          </tr>
          <tr>
            <th>手機</th>
            <td>
              <span v-if="!isEditMode">{{ customer.mobileNumber }}</span>
              <input
                v-else
                v-model="editableCustomer.mobileNumber"
                type="text"
                class="editable-input"
              />
            </td>
          </tr>
        </tbody>
      </table>

      <div v-if="isEditMode" class="form-actions">
        <button class="btn-save" @click="saveChanges">保存</button>
        <button class="btn-cancel" @click="cancelChanges">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CustomerDetails",
  data() {
    return {
      isEditMode: false,
      customer: {
        customerName: "",
        username: "",
        password: "",
        email: "",
        phoneNumber: "",
        mobileNumber: "",
      },
      editableCustomer: {},
      confirmPassword: "", // 用於確認新密碼的輸入
      username: "User123", // 假設的用戶帳號，根據需要改為動態獲取
    };
  },
  created() {
    this.fetchCustomerData();
  },
  methods: {
    // 從後端獲取客戶資料
    fetchCustomerData() {
      axios
        .get(`http://192.168.23.112:8080/api/customer/${this.username}`)
        .then((response) => {
          this.customer = response.data;
        })
        .catch((error) => {
          console.error("無法獲取會員資料:", error);
        });
    },

    // 切換編輯模式
    toggleEditMode() {
      this.isEditMode = !this.isEditMode;

      // 初始化編輯模式的資料
      if (this.isEditMode) {
        this.editableCustomer = { ...this.customer };
        this.confirmPassword = ""; // 清空確認密碼
      } else {
        this.editableCustomer = {};
        this.confirmPassword = "";
      }
    },

    // 保存修改的資料
    saveChanges() {
      // 驗證密碼輸入是否一致
      if (
        this.editableCustomer.password &&
        this.editableCustomer.password !== this.confirmPassword
      ) {
        alert("新密碼與確認密碼不一致！");
        return;
      }

      // Base64 編碼密碼（如果用戶輸入了新密碼）
      const encodedPassword = this.editableCustomer.password
        ? btoa(this.editableCustomer.password)
        : this.customer.password;

      // 發送更新請求到後端
      axios
        .put(`http://192.168.23.112:8080/api/customer/${this.username}`, {
          customerName: this.editableCustomer.customerName,
          password: encodedPassword, // 傳遞編碼後的密碼
          phoneNumber: this.editableCustomer.phoneNumber,
          mobileNumber: this.editableCustomer.mobileNumber,
        })
        .then(() => {
          alert("會員資料更新成功！");
          this.isEditMode = false;

          // 更新顯示的資料
          this.customer.customerName = this.editableCustomer.customerName;
          this.customer.password =
            this.editableCustomer.password || this.customer.password;
          this.customer.phoneNumber = this.editableCustomer.phoneNumber;
          this.customer.mobileNumber = this.editableCustomer.mobileNumber;
        })
        .catch((error) => {
          console.error("更新會員資料失敗:", error);
        });
    },

    // 取消修改
    cancelChanges() {
      this.isEditMode = false;
      this.editableCustomer = {};
      this.confirmPassword = "";
    },
  },
};
</script>

<style scoped>
/* 使用提供的 CSS */
.customerList-container {
  font-family: Arial, sans-serif;
  margin: 0 auto;
  padding: 20px;
  max-width: 1200px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.site-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.site-header .logo img {
  max-height: 50px;
}

.customer-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.customer-table th,
.customer-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.customer-table th {
  background-color: #f4f4f4;
  font-weight: bold;
}

.customer-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.customer-table tr:hover {
  background-color: #f1f1f1;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.editable-input {
  width: 85%;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.btn-edit {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-save {
  background-color: #28a745;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-cancel {
  background-color: #6c757d;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
