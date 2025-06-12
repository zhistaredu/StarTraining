const os = require('os');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

const IS_PROD = ['production', 'prod'].includes(process.env.NODE_ENV);

// css 自动加前缀
const autoprefixer = require('autoprefixer');

// gzip 压缩本地打包文件
// const CompressionWebpackPlugin = require('compression-webpack-plugin');

// 获取本地ip
const getIp = () => {
  var interfaces = os.networkInterfaces();
  for (var devName in interfaces) {
    var iface = interfaces[devName];
    for (var i = 0; i < iface.length; i++) {
      var alias = iface[i];
      if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal) {
        return alias.address;
      }
    }
  }
}
// page title
const name = 'vue pc template'
const OUTPUT_FILE_NAME = process.env.outputDir
module.exports = {
  /*
    * 相对路径 ('./')，所有的资源都会被链接为相对路径，这样打出来的包可以被部署在任意路径
    * 当使用基于 HTML5 history.pushState 的路由时，需要避免使用相对 publicPath
    * 当使用 pages 选项构建多页面应用时，应当避免使用相对 publicPath
  */
  publicPath: process.env.NODE_ENV === "production" ? "/pcuser/" : "/pcuser/",
  outputDir: `dist/${OUTPUT_FILE_NAME}`, //  生产环境构建文件的目录
  assetsDir: 'static', // 放置生成的静态资源目录
  indexPath: 'index.html', // 指定生成的 index.html 的输出路径 (相对于 outputDir)。也可以是一个绝对路径
  css: {
    extract: IS_PROD, // 是否将组件中的 CSS 提取至一个独立的 CSS 文件中
    sourceMap: !IS_PROD, // 是否为 CSS 开启 source map
    requireModuleExtension: true, // *.module.[ext] 结尾的文件会被视作 CSS Modules 模块
    loaderOptions: {
      postcss: {
        plugins: [
          autoprefixer(),
        ]
      }
    }
  },
  devServer: {
    clientLogLevel: 'warning', // 热更新时阻止控制台显示消息，太多了，没加 eslint none
    overlay: { warnings: false, errors: true }, // webpack 的 eslint 等错误、警告提示显示在页面中，全为 true 会停止页面运行
    noInfo: true, // 每次启动和保存，只显示 webpack 编译的错误和警告信息
    watchContentBase: true, // 修改没有被入口文件托管的文件，比如 index.html 文件，也会自动更新
    compress: true, // 一切服务都启用 gzip 压缩
    hot: true, // 启动 webpack 热模块替换特性
    inline: true, // 自动刷新
    open: true, // 自动打开浏览器
    port: process.env.VUE_APP_PORT,
    // proxy: {
    //   配置跨域
    //   [process.env.VUE_APP_BASE_API]: {
    //     target: `https://app135926.eapps.dingtalkcloud.com`,
    //     changeOrigin: true,
    //     ws: false,
    //     pathRewrite: {
    //       ['^' + process.env.VUE_APP_BASE_API]: ''
    //     }
    //   }
    // }
  },

  configureWebpack: config => {
    config.devtool = false;

  },

  chainWebpack: config => {
    // 移除 prefetch 插件
    config.plugins.delete('prefetch');
    // 移除 preload 插件
    // config.plugins.delete('preload');

    // 修复 HMR 热更新
    // config.resolve.symlinks(true);

     // 打包分析
    if (IS_PROD) {
      config.plugin('webpack-report').use(BundleAnalyzerPlugin,
        [
          {
            analyzerMode: 'static'
          }
        ]
      );
    }

    // 别名
    config.resolve.alias
      .set('@assets', '@/assets')
      .set('@utils', '@/assets/utils')
      .set('@components', '@/components')
      .set('@pages', '@/pages')
      .set('@server', '@/server')
  },
  lintOnSave: false, // 生产构建时禁用 eslint
  productionSourceMap: false, // 不需要生产环境的 source map
  runtimeCompiler: false, // 是否使用包含运行时编译器的 Vue 构建版本

}
