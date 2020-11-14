<template>
  <div class="menu-wrapper">
    <router-link v-if="!item.children || !item.children.length" :to="resolvePath(item.path)">
      <el-menu-item :index="resolvePath(item.path)" :class="{'submenu-title-noDropdown':!isNest}">
        <i v-if="item.icon" class="item.icon"></i>
        <span slot="title">{{ item.name }}</span>
      </el-menu-item>
    </router-link>

    <el-submenu v-else :index="item.name || item.path">
      <template slot="title">
        <i v-if="item.icon" class="item.icon"></i>
        <span slot="title">{{ item.name }}</span>
      </template>

      <template v-for="child in item.children">
        <sidebar-item
                v-if="child.children && child.children.length > 0"
                :is-nest="true"
                :item="child"
                :key="child.path"
                :base-path="resolvePath(child.path)"
                class="nest-menu"
        />
        <router-link v-else :to="resolvePath(child.path)" :key="child.name">
          <el-menu-item :index="resolvePath(child.path)">
            <i v-if="child.icon" class="child.icon"></i>
            <span slot="title">{{ child.name }}</span>
          </el-menu-item>
        </router-link>
      </template>
    </el-submenu>
  </div>
</template>

<script>
import path from 'path';

export default {
  name: 'SidebarItem',
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      onlyOneChild: null
    };
  },
  methods: {
    hasOneShowingChild(children) {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false;
        } else {
          // temp set(will be used if only has one showing child )
          this.onlyOneChild = item;
          return true;
        }
      });
      if (showingChildren.length === 1) {
        return true;
      }
      return false;
    },
    resolvePath(...paths) {
      return path.resolve(this.basePath, ...paths);
    }
  }
};
</script>

<style lang="less" scoped>
</style>
