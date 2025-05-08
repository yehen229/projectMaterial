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
  byprojectname_getList,
 getAllList_end, getAllList_processing,
  getchart_projectname_totalReviewResulDisagree,
  getAllList_disagree_all,
  servergetdesignUnpassData,
  serverGetProjectListPageView
} from "@/server/project/statisticalanalysis";
import DesignUnpaaTable from "@/views/project/statisticalCompont/designUnpaaTable.vue";
import BeforeZongBaoTable from "@/views/project/statisticalCompont/beforeZongBaoTable.vue";
import JianlireviewTable from "@/views/project/statisticalCompont/jianlireviewTable.vue";
import JianliAndgongchengbureviewTable from "@/views/project/statisticalCompont/jianliAndgongchengbureviewTable.vue";

const dialogDisagreecount = ref(5);

onMounted(async () => {
  await fetchTableData();
  await getprocessingprojectcount();
  await getendprojectcount();
  await getchart();
  await getchart_bar();
  await getDialogData();
});
const getBar_Chart_data = ref()
const transformDataForChart = (data) => {
  return data.map(item => ([
    item.count,
    item.project.name
  ]));
}
const chartOptions = ref({});
const processingcount = ref()
const getprocessingprojectcount = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await getAllList_processing();

    if (ret && ret.code == 200) {
      processingcount.value = ret.data
    }
  } catch (error) {
    ElMessage.error("获取信息失败");
    console.error("获取信息失败", error);
  }
};

const endcount = ref()
const getendprojectcount = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await getAllList_end();

    if (ret && ret.code == 200) {
      endcount.value = ret.data
    }
  } catch (error) {
    ElMessage.error("获取信息失败");
    console.error("获取信息失败", error);
  }
};
const fetchTableData = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await serverGetProjectListPageView(pageNo.value, pageSize.value);

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
const dialogVisible = ref(true);
const allDisagreeData = ref();
const dialogData = ref();

const getDialogData = async () => {
  let ret = await getAllList_disagree_all();
  if (ret && ret.code == 200) {
    allDisagreeData.value = ret.data;    
    dialogData.value = [];
    for (let i = 0; i < dialogDisagreecount.value; i++) {      
      dialogData.value[i] = allDisagreeData.value[i];
    }
    console.log(dialogData.value);
    
  }
};

const onDialogCountChange = () => {
  if (dialogDisagreecount.value > 0) {
    dialogData.value = [];
    for (let i = 0; i < dialogDisagreecount.value && i < allDisagreeData.value.length; i++) {
      dialogData.value[i] = allDisagreeData.value[i];
    }
  } else {
    dialogDisagreecount.value = 10;
  }
}

const goProjectPage = (id: string) => {
  router.push({path: '/project-details/' + id});
}

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
  try {
    let projectname = inputProjectName.value.trim();
    // 调用 API 获取项目列表
    const ret = await byprojectname_getList(inputProjectName.value, pageNo.value, pageSize.value);

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
  pageSize.value = 10;

  ifclickserarch.value = 0
  await fetchTableData();
}

const getporit = (successfulresult, totalresult) => {
  if (totalresult === 0) {
    return "0%";
  }
  let porit = successfulresult / totalresult * 100;
  return Math.floor(porit) + "%";
};
const onProjectDetailsButtonClick = (projectItem: string
) => {
  const projectlist = {
    project: {
      id: "",
    }
  }
  projectlist.project.id = projectItem
  console.log(projectItem)
  router.push({path: `/project-details/${projectlist.project.id}`});
};

const chartRef = ref(null);
const getchart = () => {
  // 基于准备好的dom，初始化echarts实例
  const chartDom = chartRef.value;
  const myChart = echarts.init(chartDom);
  const option = {
    tooltip: {
      trigger: 'item',
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '70%'],
        startAngle: 180,
        endAngle: 360,
        data: [
          {value: processingcount.value, name: '正在进行中项目'},
          {value: endcount.value, name: '已完成项目'},
        ]
      }
    ]
  };
  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
}

