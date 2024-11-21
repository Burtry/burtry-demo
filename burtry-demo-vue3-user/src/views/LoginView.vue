<script setup>
import { ref } from 'vue';
import { useRouter } from "vue-router";
import { getCodeAPI, loginAPI, registerAPI } from "@/api/user";
import { ElMessage } from 'element-plus';

import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const username = ref('');
const password = ref('');
const captcha = ref(''); // 添加验证码输入框的变量
const captchaImage = ref('');
const router = useRouter();

const registerVisible = ref(false);
const dialogVisible = ref(false);

const beforeHandLogin = () => {
  if (username.value === '' || password.value === '') {
    ElMessage.error("请输入用户名和密码")
    return
  }
  dialogVisible.value = true
  getCaptcha(username.value)


}

const handLogin = () => {
  if (captcha.value === '') {
    ElMessage.error("请输入验证码")
    return
  }
  //TODO 调用登录接口
  const data = {
    username: username.value,
    password: password.value,
    captcha: captcha.value
  }
  loginAPI(data).then(res => {
    if (res.code === 0) {
      ElMessage.error(res.msg)
    }
    else {
      if (res.msg === "验证码错误") {
        getCaptcha(username.value)
      }
      ElMessage.success("登录成功")
      userStore.userInfo = res.data;
      router.push('/');
    }
  })
};

const visitorLogin = () => {
  const data = {
    username: "visitor"
  }
  loginAPI(data).then(res => {
    if (res === 0) {
      ElMessage.error(res.msg)
      return
    } else {
      userStore.userInfo = res.data;
      router.push('/');
    }
  })
}

const registerForm = ref({
  name: '',
  password: '',
  confirmPassword: '',
  nickName: '',
  phone: '',
  sex: '',
  email: '',
  address: '',
  captcha: ''


})
const handRegister = () => {
  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    ElMessage.error("两次密码不一致")
    return
  }
  // 注册接口
  registerAPI(registerForm.value).then(res => {
    if (res.code === 0) {
      if (res.msg === "验证码错误") {
        refreshCaptcha(registerForm.value.name);
      }
      ElMessage.error(res.msg)
      return
    } else {
      ElMessage.success("注册成功")
      registerForm.value = {
        name: '',
        password: '',
        confirmPassword: '',
        nickName: '',
        phone: '',
        sex: '',
        email: '',
        address: '',
        captcha: ''
      }
      registerVisible.value = false
    }

  })
}

const rules = ref({
  name: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 10, message: '用户名长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  nickName: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 3, max: 10, message: '昵称长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { min: 11, max: 11, message: '手机号长度为 11 个字符', trigger: 'blur' }
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  address: [
    { required: true, message: '请输入地址', trigger: 'blur' },
  ]

})

const visible = ref(false);
const getCaptcha = (username) => {
  if (username === "") {
    ElMessage.warning("请先输入用户名");
    return;
  }
  visible.value = true;
  refreshCaptcha(username);
};

const visible2 = ref(false);
const registerGetCaptcha = (username) => {
  if (username === "") {
    ElMessage.warning("请先输入用户名");
    return;
  }
  visible2.value = true;
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
    </div>
    <div class="buttons">
      <el-button type="primary" class="btn" @click="beforeHandLogin">登录</el-button>
      <el-button type="primary" class="btn" @click="registerVisible = true">注册</el-button>
    </div>

    <!-- 游客模式进入 -->
    <div class="buttons">
      <el-button class="btn" text @click="visitorLogin">游客模式进入>>></el-button>
    </div>

  </div>

  <div class="icp">
    <a href="https://beian.miit.gov.cn/" target="_blank">
      豫ICP备2024095519号 (仅用于学习使用，无商业用途)</a>
  </div>

  <!-- 注册框 -->
  <el-dialog v-model="registerVisible" title="注册用户" width="30%" center>
    <el-form :model="registerForm" :rules="rules" label-width="auto" status-icon>
      <el-form-item label="用户名" prop="name">
        <el-input v-model="registerForm.name" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="registerForm.password" type="password" />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="registerForm.confirmPassword" type="password" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-input v-model="registerForm.sex" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="registerForm.phone" />
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="registerForm.nickName" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="registerForm.email" />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="registerForm.address" />
      </el-form-item>
      <el-form-item label="验证码" prop="captcha">
        <div class="input-group">
          <el-input v-model="registerForm.captcha" style="width: 140px" clearable />
          <img :src="captchaImage" alt="验证码" style="width: 100px; height: 30px;"
            @click="refreshCaptcha(registerForm.name)" v-if="visible2" />
          <a @click="registerGetCaptcha(registerForm.name)" v-else class="active">点击获取验证码</a>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="registerVisible = false">取消</el-button>
        <el-button type="primary" @click="handRegister">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="dialogVisible" title="请输入验证码" width="500">
    <div class="input-group">
      验 证 码:
      <el-input v-model="captcha" style="width: 140px" clearable />
      <img :src="captchaImage" alt="验证码" style="width: 100px; height: 30px;" @click="refreshCaptcha(username)">
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="handLogin">
          登录
        </el-button>
        <el-button @click="dialogVisible = false">取消</el-button>

      </div>
    </template>
  </el-dialog>


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

.active {
  font-size: 12px;
  margin-left: 10px;

  &:hover {
    color: #409eff;
    cursor: pointer;
  }
}
</style>
