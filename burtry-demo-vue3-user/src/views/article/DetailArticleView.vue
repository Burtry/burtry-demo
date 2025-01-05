<script setup>
import { ref, onMounted } from 'vue';
import { More, Star, ChatLineRound } from '@element-plus/icons-vue'
import CommentItem from "../../components/CommentItem.vue";
import { ElMessage } from 'element-plus';
import { getArticleDetailByIdAPI } from "@/api/article"
import router from '@/router';
import { useUserStore } from "@/stores/user";
import { postCommentAPI, getCommentListAPI } from "@/api/comment";
import { likeBehaviorAPI, getDataAPI, readBehaviorAPI } from "@/api/behavior";
// 使用 import 来引入图片
import likeIcon from '@/assets/like.svg';
import likedIcon from '@/assets/liked.svg';

const userStore = useUserStore();
const articleId = ref(router.currentRoute.value.params.id);
const commentsSection = ref(null);
const userInfo = ref({
  id: 0,
  username: "",
  image: ""
});
userInfo.value.id = userStore.userInfo.id;
userInfo.value.username = userStore.userInfo.username;
userInfo.value.image = userStore.userInfo.image;


const comments = ref([])
const commentContent = ref('');
const articleDetail = ref({});
const isLiked = ref(false);  // 默认未点赞

const behaviorNum = ref({
  like: 0,
  views: 0
})

const getArticleDetail = async () => {
  const res = await getArticleDetailByIdAPI(articleId.value);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  articleDetail.value = res.data;

  //获取点赞数
  getBehavior();

}
const likeArticle = async () => {
  const data = {
    articleId: articleId.value,
    operation: isLiked.value ? 0 : 1, // 如果已经点赞，操作为取消点赞；否则为点赞 //1 点赞 0 取消点赞
  }
  const res = await likeBehaviorAPI(data);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  if (res.data === "已点赞") {
    ElMessage.warning('已点赞');
    return;
  }

  if (res.data === "点赞") {
    behaviorNum.value.like += 1;
  } else {
    behaviorNum.value.like -= 1;
  }

  isLiked.value = !isLiked.value;
}


const getBehavior = async () => {
  const res = await getDataAPI(articleId.value);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  behaviorNum.value.like = res.data.likes;
  behaviorNum.value.views = res.data.views;
  if (res.data.likeMe === 1) {
    isLiked.value = true;
  }
}




const collectArticle = () => {
  console.log('收藏');

}

//点击评论滚到评论区
const commentArticle = () => {
  if (commentsSection.value) {
    commentsSection.value.scrollIntoView({ behavior: 'smooth' });
  }
}

//获取评论列表
const getCommentList = async () => {
  const res = await getCommentListAPI(articleId.value);
  if (res.code === 0) {
    ElMessage.error(res.msg);
    return;
  }
  comments.value = res.data;
}

//保存评论
const saveComment = async () => {
  const data = {
    articleId: articleId.value,
    content: commentContent.value,
    userId: userInfo.value.id
  }

  if (userStore.userInfo.id === "0") {
    ElMessage.error('请先登录');
    router.push('/login');
    return;
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
  getArticleDetail();
})

//延迟加载，当超过一定时间后执行阅读行为
setTimeout(async () => {
  const res = await readBehaviorAPI(articleId.value);
  if (res.code === 0) {
    console.log(res.msg);
  }

}, 5000)

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

      <!-- 阅读数 -->
      <div class="read-num">{{ behaviorNum.views }} 浏览</div>
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
        <img :src="isLiked ? likedIcon : likeIcon" alt="icon" width="30" height="30" class="icon-action ts"
          @click="likeArticle" />
        <span class="icon-text">{{ behaviorNum.like }}</span>

        <!-- 收藏 -->
        <el-icon class="icon-action" @click="collectArticle">
          <Star />
        </el-icon>
        <span class="icon-text">{{ behaviorNum.collect }}</span>

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

.read-num {
  margin-top: 20px;
  font-size: 12px;
  color: #999;
  text-align: right;
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
