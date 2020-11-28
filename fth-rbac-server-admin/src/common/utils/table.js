/**
 * yyyy-MM-dd hh:mm:ss q S
 * @param format
 * @param time
 * @returns {*}
 */
export function dateTimeFormat (format, time = new Date()) {
  const o = {
    "M+": time.getMonth() + 1, // month
    "d+": time.getDate(), // day
    "h+": time.getHours(), // hour
    "m+": time.getMinutes(), // minute
    "s+": time.getSeconds(), // second
    "q+": Math.floor((time.getMonth() + 3) / 3), // quarter
    "S": time.getMilliseconds() // millisecond
  }
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (time.getFullYear() + "").substr(4 - RegExp.$1.length))
  }
  for (let k in o) {
    if (new RegExp(`(${k})`).test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : (`00${o[k]}`).substr((`${o[k]}`).length))
    }
  }
  return format
}


export function tableFormatDateTime (row, column, cellValue) {
  if (!cellValue) return '-';
  // cellValue = cellValue.replace(new RegExp(/-/gm), '/');
  let dt = new Date(cellValue);

  return dateTimeFormat('yyyy-MM-dd hh:mm:ss', dt);
}


// 格式化时间 Date 兼容 Safari
export function tableFormatDate (row, column, cellValue) {
  if (!cellValue) return '-';
  // cellValue = cellValue.replace(new RegExp(/-/gm), '/');
  let dt = new Date(cellValue);

  return dateTimeFormat('yyyy-MM-dd', dt);
}


// export function tableFormatNumber (row, column, cellValue) {
//   return Utils.Number.format(cellValue);
// }


export function tableFormatPercent (row, column, cellValue) {
  return Number(cellValue * 100).toFixed(2) + '%';
}


export function tableIsNull (row, column, cellValue) {
  if (cellValue) {
    return cellValue;
  }
  return '-';
}
