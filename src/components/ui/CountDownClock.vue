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
    timeRemained.value = val
    init()
  },
)

//event
const emit = defineEmits<{
  (e: 'onTime'): void
}>()

onMounted(async () => {})

const timeRemained = ref(0)

interface IConfig {
  // 时钟模块的节点
  node: Element
  // 初始前牌文字
  frontText: string
  // 初始后牌文字
  backText: string
  // 翻转动画时间（毫秒，与翻转动画CSS 设置的animation-duration时间要一致）
  duration: number
}

interface INodeClass {
  flip: string
  front: string
  back: string
}

class Flipper {
  // 定位前后两个牌的DOM节点
  frontNode: HTMLElement
  backNode: HTMLElement
  // 是否处于翻牌动画过程中（防止动画未完成就进入下一次翻牌）
  isFlipping = false

  config: IConfig

  // 节点的原本class，与html对应，方便后面添加/删除新的class
  nodeClass: INodeClass

  constructor(node: Element, frontText: string, backText: string) {
    this.config = {
      // 时钟模块的节点
      node: node,
      // 初始前牌文字
      frontText: frontText,
      // 初始后牌文字
      backText: backText,
      // 翻转动画时间（毫秒，与翻转动画CSS 设置的animation-duration时间要一致）
      duration: 600,
    }

    this.nodeClass = {
      flip: 'flip',
      front: 'digital front',
      back: 'digital back',
    }

    this.frontNode = node.querySelector('.front')
    this.backNode = node.querySelector('.back')

    this._init()
  }
  // 初始化
  _init() {
    // 设置初始牌面字符
    this._setFront(this.config.frontText)
    this._setBack(this.config.backText)
  }

  // 设置前牌文字
  _setFront(className: string) {
    this.frontNode.setAttribute('class', this.nodeClass.front + ' ' + className)
  }
  // 设置后牌文字
  _setBack(className: string) {
    this.backNode.setAttribute('class', this.nodeClass.back + ' ' + className)
  }
  _flip(type, front, back) {
    // 如果处于翻转中，则不执行
    if (this.isFlipping) {
      return false
    }
    // 设置翻转状态为true
    this.isFlipping = true
    // 设置前牌文字
    this._setFront(front)
    // 设置后牌文字
    this._setBack(back)
    // 根据传递过来的type设置翻转方向
    let flipClass = this.nodeClass.flip
    if (type === 'down') {
      flipClass += ' down'
    } else {
      flipClass += ' up'
    }
    // 添加翻转方向和执行动画的class，执行翻转动画
    this.config.node.setAttribute('class', flipClass + ' go')
    // 根据设置的动画时间，在动画结束后，还原class并更新前牌文字
    setTimeout(() => {
      // 还原class
      this.config.node.setAttribute('class', flipClass)
      // 设置翻转状态为false
      this.isFlipping = false
      // 将前牌文字设置为当前新的数字，后牌因为被前牌挡住了，就不用设置了。
      this._setFront(back)
    }, this.config.duration)
  }
  // 下翻牌
  flipDown(front, back) {
    this._flip('down', front, back)
  }
  // 上翻牌
  flipUp(front, back) {
    this._flip('up', front, back)
  }
}

