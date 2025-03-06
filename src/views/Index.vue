<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from "vue";
import axios from "axios";
import { onBeforeRouteLeave, onBeforeRouteUpdate } from "vue-router";

import { useRouter, useRoute } from "vue-router/dist/vue-router";

import { useTabStore, tabMenuLabel } from "@/store/tabmenu";

import { View, Hide } from "@element-plus/icons-vue";

import type { TabsPaneContext } from "element-plus";

import {
  Menu as IconMenu,
  Location,
  Setting,
  Help,
  Memo,
  Document,
  DocumentChecked,
} from "@element-plus/icons-vue";

import {
  getToken,
  getUserName,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import { serverLoginOut } from "@/server/account/User";

import { userStore } from "@/store/user";

import { useActiveMenuStore } from "@/store/activeMenu";

const activeMenuStore = useActiveMenuStore();
const store = userStore();

const router = useRouter();
const route = useRoute();
const { currentRoute } = useRouter();
const tabStore = useTabStore();

const editableTabsValue = ref("");
const USER_ROLES = ref(null);
onMounted(async () => {
  editableTabsValue.value = route.path;

  USER_ROLES.value = localStorage.getItem("USER_ROLES");
  console.log("USER_ROLES", USER_ROLES.value);
});

// 与 beforeRouteUpdate 相同，无法访问 `this`
onBeforeRouteUpdate(async (to, from) => {
  editableTabsValue.value = to.path;
});

const isCollapse = ref(false);

const handleOpen = (key: string, keyPath: string[]) => {
  // console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  // console.log(key, keyPath);
};

const handleSelect = (key: string, keyPath: string[]) => {
  // console.log(key, keyPath);
  router.push({ path: key });
};

const onSignOut = async () => {
  await serverLoginOut();
};

const activeIndex = ref("/");
const gorouter = () => {
  router.push({ path: "scan" });
};
// const generateQRCode = () => {
//   const canvas = refQRCodeCanvas.value; // 使用 ref 引用
//
//   QRCode.toCanvas(canvas, value.value, (error) => {
//     if (error) console.error(error);
//     console.log('二维码生成成功！');
//   });
// }
</script>
<template>
  <div class="common-layout">
    <el-container>
      <el-header style="background-color: #4285f4">
        <el-menu
          mode="horizontal"
          background-color="#4285f4"
          text-color="#f1f5f7"
          active-text-color="#f1f5f7"
          :ellipsis="false"
          @select="handleSelect"
        >
          <el-menu-item index="/" style="">
            <span style="margin-left: -30px; display: block">
              全周期工程建设物料智能管理系统
            </span>
          </el-menu-item>
          <div class="flex-grow" />
          <el-menu-item index="1" @click="gorouter()">扫码</el-menu-item>
          <el-sub-menu index="2">
            <template #title>{{ getUserRealName() }}</template>
            <el-menu-item index="/userInfo">个人设置</el-menu-item>
            <el-menu-item index="/resetpwd">修改密码</el-menu-item>
            <el-menu-item index="/login" @click="onSignOut"
              >退出系统
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-header>

      <el-container>
        <el-aside
          width="200px"
          style="background-color: #2b3442; color: #9ea4ba; margin-top: -1px"
        >
          <el-menu
            :default-active="activeIndex"
            background-color="#2b3442"
            text-color="#9ea4ba"
            active-text-color="#f1f5f7"
            style="height: calc(100vh - 60px)"
            @open="handleOpen"
            @close="handleClose"
            @select="handleSelect"
            :default-openeds="['1', '2', '3']"
          >
            <el-sub-menu index="1" v-if="USER_ROLES == 'Admin'">
              <template #title>
                <el-icon>
                  <Operation></Operation>
                </el-icon>
                <span>系统管理</span>
              </template>
              <el-menu-item-group title="单位用户管理">
                <el-menu-item index="/company">单位管理</el-menu-item>
                <el-menu-item index="/company-user">用户管理</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="项目用户管理">
                <el-menu-item index="/project-user">项目用户</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="材料品牌管理">
                <el-menu-item index="/classify">材料类别管理</el-menu-item>
                <el-menu-item index="/material">材料管理</el-menu-item>
                <el-menu-item index="/brand">品牌管理</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <DocumentCopy />
                </el-icon>
                <span>项目管理</span>
              </template>
              <el-menu-item-group title="项目立项">
                <el-menu-item index="/project-list">项目列表</el-menu-item>
                <el-menu-item
                  index="/project-brand-list"
                  v-if="USER_ROLES == 'Admin'"
                  >私有品牌管理
                </el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="项目审核">
                <el-menu-item index="/project-user-task-list"
                  >项目审核
                </el-menu-item>
                <el-menu-item
                  index="/project-user-completed-task-list"
                  v-if="USER_ROLES == 'Admin'"
                  >审核历史
                </el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="二维码列表">
                <el-menu-item index="/showqrcode">二维码列表 </el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>

            <el-menu-item index="logsManage" v-if="USER_ROLES == 'Admin'">
              <el-icon>
                <document />
              </el-icon>
              <span>日志管理</span>
            </el-menu-item>
            <el-menu-item
              index="statisticalanalysis"
              v-if="USER_ROLES == 'Admin'"
            >
              <el-icon>
                <DataLine />
              </el-icon>
              <span>统计分析</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
.flex-grow {
  flex-grow: 1;
  background-color: #4285f4;
}
</style>
