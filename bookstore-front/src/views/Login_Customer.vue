<template>
  <div class="login-container">
    <div class="login-content">
      <h1>會員登入</h1>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <span style="font-size: 1rem">
            還不是森途書店的會員嗎？
            <router-link
              :to="{ name: 'Regist' }"
              class="regist"
              style="margin-left: 6px; font-size: 16px;"
              >加入會員</router-link>
          </span>
          <p></p>
          <label for="account">會員帳號</label>
          <input
            v-model="loginForm.account"
            type="text"
            id="account"
            placeholder="請輸入帳號"
            style="width: 450px;"
          />
        </div>
        <div class="form-group">
          <label for="password">會員密碼</label>
          <input
            v-model="loginForm.password"
            type="password"
            id="password"
            placeholder="請輸入密碼"
            style="width: 450px;"
          />
          <router-link to="/forgot" class="forgot" >忘記密碼</router-link>
        </div>
        <br>
        <div class="form-actions">
          <button type="submit" class="btn login-btn">登入</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useAuthStore } from "@/stores/authStore"; // Pinia Store
import { useCartStore } from "@/stores/cartStore";
import { useRouter } from "vue-router"; 
import axiosapi from "@/plugins/axios";
import Swal from "sweetalert2";

export default {
  setup() {
    const authStore = useAuthStore();
    const cartStore = useCartStore();
    const router = useRouter();

    const loginForm = ref({
      account: "",
      password: "",
    });

    const handleLogin = async () => {
		try {
			const response = await axiosapi.post("/api/customers/login", {
			username: loginForm.value.account,
			password: loginForm.value.password,
			}, { withCredentials: true });

			const data = response.data;
			console.log("後端回傳資料:", data);
			if (data.success) {
			// 從後端取回 token、user 等資訊
			const token = data.token;
			const user = data.user; // data.user 裡面需包含 username
			const customerName = data.customerName;
			const customerId = data.customerID;  //後端(data.)是customerID

			// 呼叫後端獲取 cartId (後來增加)
			const cartResponse = await axiosapi.get(`/api/cart/${customerId}`);
			const cartId = cartResponse.data;

			// 呼叫 Pinia 的 login()
			authStore.login({
				token,
				user,
				customerName,
				customerId,
				cartId,  // 設定 cartId
			});

			sessionStorage.setItem("customerID", customerId);

			// 登入後觸發購物車數據更新
			await cartStore.fetchCartItems();  // 更新購物車項目數量

			// 顯示登入成功提示
			Swal.fire({
				title: "登入成功",
				icon: "success",
				text: "即將轉跳至首頁",
			}).then(() => {
				// 登入後跳轉首頁
				router.push("/");
			});
			} else {
			// 後端回傳 success = false
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
	width: 600px; /* 模擬紅色框的寬度 */
	height: 400px; /* 模擬紅色框的高度 */
	margin: 50px auto; /* 置中 */
	background-color: #fff; /* 淺綠灰色背景，您可換成喜歡的顏色 */
	border-radius: 8px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	display: flex;
	align-items: center;
	justify-content: center;
}

.login-content {
  	width: 80%; /* 內部內容距離外框邊距 */
}

.login-content h1 {
  	margin-bottom: 15px;
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
	color: #b2bfae;
	float: left;
}

.regist {
	font-size: 0.8rem;
	text-decoration: none;
	color: #B2BFAE;
	/* 如果需要額外間距或效果，在此調整 */
}

.form-actions {
	display: flex;
	gap: 15px;
	margin-top: 20px;
}

.btn {
	flex: 1;
	padding: 10px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.login-btn {
	background-color: #b2bfae;
	color: #fff;
	width: 470px;
}
</style>
  