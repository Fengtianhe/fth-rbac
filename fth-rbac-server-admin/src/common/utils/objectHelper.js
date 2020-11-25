/**
 * @author 冯天鹤
 * @description 对象通过属性路径获取属性值
 * @param obj example: {user: {name: '王二'}}
 * @param path example: user.name
 * @param strict
 * @returns {*}
 */
export function getPropByPath(obj, path, strict) {
  let tempObj = obj;
  path = path.replace(/\[(\w+)\]/g, '.$1');
  path = path.replace(/^\./, '');
  let keyArr = path.split('.');
  let pathLen = keyArr.length;
  for (let i = 0; i < pathLen; ++i) {
    if (!tempObj && !strict) break;
    let pathKey = keyArr[i];
    if (tempObj[pathKey]) {
      tempObj = tempObj[pathKey];
    } else {
      if (strict) {
        throw new Error('please transfer a valid prop path to form item!');
      }
      break;
    }
  }
  return tempObj;
}

/**
 * 获取数据类型
 * @param {All} [o] 需要检测的数据
 * @returns {String}
 */
export function getType(o) {
  return Object.prototype.toString.call(o).slice(8, -1);
}

/**
 * 判断是否是指定数据类型
 * @param {All} [o] 需要检测的数据
 * @param {String} [type] 数据类型
 * @returns {Boolean}
 */
export function isKeyType(o, type) {
  return getType(o).toLowerCase() === type.toLowerCase();
}

/**
 * 深拷贝，支持常见类型 object Date Array等引用类型
 * @param {Any} sth
 * @return {Any}
 */
export function deepClone(sth) {
  let copy;
  if (null == sth || 'object' != typeof sth) return sth;
  if (isKeyType(sth, 'date')) {
    copy = new Date();
    copy.setTime(sth.getTime());
    return copy;
  }
  if (isKeyType(sth, 'array')) {
    copy = [];
    for (let i = 0, len = sth.length; i < len; i++) {
      copy[i] = deepClone(sth[i]);
    }
    return copy;
  }
  if (isKeyType(sth, 'object')) {
    copy = {};
    for (let attr in sth) {
      if (Object.prototype.hasOwnProperty.call(sth, attr)) copy[attr] = deepClone(sth[attr]);
    }
    return copy;
  }
  return null;
}
