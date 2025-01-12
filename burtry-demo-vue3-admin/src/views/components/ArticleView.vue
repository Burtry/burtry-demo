<script setup>
import { onMounted, ref, onUnmounted } from 'vue';

import { getChannelListAPI, getArticleListAPI } from "@/api/article";
import { ElMessage } from 'element-plus';

const articleId = ref('')
const authorId = ref('')
const status = ref(0)
const channelList = ref([])
const channelId = ref('')
const dateRange = ref([])

const pageData = ref({
  pageSize: 10,
  pageNum: 1,
  total: 0
})

const dataList = ref([
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
  getArticleList()
}


const radioChange = (value) => {
  status.value = value
  getArticleList()
}

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  getArticleList()

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  getArticleList()
}

const getArticleList = async () => {
  const data = {
    articleId: articleId.value,
    authorId: authorId.value,
    status: status.value,
    channelId: channelId.value,
    startDateTime: dateRange.value[0],
    endDateTime: dateRange.value[1],
    pageNum: pageData.value.pageNum,
    pageSize: pageData.value.pageSize
  }
  const res = await getArticleListAPI(data)
  if (res.code === 0) {
    ElMessage.error(res.msg)
    return
  }
  dataList.value = res.data.list
  pageData.value.total = res.data.total
}

const getChannelList = async () => {
  const res = await getChannelListAPI()
  channelList.value = res.data.filter(item => item.id >= "4")

  getArticleList()

}

const getChannelName = (id) => {
  const channel = channelList.value.find(item => item.id === id)
  return channel ? channel.name : "未知频道"
}
onMounted(() => {
  getChannelList()

})

const autoRefresh = ref(false);
let isRequesting = ref(false);  // 用来标识请求是否正在进行

const handleAutoRefresh = () => {
  if (autoRefresh.value) {
    const timer = setInterval(async () => {
      // 只有在没有请求进行时才发起新的请求
      if (!isRequesting.value) {
        isRequesting.value = true;  // 设置为请求中
        try {
          await getArticleList();  // 等待请求完成
        } finally {
          isRequesting.value = false;  // 请求完成，重置状态
        }
      }
    }, 5000);

    onUnmounted(() => {
      clearInterval(timer);
    });
  }
};

</script>

<template>
  <div>

    <el-radio-group v-model="status" @change="radioChange">
      <el-radio :value="0">全部</el-radio>
      <el-radio :value="4">已发布文章</el-radio>
      <el-radio :value="5">需人工审核文章</el-radio>
      <el-radio :value="6">已删除文章</el-radio>
    </el-radio-group>
    <!-- 定时刷新文章列表控制件 -->
    <el-switch class="autoRefresh" v-model="autoRefresh" active-text="定时刷新" @change="handleAutoRefresh" />
    <br><br>
    <span>查 询：</span>
    <el-input v-model="articleId" style="width: 200px;" placeholder="请输入文章ID" />
    <el-button type="primary" style="width: 100px; margin-right: 20px;" @click="handleSearch">查询</el-button>
    <el-input v-model="authorId" style="width: 200px;" placeholder="请输入作者ID" />
    <el-button type="primary" style="width: 100px;" @click="handleSearch">查询</el-button>

    <br><br>
    <span>频道列表：</span>
    <el-select v-model="channelId" placeholder="请选择频道" style="width: 200px; margin-right: 20px;">
      <el-option v-for="item in channelList" :key="item.id" :label="item.name" :value="item.id" />
    </el-select>

    <span>日期范围：</span>
    <el-date-picker v-model="dateRange" type="datetimerange" unlink-panels range-separator="--" start-placeholder="开始日期"
      format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" end-placeholder="结束日期" :shortcuts="shortcuts" />

    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>
  </div>

  <el-divider />
  <!-- 文章数据显示 -->

  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="文章ID" width="200" />
    <el-table-column prop="status" label="文章状态" width="200">
      <template #default="{ row }">
        <el-tag
          :type="row.status === 4 ? 'success' : row.status === 5 ? 'warning' : row.status === 2 ? 'warning' : 'info'">
          {{ row.status === 2 ? '待审核' : row.status === 3 ? "已审核" : row.status === 4 ? "已发布" : row.status === 5 ? "已锁定"
            :
            "已删除" }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="title" label="文章标题" width="200" show-overflow-tooltip />
    <el-table-column label="频道名称" width="200">
      <template v-slot="scope">
        <!-- 根据 channelId 获取对应的频道名称 -->
        <span>{{ getChannelName(scope.row.channelId) }}</span>
      </template>
    </el-table-column>
    <el-table-column prop="likes" label="点赞数" width="100" />
    <el-table-column prop="views" label="浏览数" width="100" />
    <el-table-column prop="collections" label="收藏数" width="100" />
    <el-table-column prop="comments" label="评论数" width="100" />
    <el-table-column prop="authorId" label="作者ID" width="200" />

    <el-table-column prop="publishTime" label="发布时间" width="150" />
    <el-table-column prop="createTime" label="创建时间" width="150" />
    <el-table-column prop="updateTime" label="更新时间" width="150" />
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
    layout="sizes, prev, pager, next, jumper,total, " :total="Number(pageData.total)"
    :page-sizes="[5, 10, 15, 20, 50, 100]" @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />



</template>

<style lang="scss" scoped>
.page {


  margin-top: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.autoRefresh {
  //右靠齐
  float: right;
}
</style>
