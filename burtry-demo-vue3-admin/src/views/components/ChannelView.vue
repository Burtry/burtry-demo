<script setup>
import { onMounted, ref } from 'vue';
import { addChannelAPI, getChannelListAPI } from "@/api/channel";
import { ElMessage } from 'element-plus';
const keyword = ref("")

const pageData = ref({
  pageNum: 1, //默认第一页
  pageSize: 10,    //每页五条数据
  total: 0,
  sortBy: "",
})

const dataList = ref([])
const getChannel = () => {
  getChannelListAPI(pageData.value).then(res => {
    dataList.value = res.data.list.map(item => ({
      ...item,
      status: item.status === 0 ? "启用" : "禁用",
      isDefault: item.isDefault === 0 ? "是" : "否",
    }))
    pageData.value.total = res.data.total
    console.log(res.data);

  })
}
onMounted(() => {
  getChannel()
})

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  getChannel()

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  getChannel()
}

const handleSearch = () => {
  console.log(keyword.value);

}

const channelData = ref({})
const addChannelDialog = ref(false)

const addChannelHandleClose = () => {
  addChannelDialog.value = false
  channelData.value = {}
}

const handleAddChannel = () => {
  addChannelAPI(channelData.value).then(res => {
    if (res.code === 1) {
      ElMessage.success(res.msg)
      addChannelDialog.value = false
      channelData.value = {}
    } else {
      ElMessage.error(res.msg)
    }
  })

}

</script>


<template>
  <div>
    <span>关键字：</span>
    <el-input v-model="keyword" style="width: 200px; margin-right: 20px;" placeholder="请输入频道名称" />
    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>

    <!-- 添加按钮 -->
    <el-button type="primary" style=" float: right; width: 100px; " @click="addChannelDialog = true">添加频道</el-button>
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
    layout="sizes, prev, pager, next, jumper,total, " :total="pageData.total" :page-sizes="[5, 10, 15, 20]"
    @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />


  <el-dialog v-model="addChannelDialog" title="添加频道" width="500" :before-close="addChannelHandleClose">
    <el-form :model="channelData" label-width="auto" style="max-width: 600px">
      <el-form-item label="频道名称">
        <el-input v-model="channelData.name" />
      </el-form-item>
      <el-form-item label="频道描述">
        <el-input v-model="channelData.description" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addChannelHandleClose">取消</el-button>
        <el-button type="primary" @click="handleAddChannel">
          添加
        </el-button>
      </div>
    </template>
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
