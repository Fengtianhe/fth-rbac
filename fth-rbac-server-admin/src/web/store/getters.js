const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  menus: state => state.permission.menus,
  addRouters: state => state.permission.addRouters,
};
export default getters;
