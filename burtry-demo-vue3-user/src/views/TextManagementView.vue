<script setup>
import { onMounted, ref } from 'vue';
import ArticleItem from './article/ArticleItem.vue';

import { getArticleListAPI } from "@/api/article";
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router'; // 用于页面跳转
import { useUserStore } from "@/stores/user";
// import useStore from 'element-plus/es/components/table/src/store';
const userStore = useUserStore();

//文章管理里面的id为当前登录用户的用户id
const userId = ref(userStore.userInfo.id)

const articleStatus = ref('0'); // 文章状态

const articleList = ref([]);
const router = useRouter(); // 路由实例

const getArticleList = async () => {
  const res = await getArticleListAPI(userId.value, articleStatus.value);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    router.push("/")
    return;
  }

  if (res.data.length === 0) {
    ElMessage.info("暂无文章");
    articleList.value = [];
    return; // 如果没有文章，不需要继续处理
  }

  articleList.value = res.data;
};


// 跳转到发布文章页面
const goToPublish = () => {
  router.push('/text');
};



onMounted(() => {
  getArticleList();
});
</script>

<template>
  <div class="text-management">
    <h1 style="margin-bottom: 20px;">文章管理</h1>

    <!-- 当文章列表为空时显示暂无页面 -->
    <el-radio-group v-model="articleStatus" @change="getArticleList">
      <el-radio value="0">全部</el-radio>
      <el-radio value="1">草稿箱</el-radio>
      <el-radio value="2">待审核</el-radio>
      <el-radio value="3">待发布</el-radio>
      <el-radio value="4">已发布</el-radio>
      <el-radio value="5">已锁定</el-radio>
    </el-radio-group>
    <div v-if="articleList.length === 0" class="empty-container">
      <el-empty :image-size="400">
        <el-button type="primary" @click="goToPublish">立即发布文章</el-button>
      </el-empty>
    </div>

    <!-- 当文章列表不为空时显示文章列表 -->
    <ul v-else class="article-list">
      <li v-for="article in articleList" :key="article.id">
        <ArticleItem :article="article" />
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
.text-management {
  padding: 20px 50px;
}

.container {
  height: 100%;
  width: 100%;
  display: flex;
  background: #f5f5f5;
}

.empty-container {
  text-align: center;
  margin-top: 50px;

  p {
    font-size: 18px;
    color: #666;
    margin-bottom: 20px;
  }

  .publish-button {
    background-color: #409eff;
    border: none;
    color: white;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 4px;
    cursor: pointer;

    &:hover {
      background-color: #66b1ff;
    }
  }
}

.article-list {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 15px;
  }
}
</style>
