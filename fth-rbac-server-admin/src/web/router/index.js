import Vue from 'vue';
import Router from 'vue-router';
/* Layout */
import Layout from '../views/layout/Layout';

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router);

/**
 * hideQuickVisit 是否不在tag view中不追加
 * @type {*[]}
 */
const routes = [
  {path: '/login', component: () => import('@web/views/login')},
  {path: '/404', component: () => import('@web/views/404')},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard/index',
    children: [
      {path: '/dashboard/index', name: '主页', component: () => import('@web/views/dashboard')},
    ]
  },
  {
    path: '/application',
    component: Layout,
    name: '应用管理',
    redirect: '/application/user/list',
    children: [
      {path: 'app/list', name: '应用列表', component: () => import('@web/views/app/application/list')},
      {
        path: 'env/list',
        name: '应用环境',
        component: () => import('@web/views/app/env/list'),
        meta: {hideQuickVisit: true}
      },
      {
        path: 'resource/list',
        name: '应用资源',
        component: () => import('@web/views/app/resource/list'),
        meta: {hideQuickVisit: true}
      },
      {path: 'role/list', name: '应用角色', component: () => import('@web/views/dashboard')},
    ]
  },
  {
    path: '/system',
    component: Layout,
    name: '系统管理',
    redirect: '/application/user/list',
    children: [
      {path: 'user/list', name: '用户管理', component: () => import('@web/views/sys/user/list')},
    ]
  },
  {path: '*', redirect: '/404'}
];

export default new Router({
  routes
});

