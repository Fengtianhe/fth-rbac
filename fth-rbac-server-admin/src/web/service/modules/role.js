import service from '@/common/utils/request';

const URL_ROLE_LIST = '/api/role/list';

export default {
  URL_ROLE_LIST,

  /**
   * 添加角色
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  add: params => service.post('/api/role', params),

  /**
   * 更新角色
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  update: params => service.put('/api/role', params),

  /**
   * 角色分配资源
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  assign: params => service.post('/api/role/assign', params),

  /**
   * 获取已经分配的资源
   * @param roleId
   * @returns {Promise<AxiosResponse<T>>}
   */
  resourceIdsAssigned: roleId => service.get('/api/role/assign-resourceids', {params: {roleId}}),

  /**
   * 获取角色
   * @param roleId
   * @returns {Promise<AxiosResponse<T>>}
   */
  getRole: roleId => service.get(`/api/role/role/${roleId}`),
};
