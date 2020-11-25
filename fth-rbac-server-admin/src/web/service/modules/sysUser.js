import service from '@common/utils/request';
import store from '@web/store';

export default {
  /**
   * 登录
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  login: params => service.post(`/api/sys/user/login`, params),

  /**
   * 获取用户信息
   * @param params
   */
  info: (params = {}) => store.commit('SET_USER_INFO')
};
