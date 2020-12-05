<template>
  <div class="container">
    <el-form size="mini" inline @submit.native.prevent>
      <el-form-item>
        <fr-select-application v-model="filterForm.appId"></fr-select-application>
      </el-form-item>
      <el-form-item>
        <el-input
                v-model="keywords"
                placeholder="要搜索的资源名称"
                clearable
                @keyup.enter.native="onSearch()"
                :disabled="!filterForm.appId"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
                type="primary"
                @click="toCreateResource()"
                icon="el-icon-plus"
                :disabled="!filterForm.appId"
        >新增资源
        </el-button>
        <el-button
                type="danger"
                @click="resetSortFn"
                :disabled="!filterForm.appId"
        >恢复资源默认排序
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
            :data="resourcesFiltered"
            border
            stripe
            :row-class-name="tableRowClassName"
            row-key="id"
            :tree-props="{children: 'children'}"
            size="mini"
    >
      <el-table-column
              prop="id"
              label="资源ID"
              width="300"
              header-align="center"
      >
        <template slot-scope="scope">
          <!--    el table 在有children的情况下，第一级不会对齐      -->
          <span :style="{'marginLeft': scope.row.level === 1 && !scope.row.children ? '22px' : '0' }">{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column label="资源名称" header-align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.level > 1">
            <span v-for="n in scope.row.level - 1" :key="n" class="empty-nbsp"></span>
          </template>
          <i :class="resourceTypeIcon[scope.row.type]"></i>
          <span v-html="highlightSearchItem(scope.row)"></span>
        </template>
      </el-table-column>
      <el-table-column prop="hidden" label="资源是否在菜单栏展示" align="center" width="160">
        <template slot-scope="scope">
          <el-switch
                  size="mini"
                  v-if="scope.row.type == 1"
                  :disabled="isResourceDisabled(scope.row)"
                  @change="changeHidden(scope.row)"
                  v-model="scope.row.inMenu"
                  active-color="#13ce66"
                  inactive-color="#eee"
                  :active-value="show"
                  :inactive-value="hide">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="资源类型" :formatter="tableFormatResourceType" align="center"
                       width="80px"></el-table-column>
      <el-table-column prop="icon" label="Icon Class" align="center" width="100px"/>
      <el-table-column prop="status" label="资源状态" align="center" width="80px">
        <template slot-scope="scope">
          {{getStatusDes(scope.row.status)}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="right" header-align="center" width="350" fixed="right">
        <template slot-scope="scope">
          <el-button
                  icon="el-icon-top"
                  type="primary"
                  size="mini"
                  v-if="!isButton(scope.row.type)"
                  @click="updateSortFn(scope.row,-1)"
                  plain
          ></el-button>
          <el-button
                  icon="el-icon-bottom"
                  @click="updateSortFn(scope.row,1)"
                  type="primary"
                  size="mini"
                  v-if="!isButton(scope.row.type)"
                  plain
          ></el-button>
          <el-button
                  @click="toEdit(scope.row)"
                  size="mini"
                  plain
                  type="primary"
          >编辑
          </el-button>
          <el-button
                  v-if="isResourceActive(scope.row)"
                  @click="disableResource(scope.row)"
                  type="danger"
                  size="mini"
                  plain
          >停用
          </el-button>
          <el-button
                  v-if="isResourceDisabled(scope.row)"
                  @click="activeResource(scope.row)"
                  size="mini"
                  plain
                  type="success"
          >启用
          </el-button>
          <el-button
              slot="reference"
              type="danger"
              size="mini"
              plain
              @click="delFn(scope.row.id)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
import { ResourceService } from '@web/service';
import StringHelper from '@common/utils/StringHelper';
import { ResourceMapping, MappingTools } from '@common/mapping';
import FrSelectApplication from '@web/components/FrSelectApplication';

export default {
  name: 'AppResourceLists',
  components: { FrSelectApplication },
  data() {
    return {
      keywords: '',
      resources: [],
      resourcesFiltered: [],
      resourceTypeIcon: {
        [ResourceMapping.type.page.value]: 'el-icon-tickets',
        [ResourceMapping.type.button.value]: 'el-icon-thumb'
      },
      hide: ResourceMapping.inMenu.hide.value,
      show: ResourceMapping.inMenu.show.value,
      searchResource: [],
      filterForm: {
        appId: ''
      }
    };
  },
  watch: {
    'filterForm.appId': async function (n) {
      await this.$router.push({ path: this.$route.query.path, query: { appId: n } });
      await this.getAllResource();
    }
  },
  async created() {
    if (this.$route.query.appId) {
      this.filterForm.appId = this.$route.query.appId;
      await this.getAllResource();
    }
  },
  methods: {
    // 更新资源排序
    async updateSortFn(row, num) {
      let { sort, id } = row;
      let newSort = Number(sort) + num;
      const res = await ResourceService.updateSort(id, newSort);
      if (res.code === 200) {
        this.getAllResource();
      }
    },
    async resetSortFn() {
      await this.$alert('此操作只是将目录顺序进行重新编排，不影响现有顺序');
      const res = await ResourceService.resetSort();
      if (res.code === 200) {
        this.getAllResource();
      }
    },
    async delFn(id) {
      const confirm = await this.$confirm("确定删除资源？此操作不可逆。")
      if (confirm === 'confirm') {
        const res = await ResourceService.delete(id);
        if (res.code === 200) {
          this.getAllResource();
        }
      }
    },
    getStatusDes(status) {
      return MappingTools.MatchLabel(ResourceMapping.status, status);
    },
    isButton(type) {
      return MappingTools.ValueEqMapping(ResourceMapping.type.button, type);
    },
    async changeHidden(row) {
      const res = await ResourceService.updateInMenu(row.id, row.inMenu);
      if (res.code === 200) {
        this.getAllResource();
      }
    },
    async getAllResource() {
      const params = {
        appId: this.filterForm.appId
      };
      const response = await ResourceService.treeAll(params);
      if (response.code === 200) {
        const resource = this.setLevel(response.data);

        this.resources = resource;
        this.resourcesFiltered = resource;
      }
    },
    /**
     * 清洗资源层级
     */
    setLevel(resources, level = 1) {
      for (let obj of resources) {
        obj.level = level;
        if (obj.children && obj.children.length) {
          obj.children = this.setLevel(obj.children, level + 1);
        }
      }
      return resources;
    },
    tableFormatResourceType(row, column, cellValue) {
      return MappingTools.MatchLabel(
        ResourceMapping.type,
        cellValue
      );
    },
    tableRowClassName({ row }) {
      if (
        MappingTools.ValueEqMapping(
          ResourceMapping.status.disabled,
          row.status
        )
      ) {
        return 'disabled-row';
      }
      return '';
    },
    isResourceActive(row) {
      return MappingTools.ValueEqMapping(ResourceMapping.status.active, row.status);
    },
    isResourceDisabled(row) {
      return MappingTools.ValueEqMapping(ResourceMapping.status.disabled, row.status);
    },
    async activeResource(row) {
      const response = await ResourceService.updateStatus(row.id, ResourceMapping.status.active.value);
      if (response.code === 200) {
        this.$message.success('操作成功');
        await this.getAllResource();
      }
    },
    async disableResource(row) {
      const response = await ResourceService.updateStatus(row.id, ResourceMapping.status.disabled.value);
      if (response.code === 200) {
        this.$message.success('操作成功');
        await this.getAllResource();
      }
    },
    toCreateResource() {
      this.$router.push({ path: '/resource/create', query: { appId: this.filterForm.appId } });
    },
    toEdit(row) {
      this.$router.push({
        path: '/resource/edit',
        query: { resourceId: row.id, appId: row.appId }
      });
    },
    highlightSearchItem(resource) {
      if (resource && resource.resourceName) {
        if (this.keywords) {
          return StringHelper.highlight(resource.resourceName, this.keywords);
        }
        return resource.resourceName;
      }
      return '';
    },
    searchAll(arr, keywords) {
      for (var i in arr) {
        if (arr[i].name.indexOf(keywords) >= 0) {
          this.searchResource.push(arr[i]);
        } else {
          if (arr[i].menus) {
            this.searchAll(arr[i].menus, keywords);
          }
        }
      }
    },
    onSearch() {
      let keywords = this.keywords;
      if (!keywords) {
        this.resourcesFiltered = this.resources;
        return;
      }
      this.searchResource = [];
      this.searchAll(this.resources, keywords);
      this.resourcesFiltered = this.searchResource;
    },

  }
};
</script>

<style scoped lang="less">
  .container {
    display: flex;
    flex-direction: column;
    padding: 10px 10px 10px 10px;
    flex: 1;
    background: #fff;

    .empty-nbsp {
      display: inline-block;
      width: 20px;
    }

    /deep/ .el-table {
      .disabled-row {
        color: #ddd;
      }
    }
  }
</style>

