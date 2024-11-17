import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './styles/common.scss'
const pinia = createPinia()
//暗黑模式css
// import 'element-plus/theme-chalk/dark/css-vars.css'


import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(ElementPlus, {
  locale: zhCn,
})
// 注册持久化插件
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)

app.mount('#app')
