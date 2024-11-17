<script setup>
import { ref } from 'vue';
import { useRouter } from "vue-router";
import { getCodeAPI, loginAPI } from "@/api/user";
import { ElMessage } from 'element-plus';

import { useUserStore } from "@/stores/user";

const userStore = useUserStore();


const username = ref('');
const password = ref('');
const captcha = ref('');
const captchaImage = ref('');
const router = useRouter();

const handLogin = async () => {

  const params = {
    username: username.value,
    password: password.value,
    captcha: captcha.value
  }

  if (!params.username || !params.password || !params.captcha) {
    ElMessage.warning("请输入完整信息");
    return;
  }

  await loginAPI(params).then(res => {

    if (res.code === 0) {
      // 登录失败
      if (res.msg === "验证码错误") {
        ElMessage.error("验证码错误")
        getCaptcha(username.value)
        return;
      }
      ElMessage.error(res.msg);
      return;
    }
    userStore.userInfo = res.data;
    console.log(userStore.userInfo);
    // 跳转到首页
    router.push('/');
    ElMessage.success("登录成功");
  }).catch(err => {
    console.error("登录失败:", err);
  })
};

const visible = ref(false);

const getCaptcha = (username) => {
  if (username === "") {
    ElMessage.warning("请先输入用户名");
    return;
  }
  visible.value = true;
  refreshCaptcha(username);
};

const refreshCaptcha = (username) => {
  getCodeAPI(username).then(res => {
    // 创建一个URL对象，将 Blob 数据转为图片URL
    captchaImage.value = URL.createObjectURL(res.data);
  }).catch(err => {
    console.error("验证码获取失败:", err);
  });
};



</script>

<template>
  <div class="login-container">
    <div class="logo"></div>
    <div class="system-name">WriteSpace 后台管理系统</div>
    <div class="form">
      <div class="input-group">
        用 户 名:
        <el-input v-model="username" style="width: 240px" placeholder="请输入用户名" clearable />
      </div>
      <div class="input-group">
        密 &nbsp;&nbsp;&nbsp;&nbsp;码:
        <el-input v-model="password" style="width: 240px" placeholder="请输入密码" show-password clearable />
        <!-- 点击获取验证码 -->
      </div>
      <div class="input-group">
        验 证 码:
        <el-input v-model="captcha" style="width: 140px" clearable />
        <img :src="captchaImage" alt="验证码" style="width: 100px; height: 30px;" @click="refreshCaptcha(username)"
          v-if="visible" />
        <a @click="getCaptcha(username)" v-else class="active">点击获取验证码</a>
      </div>
      <!-- 点击获取验证码 -->
    </div>
    <div class="buttons">
      <el-button type="primary" class="btn" @click="handLogin">登录</el-button>
    </div>
  </div>

  <div class="icp">
    <a href="https://beian.miit.gov.cn/" target="_blank">
      豫ICP备2024095519号 (仅用于学习使用，无商业用途)</a>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.logo {
  background-image: url('@/assets/logo.png');
  width: 450px;
  height: 450px;
  background-size: contain;
  background-repeat: no-repeat;
  margin-bottom: 20px;
  margin-top: -200px;
}

.system-name {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
  margin-top: -100px;
}

.form {
  text-align: center;

  .input-group {
    margin: 10px 0;
  }
}

.buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn {
  margin: 0 5px;
  width: 200px;
}

// 添加背景图片
.login-container {
  background-image: url('@/assets/bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.icp {
  /* 位于网页最下方 */
  position: fixed;
  bottom: 0;
  // 水平居中
  left: 50%;
  transform: translateX(-50%);
  // 颜色、字体大小、字体粗细
  margin: 10px;
  font-size: small;
  color: gray;
}

.active {
  font-size: 12px;
  margin-left: 10px;

  &:hover {
    color: #409eff;
    cursor: pointer;
  }
}
</style>
