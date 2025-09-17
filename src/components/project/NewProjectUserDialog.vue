<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import { ElTable } from "element-plus";
import type { Action } from "element-plus";

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
  serverGetCompanyUserPageViewByCompanyId,
  serverGetCompanyUserPageViewByCompanyNameAndType,
} from "@/server/system/companyuser";

import {
  serverGetDesignDepartmentManagerCandidatesViewPageByProjectId,
  serverGetDesignDepartmentEmployeeCandidatesViewPageByProjectId,
  serverGetEngineeringDepartmentManagerCandidatesViewPageByProjectId,
  serverGetEngineeringDepartmentEmployeeCandidatesViewPageByProjectId,
  serverGetExistsDesignDepartmentManagerByProjectId,
  serverGetExistsEngineeringDepartmentManagerByProjectId,
  serverGetDesignDepartmentManagerByProjectId,
  serverGetEngineeringDepartmentManagerByProjectId,
} from "@/server/project/projectuser";

//服务器返回到前端的类型
import { IServerResponseData, IServerPage } from "@/server/types/System";
import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
} from "@/server/types/project/project";
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

const router = useRouter();

interface Props {
  dialogVisible: boolean; //对话框是否可见
  projectView: IServerProjectView;
  companyId: string;
  type: number;
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
  type: 0,
});

const form = reactive({
  role: "项目员工", //
  users: [], //用户
});

const rules = reactive<FormRules>({
  role: [{ required: true, message: "请选择用户角色", trigger: "blur" }],
});

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const companyUserViewPage = ref<IServerPage<IServerCompanyUserView> | null>(
  null
);

const multipleTableRef = ref<InstanceType<typeof ElTable>>();
const multipleSelection = ref<IServerCompanyUserView[]>([]);
const toggleSelection = (rows?: IServerCompanyUserView[]) => {
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
const handleSelectionChange = (val: IServerCompanyUserView[]) => {
  multipleSelection.value = val;
};

const tableData = computed(() => {
  return companyUserViewPage.value?.result;
});

const totalCount = computed(() => {
  return Number(companyUserViewPage.value?.totalCount ?? 0);
});

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getCompanyUserFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getCompanyUserFromSever();
};

const handleRadioChange = async (
  val: string | number | boolean | undefined
) => {
  console.log(val);

  await getCompanyUserFromSever();
};

const getCompanyUserFromSever = async () => {
  console.log(props.projectView);
  if (props.type == 0) {
    //设计单位
    const ret = await serverGetCompanyUserPageViewByCompanyId(
      props.companyId,
      pageNo.value,
      pageSize.value
    );
    console.log(ret);
    if (ret && ret.code == 200) {
      companyUserViewPage.value = ret.data;
    }
  } else if (props.type == 1) {
    if (form.role === "项目经理") {
      const ret =
        await serverGetDesignDepartmentManagerCandidatesViewPageByProjectId(
          props.projectView.project.id,
          pageNo.value,
          pageSize.value
        );
      console.log(ret);
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    } else if (form.role === "项目员工") {
      const ret =
        await serverGetDesignDepartmentEmployeeCandidatesViewPageByProjectId(
          props.projectView.project.id,
          pageNo.value,
          pageSize.value
        );
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    } else {
      //设计部全部员工
      const ret = await serverGetCompanyUserPageViewByCompanyNameAndType(
        "设计部",
        "建设单位",
        pageNo.value,
        pageSize.value
      );
      console.log(ret);
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    }
  } else if (props.type == 2) {
    if (form.role === "项目经理") {
      const ret =
        await serverGetEngineeringDepartmentManagerCandidatesViewPageByProjectId(
          props.projectView.project.id,
          pageNo.value,
          pageSize.value
        );
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    } else if (form.role === "项目员工") {
      const ret =
        await serverGetEngineeringDepartmentEmployeeCandidatesViewPageByProjectId(
          props.projectView.project.id,
          pageNo.value,
          pageSize.value
        );
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    } else {
      //工程部
      const ret = await serverGetCompanyUserPageViewByCompanyNameAndType(
        "工程部",
        "建设单位",
        pageNo.value,
        pageSize.value
      );
      console.log(ret);
      if (ret && ret.code == 200) {
        companyUserViewPage.value = ret.data;
      }
    }
  } else if (props.type == 3) {
    //监理单位
    const ret = await serverGetCompanyUserPageViewByCompanyId(
      props.projectView.companySupervision.id,
      pageNo.value,
      pageSize.value
    );
    console.log(ret);
    if (ret && ret.code == 200) {
      companyUserViewPage.value = ret.data;
    }
  } else if (props.type == 4) {
    //总包单位
    const ret = await serverGetCompanyUserPageViewByCompanyId(
      props.projectView.companyGeneralContract.id,
      pageNo.value,
      pageSize.value
    );
    console.log(ret);
    if (ret && ret.code == 200) {
      companyUserViewPage.value = ret.data;
    }
  }
};

