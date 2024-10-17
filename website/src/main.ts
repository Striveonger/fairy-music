// js
import { createApp } from 'vue';
import { createPinia } from 'pinia'
import "bootstrap";

import App from '@/App.vue';
import router from '@/router';
// css
import "@/style.scss";
// app
const pinia = createPinia();
const app = createApp(App).use(router).use(pinia).mount('#app');