<template>
  <div class="app-wrapper">
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <navbar></navbar>
      <tags-view></tags-view>
      <app-main style="flex: 1"></app-main>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain, TagsView } from './components';

export default {
  name: 'layout',
  components: {
    Navbar,
    Sidebar,
    AppMain,
    TagsView
  },
  created() {
  },
  computed: {},
  methods: {}
};
</script>

<style lang="less" scoped>
  .app-wrapper {
    position: relative;
    height: 100%;
    width: 100%;
    display: flex;

    .sidebar-container {
      flex: 0 0 180px;
      background-color: rgb(48, 65, 86);
    }

    .main-container {
      flex: 1;
      display: flex;
      flex-direction: column;
    }
  }
</style>
