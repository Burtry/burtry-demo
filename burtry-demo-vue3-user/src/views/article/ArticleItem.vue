<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { deleteArticleAPI } from "@/api/article";
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
defineProps({
  article: {
    type: Object,
    required: true,
    default: () => ({})
  }
})

const delDialog = ref(false)

const deleteArticle = async (id) => {
  const res = await deleteArticleAPI(id)
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }

  ElMessage.success(res.msg)
  window.location.reload()
}
</script>

<template>
  <div class="article-container">
    <div class="article-card" @mouseover="isHovered = true" @mouseleave="isHovered = false">
      <div class="article-content">
        <div class="image-container">
          <el-image class="article-image" :src="article.image" />
        </div>

        <div class="text-content">
          <h3 class="title" @click="$router.push(`/article/${article.id}`)">{{ article.title }}</h3>

          <div class="meta">
            <span>点赞数: {{ article.likes }}</span>
            <span>阅读数: {{ article.views }}</span>
            <span>评论数: {{ article.comments }}</span>
          </div>

          <div v-html="article.content"></div>

          <div class="user-info">
            <el-image class="avatar" :src="article.userAvatar" alt="User Avatar" />
            <span>{{ article.username }}</span>
            <span class="published-at">发布时间：{{ article.publishTime }}</span>
            <span class="published-at">所述频道：{{ article.channelName }}</span>

            <span class="status">
              当前状态:
              <el-tag
                :type="article.status === 1 ? 'warning' : article.status === 2 ? 'info' : article.status === 3 ? 'primary' : article.status == 4 ? 'success' : 'danger'">{{
                  article.status === 1 ? "草稿" : article.status === 2 ? "审核中" : article.status ===
                    3 ? "待发布" : article.status == 4 ? "已发布" : "已锁定" }}</el-tag>
            </span>

            <el-button size="large" class="operation-button" :icon="Edit" round>
            </el-button>
            <el-button size="large" class="" :icon="Delete" round @click="delDialog = true">
            </el-button>
          </div>
        </div>
      </div>

    </div>
  </div>


  <el-dialog v-model="delDialog" title="删除文章" width="500">
    <span>你确定要删除吗(真的很久！)?</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="delDialog = false">取消</el-button>
        <el-button type="primary" @click="deleteArticle(article.id)">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.operation-button {
  margin-left: auto;
}

.status {
  margin-left: auto;
}

.published-at {
  font-size: 0.8rem;
  color: #999;
  margin-left: 100px;
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
  margin: 5px;
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
  margin-bottom: 20px;
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

  .title {
    font-size: 1.8rem;
    font-weight: bold;

    &:hover {
      color: #409eff;
      cursor: pointer;
    }
  }
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
