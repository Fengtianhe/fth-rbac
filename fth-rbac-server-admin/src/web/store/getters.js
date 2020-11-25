const getters = {
  visitedViews: state => state.tagsView.visitedViews,
  menus: state => state.permission.menus,
  addRouters: state => state.permission.addRouters,
  user: state => state.user.info
};
export default getters;
