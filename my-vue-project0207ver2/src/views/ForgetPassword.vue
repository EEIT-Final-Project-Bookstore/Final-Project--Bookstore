<template>
    <div class="login-container">
      <div class="login-content">
      <h2>忘記密碼</h2>
      <div v-if="step === 1">
        <div class="form-group">
          <label for="username">帳號</label>
          <input type="text" id="username" v-model="username" placeholder="請輸入帳號">
        </div>
        <div class="form-group">
          <label for="email">電子郵件</label>
          <input type="email" id="email" v-model="email" placeholder="請輸入電子郵件">
        </div>
        <div class="form-actions">
          <button class="btn login-btn" @click="nextStep">下一步</button>
        </div>
      </div>

      <div v-else-if="step === 2">
        <div class="form-group">
          <label for="captcha">驗證碼</label>
          <div class="captcha-container">
            <input type="text" id="captcha" v-model="captcha" placeholder="請輸入驗證碼">
            <button @click="refreshCaptcha">重新寄送驗證碼</button>
          </div>
        </div>
        <div class="form-actions">
          <button class="btn login-btn" @click="nextStep">下一步</button>
        </div>
      </div>

      <div v-else-if="step === 3">
        <div class="form-group">
          <label for="new-password">新密碼</label>
          <input type="password" id="new-password" v-model="newPassword" placeholder="請輸入新密碼">
        </div>
        <div class="form-group">
          <label for="confirm-password">確認密碼</label>
          <input type="password" id="confirm-password" v-model="confirmPassword" placeholder="請再次輸入新密碼">
        </div>
        <div class="form-actions">
          <button class="btn login-btn" @click="resetPassword">完成</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      step: 1,
      username: '',
      email: '',
      captcha: '',
      newPassword: '',
      confirmPassword: ''
    };
  },
  methods: {
    nextStep() {
      if (this.step === 1 && this.username && this.email) {
        this.step = 2;
      } else if (this.step === 2 && this.captcha) {
        this.step = 3;
      }
    },
    resetPassword() {
      if (this.newPassword === this.confirmPassword && this.newPassword) {
        alert('密碼修改成功！');
        // 清空資料並回到第一步
        this.step = 1;
        this.username = '';
        this.email = '';
        this.captcha = '';
        this.newPassword = '';
        this.confirmPassword = '';
      } else {
        alert('密碼不一致，請重新輸入！');
      }
    },
    refreshCaptcha() {
      alert('驗證碼已刷新！');
      // 這裡應該加入刷新驗證碼的邏輯
    }
  }
};
</script>
  
  <style scoped>
  .login-container {
    font-family: Arial, sans-serif;
    margin: 0 auto;
    padding: 20px;
    max-width: 800px;
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
  
  .nav-bar ul {
    list-style: none;
    display: flex;
    gap: 15px;
  }
  
  .nav-bar ul li a {
    text-decoration: none;
    color: #333;
  }
  
  .login-content h2 {
    color: #444;
    margin-bottom: 15px;
  }
  
  .benefits {
    margin-bottom: 20px;
    font-size: 14px;
    color: #555;
    list-style: disc;
    padding-left: 20px;
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
  
  .form-group .forgot {
    font-size: 12px;
    text-decoration: none;
    color: #007bff;
    float: right;
  }
  
  .captcha-container {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  
  .captcha-container img {
    height: 40px;
  }
  
  .captcha-container button {
    padding: 5px 10px;
    border: none;
    background-color: #007bff;
    color: #fff;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .form-actions {
    display: flex;
    gap: 15px;
    margin-top: 20px;
  }
  
  .form-actions .btn {
    flex: 1;
    padding: 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .login-btn {
    background-color: #007bff;
    color: #fff;
  }
  
  .register-btn {
    background-color: #28a745;
    color: #fff;
  }
  
  
  </style>
  