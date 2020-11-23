<template>
  <div class="page">
    <div class="filter-container">
      <el-form inline size="mini">
        <el-form-item label="应用ID">
          <el-input v-model="filterForm.appId"></el-input>
        </el-form-item>
        <el-form-item label="应用名称">
          <el-input v-model="filterForm.appName"></el-input>
        </el-form-item>
        <el-form-item label="创建人">
          <el-input v-model="filterForm.creator"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="$refs['table'].setFilter()">查询</el-button>
          <el-button @click="$refs['table'].resetFilter()">重置条件</el-button>
          <el-button>新增应用</el-button>
        </el-form-item>
      </el-form>
    </div>

    <table-container style="flex:1" :columns="tableColumns" ref="table">
      <template slot="column">
        <el-table-column label="操作"></el-table-column>
      </template>
    </table-container>

    <el-dialog :visible.sync="dialog">
      <div slot="title">{{dialogType}}应用</div>

      <el-form label-width="80px">
        <el-form-item label="应用ID">
          <el-input v-model="dialogForm.appId"></el-input>
        </el-form-item>
        <el-form-item label="应用名称">
          <el-input v-model="dialogForm.appName"></el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="dialogForm.monitor"></el-input>
        </el-form-item>
        <el-form-item label="创建人">
          <el-input v-model="dialogForm.creator"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer">
        <el-button>取消</el-button>
        <el-button type="primary">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import TableContainer from '@web/components/Table/TableContainer';

export default {
  name: 'list',
  components: { TableContainer },
  data () {
    return {
      filterForm: {
        appId: '',
        appName: '',
        creator: ''
      },
      tableColumns: [
        {label: '应用ID', prop: 'appId'},
        {label: '应用名称', prop: 'appName'},
        {label: '负责人', prop: 'monitor'},
        {label: '创建人', prop: 'creator'},
        {label: '创建时间', prop: 'createdAt'}
      ],
      dialog: true,
      dialogType: '新增',
      dialogForm: {
        appId: '',
        appName: '',
        monitor: '',
        creator: ''
      }
    }
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
