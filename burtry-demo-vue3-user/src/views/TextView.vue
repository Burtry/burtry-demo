<script setup>
import '@wangeditor/editor/dist/css/style.css'

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { UploadFilled } from '@element-plus/icons-vue'
// 编辑器实例
const editorRef = shallowRef()
const valueHtml = ref('<p>hello</p>')

const title = ref('');
const mode = ref('');

// 模拟异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
  }, 1500)
})

const toolbarConfig = {}
toolbarConfig.insertKeys = {
  index: 31, // 自定义插入的位置
  keys: ['keepMenu'], // 自定义的菜单 key ，多个菜单用逗号分隔
}

toolbarConfig.excludeKeys = [
  'fullScreen',
  'redo',
  'undo',
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

//https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png
const imageUrl = ref('')

const closeComment = ref(false)
const agreement = ref(false)


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
        <el-upload class="avatar-uploader" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
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
          <el-button @click="publish" type="primary">立即发布</el-button>
          <el-button @click="publish">定时发布</el-button>
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
