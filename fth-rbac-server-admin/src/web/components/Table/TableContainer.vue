<template>
  <div class="table-container">
    <div class="table">
      <el-table stripe>
        <div v-if="columns.length >0"></div>
        <slot v-else name="column"></slot>
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
    }
  },
  data() {
    return {
      baseUrl: '/api/app/application/list',
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
