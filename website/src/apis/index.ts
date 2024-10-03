import axios from 'axios'

export enum EnumContentType {
    FORM_DATA = 'multipart/form-data;charset=UTF-8',
    FORM_URLENCODED = 'application/x-www-form-urlencoded;charset=UTF-8',
    JSON = 'application/json;charset=UTF-8',
}

export interface Result<T> {
    code: string,
    state: number,
    now: Date,
    message: string,
    data: T | Array<T> | null
}

export const http = axios.create({
    baseURL: (import.meta.env.BASE_URL ? import.meta.env.BASE_URL : ''),
    headers: {},
    timeout: 1000 * 5, // 5s
});

// TODO: 可以配置 拦截器, 错误处理等...后面再说呗~
// https://www.axios-http.cn/docs/req_config

// 统一导出
// export default {EnumContentType, instance};