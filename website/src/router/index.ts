import { createWebHistory, createRouter } from 'vue-router';

// import SearchMusic from '@/views/SearchMusic.vue';
// import PlayMusic from '@/views/PlayMusic.vue';

const routes:any = [
    {
        path: '/',
        name: 'main',
        component: () => import('@/views/Main.vue')
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

export default router;