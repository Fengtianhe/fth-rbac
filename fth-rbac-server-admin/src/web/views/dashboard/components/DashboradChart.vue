<template>
  <div class="dashboard-chart" id="chart">
    <fr-echarts id="echarts" :option="options"/>
  </div>
</template>

<script>
import FrEcharts from '@web/components/FrEcharts';

export default {
  name: 'DashboradChart',
  components: { FrEcharts },
  data() {
    return {
      options: {
        title: {
          text: '过去7天请求量',
          left: '2%'
        },
        grid: {
          top: '10%',
          left: '1%',
          right: '4%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: 'line'
        }]
      }
    };
  }
};
</script>

<style scoped>
  .dashboard-chart {
    width: 100%;
    height: 100%;
  }
</style>
