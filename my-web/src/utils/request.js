import axios from 'axios';
import { getAccessToken } from '@/utils/auth'

const service = axios.create({
  // baseURL: 'https://mock.apifox.cn/m1/2428381-0-default/admin-api',
  baseURL: '/api',
  timeout: 60000  // 单位ms
});

service.interceptors.request.use(
  config => {
    // 在请求发送之前对请求数据进行处理
    if (getAccessToken()) {
      config.headers['Authorization'] = 'Bearer ' + getAccessToken() // 携带token
    }

    return config;
  },
  error => {
    // 对请求错误做些什么
    console.log(error);
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  response => {
    // 对响应数据进行处理
    // ...

    return response.data;
  },
  error => {
    // 对响应错误做些什么
    console.log(error);
    return Promise.reject(error);
  }
);

export default service;
