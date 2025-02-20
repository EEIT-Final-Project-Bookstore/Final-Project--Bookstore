<template>
  <div class="container">
    <h1>會員專區 / 加入會員</h1>
    <div class="instructions">
      <p>
        本交易為附買賣條件，依動產擔保交易法第三章之規定，在貨款未付清或票據未兌現償付之前，標的物之所有權仍歸屬本公司所有，買受人無異議同意，本公司無須經法律程序隨時取回本貨品或代物清償。
      </p>
      <p>歡迎加入森途書店會員，請提供我們您的下列資料</p>
    </div>

    <form @submit.prevent="submitForm">
      <!-- Step 1: 輸入信箱、發送驗證碼 & 輸入驗證碼 -->
      <div v-if="step === 1">
        <div class="form-group">
          <label for="email">電子信箱</label>
          <input
            style="width: 500px"
            ref="emailInput"
            v-model="form.email"
            type="email"
            id="email"
            placeholder="請輸入電子信箱"
            @blur="handleEmailBlur"
            @focus="handleFocus"
            required
          />
          <!-- 顯示 email 檢查結果 -->
          <p
            v-if="emailMessage"
            :class="{
              error: emailInvalid || emailExists,
              success: !emailInvalid && !emailExists,
            }"
          >
            {{ emailMessage }}
          </p>

          <!-- 按下按鈕 -> 呼叫後端 /api/auth/sendCode -->
          <div style="display: flex; align-items: center; gap: 10px">
            <button
              type="button"
              @click="sendCode"
              :disabled="emailInvalid || emailExists || !form.email"
            >
              發送驗證碼
            </button>
            <!-- 發送成功或失敗的訊息，成功則黑色文字，失敗則紅色 -->
            <span v-if="sendCodeMessage" :class="sendCodeError ? 'error' : 'black-text'">
              {{ sendCodeMessage }}
            </span>
          </div>
        </div>

        <div class="form-group">
          <label for="captcha">驗證碼</label>
          <div class="captcha">
            <input
              style="width: 500px"
              v-model="form.captcha"
              type="text"
              id="captcha"
              placeholder="請輸入驗證碼"
              required
            />
          </div>
          <!-- 驗證碼錯誤訊息顯示在此 -->
          <p class="error" v-if="errors.verifyCode">{{ errors.verifyCode }}</p>
        </div>

        <div class="btn-group">
          <button type="button" class="btn-submit" @click="nextStep">下一步</button>
        </div>
      </div>

      <!-- Step 2: 帳號密碼檢查 -->
      <div v-else-if="step === 2">
        <div class="form-group">
          <label for="username">帳號</label>
          <input
            v-model="form.username"
            type="text"
            id="username"
            placeholder="請輸入 6 - 20 字的英文和數字組合"
            required
            @blur="handleUsernameBlur"
          />
          <p
            v-if="usernameCheckMessage"
            :class="{ error: !usernameValid, success: usernameValid }"
          >
            {{ usernameCheckMessage }}
          </p>
        </div>

        <div class="form-group">
          <label for="password">密碼</label>
          <input
            v-model="form.password"
            type="password"
            id="password"
            placeholder="請輸入 6 - 20 字的英文和數字組合"
            required
          />
        </div>

        <div class="form-group">
          <label for="confirm-password">確認密碼</label>
          <input
            v-model="form.confirmPassword"
            type="password"
            id="confirm-password"
            placeholder="請再次輸入密碼"
            required
          />
          <!-- 密碼錯誤訊息 即時顯示 -->
          <p class="error" v-if="errors.password">{{ errors.password }}</p>
        </div>

        <div class="btn-group">
          <button type="button" class="btn-submit" @click="nextStep">下一步</button>
        </div>
      </div>

      <!-- Step 3: 填寫姓名、連絡電話等資料，送出表單 -->
      <div v-else-if="step === 3">
        <div class="form-group">
          <label for="customerName">姓名</label>
          <input
            v-model="form.customerName"
            type="text"
            id="customerName"
            placeholder="請輸入您的姓名"
            required
          />
        </div>

        <div class="form-group">
          <label for="phoneNumber">市內電話（非必填）</label>
          <input
            v-model="form.phoneNumber"
            type="text"
            id="phoneNumber"
            placeholder="請輸入市內電話"
          />
          <!-- 市內電話錯誤訊息 -->
          <p class="error" v-if="errors.phoneNumber">{{ errors.phoneNumber }}</p>
        </div>

        <div class="form-group">
          <label for="mobileNumber">手機號碼</label>
          <input
            v-model="form.mobileNumber"
            type="text"
            id="mobileNumber"
            placeholder="請輸入手機號碼"
            required
          />
          <!-- 手機號碼錯誤訊息 (即時或送出時都會顯示) -->
          <p class="error" v-if="errors.mobileNumber">{{ errors.mobileNumber }}</p>
        </div>

        <div class="btn-group">
          <!-- 當手機號碼不符規格時，按鈕會灰掉、不可點 -->
          <button type="submit" class="btn-submit" :disabled="!isMobileNumberValid">
            送出
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      step: 1,
      form: {
        username: "",
        password: "",
        confirmPassword: "",
        email: "",
        captcha: "",
        phoneNumber: "",
        mobileNumber: "",
        customerName: "",
      },
      // Email 狀態
      emailMessage: "",
      emailInvalid: false,
      emailExists: false,
      // 帳號檢查
      usernameCheckMessage: "",
      usernameValid: false,
      // 發送驗證碼提示
      sendCodeMessage: "",
      sendCodeError: false,
      // 錯誤訊息集中管理
      errors: {
        verifyCode: "",
        password: "",
        phoneNumber: "",
        mobileNumber: "",
      },
    };
  },
  computed: {
    // 只有手機號碼格式正確時，才能按「送出」
    isMobileNumberValid() {
      const mobile = this.form.mobileNumber.trim();
      return /^09\d{8}$/.test(mobile);
    },
  },
  watch: {
    // 密碼 / 確認密碼 即時檢查
    "form.password"() {
      this.validatePassword();
    },
    "form.confirmPassword"() {
      this.validatePassword();
    },
    // 即時監聽手機
    "form.mobileNumber"(newVal) {
      const mobile = newVal.trim();
      if (!mobile) {
        this.errors.mobileNumber = "請輸入手機號碼";
      } else if (!/^09\d{8}$/.test(mobile)) {
        this.errors.mobileNumber = "手機號碼格式不正確：必須以09開頭，總長10碼";
      } else {
        this.errors.mobileNumber = "";
      }
    },
    // ✅ 即時監聽市內電話
    "form.phoneNumber"(newVal) {
      const phone = newVal.trim();
      if (!phone) {
        // 若空白就不顯示錯誤，因為市話非必填
        this.errors.phoneNumber = "";
      } else if (!/^0\d{7,9}$/.test(phone)) {
        this.errors.phoneNumber =
          "市內電話格式不正確：必須以 0 開頭，並且總長 8 到 10 碼";
      } else {
        this.errors.phoneNumber = "";
      }
    },
  },
  methods: {
    // Step 1: Email 驗證邏輯
    handleEmailBlur() {
      this.errors.verifyCode = "";
      this.sendCodeMessage = "";
      this.sendCodeError = false;

      const email = this.form.email.trim();
      if (!email) {
        this.emailInvalid = false;
        this.emailExists = false;
        this.emailMessage = "";
        return;
      }

      // 檢查 '@'
      if (!email.includes("@")) {
        this.emailInvalid = true;
        this.emailExists = false;
        this.emailMessage = "電子郵件格式不正確";
        return;
      } else {
        this.emailInvalid = false;
      }

      // 檢查後端 Email 是否重複
      axiosapi
        .get(`/api/customers/checkEmail?email=${email}`)
        .then((res) => {
          if (res.data.exists) {
            this.emailExists = true;
            this.emailMessage = "此 Email 已被註冊";
          } else {
            this.emailExists = false;
            this.emailMessage = "電子郵件格式正確，可使用";
          }
        })
        .catch((error) => {
          console.error(error);
          this.emailInvalid = false;
          this.emailExists = false;
          this.emailMessage = "無法檢查信箱(可能路徑錯誤或CORS問題)，請稍後再試。";
        });
    },
    handleFocus() {
      this.emailMessage = "";
    },
    sendCode() {
      if (this.emailInvalid || this.emailExists) return;
      this.sendCodeMessage = "";
      this.sendCodeError = false;

      axiosapi
        .post("/api/auth/sendCode", { email: this.form.email })
        .then(() => {
          this.sendCodeMessage = "驗證碼已寄出，請查看您的信箱。";
          this.sendCodeError = false;
        })
        .catch((error) => {
          console.error(error);
          this.sendCodeMessage = "驗證碼寄送失敗，請確認信箱是否正確，或稍後再試。";
          this.sendCodeError = true;
        });
    },

    // Step 1 → Step 2 or Step 2 → Step 3
    nextStep() {
      if (this.step === 1) {
        // 檢查驗證碼
        this.errors.verifyCode = "";
        if (this.form.email && this.form.captcha) {
          axiosapi
            .post("/api/auth/verifyCode", {
              email: this.form.email,
              code: this.form.captcha,
            })
            .then(() => {
              this.step = 2;
            })
            .catch(() => {
              this.errors.verifyCode =
                "驗證失敗，請查看驗證碼是否逾期並重新輸入正確驗證碼。";
            });
        }
      } else if (this.step === 2) {
        // 帳號與密碼格式檢查
        if (!this.form.username) {
          this.usernameCheckMessage = "請輸入帳號";
          return;
        }
        if (!this.usernameValid) {
          this.usernameCheckMessage = "請先確認帳號可用(格式正確且未重複)";
          return;
        }
        // 密碼欄位是否填寫
        if (!this.form.password || !this.form.confirmPassword) {
          this.errors.password = "請填寫完整密碼欄位";
          return;
        }
        // 如果目前已有錯誤(如與確認密碼不一致)，阻擋
        if (this.errors.password) return;
        this.step = 3;
      }
    },

    // Step 2: 檢查帳號
    handleUsernameBlur() {
      const username = this.form.username.trim();
      if (!username) {
        this.usernameCheckMessage = "";
        this.usernameValid = false;
        return;
      }
      // 檢查格式：6~20 位英文或數字
      const regex = /^[A-Za-z0-9]{6,20}$/;
      if (!regex.test(username)) {
        this.usernameCheckMessage = "帳號格式不符合 (請輸入 6~20 位英文或數字)";
        this.usernameValid = false;
        return;
      }
      axiosapi
        .get(`/api/customers/checkUsername?username=${username}`)
        .then((res) => {
          if (res.data.exists) {
            this.usernameCheckMessage = "此帳號已存在，請更換新帳號。";
            this.usernameValid = false;
          } else {
            this.usernameCheckMessage = "此帳號可以使用。";
            this.usernameValid = true;
          }
        })
        .catch((err) => {
          console.error(err);
          this.usernameCheckMessage = "目前無法檢查帳號，請稍後再試。";
          this.usernameValid = false;
        });
    },

    // 密碼即時檢查 (含格式 & 與確認密碼是否一致)
    validatePassword() {
      let errorMsg = "";
      // (1) 先檢查密碼格式
      const passRegex = /^[A-Za-z0-9]{6,20}$/;
      if (this.form.password && !passRegex.test(this.form.password)) {
        errorMsg += "密碼格式不正確 (請輸入 6~20 位英文或數字)。 ";
      }
      // (2) 檢查「密碼與確認密碼」是否一致
      if (
        this.form.password &&
        this.form.confirmPassword &&
        this.form.password !== this.form.confirmPassword
      ) {
        errorMsg += "密碼與確認密碼不一致。";
      }
      this.errors.password = errorMsg.trim();
    },

    // Step 3: 最終送出
    submitForm() {
      axiosapi
        .post("/api/customers", {
          username: this.form.username,
          password: this.form.password,
          email: this.form.email,
          phoneNumber: this.form.phoneNumber,
          mobileNumber: this.form.mobileNumber,
          customerName: this.form.customerName,
        })
        .then(async (res) => {
          console.log("🟢 註冊成功，回傳的客戶資料：", res.data);
          console.log("🔵 嘗試發送歡迎通知，customerID:", res.data.customerID);
          // ✅ 確保 customerID 來自後端回應
          const customerID = res.data.customerID;

          // **確認 customerID 是否有值**
          if (!customerID) {
            console.error("❌ 註冊成功但 customerID 為空，無法發送通知！");
            return;
          }

          // **發送通知**
          await this.sendWelcomeNotification(customerID);

          Swal.fire({
            title: "帳號申請成功",
            icon: "success",
            text: "已自動發送1張優惠券，請至「我的優惠券」查看。",
          });

          this.$router.push("/login");
        })
        .catch((err) => {
          console.error("❌ 新增客戶失敗:", err);
          Swal.fire({
            icon: "error",
            title: "帳號申請失敗",
            text: "請檢查資料格式是否正確，並重新送出",
          });
        });
    },
    async sendWelcomeNotification(customerID) {
      if (!customerID) {
        console.error("❌ 無法發送通知，customerID 為空！");
        return;
      }

      try {
        const payload = {
          notificationType: { notificationTypeID: 3 },
          customer: { customerID: customerID }, // ✅ 這裡用動態值
          content: "🎉 歡迎加入森途書店會員！祝您購書愉快 📚",
        };

        console.log("📢 發送通知 API，內容：", JSON.stringify(payload, null, 2));

        await axiosapi.post("/admin/notifications/create", payload);
        console.log("✅ 成功發送歡迎通知");
      } catch (error) {
        console.error("❌ 發送歡迎通知失敗，完整錯誤資訊：", error);
      }
    },
  },
};
</script>

<style scoped>
.container {
  height: 600px;
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  align-items: center;
  justify-content: center;
}
.instructions {
  margin-bottom: 20px;
  font-size: 14px;
  color: #555;
}
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
.btn-group {
  display: flex;
  gap: 10px;
}
.btn-submit {
  background-color: #b2bfae;
  color: #fff;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
/* 當按鈕 disabled 時，改為灰色、禁止游標 */
.btn-submit:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

/* 錯誤文字：紅色 */
.error {
  color: #dc3545;
  margin-top: 5px;
}
/* 成功訊息改為黑色 */
.black-text {
  color: #000;
}
/* 綠色訊息（若有需要） */
.success {
  color: #28a745;
}
</style>
