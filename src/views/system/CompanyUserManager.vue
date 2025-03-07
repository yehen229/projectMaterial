<script setup lang="ts">
/**
 * 对用户进行管理
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import {
  SortUp,
  SortDown,
  Search,
  Plus,
  Download,
  Upload,
} from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";

import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserForm,
  IServerCompanyUserView,
} from "@/server/types/system/company";

import {
  serverCompanyUserAdd,
  serverCompanyUserAddByForm,
  serverCompanyUserDelete,
  serverCompanyUserDeleteById,
  serverCompanyUserUpdate,
  serverCompanyUserUpdateByForm,
  serverGetCompanyUserById,
  serverGetCompanyUserListByCompanyId,
  serverGetCompanyUserListByUserId,
  serverGetCompanyUserPage,
  serverGetCompanyUserPageView,
  serverGetCompanyUserPageViewByUserName,
  serverGetCompanyUserPageViewByProjectName,
  serverGetCompanyUserPageViewByCompanyName,
  serverDownloadAllCompanyUser,
  serverDownloadCompanyUserByUserNamer,
  serverDownloadCompanyUserByProjectName,
  serverDownloadCompanyUserByCompanyName,
  serverGetCompanyUserPageViewByCompanyType,
  serverGetCompanyUserPageViewByCompanyNameAndType,
} from "@/server/system/companyuser";

import { IServerResponseData, IServerPage } from "@/server/types/System";

import {
  serverLogin,
  serverLoginOut,
  serverGetPublicKey,
  serverGetCaptchaJpg,
  serverUserAdd,
  serverUserDelete,
  serverUserUpdate,
  serverUserUpdateOwnInfo,
  serverUserUpdateOwnPwd,
  serverUserResetPwd,
  serverGetUserByUserName,
  serverGetUserByUserId,
  serverGetUserPage,
} from "@/server/account/User";

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
import { useActiveMenuStore } from "@/store/activeMenu";

import NewCompanyUserDialog from "@/components/system/companyuser/NewCompanyUserDialog.vue";
import UpdateCompanyUserDialog from "@/components/system/companyuser/UpdateCompanyUserDialog.vue";
import UploadExcelCompanyUserDialog from "@/components/system/companyuser/UploadExcelCompanyUserDialog.vue";

const router = useRouter();
const activeMenuStore = useActiveMenuStore();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示
const formLabelWidth = "140px";
const ruleFormRef = ref<FormInstance>();
const form = reactive({
  id: "",
  name: "",
  note: "",
});
const loading = ref(false);

const updateCompanyUserView = ref<IServerCompanyUserView>();

const searchText = ref("");
const searchSelect = ref("0");

const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
});

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const companyUserViewPage = ref<IServerPage<IServerCompanyUserView> | null>(
  null
);

const radioUserType = ref(0);

const tableData = computed(() => {
  return companyUserViewPage.value?.result;
});

const totalCount = computed(() => {
  return Number(companyUserViewPage.value?.totalCount ?? 0);
});

onMounted(async () => {
  await getCompanyUserPageViewFromSever();
});

const getCompanyUserPageViewFromSever = async () => {
  loading.value = true;
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);
    if (searchSelect.value == "0") {
      //用户名称
      const ret = await serverGetCompanyUserPageViewByUserName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //项目名称
      const ret = await serverGetCompanyUserPageViewByProjectName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "2") {
      //单位名称
      const ret = await serverGetCompanyUserPageViewByCompanyName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );

      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetCompanyUserPageView(
      pageNo.value,
      pageSize.value
    );

    if (ret && ret.code == 200) {
      companyUserViewPage.value = ret.data;
    }
  }

  loading.value = false;
};

/**
 * 向前翻页
 * @param value
 */
const onPagePrevClick = (value: number) => {};

/**
 * 向后翻页
 * @param value
 */
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getCompanyUserPageViewFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getCompanyUserPageViewFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getCompanyUserPageViewFromSever();
};

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true;
};

/**
 * 用户在“新增”对话框中点击了“取消”按钮
 */
const onNewCompanyUserDialogCancel = async () => {
  dialogFormNewVisible.value = false;
};

/**
 * 用户在“新增”对话框中点击了“确认”按钮
 */
const onNewCompanyUserDialogOk = async (form: IServerCompanyUserForm) => {
  loading.value = true;
  //添加公司用户
  await serverCompanyUserAddByForm(form);

  //刷新表格
  await getCompanyUserPageViewFromSever();

  //关闭对话框
  dialogFormNewVisible.value = false;
  loading.value = false;
};

/**
 * 用户在“修改”对话框中点击了“取消”按钮
 */
const onUpdateCompanyUserDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

/**
 * 用户在“修改”对话框中点击了“确认”按钮
 */
const onUpdateCompanyUserDialogOk = async (form: IServerCompanyUserForm) => {
  loading.value = true;
  //添加公司用户
  await serverCompanyUserUpdateByForm(form);

  //刷新表格
  await getCompanyUserPageViewFromSever();

  //关闭对话框
  dialogFormUpdateVisible.value = false;
  loading.value = false;
};

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onRowEditButtonClick = async (
  index: number,
  row: IServerCompanyUserView
) => {
  dialogFormUpdateVisible.value = true;
  updateCompanyUserView.value = row;
};

