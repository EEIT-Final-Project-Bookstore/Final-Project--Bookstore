import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        user: JSON.parse(localStorage.getItem("user")) || null,
        token: localStorage.getItem("token") || null,
    }),

    getters: {
        isAuthenticated: (state) => !!state.token,
    },

    actions: {
        login(userData) {
            this.user = userData.user;
            this.token = userData.token;

            // 存入 localStorage，確保登入狀態持續
            localStorage.setItem("user", JSON.stringify(userData.user));
            localStorage.setItem("token", userData.token);
        },

        logout() {
            this.user = null;
            this.token = null;

            // 清除 localStorage
            localStorage.removeItem("user");
            localStorage.removeItem("token");
        },
    },
});
