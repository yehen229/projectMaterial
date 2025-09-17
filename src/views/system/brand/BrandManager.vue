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
import axios from 'axios';
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
  // brandPublicPageData.value = [];
  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //品牌名称
      const ret = await serverGetBrandPublicPageViewByBrandName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        console.log("这是我要打印的东西"+JSON.stringify(ret.data, null, 2));
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
  console.log(brandPublicPageData.value);
  brandPublicPageData.value
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
 * 点击“下载Excel模板”按钮，下载Excel文件
 */
 const onExcelDownloadButtonClick = async () => {
  let a = document.createElement("a");
  a.href = "/static/公有品牌模板.xlsx";
  a.download = "公有品牌模板.xlsx";
  a.style.display = "none";
  document.body.appendChild(a);
  a.click();
  a.remove();
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
      <div style="padding: 0 10px;">
        <el-button-group style="display: flex; gap: 15px;">
        <el-button  type="primary"  style="width: 80px;"@click="onNewButtonClick">
          新增品牌
        </el-button>
     <el-upload  class="upload-demo"
    :http-request="handleUpload"
    :show-file-list="false"
    :on-success="successUpload"
    accept=".xlsx,.xls"
  >
    <el-button type="primary" style="width: 80px;">批量导入</el-button>
  </el-upload>
         <el-button type="danger" style="width: 80px;" @click="handledownload">批量导出</el-button>
         <el-button style="width: 80px;" @click="onExcelDownloadButtonClick">模板</el-button>
         </el-button-group>
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
                scope.row.brandView.materialClassifySectionView?.materialClassifyDivision.name
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
        <el-table-column label="厂家" width="200">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span v-if="scope.row.company != null">{{ scope.row.company.name }}</span>
              <span v-if="scope.row.company == null">无</span>
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
      :hide-on-single-page="false"
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

<script lang="ts">
import { serverBrandExcelAdd } from '@/server/system/brandpublic';
import { serverBrandExcelDown } from '@/server/system/brandpublic';
export default {
  methods: {
    async handleUpload(params: any) {
      const file = params.file;
      const formData = new FormData();
      formData.append('file', file);

      try {
        const res = await serverBrandExcelAdd(formData);
        this.$message.success('文件上传成功');
        params.onSuccess?.(res);
      } catch (err) {
        this.$message.error('文件上传失败');
        params.onError?.(err);
      }
    },
    
     async handledownload(params: any) {
      try {
        console.log("开始try了")
        const blob = await serverBrandExcelDown();
        if (!(blob instanceof Blob)) {
            console.log('获取到的对象不是Blob类型');
            
        }
            // 创建一个隐藏的<a>元素
            const a = document.createElement('a');
            console.log("这是bobl"+blob);
            a.href = URL.createObjectURL(blob); // 创建一个指向blob数据的URL
            a.download = '品牌列表.xlsx'; // 设置下载文件的名称
            a.style.display = 'none'; // 隐藏<a>元素，不显示在页面上

            // 将<a>元素添加到body中
            document.body.appendChild(a);

            // 触发<a>元素的点击事件来开始下载
            a.click();
        console.log("触发下载了========")
            // 下载完成后移除<a>元素
            window.setTimeout(() => {
              document.body.removeChild(a);
              URL.revokeObjectURL(a.href); // 释放创建的URL对象
              params?.onSuccess?.(blob); // 调用成功回调
            }, 0);
          } catch (err) {
            console.error("下载失败:", err);
            params?.onError?.(err); // 调用错误回调
          }

    },
  },
};
</script>
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
