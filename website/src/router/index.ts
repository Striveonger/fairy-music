import { createWebHistory, createRouter } from 'vue-router';

import SearchMusic from '@/views/SearchMusic.vue';
// import PlayMusic from '@/views/PlayMusic.vue';

const routes = [
    {
        path: '/',
        name: 'search',
        component: () => import('@/views/SearchMusic.vue')
    },
    {
        path: '/play/:url',
        name: 'play',
        component: () => import("@/views/PlayMusic.vue")
    }
];

const router = createRouter({
    // history: createMemoryHistory(),
    history: createWebHistory(),
    routes,
});

export default router;