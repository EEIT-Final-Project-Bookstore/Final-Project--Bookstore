<template>
  <!-- 紅色框：登入/忘記密碼框整體範圍 -->
  <div class="login-container">
    <div class="login-content">
      <h2>忘記密碼</h2>

      <!-- Step 1: 帳號 + Email + 驗證碼 -->
      <div v-if="step === 1">
        <!-- 帳號 -->
        <div class="form-group">
          <label for="username">帳號</label>
          <input
            type="text"
            id="username"
            v-model="form.username"
            placeholder="請輸入帳號"
            @blur="checkUserEmail"
          />
        </div>
        <!-- Email -->
        <div class="form-group">
          <label for="email">電子郵件</label>
          <input
            type="email"
            id="email"
            v-model="form.email"
            placeholder="請輸入電子郵件"
            @blur="checkUserEmail"
          />
        </div>
        <!-- 錯誤訊息 -->
        <p v-if="errors.userEmail" class="error">{{ errors.userEmail }}</p>

        <!-- 寄送驗證碼按鈕 -->
        <div class="form-actions">
          <button type="button" class="btn login-btn" @click="sendCode" style="width: 150px;">
            寄送驗證碼
          </button>
          <span
            v-if="sendCodeMessage"
            :class="sendCodeError ? 'error' : 'black-text'"
            style="margin-left: 10px;"
          >
            {{ sendCodeMessage }}
          </span>
        </div>
        <br>
        <!-- 驗證碼輸入 -->
        <div class="form-group">
          <label for="captcha">驗證碼</label>
          <input
            type="text"
            id="captcha"
            v-model="form.captcha"
            placeholder="請輸入驗證碼"
          />
          <p class="error" v-if="errors.verifyCode">{{ errors.verifyCode }}</p>
        </div>

        <div class="form-actions">
          <button class="btn login-btn" type="button" @click="checkCaptcha">
            下一步
          </button>
        </div>
      </div>

      <!-- Step 2: 新密碼 -->
      <div v-else-if="step === 2">
        <div class="form-group">
          <label for="new-password">新密碼</label>
          <input
            type="password"
            id="new-password"
            v-model="form.newPassword"
            placeholder="請輸入新密碼"
            @input="validatePassword"
          />
        </div>
        <div class="form-group">
          <label for="confirm-password">確認密碼</label>
          <input
            type="password"
            id="confirm-password"
            v-model="form.confirmPassword"
            placeholder="請再次輸入新密碼"
            @input="validatePassword"
          />
          <p class="error" v-if="errors.password">{{ errors.password }}</p>
        </div>
        <div class="form-actions">
          <button class="btn login-btn" @click="resetPassword">完成</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      step: 1,
      form: {
        username: "",
        email: "",
        captcha: "",
        newPassword: "",
        confirmPassword: "",
      },
      errors: {
        userEmail: "",
        verifyCode: "",
        password: "",
      },
      sendCodeMessage: "",
      sendCodeError: false,
    };
  },
  methods: {
    // (1) 帳號 + Email 檢查
    async checkUserEmail() {
      this.errors.userEmail = "";
      const { username, email } = this.form;
      if (!username || !email) return;
      if (!email.includes("@")) {
        this.errors.userEmail = "電子郵件格式不正確";
        return;
      }
      try {
        // 檢查 email 是否存在
        const resEmail = await axios.get(
          `http://localhost:8080/api/customers/checkEmail?email=${email}`
        );
        if (!resEmail.data.exists) {
          this.errors.userEmail = "此 Email 不存在系統中";
          return;
        }
        // 檢查 username 是否存在
        const resUser = await axios.get(
          `http://localhost:8080/api/customers/${username}`
        );
        // 比對 email
        if (resUser.data.email !== email) {
          this.errors.userEmail = "帳號與 Email 不符";
        }
      } catch (err) {
        if (err.response && err.response.status === 404) {
          this.errors.userEmail = "查無此帳號";
        } else {
          console.error("checkUserEmail 錯誤:", err);
          this.errors.userEmail = "伺服器錯誤，請稍後再試";
        }
      }
    },

    // (2) 寄送驗證碼
    sendCode() {
      if (this.errors.userEmail) {
        this.sendCodeMessage = "請先修正帳號/Email錯誤後再發送驗證碼";
        this.sendCodeError = true;
        return;
      }
      if (!this.form.email) {
        this.sendCodeMessage = "請先輸入Email";
        this.sendCodeError = true;
        return;
      }
      this.sendCodeMessage = "";
      this.sendCodeError = false;
      axios
        .post("http://localhost:8080/api/auth/sendCode", {
          email: this.form.email,
        })
        .then(() => {
          this.sendCodeMessage = "驗證碼已寄出，請查看您的信箱。";
          this.sendCodeError = false;
        })
        .catch((err) => {
          console.error("寄送驗證碼錯誤:", err);
          this.sendCodeMessage = "驗證碼寄送失敗，請稍後再試。";
          this.sendCodeError = true;
        });
    },

    // (3) 驗證碼檢查
    checkCaptcha() {
      this.errors.verifyCode = "";
      if (!this.form.captcha) {
        this.errors.verifyCode = "請輸入驗證碼";
        return;
      }
      axios
        .post("http://localhost:8080/api/auth/verifyCode", {
          email: this.form.email,
          code: this.form.captcha,
        })
        .then(() => {
          this.step = 2; // 成功 -> 進入 Step2 輸入新密碼
        })
        .catch(() => {
          this.errors.verifyCode = "驗證失敗或已過期，請重新嘗試";
        });
    },

    // (4) 即時檢查密碼
    validatePassword() {
      this.errors.password = "";
      const { newPassword, confirmPassword } = this.form;
      if (newPassword && confirmPassword && newPassword !== confirmPassword) {
        this.errors.password = "新密碼與確認密碼不一致";
        return;
      }
      const passRegex = /^[A-Za-z0-9]{6,20}$/;
      if (newPassword && !passRegex.test(newPassword)) {
        this.errors.password = "密碼必須為 6~20 位英文或數字";
      }
    },

    // (5) 送出新密碼 -> 只改 password，不動其他欄位
    async resetPassword() {
      // 前端驗證
      if (this.errors.password) return;
      const { username, newPassword, confirmPassword } = this.form;
      if (!newPassword || !confirmPassword) {
        this.errors.password = "請輸入並確認新密碼";
        return;
      }
      if (newPassword !== confirmPassword) {
        this.errors.password = "新密碼與確認密碼不一致";
        return;
      }

      // 1) 取得該 user 舊資料
      let oldData;
      try {
        const getRes = await axios.get(`http://localhost:8080/api/customers/${username}`);
        oldData = getRes.data; // 舊資料
      } catch (err) {
        console.error("取得舊資料失敗:", err);
        Swal.fire({
          icon: "error",
          title: "錯誤",
          text: "查無此帳號或伺服器錯誤，無法更新密碼。",
        });
        return;
      }

      // 2) 只改 password
      const updatedData = { ...oldData, password: newPassword };

      // 3) 呼叫PUT更新
      try {
        await axios.put(`http://localhost:8080/api/customers/${username}`, updatedData);

        // === 修改成功後，使用 Swal.fire 彈窗 & 轉跳登入頁 ===
        Swal.fire({
          title: "修改成功",
          icon: "success",
          text: "即將轉跳到登入頁面",
        }).then(() => {
          // 彈窗關閉後執行跳轉
          this.$router.push("/login");
        });

        // 清空表單
        this.step = 1;
        Object.keys(this.form).forEach((key) => (this.form[key] = ""));
        this.sendCodeMessage = "";
        this.sendCodeError = false;
      } catch (err) {
        console.error("更新密碼失敗:", err);
        Swal.fire({
          icon: "error",
          title: "更新失敗",
          text: "更新密碼失敗，請稍後再試。",
        });
      }
    },
  },
};
</script>

