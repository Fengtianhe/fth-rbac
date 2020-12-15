<template>
  <div class="chart-box" ref="chartBox">
    <div class="main" :id="id" :ref="id + 'canvas'"></div>
  </div>
</template>

<script>
import echarts from 'echarts/lib/echarts';
// import chinaJson from 'echarts/map/json/china.json';
import 'echarts/lib/chart/bar';
import 'echarts/lib/chart/line';
// import 'echarts-wordcloud';
import 'echarts/theme/macarons.js';
import 'echarts/lib/component/title';
// echarts.registerMap('china', chinaJson);
export default {
  name: 'FrEcharts',
  props: {
    w: Number, // 用于拖拉债宽度改变
    h: Number, // 用于拖拉债高度改变
    id: {
      type: String,
      default: 'main'
    },
    option: Object
  },
  data() {
    return {
      echs: ''
    };
  },
  // beforeDestroy() {
  //   console.log("removeEventListener");
  //   this.echs.dispose();
  //   window.removeEventListener("resize", this.resizeCallback);
  // },
  watch: {
    w: function (val) {
      if (val) {
        this.resizeCallback();
      }
    },
    h: function (val) {
      if (val) {
        this.resizeCallback();
      }
    },
    option: {
      deep: true,
      handler(val) {
        console.log(val);
        this.$nextTick(function () {
          if (val) {
            this.renderInit();
          }
        });
      },
      immediate: true
    }
  },
  mounted() {
    // this.$nextTick(function() {
    //   this.renderInit();
    // });
  },
  methods: {
    renderInit() {
      this.echs = echarts.init(this.$refs[this.id + 'canvas']);
      this.echs.setOption(this.option, true);
      this.echs.resize();
      window.addEventListener('resize', this.resizeCallback);
      this.$once('hook:beforeDestroy', () => {
        console.log('$once  beforeDestroy => removeEventListener');
        window.removeEventListener('resize', this.resizeCallback);
      });
    },
    resizeCallback() {
      this.echs.resize();
      // setTimeout(() => {
      // }, 500);
    }
  }
};
</script>

<style>
  .chart-box {
    height: 100%;
    width: 100%;
  }

  .main {
    height: 100%;
    width: 100%;
  }
</style>
