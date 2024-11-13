import axios from 'axios';
import 'element-plus/theme-chalk/el-message.css';

const baseURL = '/api';
const instance = axios.create({
  baseURL: baseURL,
  timeout: 5000
});

export default instance;
