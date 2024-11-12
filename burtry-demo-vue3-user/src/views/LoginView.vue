<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from "vue-router";

const username = ref('');
const password = ref('');
const captcha = ref(''); // 添加验证码输入框的变量
const captchaImage = ref('');
const router = useRouter();

const handLogin = () => {
  console.log('登录');
  console.log(username.value);
  console.log(password.value);
  console.log(captcha.value); // 输出验证码
  console.log(captchaImage.value);

  // 跳转到首页
  router.push('/');
};

const refreshCaptcha = () => {
  // 重新获取验证码图片
  captchaImage.value = 'http://localhost:8801/admin/code?time=' + new Date().getTime();
};

onMounted(() => { refreshCaptcha() })


</script>

<template>
  <div class="login-container">
    <div class="logo"></div>
    <div class="system-name">WriteSpace 写作平台</div>
    <div class="form">
      <div class="input-group">
        用 户 名:
        <el-input v-model="username" style="width: 240px" placeholder="请输入用户名" clearable />
      </div>
      <div class="input-group">
        密 &nbsp;&nbsp;&nbsp;&nbsp;码:
        <el-input v-model="password" style="width: 240px" placeholder="请输入密码" show-password clearable />
      </div>
      <div class="input-group">
        验 证 码:
        <el-input v-model="captcha" style="width: 140px" clearable />
        <img :src="captchaImage" alt="验证码" style="width: 100px; height: 30px;" @click="refreshCaptcha" />
      </div>
    </div>
    <div class="buttons">
      <el-button type="primary" class="btn" @click="handLogin">登录</el-button>
      <el-button type="primary" class="btn" @click="handLogin">注册</el-button>
    </div>

    <!-- 游客模式进入 -->
    <div class="buttons">
      <el-button class="btn" text @click="handLogin">游客模式进入>>></el-button>
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
  width: 100px;
}

// 添加背景图片
.login-container {
  // background-image: url('@/assets/login_bg.png');
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
</style>
