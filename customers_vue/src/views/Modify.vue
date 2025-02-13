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
              <!-- 未進入編輯模式時顯示原密碼 -->
              <span v-if="!isEditMode">{{ customer.password }}</span>

              <!-- 編輯模式時顯示「新密碼 / 確認密碼」欄位，預設為空 -->
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
                <!-- 密碼錯誤訊息 -->
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
                {{ errors.phoneNumber }}</div>
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
                <!-- 手機錯誤訊息 -->
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
import axios from "axios";
import { useAuthStore } from "@/stores/authStore";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";

export default {
  name: "CustomerDetails",
  data() {
    return {
      isEditMode: false,
      // 初始的 customer 資料
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

    // 如果 store 尚未初始化，先初始化
    authStore.initializeAuth();

    // 確認是否已登入，且是否有 username
    if (!authStore.isAuthenticated || !authStore.user || !authStore.user.username) {
      alert("您尚未登入或無效的使用者資訊，請先登入。");
      router.push("/login"); // 導回登入頁
      return;
    }

    // 取得登入者的 username
    const loggedInUsername = authStore.user.username;
    this.fetchCustomerData(loggedInUsername);
  },
  methods: {
    // 根據 username 從後端獲取會員資料
    fetchCustomerData(username) {
      axios
        .get(`http://192.168.23.112:8080/api/customers/${username}`)
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
      if (this.isEditMode) {
        // 複製 customer 但不帶入舊密碼，確保新密碼欄位一開始是空的
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
    // 保存修改
    saveChanges() {
      // 先清空錯誤訊息
      this.resetErrors();
      let hasError = false;

      // -----------------------
      // (1) 檢查「新密碼」欄位
      // -----------------------
      // 只有在使用者輸入了新密碼時，才做以下檢查
      if (this.editableCustomer.password) {
        // (A) 新密碼 與 確認新密碼 是否一致
        if (this.editableCustomer.password !== this.confirmPassword) {
          this.errors.password = "新密碼與確認密碼不一致";
          hasError = true;
        } else {
          // (B) 新密碼格式檢查：6~20碼英文或數字
          const passwordRegex = /^[A-Za-z0-9]{6,20}$/;
          if (!passwordRegex.test(this.editableCustomer.password)) {
            this.errors.password = "密碼必須為6-20字的英文或數字";
            hasError = true;
          }
        }
      }

      // -----------------------
      // (2) 檢查「手機號碼」欄位
      // -----------------------
      // 只有當有更動手機號碼時，才做以下檢查
      if (
        this.editableCustomer.phoneNumber &&
        this.editableCustomer.phoneNumber !== this.customer.phoneNumber
      ) {
        const phoneRegex = /^0\d{7,9}$/;
        if (!phoneRegex.test(this.editableCustomer.phoneNumber)) {
          this.errors.phoneNumber = "市內電話格式不正確：必須以 0 開頭，並且總長 8 到 10 碼";
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

      // 若有任何錯誤，不進行 PUT 請求
      if (hasError) {
        return;
      }

      // -----------------------
      // (3) 若通過檢查，準備送出 PUT
      // -----------------------
      // 若有輸入新密碼，則進行 Base64 編碼，否則維持原密碼
      const encodedPassword = this.editableCustomer.password
        ? btoa(this.editableCustomer.password)
        : this.customer.password;

      axios
        .put(`http://192.168.23.112:8080/api/customers/${this.customer.username}`, {
          customerName: this.editableCustomer.customerName,
          password: encodedPassword,
          phoneNumber: this.editableCustomer.phoneNumber,
          mobileNumber: this.editableCustomer.mobileNumber,
        })
        .then(() => {
          Swal.fire({ title: "會員資料更新成功！", icon: "success", timer: 1500, showConfirmButton: false });
          this.isEditMode = false;
          // 同步更新前端顯示
          this.customer.customerName = this.editableCustomer.customerName;
          this.customer.password =
            this.editableCustomer.password || this.customer.password;
          this.customer.phoneNumber = this.editableCustomer.phoneNumber;
          this.customer.mobileNumber = this.editableCustomer.mobileNumber;
        })
        .catch((error) => {
          Swal.fire({ title: "會員資料更新失敗，請稍後再試。", icon: "error", timer: 2000, showConfirmButton: false });
        });
    },
    // 取消修改
    cancelChanges() {
      this.isEditMode = false;
      this.editableCustomer = {};
      this.confirmPassword = "";
      this.resetErrors();
    },
    // 重置錯誤訊息
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
  margin-top: 15px;          /* 與標題區分 */
  margin-bottom: 15px;       /* 與下方按鈕區分 */
}

.customer-table th,
.customer-table td {
  padding: 8px 0;            /* 與 B 相似的上下內距 */
  border: none;              /* 移除格線 */
  text-align: left;          /* 與 B 相同，左對齊 */
  vertical-align: middle;
}

.customer-table th {
  font-weight: bold;         /* B 中 label 的字體較粗 */
  width: 100px;              /* 視需要可自行調整寬度 */
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
  flex: 1;                  /* 讓按鈕在同一行時自動分配空間 */
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  background-color: #b2bfae; /* 與 B 相同的灰綠色 */
  color: #fff;
}

</style>
