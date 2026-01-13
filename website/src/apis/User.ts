import { http, Result, EnumContentType } from './index';
import type { LoginRequest, RegisterRequest, AuthResponse, UserInfo } from '@/types';

/**
 * 用户登录
 */
export const login = async (data: LoginRequest): Promise<AuthResponse> => {
    const response = await http.post<Result<AuthResponse>>('/api/v1/user/login', data, {
        headers: { 'Content-Type': EnumContentType.JSON }
    });
    return response.data.data as AuthResponse;
};

/**
 * 用户注册
 */
export const register = async (data: RegisterRequest): Promise<AuthResponse> => {
    const response = await http.post<Result<AuthResponse>>('/api/v1/user/register', data, {
        headers: { 'Content-Type': EnumContentType.JSON }
    });
    return response.data.data as AuthResponse;
};

/**
 * 获取当前用户信息
 */
export const getUserInfo = async (): Promise<UserInfo> => {
    const response = await http.get<Result<UserInfo>>('/api/v1/user/info');
    return response.data.data as UserInfo;
};

/**
 * 用户登出
 */
export const logout = async (): Promise<void> => {
    await http.post<Result<void>>('/api/v1/user/logout');
};
