<template>
  <div class="page">
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

    <div class="add-env">

    </div>
  </div>
</template>

<script>
import {AppEnvService} from "@web/service";

export default {
  name: "list",
  data () {
    return {
      envs: []
    }
  },
  created () {
    this.fetchEnvs()
  },
  methods: {
    async fetchEnvs () {
      const appId = this.$route.query.appId
      if (!appId) {
        this.$message.warning("未知应用")
      }
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
    flex-direction: row;
    justify-content: space-between;
    padding: 15px;
    flex-wrap: wrap;

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
</style>
