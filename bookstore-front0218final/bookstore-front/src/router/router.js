import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/authStore.js";
// **前台**
// 首頁:
import Home from '@/views/Home.vue';
// 登入&顧客:
import Regist from '@/views/Regist.vue';
import Login_Customer from "@/views/Login_Customer.vue";
import ForgetPassword from '@/views/ForgetPassword.vue';
import Modify from '@/views/Modify.vue';
// 書籍&搜尋:
import BookDetail from "@/views/BookDetail.vue";
import SelectedCategories from "@/views/SelectedCategories.vue";
import LifestyleCategories from "@/views/LifestyleCategories.vue";
import SearchResults from "@/views/SearchResults.vue";
// 購物車&訂單:
import Cart from "@/views/Cart.vue";
import PaymentResult from "@/views/PaymentResult.vue";
import OrderList from "@/views/OrderList.vue";
import OrderDetail from "@/views/OrderDetail.vue";
import CouponList from "@/views/CouponList.vue";

// **後台**
import Login_Admin from "@/views/admin/Login_Admin.vue";
import Dashboard from "@/views/admin/Dashboard.vue";
import List_Customer from "@/views/admin/List_Customer.vue";
import List_Admin from "@/views/admin/List_Admin.vue";
import BackstageManagement from "@/views/admin/BackstageManagement.vue";
import SearchCoupon from "@/views/admin/SearchCoupon.vue";
import BookManagement from "@/views/admin/BookManagement.vue";
import PrivacyPolicy from "@/views/utils/privacy-policy.vue";
import TermsOfService from "@/views/utils/terms-of-service.vue";

const routes = [
    // **前台:
    // 首頁:
    { path: '/', name: 'Home', component: Home },
    { path: "/selected-categories", name: "SelectedCategories", component: SelectedCategories },
    { path: "/privacy", name: "privacy-policy-page", component: PrivacyPolicy },
    { path: "/terms", name: "terms-of-service-page", component: TermsOfService },
    // 登入&顧客:
    { path: '/login', name: 'Login', component: Login_Customer },
    { path: '/register', name: 'Regist', component: Regist },
    { path: '/forget', name: 'ForgetPassword', component: ForgetPassword },
    { path: '/modify', component: Modify },
    { path: '/forgot', name: 'forgot', component: ForgetPassword },
    // 前台-書籍&搜尋:
    { path: "/book/:id", component: BookDetail, name: "book-link" },
    { path: "/lifestyle", name: "LifestyleCategories", component: LifestyleCategories },
    { path: "/results/:keyword", name: "SearchResults", component: SearchResults, props: true },
    // 前台-購物車&訂單:
    { path: "/cart", component: Cart, name: "cart-link" },
    { path: "/coupon/list", component: CouponList, name: "coupon-list-link" },
    { path: "/orders/paymentResult", component: PaymentResult, name: "ecpay-return-page" },// **這個路徑要和 EcpayController 裡的"一樣"
    { path: "/order/list", component: OrderList, name: "order-list-page" },
    { path: "/order/detail/:orderId", component: OrderDetail, name: "order-detail-list-page" },

    // **後台:
    { path: '/admin/login', component: Login_Admin },
    { path: "/backstage/management", component: BackstageManagement, name: "manage-page" },
    { path: '/admin', component: Dashboard, name: "backstage-homepage" },
    { path: '/admin/customers/list', component: List_Customer },
    { path: '/admin/list', component: List_Admin },
    // { path: '/admin/books', component:  },
    { path: '/admin/coupons', component: SearchCoupon },
    { path: "/admin/books", component: BookManagement },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        // 如果使用者點擊瀏覽器的「返回 / 前進」按鈕，
        // savedPosition 會是瀏覽器自動記錄的滾動位置。
        if (savedPosition) {
            return savedPosition;
        } else {
            // 否則就回到頂端
            return { top: 0 };
        }
    }
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
