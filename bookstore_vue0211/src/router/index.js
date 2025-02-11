import { createRouter, createWebHistory } from "vue-router";
import Home from "../pages/Home.vue"; // ✅ 確保引入 Home.vue
import BookDetail from "@/pages/BookDetail.vue";
import SelectedCategories from '@/pages/SelectedCategories.vue'; // 0207新增分類頁面

const routes = [
    { path: "/", component: Home }, // ✅ 確保 "/" 有指向 Home.vue
    { path: "/book/:id", name: "BookDetail", component: BookDetail },
    { path: '/selected-categories', component: SelectedCategories }, // 0207新增的分類總覽頁
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
