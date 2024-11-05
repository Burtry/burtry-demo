<script setup>
import { ref } from 'vue';
const articleId = ref("")
const commentId = ref("")

const pageData = ref({
  pageSize: 10,
  pageNum: 1,
})

const dataList = ref([

  {
    date: '2016-05-02',
    name: 'Tom',
    content: 'asddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd',
    address: 'No. 189, Grove St, Los Angeles',
    createTime: '2023-05-02 12:00:00'
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  }
])

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  console.log(pageNum);

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  console.log(pageSize);

}

const handleSearch = () => {
  console.log(articleId.value);
  console.log(commentId.value);
}

</script>


<template>
  <div>
    <span>查 询：</span>
    <el-input v-model="keywordId" style="width: 200px; " placeholder="请输入文章Id" />
    <el-button type="primary" style="width: 100px;;" @click="handleSearch">查询</el-button>
    <el-input v-model="keywordName" style="width: 200px; margin-left: 20px; " placeholder="请输入评论Id" />
    <el-button type="primary" style="width: 100px;" @click="handleSearch">查询</el-button>
  </div>

  <el-divider />

  <!-- 列表展示 -->
  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="评论ID" width="200" />
    <el-table-column prop="articleID" label="文章ID" width="200" />
    <el-table-column prop="userID" label="用户ID" width="200" />
    <el-table-column prop="content" label="评论内容" show-overflow-tooltip />
    <el-table-column prop="createTime" label="评论时间" width="180" />
    <el-table-column fixed="right" label="操作" width="120">
      <template #default>
        <el-button link type="primary" size="large" @click="handleClick">查看</el-button>
        <el-button link type="primary" size="large">修改</el-button>
      </template>
    </el-table-column>
  </el-table>


  <!-- 分页条 -->
  <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
    layout="sizes, prev, pager, next, jumper,total, " :total="100" :page-sizes="[5, 10, 15, 20, 50, 100]"
    @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />
</template>
<style lang="scss" scoped>
.page {


  margin-top: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
