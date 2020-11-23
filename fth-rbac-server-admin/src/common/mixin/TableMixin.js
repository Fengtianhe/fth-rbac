import Utils from '@common/utils/request';
import axios from 'axios';
import { getPropByPath } from '@common/utils/objectHelper';
// 每页默认数量
let PAGE_SIZE_DEFAULT = 15;
// 默认页码
let PAGE_NUM_DEFAULT = 1;
// 请求的页码字段
let REQUEST_PAGENUM_FIELD = 'pageNum';
// 请求的每页条数字段
let REQUEST_PAGESIZE_FIELD = 'pageSize';
// 列表数据对应字段
let RESPONSE_LIST_FIELD = 'lists';
// 每页数量对应的字段
let RESPONSE_PAGESIZE_FIELD = 'pageSize';
// 页码对应的字段
let RESPONSE_PAGENUM_FIELD = 'pageNum';
// 总条数对应的字段
let RESPONSE_TOTAL_FIELD = 'total';

const TableMixin = {
  mounted() {
    if (this.baseUrl) {
      this.setFilter();
    }
  },
  methods: {
    /**
     * 从filterForm中获取筛选项
     */
    setTableFilter() {
      const self = this;
      const baseUrl = self.baseUrl;
      if (baseUrl) {
        console.log('table-mixin: mounted function; baseUrl: ', baseUrl);
        if (!this.filterForm) {
          throw new Error('请在data中定义filterForm属性，类型为Object');
        }
        let urlQuery = self.$route.query;
        if (urlQuery && Object.keys(urlQuery).length > 0) {
          urlQuery = JSON.parse(JSON.stringify(urlQuery));

          for (let key in urlQuery) {
            // 如果筛选项存在
            if (typeof (this.filterForm[key]) !== 'undefined') {
              // 如果定义了筛选项的类型
              if (this.filterType && this.filterType[key]) {
                if (this.filterType[key] === 'array' && typeof (urlQuery[key]) === 'string') {
                  this.filterForm[key] = [urlQuery[key]];
                } else if (this.filterType[key] === 'boolean' && typeof (urlQuery[key]) === 'string') {
                  this.filterForm[key] = urlQuery[key] === 'true';
                }
              } else {
                this.filterForm[key] = urlQuery[key];
              }
            }
          }
        }

        if (urlQuery[REQUEST_PAGENUM_FIELD]) {
          self.tableData[RESPONSE_PAGENUM_FIELD] = urlQuery[REQUEST_PAGENUM_FIELD] ? parseInt(urlQuery[REQUEST_PAGENUM_FIELD]) : PAGE_NUM_DEFAULT;
        }
        if (urlQuery[REQUEST_PAGESIZE_FIELD]) {
          self.tableData[RESPONSE_PAGESIZE_FIELD] = urlQuery[REQUEST_PAGESIZE_FIELD] ? parseInt(urlQuery[REQUEST_PAGESIZE_FIELD]) : PAGE_SIZE_DEFAULT;
        }

        self.setFilter();
      }
    },
    resetFilter(filters = {}) {
      const self = this;
      for (let f in self.filterForm) {
        self.filterForm[f] = null;
      }
      self.setUrlFilters(filters);
    },
    setFilter() {
      const self = this;
      self.setUrlFilters(self.getFilters());
    },
    getFilters: function () {
      const s = {};
      const self = this;
      let filters = self.filterForm;
      const invisible = ['vendorId'];
      for (const f in filters) {
        if (invisible.includes(f)) {
          continue;
        }
        let filter = filters[f];
        if (filter) {
          if (typeof (filter) === 'number' || typeof (filter) === 'string' || typeof (filter) === 'boolean') {
            s[f] = filter;
            continue;
          }
          if (typeof (filter) === 'object' && filter !== null) {
            const match = JSON.stringify(filter);
            if (match.indexOf('000Z') >= 0) {
              const dt = new Date(filter);
              s[f] = [dt.getFullYear(), dt.getMonth() + 1, dt.getDate()].join('-').replace(/(?=\b\d\b)/g, '0');
            } else {
              s[f] = filter;
            }
          }
        }
      }
      return s;
    },
    setUrlFilters: function (filters) {
      console.log('table-mixin: setUrlFilters function');
      const self = this;
      filters[REQUEST_PAGENUM_FIELD] = filters[REQUEST_PAGENUM_FIELD] ? filters[REQUEST_PAGENUM_FIELD] : self.tableData[RESPONSE_PAGENUM_FIELD];
      filters[REQUEST_PAGESIZE_FIELD] = filters[REQUEST_PAGESIZE_FIELD] ? filters[REQUEST_PAGESIZE_FIELD] : self.tableData[RESPONSE_PAGESIZE_FIELD];
      self.$router.replace({ path: self.$route.path, query: filters });
      self.fetchTableData(filters);
    },
    async fetchTableData(params = {}) {
      const self = this;
      const baseUrl = self.baseUrl;
      const method = self.tableRequestMethod || 'get';
      params[REQUEST_PAGENUM_FIELD] = params[REQUEST_PAGENUM_FIELD] ? params[REQUEST_PAGENUM_FIELD] : this.tableData[REQUEST_PAGENUM_FIELD] ? this.tableData[REQUEST_PAGENUM_FIELD] : PAGE_NUM_DEFAULT;
      params[REQUEST_PAGESIZE_FIELD] = params[REQUEST_PAGESIZE_FIELD] ? params[REQUEST_PAGESIZE_FIELD] : this.tableData[RESPONSE_PAGESIZE_FIELD] ? this.tableData[RESPONSE_PAGESIZE_FIELD] : PAGE_SIZE_DEFAULT;
      // options.orderBy = this.filterForm && this.filterForm.orderBy ? this.filterForm.orderBy : ''
      if (baseUrl) {
        params.loading = true;
        const axiosRequestConfig = {
          method,
          url: baseUrl
        };
        const res = await axios.request(axiosRequestConfig);
        let response = res.data;
        if (!response) {
          throw Error('unknown table data response');
        }

        self.tableData = {
          lists: getPropByPath(response, RESPONSE_LIST_FIELD) || [],
          pageSize: getPropByPath(response, RESPONSE_PAGESIZE_FIELD) || PAGE_SIZE_DEFAULT,
          pageNumber: getPropByPath(response, RESPONSE_PAGENUM_FIELD) || PAGE_NUM_DEFAULT,
          total: getPropByPath(response, RESPONSE_TOTAL_FIELD) || 0
        };
        self.afterFetchTableData && self.afterFetchTableData();
      }
    },
    // 排序数据
    handleTableSortChange({ column, prop, order }) {
      console.log(column, prop, order);
      let sortType = '';
      // 驼峰转下划线
      prop = prop.replace(/([A-Z])/g, '_$1').toLowerCase();
      if (order === 'ascending') {
        sortType = `${prop} asc`;
      } else if (order === 'descending') {
        sortType = `${prop} desc`;
      }
      this.filterForm.orderBy = sortType;
      this.setFilter();
    },
    // 分页获取数据，先拿到筛选项的数据，再处理分页
    fetchWithPagination: function (currentPage) {
      console.log('table-mixin: fetchWithPagination function');
      const self = this;
      let urlQuery = self.getFilters();
      urlQuery[REQUEST_PAGENUM_FIELD] = currentPage;
      if (self.tableData.total) {
        self.setUrlFilters(urlQuery);
      }
    },
    fetchWithPageSize: function (pageSize) {
      console.log('table-mixin: fetchWithPageSize function');
      const self = this;
      self.tableData[REQUEST_PAGESIZE_FIELD] = pageSize;
      self.setFilter();
    },
    // **************************************华丽的分割线*********************************************/
    // 列表数据的格式化项
    // 格式化时间 DateTime 兼容 Safari
    tableFormatDateTime(row, column, cellValue) {
      if (!cellValue) return '-';
      cellValue = cellValue.replace(new RegExp(/-/gm), '/');
      let dt = new Date(cellValue);

      return Utils.Time.format('yyyy-MM-dd hh:mm:ss', dt);
    },
    // 格式化时间 Date 兼容 Safari
    tableFormatDate(row, column, cellValue) {
      if (!cellValue) return '-';
      cellValue = cellValue.replace(new RegExp(/-/gm), '/');
      let dt = new Date(cellValue);

      return Utils.Time.format('yyyy-MM-dd', dt);
    },
    tableFormatNumber(row, column, cellValue) {
      return Utils.Number.format(cellValue);
    },
    tableFormatPercent(row, column, cellValue) {
      return Number(cellValue * 100).toFixed(2) + '%';
    },
    tableIsNull(row, column, cellValue) {
      if (cellValue) {
        return cellValue;
      }
      return '-';
    }
  }
};

export default TableMixin;
