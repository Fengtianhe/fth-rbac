import service from '@/common/utils/request'
const URL_APP_APPLICATION_LIST = '/api/app/list';

export default {
  URL_APP_APPLICATION_LIST,

  /**
   * 添加应用
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  add: params => service.post('/api/app', params),

  /**
   * 所有有权限的应用
   * @returns {Promise<AxiosResponse<T>>}
   */
  all: () => service.get('/api/app/all')
}
