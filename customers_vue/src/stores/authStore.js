// stores/authStore.js
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

    // 2) getters: 移除 isAuthenticated，改用 state 屬性
    getters: {
        // ...其餘 getters
    },

    actions: {
        initializeAuth() {
            const storedUser = localStorage.getItem("user");
            const storedToken = localStorage.getItem("token");

            this.user = storedUser ? JSON.parse(storedUser) : null;
            this.token = storedToken || null;
            this.customerID = this.user ? this.user.customerID : null;
            this.customerName = this.user ? this.user.customerName : null;

            this.isAuthenticated = !!(this.user && this.token);
        },

        login(userData) {
            // userData 裏面會包含 { user, token, customerName, customerID, ... }
            this.user = userData.user;
            this.token = userData.token;
            this.customerID = userData.customerID;
            this.customerName = userData.customerName;
            this.isAuthenticated = true;

            // 將 user, token 寫入 localStorage
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