const chartRef_bar = ref(null);
const getchart_bar = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await getchart_projectname_totalReviewResulDisagree();
    if (ret && ret.code == 200) {
      getBar_Chart_data.value = transformDataForChart(ret.data)
    }
  } catch (error) {
    ElMessage.error("获取信息失败");
    console.error("获取信息失败", error);
  }
  // 基于准备好的dom，初始化echarts实例
  const chartDom = chartRef_bar.value;
  const myChart = echarts.init(chartDom);
  const option = {
    tooltip: {
      trigger: 'item',
    },
    title: {
      text: '审核不通过次数最多的项目',
    },
    dataset: [
      {
        dimensions: ['score', 'name'],
        source: getBar_Chart_data.value
      },
      {
        transform: {
          type: 'sort',
          config: {dimension: 'score', order: 'desc'}
        }
      }
    ],
    xAxis: {
      type: 'category',
      axisLabel: {interval: 0, rotate: 30},
      name: "项目名称"
    },
    yAxis: {
      name: "审核不通过的次数"
    },
    series: {
      type: 'bar',
      encode: {x: 'name', y: 'score'},
      datasetIndex: 1,
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      }
    },

  };
  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
}


</script>

<template>

  <div>

    <el-dialog
      v-model="dialogVisible"
      title="请注意审核驳回较多的项目"
      width="500"
    >
      审核不通过次数前<el-input v-model="dialogDisagreecount" @change="onDialogCountChange()" style="width: 50px"/>个
      <el-table :data="dialogData" style="width: 100%">
        <el-table-column label="项目名">
          <template #default="scope">
            <el-button @click="goProjectPage(scope.row.projectId)">{{ scope.row.project.name }}</el-button>
          </template>
        </el-table-column>
        <el-table-column label="审核不通过次数">
          <template #default="scope">
            <div>{{ scope.row.count }}</div>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="dialogVisible = false">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
    <el-card>
      <h2>设计单位审核不通过</h2>
      <DesignUnpaaTable/>

      <h2>材料外观审核不通过</h2>
      <Before-zong-bao-table/>
      <h2>监理审核不通过记录</h2>
      <Jianlireview-table/>
      <h2>监理和工程部审核不通过记录</h2>
      <jianli-andgongchengbureview-table/>

      <el-row :gutter="20">
        <el-col :span="12">
          <div ref="chartRef" style="width: 100%; height: 400px;"></div>
        </el-col>
        <el-col :span="12">
          <div ref="chartRef_bar" style="width: 100%; height: 400px;"></div>
        </el-col>

      </el-row>


      <div style="margin-left: 0.1%;margin-bottom: 1%;margin-top: 1%">
        <span>项目名称：</span>
        <el-input
            v-model="inputProjectName"
            style="width: 240px"
            placeholder="请输入项目名称"
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

          <el-table-column label="项目名称">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.projectName }}
              </div>
            </template>

          </el-table-column>

          <el-table-column label="项目状态">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
                  v-if="scope.row.checkProjectWhetherEnd==true"
              >
                <el-tag type="success">{{ "已完成" }}</el-tag>
              </div>
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
                  v-if="scope.row.checkProjectWhetherEnd==false"
              >
                <el-tag type="info"> {{ "进行中" }}</el-tag>

              </div>
            </template>
          </el-table-column>

          <el-table-column label="通过率">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ getporit(scope.row.totalReviewResultAgree, scope.row.totalReviewResulDisagree+scope.row.totalReviewResultAgree) }}
              </div>
            </template>
          </el-table-column>


          <el-table-column label="审核不通过次数">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.totalReviewResulDisagree + "项" }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="审核通过次数">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                {{ scope.row.totalReviewResultAgree + "项" }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="项目详细信息">
            <template #default="scope">
              <div
                  style="display: flex; align-items: center"
                  class="project-title"
              >
                <el-button
                    size="small"
                    @click="onProjectDetailsButtonClick(scope.row.projectId)"
                > 项目详细信息
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