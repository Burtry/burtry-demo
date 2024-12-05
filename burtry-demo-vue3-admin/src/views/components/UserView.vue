<script setup>
import { onMounted, ref } from 'vue';
import { getUserListAPI, updateUserStatusAPI } from "@/api/user";
import { ElMessage } from 'element-plus';
const keyword = ref("")

const pageData = ref({
  pageSize: 10,
  pageNum: 1,
  sortBy: "",
  keyword: "",
  total: 0,
})

const dataList = ref([])

const getUserList = async () => {
  const res = await getUserListAPI(pageData.value);
  if (res.code === 0) {
    ElMessage.error(res.msg)
  }
  dataList.value = res.data.list
  pageData.value.total = res.data.total
}
const userId = ref("")
const statusDialog = ref(false)
const updateUserStatusBefore = (id) => {
  userId.value = id
  statusDialog.value = true
}


const updateUserStatus = async () => {
  const res = await updateUserStatusAPI(userId.value)
  if (res.code === 0) {
    ElMessage.error(res.msg)
  } else {
    ElMessage.success(res.msg)
    getUserList()
    statusDialog.value = false
  }

}

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  getUserList()
}

const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  getUserList()

}

const handleSearch = () => {
  pageData.value.keyword = keyword.value
  getUserList()
}

const handleView = (id) => {
  console.log(id)
}

onMounted(() => {
  getUserList()
})

</script>


<template>
  <div>
    <span>查 询：</span>
    <el-input v-model="keyword" style="width: 200px; " placeholder="请输入用户Id或用户名" />
    <el-button type="primary" style="width: 100px;;" @click="handleSearch">查询</el-button>

  </div>

  <el-divider />

  <!-- 用户列表展示 -->
  <el-table :data="dataList" stripe style="width: 100%">
    <el-table-column prop="id" label="用户ID" width="200" />
    <el-table-column prop="name" label="用户名" width="180" />
    <el-table-column prop="status" label="用户状态" width="200">
      <template #default="{ row }">
        <el-tag :type="row.status === 0 ? 'success' : 'danger'">
          {{ row.status === 0 ? '正常' : '锁定' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="nickName" label="用户昵称" width="180" />
    <el-table-column prop="phone" label="手机号" width="180" />
    <el-table-column prop="email" label="邮箱" width="180" />
    <el-table-column prop="createTime" label="创建时间" width="180" />
    <el-table-column prop="address" label="地址" width="280" />

    <el-table-column fixed="right" label="操作" min-width="200">
      <template #default="{ row }">
        <el-button link type="primary" size="large" @click="handleView(row.id)">
          查看
        </el-button>
        <el-button link type="primary" size="large" @click="updateUserStatusBefore(row.id)">{{ row.status === 0 ? '锁定' :
          '解锁'
          }}</el-button>
        <el-button link type="primary" size="large">重置密码</el-button>
      </template>
    </el-table-column>

  </el-table>


  <!-- 分页条 -->
  <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
    layout="sizes, prev, pager, next, jumper,total, " :total="Number(pageData.total)"
    :page-sizes="[5, 10, 15, 20, 50, 100]" @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />


  <!-- 用户状态修改 -->
  <el-dialog v-model="statusDialog" title="提示" width="500" align-center>
    <span>确认要修改用户状态吗</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="statusDialog = false">取消</el-button>
        <el-button type="primary" @click="updateUserStatus">
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
