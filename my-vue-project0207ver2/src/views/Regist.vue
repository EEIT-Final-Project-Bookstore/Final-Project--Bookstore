<template>
  <div class="container">
    <h1>會員專區 / 加入會員</h1>
    <div class="instructions">
      <p>
        本交易為附買賣條件，依動產擔保交易法第三章之規定，在貨款未付清或票據未兌現償付之前，標的物之所有權仍歸屬本公司所有，買受人無異議同意，本公司無須經法律程序隨時取回本貨品或代物清償。
      </p>
      <p>歡迎加入 松鼠 會員，請提供我們您的下列資料</p>
    </div>

    <form @submit.prevent="submitForm">
      <div v-if="step === 1">
        <div class="form-group">
          <label for="email">電子信箱</label>
          <input
            ref="emailInput"
            v-model="form.email"
            type="email"
            id="email"
            placeholder="請輸入電子信箱"
            @blur="handleBlur"
            @focus="handleFocus"
            required
          />
          <p v-if="emailMessage" :class="{ error: emailExists || emailInvalid, success: !emailExists && !emailInvalid }">
            {{ emailMessage }}
          </p>
          <button 
            type="button" 
            @click="verify" 
            :disabled="emailInvalid || emailExists || !form.email">
            發送驗證碼
          </button>
        </div>

        <div class="form-group">
          <label for="captcha">驗證碼</label>
          <div class="captcha">
            <input
              v-model="form.captcha"
              type="text"
              id="captcha"
              placeholder="請輸入驗證碼"
              required
            />
          </div>
        </div>

        <div class="btn-group">
          <button type="button" class="btn-submit" @click="nextStep">下一步</button>
        </div>
      </div>

      <div v-else-if="step === 2">
        <div class="form-group">
          <label for="username">帳號</label>
          <input
            v-model="form.username"
            type="text"
            id="username"
            placeholder="請輸入 6 - 20 字的英文和數字組合"
            required
          />
          <button type="button" @click="checkUsername">檢查帳號</button>
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
        </div>

        <div class="btn-group">
          <button type="button" class="btn-submit" @click="nextStep">下一步</button>
        </div>
      </div>

      <div v-else-if="step === 3">
        <div class="form-group">
          <label for="phoneNumber">市內電話</label>
          <input
            v-model="form.phoneNumber"
            type="text"
            id="phoneNumber"
            placeholder="請輸入市內電話"
          />
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
        </div>

        <div class="btn-group">
          <button type="submit" class="btn-submit" :disabled="!form.terms">送出</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      step: 1,
      form: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        captcha: '',
        phoneNumber: '',
        mobileNumber: '',
        terms: false,
      },
      emailMessage: '',
      emailExists: false,
      emailInvalid: false,
    };
  },
  methods: {
    nextStep() {
      if (this.step === 1 && this.form.email && this.form.captcha) {
        this.step = 2;
      } else if (this.step === 2 && this.form.username && this.form.password && this.form.confirmPassword) {
        this.step = 3;
      }
    },
    submitForm() {
      alert('表單已送出');
    },
    verify() {
      alert('驗證碼已發送');
    },
    checkUsername() {
      alert('帳號檢查中');
    },
    handleBlur() {
      this.emailInvalid = !this.form.email.includes('@');
      this.emailMessage = this.emailInvalid ? '電子郵件格式不正確' : '電子郵件格式正確';
    },
    handleFocus() {
      this.emailMessage = '';
    },
  },
};
</script>

<style scoped>
.container {
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
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
  background-color: #007bff;
  color: #fff;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.error {
  color: #dc3545;
}
.success {
  color: #28a745;
}
</style>
