import { createApp } from 'vue';
import App from './App.vue';
import VuePaginate from 'vuejs-paginate-next';
import router from './router';
createApp(App).component('VuePaginate', VuePaginate).use(router).mount('#app')