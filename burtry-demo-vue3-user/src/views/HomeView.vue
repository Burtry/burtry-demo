<script setup>
import { Search } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue';
import ArticleView from "@/views/article/ArticleView.vue";

import { getChannelListAPI } from "@/api/channel";
import { ElMessage } from 'element-plus';

const articles = ref([
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    username: 'kooriookami',
    userId: '1',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },


]);

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
}

onMounted(() => {
  getChannelList();
});

const activeChannel = ref(1);

const handleChannelClick = (id) => {
  activeChannel.value = id;
};


const pageData = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const OnSizeChange = (val) => {
  console.log(val);
};

const OnCurrentChange = (val) => {
  console.log(val);
};

</script>

<template>
  <div class="bg">
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
    <div class="article-list">
      <div v-for="article in articles" :key="article.id" class="article-item">
        <ArticleView :article="article" />
      </div>
      <!-- 分页条 -->
      <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize"
        :page-sizes="[5, 10, 15, 20]" size="large" background layout="sizes, prev, pager, next, jumper, total"
        :total="pageData.total" @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />
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
