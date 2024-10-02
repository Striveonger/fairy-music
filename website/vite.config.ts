import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import * as path from 'path'
// Naive UI
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        }
    },
    server: {
        open: false,
        port: 5203,
        // https: false,
        host: "0.0.0.0",
        proxy: {
            "api/v1": {
                target: 'http://127.0.0.1:18081',
                changeOrigin: true,
                // secure: false,
                // rewrite: (path: any) => path.replace(/^\/fm/, '')
            }
        }
    }
})
