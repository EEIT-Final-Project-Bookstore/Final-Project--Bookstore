<template>
    <h2>森途書店 後台管理系統</h2>
    <h3>歡迎回來，{{ authStore.adminAccount }}</h3>
    <br>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover" @click="goTo('customer')">
          <h3>顧客管理</h3>
        </el-card>
      </el-col>
      <!-- <el-col :span="8">
        <el-card shadow="hover" @click="goTo('books')">
          <h3>書籍管理</h3>
        </el-card>
      </el-col> -->
      <el-col :span="8">
        <el-card shadow="hover" @click="goTo('coupons')">
          <h3>優惠券管理</h3>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" @click="goTo('notifications')">
          <h3>通知管理</h3>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" @click="goTo('reviews')">
          <h3>書籍評論管理</h3>
        </el-card>
      </el-col>
    </el-row>
</template>
  
<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from "@/stores/authStore";

const authStore = useAuthStore();
const router = useRouter()

const goTo = (page) => {
    router.push(`/admin/${page}`)
}
</script>
  
<style scoped>
h3 {
    text-align: center;
}
.el-card {
    text-align: center;
    font-size: 18px;
    cursor: pointer;
    transition: transform 0.2s ease-in-out;
}
.el-card:hover {
    transform: scale(1.05);
}
</style>
  