<script setup>
import { useRouter } from "vue-router"
const router = useRouter()

import { useUserStore } from "@/stores/user";
import { getStringAPI } from "@/api/user";
import { ElMessage } from "element-plus";
const userStore = useUserStore();

const userInfo = userStore.userInfo;

const getString = () => {
  getStringAPI().then(res => {
    ElMessage.success(res.data);
  })
}




const exit = () => {
  userStore.removeUserInfo();
  router.push("/login");
}




</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header区域 -->
      <el-header class="app-header">
        <a href="/">
          <h1 class="app-title">
            <div class="logo"></div>
            <span class="app-title-text">WriteSpace 后台管理平台</span>
          </h1>
        </a>

        <!-- 当前用户 -->
        <el-button @click="getString">测试</el-button>



        <div class="user-info">
          <div style="font-size: large;">欢迎您：</div>
          <el-avatar :size="30" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
          </el-avatar>
          <span>{{ userInfo.nickName }}</span>

          <!-- 使用 SVG 图标作为退出按钮 -->
          <a class="logout" @click="exit">
            <img src="@/assets/exit.svg" alt="退出" class="exit-icon" />
            退出
          </a>
        </div>
      </el-header>

      <el-container>
        <!-- 侧边栏区域 -->
        <el-aside class="app-aside">
          <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff"
            active-text-color="#94cdf5" default-active="1">
            <el-menu-item index="1" @click="router.push('/article')">文章管理</el-menu-item>
            <el-menu-item index="2" @click="router.push('/user')">用户管理</el-menu-item>
            <el-menu-item index="3" @click="router.push('/comment')">评论管理</el-menu-item>
            <el-menu-item index="4" @click="router.push('/sensitive')">敏感词管理</el-menu-item>
            <el-menu-item index="5" @click="router.push('/channel')">频道管理</el-menu-item>
          </el-menu>
        </el-aside>

        <!-- 内容区域 -->
        <el-container>
          <el-main class="app-main">
            <RouterView></RouterView>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.common-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-header {
  background-color: #565c64;
  color: #fff;
  text-align: center;
  height: 80px;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.app-title {
  display: flex;
  align-items: center;
  gap: 70px;

  span {
    font-size: 24px;
    font-weight: bold;
  }
}

.app-title-text {
  font-size: 24px;
  font-weight: bold;
  color: #fff;
}

.logo {
  background-image: url('@/assets/logo.png');
  width: 100px;
  height: 100px;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  margin-top: 20px;
  margin-left: 10px;
}

.app-aside {
  width: 200px;
  background-color: #545c64;
  color: #fff;
  padding: 20px;
}

.app-main {
  background-color: #f2f2f2;
  padding: 20px;
}

.el-menu-vertical-demo {
  width: 100%;
  border-right: none;
}

.user-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  font-size: 16px;
}

.logout {
  color: #fff;
  display: flex;
  align-items: center;
  gap: 5px;

  /* 默认状态下的图标颜色 */
  .exit-icon {
    width: 30px;
    height: 30px;
    background: #575c64;
  }

  /* 鼠标悬停时改变颜色 */
  &:hover {
    color: #94cdf5;

  }
}
</style>
