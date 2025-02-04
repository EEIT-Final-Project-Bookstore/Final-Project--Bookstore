import { createApp } from "vue";
import App from "./App.vue";
import VuePaginate from "vuejs-paginate-next"; // 引入 VuePaginate
import SearchPage from "./components/SearchPage.vue"; // 引入 SearchPage 组件
import router from "./router"; // 引入路由

// 创建 Vue 应用并注册组件
const app = createApp(App);

// 全局注册 VuePaginate
app.component("VuePaginate", VuePaginate);

// 全局注册 SearchPage（可选，如果需要在多处直接使用）
app.component("SearchPage", SearchPage);

// 使用路由
app.use(router);

// 挂载应用
app.mount("#app");
