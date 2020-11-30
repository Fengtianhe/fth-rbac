import Vue from 'vue';
import {TableMixinMethods} from 'element-table-mixin'
Vue.filter('substr', function (model, val, ) {
  return model.length > val ? model.substring(0, val) : model;
});

Vue.filter('dateFormat', function (date) {
  return TableMixinMethods.dateTimeFormat("yyyy-MM-dd", new Date(date))
})
