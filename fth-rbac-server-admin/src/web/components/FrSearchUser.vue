<template>
  <el-autocomplete
          class="inline-input"
          v-model="keywords"
          :fetch-suggestions="querySearch"
          placeholder="请输入用户名/登录名"
          @select="handleSelect"
          clearable
          :size="size"
          :trigger-on-focus="false"
          value-key="name"
          @clear="handleClear"
  ></el-autocomplete>
</template>

<script>
import { SysUserService } from '@web/service';

export default {
  name: 'FrSearchUser',
  model: {
    prop: 'value',
    event: 'select-user'
  },
  props: {
    value: {
      type: String
    },
    size: {
      type: String,
      default: 'mini'
    },
    clearOnSelect: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      keywords: ''
    };
  },
  watch: {
    'value': function (n) {
      if (!n) {
        this.keywords = '';
      }
    }
  },
  methods: {
    async querySearch(queryString, callback) {
      const response = await SysUserService.byKeywords({ keywords: this.keywords });
      for (let obj of response.data) {
        obj.name = obj.nickname || obj.username;
      }
      callback(response.data);
    },
    handleSelect(user) {
      this.$emit('select-user', user.id);
      this.$emit('select', user);
      if (this.clearOnSelect) {
        this.keywords = '';
      }
    },
    handleClear() {
      this.$emit('select-user', null);
    }
  }
};
</script>

<style scoped>

</style>
