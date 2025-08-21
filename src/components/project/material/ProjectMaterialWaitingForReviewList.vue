<script setup lang="ts">
/**
 * 对项目材料进行管理 *
 * 主要功能：增删改查
 */

import {
  computed,
  onMounted,
  reactive,
  ref,
  Ref,
  shallowRef,
  watchEffect,
} from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import {
  View,
  Hide,
  Search,
  Plus,
  Download,
  Upload,
} from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import { IServerMaterial } from "@/server/types/system/material";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectMaterial,
  IServerProjectMaterialView,
  IServerProjectMaterialForm,
} from "@/server/types/project/project";
import {
  serverProjectAdd,
  serverProjectDelete,
  serverProjectUpdate,
  serverGetProjectById,
  serverGetProjectViewById,
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
} from "@/server/project/project";
import {
  serverProjectMaterialAdd,
  serverProjectMaterialAddForm,
  serverProjectMaterialDelete,
  serverProjectMaterialDeleteById,
  serverProjectMaterialUpdate,
  serverProjectMaterialUpdateForm,
  serverGetProjectMaterialById,
  serverGetProjectMaterialPage,
  serverGetProjectMaterialPageView,
  serverGetProjectMaterialPageViewByProject,
  serverGetProjectMaterialPageViewByTaskIdAndProjectId,
} from "@/server/project/projectmaterial";

import {
  serverStartProcess,
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewProjectMaterialDialog from "@/components/project/material/NewProjectMaterialDialog.vue";
import UpdateProjectMaterialDialog from "@/components/project/material/UpdateProjectMaterialDialog.vue";
import UploadExcelProjectMaterialDialog from "@/components/project/material/UploadExcelProjectMaterialDialog.vue";

import ShowProjectMaterialDialog from "./ShowProjectMaterialDialog.vue";
import ShowProjectMaterialDiffDialog from "@/components/project/material/ShowProjectMaterialDiffDialog.vue";

const router = useRouter();
const route = useRoute();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示
const dialogShowDetailsVisible = ref(false); //控制“显示对话框”是否显示
const dialogShowProjectMaterialDiffDialogVisible = ref(false); //控制“显示材料不同对话框”是否显示

const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectMaterialViewPageData =
  ref<IServerPage<IServerProjectMaterialView> | null>(null);
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();
const radioUserType = ref(0);

const { projectId, taskId, designCompanyIndex } = defineProps<{
  projectId: string;
  taskId: string;
  designCompanyIndex: number;
}>();

onMounted(async () => {
  if (projectId) {
    await getProjectFromServer(projectId);

    await getProjectMaterialViewFromSever();
  }
});

const getProjectFromServer = async (projectId: string) => {
  const ret = await serverGetProjectViewById(projectId);
  // console.log(ret);
  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getProjectMaterialViewFromSever = async () => {
  let search = searchText.value.trim();
  let name = "", location = "", itemMark = "", technology = "", installation = "", brandPublic = "", brandPrivate = "";
  if (search) {
    switch (searchSelect.value) {
      case "0":
        name = search;
        break;
      case "1":
        location = search;
        break;
      case "2":
        itemMark = search;
        break;
      case "3":
        technology = search;
        break;
      case "4":
        installation = search;
        break;
      case "5":
        brandPublic = search;
        break;
      case "6":
        brandPrivate = search;
        break;
      default:
        break;
    }
    const ret = await serverGetProjectMaterialPageViewByTaskIdAndProjectId(
      projectId,
      taskId,
      designCompanyIndex,
      name,
      location,
      itemMark,
      technology,
      installation,
      brandPublic,
      brandPrivate,
      pageNo.value,
      pageSize.value
    );
      if (ret && ret.code == 200) {
        projectMaterialViewPageData.value = ret.data;
      }
    }
   else {
    //console.log(projectId);
    const ret = await serverGetProjectMaterialPageViewByTaskIdAndProjectId(
      projectId,
      taskId,
      designCompanyIndex,
      name,
      location,
      itemMark,
      technology,
      installation,
      brandPublic,
      brandPrivate,
      pageNo.value,
      pageSize.value
    );
    if (ret && ret.code == 200) {
      projectMaterialViewPageData.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return projectMaterialViewPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(projectMaterialViewPageData.value?.totalCount ?? 0);
});

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true;
};

/**
 * 单击显示按钮
 * @param index
 * @param row
 */
const onRowShowButtonClick = async (
  index: number,
  row: IServerProjectMaterialView
) => {
  updateProjectMaterialView.value = row;
  dialogShowDetailsVisible.value = true;
};

const onShowProjectMaterialDialogCancel = () => {
  dialogShowDetailsVisible.value = false;
};

const onShowProjectMaterialDialogOk = async (
  projectMaterialForm: IServerProjectMaterialForm
) => {
  dialogShowDetailsVisible.value = false;
};

const onPagePrevClick = async (value: number) => {
};
const onPageNextClick = async (value: number) => {
};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectMaterialViewFromSever();
};

// const onPageSizeChange = async (value: number) => {
//   pageSize.value = value;
//   setUserPageSize(value);
//   await getProjectMaterialViewFromSever();
// };

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getProjectMaterialViewFromSever();
};
const collapsed = ref(false);

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  // console.log(projectId);
  if (projectId) {
    await getProjectMaterialViewFromSever();
    await getProjectFromServer(projectId);
  }
});

