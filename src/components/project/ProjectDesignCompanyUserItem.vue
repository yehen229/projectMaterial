<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";

//服务器返回到前端的类型
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
  projectAllUserView: IServerProjectAllUserView; //对话框是否可见
  type: number;
}

const props = withDefaults(defineProps<Props>(), {});

//event
const emit = defineEmits<{
  (e: "onNew", projectView: IServerProjectView, companyId: string): void;
  (e: "onDelete", item: IServerProjectUserView): void;
}>();

const title = computed(() => {
  return "设计单位";
});
const projectUserViewList = computed(() => {
  if (props.projectAllUserView == null) return [];
  if (props.type == 0)
    //设计单位员工列表，包括项目经理和项目员工
    return props.projectAllUserView.projectUserViewListDesignCompany;
  else if (props.type == 1)
    //设计部员工列表，包括项目经理和项目员工
    return props.projectAllUserView.projectUserViewListDesignDepartment;
  else if (props.type == 2)
    //工程部员工列表，包括项目经理和项目员工
    return props.projectAllUserView.projectUserViewListEngineeringDepartment;
  else if (props.type == 3)
    //监理单位员工列表，包括项目经理和项目员工
    return props.projectAllUserView.projectUserViewListSupervisionCompany;
  else if (props.type == 4)
    //总包单位员工列表，包括项目经理和项目员工
    return props.projectAllUserView.projectUserViewListConstructionCompany;
  return [];
});

const onNew = (companyId: string) => {
  emit("onNew", props.projectAllUserView.projectView, companyId);
};

/**
 * 增加该单位所有人员为项目员工
 */
const onAddAllEmployee = async () => {};

const onDelete = (item: IServerProjectUserView) => {
  emit("onDelete", item);
};
</script>

<template>
  <!--设计单位和其它单位不同，项目可能存在多个设计单位，但是只有一个设计部、工程部、监理单位、总包单位等-->

  <div
    v-if="
      projectAllUserView && projectAllUserView.projectUserViewListDesignCompany
    "
  >
    <div
      class="project-user-item"
      v-for="(
        itemCompanyUser, index
      ) in projectAllUserView.projectUserViewListDesignCompany"
    >
      <div class="project-user-item-company">{{ title }}</div>
      <div class="project-user-item-users">
        <div>
          <span style="margin-right: 10px">
            {{ itemCompanyUser.company.name }}</span
          ><el-button
            type="primary"
            :icon="Plus"
            size="small"
            @click="onNew(itemCompanyUser.company.id)"
          >
            新增
          </el-button>
          <!--   <el-button
          type="primary"
          :icon="Plus"
          size="small"
          @click="onAddAllEmployee"
        >
          添加该单位所有员工
        </el-button>-->
        </div>

        <div class="divider"></div>

        <!--项目员工-->
        <div
          v-for="(item, index) in itemCompanyUser.projectUserViewList"
          :key="index"
          class="project-user-item-user"
        >
          <div
            class="project-user-item-user-name"
            v-if="item.role.name === 'Employee'"
          >
            {{ item.user.realName }} ({{ item.user.tel }})
          </div>
          <div
            class="project-user-item-user-role"
            v-if="item.role.name === 'Employee'"
          >
            <el-tag type="success">项目员工</el-tag>
          </div>
          <div v-if="item.role.name === 'Employee'">
            <el-button size="small" type="danger" @click="onDelete(item)">
              删除
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.project-user-item-container {
}

.project-user-item {
  border: 1px solid #dcdfe6;
  display: flex;
  margin: 5px;
  padding: 5px;
}
.project-user-item-company {
  width: 100px;
}
.project-user-item-users {
}
.project-user-item-user {
  display: flex;
  align-items: center;
}
.project-user-item-user-name {
  width: 400px;
  margin: 5px;
}
.project-user-item-user-role {
  width: 100px;
  margin: 5px;
}

.divider {
  width: 100%;
  height: 1px;
  background-color: #eee;
  margin: 5px;
}
</style>
