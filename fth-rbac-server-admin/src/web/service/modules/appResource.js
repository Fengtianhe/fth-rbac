import service from '@/common/utils/request'

export default {
  /**
   * 列出所有环境
   * @returns {Promise<AxiosResponse<T>>}
   * @param appId
   */
  all: appId => service.get(`/api/app/env/by-appid/${appId}`),

  /**
   * 创建资源
   * @param params
   * @returns {Promise<AxiosResponse<T>>}
   */
  save: params => service.post(`/api/app/resource`, params)
}