const textElipsisValue = ref(false); //文本省略显示
const showProjectMaterialDiff = ref(true); //是否显示不同

/**
 * 材料是否不同
 * @param projectMaterialViewItem
 */
const isMaterialDifferent = (
  projectMaterialViewItem: IServerProjectMaterialView
) => {
  return (
    projectMaterialViewItem.material.id !==
    projectMaterialViewItem.projectMaterial.materialOriginId
  );
};

const showDiffProjectMaterialView = ref<IServerProjectMaterialView>();
const onMaterialDiffClick = async (row: IServerProjectMaterialView) => {
  showDiffProjectMaterialView.value = row;
  dialogShowProjectMaterialDiffDialogVisible.value = true;
};
const onShowProjectMaterialDiffDialogOk = () => {
  dialogShowProjectMaterialDiffDialogVisible.value = false;
};
const onShowProjectMaterialDiffDialogCancel = () => {
  dialogShowProjectMaterialDiffDialogVisible.value = false;
};
</script>

<template>
  <ShowProjectMaterialDialog
    :dialogVisible="dialogShowDetailsVisible"
    :projectView="projectViewData"
    :projectMaterialView="updateProjectMaterialView"
    @onDilalogCancel="onShowProjectMaterialDialogCancel"
    @onDilalogOk="onShowProjectMaterialDialogOk"
  >
  </ShowProjectMaterialDialog>

  <ShowProjectMaterialDiffDialog
    :dialogVisible="dialogShowProjectMaterialDiffDialogVisible"
    :showDiffProjectMaterialView="showDiffProjectMaterialView"
    @onDilalogCancel="onShowProjectMaterialDiffDialogCancel"
    @onDilalogOk="onShowProjectMaterialDiffDialogOk"
  ></ShowProjectMaterialDiffDialog>

  <div class="project-material-list-container container">
    <div style="font: 1.2em sans-serif; margin: 10px">
      待审核项目物料信息
      <el-icon
        style="float: right; cursor: pointer"
        v-if="collapsed"
        @click="collapsed = !collapsed"
      >
        <ArrowDown />
      </el-icon>
      <el-icon
        style="float: right; cursor: pointer"
        v-else
        @click="collapsed = !collapsed"
      >
        <ArrowUp />
      </el-icon>
    </div>

    <!--显示内容-->
    <div class="project-container" v-show="!collapsed">
      <div class="top-toolbar">
        <!--搜索框-->
        <div class="input-with-select">
          <el-input v-model="searchText" placeholder="输入搜索内容">
            <template #prepend>
              <el-select
                v-model="searchSelect"
                placeholder="Select"
                style="width: 115px"
              >
                <el-option label="材料名称" value="0" />
                <el-option label="材料位置" value="1" />
                <el-option label="编号" value="2" />
                <el-option label="技术要求" value="3" />
                <el-option label="施工要求" value="4" />
                <el-option label="品牌（公有）" value="5" />
                <el-option label="品牌（私有）" value="6" />
                <!-- <el-option label="审核状态" value="6" /> -->
              </el-select>
            </template>
            <template #append>
              <el-button :icon="Search" @click="onSearchClick" />
            </template>
          </el-input>
        </div>

        <div>
          <el-switch
            v-model="textElipsisValue"
            inline-prompt
            style="
              --el-switch-on-color: #13ce66;
              --el-switch-off-color: #ff4949;
            "
            active-text="自动调整高度"
            inactive-text="显示全部内容"
          />

          <el-switch
            v-model="showProjectMaterialDiff"
            class="ml-2"
            inline-prompt
            style="
              --el-switch-on-color: #13ce66;
              --el-switch-off-color: #ff4949;
            "
            active-text="显示不同"
            inactive-text="隐藏不同"
          />
        </div>
      </div>

      <el-row
        style="
          width: 100%;
          font: 1em sans-serif;
          border: 1px solid #eee;
          border-bottom-style: none;
          padding: 5px;
          margin: 5px;
          margin-left: 0px;
          white-space: pre-wrap;
          line-height: 1.5;
          margin-top: 10px;
          margin-bottom: -5px;
        "
      >
        <el-col :span="3">材料名称 </el-col>
        <el-col :span="3"> 材料位置</el-col>
        <el-col :span="2">编号 </el-col>
        <el-col :span="6">技术要求 </el-col>
        <el-col :span="4"> 施工要求</el-col>
        <el-col :span="2"> 品牌</el-col>
        <!-- <el-col :span="2"> 审核状态 </el-col> -->
        <el-col :span="2"> 操作 </el-col>
      </el-row>

      <el-row
        v-for="(projectMaterialViewItem, projectMaterialViewIndex) in tableData"
        style="
          width: 100%;
          font: 0.8em sans-serif;
          border: 1px solid #eee;
          padding: 5px;
          margin: 5px;
          margin-left: 0px;
          white-space: pre-wrap;
          line-height: 1.5;
          color: #606266;
        "
        v-loading="loading"
        :gutter="20"
      >
        <!--材料名称-->
        <el-col :span="3">
          <div v-if="isMaterialDifferent(projectMaterialViewItem)">
            <el-badge value="修改" class="item">
              <el-button
                @click="onMaterialDiffClick(projectMaterialViewItem)"
                >{{ projectMaterialViewItem.material.name }}</el-button
              >
              <template #content="{ value }">
                <div class="custom-content">
                  <el-icon>
                    <EditPen />
                  </el-icon>
                  <span>{{ value }}</span>
                </div>
              </template>
            </el-badge>
          </div>
          <div v-else style="display: flex; align-items: center">
            <span>{{ projectMaterialViewItem.material.name }}</span>
          </div>
        </el-col>

        <!--材料位置-->
        <el-col :span="3">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(projectMaterialViewItem) &&
                projectMaterialViewItem.material.location !==
                  projectMaterialViewItem.materialOrigin.location
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ projectMaterialViewItem.material.location }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.location }}
            </div>
          </div>
        </el-col>

        <!--编号-->
        <el-col :span="2">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(projectMaterialViewItem) &&
                projectMaterialViewItem.material.itemMark !==
                  projectMaterialViewItem.materialOrigin.itemMark
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ projectMaterialViewItem.material.itemMark }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.itemMark }}
            </div>
          </div>
        </el-col>

        <!--技术要求-->
        <el-col :span="6">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(projectMaterialViewItem) &&
                projectMaterialViewItem.material.technology !==
                  projectMaterialViewItem.materialOrigin.technology
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ projectMaterialViewItem.material.technology }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.technology }}
            </div>
          </div>
        </el-col>

        <!--施工要求-->
        <el-col :span="4">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(projectMaterialViewItem) &&
                projectMaterialViewItem.material.installation !==
                  projectMaterialViewItem.materialOrigin.installation
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ projectMaterialViewItem.material.installation }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.installation }}
            </div>
          </div>
        </el-col>

        <!--品牌-->
        <el-col :span="2">
          <div style="display: flex; flex-wrap: wrap; align-items: center">
            <!--公共品牌-->
            <span
              v-for="(
                brandPublicViewItem, brandPublicIndex
              ) in projectMaterialViewItem.projectMaterialBrandPublicViewList"
              :key="brandPublicIndex"
            >
              <el-tag type="success" style="margin-left: 3px">{{
                brandPublicViewItem.brandPublicView.brandView.brand.name
              }}</el-tag>
            </span>
            <!--项目私有品牌-->

            <span
              v-for="(
                brandPrivateViewItem, brandPrivateIndex
              ) in projectMaterialViewItem.projectMaterialBrandPrivateViewList"
              :key="brandPrivateIndex"
            >
              <el-tag type="info">{{
                brandPrivateViewItem.projectBrandView.brandView.brand.name
              }}</el-tag>
            </span>
          </div>
        </el-col>

        <!--审核状态-->
        <el-col :span="2">
          <div style="display: flex; align-items: center">
            <span style="margin-left: 10px">{{
              projectMaterialViewItem.checkstate
            }}</span>
          </div>
        </el-col>

        <!--操作-->
        <el-col :span="2">
          <div
            style="
              display: flex;
              flex-direction: column;
              align-items: center;
              justify-content: center;
            "
          >
            <el-button
              size="small"
              @click="
                onRowShowButtonClick(
                  projectMaterialViewIndex,
                  projectMaterialViewItem
                )
              "
            >
              详细信息
            </el-button>
          </div>
        </el-col>
      </el-row>

      <el-pagination
        :hide-on-single-page="false"
        class="page-class"
        background
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        layout="total, prev, pager, next"
        :total="totalCount"
        @prev-click="onPagePrevClick"
        @next-click="onPageNextClick"
        @current-change="onPageCurrentChange"
      />

      <!-- <el-pagination
        :hide-on-single-page="true"
        class="page-class"
        background
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[10, 50, 100, 200, 300, 400]"
        layout="total, sizes, prev, pager, next"
        :total="totalCount"
        @prev-click="onPagePrevClick"
        @next-click="onPageNextClick"
        @current-change="onPageCurrentChange"
        @size-change="onPageSizeChange"
      /> -->
    </div>
  </div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");
.project-material-list-container {
  padding-top: 10px;
}
.page-class {
  padding: 10px;
}

.top-toolbar {
  display: flex;

  margin: 0 10px;
}

.user-type-radio {
  margin-left: 50px;
}

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}

.textEllipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  overflow: hidden;
  /* autoprefixer: ignore next */
  -webkit-box-orient: vertical;
}
</style>
