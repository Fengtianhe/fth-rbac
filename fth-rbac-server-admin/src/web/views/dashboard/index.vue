<template>
  <div class="dashboard-container">
    <!--  顶部数据  -->
    <dashboard-top-card/>

    <div class="dashboard-body">
      <!--  过去一周接口请求量  -->
      <div class="chart">
        <dashborad-chart/>
      </div>
      <div class="message">
        <dashboard-message :messages="version"></dashboard-message>
      </div>
    </div>

    <div class="dashboard-copyright">
      <span>Version: {{version[0].version}}</span>
      <span>
        Copyleft © 2020-{{year}} Fengtianhe. All rights reserved.
      <a href="https://github.com/Fengtianhe/fth-rbac" target="_blank">https://github.com/Fengtianhe/fth-rbac</a>
      </span>
    </div>
  </div>
</template>

<script>


import DashboardTopCard from '@web/views/dashboard/components/DashboardTopCard';
import DashboradChart from '@web/views/dashboard/components/DashboradChart';
import DashboardMessage from '@web/views/dashboard/components/DashboardMessage';
import version from '@/common/assets/data/version';

export default {
  name: 'DashboardAdmin',
  components: { DashboardMessage, DashboradChart, DashboardTopCard },
  data() {
    return {
      year: new Date().getFullYear(),
      version
    };
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
      padding: 0 20px;

      .chart {
        flex: 1;
        margin-right: 20px;
        height: 100%;
      }

      .message {
        flex: 0 0 400px;
        height: 100%;
        padding: 0 0 20px 0;
      }
    }

    .dashboard-copyright {
      padding: 0 20px;
      font-size: 12px;
      flex: 0 0 30px;
      border-top: 1px solid #eee;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }
</style>
