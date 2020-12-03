import service from '@common/utils/request';

const user = {
  state: {
    token: '',
    info: {
      id: '',
      username: '',
      nickname: '',
      status: '',
      createdAt: ''
    }
  },
  mutations: {
    SET_TOKEN: function (state, token) {
      state.token = token;
    },
    CLEAR_TOKEN: function (state) {
      state.token = '';
    },
    SET_USER_INFO: async function (state) {
      if (state.token) {
        const response = await service.get('/api/sys/user/info');
        state.info = response.data;
      } else {
        this.CLEAR_TOKEN(state);
      }
    }
  },
  actions: {
    LOGOUT({ commit }) {
      return new Promise((resolve) => {
        commit('CLEAR_TOKEN');
        commit('CLEAR_MENUS');
        resolve();
      });
    },
    async UPT_USER({ commit, state }, payload) {
      console.log(payload);
      const keys = Object.keys(payload);
      const { info } = state;
      const uptData = {};
      let upt = false;
      // 对比数据，有变化才更新
      for (let k of keys) {
        if (payload[k] && payload[k] !== info[k]) {
          uptData[k] = payload[k];
          upt = true;
        }
      }
      // 没有数据变化
      if (!upt) {
        return;
      }
      await service.put('/api/sys/user', uptData);
      commit('SET_USER_INFO');
    }
  }
};

export default user;
