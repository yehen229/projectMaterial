<script setup lang="ts">
import VueQrcode from 'vue-qrcode'
import {computed, onMounted, ref} from 'vue'
import {getpageviewbykey, serverGetQrcodeProjectPageView} from "@/server/project/aboutqrcode";
import {getUserPageSize, setUserPageSize} from "@/cookies/user";
import {Calendar, Search, Refresh} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import {IServerPage} from "@/server/types/System";
import {IServerProjectView} from "@/server/types/project/project";
import router from "@/router";

// 二维码生成器 qr-code
const qrCodeValue = ref("123")
const qrCodeWidth = ref(150)
const qrCodeDarkColor = ref('#000')
const qrCodeLightColor = ref('#FFF')
onMounted(async () => {
  await fetchTableData();
});
const fetchTableData = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await serverGetQrcodeProjectPageView(pageNo.value, pageSize.value);

    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
    }
  } catch (error) {
    ElMessage.error("获取项目列表失败");
    console.error("获取项目列表失败", error);
  }
};

const projectViewPage = ref<IServerPage<IServerProjectView>>();
const tableData = computed(() => {
  return projectViewPage.value?.result;
});
const totalCount = computed(() => {
  return Number(projectViewPage.value?.totalCount ?? 0);
});
// 页码和页大小
const pageNo = ref(1);
// const pageSize = ref(getUserPageSize());
const pageSize = ref(5);
const loading = ref(false);

const onPagePrevClick = (value: number) => {
};
const onPageNextClick = (value: number) => {
};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  if (ifclickserarch.value == 0) {
    await fetchTableData();
  }
  if (ifclickserarch.value == 1) {
    await inputSearch();
  }
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  if (ifclickserarch.value == 0) {
    await fetchTableData();
  }
  if (ifclickserarch.value == 1) {
    await inputSearch();
  }
};
// 下载二维码
const downloadQrCode = (qrcodeValue: string, ids: string, projectname: string, materialbatch: string, materialname: string, materialCount: string, materialunit: string) => {
  console.log(qrcodeValue)
  const qrCodeSrc = document.getElementById(ids).src;
  const link = document.createElement('a');
  link.href = qrCodeSrc; // 使用 qrCodeSrc 而不是 this.qrCodeValue
  link.download = projectname + ":第" + materialbatch + "批次:" + materialname + "-" + materialCount + materialunit;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};
const materialInfo = (qrcode: string) => {
  localStorage.setItem('qrcode', JSON.stringify(qrcode))
  router.push({path: "scanAfter"});
}

// 搜索框
const inputProjectName = ref("")
const inputMaterialName = ref("")
const inputbatch = ref(-1)
// 0是没点击 1是点击
const ifclickserarch = ref(0)
const inputSearch = async () => {
  try {
    let projectname = inputProjectName.value.trim();
    let materialname = inputMaterialName.value.trim();
    // 调用 API 获取项目列表
    const ret = await getpageviewbykey(inputProjectName.value, inputbatch.value, inputMaterialName.value, pageNo.value, pageSize.value);

    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
      ifclickserarch.value = 1
    }
  } catch (error) {
    ElMessage.error("获取项目列表失败");
    console.error("获取项目列表失败", error);
  }
}
//重置按钮
const inputReset = async () => {
  inputProjectName.value = "";
  inputMaterialName.value = "";
  inputbatch.value = -1;
  pageNo.value = 1;
  pageSize.value = 5;

  ifclickserarch.value = 0
  await fetchTableData();
}


</script>

<template>

  <div>
    <!--    <div>{{qrCodeValue}}</div>-->
    <!--    <vue-qrcode :value="qrCodeValue" :width="qrCodeWidth" :color="{ dark: qrCodeDarkColor, light: qrCodeLightColor }" id="qrCodeDom"></vue-qrcode>-->
    <!--    <button @click="handleDownload">下载二维码</button>-->
    <el-card>
      <div style="margin-left: 0.1%;margin-bottom: 1%;margin-top: 1%">
        <span>项目名称：</span>
        <el-input
            v-model="inputProjectName"
            style="width: 240px"
            placeholder="请输入项目名称"
            :prefix-icon="Search"
        />

        <span style="margin-left: 1%">订阅批次：</span>
        <!--  <el-input-->
        <!--      v-model="input2"-->
        <!--      style="width: 240px"-->
        <!--      placeholder="请输入项目名称"-->
        <!--      :prefix-icon="Search"-->
        <!--  />-->
        <el-input-number v-model="inputbatch" :min="-1" @change="handleChange"/>
        <span style="margin-left: 1%">材料名称：</span>
        <el-input
            v-model="inputMaterialName"
            style="width: 240px"
            placeholder="请输入材料名称"
            :prefix-icon="Search"
        />

        <el-button type="primary" :icon="Search" style="margin-left: 1%" @click="inputSearch">搜索</el-button>
        <el-button type="primary" :icon="Refresh" @click="inputReset">重置</el-button>
      </div>
      <div class="project-container">
        <el-table
            :data="tableData"
            table-layout="auto"
            style="width: 100%"
            v-loading="loading"
            stripe
        >
          <!--        <el-table-column type="selection" :selectable="selectlist()" width="55" />-->

          <el-table-column label="所属项目名称">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.project.name }}
              </div>
            </template>

          </el-table-column>

          <el-table-column label="订阅批次">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.buyMaterial.batch }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="材料名称">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.material.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="材料数量">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.buyMaterial.materialCount }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="材料单位">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.buyMaterial.materialUnit }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="二维码">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                <!--              {{ scope.row.buyMaterial.qrcode }}-->
                <vue-qrcode :id="scope.row.buyMaterial.id" :value="scope.row.buyMaterial.qrcode" :width="qrCodeWidth"
                            :color="{ dark: qrCodeDarkColor, light: qrCodeLightColor }"></vue-qrcode>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="详情">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                <el-tag type="primary" @click="materialInfo(scope.row.buyMaterial.qrcode)">材料详情</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="下载二维码">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                <el-button type="primary"
                           @click="downloadQrCode(scope.row.buyMaterial.qrcode,scope.row.buyMaterial.id,scope.row.project.name,scope.row.buyMaterial.batch,scope.row.material.name,scope.row.buyMaterial.materialCount,scope.row.buyMaterial.materialUnit)">
                  下载
                </el-button>
              </div>
            </template>
          </el-table-column>

        </el-table>
      </div>

      <el-pagination
          style="margin-top: 10px"

          class="page-class"
          background
          v-model:current-page="pageNo"
          v-model:page-size="pageSize"
          :page-sizes="[5,10, 50, 100]"
          layout="total, sizes, prev, pager, next"
          :total="totalCount"
          @prev-click="onPagePrevClick"
          @next-click="onPageNextClick"
          @current-change="onPageCurrentChange"
          @size-change="onPageSizeChange"
      />
    </el-card>
  </div>

</template>

<style scoped>

</style>