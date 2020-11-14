<template>
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <el-menu
        mode="vertical"
        :show-timeout="200"
        :default-active="$route.path"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
    >
      <sidebar-item v-for="route in menus" :key="route.name" :item="route" :base-path="route.path"></sidebar-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
  import {mapGetters} from 'vuex';
  import SidebarItem from './SidebarItem';

  export default {
    components: {SidebarItem},
    computed: {
      ...mapGetters([
        'menus'
      ])
    },
    methods: {}
  };
</script>
<style scoped lang="less">
  .back-btn {
    padding: 10px 0 10px 30px;
    cursor: pointer;
    background-color: #304156;

    &:hover {
      background: #263445;
    }
  }
</style>
