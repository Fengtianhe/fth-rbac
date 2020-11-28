import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from '@common/utils/request';
import '@common/filter';

import TableMixinConfig from '@/common/mixin/TableMixinConfig'

TableMixinConfig.REQUEST = request

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.config.devtools = true;

// 本地开发显示所有目录
import menus from '@/common/assets/data/menu';

store.commit('SET_MENUS', menus);

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app');
