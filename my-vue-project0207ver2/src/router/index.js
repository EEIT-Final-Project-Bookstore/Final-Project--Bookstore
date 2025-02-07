import { createRouter, createWebHistory } from "vue-router";
import SearchResults from "../views/SearchResults.vue";
import BookDetail from "../views/BookDetail.vue";
import ManageImages from "../views/ManageImages.vue";
import Homepage from "../views/Homepage.vue";
import AdminDashboard from "../views/AdminDashboard.vue";
import PaymentPage from "@/components/PaymentPage.vue";
import ReviewManagement from "@/views/ReviewManagement.vue";
import NotificationManagement from "@/views/NotificationManagement.vue"; // 通知管理頁面
import Login_Customer from '@/views/Login_Customer.vue';
import Regist from '@/views/Regist.vue';
import ForgetPassword from '@/views/ForgetPassword.vue';
import List_Customer from '@/views/List_Customer.vue'
import Modify from '@/views/Modify.vue';
import List_Admin from '@/views/List_Admin.vue';
import Login_Admin from '@/views/Login_Admin.vue';
import Navbar from '@/views/Navbar.vue';



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
  }, { path: '/login', name: 'Login', component: Login_Customer },
  { path: '/register', name: 'Regist', component: Regist },
  { path: '/forget', name: 'ForgetPassword', component: ForgetPassword },
  { path: '/customers', component: List_Customer },
  { path: '/modify', component: Modify },
  { path: '/admins', component: List_Admin },
  { path: '/login/admin', component: Login_Admin },
  { path: '/navbar', component: Navbar },

];

const router = createRouter({
  history: createWebHistory('/'), // 替換 process.env.BASE_URL 為靜態字符串
  routes,
});

export default router;
