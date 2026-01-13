import { createWebHistory, createRouter } from 'vue-router';
import { useAuthStore } from '@/store/user';

// import SearchMusic from '@/views/SearchMusic.vue';
// import PlayMusic from '@/views/PlayMusic.vue';

const routes:any = [
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/Login.vue'),
        meta: { requiresAuth: false }
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/Register.vue'),
        meta: { requiresAuth: false }
    },
    {
        path: '/',
        name: 'main',
        component: () => import('@/views/Main.vue'),
        meta: { requiresAuth: true }
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

// 路由守卫
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    const requiresAuth = to.meta.requiresAuth !== false; // 默认需要认证

    if (requiresAuth && !authStore.isAuthenticated) {
        // 需要认证但未登录，跳转到登录页
        next('/login');
    } else if ((to.path === '/login' || to.path === '/register') && authStore.isAuthenticated) {
        // 已登录用户访问登录/注册页，跳转到首页
        next('/');
    } else {
        next();
    }
});

export default router;