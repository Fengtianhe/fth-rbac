import axios from 'axios';
import { Message } from 'element-ui';

let api = '';
let method = '';
// 创建axios实例
const service = axios.create({
  headers: {
    'content-type': 'application/json',
    'api-version': '1.0.0'
  }
});

// request拦截器
service.interceptors.request.use(
  config => {
    api = config.url;
    method = config.method;
    return config;
  },
  error => {
    console.log(error); // for debug
    Promise.reject(error);
  }
);

// response 拦截器
service.interceptors.response.use(response => {
    const res = response.data;
    if (res.code === 200 || res.code === 0) {
      return response.data;
    }
    if (res.code === 50003) {
      Message.error('请重新登录');
      return;
    }
    if (res.code === 404) {
      Message.error(`接口不存在：[${method}] => ${api}`);
      return;
    }
    Message({
      message: res.msg,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject('error');
  },
  error => {
    console.log('err' + error);// for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
