<template>
  <div class="container">
    <div class="form">
      <el-form size="mini" label-width="120px" :model="formData" :rules="formRule" ref="form">
        <el-form-item label="资源名称:" prop="name">
          <el-input v-model="formData.name" maxlength="50" clearable show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="资源类型:" prop="type">
          <el-select v-model="formData.type">
            <el-option
                v-for="type in typeOptions"
                :key="type.value"
                :value="type.value"
                :label="type.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单展示" v-if="!isButton">
          <el-radio-group v-model="formData.inMenu">
            <el-radio :label="item.value" v-for="item in inMenuOptions" :key="item.value">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="父级ID:">
          <el-cascader
              v-model="formData.parentId"
              style="width: 100%"
              :options="parentIdTree"
              :props="treeProps"
              @change="value => handlerParentId(value)"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="Icon Class:" prop="icon" v-if="!isButton">
          <el-input v-model="formData.icon"></el-input>
        </el-form-item>
        <el-form-item label="页面URL:" prop="pageUrl" v-if="!isButton">
          <el-input v-model="formData.pageUrl"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="goBack()">取消</el-button>
          <el-button type="primary" @click="onSubmit()">确认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

import {MappingTools, ResourceMapping} from '@/common/mapping';
import {AppResourceService} from '@web/service';

export default {
  name: 'create',
  computed: {
    isButton: function () {
      const isButton = MappingTools.ValueEqMapping(
        ResourceMapping.type.button,
        this.formData.type
      );
      return isButton;
    }
  },
  data () {
    const self = this;
    return {
      initialization: true,
      formData: {
        inMenu: ResourceMapping.inMenu.show.value,
        name: '',
        type: ResourceMapping.type.page.value,
        parentId: '',
        menu: '',
        page: '',
        icon: '',
        pageUrl: '',
        sort: 0
      },
      inMenuOptions: MappingTools.TransferOptions(ResourceMapping.inMenu),
      formRule: {
        name: [
          {
            required: true,
            message: '请输入资源名称',
            trigger: ['trigger', 'change', 'blur']
          }
        ],
        type: [
          {
            required: true,
            message: '请选择资源类型',
            trigger: ['trigger', 'change', 'blur']
          }
        ],
        parentId: [
          {
            validator: (rule, value, callback) => {
              if (
                MappingTools.ValueEqMapping(
                  ResourceMapping.type.module,
                  self.formData.type
                ) ||
                value
              ) {
                callback();
              } else {
                callback(new Error('请选择资源父级资源'));
              }
            },
            trigger: ['trigger', 'change', 'blur']
          }
        ],
        pageUrl: [
          {
            validator: (rule, value, callback) => {
              if (!value) {
                callback();
              }
              if (!/^(\/[a-zA-Z0-9-]+)+$/.test(value)) {
                return callback(
                  new Error('请输入正确的资源地址，例如：/aaa/bbb/... 的形式')
                );
              }
              callback();
            },
            trigger: ['trigger', 'change', 'blur']
          }
        ]
      },
      typeOptions: MappingTools.TransferOptions(ResourceMapping.type),
      iconTypes: MappingTools.TransferOptions(ResourceMapping.iconType),
      moduleOptions: [],
      menuOptions: [],
      pageOptions: [],
      // 父级ID
      parentIdTree: [],
      treeProps: {
        value: 'id',
        label: 'resourceName',
        children: 'children',
        checkStrictly: true // 如果中间可以选择为True
      },
    };
  },
  async created () {
    await this.getTreeListFn();
    await this.getById();
  },
  watch: {
    isButton: function (n) {
      console.log('is button = ', n);
      this.treeProps.checkStrictly = !n;
    }
  },
  methods: {
    handlerParentId (value) {
      if (value && value.length) {
        this.formData.parentId = value[value.length - 1];
      }
    },
    async getTreeListFn () {
      const res = await AppResourceService.treeAll({
        type: ResourceMapping.type.page.value,
        appId: this.$route.query.appId
      });
      if (res.code === 200) {
        this.parentIdTree = res.data;
      }
    },
    async getById () {
      if (this.$route.query.id) {
        const response = await AppResourceService.getById(this.$route.query.id);
        if (response.code === 0) {
          this.formData = {
            ...this.formData,
            ...response.data
          };
        }
      }
      this.initialization = false;
    },
    async onSubmit () {
      const that = this;
      this.$refs.form.validate(async valid => {
        if (valid) {
          let response = await AppResourceService.save({
            resourceName: this.formData.name,
            pageUrl: this.formData.pageUrl,
            parentId: this.formData.parentId || '0',
            type: this.formData.type,
            appId: this.$route.query.appId,
            inMenu: this.formData.inMenu
          });

          this.$alert(`新增资源成功，ID；${response.data}`).then(() => {
            that.$router.back();
          });
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
  .container {
    background: #fff;
    padding: 0 10px 10px 10px;
    width: 100%;
    height: 100%;
    overflow: auto;

    /deep/ .el-page-header__content {
      font-size: 16px;
    }

    .form {
      padding-top: 30px;
      width: 750px;
      margin: 0 auto;

      .apiurl-item {
        display: flex;
        margin-bottom: 15px;

        &:last-child {
          margin-bottom: 0;
        }
      }
    }
  }

</style>
