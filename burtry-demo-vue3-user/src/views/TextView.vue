<script setup>
import '@wangeditor/editor/dist/css/style.css'
//onMounted
import { onBeforeUnmount, onMounted, ref, shallowRef } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { getChannelListAPI } from "@/api/channel";
import { publishArticleAPI, getArticleByIdAPI } from "@/api/article";
import { uploadFileAPI } from "@/api/upload";
import router from '@/router';
import { useRoute } from 'vue-router';
const route = useRoute();
// 编辑器实例
const editorRef = shallowRef()
const valueHtml = ref('')
const mode = ref('');
const toolbarConfig = {}
toolbarConfig.excludeKeys = [
  'fullScreen',
  'group-video'
]
const editorConfig = { placeholder: '请输入内容...' }

// 销毁编辑器实例
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor) editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor
}
// 模拟异步获取内容
// onMounted(() => {
//   setTimeout(() => {
//     valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
//   }, 1500)
// })
//----------------------------------------------------------------------------------
//https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png

const imageUrl = ref("")
const title = ref('');
const channelList = ref([])
const channelId = ref('')
const getChannelList = async () => {
  const res = await getChannelListAPI()
  channelList.value = res.data.slice(3)
}

const closeComment = ref(false)
const agreement = ref(false)
const scheduledPublishTime = ref('')
const scheduledDialog = ref(false)
const articleId = ref("")
const articleData = ref({
  id: "",
  title: "",
  content: "",
  channelId: "",
  closeComment: false,
  images: "",
  status: 1, //1为草稿，2为待审核  //默认为草稿
  publishTime: ""
})

//立即发布
const handlerPublish = async () => {
  if (agreement.value === false) {
    ElMessage.error('请先阅读并同意协议')
    return
  }
  articleData.value.id = articleId.value
  articleData.value.title = title.value
  articleData.value.content = valueHtml.value
  articleData.value.channelId = channelId.value
  articleData.value.closeComment = closeComment.value
  articleData.value.images = imageUrl.value
  //立即发布
  articleData.value.status = 2

  articleData.value.publishTime = ""

  const res = await publishArticleAPI(articleData.value)
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  //发布成功后操作
  ElMessage.success(res.msg)
  title.value = ''
  valueHtml.value = ''
  imageUrl.value = ''
  channelId.value = ''
  closeComment.value = false
  agreement.value = false

  //跳转到文章管理
  router.push('/textManagement')

}

//保存草稿
const handlerSave = async () => {
  articleData.value.id = articleId.value
  articleData.value.title = title.value
  articleData.value.content = valueHtml.value
  articleData.value.channelId = channelId.value
  articleData.value.closeComment = closeComment.value
  articleData.value.images = imageUrl.value
  //暂存草稿
  articleData.value.status = 1

  articleData.value.publishTime = ""

  const res = await publishArticleAPI(articleData.value)
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  //草稿暂存成功后操作
  ElMessage.success(res.msg)
  title.value = ''
  valueHtml.value = ''
  imageUrl.value = ''
  channelId.value = ''
  closeComment.value = false

  //跳转到文章管理
  router.push('/textManagement')
}


const scheduledPublish = async () => {
  if (agreement.value === false) {
    ElMessage.error('请先阅读并同意协议')
    return
  }
  articleData.value.id = articleId.value
  articleData.value.title = title.value
  articleData.value.content = valueHtml.value
  articleData.value.channelId = channelId.value
  articleData.value.closeComment = closeComment.value
  articleData.value.images = imageUrl.value
  //定时发布
  articleData.value.status = 2  //待审核状态
  articleData.value.publishTime = scheduledPublishTime.value

  const res = await publishArticleAPI(articleData.value)
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  //定时发布成功后操作
  ElMessage.success(res.msg)
  title.value = ''
  valueHtml.value = ''
  imageUrl.value = ''
  channelId.value = ''
  closeComment.value = false
  agreement.value = false

  //跳转到文章管理
  router.push('/textManagement')
}




const uploadImage = async (params) => {
  const file = params.file
  // 根据后台需求数据格式
  const form = new FormData();
  // 文件对象
  form.append('file', file);

  const res = await uploadFileAPI(form)
  if (res.code === 0) {
    ElMessage.error('上传失败')
    return
  }
  imageUrl.value = res.data
  ElMessage.success('上传成功')
}


