// authStore.js (範例完整可行版本)
import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
    // 1) state: 新增 isAuthenticated 做為布林值，預設 false
    state: () => ({
        user: null,
        token: null,
        customerName: null,
        customerID: null,
        isAuthenticated: false,
    }),

    // 2) 移除 "isAuthenticated" 這個 getter
    getters: {
        // ...（如果有其他 getter，留在這裡，但不要再有 isAuthenticated）
    },

    actions: {
        initializeAuth() {
            const storedUser = localStorage.getItem("user");
            const storedToken = localStorage.getItem("token");

            this.user = storedUser ? JSON.parse(storedUser) : null;
            this.token = storedToken || null;
            this.customerID = this.user ? this.user.customerID : null;
            this.customerName = this.user ? this.user.customerName : null;

            // 因為我們現在用 state 裡的 isAuthenticated
            // 可自行判斷是否要在這裡設定
            this.isAuthenticated = !!(this.user && this.token);
        },

        login(userData) {
            this.user = userData.user;
            this.token = userData.token;
            this.customerID = userData.customerID;
            this.customerName = userData.customerName;

            // 這裡可以直接 set，因為現在 isAuthenticated 已是 state
            this.isAuthenticated = true;

            localStorage.setItem("user", JSON.stringify(userData.user));
            localStorage.setItem("token", userData.token);
        },

        logout() {
            this.user = null;
            this.token = null;
            this.customerName = null;
            this.customerID = null;
            this.isAuthenticated = false;

            localStorage.removeItem("user");
            localStorage.removeItem("token");
        },
    },
});
