<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
import { useUserStore } from "@/stores/user";
// import { getStringAPI } from "@/api/user";
import { ElMessage } from "element-plus";
const userStore = useUserStore();

const userInfo = ref(userStore.userInfo);

const toUser = () => {
  console.log(userInfo.value);

  if (userInfo.value.id === "0") {
    ElMessage.warning("请先登录");
    router.push("/login");
    return;
  }
  router.push(`/user/${userInfo.value.id}`);
};

const toText = () => {
  if (userInfo.value.id === "0") {
    ElMessage.warning("请先登录");
    router.push("/login");
    return;
  }
  router.push("/text");
};

const exit = () => {
  userStore.removeUserInfo();
  router.push("/login");
};

const toTextManagement = () => {
  if (userInfo.value.id === "0") {
    ElMessage.warning("请先登录");
    router.push("/login");
    return;
  }
  router.push("/textManagement");
}

// const getString = () => {
//   getStringAPI().then((res) => {
//     console.log(res);
//   });
// }

const updateUserInfo = () => {
  if (userInfo.value.id === "0") {
    ElMessage.warning("请先登录");
    router.push("/login");
    return;
  }
  console.log(userInfo.value);
}

</script>

<template>
  <div class="layout-head">
    <!-- Left side logo -->
    <div class="logo">
      <RouterLink to="/" style="color: #3c3c3c;">
        <img src="@/assets/logo.png" alt="logo" />
        WriteSpace
      </RouterLink>
    </div>
    <div class="user-section">

      <!-- <button @click="getString()">test</button> -->
      <RouterLink :to="`/user/${userInfo.id}`"><span class="user-name">{{ userInfo.nickName ? userInfo.nickName : "游客"
          }}</span>
      </RouterLink>

      <el-dropdown class="avatar-dropdown" trigger="hover">
        <RouterLink :to="`/user/${userInfo.id}`">
          <el-avatar :src="userStore.userInfo.image" />
        </RouterLink>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
            <el-dropdown-item @click="updateUserInfo">修改资料</el-dropdown-item>
            <el-dropdown-item @click="toText">发布文章</el-dropdown-item>
            <el-dropdown-item @click="toTextManagement">文章管理</el-dropdown-item>
            <el-dropdown-item divided @click="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

    </div>
  </div>
</template>

<style scoped lang="scss">
.layout-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  font-size: 20px;
  font-weight: bold;
  background-color: #f5f5f5;
  border-bottom: 1px solid #e0e0e0;
}

.logo {
  display: flex;
  align-items: center;

  img {
    width: 50px;
    height: 50px;
    background-color: #f5f5f5;
  }
}

.user-section {
  display: flex;
  align-items: center;

  .user-name {
    font-size: 14px;
    color: #3c3c3c;
    margin-right: 10px;

    &:hover {
      color: #409eff;
    }
  }

  .avatar-dropdown {
    cursor: pointer;
  }
}
</style>
