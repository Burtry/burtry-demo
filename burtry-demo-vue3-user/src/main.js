import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import './styles/common.scss'
import keepMenu from "@/menu/keepMenu";

import { Boot } from '@wangeditor/editor'


import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia()
app.use(ElementPlus, {
  locale: zhCn,
})
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)

const keepBtn = {
  key: 'keepMenu',
  factory() {
    return new keepMenu();
  }
}

Boot.registerMenu(keepBtn);
app.mount('#app')
