import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/authStore.js";
//前台-登入&顧客:
import Home from '@/views/Home.vue';
import Regist from '@/views/Regist.vue';
import Login_Customer from "@/views/Login_Customer.vue";
import ForgetPassword from '@/views/ForgetPassword.vue';
import Modify from '@/views/Modify.vue';
//前台-書籍(test addCart)
import BookDetail from "@/views/BookDetail.vue";
//前台-購物車&訂單:
import Cart from "@/views/Cart.vue";
import PaymentResult from "@/views/PaymentResult.vue";
import OrderList from "@/views/OrderList.vue";
import OrderDetail from "@/views/OrderDetail.vue";
import CouponList from "@/views/CouponList.vue";
//後台:
import Login_Admin from "@/views/admin/Login_Admin.vue";
import List_Customer from "@/views/admin/List_Customer.vue";
import List_Admin from "@/views/admin/List_Admin.vue";
import CouponManagement from "@/views/admin/CouponManagement.vue";

const routes = [
    { path: '/', name: 'Home', component: Home },
    // Login & Customers:
    { path: '/login', name: 'Login', component: Login_Customer },
    { path: '/register', name: 'Regist', component: Regist },
    { path: '/forget', name: 'ForgetPassword', component: ForgetPassword },
    { path: '/modify', component: Modify },
    { path: '/pwdforget', component: ForgetPassword },
    // Book:
    { path: "/book/:id", component: BookDetail, name: "book-link" },
    // Cart & Orders:
    { path: "/cart", component: Cart, name: "cart-link" },
    { path: "/coupon/list", component: CouponList, name: "coupon-list-link" },
    { path: "/orders/paymentResult", component: PaymentResult, name: "ecpay-return-page" },// 這個路徑要和 EcpayController 裡的一樣
    { path: "/order/list", component: OrderList, name: "order-list-page" },
    { path: "/order/detail/:orderId", component: OrderDetail, name: "order-detail-list-page" },
    //後台:
    { path: '/customers/list', component: List_Customer },
    { path: '/admins/list', component: List_Admin },
    { path: '/admins/login', component: Login_Admin },
    { path: "/coupon/management", component: CouponManagement, name: "coupon-management-page" },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// 新增導航守衛: 確保用戶未登入時，點擊"購物車"或"訂單"相關頁面時會自動導向登入頁面
/* 解釋: 
    - 在 routes 裡 meta 設定 requiresAuth: true，表示該頁面需要登入。
    - initializeAuth()：確保 localStorage 中的登入狀態能正確載入，即使頁面重新整理也不會丟失登入狀態
*/
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore(); // 取得 userStore
    authStore.initializeAuth(); // 確保重新整理時仍然有登入資訊
    if (to.meta.requiresAuth && !authStore.isAuthenticated) {
        next("/login"); // 若未登入則導向登入頁
    } else {
        next(); // 允許導航
    }
});

export default router;
