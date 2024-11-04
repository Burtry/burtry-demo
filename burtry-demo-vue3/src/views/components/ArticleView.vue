<script setup>
import { ref } from 'vue';

const articleID = ref('')
const authorID = ref('')
const radio = ref(0)
const channel = ref('')
const dateRange = ref('')

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
  },
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
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
  },
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
])
const shortcuts = [
  {
    text: '一天之内',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24)
      return [start, end]
    },
  },
  {
    text: '一周之内',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: '一个月之内',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: '三个月之内',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  },
]


const handleSearch = () => {
  console.log(dateRange.value);
}


const radioChange = (value) => {
  radio.value = value
  console.log(value);
}

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  console.log(pageNum);

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  console.log(pageSize);

}
</script>

<template>
  <div>

    <el-radio-group v-model="radio" @change="radioChange">
      <el-radio :value="0">全部</el-radio>
      <el-radio :value="1">待审核文章</el-radio>
      <el-radio :value="2">已审核文章</el-radio>
    </el-radio-group>
    <br><br>
    <span>查 询：</span>
    <el-input v-model="articleID" style="width: 200px;" placeholder="请输入文章ID" />
    <el-button type="primary" style="width: 100px; margin-right: 20px;" @click="handleSearch">查询</el-button>
    <el-input v-model="authorID" style="width: 200px;" placeholder="请输入作者ID" />
    <el-button type="primary" style="width: 100px;" @click="handleSearch">查询</el-button>

    <br><br>
    <span>频道列表：</span>
    <el-select v-model="channel" placeholder="请选择频道" style="width: 200px; margin-right: 20px;">
      <el-option label="频道1" value="1" />
      <el-option label="频道2" value="2" />
      <el-option label="频道3" value="3" />
    </el-select>

    <span>日期范围：</span>
    <el-date-picker v-model="dateRange" type="daterange" unlink-panels range-separator="--" start-placeholder="开始日期"
      end-placeholder="结束日期" :shortcuts="shortcuts" />

    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>
  </div>

  <el-divider />
  <!-- 文章数据显示 -->

  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="articleID" label="文章ID" width="200" />
    <el-table-column prop="likes" label="点赞数" width="100" />
    <el-table-column prop="views" label="浏览数" width="100" />
    <el-table-column prop="collections" label="收藏数" width="100" />
    <el-table-column prop="comments" label="评论数" width="100" />
    <el-table-column prop="authorID" label="作者ID" width="200" />
    <el-table-column prop="channelID" label="频道ID" width="200" />
    <el-table-column prop="create_time" label="创建时间" width="150" />
    <el-table-column prop="update_time" label="更新时间" width="150" />
    <el-table-column fixed="right" label="操作" min-width="120">
      <template #default>
        <el-button link type="primary" size="large" @click="handleClick">
          查看
        </el-button>
        <el-button link type="primary" size="large">修改</el-button>
      </template>
    </el-table-column>
  </el-table>


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
