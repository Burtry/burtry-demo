<script setup>
import { Search } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { searchAPI } from "@/api/search";
const route = useRoute();
const keyWords = ref(route.query.keyWords);
import SearchArticleView from './article/SearchArticleView.vue';
const articles = ref([]);

const pageData = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0,
})

const OnSizeChange = (val) => {
  pageData.value.pageSize = val;
  handleSearch();
}

const OnCurrentChange = (val) => {
  pageData.value.pageNum = val;
  handleSearch();
}

const handleSearch = async () => {
  const data = {
    keyWords: keyWords.value,
    pageNum: pageData.value.pageNum,
    pageSize: pageData.value.pageSize,
  }
  const res = await searchAPI(data)
  articles.value = res.data;
  pageData.value.total = res.total;
  pageData.value.pageNum = res.pageNum;
  pageData.value.pageSize = res.pageSize;
}

onMounted(() => {
  handleSearch();
})

</script>

<template>
  <div class="bg">
    <div class="search-container">
      <el-input v-model="keyWords" class="search" placeholder="输入关键字搜索" :prefix-icon="Search"
        @keyup.enter="handleSearch" />
      <el-button type="primary" class="submit" @click="handleSearch">搜索</el-button>
    </div>

    <el-divider />
    <div class="article-list" style="overflow: auto">
      <div v-for="article in articles" :key="article.id" class="article-item">
        <SearchArticleView :article="article" />
      </div>
    </div>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageData.pageNum" v-model:page-size="pageData.pageSize" background
      layout="sizes, prev, pager, next,jump-to,total " :total="Number(pageData.total)"
      :page-sizes="[5, 10, 15, 20, 50, 100]" @size-change="OnSizeChange" @current-change="OnCurrentChange"
      class="page" />

  </div>
</template>

<style lang="scss" scoped>
.bg {
  min-height: 100vh;
  height: auto;
  background-image: url('@/assets/bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding: 20px;
}

.active {
  color: #409eff;
  border-bottom: 2px solid #409eff;
}

.search-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search,
.submit {
  height: 50px;
  border-radius: 25px;
  border: 1px solid #fff;
  color: #fff;
  font-size: 20px;
}

.search {
  width: 540px;
  border-radius: 25px 0 0 25px;
  padding-left: 20px;
}

.submit {
  width: 100px;
}

.article-list {
  width: 100%;
  max-width: 1400px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  background-color: rgba(255, 255, 255, 0.6);
  border-radius: 10px;
  padding: 20px;

}

.article-item {
  width: 48%;
  height: 150px;
  margin-bottom: 20px;
  background-color: rgba(255, 255, 255, 0.4);
  border-radius: 10px;
  margin-bottom: 50px;
}

.page {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}
</style>
