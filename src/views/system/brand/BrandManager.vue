<script setup lang="ts">
/**
 * 对公共库中的品牌进行管理 *
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

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

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

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

import NewBrandDialog from "@/components/system/brand/NewBrandDialog.vue";
import UpdateBrandDialog from "@/components/system/brand/UpdateBrandDialog.vue";
import UploadExcelBrandDialog from "@/components/system/brand/UploadExcelBrandDialog.vue";

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示

const loading = ref(false);
const router = useRouter();
const updateBrandPublicView = ref<IServerBrandPublicView>();

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const brandPublicPageData = ref<IServerPage<IServerBrandPublicView> | null>(
  null
);

const radioUserType = ref(0);

onMounted(async () => {
  await getBrandFromSever();
});

const getBrandFromSever = async () => {
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //品牌名称
      console.log(search);
      const ret = await serverGetBrandPublicPageViewByBrandName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        brandPublicPageData.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //品牌定位
      const ret = await serverGetBrandPublicPageViewByBrandPosition(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        brandPublicPageData.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetBrandPublicPageView(
      pageNo.value,
      pageSize.value
    );
    //console.log(ret);
    if (ret && ret.code == 200) {
      brandPublicPageData.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return brandPublicPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(brandPublicPageData.value?.totalCount ?? 0);
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
const onNewBrandDialogCancel = async () => {
  dialogFormNewVisible.value = false;
};

/**
 * 用户在“新增”对话框中点击了“确认”按钮
 */
const onNewBrandDialogOk = async (brand: IServerBrand) => {
  console.log(brand);
  await serverBrandPublicAdd(brand);
  await getBrandFromSever();
  dialogFormNewVisible.value = false;
};

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onRowEditButtonClick = async (
  index: number,
  row: IServerBrandPublicView
) => {
  updateBrandPublicView.value = row;
  dialogFormUpdateVisible.value = true;
};

const onUpdateBrandDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

const onUpdateBrandDialogOk = async (brand: IServerBrand) => {
  await serverBrandPublicUpdate(brand);
  await getBrandFromSever();
  dialogFormUpdateVisible.value = false;
};

/**
 * 点击删除按钮，删除内容
 * @param index
 * @param row
 */
const onRowDeleteButtonClick = async (
  index: number,
  row: IServerBrandPublicView
) => {
  console.log(index, row);

  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverBrandPublicDelete(row.brandPublic);
      await getBrandFromSever();
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
  await getBrandFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getBrandFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getBrandFromSever();
};

const onExcelUploadDialogCancel = () => {
  dialogFormExcelVisible.value = false;
};

const onExcelUploadDialogOk = () => {
  dialogFormExcelVisible.value = false;
};

/**
 * 上传Excel文件，导入用户
 */
const onExcelUploadButtonClick = () => {
  dialogFormExcelVisible.value = true;
};

/**
 * 下载用户名单
 * @param index
 * @param row
 */
const onDownloadExcelButtonClick = async () => {
  const downloadFilename = "用户名单";

  loading.value = true;
  let search = searchText.value.trim();

  if (search) {
    if (searchSelect.value == "用户名称") {
      //用户名称
      const ret = await serverDownloadCompanyUserByUserNamer(
        searchText.value,
        downloadFilename
      );
    } else if (searchSelect.value == "项目名称") {
      //项目名称
      const ret = await serverDownloadCompanyUserByProjectName(
        searchText.value,
        downloadFilename
      );
    } else if (searchSelect.value == "单位名称") {
      //单位名称
      const ret = await serverDownloadCompanyUserByCompanyName(
        searchText.value,
        downloadFilename
      );
    }
  } else {
    await serverDownloadAllCompanyUser(downloadFilename);
  }

  loading.value = false;
};

const goBack = () => {
  history.back();
};
</script>

<template>
  <!--新增对话框-->
  <NewBrandDialog
    :dialogVisible="dialogFormNewVisible"
    @onDilalogCancel="onNewBrandDialogCancel"
    @onDilalogOk="onNewBrandDialogOk"
  ></NewBrandDialog>

  <!--修改对话框-->
  <UpdateBrandDialog
    :dialogVisible="dialogFormUpdateVisible"
    :brandPublicView="updateBrandPublicView"
    @onDilalogCancel="onUpdateBrandDialogCancel"
    @onDilalogOk="onUpdateBrandDialogOk"
  ></UpdateBrandDialog>

  <!--上传Excel文件对话框-->
  <UploadExcelBrandDialog
    :dialogVisible="dialogFormExcelVisible"
    @onDilalogCancel="onExcelUploadDialogCancel"
    @onDilalogOk="onExcelUploadDialogOk"
  ></UploadExcelBrandDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">公共品牌管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
          新增品牌
        </el-button>
        <el-button :icon="Upload" @click="onExcelUploadButtonClick">
          导入品牌（Excel）
        </el-button>
        <el-button :icon="Download" @click="onDownloadExcelButtonClick">
          导出品牌（Excel）
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
              <el-option label="品牌名称" value="0" />
              <el-option label="定位" value="1" />
            </el-select>
          </template>
          <template #append>
            <el-button :icon="Search" @click="onSearchClick" />
          </template>
        </el-input>
      </div>
    </div>

    <!--显示内容-->
    <div class="project-container">
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        stripe
      >
        <el-table-column
          label="大类-专业"
          width="200"
          v-if="radioUserType == 0"
        >
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span>{{
                scope.row.brandView.materialClassifySectionView
                  .materialClassifyDivision.name
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="中类-材料分类" width="200">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span>{{
                scope.row.brandView.materialClassifySectionView
                  .materialClassifyGroup.name
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="小类-材料名称" width="200">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span>{{
                scope.row.brandView.materialClassifySectionView
                  .materialClassifySection.name
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="定位" width="100">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span>{{ scope.row.brandView.brand.position }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="品牌" width="200">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span>{{ scope.row.brandView.brand.name }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="240">
          <template #default="scope">
            <el-button
              size="small"
              @click="onRowEditButtonClick(scope.$index, scope.row)"
            >
              编辑
            </el-button>

            <el-button
              size="small"
              type="danger"
              @click="onRowDeleteButtonClick(scope.$index, scope.row)"
            >
              删除
            </el-button>
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
</style>
