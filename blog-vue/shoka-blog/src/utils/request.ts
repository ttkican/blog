import useStore from "@/store";
import axios, { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from "axios";
import { getToken, token_prefix } from "./token";

const requests = axios.create({
  baseURL: "/api",
  timeout: 10000,
  // 请求头
  headers: {
    "Content-Type": "application/json;charset=UTF-8",
  },
});

// 请求拦截器
requests.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 请求带token
    if (getToken()) {
      config.headers["Authorization"] = token_prefix + getToken();
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// 配置响应拦截器
requests.interceptors.response.use(
  (response: AxiosResponse) => {
    switch (response.data.code) {
      case -1:
        window.$message?.error(response.data.msg);
        break;
      case 400:
        window.$message?.error(response.data.msg);
        break;
      case 402:
        const { user } = useStore();
        user.forceLogOut();
        window.$message?.error(response.data.msg);
        break;
      case 500:
        window.$message?.error(response.data.msg);
        break;
    }
    return response;
  },
  (error: AxiosError) => {
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substring(message.length - 3) + "异常";
    }
    window.$message?.error(message, { duration: 5000 });
    return Promise.reject(error);
  }
);

// 对外暴露
export default requests;
