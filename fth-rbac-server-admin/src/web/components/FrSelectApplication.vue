<template>
  <el-select v-model="value" placeholder="请选择应用">
    <el-option v-for="app in applications" :key="app.appId" :value="app.appId" :label="app.appName"></el-option>
  </el-select>
</template>

<script>
import {AppApplicationService} from "@web/service";

export default {
  name: "FrSelectApplication",
  model: {
    prop: 'appId',
    event: 'change'
  },
  props: {
    appId: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      value: this.appId,
      applications: []
    }
  },
  watch: {
    'appId': function () {
      this.value = this.appId
    },
    'value': function () {
      this.$emit('change', this.value)
    }
  },
  created () {
    this.fetchAllApplication()
  },
  methods: {
    async fetchAllApplication () {
      const response = await AppApplicationService.all()
      if (response.code === 200) {
        this.applications = response.data
      }
    }
  }
}
</script>

<style scoped>

</style>
