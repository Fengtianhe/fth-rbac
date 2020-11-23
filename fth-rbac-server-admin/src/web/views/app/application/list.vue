<template>
  <div class="page">
    <div class="filter-container">
      <el-form inline size="mini">
        <el-form-item></el-form-item>
      </el-form>
    </div>

    <table-container style="flex:1">
    </table-container>
  </div>
</template>

<script>

import TableContainer from '@web/components/Table/TableContainer';

export default {
  name: 'list',
  components: { TableContainer },
};
</script>

<style scoped lang="less">
  .page {
    height: 100%;
    display: flex;
  }
</style>
