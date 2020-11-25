import Vue from 'vue';

Vue.filter('substr', function (model, val, ) {
  return model.length > val ? model.substring(0, val) : model;
});
