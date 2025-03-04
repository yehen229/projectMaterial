<template>
  <div class="relative h-100vh overflow-hidden">
    <video
        id="qrcode-scanner"
        class="h-full absolute left-1/2 -translate-x-1/2"
        style="height:100%; width:100%;"
    ></video>
  </div>
</template>

<script lang="ts" setup>
import { BrowserMultiFormatReader } from '@zxing/library'
import { ref, onMounted } from 'vue'
import router from "@/router";

const loading = ref(false)
const render = new BrowserMultiFormatReader()
const deviceId = ref('')
const scanner = ref(null) // 添加一个引用来保存扫描器实例

onMounted(() => {
  openScan()
})

function openScan() {
  loading.value = true
  render.listVideoInputDevices().then((videoInputDevices) => {
    if (videoInputDevices.length) {
      // 0 前置摄像头  1 后置摄像头
      // 默认获取第一个摄像头设备id
      deviceId.value = videoInputDevices[0]?.deviceId
      if (videoInputDevices.length > 1) {
        deviceId.value = videoInputDevices[1]?.deviceId
      }
      decodeFromInputVideo()
    }
  })
}

function decodeFromInputVideo() {
  scanner.value = render.decodeFromVideoDevice(deviceId.value, 'qrcode-scanner', (result) => {
    if (result) {
      console.log('result', result)
      let text = result.getText()
      localStorage.setItem('qrcode', JSON.stringify(text))
      router.push({ path: "scanAfter" });
      scanner.value && scanner.value.abort() // 停止扫描
    }
  })
}
</script>