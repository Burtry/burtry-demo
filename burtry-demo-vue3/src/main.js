import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './styles/common.scss'
//暗黑模式css
// import 'element-plus/theme-chalk/dark/css-vars.css'


import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
})
app.use(createPinia())
app.use(router)

app.mount('#app')
