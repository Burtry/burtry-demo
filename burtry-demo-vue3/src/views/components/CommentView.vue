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
    address: 'No. 189, Grove St, Los Angeles',
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
    <span>文章Id：</span>
    <el-input v-model="articleId" style="width: 200px; margin-right: 20px;" placeholder="请输入文章Id" />
    <span>评论Id：</span>
    <el-input v-model="commentId" style="width: 200px; margin-right: 20px;" placeholder="请输入评论Id" />

    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>
  </div>

  <el-divider />

  <!-- 列表展示 -->
  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="address" label="Address" />
  </el-table>


  <!-- 分页条 -->
  <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
    layout="sizes, prev, pager, next, jumper,total, " :total="100" :page-sizes="[5, 10, 15, 20, 50, 100]"
    @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />
</template>
