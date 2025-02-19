import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        user: null,
        token: null,
        customerName: null,
        customerId: null,
        adminId: null,
        adminAccount: null,
        isAuthenticated: false,
        cartId: null,
        orderId: null,
    }),
    actions: {
        initializeAuth() {
            const storedUser = localStorage.getItem("user");
            const storedToken = localStorage.getItem("token");
            const storedCustomerId = localStorage.getItem("customerId");
            const storedCartId = localStorage.getItem("cartId");
            const storedOrderId = localStorage.getItem("orderId");

            this.user = storedUser ? JSON.parse(storedUser) : null;
            this.token = storedToken || null;
            this.adminId = this.user ? this.user.adminId : null;
            this.adminAccount = this.user ? this.user.adminAccount : null;
            this.customerId = storedCustomerId ? Number(storedCustomerId) : null;
            this.cartId = storedCartId ? Number(storedCartId) : null;
            this.orderId = storedOrderId ? storedOrderId : null;
            this.customerName = this.user ? this.user.customerName : null;
            this.isAuthenticated = !!(this.user && this.token);
        },
        login(userData) {
            this.user = userData.user;
            this.token = userData.token;
            this.adminId = userData.adminId;
            this.adminAccount = userData.adminAccount;
            this.customerId = userData.customerId;
            this.customerName = userData.customerName;
            this.cartId = userData.cartId || null;
            this.isAuthenticated = true;

            localStorage.setItem("user", JSON.stringify(userData.user));
            localStorage.setItem("token", userData.token);
            localStorage.setItem("customerId", this.customerId);
            localStorage.setItem("cartId", this.cartId);
            localStorage.removeItem("orderId");
        },
        logout() {
            this.user = null;
            this.token = null;
            this.adminId = null;
            this.adminAccount = null;
            this.customerName = null;
            this.customerId = null;
            this.cartId = null;
            this.orderId = null;
            this.isAuthenticated = false;

            localStorage.removeItem("user");
            localStorage.removeItem("token");
            localStorage.removeItem("customerId");
            localStorage.removeItem("cartId");
            localStorage.removeItem("orderId");
            sessionStorage.removeItem("customerID");
        },
        // ✅ 新增這個方法：更新 store.user / store.customerName 並同步 localStorage
        setUser(updatedUser) {
            // 將原本的 user 與新資料合併
            this.user = { ...this.user, ...updatedUser };
            // 若 store 有獨立的 customerName，也更新
            this.customerName = this.user.customerName;

            // 寫回 localStorage，確保重新整理後也是最新
            localStorage.setItem("user", JSON.stringify(this.user));
        },

        setCartId(cartId) {
            this.cartId = cartId;
        },
        setOrderId(id) {
            this.orderId = id;
            localStorage.setItem("orderId", id);
        },
        persist: {
            enabled: true,
            strategies: [
                {
                    key: "auth",
                    storage: localStorage,
                    paths: ["user", "token", "isAuthenticated", "customerId", "cartId", "orderId"],
                },
            ],
        },
    },
});
