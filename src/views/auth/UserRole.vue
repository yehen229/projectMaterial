<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from 'vue'

import { useRouter } from 'vue-router/dist/vue-router'

import store from '@/store'

import type { FormInstance, FormRules } from 'element-plus'
import { View, Hide, Search, Plus } from '@element-plus/icons-vue'

import { ElMessage, ElMessageBox } from 'element-plus'

import {
  serverUserRoleAdd, //增加
  serverUserRoleDelete, //删除
  serverUserRoleUpdate, //修改
  serverGetUserRolePage, //得到指定页面数据
  serverGetUserRolePageView, //根据指定页面数据
  serverGetUserRolePageViewByUserName,
  serverGetUserRolePageViewByUserRealName,
  serverGetUserRolePageViewByRoleName,
  serverGetUserRolePageViewByUserDepartment,
  serverGetUserRolePageViewByUserType,
} from '@/server/auth/SysUserRole'

//服务器返回到前端的类型
import {
  IServerResponseData,
  IServerSysUserRole,
  IServerPage,
  IServerSysUserRoleView,
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

import UserRoleUpdateDialog from '@/components/auth/UserRoleUpdateDialog.vue'

const router = useRouter()

const dialogFormUpdateVisible = ref(false) //控制“修改对话框”是否显示
const editRowData = ref<IServerSysUserRoleView>()

const form = reactive({
  id: '',
  name: '',
  note: '',
})
const loading = ref(false)

const searchText = ref('')
const searchSelect = ref('1')

const rules = reactive<FormRules>({
  name: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
})

const pageNo = ref(1) //第几页
const pageSize = ref(getUserPageSize()) //每页多少数据

const rolesData = ref<IServerPage<IServerSysUserRoleView> | null>(null)

onMounted(async () => {
  await getUserRoleViewFromSever()
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
const onChangeUserRole = async (index: number, row: IServerSysUserRoleView) => {
  console.log(index, row)

  editRowData.value = row
  dialogFormUpdateVisible.value = true
}

const getUserRoleViewFromSever = async () => {
  let ret = await serverGetUserRolePageView(pageNo.value, pageSize.value)
  if (ret && ret.code == 200) {
    rolesData.value = ret.data
  }
}

const onPagePrevClick = (value: number) => { }
const onPageNextClick = (value: number) => { }
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value
  await getUserRoleViewFromSever()
}

const onPageSizeChange = async (value: number) => {
  pageSize.value = value
  setUserPageSize(value)
  await getUserRoleViewFromSever()
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

  if (searchSelect.value == '0') {
    //用户ID
    let ret = await serverGetUserRolePageViewByUserName(
      search,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  } else if (searchSelect.value == '1') {
    //用户真实名称
    let ret = await serverGetUserRolePageViewByUserRealName(
      search,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  } else if (searchSelect.value == '2') {
    //用户部门
    let ret = await serverGetUserRolePageViewByUserDepartment(
      search,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  } else if (searchSelect.value == '3') {
    //用户类型
    let ret = await serverGetUserRolePageViewByUserType(
      search,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  } else if (searchSelect.value == '4') {
    //角色名称
    let ret = await serverGetUserRolePageViewByRoleName(
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
 * 修改对话框确定按钮，修改用户角色
 */
const onUpdateDialogOk = async (
  sysUserRoleView: IServerSysUserRoleView,
  newRoles: string[],
) => {
  let sysUserRoleViewNew: IServerSysUserRoleView = sysUserRoleView
  sysUserRoleViewNew.sysRoleList = []
  newRoles.forEach((val, idx, array) => {
    sysUserRoleViewNew.sysRoleList.push({ id: val, name: '', note: '' })
  })

  await serverUserRoleUpdate(sysUserRoleView)
  await getUserRoleViewFromSever()
  dialogFormUpdateVisible.value = false
}

const goBack = () => {
  history.back()
}
</script>

<template>
  <!--修改对话框-->
  <UserRoleUpdateDialog :dialogVisible="dialogFormUpdateVisible" :sysUserRoleView="editRowData"
    @onDilalogCancel="onUpdateDialogCancel" @onDilalogOk="onUpdateDialogOk"></UserRoleUpdateDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px;">
    <template #content>
      <span class="text-large font-600 mr-3">用户角色管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--搜索框-->
      <el-input v-model="searchText" placeholder="输入搜索内容" class="input-with-select">
        <template #prepend>
          <el-select v-model="searchSelect" placeholder="Select" style="width: 180px;">
            <el-option label="用户ID" value="0" />
            <el-option label="用户真实名称" value="1" />
            <el-option label="用户部门" value="2" />
            <el-option label="用户类型" value="3" />
            <el-option label="角色名称" value="4" />
          </el-select>
        </template>
        <template #append>
          <el-button :icon="Search" @click="onSearchClick" />
        </template>
      </el-input>
    </div>

    <!--表格显示内容-->

    <el-table :data="tableData" style="width: 100%;" v-loading="loading" stripe>
      <el-table-column label="用户ID" width="180">
        <template #default="scope">
          <div style="display: flex; align-items: center;">
            <span style="margin-left: 10px;">
              {{ scope.row.sysUser.userName }}
            </span>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="用户名称" width="180">
        <template #default="scope">
          <div style="display: flex; align-items: center;">
            <span style="margin-left: 10px;">
              {{ scope.row.sysUser.realName }}
            </span>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="用户类型" width="180">
        <template #default="scope">
          <div style="display: flex; align-items: center;">
            <span style="margin-left: 10px;">
              {{ scope.row.sysUser.userType }}
            </span>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="角色">
        <template #default="scope">
          <span style="margin-left: 10px;">
            <el-tag v-for="(item, index) in scope.row.sysRoleList" :key="index" type="info" size="small"
              class="option-tag" @click="onChangeUserRole(scope.$index, scope.row)">
              {{ item.name }}({{ item.note }})
            </el-tag>
          </span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="onChangeUserRole(scope.$index, scope.row)">
            编辑
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
