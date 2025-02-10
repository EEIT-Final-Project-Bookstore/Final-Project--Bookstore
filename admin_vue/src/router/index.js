import Home from '@/views/Home.vue';
import List_Admin from '@/views/List_Admin.vue';
import List_Customer from '@/views/List_Customer.vue';
import Login_Admin from '@/views/Login_Admin.vue';
import Navbar from '@/views/Navbar.vue';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/login/admin', component: Login_Admin },
    { path: '/customers', component: List_Customer },
    { path: '/admins', component: List_Admin },
    { path: '/navbar', component: Navbar },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
