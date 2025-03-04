<script setup lang="ts">
import {
  computed,
  onMounted,
  reactive,
  ref,
  Ref,
  shallowRef,
  watchEffect,
} from "vue";

import { IPieData } from "./index";

interface IPieRatioData {
  ratio: number; //角度
  title: string; //标签
  color: string; //颜色
  colorrandom: boolean; //颜色是否随机？
}

const pieGraphWidth = ref(100);

const { pieDataList } = defineProps(["pieDataList"]);

const pieDataListShow = ref<IPieData[]>();

const convertData = (data: IPieData[]): IPieRatioData[] => {
  const sum = data.reduce((acc, cur) => acc + cur.data, 0);

  return data.map((item) => {
    const temp: IPieRatioData = {
      ratio: item.data / sum,
      title: item.title,
      color: item.color,
      colorrandom: item.colorrandom,
    };
    return temp;
  });
};

function execPoint(cx: number, cy: number, r: number, ratio: number) {
  // 计算弧度，一个整圆弧度是 2π
  const rad = ratio * 2 * Math.PI;
  return {
    x: cx + Math.sin(rad) * r,
    y: cy - Math.cos(rad) * r,
  };
}

/**
 * 构建裁剪路径
 * @param data 数据
 * @param width 元素宽度，宽高相同
 */
function buildSectorPaths(data: IPieRatioData[], width: number) {
  // 偏转量
  let offset = 0;
  // 圆心坐标
  const cx = width / 2;
  const cy = width / 2;
  // 半径
  const r = width / 2;
  const result = [];
  for (const datum of data) {
    let path = `M ${cx},${cy}`;
    // 圆弧起点
    const start = execPoint(cx, cy, r, offset);
    path += ` L ${start.x},${start.y}`;
    // 圆弧终点
    offset += datum.ratio;
    const end = execPoint(cx, cy, r, offset);
    // 圆弧大关圆画大圆，否则画小圆
    const angle = datum.ratio * 2 * Math.PI;
    path += ` A ${r},${r} 0,${angle > Math.PI ? 1 : 0},1 ${end.x},${end.y}`;
    path += " Z";
    result.push(path);
  }
  return result;
}

interface ISector {
  id: number;
  title: string;
  path: string;
  backgroundColor: string;
}
const sectorList = ref<ISector[]>([]);

function buildPie(data: IPieRatioData[]) {
  console.log(data);
  const paths = buildSectorPaths(data, pieGraphWidth.value);
  for (let i = 0; i < paths.length; i++) {
    const path = paths[i];
    const sector = document.createElement("div");
    sector.classList.add("sector");
    sector.style.clipPath = `path('${path}')`;

    let bgcolor = data[i].color;

    if (data[i].colorrandom) {
      // 给个随机背景色
      const r = Math.floor(Math.random() * 255);
      const g = Math.floor(Math.random() * 255);
      const b = Math.floor(Math.random() * 255);
      bgcolor = `rgb(${r},${g},${b})`;
    }

    const sectorItem: ISector = {
      id: i,
      title: data[i].title,
      path: `path('${path}')`,
      backgroundColor: bgcolor,
    };
    sectorList.value.push(sectorItem);
  }
}
/**
 * 注意，该函数需要放在最后，防止convertData等函数未定义
 */
watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  console.log(pieDataList);
  pieDataListShow.value = pieDataList;
  buildPie(convertData(pieDataList));
});

onMounted(async () => {
  buildPie(convertData(pieDataList));
});
</script>

<template>
  <div class="pie-container">
    <div style="margin-right: 20px">
      <div
        v-for="sectorItem in sectorList"
        :key="sectorItem.id"
        style="display: flex; align-items: center; font: 0.8em sans-serif"
      >
        <div
          class="sector-legend"
          :style="{
            backgroundColor: sectorItem.backgroundColor,
          }"
        ></div>
        <div style="margin-left: 5px">
          {{ sectorItem.title }}
        </div>
      </div>
    </div>

    <div
      class="pie"
      :style="{ width: pieGraphWidth + 'px', height: pieGraphWidth + 'px' }"
    >
      <div
        class="sector"
        :style="{
          clipPath: sectorItem.path,
          backgroundColor: sectorItem.backgroundColor,
        }"
        v-for="sectorItem in sectorList"
        :key="sectorItem.id"
      ></div>
    </div>
  </div>
</template>

<style scoped>
.pie-container {
  display: flex;
  align-items: center;
  border: 1px solid var(--el-border-color);
  padding: 10px;
}
.sector-legend {
  width: 20px;
  height: 10px;
}
.pie {
  position: relative;
}

.pie .sector {
  position: absolute;
  width: 100%;
  height: 100%;
  transition: all 0.3s ease-in;
}

.pie .sector:hover {
  transform: scale(1.1);
}

.pie .title {
  display: none;
}

.pie .sector:hover + .title {
  position: absolute;
  top: 50%;
  left: 110%;
  transform: translateY(-50%);
  display: block;
}
</style>
