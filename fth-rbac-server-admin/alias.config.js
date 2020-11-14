const path = require('path');

function resolve(dir) {
  return path.join(__dirname, dir);
}

/**
 * 用于 Webstorm settings -> Language & Framework -> Javascript -> webpack
 * 请务必与 vue.config.js chainWebpack 中 config.resolve.alias 保持一致
 * @author fengtianhe
 * @date 2020-07-01
 * @type {{resolve: {alias: {'@': *}}}}
 */
module.exports = {
  resolve: {
    alias: {
      '@': resolve('src'),
      '@common': resolve('src/common'),
      '@c': resolve('src/common/components'),
      '@u': resolve('src/common/utils/index.js'),
      '@m': resolve('src/common/mapping/index.js'),
      '@web': resolve('src/web')
    }
  }
};
