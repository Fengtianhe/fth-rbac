import axios from 'axios';
import { Message } from 'element-ui';
import store from '@web/store';

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
    config.headers['X-Authorization-Token'] = store.state.user.token;
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
    console.log(res);
    if (res.code === 200 || res.code === 0) {
      return res;
    }
    if (res.code === 401) {
      // Message.error('请重新登录');
      window.$router.push({ path: '/login' });
      return;
    }
    if (res.code === 404) {
      Message.error(`接口不存在：[${method}] => ${api}`);
      return;
    }
    Message({
      message: res.message,
      type: 'error',
      duration: 5 * 1000
    });
    return new Promise.reject('error');
  },
  error => {
    console.log('err' + error);// for debug
    if (error.response) {
      if (error.response.status === 404) {
        Message.error(`请求资源不存在：[${method}] => ${api}`);
        return;
      }
    }

    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
