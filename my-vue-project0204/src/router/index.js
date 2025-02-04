import { createRouter, createWebHistory } from "vue-router";
import SearchResults from "../views/SearchResults.vue";
import BookDetail from "../views/BookDetail.vue";
import ManageImages from "../views/ManageImages.vue";
import Homepage from "../views/Homepage.vue";
import AdminDashboard from "../views/AdminDashboard.vue";
import PaymentPage from "@/components/PaymentPage.vue";
import ReviewManagement from "@/views/ReviewManagement.vue";
import NotificationManagement from "@/views/NotificationManagement.vue"; // 通知管理頁面


const routes = [
  {
    path: "/payment",
    name: "Payment",
    component: PaymentPage,
  },
  {
    path: "/results/:keyword",
    name: "SearchResults",
    component: SearchResults,
    props: true, // 將 keyword 作為參數傳遞給組件
  },
  {
    path: "/book/:id",
    name: "BookDetail",
    component: BookDetail,
    props: true, // 將 id 作為參數傳遞給組件
  },
  {
    path: "/admin/images",
    name: "ManageImages",
    component: ManageImages
  },
  {
    path: "/reviews", // 新增評論管理頁面路由
    name: "reviews",
    component: ReviewManagement,
  }, {
    path: "/notification-management",
    name: "notification-management",
    component: NotificationManagement,
  },
  {
    path: '/admin', name: 'AdminDashboard', component: AdminDashboard
  },

  {
    path: '/', name: 'Homepage', component: Homepage
  }
];

const router = createRouter({
  history: createWebHistory('/'), // 替換 process.env.BASE_URL 為靜態字符串
  routes,
});

export default router;
