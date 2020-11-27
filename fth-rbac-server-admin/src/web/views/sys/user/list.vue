<template>
  <div class="page">
    <div class="filter-container">
      <el-form inline size="mini">
        <el-form-item label="用户名">
          <el-input v-model="filterForm.username"></el-input>
        </el-form-item>
        <el-form-item label="创建人">
          <el-input v-model="filterForm.creator"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="$refs['table'].setFilter()">查询</el-button>
          <el-button @click="$refs['table'].resetFilter()">重置条件</el-button>
          <el-button @click="showDetailDialog({})">新增用户</el-button>
        </el-form-item>
      </el-form>
    </div>

    <table-container
            :url='tableDataUrl'
            style="flex:1"
            :columns="tableColumns"
            ref="table">
      <template slot="column">
        <el-table-column label="操作"></el-table-column>
      </template>
    </table-container>
  </div>
</template>

<script>
import TableContainer from '@web/components/Table/TableContainer';
import { SysUserService } from '@web/service';

export default {
  name: 'list',
  components: {
    TableContainer
  },
  data() {
    return {
      tableDataUrl: SysUserService.URL_SYS_USER_LIST,
      filterForm: {},
      tableColumns: [
        { label: '用户ID', prop: 'id' },
        { label: '用户名', prop: 'username' },
        { label: '状态', prop: 'status' },
        { label: '创建时间', prop: 'createdAt' }
      ]
    };
  }
};
</script>

<style scoped lang="less">
  .page {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 5px;

    .filter-container {
      padding: 10px;
    }
  }
</style>
