import { createRouter, createWebHistory } from "vue-router";
import BookDetail from "@/pages/BookDetail.vue";
import BookManagement from "@/pages/BookManagement.vue";

const routes = [
    { path: "/book/:id", name: "BookDetail", component: BookDetail },
    { path: "/manage", component: BookManagement, name: "BookManagement" },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
