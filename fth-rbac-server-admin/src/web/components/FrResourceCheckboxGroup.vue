<template>
  <div>
    <template v-if="isButton">
      <el-checkbox v-for="(dt, idx) in data" :key="idx" v-model="dt.checked" @change="onChanged(dt)">
        <i :class="resourceTypeIcon[dt.type]"></i>
        {{dt.resourceName}}
        <span class="tip">({{dt.id}})</span>
      </el-checkbox>
    </template>
    <template v-else>
      <div v-for="(dt, idx) in data" :key="idx">
        <el-checkbox v-model="dt.checked" @change="onChanged(dt)">
          <i :class="resourceTypeIcon[dt.type]"></i>
          {{dt.resourceName}}
          <span class="tip">({{dt.id}})</span>
        </el-checkbox>
        <br>
        <fr-resource-checkbox-group
            style="padding: 0 30px"
            :data.sync="dt.children"
            v-if="dt.children && dt.children.length"
            @child-checked="onChildChecked"
        />
      </div>
    </template>
  </div>
</template>

<script>
import {MappingTools, ResourceMapping} from "@common/mapping";

export default {
  name: "FrResourceCheckboxGroup",
  props: {
    data: {
      type: Array
    }
  },
  data () {
    return {
      resourceTypeIcon: {
        [ResourceMapping.type.page.value]: 'el-icon-tickets',
        [ResourceMapping.type.button.value]: 'el-icon-thumb'
      }
    }
  },
  computed: {
    isButton () {
      if (this.data && this.data.length) {
        return MappingTools.ValueEqMapping(ResourceMapping.type.button, this.data[0].type)
      }
    }
  },
  methods: {
    /**
     * 选中按钮，则上级所有页面资源都应被选中
     */
    onChanged (res) {
      if (res.checked) {
        this.$emit("child-checked", res)
      } else {
        this.uncheckChildren(res)
      }
    },
    /**
     * 处理子资源被选中
     * @param res
     */
    onChildChecked (res) {
      const parentId = res.parentId
      const idx = this.data.findIndex(obj => obj.id === parentId)
      // 找到父级资源
      const obj = this.data[idx]
      obj.checked = true
      this.data.splice(idx, 1, obj)

      // 如果还存在父级资源，基础触发上级
      if (obj.parentId !== '0') {
        this.$emit("child-checked", obj)
      }
    },
    /**
     * 取消选择，需要取消选择下面所有资源
     * @param res
     */
    uncheckChildren (res) {
      if (res.children && res.children.length) {
        for (let child of res.children) {
          child.checked = false
          this.uncheckChildren(child)
        }
      }
    }
  }
}
</script>

<style scoped>
  .tip {
    font-size: 12px;
    color: #d3d3d3;
  }
</style>
