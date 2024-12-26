import { createWebHistory, createRouter } from 'vue-router';

import SearchMusic from '@/views/SearchMusic.vue';
// import PlayMusic from '@/views/PlayMusic.vue';

const routes:any = [
    {
        path: '/',
        name: 'search',
        component: () => import('@/views/SearchMusic.vue')
    },
    {
        path: '/play/:url',
        name: 'play',
        component: () => import("@/views/PlayList.vue")
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

export default router;