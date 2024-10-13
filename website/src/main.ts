// js
import { createApp } from 'vue';
import "bootstrap";

import App from '@/App.vue';
import router from '@/router';
// css
import "@/style.scss";
// app
createApp(App).use(router).mount('#app');
