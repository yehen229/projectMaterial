<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import screenfull from 'screenfull'

import { FullScreen } from '@element-plus/icons-vue'

interface Props {
  screenFullElement: HTMLElement
}

const props = withDefaults(defineProps<Props>(), {
  screenFullElement: undefined,
})

//event
const emit = defineEmits<{
  (e: 'onOpenScreenFull'): void
  (e: 'onCloseScreenFull'): void
}>()

// 是否全屏
const isFullscreen = ref(false)

// 监听变化
const change = () => {
  isFullscreen.value = screenfull.isFullscreen

  if (screenfull.isFullscreen) emit('onOpenScreenFull')
  else emit('onCloseScreenFull')
}

// 切换事件
const onToggle = () => {
  if (props.screenFullElement) screenfull.toggle(props.screenFullElement)
  else screenfull.toggle()
}

// 设置侦听器
onMounted(() => {
  screenfull.on('change', change)
})

// 删除侦听器
onUnmounted(() => {
  screenfull.off('change', change)
})
</script>
<template>
  <span style="margin-left: 10px; margin-right: 10px;">
    <el-button @click="onToggle" :icon="FullScreen"></el-button>
  </span>
</template>
