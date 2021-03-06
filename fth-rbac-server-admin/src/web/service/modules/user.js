import service from '@common/utils/request';
import store from '@web/store';

const URL_SYS_USER_LIST = '/api/user/list';
export default {
  URL_SYS_USER_LIST,
  /**
   * 登录
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  login: params => service.post(`/api/user/login`, params),

  /**
   * 获取用户信息
   * @param params
   */
  info: () => store.commit('SET_USER_INFO'),

  /**
   * 通过关键词查询用户列表
   * @returns {Promise<AxiosResponse<T>>}
   */
  byKeywords: params => service.get('/api/user/by-keywords', { params }),

  /**
   * 添加用户
   */
  add: params => service.post('/api/user', params)
};
