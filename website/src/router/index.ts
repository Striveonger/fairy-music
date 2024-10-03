import { createMemoryHistory, createRouter } from 'vue-router';

import SearchMusic from '../views/SearchMusic.vue';
import PlayMusic from '../views/PlayMusic.vue';


const routes = [
    { path: '/', component: SearchMusic },
    { path: '/play', component: PlayMusic },
]
const router = createRouter({
    history: createMemoryHistory(),
    routes,
})