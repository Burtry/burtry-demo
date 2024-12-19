<script setup>
defineProps({
  articles: {
    type: Array,
    required: true,
  },
})

// 定义内容最大显示长度
const maxLength = 20;
// 方法：截断内容并加上省略号
const getTruncatedContent = (content) => {
  return content.length > maxLength ? content.slice(0, maxLength) + '...' : content;
};

</script>

<template>
  <div class="article-container">
    <div v-for="(article, index) in articles" :key="index" class="article-card" @mouseover="isHovered = true"
      @mouseleave="isHovered = false" @click="$router.push(`/article/${article.id}`)">
      <div class="article-content">
        <div class="image-container">
          <el-image class="article-image" :src="article.image" />
        </div>

        <div class="text-content">
          <h3>{{ article.title }}</h3>

          <div class="meta">
            <span>点赞数: {{ article.likes }}</span>
            <span>阅读数: {{ article.views }}</span>
            <span>评论数: {{ article.comments }}</span>
          </div>

          <div class="content">
            <p v-html="article.isExpanded ? article.content : getTruncatedContent(article.content)"></p>
          </div>

          <div class="user-info">
            <el-image class="avatar" :src="article.userAvatar" alt="User Avatar" />
            <span>{{ article.username }}</span>
            <span class="published-at">发布时间：{{ article.publishTime }}</span>
            <span class="published-at">所述频道：{{ article.channelName }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.published-at {
  font-size: 0.8rem;
  color: #999;
  margin-left: 50px;
}

.article-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  display: flex;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out, box-shadow 0.3s ease-in-out;
  cursor: pointer;
}

.article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.article-content {
  display: flex;
  align-items: flex-start;
  width: 100%;
}

.image-container {
  width: 150px;
  margin-right: 20px;
}

.article-image {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.text-content {
  flex: 1;
}

h3 {
  margin-bottom: 10px;
  font-size: 1.6rem;
}

.meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  font-size: 1rem;
  color: #555;
}

.meta span {
  padding: 5px;
  background-color: #f0f0f0;
  border-radius: 5px;
}

.content p {
  font-size: 1rem;
  color: #333;
  line-height: 1.5;
  margin-bottom: 15px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.user-info {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.user-info span {
  font-size: 1rem;
  color: #333;
}
</style>
