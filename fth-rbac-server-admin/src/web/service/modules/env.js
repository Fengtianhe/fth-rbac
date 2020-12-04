import service from '@/common/utils/request'

export default {
  /**
   * 列出所有环境
   * @returns {Promise<AxiosResponse<T>>}
   * @param appId
   */
  all: appId => service.get(`/api/env/by-appid/${appId}`)
}
