<template>
  <div class="page">
    <div class="form">
      <el-form size="mini" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="roleName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="$router.back()">取消</el-button>
          <el-button type="primary" @click="onSubmit()">确定</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="resource">
      <el-form size="mini" label-width="80px">
        <el-form-item label="资源配置">
          <span style="font-size: 12px; color: #d3d3d3">(选中子资源，会自动选中上级资源，取消选中父资源，会取消选中所有下级资源)</span>
          <br>
          <fr-resource-checkbox-tree
              :app-id="appId"
              :checked="checkedResourceIds"
              ref="resourceCheckboxTree"
          />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import FrResourceCheckboxTree from "@web/components/FrResourceCheckboxTree";
import {RoleService} from "@web/service";

export default {
  name: "RoleDeploy",
  components: {FrResourceCheckboxTree},
  data () {
    return {
      appId: this.$route.query.appId || '',
      roleId: this.$route.query.roleId || '',
      roleName: '',
      checkedResourceIds: []
    }
  },
  created () {
    this.getRole()
  },
  methods: {
    async onSubmit () {
      const checkedResource = this.$refs['resourceCheckboxTree'].getCheckedResource();
      const checkedIds = checkedResource.map(o => o.id)
      RoleService.assign({
        roleName: this.roleName,
        roleId: this.roleId,
        resourceIds: checkedIds,
        appId: this.appId
      }).then(() => {
        this.$message.success('操作成功')
        this.$router.back()
      })
    },
    async getRole () {
      if (this.roleId) {
        const roleResp = await RoleService.getRole(this.roleId)
        this.roleName = roleResp.data.roleName

        const response = await RoleService.resourceIdsAssigned(this.roleId)
        this.checkedResourceIds = response.data
      }
    }
  }
}
</script>

<style scoped lang="less">
  .page {
    padding: 15px;
    display: flex;

    .form {
      flex: 0 0 250px;
    }

    .resource {
      flex: 1;
    }
  }
</style>
