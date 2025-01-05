<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
import { useUserStore } from "@/stores/user";
import { ElMessage } from "element-plus";
import { uploadFileAPI } from "@/api/upload";
import { updateUserInfoAPI } from "@/api/user";
const userStore = useUserStore();

const userInfo = ref(userStore.userInfo);

const dialogFormVisible = ref(false);

const imageUrl = ref('')
const uploadImage = async (params) => {
  const file = params.file
  // 根据后台需求数据格式
  const form = new FormData();
  // 文件对象
  form.append('file', file);

  const res = await uploadFileAPI(form)
  if (res.code === 0) {
    ElMessage.error('上传失败')
    return
  }
  imageUrl.value = res.data
  ElMessage.success('上传成功')
}

const toUser = () => {
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
const userInfoForm = ref({
  id: '',
  nickName: '',
  phone: '',
  url: '', //头像url
  sex: '',
  email: '',
  address: ''
})

const cancelUpdateUserInfo = () => {
  dialogFormVisible.value = false;
  userInfoForm.value = {
    id: '',
    nickName: '',
    phone: '',
    url: '',
    sex: '',
    email: '',
    address: ''
  }

}


//TODO
const updateUserInfo = async () => {
  if (userInfo.value.id === "0") {
    ElMessage.warning("请先登录");
    router.push("/login");
    return;
  }
  userInfoForm.value.url = imageUrl.value
  if (userInfoForm.value.nickName === '' || userInfoForm.value.phone === '' || userInfoForm.value.sex === '' || userInfoForm.value.email === '' || userInfoForm.value.address === '') {
    ElMessage.warning("请填写完整信息");
    return;
  }
  userInfoForm.value.id = userInfo.value.id

  const res = await updateUserInfoAPI(userInfoForm.value)
  if (res.code === 0) {
    ElMessage.error('更新失败')
    return
  }
  ElMessage.success('更新成功，请重新登录')
  userStore.removeUserInfo();
  cancelUpdateUserInfo();
  router.push("/login");

}

const openUpdateUserInfo = () => {
  if (userInfo.value.id === "0") {
    ElMessage.warning("请先登录");
    router.push("/login");
    return;
  }
  userInfoForm.value = userInfo.value
  dialogFormVisible.value = true;
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

      <div v-if="userInfo.id === '0'" class="user-name you-user-name">游客</div>
      <RouterLink v-else :to="`/user/${userInfo.id}`"><span class="user-name">{{ userInfo.nickName }}</span>
      </RouterLink>

      <el-dropdown class="avatar-dropdown" trigger="hover">
        <div v-if="userInfo.id === '0'" class="user-name you-user-name">
          <el-avatar :src="userStore.userInfo.image" />
        </div>
        <RouterLink v-else :to="`/user/${userInfo.id}`">
          <el-avatar :src="userStore.userInfo.image" />
        </RouterLink>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
            <el-dropdown-item @click="openUpdateUserInfo">修改资料</el-dropdown-item>
            <el-dropdown-item @click="toText">发布文章</el-dropdown-item>
            <el-dropdown-item @click="toTextManagement">文章管理</el-dropdown-item>
            <el-dropdown-item divided @click="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

    </div>

    <el-dialog v-model="dialogFormVisible" title="更新用户信息 " width="500">
      <el-form :model="userInfoForm" label-width="80px">
        <el-form-item label="昵 称" required>
          <el-input v-model="userInfoForm.nickName" prop="nickName" />
        </el-form-item>
        <el-form-item label="手机号" required>
          <el-input v-model="userInfoForm.phone" prop="phone" />
        </el-form-item>
        <el-form-item label="头 像" prop="url">


          <el-upload action="##" :limit="1" :http-request="uploadImage">
            <template #trigger>
              <el-button type="primary">上传头像</el-button>
            </template>

            <template #tip>
              <div class="el-upload__tip text-red">
                (jpg/png格式，大小不超过500kb)
              </div>
            </template>

          </el-upload>

        </el-form-item>
        <el-form-item label="性 别" required>
          <el-radio-group v-model="userInfoForm.sex">
            <el-radio value="1">男</el-radio>
            <el-radio value="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮 箱" required>
          <el-input v-model="userInfoForm.email" prop="email" />
        </el-form-item>
        <el-form-item label="地 址" required>
          <el-input v-model="userInfoForm.address" prop="address" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancelUpdateUserInfo">取消</el-button>
          <el-button type="primary" @click="updateUserInfo">
            提交
          </el-button>
        </div>
      </template>
    </el-dialog>


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

  .you-user-name {
    &:hover {
      color: #3c3c3c;
    }
  }

  .avatar-dropdown {
    cursor: pointer;
  }
}
</style>
