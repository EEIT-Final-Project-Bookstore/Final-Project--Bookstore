<template>
  <div class="login-container">
    <div class="login-content">
      <h1>管理員登入</h1>

      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="account">管理員帳號</label>
          <input v-model="loginForm.account" type="text" id="account" placeholder="請輸入帳號" />
        </div>
        <div class="form-group">
          <label for="password">管理員密碼</label>
          <input v-model="loginForm.password" type="password" id="password" placeholder="請輸入密碼" />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn login-btn">登入</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useAuthStore } from "@/stores/authStore";
import { useRouter } from "vue-router";
import axios from "axios";
import Swal from "sweetalert2";

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
        const response = await axios.post("http://localhost:8080/api/admins/login", {
          username: loginForm.value.account,
          password: loginForm.value.password,
        });

        const data = response.data;
        if (data.success) {
          // 從後端取回 token、user 等資訊
          const token         = data.token;
          const user          = data.user;        // 包含 adminId, adminAccount, ...
          const adminId       = data.adminId;
          const adminAccount  = data.adminAccount;

          // 呼叫 Pinia 的 login()
          authStore.login({
            token,
            user,
            adminId,
            adminAccount,
          });

          Swal.fire({
            title: "登入成功",
            icon: "success",
            text: "即將轉跳至首頁",
          }).then(() => {
            router.push("/");
          });
        } else {
          Swal.fire({
            icon: "error",
            title: "登入失敗",
            text: data.message || "請檢查帳號密碼是否輸入正確。",
          });
        }
      } catch (error) {
        console.error("登入時發生錯誤:", error);
        Swal.fire({
          icon: "error",
          title: "登入失敗",
          text: "請檢查帳號密碼是否輸入正確。",
        });
      }
    };

    return {
      loginForm,
      handleLogin,
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

.login-content h1 {
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
