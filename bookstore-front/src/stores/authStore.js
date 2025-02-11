import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        user: null,
        token: null,
        customerName: null,
        customerId: null,
        isAuthenticated: false,
        cartId: null,  // 新增 cartId
        orderId: null, // 新增 orderId 屬性
    }),
    actions: {
        initializeAuth() {
            const storedUser = localStorage.getItem("user");
            const storedToken = localStorage.getItem("token");
            const storedCustomerId = localStorage.getItem("customerId");
            const storedCartId = localStorage.getItem("cartId");  // 讀取 cartId
            const storedOrderId = localStorage.getItem("orderId");  // 讀取 orderId

            this.user = storedUser ? JSON.parse(storedUser) : null;
            this.token = storedToken || null;
            this.customerId = storedCustomerId ? Number(storedCustomerId) : null; // 轉成數字，避免變成字串
            this.cartId = storedCartId ? Number(storedCartId) : null; // 讀取 cartId 並轉成數字
            this.orderId = storedOrderId ? storedOrderId : null; // 讀取 orderId
            this.customerName = this.user ? this.user.customerName : null;
            this.isAuthenticated = !!(this.user && this.token);
        },
        login(userData) {
            this.user = userData.user;
            this.token = userData.token;
            this.customerId = userData.customerId;
            this.customerName = userData.customerName;
            this.cartId = userData.cartId || null;  // 儲存 cartId
            this.isAuthenticated = true;

            localStorage.setItem("user", JSON.stringify(userData.user));
            localStorage.setItem("token", userData.token);
            localStorage.setItem("customerId", this.customerId);
            localStorage.setItem("cartId", this.cartId);  // 儲存 cartId
            localStorage.removeItem("orderId");  // 登入時清除 orderId
        },
        logout() {
            this.user = null;
            this.token = null;
            this.customerName = null;
            this.customerId = null;
            this.cartId = null;  // 清除 cartId
            this.orderId = null;  // 清除 orderId
            this.isAuthenticated = false;

            localStorage.removeItem("user");
            localStorage.removeItem("token");
            localStorage.removeItem("customerId");
            localStorage.removeItem("cartId");  // 移除 cartId
            localStorage.removeItem("orderId");  // 移除 orderId
        },
        setCartId(cartId) {  // 新增 cartId
            this.cartId = cartId;
        },
        setOrderId(id) { // 新增設定 orderId 的方法
            this.orderId = id;
            localStorage.setItem("orderId", id);  // 儲存 orderId
        },
        persist: {   // -> pinia-plugin-persistedstate，讓網頁重整後登入資料還在
            enabled: true,
            strategies: [
                {
                    key: "auth",
                    storage: localStorage, // 使用 localStorage 儲存登入資訊
                    paths: ["user", "token", "isAuthenticated", "customerId", "cartId", "orderId"],
                },
            ],
        }
    },
})

