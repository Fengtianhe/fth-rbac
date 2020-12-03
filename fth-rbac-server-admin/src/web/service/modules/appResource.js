import service from '@/common/utils/request';

export default {
  /**
   * 查询资源详情
   * @param resourceId
   * @returns {Promise<AxiosResponse<T>>}
   */
  getById: resourceId => service.get(`/api/app/resource/resource`, {params: {resourceId}}),

  /**
   * 列出所有资源树
   * @returns {Promise<AxiosResponse<T>>}
   * @param params
   */
  treeAll: params => service.get(`/api/app/resource/tree-all`, {params}),

  /**
   * 创建资源
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  save: params => service.post(`/api/app/resource`, params),

  /**
   * 更新资源
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  update: params => service.put(`/api/app/resource`, params),

  /**
   * 删除资源
   * @param id
   * @returns {Promise<AxiosResponse<T>>}
   */
  delete: resourceId => service.delete(`/api/app/resource`, {params: {resourceId}}),

  /**
   * 更新资源顺序
   * @param resourceId
   * @param sort
   * @returns {Promise<AxiosResponse<T>>}
   */
  updateSort: (resourceId, sort) => service.put(`/api/app/resource/sort`, {resourceId, sort}),

  /**
   * 更新状态
   * @param resourceId
   * @param status
   * @returns {Promise<AxiosResponse<T>>}
   */
  updateStatus: (resourceId, status) => service.put(`/api/app/resource/status`, {resourceId, status}),

  /**
   * 更新目录显示状态
   * @param resourceId
   * @param inMenu
   * @returns {Promise<AxiosResponse<T>>}
   */
  updateInMenu: (resourceId, inMenu) => service.put(`/api/app/resource/inmenu`, {resourceId, inMenu})
};