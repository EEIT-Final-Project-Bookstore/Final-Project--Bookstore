<template>
  <div class="customerList-container">
    <h1>修改會員資料</h1>
    <div class="customer-details">
      <!-- 切換編輯模式按鈕 -->
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
                  placeholder="新密碼，若無變更毋需填寫"
                  class="editable-input"
                />
                <input
                  v-model="confirmPassword"
                  type="text"
                  placeholder="確認新密碼，若無變更毋需填寫"
                  class="editable-input"
                />
                <div v-if="errors.password" class="error-message">
                  {{ errors.password }}
                </div>
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
              <div v-if="errors.phoneNumber" class="error-message">
                {{ errors.phoneNumber }}
              </div>
            </td>
          </tr>
          <tr>
            <th>手機</th>
            <td>
              <span v-if="!isEditMode">{{ customer.mobileNumber }}</span>
              <div v-else>
                <input
                  v-model="editableCustomer.mobileNumber"
                  type="text"
                  class="editable-input"
                />
                <div v-if="errors.mobileNumber" class="error-message">
                  {{ errors.mobileNumber }}
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- 編輯狀態下的動作按鈕 -->
      <div v-if="isEditMode" class="form-actions">
        <button class="btn-save" @click="saveChanges" style="justify-content: center">
          保存
        </button>
        <button class="btn-cancel" @click="cancelChanges">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/authStore";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";
import axiosapi from "@/plugins/axios";

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
      confirmPassword: "",
      errors: {
        password: "",
        mobileNumber: "",
      },
    };
  },
  created() {
    const authStore = useAuthStore();
    const router = useRouter();
    authStore.initializeAuth();
    if (!authStore.isAuthenticated || !authStore.user || !authStore.user.username) {
      alert("您尚未登入或無效的使用者資訊，請先登入。");
      router.push("/login");
      return;
    }
    const loggedInUsername = authStore.user.username;
    this.fetchCustomerData(loggedInUsername);
  },
  methods: {
    fetchCustomerData(username) {
      axiosapi
        .get(`/api/customers/${username}`, { withCredentials: true })
        .then((response) => {
          this.customer = response.data;
        })
        .catch((error) => {
          console.error("無法獲取會員資料:", error);
        });
    },
    toggleEditMode() {
      this.isEditMode = !this.isEditMode;
      if (this.isEditMode) {
        this.editableCustomer = {
          ...this.customer,
          password: "",
        };
        this.confirmPassword = "";
      } else {
        this.editableCustomer = {};
        this.confirmPassword = "";
        this.resetErrors();
      }
    },
    saveChanges() {
      this.resetErrors();
      let hasError = false;

      // (1) 檢查「新密碼」欄位
      if (this.editableCustomer.password) {
        if (this.editableCustomer.password !== this.confirmPassword) {
          this.errors.password = "新密碼與確認密碼不一致";
          hasError = true;
        } else {
          const passwordRegex = /^[A-Za-z0-9]{6,20}$/;
          if (!passwordRegex.test(this.editableCustomer.password)) {
            this.errors.password = "密碼必須為6-20字的英文或數字";
            hasError = true;
          }
        }
      }

      // (2) 檢查電話 / 手機
      if (
        this.editableCustomer.phoneNumber &&
        this.editableCustomer.phoneNumber !== this.customer.phoneNumber
      ) {
        const phoneRegex = /^0\d{7,9}$/;
        if (!phoneRegex.test(this.editableCustomer.phoneNumber)) {
          this.errors.phoneNumber =
            "市內電話格式不正確：必須以 0 開頭，並且總長 8 到 10 碼";
          hasError = true;
        }
      }
      if (
        this.editableCustomer.mobileNumber &&
        this.editableCustomer.mobileNumber !== this.customer.mobileNumber
      ) {
        const phoneRegex = /^09\d{8}$/;
        if (!phoneRegex.test(this.editableCustomer.mobileNumber)) {
          this.errors.mobileNumber = "手機號碼必須為 10 碼，且以 09 開頭";
          hasError = true;
        }
      }
      if (hasError) return;

      // (3) 組合要送給後端的資料
      const passwordToUpdate = this.editableCustomer.password
        ? this.editableCustomer.password
        : this.customer.password;

      axiosapi
        .put(`/api/customers/${this.customer.username}`, {
          customerName: this.editableCustomer.customerName,
          password: passwordToUpdate,
          phoneNumber: this.editableCustomer.phoneNumber,
          mobileNumber: this.editableCustomer.mobileNumber,
        })
        .then(() => {
          Swal.fire({
            title: "會員資料更新成功！",
            icon: "success",
            timer: 1500,
            showConfirmButton: false,
          });
          this.isEditMode = false;

          // ✅ 1. 更新本地畫面
          this.customer.customerName = this.editableCustomer.customerName;
          this.customer.password =
            this.editableCustomer.password || this.customer.password;
          this.customer.phoneNumber = this.editableCustomer.phoneNumber;
          this.customer.mobileNumber = this.editableCustomer.mobileNumber;

          // ✅ 2. 同步更新 authStore.user & localStorage
          const authStore = useAuthStore();
          // 用 authStore.setUser() 把新名字等資料寫回 store & localStorage
          authStore.setUser({
            customerName: this.editableCustomer.customerName,
            // 其他欄位看你是否也要更新 store
          });
        })
        .catch((error) => {
          Swal.fire({
            title: "會員資料更新失敗，請稍後再試。",
            icon: "error",
            timer: 2000,
            showConfirmButton: false,
          });
          console.error(error);
        });
    },
    cancelChanges() {
      this.isEditMode = false;
      this.editableCustomer = {};
      this.confirmPassword = "";
      this.resetErrors();
    },
    resetErrors() {
      this.errors.password = "";
      this.errors.mobileNumber = "";
      this.errors.phoneNumber = "";
    },
  },
};
</script>


