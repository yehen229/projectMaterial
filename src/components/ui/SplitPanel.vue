<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from 'vue'

import { useRouter } from 'vue-router/dist/vue-router'

import store from '@/store'

interface Props {
  minValue: number
  maxValue: number
}

const props = withDefaults(defineProps<Props>(), {
  minValue: 10,
  maxValue: 90,
})

const panelLengthPercent = ref(50)

//event
const emit = defineEmits<{
  (e: 'onPanelLengthPercentChanged', panelLengthPercent: number): void
}>()

const triggerLeftOffset = ref(0)
const splitPanel = ref<HTMLElement | null>(null)

const panelLengthValue = computed(() => {
  const r = `calc(${panelLengthPercent.value}% - 5px)`

  return r
})

// 按下滑动器
const handleMouseDown = (e) => {
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)

  triggerLeftOffset.value = e.pageX - e.srcElement.getBoundingClientRect().left
  console.log(triggerLeftOffset.value)
}

// 按下滑动器后移动鼠标
const handleMouseMove = (e) => {
  const clientRect = splitPanel.value?.getBoundingClientRect()
  if (!clientRect) return

  let temp = 0

  const offset = e.pageX - clientRect.left - triggerLeftOffset.value + 5
  temp = (offset / clientRect.width) * 100

  if (temp < props.minValue) {
    temp = props.minValue
  }
  if (temp > props.maxValue) {
    temp = props.maxValue
  }

  panelLengthPercent.value = temp

  emit('onPanelLengthPercentChanged', panelLengthPercent.value)
}

// 松开滑动器
const handleMouseUp = () => {
  document.removeEventListener('mousemove', handleMouseMove)
}
</script>

<template>
  <div ref="splitPanel" class="split-panel">
    <div class="panel panel-one" :style="'width:' + panelLengthValue">
      <slot name="one"></slot>
    </div>

    <div class="panel-trigger" @mousedown="handleMouseDown">
      <div class="circle" style="top: calc(50% - 40px);"></div>
      <div class="circle" style="top: calc(50% - 20px);"></div>
      <div class="circle" style="top: 50%;"></div>
      <div class="circle" style="top: calc(50% + 20px);"></div>
      <div class="circle" style="top: calc(50% + 40px);"></div>
    </div>

    <div class="panel panel-two">
      <slot name="two"></slot>
    </div>
  </div>
</template>

<style scoped>
.split-panel {
  background: white;
 
  height: 100%;
  min-height: 400px;
  display: flex;
}

.panel {
  height: 100%;
 
}
.panel-trigger {
  height: 100%;
  cursor: col-resize;
}

.panel-one {
  height: 100%;
  overflow: auto;
}
.panel-trigger {
  user-select: none;
  background: #f0f2f5;
  width: 10px;
}
.panel-two {
  flex: 1;
  min-height: 400px;
  overflow: auto;
}
.circle {
  position: relative;
  margin: auto;
  height: 4px;
  width: 4px;
  left: calc(50% - 5px);
  border-radius: 2px;
  background: #cdd0d6;
}
</style>