const init = () => {
  // 定位时钟模块
  let clock = document.getElementById('clock')

  // 定位6个翻板
  let flips = clock?.querySelectorAll('.flip')

  console.log(timeRemained.value)

  // 获取当前时间
  let nowTimeStr = formatDate(timeRemained.value)
  timeRemained.value -= 1000
  // 格式化下一秒的时间
  let nextTimeStr = formatDate(timeRemained.value)

  // 定义牌板数组，用来存储6个Flipper翻板对象
  let flipObjs: Flipper[] = []

  if (flips) {
    for (let i = 0; i < flips.length; i++) {
      // 创建6个Flipper实例，初始化并存入flipObjs
      flipObjs.push(
        new Flipper(
          // 每个Flipper实例按数组顺序与翻板DOM的顺序一一对应
          flips[i],
          // 按数组顺序取时间字符串对应位置的数字
          'number' + nowTimeStr[i],
          'number' + nextTimeStr[i],
        ),
      )
    }
  }

  const interval = 1000 // 设定倒计时规则为每秒倒计时
  let totalCount = timeRemained.value // 设定总倒计时长

  let count = 0 // 记录递归已执行次数，以倒计时时间间隔 interval=1s 为例，那么count就相当于如果没有时间偏差情况下的理想执行时间

  const startTime = new Date().getTime() // 记录程序开始运行的时间

  /*
  var interval = setInterval(function () {
    // 格式化当前时间
    let nowTimeStr = formatDate(timeRemained.value)

    timeRemained.value -= 1000
    if (timeRemained.value <= 0) {
      timeRemained.value = 0
    }
    // 格式化下一秒时间
    let nextTimeStr = formatDate(timeRemained.value)

    // 将当前时间和下一秒时间逐位对比
    for (let i = 0; i < flipObjs.length; i++) {
      // 如果前后数字没有变化，则直接跳过，不翻牌
      if (nowTimeStr[i] === nextTimeStr[i]) {
        continue
      }

      // 传递前后牌的数字，进行向下翻牌动画
      flipObjs[i].flipUp('number' + nowTimeStr[i], 'number' + nextTimeStr[i])
    }

    if (timeRemained.value <= 0) {
      timeRemained.value = 0
      clearInterval(interval)
      emit('onTime')
      return
    }
  }, 1000)*/
  console.log(totalCount)

  const flipCount = () => {
    // 格式化当前时间
    let nowTimeStr = formatDate(timeRemained.value)

    timeRemained.value -= 1000
    if (timeRemained.value <= 0) {
      timeRemained.value = 0
    }
    // 格式化下一秒时间
    let nextTimeStr = formatDate(timeRemained.value)

    // 将当前时间和下一秒时间逐位对比
    for (let i = 0; i < flipObjs.length; i++) {
      // 如果前后数字没有变化，则直接跳过，不翻牌
      if (nowTimeStr[i] === nextTimeStr[i]) {
        continue
      }

      // 传递前后牌的数字，进行向下翻牌动画
      flipObjs[i].flipUp('number' + nowTimeStr[i], 'number' + nextTimeStr[i])
    }
  }

  // 倒计时回调函数
  function countDownFn() {
    count++ // count自增，记录理想执行时间
    // 获取当前时间减去刚开始记录的startTime再减去理想执行时间得到时间偏差：等待执行栈为空的时间
    const offset = new Date().getTime() - startTime - count * interval
    let nextTime = interval - offset // 根据时间偏差，计算下次倒计时设定的回调时间，从而达到纠正的目的
    if (nextTime < 0) {
      nextTime = 0
    }

    totalCount -= interval

    if (totalCount < 0) {
      timeRemained.value = 0
      clearTimeout(timeoutID)
      emit('onTime')
    } else {
      flipCount()
      timeoutID = setTimeout(countDownFn, nextTime)
    }
  }

  let timeoutID = setTimeout(countDownFn, interval)
}

/**
 * 得到剩余天数
 * @param val 单位：毫秒
 */
const getDay = (val: number) => {
  var v = Math.floor(val / (1000 * 60 * 60 * 24)) + ''

  if (v.length == 1) return '000' + v
  if (v.length == 2) return '00' + v
  if (v.length == 3) return '0' + v
  if (v.length == 4) return '' + v
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
  if (val == 0) return '0000000000'
  return getDay(val) + getHours(val) + getMinutes(val) + getSeconds(val)
}
</script>

<template>
  <div class="clock" id="clock">
    <!--天数占用四位-->
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <em>-</em>

    <em>&nbsp</em>

    <!--小时占用2位-->
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <em>:</em>
    <!--分钟占用2位-->
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <em>:</em>
    <!--秒数占用2位-->
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
    <div class="flip down">
      <div class="digital front number0"></div>
      <div class="digital back number1"></div>
    </div>
  </div>
