import service from '@common/utils/request';

const user = {
  state: {
    token: '',
    info: {}
  },
  mutations: {
    SET_TOKEN: function (state, token) {
      state.token = token;
    },
    SET_USER_INFO: async function (state) {
      const response = await service.get('/api/sys/user/info');
      state.info = response.data;
    }
  },
  actions: {}
};

export default user;
