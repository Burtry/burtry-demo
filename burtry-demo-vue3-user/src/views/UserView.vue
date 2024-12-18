<script setup>
import ArticleView from "@/views/article/UserArticleView.vue";
import { onMounted, ref } from "vue";
import { getUserByIdAPI } from "@/api/user";
import { getArticleListAPI } from "@/api/article";
import router from "@/router";
import { ElMessage } from "element-plus";
const articles = ref([]);

const userInfo = ref({
  id: "",
  nickName: "",
  phone: "",
  email: "",
  status: "",
  avatar: "",
  createTime: "",
  updateTime: "",
});
//此用户id为路径上的userid
const userId = router.currentRoute.value.params.id;

//获取用户信息
const getUserInfo = async () => {
  const res = await getUserByIdAPI(userId);
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  userInfo.value = res.data;

}

//获取用户文章信息
const getUserArticle = async () => {
  const res = await getArticleListAPI(userId);
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  articles.value = res.data;

}

onMounted(() => {
  getUserInfo();
  getUserArticle();
})

</script>

<template>
  <div class="bg">
    <div class="user-info">
      <el-descriptions border>
        <el-descriptions-item :rowspan="2" :width="140" label="头像" align="center">
          <el-image style="width: 100px; height: 100px" :src="userInfo.image" fit="cover" />
        </el-descriptions-item>
        <el-descriptions-item label="用户昵称">{{ userInfo.nickName }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ userInfo.phone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag v-if="userInfo.status === 0">正常</el-tag>
          <el-tag v-else type="danger">锁定</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="地址">{{ userInfo.address }}</el-descriptions-item>
      </el-descriptions>

      <div class="overview">
        <!-- 数据总览 -->
        <el-row>
          <el-col :span="6">
            <el-statistic title="文章总点赞量" :value="111" class="over-item" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="文章总收藏量" :value="222" class="over-item" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="文章总评论量" :value="333" class="over-item" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="文章总阅读量" :value="444" class="over-item" />
          </el-col>
        </el-row>
      </div>

      <div class="article-info">
        <div v-if="articles.length === 0">
          <el-empty :image-size="200">
          </el-empty>
        </div>
        <div v-else>
          <h3 style="margin-bottom: 20px;">用户发表文章</h3>
          <ArticleView :articles="articles" />
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.bg {
  min-height: 100vh;
  height: auto;
  background-image: url('@/assets/bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding: 20px;
}

.user-info {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 800px;
}

.article-info {
  margin-top: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 8px;
}

.overview {
  margin-top: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 8px;
  // 居中
  display: flex;
  justify-content: center;
  align-items: center;
}

.over-item {
  margin: 0 50px;
}
</style>
