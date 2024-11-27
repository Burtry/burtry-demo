<script setup>
import { onMounted, ref } from 'vue';
import { addChannelAPI, getChannelListAPI, updateChannelStatusAPI, deleteChannelAPI, updateChannelAPI } from "@/api/channel";
import { ElMessage } from 'element-plus';
const keyword = ref("")

const pageData = ref({
  pageNum: 1, //默认第一页
  pageSize: 10,    //每页五条数据
  total: 0,
  sortBy: "",
  keyword: "",
  status: 2 //全部频道

})
const status = ref(2)
const dataList = ref([])
const channelData = ref({
  name: "",
  description: "",
})
const addChannelDialog = ref(false)
const deleteChannelDialog = ref(false)
const enableChannelDialog = ref(false)
const channelId = ref("")

const getChannel = () => {
  getChannelListAPI(pageData.value).then(res => {

    if (res.code === 0) {
      ElMessage.error(res.msg)
    }
    dataList.value = res.data.list.map(item => ({
      ...item,
      status: item.status === 0 ? "启用" : "禁用"
    }))
    pageData.value.total = res.data.total
  })
}

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  getChannel()

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  getChannel()
}

const handleSearch = () => {
  pageData.value.keyword = keyword.value
  const res = getChannel()
  if (res.code === 0) {
    ElMessage.error(res.msg)
  }
}

const addChannelHandleClose = () => {
  addChannelDialog.value = false
  channelData.value = {
    name: "",
    description: "",
  }
}

const handleAddChannel = () => {
  if (channelData.value.name === "" || channelData.value.description === "") {
    ElMessage.error("请输入频道名称和描述")
    return
  }
  addChannelAPI(channelData.value).then(res => {
    if (res.code === 1) {
      ElMessage.success(res.msg)
      getChannel()
      addChannelDialog.value = false
      channelData.value = {
        name: "",
        description: "",
      }
    } else {
      ElMessage.error(res.msg)
    }
  })

}

const handleDeleteChannelBefore = (id) => {
  channelId.value = id
  deleteChannelDialog.value = true
}

const handleDeleteChannel = () => {
  deleteChannelAPI(channelId.value).then(res => {
    if (res.code === 1) {
      ElMessage.success(res.msg)
      getChannel()
      deleteChannelDialog.value = false
      channelId.value = ""

    } else {
      ElMessage.error(res.msg)
    }
  })

}
const handleUpdateChannelStatusBefore = (id) => {
  channelId.value = id
  enableChannelDialog.value = true
}

const handleUpdateChannelStatus = () => {
  updateChannelStatusAPI(channelId.value).then(res => {
    if (res.code === 1) {
      ElMessage.success(res.msg)
      getChannel()
      enableChannelDialog.value = false
      channelId.value = ""
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleEditBefore = (row) => {
  channelData.value = {
    name: row.name,
    description: row.description,
    id: row.id
  }
  updateChannelDialog.value = true
}

const updateChannelHandleClose = () => {
  updateChannelDialog.value = false
  channelData.value = {
    name: "",
    description: "",
  }
}

const handleUpdateChannel = () => {
  if (channelData.value.name === "" || channelData.value.description === "") {
    ElMessage.error("请输入频道名称和描述")
    return
  }
  updateChannelAPI(channelData.value).then(res => {
    if (res.code === 1) {
      ElMessage.success(res.msg)
      getChannel()
      updateChannelDialog.value = false
      channelData.value = {
        name: "",
        description: "",
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const radioChange = (val) => {
  pageData.value.status = val
  getChannel()
}

onMounted(() => {
  getChannel()
})

const updateChannelDialog = ref(false)


</script>


<template>
  <div>
    <span>关键字：</span>
    <el-input v-model="keyword" style="width: 200px; margin-right: 20px;" placeholder="请输入频道名称"
      @keyup.enter="handleSearch" />
    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>
    <!-- 添加按钮 -->
    <el-button type="primary" style=" float: right; width: 100px; " @click="addChannelDialog = true">添加频道</el-button>
  </div>
  <el-radio-group v-model="status" @change="radioChange">
    <el-radio :value="2">全部</el-radio>
    <el-radio :value="0">已启用频道</el-radio>
    <el-radio :value="1">已禁用频道</el-radio>
  </el-radio-group>

  <el-divider />

  <!-- 列表展示 -->
  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="频道ID" width="200" />
    <el-table-column prop="name" label="频道名称" width="200" />
    <el-table-column prop="description" label="频道描述" width="200" show-overflow-tooltip />
    <el-table-column prop="status" label="频道状态" width="200">
      <template #default="{ row }">
        <el-tag :type="row.status === '禁用' ? 'danger' : 'success'">
          {{ row.status === '禁用' ? '禁用' : '启用' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" width="250" />
    <el-table-column fixed="right" label="操作" width="180">
      <template #default="scope">
        <!-- scope.row 是当前行的数据 -->
        <el-button :link="true" :type="scope.row.status === '禁用' ? 'primary' : 'danger'" size="large"
          @click="handleUpdateChannelStatusBefore(scope.row.id)">
          {{ scope.row.status === '禁用' ? '启用' : '禁用' }}
        </el-button>
        <el-button link type="primary" size="large" @click="handleEditBefore(scope.row)">
          修改
        </el-button>
        <el-button link type="primary" size="large" @click="handleDeleteChannelBefore(scope.row.id)">
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>


  <!-- 分页条 -->
  <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
    layout="sizes, prev, pager, next, jumper,total, " :total="Number(pageData.total)" :page-sizes="[5, 10, 15, 20]"
    @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />


  <!-- 添加Dialog -->
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

  <!-- 修改Dialog -->
  <el-dialog v-model="updateChannelDialog" title="修改频道信息" width="500" :before-close="updateChannelHandleClose">
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
        <el-button @click="updateChannelHandleClose">取消</el-button>
        <el-button type="primary" @click="handleUpdateChannel">
          添加
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 删除Dialog -->

  <el-dialog v-model="deleteChannelDialog" title="删除频道" width="500" align-center>
    <span>确认要删除频道吗</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="deleteChannelDialog = false">取消</el-button>
        <el-button type="primary" @click="handleDeleteChannel">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 启用/禁用Dialog -->

  <el-dialog v-model="enableChannelDialog" title="提示" width="500" align-center>
    <span>确认要修改频道状态吗</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="enableChannelDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdateChannelStatus">
          确认
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
