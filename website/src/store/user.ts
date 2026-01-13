import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { login as apiLogin, register as apiRegister, logout as apiLogout, getUserInfo } from '@/apis/User';
import type { LoginRequest, RegisterRequest, UserInfo } from '@/types';
import { Constant } from '@/types';

/**
 * 用户认证状态管理
 */
export const useAuthStore = defineStore('auth', () => {
    const token = ref<string>(localStorage.getItem(Constant.TOKEN_KEY) || '');
    const user = ref<UserInfo | null>(
        localStorage.getItem(Constant.USER_KEY)
            ? JSON.parse(localStorage.getItem(Constant.USER_KEY)!)
            : null
    );

    const isAuthenticated = computed(() => !!token.value);

    /**
     * 设置认证信息
     */
    const setAuth = (authToken: string, userInfo: UserInfo) => {
        token.value = authToken;
        user.value = userInfo;
        localStorage.setItem(Constant.TOKEN_KEY, authToken);
        localStorage.setItem(Constant.USER_KEY, JSON.stringify(userInfo));
    };

    /**
     * 清除认证信息
     */
    const clearAuth = () => {
        token.value = '';
        user.value = null;
        localStorage.removeItem(Constant.TOKEN_KEY);
        localStorage.removeItem(Constant.USER_KEY);
    };

    /**
     * 用户登录
     */
    const login = async (credentials: LoginRequest) => {
        const response = await apiLogin(credentials);
        setAuth(response.token, response.user);
        return response;
    };

    /**
     * 用户注册
     */
    const register = async (data: RegisterRequest) => {
        const response = await apiRegister(data);
        setAuth(response.token, response.user);
        return response;
    };

    /**
     * 用户登出
     */
    const logout = async () => {
        try {
            await apiLogout();
        } finally {
            clearAuth();
        }
    };

    /**
     * 获取用户信息
     */
    const fetchUserInfo = async () => {
        const userInfo = await getUserInfo();
        user.value = userInfo;
        localStorage.setItem(Constant.USER_KEY, JSON.stringify(userInfo));
        return userInfo;
    };

    return {
        token,
        user,
        isAuthenticated,
        login,
        register,
        logout,
        fetchUserInfo,
        clearAuth
    };
});
