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

    <el-table :data="resourcesFiltered" border stripe :row-class-name="tableRowClassName" row-key="id"
              :tree-props="{children: 'menus'}" size="mini">
      <el-table-column prop="id" label="资源ID" width="300" header-align="center" show-overflow-tooltip></el-table-column>
      <el-table-column label="资源名称" header-align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.type > 1">
            <span v-for="n in scope.row.type - 1" :key="n" class="empty-nbsp"></span>
          </template>
          <i :class="resourceTypeIcon[scope.row.type]"></i>
          <span v-html="highlightSearchItem(scope.row)"></span>
        </template>
      </el-table-column>
      <el-table-column prop="hidden" label="资源是否在菜单栏展示" align="center" width="160">
        <template slot-scope="scope">
          <el-switch v-if="scope.row.type == 1" :disabled="isResourceDisabled(scope.row)"
                     @change="changeHidden(scope.row)" v-model="scope.row.inMenu" active-color="#13ce66"
                     inactive-color="#eee" :active-value="show" :inactive-value="hide">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="资源类型" :formatter="tableFormatResourceType" align="center"
                       width="80px"></el-table-column>
      <el-table-column prop="icon" label="icon" align="center" width="100px" show-overflow-tooltip>
        <template slot-scope="scope">
          <i v-if="scope.row.icon" :class="['iconfont', scope.row.icon]"></i>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="资源状态" align="center" width="80px">
        <template slot-scope="scope">
          {{getStatusDes(scope.row.status)}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250" fixed="right">
        <template slot-scope="scope">
          <el-button icon="el-icon-top" type="primary" size="mini" v-if="!isButton(scope.row.type)" plain
                     @click="updateSortFn(scope.row,-1)"></el-button>
          <el-button icon="el-icon-bottom" @click="updateSortFn(scope.row,1)" type="primary" size="mini"
                     v-if="!isButton(scope.row.type)" plain></el-button>
          <el-popover placement="top" width="160" v-model="visible">
            <p>确定删除改资源</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="visible = false">取消</el-button>
              <el-button type="primary" size="mini" @click="visible = false">确定</el-button>
            </div>
            <el-button slot="reference" type="text" icon="el-icon-delete">删除</el-button>
          </el-popover>
          <el-button @click="toEdit(scope.row)" size="mini" plain type="success">编辑
          </el-button>
          <el-button v-if="isResourceActive(scope.row)" @click="disableResource(scope.row)" type="danger" size="mini"
                     plain>停用
          </el-button>
          <el-button v-if="isResourceDisabled(scope.row)" @click="activeResource(scope.row)" size="mini" plain
                     type="danger">启用
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
import {AppResourceService} from '@/web/service';
import StringHelper from '@/common/utils/StringHelper';
import {ResourceMapping, MappingTools} from "@/common/mapping"
import FrSelectApplication from "@web/components/FrSelectApplication";

export default {
  name: 'AppResourceLists',
  components: {FrSelectApplication},
  data () {
    return {
      loading: false,
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
    'filterForm.appId': function (n) {
      this.$router.push({path: this.$route.query.path, query: {appId: n}})
      this.getAllResource()
    }
  },
  async created () {
    if (this.$route.query.appId) {
      this.filterForm.appId = this.$route.query.appId
      await this.getAllResource();
    }
  },
  methods: {
    // 更新资源排序
    async updateSortFn (row, num) {
      let {sort, id} = row;
      let newSort = Number(sort) + num
      const res = await AppResourceService.updateSort(id, newSort);
      if (res.code === 200) {
        this.getAllResource();
      }
    },
    async resetSortFn () {
      const res = await AppResourceService.resetSort();
      if (res.code === 200) {
        this.$notify.success('重置成功')
        this.getAllResource();
      }
    },
    async delFn (id) {
      const res = await AppResourceService.delRrsource(id);
      if (res.code === 200) {
        this.getAllResource()
      }
    },
    getStatusDes (status) {
      return MappingTools.MatchLabel(ResourceMapping.status, status)
    },
    isButton (type) {
      return MappingTools.ValueEqMapping(ResourceMapping.type.button, type)
    },
    async changeHidden (row) {
      let bool = MappingTools.ValueEqMapping(ResourceMapping.inMenu.hide, row.inMenu)
      const res = await AppResourceService.switchResource({id: row.id, inMenu: bool})
      if (res.code === 200) {
        this.getAllResource()
      }
    },
    async getAllResource () {
      this.loading = true;
      const response = await AppResourceService.getList();
      this.loading = false;
      if (response.code === 0) {
        this.resources = response.data;
        this.resourcesFiltered = response.data;
      }
    },
    tableFormatResourceType (row, column, cellValue) {
      return MappingTools.matchLabel(
        ResourceMapping.type,
        cellValue
      );
    },
    tableRowClassName ({row}) {
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
    isResourceActive (row) {
      return MappingTools.ValueEqMapping(
        ResourceMapping.status.active,
        row.status
      );
    },
    isResourceDisabled (row) {
      return MappingTools.ValueEqMapping(
        ResourceMapping.status.disabled,
        row.status
      );
    },
    async activeResource (row) {
      const response = await AppResourceService.updateStatusActive({
        powerId: row.id
      });
      if (response.code === 0) {
        this.$message.success('操作成功');
        await this.getAllResource();
      }
    },
    async disableResource (row) {
      const response = await AppResourceService.updateStatusDisabled({
        powerId: row.id
      });
      if (response.code === 0) {
        this.$message.success('操作成功');
        await this.getAllResource();
      }
    },
    toDetail (row) {
      this.$router.push({
        path: '/system/resource/detail',
        query: {id: row.id}
      });
    },
    toCreateResource () {
      this.$router.push({path: '/application/resource/create', query: {appId: this.filterForm.appId}});
    },
    toEdit (row) {
      this.$router.push({
        path: '/system/resource/edit',
        query: {id: row.id}
      });
    },
    highlightSearchItem (resource) {
      if (resource && resource.name) {
        if (this.keywords) {
          return StringHelper.highlight(resource.name, this.keywords);
        }
        return resource.name;
      }
      return '';
    },
    searchAll (arr, keywords) {
      for (var i in arr) {
        if (arr[i].name.indexOf(keywords) >= 0) {
          this.searchResource.push(arr[i])
        } else {
          if (arr[i].menus) {
            this.searchAll(arr[i].menus, keywords);
          }
        }
      }
    },
    onSearch () {
      let keywords = this.keywords;
      if (!keywords) {
        this.resourcesFiltered = this.resources;
        return;
      }
      this.searchResource = []
      this.searchAll(this.resources, keywords)
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

    .table-wrap {
      overflow: auto;

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
  }
</style>