//event
const emit = defineEmits<{
  (
    e: "onDilalogOk",
    projectView: IServerProjectView,
    val: IServerCompanyUserView[],
    type: number,
    role: string
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

const existManager = ref(true);

const onOpenDialog = async () => {
  console.log(props);
  existManager.value = true;
  if (props.type == 1) {
    //设计部
    const ret = await serverGetExistsDesignDepartmentManagerByProjectId(
      props.projectView.project.id
    );
    if (ret && ret.code == 200) {
      existManager.value = ret.data;
    }

    if (existManager.value) form.role = "项目员工";
    console.log(form.role);
  } else if (props.type == 2) {
    //工程部
    const ret = await serverGetExistsEngineeringDepartmentManagerByProjectId(
      props.projectView.project.id
    );
    if (ret && ret.code == 200) {
      existManager.value = ret.data;
    }
    if (existManager.value) form.role = "项目员工";
    console.log(form.role);
  }
  await getCompanyUserFromSever();
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (props.type == 0 || props.type == 3 || props.type == 4)
    form.role = "项目员工";

  if (!form.role || form.role.length == 0) {
    ElMessageBox.alert("用户角色", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  if (multipleSelection.value.length == 0) {
    if (form.role === "项目经理")
      ElMessageBox.alert("用户为空，必须选择一个用户为项目经理", "提示", {
        confirmButtonText: "确定",
      });
    else
      ElMessageBox.alert("用户为空，必须选择一个或多个用户为项目员工", "提示", {
        confirmButtonText: "确定",
      });

    return;
  }

  if (form.role === "项目经理" && multipleSelection.value.length > 1) {
    ElMessageBox.alert("只能选择一个用户为项目经理", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  emit(
    "onDilalogOk",
    props.projectView,
    multipleSelection.value,
    props.type,
    form.role
  );
};

const onCancel = () => {
  emit("onDilalogCancel");
};

const dialogTitle = computed(() => {
  return props.type == 0
    ? "添加设计单位员工"
    : props.type == 1
    ? "添加设计部项目经理和项目员工"
    : props.type == 2
    ? "添加工程部项目经理和项目员工"
    : props.type == 3
    ? "添加监理单位员工"
    : props.type == 4
    ? "添加总包单位员工"
    : "";
});
</script>

<template>
  <div>
    <el-dialog
      :title="dialogTitle"
      v-model="dialogFormVisible"
      :before-close="handleClose"
      width="800px"
      @open="onOpenDialog"
      draggable
    >
      <el-form
        label-width="100px"
        :model="form"
        :rules="rules"
        ref="ruleFormRef"
      >
        <!--只有设计部和工程部才能添加项目经理和项目员工，其它类型（设计单位、监理单位、总包单位）只能添加项目员工-->
        <el-form-item
          label="用户角色"
          prop="role"
          v-if="props.type == 1 || props.type == 2"
        >
          <el-radio-group v-model="form.role" @change="handleRadioChange">
            <el-radio
              label="项目经理"
              value="项目经理"
              :disabled="existManager"
            />
            <el-radio label="项目员工" value="项目员工" />
          </el-radio-group>
        </el-form-item>

        <el-form-item label="用户" prop="users">
          <el-table
            ref="multipleTableRef"
            :data="tableData"
            style="width: 100%"
            @selection-change="handleSelectionChange"
            stripe
          >
            <el-table-column type="selection" width="55" />

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
          </el-table>
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
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel">取消</el-button>
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