<style scoped>
/* ========== 套用 B 的外觀，並保留 A 原本的錯誤訊息樣式 ========== */

/* 紅色框整體：可視需要自行調整寬度、高度與背景色 */
.login-container {
  width: 600px; /* 模擬紅色框的寬度 */
  height: 450px; /* 模擬紅色框的高度 */
  margin: 50px auto; /* 置中 */
  background-color: #fff; 
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);

  /* 讓內容能置中排列 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-content {
  width: 80%; /* 內部內容距離外框邊距 */
}

/* 讓 h2 與 B 的 h1 保持類似間距 */
.login-content h2 {
  margin-bottom: 15px;
}

/* form-group 與其 label、input */
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}
.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

/* 操作區域 (按鈕) */
.form-actions {
  display: flex;
  gap: 15px;
  margin-top: 20px;
}

/* 按鈕共用樣式 */
.btn {
  flex: 1; /* 佔滿該列 */
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* 登入(或送出)按鈕外觀，模擬 B 的灰綠色 */
.login-btn {
  background-color: #b2bfae;
  color: #fff;
  width: 470px;
}

/* 錯誤文字：紅色 */
.error {
  margin-top: 5px;
  color: #dc3545;
}
/* 提示文字：黑色 */
.black-text {
  color: #000;
}
</style>