<style scoped>
/* 1. 外層容器：套用與 B 相似的外觀、置中方式 */
.customerList-container {
  width: 700px;
  height: 600px;
  margin: 50px auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center; /* 讓內容水平居中 */
}

/* 2. 內層容器：模擬 .login-content */
.customer-details {
  width: 80%;
}

/* 與 B 相同，標題底部加一點間距 */
.customer-details h1 {
  margin-bottom: 15px;
}

/* 3. 原本的 table 仍保留，但改成較簡潔的風格 */
.customer-table {
  width: 100%;
  height: 350px;
  border-collapse: collapse; /* 取消表格的預設間隙 */
  margin-top: 15px; /* 與標題區分 */
  margin-bottom: 15px; /* 與下方按鈕區分 */
}

.customer-table th,
.customer-table td {
  padding: 8px 0; /* 與 B 相似的上下內距 */
  border: none; /* 移除格線 */
  text-align: left; /* 與 B 相同，左對齊 */
  vertical-align: middle;
}

.customer-table th {
  font-weight: bold; /* B 中 label 的字體較粗 */
  width: 100px; /* 視需要可自行調整寬度 */
}

/* 4. 編輯模式下的輸入框：套用 B 類似的 input 風格 */
.editable-input {
  width: 90%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

/* 錯誤訊息提示樣式 (與 A 保持相同，或自行調整) */
.error-message {
  margin-top: 5px;
  color: red;
  font-size: 14px;
}

/* 5. 編輯狀態下的動作按鈕區域：模擬 B 的 form-actions */
.form-actions {
  display: flex;
  gap: 15px;
  margin-top: 20px;
}

/* 6. 按鈕風格：參考 B 的 .btn + .login-btn */
.btn-edit,
.btn-save,
.btn-cancel {
  flex: 1; /* 讓按鈕在同一行時自動分配空間 */
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  background-color: #b2bfae; /* 與 B 相同的灰綠色 */
  color: #fff;
}
</style>
