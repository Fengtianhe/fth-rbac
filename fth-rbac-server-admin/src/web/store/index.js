import Vue from 'vue';
import Vuex from 'vuex';
import app from './modules/app';
import getters from './getters';
import tagsView from './modules/tagsView';
import permission from './modules/permission';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    tagsView,
    permission
  },
  getters
});

export default store;
