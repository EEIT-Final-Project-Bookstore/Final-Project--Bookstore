import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import Regist from '@/views/Regist.vue';
import ForgetPassword from '@/views/ForgetPassword.vue';
import CustomerList from '@/views/CustomerList.vue'
import Modify from '@/views/Modify.vue';

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Regist', component: Regist },
    { path: '/forget', name: 'ForgetPassword', component: ForgetPassword },
    { path: '/customers', component: CustomerList },
    { path: '/modify', component: Modify },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
