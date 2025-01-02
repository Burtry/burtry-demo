<script setup>
import { defineProps } from 'vue'
import { RouterLink } from 'vue-router'

// 接收来自父组件的 `comments` 和 `floor` props
defineProps({
  comments: {
    type: Object,
    required: true
  },
  floor: {
    type: Number,
    required: true
  }
})
</script>

<template>
  <div class="comment-item">
    <!-- 用户头像 -->
    <RouterLink :to="`/user/${comments.userId}`">
      <el-image class="user-image" :src="comments.userAvatar" />
    </RouterLink>

    <!-- 评论详情 -->
    <div class="comment-content">
      <!-- 用户名 -->
      <RouterLink :to="`/user/${comments.userId}`" class="user-name">
        {{ comments.username }}
      </RouterLink>
      <!-- 评论内容 -->
      <p class="comment-text">{{ comments.content }}</p>
      <!-- 发布时间 -->
      <div class="publish-time">{{ comments.publishTime }}</div>
    </div>

    <!-- 楼层信息 -->
    <div class="floor-info">
      #{{ floor }}
    </div>
  </div>
</template>

<style lang="scss" scoped>
.comment-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
  position: relative;
}

.user-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.comment-content {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-weight: bold;
  text-decoration: none;

  &:hover {
    color: #409eff;
  }
}

.comment-text {
  margin: 5px 0;
  font-size: 14px;
  color: #333;
}

.publish-time {
  font-size: 12px;
  color: #999;
}

.floor-info {
  margin-left: auto;
  color: #666;
  font-size: 14px;
  // 居中
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}
</style>
