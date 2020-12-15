import service from '@/common/utils/request';

export default {
  overview: () => service.get('/api/dashboard/overview')
};
