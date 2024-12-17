<script setup>
import { onMounted, ref } from 'vue';
import ArticleItem from './article/ArticleItem.vue';

import { getArticleListAPI } from "@/api/article";
import { ElMessage } from 'element-plus';

const articleList = ref([]);

const getArticleList = async () => {
  const res = await getArticleListAPI()
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  articleList.value = res.data.map(item => {
    //当content长度大于50时，截断并加省略号
    if (item.content.length > 50) {
      item.content = item.content.slice(0, 50) + '...'
    }
    return item
  })
}

onMounted(() => {
  getArticleList()
})
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
