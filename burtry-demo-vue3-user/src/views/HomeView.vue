<script setup>
import { Search } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue';
import ArticleView from "@/views/article/ArticleView.vue";

import { getChannelListAPI } from "@/api/channel";
import { ElMessage } from 'element-plus';

import { getHomeArticleListAPI } from "@/api/article";

const articles = ref([
]);
const noMore = ref(false);
const loadData = ref({
  channelId: "", // 频道id
  pageNum: 1,
  pageSize: 10,
})

// 获取文章列表
const loadArticles = async () => {
  loadData.value.channelId = activeChannel.value;
  const res = await getHomeArticleListAPI(loadData.value)
  if (res.code === 0) {
    ElMessage.error("获取文章列表失败")
    return
  }
  if (res.data.length === 0) {
    noMore.value = true;
    loading.value = false;
    return
  }
  articles.value.push(...res.data)
  loading.value = false;
}

const searchInfo = ref('');

const channelList = ref([]);

const getChannelList = async () => {
  const res = await getChannelListAPI()
  if (res.code === 0) {
    ElMessage.error("获取频道列表失败")
    return
  }
  channelList.value = res.data
  activeChannel.value = channelList.value[0].id

  // 获取文章列表
  loadArticles();
}

onMounted(() => {
  getChannelList();
});

const activeChannel = ref(1);

const handleChannelClick = (id) => {
  activeChannel.value = id;
  loadData.value.pageNum = 1;
  articles.value = [];
  noMore.value = false;
  loadArticles();
};

const loading = ref(false);

const load = () => {
  if (loading.value) return;  // 防止在加载期间再次触发

  loading.value = true;
  loadData.value.pageNum++;
  loadArticles();
}

</script>

<template>
  <div class="bg" v-infinite-scroll="load">
    <div class="search-container">
      <el-input v-model="searchInfo" class="search" placeholder="输入关键字搜索" :prefix-icon="Search" clearable />
      <el-button type="primary" class="submit">搜索</el-button>
    </div>

    <!-- 频道 -->
    <div class="channel">
      <el-button v-for="channel in channelList" :key="channel.id" text
        :class="{ 'active': activeChannel === channel.id }" @click="handleChannelClick(channel.id)"
        class="channel-item">
        {{ channel.name }}
      </el-button>
    </div>
    <el-divider />
    <div class="article-list" style="overflow: auto">
      <div v-for="article in articles" :key="article.id" class="article-item">
        <ArticleView :article="article" />
      </div>
      <p v-if="loading" class="loading">Loading...</p>
      <p v-if="noMore" class="loading">没有更多内容了...</p>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.loading {
  text-align: center;
  margin-top: 20px;
  font-size: 18px;
  color: #999;
  margin-left: 47%;
}

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

.active {
  color: #409eff;
  border-bottom: 2px solid #409eff;
}

.channel {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-top: 20px;
}

.channel-item {
  height: 40px;
  width: 150px;
  font-size: 18px;
  margin-right: 10px;

  &:hover {
    color: #409eff;
    background-color: transparent !important; // 禁止背景颜色变化
  }
}

.search-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search,
.submit {
  height: 50px;
  border-radius: 25px;
  border: 1px solid #fff;
  color: #fff;
  font-size: 20px;
}

.search {
  width: 540px;
  border-radius: 25px 0 0 25px;
  padding-left: 20px;
}

.submit {
  width: 100px;
}

.article-list {
  width: 100%;
  max-width: 1400px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  background-color: rgba(255, 255, 255, 0.6);
  border-radius: 10px;
  padding: 20px;

}

.article-item {
  width: 48%;
  height: 150px;
  margin-bottom: 20px;
  background-color: rgba(255, 255, 255, 0.4);
  border-radius: 10px;
  margin-bottom: 50px;
}

.page {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}
</style>
