import { createApp } from "vue";
import AdminApp from "./AdminApp.vue";  // 載入 Admin 主組件
import adminRouter from "./router";

const app = createApp(AdminApp);
app.use(adminRouter);
app.mount("#admin"); // 確保 index.html 內有 <div id="admin"></div>
