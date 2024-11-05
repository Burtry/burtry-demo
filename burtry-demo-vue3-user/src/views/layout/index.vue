<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import LayoutHead from './components/LayoutHead.vue'
import LayoutFooter from './components/LayoutFooter.vue'
</script>

<template>
  <LayoutHead />
  <RouterView />
  <LayoutFooter />
</template>
