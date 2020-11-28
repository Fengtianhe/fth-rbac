import allmenus from '@/common/assets/data/menu'
const permission = {
  state: {
    menus: []
  },
  mutations: {
    SET_MENUS: (state) => {
      state.menus = allmenus;
    },
    CLEAR_MENUS: (state) => {
      state.menus = [];
    }
  },
  actions: {}
};

export default permission;
