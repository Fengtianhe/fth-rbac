<template>
  <div class="page">
    <!--  LOGO  -->
    <div class="logo">
      <span>FTH-RBAC</span>
    </div>

    <!--  菜单  -->
    <div class="scrollbar">
      <div class="scrollbar-scope">
        <el-menu
            mode="vertical"
            :show-timeout="200"
            :default-active="$route.path"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
        >
          <sidebar-item v-for="route in menus" :key="route.name" :item="route" :base-path="route.path"></sidebar-item>
        </el-menu>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import SidebarItem from './SidebarItem';

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters([
      'menus'
    ])
  },
  methods: {}
};
</script>
<style scoped lang="less">
  .page {
    height: 100%;
    display: flex;
    flex-direction: column;
    width: 200px;
    overflow: hidden;
    background: #545c64;
    .logo {
      line-height: 85px;
      text-align: center;
      width: 100%;
      height: 85px;
      margin: 0 auto;
      color: #cce7f8;
      font-family: "Pacifico", cursive;
      text-transform: uppercase;
      border-bottom: 1px solid #cce7f8;

      span {
        font-size: 30px;
        display: inline-block;
        animation: flip 10.0s infinite linear;
        transform-origin: 0 50%;
        transform-style: preserve-3d;
        -webkit-transform-style: preserve-3d;
      }
    }

    .scrollbar {
      flex: 1;
      overflow: hidden;
      width: 180px;

      .scrollbar-scope {
        overflow-y: scroll;
        overflow-x: hidden;
        height: 100%;
        width: 200px;
      }
    }
  }

  .back-btn {
    padding: 10px 0 10px 30px;
    cursor: pointer;
    background-color: #304156;

    &:hover {
      background: #263445;
    }
  }

  @keyframes flip {
    35% {
      transform: rotateX(360deg);
    }
    100% {
      transform: rotatex(360deg);
    }
  }
</style>
