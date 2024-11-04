<script setup>
import { ref } from 'vue';
const keywordId = ref("")
const keywordName = ref("")

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
  console.log(keywordId.value);
  console.log(keywordName.value);
}

</script>


<template>
  <div>
    <span>查 询：</span>
    <el-input v-model="keywordId" style="width: 200px; " placeholder="请输入用户Id" />
    <el-button type="primary" style="width: 100px;;" @click="handleSearch">查询</el-button>
    <el-input v-model="keywordName" style="width: 200px; margin-left: 20px; " placeholder="请输入用户名" />
    <el-button type="primary" style="width: 100px;" @click="handleSearch">查询</el-button>
  </div>

  <el-divider />

  <!-- 用户列表展示 -->
  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="用户ID" width="200" />
    <el-table-column prop="name" label="用户名" width="180" />
    <el-table-column prop="status" label="用户状态" width="180" />
    <el-table-column prop="nick_name" label="用户昵称" width="180" />
    <el-table-column prop="sex" label="性别" width="180" />
    <el-table-column prop="phone" label="手机号" width="180" />
    <el-table-column prop="email" label="邮箱" width="180" />

    <el-table-column fixed="right" label="操作" min-width="150">
      <template #default>
        <el-button link type="primary" size="large" @click="handleClick">
          查看
        </el-button>
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
