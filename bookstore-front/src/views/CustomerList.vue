<template>
    <div class="customerList-container">
      
      <div class="login-content">
        <h1>管理員頁面－客戶列表</h1>
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
            <tr v-for="(customer, index) in customers" :key="index">
              <td>{{ customer.customerId }}</td>
              <td>{{ customer.customerName }}</td>
              <td>{{ customer.username }}</td>
              <td>{{ customer.password }}</td>
              <td>{{ customer.email }}</td>
              <td>0{{ customer.phoneNumber }}</td>
              <td>0{{ customer.mobileNumber }}</td>
              <td>{{ formatDate(customer.registrationTime) }}</td>
              <td>
                <button class="btn-edit" @click="editCustomer(customer)">修改</button>
                <button class="btn-delete" @click="deleteCustomer(customer.customerId)">
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
  import axiosapi from "@/plugins/axios.js";
  
  export default {
    name: "CustomerList",
    data() {
      return {
        customers: [], // 存放從後端獲取的客戶資料
        isEditModalVisible: false, // 控制修改彈窗的顯示與隱藏
        editForm: {
          customerId: null,
          customerName: "",
          email: "",
          phoneNumber: "",
          mobileNumber: "",
          password: "",
        },
      };
    },
    created() {
      // 從後端獲取客戶列表資料
      this.fetchCustomers();
    },
    methods: {
      // 獲取客戶列表
      fetchCustomers() {
        axiosapi
          .get("/api/customers") // 修改為你的後端 API 地址
          .then((response) => {
            this.customers = response.data;
          })
          .catch((error) => {
            console.error("無法取得客戶列表:", error);
          });
      },
  
      // 格式化註冊時間
      formatDate(dateString) {
        const options = {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
          hour: "2-digit",
          minute: "2-digit",
        };
        return new Date(dateString).toLocaleString("zh-TW", options);
      },
  
      // 點擊「修改」按鈕
      editCustomer(customer) {
        this.editForm = { ...customer }; // 複製選中的客戶資料到表單
        this.isEditModalVisible = true;
      },
  
      // 更新客戶資料
      updateCustomer() {
        const { customerId, customerName, email, phoneNumber, mobileNumber, password } = this.editForm;
  
        // 將密碼字串轉換為 base64 編碼，後端應將其解碼並儲存為 byte[]
        const encodedPassword = btoa(password);
  
        axiosapi
          .put(`/api/customers/${customerId}`, {
            customerName,
            email,
            phoneNumber,
            mobileNumber,
            password: encodedPassword, // 傳遞編碼後的密碼
          })
          .then(() => {
            alert("會員資料更新成功！");
            this.isEditModalVisible = false;
            this.fetchCustomers(); // 刷新列表
          })
          .catch((error) => {
            console.error("更新會員資料失敗:", error);
          });
      },
  
      // 關閉修改彈窗
      closeEditModal() {
        this.isEditModalVisible = false;
      },
  
      // 點擊「刪除」按鈕
      deleteCustomer(customerId) {
        if (confirm("確定要刪除此會員嗎？")) {
          axiosapi
            .delete(`/api/customers/${customerId}`)
            .then(() => {
              alert("會員刪除成功！");
              this.fetchCustomers(); // 刷新列表
            })
            .catch((error) => {
              console.error("刪除會員失敗:", error);
            });
        }
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
  