import { createRouter, createWebHistory } from "vue-router";
import PaymentPage from "@/views/PaymentPage.vue";

const routes = [
  {
    path: "/payment",
    name: "Payment",
    component: PaymentPage,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;