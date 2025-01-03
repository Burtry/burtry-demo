<script setup>
import { ref, onMounted } from 'vue';
import { More, Star, ChatLineRound } from '@element-plus/icons-vue'
import CommentItem from "../../components/CommentItem.vue";
import { ElMessage } from 'element-plus';
import { getArticleDetailByIdAPI } from "@/api/article"
import router from '@/router';
import { useUserStore } from "@/stores/user";
import { postCommentAPI, getCommentListAPI } from "@/api/comment";

const userStore = useUserStore();


const articleId = ref(router.currentRoute.value.params.id);

const userInfo = ref({
  id: 0,
  username: "",
  image: ""
});


const articleDetail = ref({});

const getArticleDetail = async () => {
  const res = await getArticleDetailByIdAPI(articleId.value);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  articleDetail.value = res.data;

}

userInfo.value.id = userStore.userInfo.id;
userInfo.value.username = userStore.userInfo.username;
userInfo.value.image = userStore.userInfo.image;
onMounted(() => {

  getArticleDetail();

})



const commentsSection = ref(null);
const likeArticle = () => {
  console.log('点赞');

}

const collectArticle = () => {
  console.log('收藏');

}

const commentArticle = () => {
  if (commentsSection.value) {
    commentsSection.value.scrollIntoView({ behavior: 'smooth' });
  }

}

const comments = ref([])

const commentContent = ref('');

const getCommentList = async () => {
  const res = await getCommentListAPI(articleId.value);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  comments.value = res.data;
}

const saveComment = async () => {
  const data = {
    articleId: articleId.value,
    content: commentContent.value,
    userId: userInfo.value.id
  }

  if (data.content === '') {
    ElMessage.error('评论内容不能为空');
    return;
  }
  const res = await postCommentAPI(data);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  ElMessage.success('评论成功');
  commentContent.value = '';

  //重新获取评论列表
  getCommentList();
}

onMounted(() => {
  getCommentList();
})



</script>

<template>
  <div class="bg">
    <div class="article-content">
      <h1>{{ articleDetail.title }} 标题</h1>

      <!-- 用户信息 -->
      <div class="user-info">
        <RouterLink :to="`/user/${articleDetail.userId}`"><el-image class="user-image"
            :src="articleDetail.userAvatar" />
        </RouterLink>
        <div class="user-center">
          <RouterLink :to="`/user/${articleDetail.userId}`">
            <div class="user-name">{{ articleDetail.username }}</div>
          </RouterLink>
          <div class="publish-time">编辑于{{ articleDetail.updateTime }}</div>
        </div>
        <div class="user-right">
          <el-dropdown>
            <span class="el-dropdown-link">
              <el-icon size="20" color="#9599a0">
                <More />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>举报</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>

      <!-- 文章内容 -->
      <div class="article-text" v-html="articleDetail.content">
      </div>
      <el-divider />

      <!-- 评论区 -->
      <div class="article-comment" ref="commentsSection">
        <h1 style="color: #62666d; font-size: 18px; margin-bottom: 20px;">评论&nbsp;{{ comments.length }}</h1>
        <!-- 评论输入框 -->
        <div class="comment-input">

          <el-image class="user-image comment-image" :src="userInfo.image" />
          <el-input v-model="commentContent" type="textarea" placeholder="请输入一条友善的评论" :rows="3" />

        </div>
        <!-- 评论按钮 -->
        <div class="comment-button">
          <el-button type="primary" @click="saveComment">发表评论</el-button>
        </div>

        <!-- 评论列表 -->
        <div class="comment-list">
          <ul>
            <li v-for="(comment, index) in comments.slice().reverse()" :key="comment.id">
              <CommentItem :comments="comment" :floor="comments.length - index" />
              <el-divider />
            </li>
          </ul>
          <div class="no-comment">没有更多评论</div>
        </div>
      </div>

      <!-- 文章点赞、收藏、评论操作栏 -->
      <div class="article-fixed">
        <!-- 点赞 -->
        <img src="@/assets/like.svg" alt="icon" width="30" height="30" class="icon-action ts" @click="likeArticle" />
        <span class="icon-text">{{ userInfo.id }}</span>

        <!-- 收藏 -->
        <el-icon class="icon-action" @click="collectArticle">

          <Star />
        </el-icon>
        <span class="icon-text">{{ userInfo.id }}</span>

        <!-- 评论 -->
        <el-icon class="icon-action" @click="commentArticle">
          <ChatLineRound />
        </el-icon>
        <span class="icon-text">{{ comments.length }}</span>


      </div>
    </div>
  </div>
</template>


<style lang="scss" scoped>
.bg {
  min-height: 100vh;
  background-image: url('@/assets/bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  display: flex;
  justify-content: center;
  padding: 20px;
}

.article-content {
  position: relative;
  min-width: 750px;
  max-width: 800px;
  padding: 50px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 5px;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.user-center {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  margin-left: 15px;
}

.user-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-top: 20px;
}

.user-name {
  margin-left: 10px;
  font-size: 18px;
  font-weight: bold;

  &:hover {
    text-decoration: none;
    color: #409eff;
  }
}

.publish-time {

  margin-left: 10px;
  margin-top: 5px;
  font-size: 12px;
  color: #999;
}

.user-right {
  margin-left: auto;

}

.article-text {
  margin-top: 20px;
  font-size: 16px;
  line-height: 1.5;
  text-align: justify;
}

.article-comment {
  background-color: #f7fbfa;
}

.select-type {
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: -10px;
}

.comment-input {
  display: flex;
  align-items: flex-start;
}

.comment-image {
  margin-right: 10px;
  margin-top: 2px;
}

.comment-button {
  text-align: right;
  margin-top: 5px;
  margin-bottom: 20px;
}

.no-comment {
  text-align: center;
  margin-top: 20px;
  font-size: 13px;
  color: #999;
}

.icon-text {
  font-size: 12px;
  color: #999;

}


.article-fixed {
  position: fixed;
  top: 30%;
  /* 让它在浏览器窗口垂直方向上固定 */
  right: 10px;
  /* 固定在浏览器窗口的右边，确保不会受文章内容影响 */
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

}

.icon-action {
  font-size: 2rem;
  /* 使用相对单位 */
  color: #333;
  margin: 15px 0;
  /* 保持元素之间的间距 */
  cursor: pointer;
  transition: color 0.3s;
}

.icon-action:hover {
  color: #409eff;
}

.icon-text {
  font-size: 12px;
  color: #999;
  margin-top: -5px;
}

.ts {
  background-color: transparent;
}

.ts:hover {
  filter: brightness(0) saturate(100%) invert(69%) sepia(45%) saturate(2037%) hue-rotate(178deg) brightness(95%) contrast(101%);
}
</style>
