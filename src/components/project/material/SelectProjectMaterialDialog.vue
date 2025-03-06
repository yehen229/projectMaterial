<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import type { CascaderValue } from "element-plus";
import { ElTable } from "element-plus";
import {
  clearCookies,
  setUserCookies,
  getToken,
  getUserID,
  getUserName,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import {
  IMaterialClassifyOption,
  generateMaterialClassifyOption,
} from "@/utils/MaterialClassifyOptions";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
  IServerProjectBrand,
  IServerProjectBrandView,
  IServerProjectBrandForm,
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
  IServerMaterial,
  IServerMaterialBrand,
  IServerMaterialBrandView,
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyDivisionTreeItem,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifyGroupTreeItem,
  IServerMaterialClassifyGroupView,
  IServerMaterialClassifySection,
  IServerMaterialClassifySectionView,
  IServerMaterialClassifyTree,
  IServerMaterialPhoto,
  IServerMaterialPhotoView,
  IServerMaterialForm,
  IServerMaterialView,
} from "@/server/types/system/material";

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
} from "@/server/project/projectmaterial";

import {
  serverBrandPublicAdd,
  serverBrandPublicDelete,
  serverBrandPublicDeleteById,
  serverBrandPublicUpdate,
  serverGetBrandPublicById,
  serverGetBrandPublicByBrandId,
  serverGetBrandPublicByMaterialClassifySectionId,
  serverGetBrandPublicPage,
  serverGetBrandPublicPageView,
  serverGetBrandPublicPageViewByBrandName,
  serverGetBrandPublicPageViewByBrandPosition,
} from "@/server/system/brandpublic";

import {
  serverGetProjectBrandListByProjectId,
  serverGetProjectBrandViewListByProjectId,
} from "@/server/project/projectbrand";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { serverGetMaterialListByMaterialClassifySectionId } from "@/server/system/material";

import { areaOptions } from "@/utils/area";

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

const multipleTableRef = ref<InstanceType<typeof ElTable>>();
const multipleSelection = ref<IServerProjectMaterialView[]>([]);
const toggleSelection = (rows?: IServerProjectMaterialView[]) => {
  if (rows) {
    rows.forEach((row) => {
      // TODO: improvement typing when refactor table
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-expect-error
      multipleTableRef.value!.toggleRowSelection(row, undefined);
    });
  } else {
    multipleTableRef.value!.clearSelection();
  }
};

const handleSelectionChange = (val: IServerProjectMaterialView[]) => {
  multipleSelection.value = val;
};

interface Props {
  dialogVisible: boolean; //对话框是否可见
  projectId: string;
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
  projectId: "",
});

const getProjectFromServer = async (projectId: string) => {
  const ret = await serverGetProjectViewById(projectId);
  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getProjectMaterialViewFromSever = async () => {
  let search = searchText.value.trim();
  //暂时去掉审核状态
  let name = "", location = "", itemMark = "", technology = "", installation = "", brand = "", brandPrivate = "";
  if (search) {
    console.log(searchSelect.value);
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
        brand = search;
        break;
      case "6":
        brandPrivate = search;
        break;
      default:
        break;
    }
    const ret = await serverGetProjectMaterialPageViewByProject(
      props.projectId,
      name,
      location,
      itemMark,
      technology,
      installation,
      brand,
      brandPrivate,
      pageNo.value,
      pageSize.value
    );
    if (ret && ret.code == 200) {
      projectMaterialViewPageData.value = ret.data;
    }
    console.log(projectMaterialViewPageData.value);
  }
};

const tableData = computed(() => {
  return projectMaterialViewPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(projectMaterialViewPageData.value?.totalCount ?? 0);
});

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectMaterialViewFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectMaterialViewFromSever();
};

const projectForm = reactive({
  name: "",
  location: "",
  totalInvestmentWithTax: 0,
  totalInvestmentWithoutTax: 0,
  buildingAreaAboveGround: 0,
  buildingAreaUnderGround: 0,
  companyConstructionId: "",
  companyDesignId: "",
  note: "",
  approvalDate: new Date(),
});

//event
const emit = defineEmits<{
  (
    e: "onDilalogOk",
    projectMaterialViewList: IServerProjectMaterialView[]
  ): void;
  (e: "onDilalogCancel"): void;
}>();

const dialogFormVisible = computed({
  get() {
    return props.dialogVisible;
  },
  set(val) {
    return val;
  },
});

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getProjectMaterialViewFromSever();
};

const onOpenDialog = async () => {
  await getProjectFromServer(props.projectId);
  await getProjectMaterialViewFromSever();
};
const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  console.log(multipleSelection.value);
  if (multipleSelection.value.length == 0) {
    ElMessage.error("请选择要选择的物料");
    return;
  }
  emit("onDilalogOk", multipleSelection.value);
};

const onCancel = () => {
  emit("onDilalogCancel");
};

const cascaderProps = {
  expandTrigger: "hover" as const,
  emitPath: true,
};
const textElipsisValue = ref(false);
</script>

<template>
  <div>
    <el-dialog
      title="选择项目物料"
      v-model="dialogFormVisible"
      :before-close="handleClose"
      width="1200px"
      @open="onOpenDialog"
      draggable
    >
      <div class="project-material-list-container">
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
                  <el-option label="品牌" value="5" />
                  <el-option label="审核状态" value="6" />
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
          </div>
        </div>

        <!--显示内容-->
        <div class="project-container">
          <el-table
            ref="multipleTableRef"
            :data="tableData"
            style="width: 100%"
            v-loading="loading"
            stripe
            show-overflow-tooltip
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column
              label="材料名称"
              width="100"
              v-if="radioUserType == 0"
              show-overflow-tooltip
            >
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <div :class="{ textEllipsis: textElipsisValue }">
                    {{ scope.row.material.name }}
                  </div>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="材料位置" width="170" show-overflow-tooltip>
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <div :class="{ textEllipsis: textElipsisValue }">
                    {{ scope.row.material.location }}
                  </div>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="编号" width="100" show-overflow-tooltip>
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <span style="margin-left: 10px">{{
                    scope.row.material.itemMark
                  }}</span>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="技术要求" show-overflow-tooltip>
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <div :class="{ textEllipsis: textElipsisValue }">
                    {{ scope.row.material.technology }}
                  </div>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="施工要求" width="200" show-overflow-tooltip>
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <div :class="{ textEllipsis: textElipsisValue }">
                    {{ scope.row.material.installation }}
                  </div>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="品牌" width="200" show-overflow-tooltip>
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <!--公共品牌-->
                  <span
                    v-for="(brandPublicViewItem, brandPublicIndex) in scope.row
                      .projectMaterialBrandPublicViewList"
                    :key="brandPublicIndex"
                  >
                    <el-tag type="success">{{
                      brandPublicViewItem.brandPublicView.brandView.brand.name
                    }}</el-tag>
                  </span>
                  <!--项目私有品牌-->
                  <span
                    v-for="(brandPrivateViewItem, brandPrivateIndex) in scope
                      .row.projectMaterialBrandPrivateViewList"
                    :key="brandPrivateIndex"
                  >
                    <el-tag type="info">{{
                      brandPrivateViewItem.projectBrandView.brandView.brand.name
                    }}</el-tag>
                  </span>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <el-pagination
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
        />
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel">取消</el-button>
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.page-class {
  padding: 10px;
}

.top-toolbar {
  display: flex;
  margin: 0 10px;
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

::v-deep .el-table .cell {
  white-space: pre-line;
}
</style>
