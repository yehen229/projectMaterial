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
import * as echarts from 'echarts';
import 'echarts/core';
import {BarChart} from 'echarts/charts';
import {TitleComponent, TooltipComponent, GridComponent} from 'echarts/components';
import {CanvasRenderer} from 'echarts/renderers';
import {
  byprojectname_getList, getchart_projectname_totalReviewResulDisagree,
  serverGetProjectListPageView
} from "@/server/project/statisticalanalysis";
import {byprojectname_Search, serverGetlogListPageView} from "@/server/project/logManage";
import {formatDate} from "@/utils/utils";

// 二维码生成器 qr-code
const qrCodeValue = ref("123")
const qrCodeWidth = ref(150)
const qrCodeDarkColor = ref('#000')
const qrCodeLightColor = ref('#FFF')
onMounted(async () => {
  await fetchTableData();
});
const getBar_Chart_data = ref()
const transformDataForChart = (data) => {
  return data.map(item => ([
    item.totalReviewResulDisagree,
    item.projectName
  ]));
}
const chartOptions = ref({});
const agreecount = ref()
const getagreecount = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await getAllList_agree();

    if (ret && ret.code == 200) {
      agreecount.value = ret.data
      // console.log(agreecount.value)
    }
  } catch (error) {
    ElMessage.error("获取信息失败");
    console.error("获取信息失败", error);
  }
};

const disagreecount = ref()
const getdisagreecount = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await getAllList_disagree();

    if (ret && ret.code == 200) {
      disagreecount.value = ret.data
      console.log(disagreecount.value)
    }
  } catch (error) {
    ElMessage.error("获取信息失败");
    console.error("获取信息失败", error);
  }
};
const fetchTableData = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await serverGetlogListPageView(pageNo.value, pageSize.value);

    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
    }
  } catch (error) {
    ElMessage.error("获取项目列表失败");
    console.error("获取项目列表失败", error);
  }
};

const projectViewPage = ref();

const tableData = computed(() => {
  return projectViewPage.value?.result;
});
const totalCount = computed(() => {
  return Number(projectViewPage.value?.totalCount ?? 0);
});
// 页码和页大小
const pageNo = ref(1);
// const pageSize = ref(getUserPageSize());
const pageSize = ref(10);
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

// 搜索框
const inputProjectName = ref("")
const inputMaterialName = ref("")
const inputbatch = ref(-1)
// 0是没点击 1是点击
const ifclickserarch = ref(0)
const inputSearch = async () => {
  // await inputReset()
  try {
    let projectname = inputProjectName.value.trim();
    // 调用 API 获取项目列表
    const ret = await byprojectname_Search(projectname, pageNo.value, pageSize.value);
    console.log(ret);
    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
      console.log("projectViewPage", projectViewPage.value);
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
  pageSize.value = 10;

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
        <span>操作者：</span>
        <el-input
            v-model="inputProjectName"
            style="width: 240px"
            placeholder="请输入操作者"
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
          <el-table-column label="操作者">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"

              >
                {{ scope.row.user.realName }}
              </div>
            </template>

          </el-table-column>


          <el-table-column label="单位">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
                  v-if="scope.row.company!==null"
              >
                {{ scope.row.company.name }}
              </div>
<!--              <div-->
<!--                  style="display: flex; align-items: center"-->
<!--                  class="project-title"-->
<!--                  v-else="scope.row.company"-->
<!--              >-->
<!--                {{ scope.row.company.name }}-->
<!--              </div>-->

            </template>

          </el-table-column>

          <el-table-column label="项目">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
                  v-if="scope.row.project!==null"
              >
                {{ scope.row.project.name }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.log.step_description }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="时间">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{
                  formatDate(scope.row.log.op_datetime)
                }}
              </div>
            </template>
          </el-table-column>
          <!--          <el-table-column label="项目">-->
          <!--            <template #default="scope">-->
          <!--              <div-->
          <!--                  style="display: flex; align-items: center"-->
          <!--                  class="project-title"-->
          <!--              >-->
          <!--                {{ getporit(scope.row.totalReviewResultAgree,scope.row.totalReviewResult) }}-->
          <!--              </div>-->
          <!--            </template>-->
          <!--          </el-table-column>          <el-table-column label="操作">-->
          <!--            <template #default="scope">-->
          <!--              <div-->
          <!--                  style="display: flex; align-items: center"-->
          <!--                  class="project-title"-->
          <!--              >-->
          <!--                {{ getporit(scope.row.totalReviewResultAgree,scope.row.totalReviewResult) }}-->
          <!--              </div>-->
          <!--            </template>-->
          <!--          </el-table-column>-->


          <!--          <el-table-column label="时间">-->
          <!--            <template #default="scope">-->
          <!--              <div-->
          <!--                  style="display: flex; align-items: center"-->
          <!--                  class="project-title"-->
          <!--              >-->
          <!--                {{ scope.row.totalReviewResulDisagree+"项" }}-->
          <!--              </div>-->
          <!--            </template>-->
          <!--          </el-table-column>-->


        </el-table>
      </div>

      <el-pagination
          style="margin-top: 10px"

          class="page-class"
          background
          v-model:current-page="pageNo"
          v-model:page-size="pageSize"
          :page-sizes="[10, 50, 100]"
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