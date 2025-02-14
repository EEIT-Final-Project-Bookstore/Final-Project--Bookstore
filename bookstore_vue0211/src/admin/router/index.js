import { createRouter, createWebHistory } from "vue-router";
import AdminDashboard from "../pages/AdminDashboard.vue";
import BookManagement from "../pages/BookManagement.vue";


const routes = [

    { path: "/", component: AdminDashboard }, // 儀表板
    { path: "/books", component: BookManagement }, // 書籍管理

];

const adminRouter = createRouter({
    history: createWebHistory("/admin"), // 後台使用 /admin 作為基礎路徑
    routes,
});

export default adminRouter;
