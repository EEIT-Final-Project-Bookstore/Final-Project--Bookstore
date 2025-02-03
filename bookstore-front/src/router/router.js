import { createRouter, createWebHistory } from "vue-router";
//前台-登入&顧客:
import Home from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import Regist from '@/views/Regist.vue';
import ForgetPassword from '@/views/ForgetPassword.vue';
import CustomerList from '@/views/CustomerList.vue'
import Modify from '@/views/Modify.vue';
//前台-購物車&訂單:
import Cart from "@/views/Cart.vue";
import PaymentResult from "@/views/PaymentResult.vue";
import OrderList from "@/views/OrderList.vue";
import OrderDetail from "@/views/OrderDetail.vue";
//後台-優惠券:
import CouponManagement from "@/views/admin/CouponManagement.vue";

const routes = [
    { path: '/', name: 'Home', component: Home },
    // Login & Customers:
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Regist', component: Regist },
    { path: '/forget', name: 'ForgetPassword', component: ForgetPassword },
    { path: '/customers', component: CustomerList },
    { path: '/modify', component: Modify },
    // Cart & Orders:
    { path: "/cart", component: Cart, name: "cart-link" },
    { path: "/orders/paymentResult", component: PaymentResult, name: "ecpay-return-page" },
    { path: "/orderList", component: OrderList, name: "order-list-page" },
    { path: "/order/detail/:orderId", component: OrderDetail, name: "order-detail-list-page" },
    //後台:
    { path: "/coupon/management", component: CouponManagement, name: "coupon-management-page" },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
