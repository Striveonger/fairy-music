import axios from 'axios';
import router from '@/router';

export enum EnumContentType {
    FORM_DATA = 'multipart/form-data;charset=UTF-8',
    FORM_URLENCODED = 'application/x-www-form-urlencoded;charset=UTF-8',
    JSON = 'application/json;charset=UTF-8',
};

export interface Result<T> {
    code: string,
    state: number,
    now: Date,
    message: string,
    data: T | T[] | null
};

export const http = axios.create({
    baseURL: import.meta.env.BASE_URL || '',
    headers: {},
    timeout: 1000 * 10
});

// 请求拦截器：自动添加 token
http.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('fm.auth.token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 响应拦截器：处理 401 未授权错误
http.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        // 检查是否是 401 错误
        if (error.response && error.response.state === 401) {
            // 清除本地存储的认证信息
            localStorage.removeItem('fm.auth.token');
            localStorage.removeItem('fm.auth.user');

            // 跳转到登录页
            router.push('/login');
        }
        return Promise.reject(error);
    }
);

// 统一导出
// export default {EnumContentType, instance};