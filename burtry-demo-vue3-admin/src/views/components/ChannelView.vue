<script setup>
import { ref } from 'vue';
const keyword = ref("")

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
  console.log(keyword.value);

}

const addSensitive = () => {
  console.log("添加频道");
}

</script>


<template>
  <div>
    <span>关键字：</span>
    <el-input v-model="keyword" style="width: 200px; margin-right: 20px;" placeholder="请输入频道名称" />
    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>

    <!-- 添加按钮 -->
    <el-button type="primary" style=" float: right; width: 100px; " @click="addSensitive">添加频道</el-button>
  </div>

  <el-divider />

  <!-- 列表展示 -->
  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="频道ID" width="200" />
    <el-table-column prop="name" label="频道名称" width="200" />
    <el-table-column prop="description" label="频道描述" width="200" show-overflow-tooltip />
    <el-table-column prop="status" label="频道状态" width="200" />
    <el-table-column prop="isDefault" label="是否默认" width="200" />
    <el-table-column prop="createTime" label="创建时间" width="200" />
    <el-table-column fixed="right" label="操作" width="120">
      <template #default>
        <el-button link type="primary" size="large" @click="handleClick">修改</el-button>
        <el-button link type="primary" size="large">删除</el-button>
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
