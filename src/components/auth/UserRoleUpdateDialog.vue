<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from 'vue'

import { useRouter } from 'vue-router/dist/vue-router'

import store from '@/store'

import type { FormInstance, FormRules } from 'element-plus'
import { View, Hide, Search, Plus } from '@element-plus/icons-vue'

import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'
import {
  serverUserRoleAdd, //增加
  serverUserRoleDelete, //删除
  serverUserRoleUpdate, //修改
  serverGetUserRolePage, //得到指定页面数据
  serverGetUserRolePageView, //根据指定页面数据
} from '@/server/auth/SysUserRole'

import { serverGetRolePage } from '@/server/auth/SysRole'

//服务器返回到前端的类型
import {
  IServerResponseData,
  IServerSysUserRole,
  IServerPage,
  IServerSysUserRoleView,
  IServerSysRole,
} from '@/server/ServerType'

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
  isStudent, getUserPageSize,
  setUserPageSize
} from '@/cookies/user'

const router = useRouter()

interface Props {
  dialogVisible: boolean//对话框是否可见
  sysUserRoleView: IServerSysUserRoleView
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
  sysUserRoleView: undefined,
})

const roleList = ref([])

interface IFormUserRoles {
  id: string
  name: string
  type: string[]
}

const form = reactive<IFormUserRoles>({
  id: '',
  name: '',
  type: [],
})

//event
const emit = defineEmits<{
  (
    e: 'onDilalogOk',
    sysUserRoleView: IServerSysUserRoleView,
    newRoles: string[],
  ): void
  (e: 'onDilalogCancel'): void
}>()

const userName = computed(() => {
  return props.sysUserRoleView.sysUser.userName
})

const dialogFormUpdateVisible = computed({
  get() {
    return props.dialogVisible
  },
  set(val) {
    return val
  },
})

const pageNo = ref(1) //第几页
const pageSize = ref(getUserPageSize()) //每页多少数据

const rolesData = ref<IServerPage<IServerSysRole> | null>(null)

const onOpenDialog = async () => {
  await getUserRolesFromServer()
}

const getUserRolesFromServer = async () => {
  form.type = []
  let ret = await serverGetRolePage(pageNo.value, pageSize.value)
  if (ret && ret.code == 200) {
    rolesData.value = ret.data


    props.sysUserRoleView?.sysRoleList?.forEach((val, idx, array) => {
      form.type.push(val.id)
    })
  }
}

const tableData = computed(() => {
  return rolesData.value?.result
})

const totalCount = computed(() => {
  return Number(rolesData.value?.totalCount ?? 0)
})

const onPagePrevClick = (value: number) => { }
const onPageNextClick = (value: number) => { }
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value
  await getUserRolesFromServer()
}

const onPageSizeChange = async (value: number) => {
  pageSize.value = value
  setUserPageSize(value)
  await getUserRolesFromServer()
}

const handleClose = () => {
  emit('onDilalogCancel')
}
const onOk = () => {
  if (!form.type || form.type.length == 0) {
    ElMessageBox.alert('没有为用户添加角色', '提示', {
      // if you want to disable its autofocus
      // autofocus: false,
      confirmButtonText: '确定',
    })

    return
  }

  emit('onDilalogOk', props.sysUserRoleView, form.type)
}

const onCancel = () => {
  console.log(form.type)
  emit('onDilalogCancel')
}
</script>

<template>
  <div>
    <el-dialog title="编辑用户的角色" v-model="dialogFormUpdateVisible" :before-close="handleClose" width="1000px"
      @open="onOpenDialog" draggable>
      <el-form label-position="right" label-width="80px" :model="form">
        <div class="grid-content bg-purple-light">
          <h1>姓名：{{ userName }}</h1>
          <h1>角色</h1>
          <el-form-item>
            <el-checkbox-group v-model="form.type">
              <el-checkbox v-for="(item, index) in tableData" :key="index" :label="item.id" name="type">
                {{ item.name }}({{ item.note }})
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </div>
      </el-form>
      <el-pagination :hide-on-single-page="true" class="page-class" background v-model:current-page="pageNo"
        v-model:page-size="pageSize" :page-sizes="[10, 50, 100, 200, 300, 400]" layout="total, sizes, prev, pager, next"
        :total="totalCount" @prev-click="onPagePrevClick" @next-click="onPageNextClick"
        @current-change="onPageCurrentChange" @size-change="onPageSizeChange" />

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel">取消</el-button>
          <el-button type="primary" @click="onOk()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
