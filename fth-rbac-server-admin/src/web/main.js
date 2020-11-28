import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from '@common/utils/request';
import '@common/filter';

import {TableMixinConfig} from 'element-table-mixin'

TableMixinConfig.REQUEST = request
TableMixinConfig.EL_TABLE_SIZE = "mini"
TableMixinConfig.EL_TABLE_BORDER = false

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.config.devtools = true;

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app');
