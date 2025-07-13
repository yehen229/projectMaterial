import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import basicSsl from '@vitejs/plugin-basic-ssl'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
    // basicSsl()
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'),
      components: resolve(__dirname, 'src/components'),
      utils: resolve(__dirname, 'src/utils'),
      views: resolve(__dirname, 'src/views'),
      icons: resolve(__dirname, 'src/assets/icons'),
      images: resolve(__dirname, 'src/assets/images'),
      http: resolve(__dirname, 'src/httpconfig'),
      server: resolve(__dirname, 'src/server'),
      store: resolve(__dirname, 'src/store'),
    },
  },
  // server: {
  //   host: '0.0.0.0'
  // },
  // server:{
  //   https: true,
  // }

})
