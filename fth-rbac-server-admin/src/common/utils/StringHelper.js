function highlight (string, highlightString) {
  let result = '';
  if (highlightString && highlightString.length > 0) {
    // 匹配关键字正则
    let replaceReg = new RegExp(highlightString, 'g');
    // 高亮替换v-html值
    let replaceString = '<span style=\'color: red\'>' + highlightString + '</span>';
    // 开始替换
    result = string.replace(replaceReg, replaceString);
  }
  return result;
}

function toLocaleUpperCase (str) {
  return str ? str.toLocaleUpperCase() : '';
}

/**
 * 获取路由参数
 * @param variable
 * @returns {string|boolean}
 */
function getRouteQuery (variable) {
  let href = window.location.href;
  let query = href.substring(href.indexOf('?') + 1);
  let vars = query.split('&');
  for (let i = 0; i < vars.length; i++) {
    let pair = vars[i].split('=');
    if (pair[0] === variable) {
      return pair[1];
    }
  }
  return false;
}

export default {
  highlight,
  toLocaleUpperCase,
  getRouteQuery
}
