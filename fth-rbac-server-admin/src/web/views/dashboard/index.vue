<template>
  <div class="dashboard-container">
    <!--  顶部数据  -->
    <dashboard-top-card/>

    <div class="dashboard-body">
      <!--  过去一周接口请求量  -->
      <div class="chart">
        <dashborad-chart/>
      </div>
      <div class="message"></div>
    </div>
  </div>
</template>

<script>


import DashboardTopCard from '@web/views/dashboard/components/DashboardTopCard';
import DashboradChart from '@web/views/dashboard/components/DashboradChart';

export default {
  name: 'DashboardAdmin',
  components: { DashboradChart, DashboardTopCard },
  data() {
    return {};
  },
  created() {

  },
  methods: {}
};
</script>

<style rel="stylesheet/less" lang="less" scoped>
  .dashboard-container {
    height: 100%;
    display: flex;
    flex-direction: column;

    .dashboard-body {
      flex: 1;
      display: flex;
      margin-top: 30px;

      .chart {
        flex: 1;
        margin-right: 20px;
        height: 100%;
      }

      .message {
        flex: 0 0 400px;
        height: 100%;
      }
    }
  }
</style>
