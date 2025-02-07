<script setup>
import { useAuthStore } from "@/store/authStore";

const authStore = useAuthStore();

const logout = () => {
  authStore.logout();
};
</script>

<template>
  <nav>
    <span v-if="authStore.isAuthenticated">
      歡迎, {{ authStore.customerName }}
      <button @click="logout">登出</button>
    </span>
    <span v-else>
      <router-link to="/login">登入</router-link>
    </span>
  </nav>
</template>
