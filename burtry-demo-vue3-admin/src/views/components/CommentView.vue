<script setup>
import { ref, onMounted } from 'vue';

import { getCommentListAPI, getCommentByIdAPI, deleteCommentAPI } from "@/api/comment";
import { ElMessage } from 'element-plus';

const articleId = ref("")
const commentId = ref("")

const comment = ref({})
const dialogComment = ref(false)


const pageData = ref({
  pageSize: 10,
  pageNum: 1,
  total: 0

})

const dataList = ref([])

const getDataList = async () => {
  const params = {
    pageNum: pageData.value.pageNum,
    pageSize: pageData.value.pageSize,
    articleId: articleId.value,
  }
  const res = await getCommentListAPI(params);
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }

  dataList.value = res.data.list
  pageData.value.total = res.data.total
}

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  getDataList()

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  getDataList()

}

const getByCommentId = async () => {
  const res = await getCommentByIdAPI(commentId.value);
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  comment.value = res.data
  dialogComment.value = true
}

const handleSearch = () => {
  getDataList()
}

const handleDialogCommentClose = () => {
  dialogComment.value = false
  commentId.value = ""
  comment.value = {}
}

const handlerDeleteComment = async (id) => {
  const res = await deleteCommentAPI(id);
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  ElMessage.success("删除成功")
  getDataList()
  dialogComment.value = false
  commentId.value = ""
}

onMounted(() => {
  getDataList()

})

</script>


<template>
  <div>
    <span>查 询：</span>
    <el-input v-model="articleId" style="width: 200px; " placeholder="请输入文章Id" />
    <el-button type="primary" style="width: 100px;;" @click="handleSearch">查询</el-button>
    <el-input v-model="commentId" style="width: 200px; margin-left: 20px; " placeholder="请输入评论Id" />
    <el-button type="primary" style="width: 100px;" @click="getByCommentId">查询</el-button>
  </div>

  <el-divider />

  <!-- 空列表 -->
  <el-empty v-if="dataList.length === 0" description="暂无该文章评论" />
  <!-- 列表展示 -->
  <el-table v-else :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="评论ID" width="200" />
    <el-table-column prop="articleId" label="文章ID" width="200" />
    <el-table-column prop="userId" label="用户ID" width="200" />
    <el-table-column prop="content" label="评论内容" show-overflow-tooltip />
    <el-table-column prop="createTime" label="评论时间" width="180" />
    <el-table-column fixed="right" label="操作" width="120">
      <template #default='scope'>
        <el-button link type="warning" size="large" @click="handlerDeleteComment(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>


  <!-- 分页条 -->
  <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
    layout="sizes, prev, pager, next, jumper,total, " :total="Number(pageData.total)"
    :page-sizes="[5, 10, 15, 20, 50, 100]" @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />


  <el-dialog v-model="dialogComment" title="评论信息" width="1200" :before-close="handleDialogCommentClose">
    <el-table :data="[comment]" stripe style="width: 100%">
      <el-table-column prop="id" label="评论ID" width="200" />
      <el-table-column prop="articleId" label="文章ID" width="200" />
      <el-table-column prop="userId" label="用户ID" width="200" />
      <el-table-column prop="content" label="评论内容" show-overflow-tooltip />
      <el-table-column prop="createTime" label="评论时间" width="180" />
      <el-table-column fixed="right" label="操作" width="220">
        <template #default="scope">
          <el-button link type="warning" size="large" @click="handlerDeleteComment(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<style lang="scss" scoped>
.page {


  margin-top: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
