<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <span class="breadcrumb-label">当前位置：</span>
    <transition-group name="breadcrumb">
      <template>
        <el-breadcrumb-item v-for="(item,index) in levelList" :key="index">
          <template v-if="item.name">
            <span v-if="index == levelList.length - 1" class="no-redirect">{{item.name}}</span>
            <router-link v-else :to="item.redirect || item.path">{{item.name}}</router-link>
          </template>
        </el-breadcrumb-item>
      </template>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
export default {
  created() {
    this.getBreadcrumb();
  },
  data() {
    return {
      levelList: null
    };
  },
  watch: {
    $route() {
      this.getBreadcrumb();
    }
  },
  methods: {
    getBreadcrumb() {
      let matched = this.$route.matched.filter(item => item.name);
      // console.log(matched)
      this.levelList = matched;
    }
  }
};
</script>

<style lang="less" scoped>
  .app-breadcrumb.el-breadcrumb {
    display: inline-block;
    font-size: 14px;
    line-height: 50px;
    margin-left: 10px;

    .no-redirect {
      color: #97a8be;
      cursor: text;
    }

    .breadcrumb-label {
      float: left;
    }
  }
</style>
