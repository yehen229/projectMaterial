<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, shallowRef, watch } from 'vue'

import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router'

const router = useRouter()
const route = useRoute()

interface Props {
  timeRemaining: number //单位：毫秒
}

const props = withDefaults(defineProps<Props>(), {
  timeRemaining: 0,
})

watch(
  () => props.timeRemaining,
  async (val, prevval) => {
    /* ... */
    clearInterval(interval.value)
    timeRemained.value = val
    init()
  },
)

//event
const emit = defineEmits<{
  (e: 'onTime'): void
}>()

onMounted(async () => {
  clearInterval(interval.value)
})

const timeRemained = ref(0)
const timeText = ref('')
const interval = ref<NodeJS.Timer>()

const init = () => {
  // 获取当前时间

  timeText.value = formatDate(timeRemained.value)

  interval.value = setInterval(function () {
    // 格式化当前时间

    timeRemained.value -= 1000
    if (timeRemained.value <= 0) {
      timeRemained.value = 0
    }

    timeText.value = formatDate(timeRemained.value)

    if (timeRemained.value <= 0) {
      timeRemained.value = 0
      clearInterval(interval.value)
      emit('onTime')
      return
    }
  }, 1000)
}

/**
 * 得到剩余天数
 * @param val 单位：毫秒
 */
const getDay = (val: number) => {
  var v = Math.floor(val / (1000 * 60 * 60 * 24))

  if (v === 0) return ''
  return v + '天'
}

const getHours = (val: number) => {
  var v = Math.floor((val / (1000 * 60 * 60)) % 60) + ''
  return v.length == 1 ? '0' + v : v
}

const getMinutes = (val: number) => {
  var v = Math.floor((val / (1000 * 60)) % 60) + ''
  return v.length == 1 ? '0' + v : v
}

/**
 * 剩余秒数，如果为个位数，则前面加0
 * @param val 单位：毫秒
 */
const getSeconds = (val: number) => {
  var v = Math.floor((val / 1000) % 60) + ''
  return v.length == 1 ? '0' + v : v
}

//正则格式化日期
function formatDate(val: number) {
  if (val == 0) return '00:00:00'
  return (
    getDay(val) + getHours(val) + ':' + getMinutes(val) + ':' + getSeconds(val)
  )
}
</script>

<template>
  <div>{{ timeText }}</div>
</template>

<style scoped></style>
