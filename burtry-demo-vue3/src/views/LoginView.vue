<script setup>
import { ref } from 'vue';
import { useRouter } from "vue-router"
const username = ref('');
const password = ref('');

const router = useRouter()

const handLogin = () => {
    console.log('登录');
    console.log(username.value);
    console.log(password.value)

    //跳转到首页
    router.push('/')

};
</script>


<template>
    <div class="login-container">
        <!-- logo -->
        <div class="logo"></div>
        <!-- 系统名称 -->
        <div class="system-name">WriteSpace 后台管理系统</div>
        <!-- 用户名和密码 -->
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
        <!-- 登录和注册按钮 -->
        <div class="buttons">
            <el-button type="primary" class="btn" @click="handLogin">登录</el-button>
            <el-button type="primary" class="btn">注册</el-button>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.login-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* 水平居中 */
    align-items: center;
    /* 垂直居中 */
    height: 100vh;
    /* 让父容器占据整个视口高度 */
}

.logo {
    background-image: url('@/assets/logo.png');
    width: 450px;
    height: 450px;
    background-size: contain;
    background-repeat: no-repeat;
    margin-bottom: 20px;
    margin-top: -200px;
    /* 增加与系统名称的间距 */
}

.system-name {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 30px;
    margin-top: -100px;
    /* 增加与表单的间距 */
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
    margin: 0 10px;
    /* 给两个按钮之间设置水平间距 */
    width: 100px;
}
</style>
