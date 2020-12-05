<template>
  <div class="page">
    <div class="filter-container">
      <el-form inline size="mini">
        <el-form-item>
          <fr-select-application v-model="filterForm.appId"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="toCreateRole()" :disabled="!filterForm.appId">添加角色</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <table-container
          ref="table"
          :url="baseUrl"
          :auto-load="false"
          :columns="tableCols"
          :table-ops="tableOps"
          :filters.sync="filterForm"
      >
      </table-container>
    </div>
  </div>
</template>

<script>
import FrSelectApplication from "@web/components/FrSelectApplication";
import {TableContainer} from 'element-table-mixin'
import {RoleService} from '@/web/service'

export default {
  name: "list",
  components: {FrSelectApplication, TableContainer},
  data () {
    return {
      baseUrl: RoleService.URL_ROLE_LIST,
      tableCols: [
        {label: '角色ID', prop: 'id'},
        {label: '角色名称', prop: 'roleName'}
      ],
      tableOps: [
        {name: '启用', command: 'ENABLED', hide: this.disabledStatusBtn},
        {name: '停用', command: 'DISABLED', hide: !this.disabledStatusBtn},
        {name: '权限配置', command: 'RESOURCE', handle: this.toDeployResource}
      ],
      filterForm: {
        appId: this.$route.query.appId || ""
      }
    }
  },
  watch: {
    'filterForm.appId': function () {
      this.$refs['table'].setTableFilter()
    }
  },
  mounted () {
    if (this.$route.query.appId) {
      this.$refs['table'].setTableFilter()
    }
  },
  methods: {
    disabledStatusBtn (scope) {
      return true
    },
    toCreateRole () {
      this.$router.push({path: '/role/deploy', query: {appId: this.filterForm.appId}})
    },
    toDeployResource (scope) {
      this.$router.push({path: '/role/deploy', query: {roleId: scope.row.id, appId: scope.row.appId}})
    }
  }
}
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
