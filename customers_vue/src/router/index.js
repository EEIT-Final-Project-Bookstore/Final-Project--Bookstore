import ForgetPassword from '@/views/ForgetPassword.vue';
import Home from '@/views/Home.vue';
import Login_Customer from '@/views/Login_Customer.vue';
import Modify from '@/views/Modify.vue';
import Navbar from '@/views/Navbar.vue';
import Regist from '@/views/Regist.vue';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: Login_Customer },
    { path: '/register', name: 'Regist', component: Regist },
    { path: '/forgot', name: 'ForgetPassword', component: ForgetPassword },
    { path: '/modify', component: Modify },
    { path: '/navbar', component: Navbar },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
