<template>
  <div class="customerList-container">
    <div class="login-content">
      <h1>客戶列表</h1>
      <table class="customer-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>帳號</th>
            <th>密碼</th>
            <th>電子郵件</th>
            <th>電話</th>
            <th>手機</th>
            <th>註冊時間</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(customer, index) in customers" :key="customer.customerID">
            <td>{{ customer.customerID }}</td>
            <td>{{ customer.customerName }}</td>
            <td>{{ customer.username }}</td>
            <td>{{ customer.password }}</td>
            <td>{{ customer.email }}</td>
            <td>0{{ customer.phoneNumber }}</td>
            <td>0{{ customer.mobileNumber }}</td>
            <td>{{ formatDate(customer.registrationTime) }}</td>
            <td>
              <button class="btn-edit" @click="editCustomer(customer)">修改</button>
              <button class="btn-delete" @click="deleteCustomer(customer.username)">
                刪除
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <div v-if="isEditModalVisible" class="modal">
        <div class="modal-content">
          <h3>修改會員資料</h3>
          <form @submit.prevent="updateCustomer">
            <div class="form-group">
              <label for="customerName">姓名</label>
              <input v-model="editForm.customerName" type="text" id="customerName" />
            </div>
            <div class="form-group">
              <label for="email">電子郵件</label>
              <input v-model="editForm.email" type="email" id="email" />
            </div>
            <div class="form-group">
              <label for="phoneNumber">電話</label>
              <input v-model="editForm.phoneNumber" type="text" id="phoneNumber" />
            </div>
            <div class="form-group">
              <label for="mobileNumber">手機</label>
              <input v-model="editForm.mobileNumber" type="text" id="mobileNumber" />
            </div>
            <div class="form-group">
              <label for="password">密碼</label>
              <input v-model="editForm.password" type="text" id="password" />
            </div>
            <div class="form-actions">
              <button type="submit" class="btn-save">保存</button>
              <button type="button" class="btn-cancel" @click="closeEditModal">
                取消
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axiosapi from '@/plugins/axios.js';
import Swal from "sweetalert2";

export default {
  name: "CustomerList",
  data() {
    return {
      customers: [], // 存放從後端獲取的客戶資料
      isEditModalVisible: false, // 控制修改彈窗的顯示與隱藏
      editForm: {
        customerID: null,
        username: "",
        customerName: "",
        email: "",
        phoneNumber: "",
        mobileNumber: "",
        password: "",
      },
    };
  },
  created() {
    this.fetchCustomers();
  },
  methods: {
    fetchCustomers() {
      axiosapi
        .get("/api/customers")
        .then((response) => {
          console.log("顧客資料:", response.data);
          this.customers = response.data;
        })
        .catch((error) => {
          console.error("無法取得客戶列表:", error);
        });
    },

    formatDate(dateString) {
      if (!dateString) return "";
      const options = {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
      };
      return new Date(dateString).toLocaleString("zh-TW", options);
    },

    editCustomer(customer) {
      this.editForm = { ...customer };
      this.isEditModalVisible = true;
    },

    updateCustomer() {
      const {
        username,
        customerName,
        email,
        phoneNumber,
        mobileNumber,
        password,
      } = this.editForm;

      if (!username) {
        console.error("無效的 username，無法更新");
        return;
      }

      axiosapi
        .put(`/api/customers/${username}`, {
          customerName,
          email,
          phoneNumber,
          mobileNumber,
          password,
        })
        .then(() => {
          Swal.fire({
            title: "修改成功",
            icon: "success",
          });
          this.isEditModalVisible = false;
          this.fetchCustomers();
        })
        .catch((error) => {
          console.error("更新會員資料失敗:", error);
        });
    },

    deleteCustomer(username) {
      if (!username) {
        console.error("無效的 username，無法刪除");
        return;
      }

      Swal.fire({
        title: "確定刪除該帳戶的資料？",
        text: "資料將無法再復原",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "是",
        cancelButtonText: "否",
      }).then((result) => {
        if (result.isConfirmed) {
          axiosapi
            .delete(`/api/customers/${username}`)
            .then(() => {
              Swal.fire({
                title: "刪除成功",
                text: "該帳戶已被刪除",
                icon: "success",
              });
              this.fetchCustomers();
            })
            .catch((error) => {
              console.error("刪除會員失敗:", error);
              Swal.fire({
                title: "刪除失敗",
                text: "無法刪除該用戶資料！",
                icon: "error",
              });
            });
        }
      });
    },

    closeEditModal() {
      this.isEditModalVisible = false;
    },
  },
};
</script>
<style scoped>
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

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.btn-edit {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-delete {
  background-color: #dc3545;
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