/**
 * 点击删除按钮，删除内容
 * @param index
 * @param row
 */
const onRowDeleteButtonClick = async (
  index: number,
  row: IServerCompanyUserView
) => {
  console.log(index, row);

  ElMessageBox.confirm("是否真的删除用户？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverUserDelete(row.user);

      //刷新表格
      await getCompanyUserPageViewFromSever();

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

/**
 * 重置用户密码
 * @param index
 * @param row
 */
const onRowResetPasswordButtonClick = async (
  index: number,
  row: IServerCompanyUserView
) => {
  ElMessageBox.confirm('是否真的重置密码为"123456"？', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverUserResetPwd(row.user);

      ElMessage({
        type: "success",
        message: "完成重置密码",
      });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "重置密码失败",
      });
    });
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

const tableIndex = (index: number) => {
  return (pageNo.value - 1) * pageSize.value + index + 1;
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

  // if (search) {
  //   if (searchSelect.value == "0") {
  //     //用户名称
  //     const ret = await serverDownloadCompanyUserByUserNamer(
  //       searchText.value,
  //       downloadFilename
  //     );
  //   } else if (searchSelect.value == "1") {
  //     //项目名称
  //     const ret = await serverDownloadCompanyUserByProjectName(
  //       searchText.value,
  //       downloadFilename
  //     );
  //   } else if (searchSelect.value == "2") {
  //     //单位名称
  //     const ret = await serverDownloadCompanyUserByCompanyName(
  //       searchText.value,
  //       downloadFilename
  //     );
  //   }
  // } else {
    await serverDownloadAllCompanyUser(downloadFilename);
  // }

  loading.value = false;
};

const goBack = () => {
  history.back();
};
</script>

<template>
  <!--新增对话框-->
  <NewCompanyUserDialog
    :dialogVisible="dialogFormNewVisible"
    @onDilalogCancel="onNewCompanyUserDialogCancel"
    @onDilalogOk="onNewCompanyUserDialogOk"
  ></NewCompanyUserDialog>

  <!--修改对话框-->
  <UpdateCompanyUserDialog
    :dialogVisible="dialogFormUpdateVisible"
    :companyUserView="updateCompanyUserView"
    @onDilalogCancel="onUpdateCompanyUserDialogCancel"
    @onDilalogOk="onUpdateCompanyUserDialogOk"
  ></UpdateCompanyUserDialog>

  <!--上传Excel文件对话框-->
  <UploadExcelCompanyUserDialog
    :dialogVisible="dialogFormExcelVisible"
    @onDilalogCancel="onExcelUploadDialogCancel"
    @onDilalogOk="onExcelUploadDialogOk"
  ></UploadExcelCompanyUserDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">用户管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
          新增用户
        </el-button>
        <!-- <el-button :icon="Upload" @click="onExcelUploadButtonClick">
          导入用户（Excel）
        </el-button> -->
        <el-button :icon="Download" @click="onDownloadExcelButtonClick">
          导出用户（Excel）
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
              <el-option label="用户名称" value="0" />
              <el-option label="项目名称" value="1" />
              <el-option label="公司名称" value="2" />
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
        <!--显示用户名称-->
        <el-table-column label="用户名称" width="120">
          <template #default="scope">
            {{ scope.row.user.realName }}
          </template>
        </el-table-column>

        <!--显示电话-->
        <el-table-column label="电话" width="160">
          <template #default="scope">
            {{ scope.row.user.tel }}
          </template>
        </el-table-column>

        <!--显示邮箱-->
        <el-table-column label="邮箱" width="200">
          <template #default="scope">
            {{ scope.row.user.email }}
          </template>
        </el-table-column>

        <!--显示单位名称-->
        <el-table-column label="单位" width="400">
          <template #default="scope">
            {{ scope.row.company.name }}
          </template>
        </el-table-column>

        <!--显示项目名称、项目角色等-->
        <el-table-column label="项目" width="400">
          <template #default="scope">
            <div
              v-for="(projectUserView, index) in scope.row.projectUserViewList"
              style="display: flex"
            >
              <div>{{ projectUserView.project.name }}</div>
              <div v-if="projectUserView.role.name == 'Employee'">
                <el-tag type="info">项目员工</el-tag>
              </div>
              <div v-else-if="projectUserView.role.name == 'Manager'">
                <el-tag type="primary">项目经理</el-tag>
              </div>
              <div v-else-if="projectUserView.role.name == 'Admin'">管理员</div>
            </div>
          </template>
        </el-table-column>

        <!--操作列-->
        <el-table-column label="Operations" width="240">
          <template #default="scope">
            <el-button
              size="small"
              @click="onRowEditButtonClick(scope.$index, scope.row)"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="warning"
              @click="onRowResetPasswordButtonClick(scope.$index, scope.row)"
            >
              重置密码
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
