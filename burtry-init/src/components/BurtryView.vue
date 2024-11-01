<script setup>
</script>

<template>
  <div class="container">
    <!-- 左边部分 -->
    <div class="left-content">
      <h3>啥都不是的破网站刚刚搭建成，还没想好打算做些什么，先贴个简历在这吧 : )</h3>
      <el-divider border-style="double" />
      <span>Github：<a href="https://github.com/Burtry" target="_blank">https://github.com/Burtry</a></span><br>
      <span>bilibili：<a href="https://space.bilibili.com/382410070"
          target="_blank">https://space.bilibili.com/382410070</a></span>
      <br>
      <span>twitter<a href="https://twitter.com/BurtryCc" target="_blank">https://twitter.com/BurtryCc</a></span><br>
      <span>bangumi<a href="https://bgm.tv/user/burtry" target="_blank">https://bgm.tv/user/burtry</a></span><br>
      <span style="font-size: small;">(Anime is my spiritual medicine)</span>

      <el-divider border-style="double" />
      <span>力扣：<a href="https://leetcode.cn/" target="_blank">https://leetcode.cn/</a></span><br>
      <p style="font-size: small;">开刷之前：打不倒我的，只会让我更强大(昂首挺胸)</p>
      <p style="font-size: small;">刷题之后：我就是个FW(哭)</p>
      <el-divider border-style="double" />
      <span>累了，不想写了，摆烂了。就这样吧，简单写点东西看起来不这么空就行了。</span>
      <br>
      <br>
      <h1 style="color: brown;">摆烂ing...</h1>

      <!-- icp备案 -->
      <div class="icp">
        <a href="https://beian.miit.gov.cn/" target="_blank">
          豫ICP备2024095519号</a>
      </div>
    </div>

    <!-- 右边展示图片 -->
    <div class="right-content">
      <el-image src="https://wsw-demo.oss-cn-beijing.aliyuncs.com/imagev1.2.png"
        :preview-src-list="['https://wsw-demo.oss-cn-beijing.aliyuncs.com/imagev1.2.png']" alt="简历" />
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
}

.left-content {
  width: 600px;

  h3 {
    margin-top: 20px;
  }

  img {
    width: 280px;
    height: auto;
  }
}

.right-content {
  width: 600px;
}

img {
  width: 380px;
  height: auto;
}

.icp {
  /* 位于网页最下方 */
  position: fixed;
  bottom: 0;
  left: 120px;
  margin: 10px;
  font-size: small;
  color: gray;
}
</style>
