<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import {setUserPageSize} from "@/cookies/user";
import {
  servergetunpassbeforezongbao, servergetunpassreviewbefore_zongbao,
  servergetunpassreviewbyprojectidmaterialid_companyid
} from "@/server/project/statisticalanalysis";
import {ElMessage} from "element-plus";
import {formatDate} from "@/utils/utils";

onMounted(async () => {
  await getdesignUnpassData();
});
const getdesignUnpassData = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await servergetunpassbeforezongbao(pageNo.value, pageSize.value);

    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
    }
  } catch (error) {
    ElMessage.error("获取项目列表失败");
    console.error("获取项目列表失败", error);
  }
};

const projectViewPage = ref();

const tableData = computed(   () => {
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
// 0是没点击 1是点击
const ifclickserarch = ref(0)
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  if (ifclickserarch.value == 0) {
    await getdesignUnpassData();
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

// 材料审核详情
const handleCollapseChange = async (index) => {
  console.log("222222222222222");
  const row = tableData.value[index];
  console.log(row);
  const data = await getdesignUnpass(row.projectId,  row.materialId);
  row.reviewData  = data;
  console.log("333");
  console.log(row);
};
const getdesignUnpass = async (projectId,materialId) => {
//   调取接口 获取对应的材料审核记录
    let paramdata;
    try {
      // 调用 API 获取项目列表
      const ret = await servergetunpassreviewbefore_zongbao(projectId, materialId);

      if (ret && ret.code == 200) {
        paramdata = ret.data;
      }
    } catch (error) {
      ElMessage.error("获取项目列表失败");
      console.error("获取项目列表失败", error);
  };
    return paramdata;
}
</script>

<template>

  <div>
    <div class="project-container">
    <el-table
        :data="tableData"
        table-layout="auto"
        style="width: 100%"
        v-loading="loading"
        stripe
    >
      <!--        <el-table-column type="selection" :selectable="selectlist()" width="55" />-->

      <el-table-column label="材料名称">
        <template #default="scope">
          <div
              style="display: flex; align-items: center"
              class="project-title"
              v-if="scope.row.material"
          >
            {{ scope.row.material.name }}
          </div>
        </template>

      </el-table-column>

      <el-table-column label="材料编号">
        <template #default="scope">
          <div
              style="display: flex; align-items: center"
              class="project-title"
              v-if="scope.row.material"
          >
            {{ scope.row.material.itemMark }}
          </div>
        </template>

      </el-table-column>
      <el-table-column label="材料位置">
        <template #default="scope">
          <div
              style="display: flex; align-items: center"
              class="project-title"
              v-if="scope.row.material"
          >
            {{ scope.row.material.location }}
          </div>
        </template>

      </el-table-column>
      <el-table-column label="材料所属项目">
        <template #default="scope">
          <div
              style="display: flex; align-items: center"
              class="project-title"
              v-if="scope.row.project"
          >
            {{ scope.row.project.name }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="材料审核详情">
        <template #default="scope">

          <el-collapse  @change="handleCollapseChange(scope.$index)" >
            <el-collapse-item title="审核详情" name="1">
              <el-timeline style="max-width: 600px">
                <el-timeline-item :timestamp="formatDate(item.projectAppearanceReviewUser.reviewDatetime)"
                                  placement="top"
                                  v-for="(item, index) in scope.row.reviewData"
                                  :key="index"
                >
                  <el-card>
                    <el-descriptions
                        :column="1"
                        border
                    >
                      <el-descriptions-item label="提交单位" >{{item.company.companyType	 }}</el-descriptions-item>
                      <el-descriptions-item label="提交单位名字" >{{ item.company.name }}</el-descriptions-item>
                      <el-descriptions-item label="检查人">{{item.user.realName}}</el-descriptions-item>
                      <el-descriptions-item label="审核评论">
                        {{item.reviewcotent}}
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </el-collapse-item>
            </el-collapse>

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
  </div>
</template>

<style scoped>

</style>