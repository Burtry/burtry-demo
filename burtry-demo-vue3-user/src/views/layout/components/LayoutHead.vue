<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const userInfo = ref({
  id: "2",
  nickName: "Burtry",
  phone: "13416395349",
  email: "1231231231@qq.com",
  status: "正常",
  address: "No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu Province",
});

const toUser = () => {
  router.push(`/user/${userInfo.value.id}`);
};

const toText = () => {
  router.push("/text");
};

const exit = () => {
  router.push("/login");
};
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
      <RouterLink :to="`/user/${userInfo.id}`"><span class="user-name">{{ userInfo.nickName }}</span>
      </RouterLink>

      <el-dropdown class="avatar-dropdown" trigger="hover">
        <RouterLink :to="`/user/${userInfo.id}`">
          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        </RouterLink>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
            <el-dropdown-item @click="toText">发布文章</el-dropdown-item>
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
