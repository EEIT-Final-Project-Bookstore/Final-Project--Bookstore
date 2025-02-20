import { defineStore } from "pinia";

export const useAdminStore = defineStore("admin", {
    state: () => ({
        user: null,
        token: null,
        adminId: null,
        adminAccount: null,
        isAuthenticated: false,
    }),

    actions: {
        initializeAuth() {
            const storedUser = localStorage.getItem("user");
            const storedToken = localStorage.getItem("token");

            this.user = storedUser ? JSON.parse(storedUser) : null;
            this.token = storedToken || null;
            this.adminId = this.user ? this.user.adminId : null;
            this.adminAccount = this.user ? this.user.adminAccount : null;
            this.isAuthenticated = !!(this.user && this.token);
        },

        login(userData) {
            this.user = userData.user;
            this.token = userData.token;
            this.adminId = userData.adminId;
            this.adminAccount = userData.adminAccount;
            this.isAuthenticated = true;

            localStorage.setItem("user", JSON.stringify(userData.user));
            localStorage.setItem("token", userData.token);
        },

        logout() {
            this.user = null;
            this.token = null;
            this.adminId = null;
            this.adminAccount = null;
            this.isAuthenticated = false;

            localStorage.removeItem("user");
            localStorage.removeItem("token");
        },
    },
});