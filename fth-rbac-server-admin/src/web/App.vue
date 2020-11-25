<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>

import { SysUserService } from '@web/service';

export default {
  name: 'App',
  watch: {},
  async created() {
    // 把router放到全局，为了request处理登录过期的逻辑
    window.$router = this.$router;

    // 解决刷新页面 vuex数据丢失问题
    if (localStorage.getItem('store')) {
      await this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(localStorage.getItem('store'))));
    }

    window.addEventListener('beforeunload', () => {
      try {
        localStorage.setItem('store', JSON.stringify(this.$store.state));
      } catch (e) {
        localStorage.setItem('error', e);
      }
    });

    // 判断当前用户是否登录
    if (!this.$store.state.user.token) {
      this.$router.push({ path: '/login' });
    } else {
      SysUserService.info();
    }
    localStorage.removeItem('store');
  }
};
</script>

<style lang="less">
  @import "../common/assets/style/base/_base.css";
</style>
