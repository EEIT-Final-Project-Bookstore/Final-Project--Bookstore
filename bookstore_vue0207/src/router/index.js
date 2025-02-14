import { createRouter, createWebHistory } from "vue-router";
import BookDetail from "@/pages/BookDetail.vue";
import BookManagement from "@/pages/BookManagement.vue";
import SelectedCategories from '@/pages/SelectedCategories.vue'; // 0207新增分類頁面



const routes = [
    { path: "/book/:id", name: "BookDetail", component: BookDetail },
    { path: "/manage", component: BookManagement, name: "BookManagement" },
    { path: '/selected-categories', component: SelectedCategories } // 0207新增的分類總覽頁

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
