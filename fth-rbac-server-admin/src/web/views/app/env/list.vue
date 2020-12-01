<template>
  <div class="page">
    <div class="filter-container">
      <el-form size="mini" inline>
        <el-form-item>
          <fr-select-application v-model="filterForm.appId"/>
        </el-form-item>
      </el-form>
    </div>

    <div class="envcards">
      <div class="env-item" v-for="env in envs" :key="env.envId">
        <div class="env-item__top">
          <div class="env-item__id">
            {{env.envId}}
            <div class="env-item__name">({{env.envName}})</div>
          </div>
        </div>
        <div class="env-item__bottom">
          <div class="env-item__creator">{{env.creatorName}} 创建于 {{env.createdAt | dateFormat}}</div>
        </div>
      </div>

      <div class="add-env"></div>
    </div>
  </div>
</template>

<script>
import {AppEnvService} from "@web/service";
import FrSelectApplication from "@web/components/FrSelectApplication";

export default {
  name: "list",
  components: {FrSelectApplication},
  data () {
    return {
      envs: [],
      filterForm: {
        appId: ''
      }
    }
  },
  created () {
    this.fetchEnvs()
  },
  watch: {
    "filterForm.appId": function (n) {
      this.$router.push({path: this.$route.query.path, query: {appId: n}})
      this.fetchEnvs()
    }
  },
  methods: {
    async fetchEnvs () {
      const appId = this.$route.query.appId
      if (!appId) {
        return
      }
      this.filterForm.appId = appId
      const response = await AppEnvService.all(appId)
      if (response.code === 200) {
        this.envs = response.data
      }
    }
  }
}
</script>

<style scoped lang="less">
  @import "../../../../common/assets/style/base/variable";

  .page {
    display: flex;
    flex-direction: column;
    padding: 15px;

    .filter-container {

    }

    .envcards {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      flex-wrap: wrap;
      flex: 1;

      .env-item {
        border: 1px solid #eee;
        border-radius: 5px;
        width: 30%;
        height: 80px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 10px;
        margin-bottom: 10px;

        .env-item__top {
          display: flex;
          flex-direction: row;
          justify-content: space-between;

          .env-item__id {
            text-transform: uppercase;
            display: flex;
            flex-direction: row;
            align-items: flex-end;
            font-size: 16px;

            .env-item__name {
              font-size: 12px;
              margin-left: 5px;
            }
          }
        }

        .env-item__bottom {
          display: flex;
          justify-content: flex-end;

          .env-item__creator {
            font-size: 12px;
          }
        }


      }

      &:after {
        content: '';
        width: 30%;
        border: 1px solid transparent;
      }

      .add-env {
        border: 1px solid;
        border-radius: 5px;
        width: 30%;
        height: 80px;
        position: relative;
        color: #eee;

        &::before {
          content: '';
          position: absolute;
          left: 50%;
          top: 50%;
          width: 60px;
          margin-left: -30px;
          margin-top: -2.5px;
          border-top: 5px solid;

        }

        &::after {
          content: '';
          position: absolute;
          left: 50%;
          top: 50%;
          height: 60px;
          margin-left: -2.5px;
          margin-top: -30px;
          border-left: 5px solid;
        }

        &:hover {
          color: @primary-color;
          cursor: pointer;
        }
      }
    }
  }
</style>
