import { createRouter, createWebHistory } from "vue-router";
import PaymentPage from "@/views/PaymentPage.vue";
import ReviewManagement from "@/views/ReviewManagement.vue"; // 引入 ReviewManagement.vue

const routes = [
  {
    path: "/payment",
    name: "Payment",
    component: PaymentPage,
  },
  {
    path: "/reviews", // 新增評論管理頁面路由
    name: "reviews",
    component: ReviewManagement,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
