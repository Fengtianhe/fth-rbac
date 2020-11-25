<template>
  <div class="table-container">
    <div class="table">
      <el-table stripe>
        <el-table-column
                v-for="col in columns"
                :key="`${col.label}-${col.prop}`"
                :label="col.label"
                :prop="col.prop"
        ></el-table-column>
        <slot name="column"></slot>
      </el-table>
    </div>

    <div class="pagination">
      <el-pagination></el-pagination>
    </div>
  </div>
</template>

<script>
import TableMixin from '@common/mixin/TableMixin';

export default {
  name: 'TableContainer',
  mixins: [TableMixin],
  props: {
    columns: {
      type: Array,
      default: function () {
        return [];
      }
    },
    url: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      baseUrl: this.url,
      tableData: {},
      filterForm: {}
    };
  },
  methods: {}
};
</script>

<style scoped lang="less">
  .table-container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;

    .table {
      flex: 1;
    }

    .pagination {
      padding: 0 10px;
      flex: 0 0 50px;
      display: flex;
      justify-content: flex-end;
      align-items: center;
    }
  }
</style>
