import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './styles/common.scss'
import MyButtonMenu1 from "@/class/menu1";
import MyButtonMenu2 from "@/class/menu2";
import MyButtonMenu3 from "@/class/menu3";
import { Boot } from '@wangeditor/editor'


import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(ElementPlus, {
  locale: zhCn,
})
app.use(createPinia())
app.use(router)


// 在应用启动时注册菜单，只执行一次
const menu1Conf = {
  key: 'menu1',
  factory() {
    return new MyButtonMenu1();
  }
};
const menu2Conf = {
  key: 'menu2',
  factory() {
    return new MyButtonMenu2();
  }
}
const menu3Conf = {
  key: 'menu3',
  factory() {
    return new MyButtonMenu3();
  }
}
Boot.registerMenu(menu1Conf);
Boot.registerMenu(menu2Conf);
Boot.registerMenu(menu3Conf);




app.mount('#app')
