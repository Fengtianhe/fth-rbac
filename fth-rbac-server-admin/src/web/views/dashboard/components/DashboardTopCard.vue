<template>
  <div class="top-card">
    <el-card v-for="obj in cards" :key="obj.title">
      <div class="card-title">{{obj.title}}</div>
      <div class="card-body">
        <div class="card-icon">
          <span :class="obj.icon" :style="{'color': obj.color}"></span>
        </div>
        <div class="card-num">{{obj.num}}</div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { DashboardService } from '@web/service';

export default {
  name: 'DashboardTopCard',
  data() {
    return {
      cards: [
        { title: '应用数', icon: 'iconfont friconall', color: '#d94f42', num: '-' },
        { title: '资源数', icon: 'iconfont friconclick', color: '#26c281', num: '-' },
        { title: '角色数', icon: 'iconfont friconzhanghucaozuo', color: '#58889c', num: '-' }
      ]
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      const response = await DashboardService.overview();
      this.cards = [
        { title: '应用数', icon: 'iconfont friconall', color: '#d94f42', num: response.data.numApp },
        { title: '资源数', icon: 'iconfont friconclick', color: '#26c281', num: response.data.numRes },
        { title: '角色数', icon: 'iconfont friconzhanghucaozuo', color: '#58889c', num: response.data.numRol }
      ];
    }
  }
};
</script>

<style scoped lang="less">
  .top-card {
    display: flex;
    height: 150px;

    .el-card {
      flex: 1;
      margin: 10px 5px 10px 5px;
      height: 130px;

      .card-title {
        font-size: 20px;
        font-weight: bolder;
      }

      .card-body {
        display: flex;

        .card-icon {
          .iconfont {
            font-size: 70px;
          }
        }

        .card-num {
          flex: 1;
          display: flex;
          justify-content: flex-end;
          align-items: center;
          font-size: 30px;
        }
      }
    }
  }
</style>
