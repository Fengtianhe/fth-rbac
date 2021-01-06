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
          <fr-search-user v-model="filterForm.creator"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="$refs['table'].setFilter()">查询</el-button>
          <el-button @click="$refs['table'].resetFilter()">重置条件</el-button>
          <el-button @click="showDetailDialog({})">新增应用</el-button>
        </el-form-item>
      </el-form>
    </div>

    <table-container
            :url='tableDataUrl'
            style="flex:1"
            :columns="tableColumns"
            ref="table">
      <template slot="column">
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button size="mini" @click="showEditDialog(scope)" type="primary" plain>编辑</el-button>
            <el-button
                    size="mini"
                    @click="disableApp(scope)"
                    type="danger"
                    plain
                    v-if="isAppEnabled(scope)"
            >停用
            </el-button>
            <el-button
                    size="mini"
                    @click="enableApp(scope)"
                    type="success"
                    plain
                    v-if="isAppDisabled(scope)"
            >启用
            </el-button>
          </template>
        </el-table-column>
      </template>
    </table-container>

    <el-dialog :visible.sync="dialog">
      <div slot="title">{{dialogType}}应用</div>

      <el-form label-width="80px">
        <el-form-item label="应用ID" required>
          <el-input v-model="dialogForm.appId" :disabled="dialogType!=='新增'"></el-input>
        </el-form-item>
        <el-form-item label="应用名称" required>
          <el-input v-model="dialogForm.appName"></el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <fr-search-user @select="onSelectDeveloper" :clear-on-select="true"/>
          <br>
          <el-tag
                  v-for="(developer, idx) in dialogForm.developers"
                  :key="developer.id"
                  closable
                  @close="onRemoveDeveloper(idx)"
          >
            {{developer.nickname || developer.username}}
          </el-tag>
        </el-form-item>
      </el-form>

      <div slot="footer">
        <el-button @click="onCancelSubmit()">取消</el-button>
        <el-button type="primary" @click="onConfirmSubmit()">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { TableContainer } from 'element-table-mixin';
import { AppService } from '@web/service';
import { tableFormatDate } from '@common/utils/table';
import { ApplicationMapping, MappingTools } from '@common/mapping';
import FrSearchUser from '@web/components/FrSearchUser';

export default {
  name: 'list',
  components: { FrSearchUser, TableContainer },
  data() {
    return {
      tableDataUrl: AppService.URL_APP_APPLICATION_LIST,
      filterForm: {
        appId: '',
        appName: '',
        creator: ''
      },
      tableColumns: [
        { label: '应用ID', prop: 'appId' },
        { label: '应用名称', prop: 'appName' },
        { label: '状态', prop: 'status', formatter: this.matchAppStatus },
        { label: '客户端域', prop: 'domain', render: this.renderDomain },
        { label: '负责人', prop: 'developers', formatter: this.formatDevelopers },
        { label: '创建人', prop: 'creatorName' },
        { label: '创建时间', prop: 'createdAt', align: 'center', width: '130px', formatter: tableFormatDate }
      ],
      dialog: false,
      dialogType: '新增',
      dialogForm: {
        appId: '',
        appName: '',
        developers: []
      }
    };
  },
  methods: {
    showDetailDialog(data = {}) {
      this.dialogForm = data;
      this.dialog = true;
    },
    onCancelSubmit() {
      this.dialog = false;
      this.dialogForm = {};
    },
    async onConfirmSubmit() {
      if (this.dialogType === '编辑') {
        await AppService.update({
          ...this.dialogForm,
          developers: this.dialogForm.developers.map(o => o.id)
        });
        this.$message.success('编辑成功');
      } else {
        await AppService.add(this.dialogForm);
        this.$message.success('添加成功');
      }
      this.onCancelSubmit();
      this.$refs['table'].resetFilter();
    },
    showEditDialog(scope) {
      this.dialog = true;
      this.dialogType = '编辑';
      this.dialogForm.appId = scope.row.appId;
      this.dialogForm.appName = scope.row.appName;
      this.dialogForm.developers = scope.row.developers || [];
    },
    onSelectDeveloper(user) {
      const idx = this.dialogForm.developers.findIndex(u => user.id === u.id);
      if (idx !== -1) {
        this.$message.info('已存在该用户');
      } else {
        this.dialogForm.developers.push(user);
      }
    },
    onRemoveDeveloper(idx) {
      this.dialogForm.developers.splice(idx, 1);
    },
    matchAppStatus(row, column, cellValue) {
      return MappingTools.MatchLabel(ApplicationMapping.status, cellValue);
    },
    isAppEnabled(scope) {
      return MappingTools.ValueEqMapping(ApplicationMapping.status.enabled, scope.row.status);
    },
    isAppDisabled(scope) {
      return MappingTools.ValueEqMapping(ApplicationMapping.status.disabled, scope.row.status);
    },
    formatDevelopers(row) {
      let devnames = [];
      for (let dev of row.developers) {
        devnames.push(dev.nickname || dev.username);
      }
      return devnames.join(',');
    },
    renderDomain(scope) {
return `<div style></div>`
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
