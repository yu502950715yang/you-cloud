import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // 别名配置
      '~': path.resolve(__dirname, './'),
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    https: false,
    host: true,
    port: 5173,
    // 是否自动打开浏览器
    open: false,
    // 跨域设置允许
    cors: true,
    // 接口代理
    proxy: {
      "/api" : {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
