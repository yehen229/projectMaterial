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

import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
} from "@/server/types/system/company";

import {
  serverCompanyAdd,
  serverCompanyDelete,
  serverCompanyDeleteById,
  serverCompanyUpdate,
  serverGetCompanyUserById,
  serverGetAllCompanyList,
  serverGetAllCompanyListByCompanyType,
  serverGetCompanyPage,
  serverGetCompanyPageByCompanyName,
  serverGetCompanyPageByCompanyType,
} from "@/server/system/company";

//服务器返回到前端的类型
import { IServerResponseData, IServerPage } from "@/server/types/System";

import {
  setUserCookies,
  getUserName,
  getUserID,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import NewCompanyDialog from "@/components/system/company/NewCompanyDialog.vue";
import UpdateUserDialog from "@/components/system/company/UpdateCompanyDialog.vue";

const router = useRouter();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const formLabelWidth = "140px";
const ruleFormRef = ref<FormInstance>();
const form = reactive({
  id: "",
  name: "",
  note: "",
});
const loading = ref(false);

const updateCompany = ref<IServerCompany>({
  id: "", //id,主键
  name: "", //单位名称单位名称
  companyType: "", //company_type,单位类型：设计单位、设计部、工程部、监理单位、总包单位等单位类型：设计单位、设计部、工程部、监理单位、总包单位等
  note: "", //note,备注备注
  deletedAt: new Date(),
});

const searchText = ref("");
const searchSelect = ref("1");

const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入公司名称", trigger: "blur" }],
});

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const companyPageData = ref<IServerPage<IServerCompany> | null>(null);

const radioUserType = ref(0);

onMounted(async () => {
  await getCompanyFromSever();
});

const getCompanyFromSever = async () => {
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //单位类型
      console.log(search);
      const ret = await serverGetCompanyPageByCompanyName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        companyPageData.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //单位名称
      const ret = await serverGetCompanyPageByCompanyType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        companyPageData.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetCompanyPage(pageNo.value, pageSize.value);
    //console.log(ret);
    if (ret && ret.code == 200) {
      companyPageData.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return companyPageData.value?.result;
});

const totalCount = computed(() => {
  return Number(companyPageData.value?.totalCount ?? 0);
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
const onNewCompanyDialogCancel = async () => {
  dialogFormNewVisible.value = false;
};

/**
 * 用户在“新增”对话框中点击了“确认”按钮
 */
const onNewCompanyDialogOk = async (company: IServerCompany) => {
  let ret = await serverCompanyAdd(company);
  if (ret && ret.code == 500) {
    ElMessage({
    type: "error",
    message: "此单位已经存在",
  });
  } else {
    ElMessage({
    type: "success",
    message: "完成新增",
  });
  }
  await getCompanyFromSever();
  dialogFormNewVisible.value = false;
};

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onRowEditButtonClick = async (index: number, row: IServerCompany) => {
  updateCompany.value = row;
  dialogFormUpdateVisible.value = true;
};

const onUpdateCompanyDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

const onUpdateCompanyDialogOk = async (company: IServerCompany) => {
  console.log(company);
  let ret = await serverCompanyUpdate(company);
  if (ret && ret.code == 500) {
    ElMessage({
    type: "error",
    message: "此单位已经存在",
  });
  } else {
    ElMessage({
    type: "success",
    message: "完成新增",
  });
  }
  await getCompanyFromSever();
  dialogFormUpdateVisible.value = false;
};

/**
 * 点击删除按钮，删除内容
 * @param index
 * @param row
 */
const onRowDeleteButtonClick = async (index: number, row: IServerCompany) => {
  console.log(index, row);

  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverCompanyDelete(row);
      await getCompanyFromSever();
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
  await getCompanyFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getCompanyFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getCompanyFromSever();
};

const goBack = () => {
  history.back();
};
</script>

<template>
  <!--新增对话框-->
  <NewCompanyDialog
    :dialogVisible="dialogFormNewVisible"
    @onDilalogCancel="onNewCompanyDialogCancel"
    @onDilalogOk="onNewCompanyDialogOk"
  ></NewCompanyDialog>

  <!--修改对话框-->
  <UpdateUserDialog
    :dialogVisible="dialogFormUpdateVisible"
    :company="updateCompany"
    @onDilalogCancel="onUpdateCompanyDialogCancel"
    @onDilalogOk="onUpdateCompanyDialogOk"
  ></UpdateUserDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">单位管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
          新增
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
              <el-option label="单位名称" value="0" />
              <el-option label="单位类型" value="1" />
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
        <el-table-column label="单位名称" v-if="radioUserType == 0">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{ scope.row.name }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="单位类型" width="100">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{ scope.row.companyType }}</span>
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