</template>

<style scoped>
.flip {
  display: inline-block;
  position: relative;
  width: 60px;
  height: 100px;
  line-height: 100px;
  border: solid 1px #000;
  border-radius: 10px;
  background: #fff;
  font-size: 66px;
  color: #fff;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.5);
  text-align: center;
  font-family: 'Helvetica Neue';
}

.flip .digital:before,
.flip .digital:after {
  content: '';
  position: absolute;
  left: 0;
  right: 0;
  background: #000;
  overflow: hidden;
  box-sizing: border-box;
}

.flip .digital:before {
  top: 0;
  bottom: 50%;
  border-radius: 10px 10px 0 0;
  border-bottom: solid 1px #666;
}

.flip .digital:after {
  top: 50%;
  bottom: 0;
  border-radius: 0 0 10px 10px;
  line-height: 0;
}

.flip .number0:before,
.flip .number0:after {
  content: '0';
}

.flip .number1:before,
.flip .number1:after {
  content: '1';
}

.flip .number2:before,
.flip .number2:after {
  content: '2';
}

.flip .number3:before,
.flip .number3:after {
  content: '3';
}

.flip .number4:before,
.flip .number4:after {
  content: '4';
}

.flip .number5:before,
.flip .number5:after {
  content: '5';
}

.flip .number6:before,
.flip .number6:after {
  content: '6';
}

.flip .number7:before,
.flip .number7:after {
  content: '7';
}

.flip .number8:before,
.flip .number8:after {
  content: '8';
}

.flip .number9:before,
.flip .number9:after {
  content: '9';
}

/*向下翻*/
.flip.down .front:before {
  z-index: 3;
}

.flip.down .back:after {
  z-index: 2;
  transform-origin: 50% 0%;
  transform: perspective(160px) rotateX(180deg);
}

.flip.down .front:after,
.flip.down .back:before {
  z-index: 1;
}

/*向上翻*/
.flip.up .front:after {
  z-index: 3;
}

.flip.up .back:before {
  z-index: 2;
  transform-origin: 50% 100%;
  transform: perspective(160px) rotateX(-180deg);
}

.flip.up .front:before,
.flip.up .back:after {
  z-index: 1;
}

.flip.down.go .front:before {
  transform-origin: 50% 100%;
  animation: frontFlipDown 0.6s ease-in-out both;
  box-shadow: 0 -2px 6px rgba(255, 255, 255, 0.3);
  backface-visibility: hidden;
}

.flip.down.go .back:after {
  animation: backFlipDown 0.6s ease-in-out both;
}

@keyframes frontFlipDown {
  0% {
    transform: perspective(160px) rotateX(0deg);
  }

  100% {
    transform: perspective(160px) rotateX(-180deg);
  }
}

@keyframes backFlipDown {
  0% {
    transform: perspective(160px) rotateX(180deg);
  }

  100% {
    transform: perspective(160px) rotateX(0deg);
  }
}

.flip.up.go .front:after {
  transform-origin: 50% 0;
  animation: frontFlipUp 0.6s ease-in-out both;
  box-shadow: 0 2px 6px rgba(255, 255, 255, 0.3);
  backface-visibility: hidden;
}

.flip.up.go .back:before {
  animation: backFlipUp 0.6s ease-in-out both;
}
@keyframes frontFlipUp {
  0% {
    transform: perspective(160px) rotateX(0deg);
  }

  100% {
    transform: perspective(160px) rotateX(180deg);
  }
}

@keyframes backFlipUp {
  0% {
    transform: perspective(160px) rotateX(-180deg);
  }

  100% {
    transform: perspective(160px) rotateX(0deg);
  }
}

.clock {
  text-align: center;
}

.clock em {
  display: inline-block;
  line-height: 102px;
  font-size: 66px;
  font-style: normal;
  vertical-align: top;
}
</style>
