const path = require('path');

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  publicPath: './',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: true,
  runtimeCompiler: true,
  productionSourceMap: false,
  pages: {
    index: {
      entry: 'src/web/main.js',
    },
    /* 多页面配置 */
    // openview: {
    //   entry: 'src/openview/main.js',
    //   template: 'public/openview/openview.html',
    //   filename: 'openview.html'
    // }
  },
  devServer: {
    port: 3000,
    proxy: {
      // proxy all requests starting with /api to jsonplaceholder
      '/': {
        target: 'http://127.0.0.1:8080', //代理接口
        secure: false,
        changeOrigin: true,
        pathRewrite: {
          '/': ''
        },
        ws: false
      }
    }
  },
  configureWebpack: () => {
    if (process.env.NODE_ENV === 'production') {
      return {
        plugins: [
        ]
      };
    }
  },
  chainWebpack: config => {
    // 分析打包体积
    if (process.env.npm_config_report) {
      config.plugin('webpack-bundle-analyzer').use(require('webpack-bundle-analyzer').BundleAnalyzerPlugin);
    }
    // 修复HMR
    config.resolve.symlinks(true);

    // 定义路径别名
    config.resolve.alias
      .set('@', resolve('src'))
      .set('@common', resolve('src/common'))
      .set('@c', resolve('src/common/components'))
      .set('@utils', resolve('src/common/utils/index.js'))
      .set('@m', resolve('src/common/mapping/index.js'))
      .set('@web', resolve('src/web'));

    config.plugin('define').tap(args => {
      // args => [{"process.env": {"NODE_ENV": "production"}}]
      // 可定义全局变量
      return args;
    });
  },
  pluginOptions: { // 第三方插件配置
    'style-resources-loader': {
      preProcessor: 'less',
      patterns: [
        // less所在文件路径
        // 直接引用会报变量不存在 需要 yarn add vue-cli-plugin-style-resources-loader
        resolve('./src/common/assets/style/base/variable.less')
      ]
    }
  },
  css: {
    //是否启用css分离插件，默认是true，如果不启用css样式分离插件，打包出来的css是通过内联样式的方式注入至dom中的，方便动态换肤
    extract: false,
    sourceMap: false,
    modules: false
    // requireModuleExtension: false,
  }
};
