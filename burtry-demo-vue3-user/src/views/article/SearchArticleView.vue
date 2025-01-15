<script setup>
defineProps({
  article: {
    type: [Array, Object],
    required: true,
  },
})

</script>

<template>
  <div class="article-view">
    <div class="image-container">
      <RouterLink :to="`/article/${article.id}`"><el-image class="article-image" :src="article.images" /></RouterLink>
    </div>
    <div class="article-content">
      <!-- Title -->
      <RouterLink :to="`/article/${article.id}`">
        <h3 class="article-title" v-html="article.title"></h3>
      </RouterLink>

      <!-- Statistics -->
      <div class="article-stats">
        <span>阅读: {{ article.views }}</span>
        <span>点赞: {{ article.likes }}</span>
        <span>评论: {{ article.comments }}</span>
      </div>

      <!-- Content Snippet -->
      <p class="article-snippet" v-html="article.content"></p>

      <!-- Author and Time -->
      <div class="article-footer">
        <span style="font-weight: bolder;">作者:</span>
        <RouterLink :to="`/user/${article.userId}`"> <span class="author-name">{{ article.authorName }}</span>
        </RouterLink>
        <span style="margin-left: 80px;">{{ article.channelName }}</span>
        <span class="publish-time">{{ article.publishTime }}</span>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
.article-view {
  display: flex;
  padding: 20px;
  height: auto;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.4);
  border-radius: 10px;
  transition: transform 0.5s ease, box-shadow 0.5s ease;
  align-items: flex-start;

  &:hover {
    transform: scale(1.02);
    box-shadow: 0 2px 2px rgba(139, 188, 255, 0.6);
  }
}

.image-container {
  width: 150px;
  margin-right: 20px;
}

.article-image {
  width: 100%;
  height: 100%;
  border-radius: 8px;
  object-fit: cover;
}

.article-content {
  flex: 1;

  em {
    color: #e15f8f;
    /* 设置为红色 */
    font-style: normal;
    /* 如果你希望去掉斜体，可以设置 font-style 为 normal */
  }
}

.article-title {
  font-size: 20px;
  color: #333;
  margin: 0 0 10px;

  &:hover {
    color: #8bbcff;
  }

  em {
    color: #e15f8f;
    /* 设置为红色 */
    font-style: normal;
    /* 如果你希望去掉斜体，可以设置 font-style 为 normal */
  }
}

.article-stats {
  display: flex;
  gap: 15px;
  font-size: 14px;
  color: #888;
  margin-bottom: 10px;
}

.article-snippet {
  font-size: 16px;
  color: #555;
  margin-bottom: 15px;
}

.article-footer {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  color: #888;
}


.author-name {
  font-weight: bold;
  color: #333;

  &:hover {
    color: #8bbcff;
  }
}

.publish-time {
  color: #aaa;
  margin-left: auto;
  margin-right: 25px;
}
</style>
