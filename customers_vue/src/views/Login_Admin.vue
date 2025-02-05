<template>
    <div class="login-container">
      <div class="login-content">
        <h1>管理員登入</h1>
        <ul class="benefits">
          <li>松鼠 之各項優惠及會員折扣，限網路訂書使用。</li>
        </ul>
  
        <form @submit.prevent="handleLogin">
          <div class="form-group">
            <label for="account">管理員帳號</label>
            <input v-model="loginForm.account" type="text" id="account" placeholder="請輸入帳號">
          </div>
  
          <div class="form-group">
            <label for="password">管理員密碼</label>
            <input v-model="loginForm.password" type="password" id="password" placeholder="請輸入密碼">
          </div>
  
          <div class="form-actions">
            <button type="submit" class="btn login-btn">登入</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        loginForm: {
          account: '',
          password: '',
          captcha: '',
        },
      };
    },
    methods: {
      handleLogin() {
        alert(`登入帳號: ${this.loginForm.account}`);
      },
      reloadCaptcha() {
        alert('重新載入驗證碼');
      },
      navigateToRegister() {
        window.location.href = 'http://192.168.23.112:5173/register';
      },
    },
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
  