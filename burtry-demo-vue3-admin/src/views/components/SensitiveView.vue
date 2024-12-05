<script setup>
import { ref, onMounted } from 'vue';
import { getSensitiveListAPI, addSensitiveAPI, deleteSensitiveAPI, updateSensitiveAPI } from "@/api/sensitive";
import { ElMessage } from 'element-plus';
const keyword = ref("")

const pageData = ref({
  pageSize: 10,
  pageNum: 1,
  sortBy: "",
  keyword: "",
})

const sensitiveList = ref([])

const getSensitiveList = async () => {
  const res = await getSensitiveListAPI(pageData.value);
  if (res.code === 0) {
    ElMessage.error("获取敏感词列表失败");
  }
  sensitiveList.value = res.data.list;
  pageData.value.total = res.data.total;
}

onMounted(() => {
  getSensitiveList();
})

const OnCurrentChange = (pageNum) => {
  pageData.value.pageNum = pageNum;
  console.log(pageNum);

}
const OnSizeChange = (pageSize) => {
  pageData.value.pageSize = pageSize;
  console.log(pageSize);

}

const handleSearch = () => {
  pageData.value.keyword = keyword.value
  getSensitiveList()

}

const addSensitiveDialog = ref(false)
const sensitiveData = ref({
  id: "",
  sensitiveName: "",
})
const handleAddSensitive = () => {
  if (sensitiveData.value.sensitiveName === "") {
    ElMessage.error("敏感词不能为空");
    return
  }
  addSensitiveAPI(sensitiveData.value).then(res => {
    if (res.code === 0) {
      ElMessage.error(res.msg);
      return
    }
    if (res.msg === "服务异常") {
      ElMessage.error("服务异常，请稍后再试");
      return
    }
    ElMessage.success("添加敏感词成功");
    addSensitiveDialog.value = false;
    sensitiveData.value.sensitiveName = "";
    getSensitiveList();
  })
}

const addChannelHandleClose = () => {
  addSensitiveDialog.value = false;
  sensitiveData.value.sensitiveName = "";
}

const deleteSensitiveDialog = ref(false)
const sensitiveId = ref("")

const handleDeleteBefore = (id) => {
  sensitiveId.value = id
  console.log(sensitiveId.value);

  deleteSensitiveDialog.value = true
}

const handleDeleteChannel = () => {
  deleteSensitiveAPI(sensitiveId.value).then(res => {
    if (res.code === 0) {
      ElMessage.error(res.msg);
      return
    }
    if (res.msg === "服务异常") {
      ElMessage.error("服务异常，请稍后再试");
      return
    }
    ElMessage.success("删除敏感词成功");
    deleteSensitiveDialog.value = false;
    getSensitiveList();
  })
}

const updateSensitiveDialog = ref(false)
const handleEditBefore = (row) => {
  sensitiveData.value.sensitiveName = row.sensitiveName
  sensitiveData.value.id = row.id
  updateSensitiveDialog.value = true
}

const handleUpdateSensitive = () => {
  if (sensitiveData.value.sensitiveName === "") {
    ElMessage.error("敏感词不能为空");
    return
  }
  updateSensitiveAPI(sensitiveData.value).then(res => {
    if (res.code === 0) {
      ElMessage.error(res.msg);
      return
    }
    ElMessage.success("修改敏感词成功");
    updateSensitiveHandleClose()
    getSensitiveList();

  })


}

const updateSensitiveHandleClose = () => {
  updateSensitiveDialog.value = false;
  sensitiveData.value.sensitiveName = "";
  sensitiveData.value.id = "";
}

</script>


<template>
  <div>
    <span>关键字：</span>
    <el-input v-model="keyword" style="width: 200px; margin-right: 20px;" placeholder="请输入敏感词"
      @keyup.enter="handleSearch" />
    <el-button type="primary" style="width: 100px; margin-left: 20px;" @click="handleSearch">查询</el-button>

    <!-- 添加按钮靠右 -->
    <el-button type="primary" style=" float: right; width: 100px; " @click="addSensitiveDialog = true">添加敏感词</el-button>
  </div>

  <el-divider />

  <!-- 敏感词列表展示 -->
  <el-table :data="sensitiveList" stripe style="width: 100%">
    <el-table-column prop="id" label="敏感词ID" width="300" />
    <el-table-column prop="sensitiveName" label="敏感词" width="300" />
    <el-table-column prop="createTime" label="创建时间" width="300" />
    <el-table-column prop="updateTime" label="更新时间" width="300" />
    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        <el-button link type="primary" size="large" @click="handleEditBefore(scope.row)">修改</el-button>
        <el-button link type="primary" size="large" @click="handleDeleteBefore(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>


  <!-- 分页条 -->
  <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
    layout="sizes, prev, pager, next, jumper,total, " :total="Number(pageData.total)"
    :page-sizes="[5, 10, 15, 20, 50, 100]" @size-change="OnSizeChange" @current-change="OnCurrentChange" class="page" />



  <!-- 添加Dialog -->
  <el-dialog v-model="addSensitiveDialog" title="添加敏感词" width="500" :before-close="addChannelHandleClose">
    <el-form :model="sensitiveData" label-width="auto" style="max-width: 600px">
      <el-form-item label="敏感词">
        <el-input v-model="sensitiveData.sensitiveName" placeholder="请输入敏感词" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addChannelHandleClose">取消</el-button>
        <el-button type="primary" @click="handleAddSensitive">
          添加
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 删除Dialog -->
  <el-dialog v-model="deleteSensitiveDialog" title="删除敏感词" width="500" align-center>
    <span>确认要删除敏感词吗</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="deleteSensitiveDialog = false">取消</el-button>
        <el-button type="primary" @click="handleDeleteChannel">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 修改Dialog -->
  <el-dialog v-model="updateSensitiveDialog" title="修改敏感词信息" width="500" :before-close="updateSensitiveHandleClose">
    <el-form :model="sensitiveData" label-width="auto" style="max-width: 600px">
      <el-form-item label="敏感词">
        <el-input v-model="sensitiveData.sensitiveName" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="updateSensitiveHandleClose">取消</el-button>
        <el-button type="primary" @click="handleUpdateSensitive">
          修改
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
