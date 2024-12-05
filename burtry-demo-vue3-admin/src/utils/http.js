import axios from 'axios';
import 'element-plus/theme-chalk/el-message.css';

import { ElMessage } from 'element-plus';
import { useUserStore } from '../stores/user';
import router from '@/router/index';



const baseURL = '/api';
const instance = axios.create({
  baseURL: baseURL,
  timeout: 5000
});

// axios请求拦截器
instance.interceptors.request.use(config => {
  const userStore = useUserStore()

  const token = userStore.userInfo.token
  if (token) {
    config.headers.token = token
  }
  return config
}, e => Promise.reject(e))


//响应拦截器
instance.interceptors.response.use(res => {
  // 检查请求 URL 是否是验证码请求，如果是，则直接返回原始响应，不进行二次处理
  if (res.config.url.includes('api/v1/code')) {
    return res;  // 直接返回原始响应
  }
  return res.data
}, e => {
  const userStore = useUserStore()
  // ElMessage({ message: e.response.data.msg, type: 'error' })
  //401处理
  if (e.response.status === 401) {
    userStore.removeUserInfo()
    ElMessage({ type: 'error', message: '登录过期，请重新登录' });
    router.push('/login')
  }
  if (e.response.status === 500) {
    ElMessage({ type: 'error', message: '服务器错误' });
  }

  if (e.response.status === 503) {
    ElMessage({ type: 'warning', message: '服务器维护中' });
  }
  return Promise.reject(e)
})

// //响应拦截器
// instance.interceptors.response.use(res => {
//   // 检查请求 URL 是否是验证码请求，如果是，则直接返回原始响应，不进行二次处理
//   if (res.config.url.includes('api/v1/code')) {
//     return res;  // 直接返回原始响应
//   }
//   return res.data
// }, e => {
//   const userStore = useUserStore()
//   const router = useRouter()
//   //401处理
//   if (e.response.status === 401) {
//     console.log(e);


//     userStore.removeUserInfo()
//     ElMessage({ type: 'error', message: '登录过期，请重新登录' });
//     router.push('/login')
//   }
//   return Promise.reject(e)
// })
export default instance
