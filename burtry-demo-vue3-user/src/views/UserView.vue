<script setup>
import ArticleView from "@/views/article/UserArticleView.vue";
import { ref } from "vue";
const articles = ref([
  {
    title: 'How to Learn Vue 3',
    id: '1',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    username: 'kooriookami',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'Vue 3 Composition API Explained',
    likes: 300,
    views: 4000,
    comments: 150,
    contentPreview: 'Let’s dive deep into the Composition API of Vue 3...',
    userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    username: 'devguru',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-10-15', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    username: 'kooriookami',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08', // Date the article was published
  },
  {
    title: 'Vue 3 Composition API Explained',
    likes: 300,
    views: 4000,
    comments: 150,
    contentPreview: 'Let’s dive deep into the Composition API of Vue 3...',
    userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    username: 'devguru',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-10-15', // Date the article was published
  },
  {
    title: 'How to Learn Vue 3',
    likes: 245,
    views: 3321,
    comments: 120,
    contentPreview: 'This is a comprehensive guide to learning Vue 3...',
    userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    username: 'kooriookami',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-11-08',
  },
  {
    title: 'Vue 3 Composition API Explained',
    likes: 300,
    views: 4000,
    comments: 150,
    contentPreview: 'Let’s dive deep into the Composition API of Vue 3...',
    userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    username: 'devguru',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    publishedAt: '2024-10-15',
  },
]);

const userInfo = ref({
  id: "1",
  nickName: 'Burtry',
  phone: '13416395349',
  email: '1231231231@qq.com',
  status: '正常',
  address: 'No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu Province',
})
</script>

<template>
  <div class="bg">
    <div class="user-info">
      <el-descriptions border>
        <el-descriptions-item :rowspan="2" :width="140" label="头像" align="center">
          <el-image style="width: 100px; height: 100px"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        </el-descriptions-item>
        <el-descriptions-item label="用户昵称">{{ userInfo.nickName }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ userInfo.phone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag>{{ userInfo.status }}</el-tag>
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
        <h3 style="margin-bottom: 20px;">用户发表文章</h3>
        <ArticleView :articles="articles" />
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
