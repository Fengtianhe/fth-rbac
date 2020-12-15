<template>
  <el-card class="message-container">
    <div v-for="(obj, index) in messages" :key="index" class="message-item">
      <span>版本：{{obj.version}}</span>
      <br>
      <span>时间：{{obj.date}}</span>
      <br>
      <span v-for="(n, ni) in obj.note" :key="n">
        {{ni + 1}}. {{n}} <br>
      </span>
    </div>
  </el-card>
</template>

<script>


export default {
  name: 'DashboardMessage',
  props: {
    messages: {
      type: Array
    }
  }
};
</script>

<style scoped lang="less">
  .message-container {
    font-size: 12px;
    height: 100%;
    overflow-y: auto;

    .message-item {
      margin-bottom: 10px;
    }
  }
</style>
