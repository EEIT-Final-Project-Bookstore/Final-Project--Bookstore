<template>
  <div class="login-container">
    <div class="login-content">
      <h1>會員登入</h1>
      <ul class="benefits">
        <li>森途書店之各項優惠及會員折扣，限網路訂書使用。</li>
      </ul>

      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="account">會員帳號</label>
          <input v-model="loginForm.account" type="text" id="account" placeholder="請輸入帳號" />
        </div>
        <div class="form-group">
          <label for="password">會員密碼</label>
          <input v-model="loginForm.password" type="password" id="password" placeholder="請輸入密碼" />
          <a href="http://192.168.23.112:5173/forget" class="forgot">忘記密碼</a>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn login-btn">登入</button>
          <button type="button" class="btn register-btn" @click="navigateToRegister">加入會員</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useAuthStore } from "@/stores/authStore"; // Pinia Store
import { useRouter } from "vue-router"; 
import axios from "axios";

export default {
  setup() {
    const authStore = useAuthStore();
    const router = useRouter();

    const loginForm = ref({
      account: "",
      password: "",
    });

    const handleLogin = async () => {
      try {
        const response = await axios.post("http://192.168.23.112:8080/api/customers/login", {
          username: loginForm.value.account,
          password: loginForm.value.password,
        });

        const data = response.data;
        if (data.success) {
          // 從後端取回 token、user 等資訊
          const token = data.token;
          const user = data.user; // email or username
          const customerName = data.customerName;
          const customerID = data.customerID;

          // 呼叫 Pinia 的 login()
          authStore.login({
            token,
            user,
            customerName,
            customerID,
          });

          // 登入後跳轉首頁
          router.push("/");
        } else {
          alert(data.message || "登入失敗，請稍後重試");
        }
      } catch (error) {
        console.error("登入時發生錯誤:", error);
        alert("登入失敗");
      }
    };

    const navigateToRegister = () => {
      router.push("/register");
    };

    return {
      loginForm,
      handleLogin,
      navigateToRegister,
    };
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
  