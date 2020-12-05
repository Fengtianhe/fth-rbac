<template>
  <fr-resource-checkbox-group :data.sync="resources"/>
</template>

<script>
import {ResourceService} from "@web/service";
import {ResourceMapping} from "@common/mapping";
import FrResourceCheckboxGroup from './FrResourceCheckboxGroup'

export default {
  name: "FrResourceCheckboxTree",
  components: {FrResourceCheckboxGroup},
  props: {
    appId: {
      type: String,
      required: true
    },
    checked: {
      type: Array,
      default: function () {
        return []
      }
    }
  },
  data () {
    return {
      resources: []
    }
  },
  created () {
    if (this.appId) {
      this.fetchResources()
    }
  },
  watch: {
    appId: function () {
      this.fetchResources()
    },
    checked: {
      immediate: true,
      deep: true,
      handler: function () {
        this.resources = this.setChecked(this.resources)
      }
    }
  },
  methods: {
    async fetchResources () {
      const response = await ResourceService.treeAll({
        appId: this.appId,
        status: ResourceMapping.status.active.value
      })
      this.resources = this.setChecked(response.data)
    },
    /**
     * 设置checked字段，为了vue数据绑定
     * @param resources
     * @returns {*}
     */
    setChecked (resources) {
      if (!resources || !resources.length) {
        return []
      }
      for (let res of resources) {
        res.checked = this.checked.includes(res.id)
        if (res.children && res.children.length) {
          this.setChecked(res.children)
        }
      }
      return resources
    },
    /**
     * ref方法，获取选中的资源
     */
    getCheckedResource () {
      return this.getChecked(this.resources)
    },
    getChecked (resources) {
      let result = []
      for (let res of resources) {
        if (res.checked) {
          result.push(res)
        }
        if (res.children && res.children.length) {
          result = result.concat(this.getChecked(res.children))
        }
      }
      return result
    }
  }
}
</script>

<style scoped>

</style>
