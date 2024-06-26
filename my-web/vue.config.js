const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:28080",
        // target: process.env.VUE_APP_BASE_API,
        changeOrigin: true,
        pathRewrite: {
          // "^/api": process.env.VUE_APP_PATH
          "^/api": ""
        }
      }
    }
  }
})
