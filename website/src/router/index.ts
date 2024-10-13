import { createWebHistory, createRouter } from 'vue-router';

import SearchMusic from '@/views/SearchMusic.vue';
import PlayMusic from '@/views/PlayMusic.vue';

const routes = [
    {
        path: '/',
        name: 'search',
        component: SearchMusic
    },
    {
        path: '/play/:url',
        name: 'play',
        component: PlayMusic
    }
];

const router = createRouter({
    // history: createMemoryHistory(),
    history: createWebHistory(),
    routes,
});

export default router;