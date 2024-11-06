<script setup>
import { useRouter } from "vue-router";
const router = useRouter()

const toUser = () => {
  router.push('/user')
}

const toText = () => {
  router.push('/text')
}


</script>

<template>
  <div class="layout-head">
    <div class="logo">
      <RouterLink to="/" style="color: #3c3c3c;">
        <img src="@/assets/logo.png" alt="logo">
        WriteSpace
      </RouterLink>
    </div>

    <el-dropdown class="user-info">
      <template #dropdown>
        <div class="user-name">UserName</div>
        <el-dropdown-menu>
          <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click="toText">发布文章</el-dropdown-item>
          <el-dropdown-item divided>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
      <RouterLink to="/user">
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
      </RouterLink>
    </el-dropdown>


  </div>

</template>


<style scoped lang="scss">
.logo {
  float: left;
  margin-left: 20px;

  img {
    width: 50px;
    height: 50px;
    // border-radius: 50%;
    // margin-right: 10px;
    background-color: #f5f5f5;
  }
}

.user-info {
  float: right;
  margin-right: 20px;
  width: 40px;
  height: 40px;
  background-color: #3c3c3c;
  background-image: url('@/assets/bg.png');
  border-radius: 50%;
  margin-right: 50px;
  margin-top: 10px;

}

.user-name {
  text-align: center;
  margin-top: 10px;
  font-size: 14px;
}



.layout-head {

  height: 60px;
  line-height: 60px;
  font-size: 20px;
  font-weight: bold;
  background-color: #f5f5f5;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 20px;
}
</style>
