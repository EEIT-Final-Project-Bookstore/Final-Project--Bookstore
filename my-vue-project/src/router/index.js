import { createRouter, createWebHistory } from "vue-router";
import SearchResults from "../views/SearchResults.vue";
import BookDetail from "../views/BookDetail.vue";
import ManageImages from "../views/ManageImages.vue";
import Homepage from "../views/Homepage.vue";
import AdminDashboard from "../views/AdminDashboard.vue";

const routes = [
  {
    path: "/results/:keyword",
    name: "SearchResults",
    component: SearchResults,
    props: true, // 將 keyword 作為參數傳遞給組件
  },
  {
    path: "/book/:id",
    name: "BookDetail",
    component: BookDetail,
    props: true, // 將 id 作為參數傳遞給組件
  },
  {
    path: "/admin/images",
    name: "ManageImages",
    component: ManageImages
  },
  {
    path: '/admin', name: 'AdminDashboard', component: AdminDashboard
  },
  {
    path: '/', name: 'Homepage', component: Homepage
  }

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
