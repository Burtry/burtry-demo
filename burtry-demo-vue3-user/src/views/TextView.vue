<script setup>
import '@wangeditor/editor/dist/css/style.css'

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
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
  keys: ['menu2', 'menu1', 'menu3'], // 自定义的菜单 key ，多个菜单用逗号分隔
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

const fileList = ref([
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
]);


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
      <!-- 封面图片 -->
      <el-upload class="upload-demo" drag action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
        multiple>
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          上传封面<em>点击上传</em>
          <p style="font-size: 10px;">文件大小小于500kb</p>
        </div>
      </el-upload>
    </div>


  </div>

  <div class="icp">
    <a href="https://beian.miit.gov.cn/" target="_blank">
      豫ICP备2024095519号 (仅用于学习使用，无商业用途)</a>
  </div>
</template>

<style lang="scss" scoped>
.upload-demo {
  margin-bottom: 80px;
}

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

.icp {
  // 位于元素最下方
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  text-align: center;
  font-size: 12px;
  color: #999;
  background-color: #f7f8fa;
  padding: 10px 0;

  a {
    color: #999;
    text-decoration: none;
  }
}
</style>
