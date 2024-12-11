<script setup>
import { ref } from 'vue';
import ArticleItem from './article/ArticleItem.vue';

const articleList = ref([
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
</script>

<template>
  <div class="text-management">
    <h1 style="margin-bottom: 20px;">文章管理</h1>
    <ul class="article-list">
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
</style>
