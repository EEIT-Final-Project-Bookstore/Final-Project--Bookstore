import { createApp } from 'vue';
import { createPinia } from "pinia";
import App from './App.vue';
import router from './router';

const app = createApp(App); // 創建 Vue 應用實例
const pinia = createPinia(); // 創建 Pinia 實例

app.use(pinia); // 掛載 Pinia
app.use(router); // 掛載 Vue Router
app.mount("#app"); // 最後統一掛載