onMounted(() => {
  getChannelList()
  // //获取要编辑的文章
  articleId.value = route.query.id ? route.query.id : ""
  if (articleId.value) {
    //获取该文章
    getArticleByIdAPI(articleId.value).then(res => {
      if (res.code === 0) {
        ElMessage.error(res.msg)
        return
      }
      title.value = res.data.title
      valueHtml.value = res.data.content
      imageUrl.value = res.data.image
      channelId.value = res.data.channelId
      closeComment.value = res.data.isComment === 0 ? false : true

    })
  }


})
</script>


<template>
  <div class="background-image">
    <div class="tencent-docs-editor">
      <!-- Toolbar -->
      <Toolbar class="tencent-docs-toolbar" :editor="editorRef" :defaultConfig="toolbarConfig" :mode="mode" />
      <input v-model="title" class="title-input" placeholder="请输入标题" type="text" />
      <!-- Editor -->
      <Editor class="tencent-docs-editor-content" v-model="valueHtml" :defaultConfig="editorConfig" :mode="mode"
        style="height: 600px; overflow-y: hidden;" @onCreated="handleCreated" />
    </div>

    <!-- 操作栏 -->
    <div class="operation-bar">
      <!-- 封面上传 -->
      <div class="left">
        <el-upload class="avatar-uploader" action="##" :show-file-list="false" :http-request="uploadImage">
          <div v-if="imageUrl" class="image-container">
            <img :src="imageUrl" class="avatar" />
          </div>
          <div v-else class="placeholder-container">
            <el-icon class="avatar-uploader-icon">
              <Plus />
            </el-icon>
            <div class="upload-text">点击上传封面</div>
            <div class="upload-subtext">jpg/png 文件，大小小于 500KB</div>
          </div>
        </el-upload>
      </div>

      <!-- 操作 -->
      <div class="right">
        <!-- 选择频道 -->
        <el-select v-model="channelId" placeholder="请选择文章所属频道">
          <el-option v-for="item in channelList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <!-- 是否可评论 -->
        <el-checkbox v-model="closeComment">关闭评论</el-checkbox>
        <!-- 遵守协议 -->
        <el-checkbox v-model="agreement">我已阅读并同意遵守<a href="#" class="agreement">用户协议</a></el-checkbox>
        <!-- 发布按钮 -->
        <div class="buttons">
          <el-button @click="handlerPublish" type="primary">立即发布</el-button>
          <el-button @click="handlerSave" type="default">暂存草稿</el-button>
          <el-button @click="scheduledDialog = true" type="default">定时发布</el-button>
          <div v-if="scheduledDialog">
            <el-date-picker v-model="scheduledPublishTime" type="datetime" placeholder="选择时间"
              format="YYYY-MM-DD hh:mm:ss" value-format="x">
            </el-date-picker>
            <el-button @click="scheduledPublish" type="primary"
              style="margin-left: 40px; margin-top: 10px;">确认</el-button>
            <el-button @click="scheduledPublishTime = '', scheduledDialog = false" type="default"
              style="margin-left: 10px; margin-top: 10px;">取消</el-button>
          </div>

        </div>
      </div>
    </div>




  </div>

</template>

<style lang="scss" scoped>
.tencent-docs-editor {
  border: 1px solid #dfe1e5;
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  max-width: 900px;
  margin: 0 auto;
}

.tencent-docs-toolbar {
  border-bottom: 1px solid #dfe1e5;
  padding: 8px 12px;
  background-color: #f7f8fa;
}

.tencent-docs-editor-content {
  height: 500px;
  padding: 16px;
  overflow-y: auto;
  background-color: #ffffff;
  font-size: 14px;
  line-height: 1.6;
  color: #333;
}

.background-image {
  height: 100%;
  background-image: url('@/assets/bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.title-input {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border-bottom: 1px solid #ccc;
  font-size: 28px;
  outline: none;
  text-align: center;
}

.operation-bar {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 16px;
  background-color: #ffffff;
  border: 1px solid #dfe1e5;
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  max-width: 900px;
  margin: 6px auto;
}

.left {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.right {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.avatar-uploader {
  width: 300px;
  height: 180px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  overflow: hidden;
  cursor: pointer;
  transition: border-color 0.3s;
}

.avatar-uploader:hover {
  border-color: var(--el-color-primary);
}

.image-container {
  width: 100%;
  height: 100%;
}

.avatar {
  width: 300px;
  height: 180px;
}

.placeholder-container {
  text-align: center;
  color: #999;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  margin-bottom: 8px;
}

.upload-text {
  font-size: 14px;
  margin-bottom: 4px;
}

.upload-subtext {
  font-size: 12px;
  color: #ccc;
}

.agreement {
  color: #609fff;
  text-decoration: none;
}

.buttons {
  display: flex;
  gap: 12px;
  justify-content: flex-start;
}
</style>
