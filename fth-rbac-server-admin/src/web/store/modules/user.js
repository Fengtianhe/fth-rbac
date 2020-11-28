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
    CLEAR_TOKEN: function (state) {
      state.token = ''
    },
    SET_USER_INFO: async function (state) {
      if (state.token) {
        const response = await service.get('/api/sys/user/info');
        state.info = response.data;
      } else {
        this.CLEAR_TOKEN(state)
      }
    }
  },
  actions: {
    LOGOUT ({commit}) {
      return new Promise((resolve) => {
        commit('CLEAR_TOKEN');
        commit('CLEAR_MENUS')
        resolve();
      });
    }
  }
};

export default user;
