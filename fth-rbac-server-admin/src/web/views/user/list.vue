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
          <el-button @click="onAddUser">新增用户</el-button>
        </el-form-item>
      </el-form>
    </div>

    <table-container
            :url='tableDataUrl'
            style="flex:1"
            :columns="tableColumns"
            ref="table">
      <template slot="column">
        <el-table-column label="操作" width="100px">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" plain v-if="showEditDialog(scope)">编辑</el-button>
            <el-button size="mini" type="success" plain v-if="isStatusDisabled(scope)">启用</el-button>
            <el-button size="mini" type="danger" plain v-if="!isStatusDisabled(scope)">停用</el-button>
          </template>
        </el-table-column>
      </template>
    </table-container>
  </div>
</template>

<script>
import { TableContainer } from 'element-table-mixin';
import { UserService } from '@web/service';
import { tableFormatDate } from '@common/utils/table';
import { MappingTools, UserMapping } from '@common/mapping';

export default {
  name: 'list',
  components: {
    TableContainer
  },
  data() {
    return {
      tableDataUrl: UserService.URL_SYS_USER_LIST,
      filterForm: {},
      tableColumns: [
        { label: '用户ID', prop: 'id' },
        { label: '用户名', prop: 'username' },
        { label: '管理员', prop: 'admin', formatter: this.formatAdmin },
        { label: '状态', prop: 'status', formatter: this.formatStatus },
        { label: '创建时间', prop: 'createdAt', formatter: tableFormatDate }
      ]
    };
  },
  methods: {
    isStatusDisabled(scope) {
      return MappingTools.ValueEqMapping(UserMapping.status.disabled, scope.row.status);
    },
    formatStatus(row) {
      return MappingTools.MatchLabel(UserMapping.status, row.status);
    },
    formatAdmin(row) {
      return MappingTools.MatchLabel(UserMapping.admin, row.admin);
    },
    onAddUser() {
      this.$prompt('请输入用户名').then(async res => {
        if (res.value && res.action === 'confirm') {
          const response = await UserService.add({ username: res.value });
          this.$alert(`创建成功，初始密码为：${response.data}`);
          this.$refs['table'].resetFilter();
        }
      });
    },
    showEditDialog(scope) {

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
