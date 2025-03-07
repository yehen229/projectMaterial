<script setup lang="ts">
/**
 * 对单位进行管理 *
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import {
  IServerMaterial,
  IServerMaterialForm,
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
  IServerMaterialView,
} from "@/server/types/system/material";

import {
  serverMaterialAdd,
  serverMaterialAddForm,
  serverMaterialDelete,
  serverMaterialDeleteById,
  serverMaterialUpdate,
  serverGetMaterialById,
  serverGetMaterialPage,
  serverGetMaterialPageView,
  serverGetMaterialPageViewByNameAndBindType,
  serverGetMaterialPageViewByLocationAndBindType,
  serverGetMaterialPageViewByItemMarkAndBindType,
  serverGetMaterialPageViewByTechnologyAndBindType,
  serverGetMaterialPageViewByInstallationAndBindType,
  serverGetMaterialPageViewByBrandAndBindType,
  serverGetMaterialPageViewByClassifySectionId,
} from "@/server/system/material";

import { serverGetMaterialPhotoFileById } from "@/server/system/materialphoto";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewMaterialDialog from "@/components/system/meterial/NewMaterialDialog.vue";
import UpdateMaterialDialog from "@/components/system/meterial/UpdateMaterialDialog.vue";

import MaterialPhoto from "@/components/system/meterial/MaterialPhoto.vue";

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示

const loading = ref(false);
const router = useRouter();
const updateMaterialView = ref<IServerMaterialView>();

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const materialViewPageData = ref<IServerPage<IServerMaterialView>>();

const radioUserType = ref(0);

onMounted(async () => {
  await getMaterialFromSever();
});

const getMaterialFromSever = async () => {
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //名字
      console.log(search);
      const ret = await serverGetMaterialPageViewByNameAndBindType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        materialViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //位置
      const ret = await serverGetMaterialPageViewByLocationAndBindType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        materialViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "2") {
      //编号
      const ret = await serverGetMaterialPageViewByItemMarkAndBindType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        materialViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "3") {
      //类别
      const ret = await serverGetMaterialPageViewByTechnologyAndBindType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        materialViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "4") {
      //类别
      const ret = await serverGetMaterialPageViewByInstallationAndBindType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        materialViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "5") {
      //类别
      const ret = await serverGetMaterialPageViewByBrandAndBindType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        materialViewPageData.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetMaterialPageView(pageNo.value, pageSize.value);
    console.log(ret);
    if (ret && ret.code == 200) {
      materialViewPageData.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return materialViewPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(materialViewPageData.value?.totalCount ?? 0);
});

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true;
};

/**
 * 用户在“新增”对话框中点击了“取消”按钮
 */
const onNewMaterialDialogCancel = async () => {
  dialogFormNewVisible.value = false;
};

/**
 * 用户在“新增”对话框中点击了“确认”按钮
 */
const onNewMaterialDialogOk = async (material: IServerMaterial) => {
  await getMaterialFromSever();
  dialogFormNewVisible.value = false;
};

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onRowEditButtonClick = async (
  index: number,
  row: IServerMaterialView
) => {
  updateMaterialView.value = row;
  dialogFormUpdateVisible.value = true;
};

const onUpdateMaterialDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

const onUpdateMaterialDialogOk = async (materialView: IServerMaterialView) => {
  console.log(materialView);

  await getMaterialFromSever();
  dialogFormUpdateVisible.value = false;
};

/**
 * 点击删除按钮，删除内容
 * @param index
 * @param row
 */
const onRowDeleteButtonClick = async (
  index: number,
  materialView: IServerMaterialView
) => {
  //console.log(index, row);

  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverMaterialDelete(materialView.material);
      await getMaterialFromSever();
      ElMessage({
        type: "success",
        message: "完成删除",
      });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "删除失败",
      });
    });
};

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getMaterialFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getMaterialFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getMaterialFromSever();
};

const goBack = () => {
  history.back();
};

const getImgUrl = async (photoItem: IServerMaterialPhotoView) => {
  let res = await serverGetMaterialPhotoFileById(photoItem.materialPhoto.id);
  console.log(res);

  if (res && res.code == 200) return res.data;
  return "";
};

const textElipsisValue = ref(false);
</script>

<template>
  <!--新增对话框-->
  <NewMaterialDialog
    :dialogVisible="dialogFormNewVisible"
    @onDilalogCancel="onNewMaterialDialogCancel"
    @onDilalogOk="onNewMaterialDialogOk"
  ></NewMaterialDialog>

  <!--修改对话框-->
  <UpdateMaterialDialog
    :dialogVisible="dialogFormUpdateVisible"
    :materialView="updateMaterialView"
    @onDilalogCancel="onUpdateMaterialDialogCancel"
    @onDilalogOk="onUpdateMaterialDialogOk"
  ></UpdateMaterialDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">材料管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
          新增材料
        </el-button>
      </div>

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
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
          active-text="自动调整高度"
          inactive-text="显示全部内容"
        />
      </div>
    </div>

    <!--显示内容-->
    <div class="project-container">
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
        <el-col :span="2"> 样本照片</el-col>
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
          <div style="display: flex; align-items: center">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.name }}
            </div>
          </div>
        </el-col>

        <!--材料位置-->
        <el-col :span="3">
          <div style="display: flex; align-items: center">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.location }}
            </div>
          </div>
        </el-col>

        <!--编号-->
        <el-col :span="2">
          <div style="display: flex; align-items: center">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.itemMark }}
            </div>
          </div>
        </el-col>

        <!--技术要求-->
        <el-col :span="6">
          <div style="display: flex; align-items: center">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.technology }}
            </div>
          </div>
        </el-col>

        <!--施工要求-->
        <el-col :span="4">
          <div style="display: flex; align-items: center">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ projectMaterialViewItem.material.installation }}
            </div>
          </div>
        </el-col>

        <!--品牌-->
        <el-col :span="2">
          <div style="display: flex; flex-wrap: wrap; align-items: center">
            <div
              v-for="(
                brandItem, brandIndex
              ) in projectMaterialViewItem.materialBrandViewList"
              :key="brandIndex"
            >
              <el-tag :key="brandIndex" style="margin-right: 10px"
                >{{ brandItem.brand.name }}({{
                  brandItem.brand.position
                }})</el-tag
              >
            </div>
          </div>
        </el-col>
        <!--样本照片-->

        <el-col :span="2">
          <div style="display: flex; align-items: center">
            <div
              v-for="(
                photoItem, photoIndex
              ) in projectMaterialViewItem.materialPhotoViewList"
              :key="photoIndex"
            >
              <MaterialPhoto :photoItem="photoItem"></MaterialPhoto>
            </div>
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
                onRowEditButtonClick(
                  projectMaterialViewIndex,
                  projectMaterialViewItem
                )
              "
            >
              编辑
            </el-button>

            <el-button
              size="small"
              type="danger"
              style="margin-left: 0px; margin-top: 5px"
              @click="
                onRowDeleteButtonClick(
                  projectMaterialViewIndex,
                  projectMaterialViewItem
                )
              "
            >
              删除
            </el-button>
          </div>
        </el-col>
      </el-row>
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
</template>

<style scoped>
@import url("@/assets/css/basic.css");
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
