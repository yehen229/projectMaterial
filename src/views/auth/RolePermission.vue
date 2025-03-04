<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from 'vue'

import { useRouter } from 'vue-router/dist/vue-router'

import store from '@/store'

import type { FormInstance, FormRules } from 'element-plus'
import { View, Hide, Search, Plus } from '@element-plus/icons-vue'

import { ElMessage, ElMessageBox } from 'element-plus'

import {
  serverRolePermissionUpdate,
  serverGetRolePermissionPageView,
  serverGetRolePermissionPageViewByRoleName,
  serverGetRolePermissionPageViewByPermissionName,
} from '@/server/auth/SysRolePermission'

//服务器返回到前端的类型
import {
  IServerResponseData,
  IServerPage,
  IServerSysRolePermissionView,
} from '@/server/ServerType'

import {
  setUserCookies,
  getUserName,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent, getUserPageSize,
  setUserPageSize
} from '@/cookies/user'

import RolePermissionUpdateDialog from '@/components/auth/RolePermissionUpdateDialog.vue'

const router = useRouter()

const dialogFormUpdateVisible = ref(false) //控制“修改对话框”是否显示
const editRowData = ref<IServerSysRolePermissionView>()

const form = reactive({
  id: '',
  name: '',
  note: '',
})
const loading = ref(false)

const searchText = ref('')
const searchSelect = ref('0')

const rules = reactive<FormRules>({
  name: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
})

const pageNo = ref(1) //第几页
const pageSize = ref(getUserPageSize()) //每页多少数据

const rolesData = ref<IServerPage<IServerSysRolePermissionView> | null>(null)

onMounted(async () => {
  await getRolePermissionViewFromSever()
})

const tableData = computed(() => {
  return rolesData.value?.result
})

const totalCount = computed(() => {
  return Number(rolesData.value?.totalCount ?? 0)
})

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onChangeRolePermission = async (
  index: number,
  row: IServerSysRolePermissionView,
) => {
  console.log(index, row)

  editRowData.value = row
  dialogFormUpdateVisible.value = true
}

const getRolePermissionViewFromSever = async () => {
  let ret = await serverGetRolePermissionPageView(pageNo.value, pageSize.value)
  if (ret && ret.code == 200) {
    rolesData.value = ret.data
  }
}

const onPagePrevClick = (value: number) => { }
const onPageNextClick = (value: number) => { }
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value
  await getRolePermissionViewFromSever()
}

const onPageSizeChange = async (value: number) => {
  pageSize.value = value
  setUserPageSize(value)
  await getRolePermissionViewFromSever()
}

/**
 * 查询按钮
 */
const onSearchClick = async () => {
  let search = searchText.value.trim()
  if (!search) {
    ElMessageBox.alert('请输入查询内容', '提示', {
      confirmButtonText: '确定',
    })
    return
  }

  pageNo.value = 1

  if (searchSelect.value == '0') {
    //角色名称
    let ret = await serverGetRolePermissionPageViewByRoleName(
      search,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  } else if (searchSelect.value == '1') {
    //权限名称
    let ret = await serverGetRolePermissionPageViewByPermissionName(
      search,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  }
}

/**
 * 修改对话框取消按钮
 */
const onUpdateDialogCancel = () => {
  dialogFormUpdateVisible.value = false
}
/**
 * 修改对话框确定按钮，修改角色权限
 */
const onUpdateDialogOk = async (
  sysRolePermissionView: IServerSysRolePermissionView,
  newPermissions: string[],
) => {
  let sysRolePermissionViewNew: IServerSysRolePermissionView = sysRolePermissionView
  sysRolePermissionViewNew.sysPermissionList = []

  newPermissions?.forEach((val, idx, array) => {
    sysRolePermissionViewNew.sysPermissionList.push({
      id: val,
      name: '',
      note: '',
    })
  })

  await serverRolePermissionUpdate(sysRolePermissionViewNew)
  await getRolePermissionViewFromSever()
  dialogFormUpdateVisible.value = false
}

const goBack = () => {
  history.back()
}
</script>

<template>
  <!--修改对话框-->
  <RolePermissionUpdateDialog :dialogVisible="dialogFormUpdateVisible" :sysRolePermissionView="editRowData"
    @onDilalogCancel="onUpdateDialogCancel" @onDilalogOk="onUpdateDialogOk"></RolePermissionUpdateDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px;">
    <template #content>
      <span class="text-large font-600 mr-3">角色权限管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--搜索框-->
      <el-input v-model="searchText" placeholder="输入搜索内容" class="input-with-select">
        <template #prepend>
          <el-select v-model="searchSelect" placeholder="Select" style="width: 180px;">
            <el-option label="角色名称" value="0" />
            <el-option label="权限名称" value="1" />
          </el-select>
        </template>
        <template #append>
          <el-button :icon="Search" @click="onSearchClick" />
        </template>
      </el-input>
    </div>

    <!--表格显示内容-->

    <el-table :data="tableData" style="width: 100%;" v-loading="loading" stripe>
      <el-table-column label="角色名称" width="180">
        <template #default="scope">
          <div style="display: flex; align-items: center;">
            <span style="margin-left: 10px;">
              {{ scope.row.sysRole.name }}
            </span>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="权限">
        <template #default="scope">
          <span style="margin-left: 10px;">
            <el-tag v-for="(item, index) in scope.row.sysPermissionList" :key="index" type="info" size="small"
              class="option-tag" @click="onChangeRolePermission(scope.$index, scope.row)">
              {{ item.name }}
            </el-tag>
          </span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="onChangeRolePermission(scope.$index, scope.row)">
            编辑
          </el-button>
          <el-button size="small" @click="onChangeUserRole(scope.$index, scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :hide-on-single-page="true" class="page-class" background v-model:current-page="pageNo"
      v-model:page-size="pageSize" :page-sizes="[10, 50, 100, 200, 300, 400]" layout="total, sizes, prev, pager, next"
      :total="totalCount" @prev-click="onPagePrevClick" @next-click="onPageNextClick"
      @current-change="onPageCurrentChange" @size-change="onPageSizeChange" />
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
  margin-left: 20px;
}

.option-tag {
  margin-right: 5px;
  cursor: pointer;
  -moz-user-select: none;
  -webkit-user-select: none;
}
</style>
