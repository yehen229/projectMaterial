<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from 'vue'

import { useRouter } from 'vue-router/dist/vue-router'

import store from '@/store'

import type { FormInstance, FormRules } from 'element-plus'
import { View, Hide, Search, Plus } from '@element-plus/icons-vue'

import { ElMessage, ElMessageBox } from 'element-plus'

import {
  serverRoleAdd, //增加
  serverRoleDelete, //删除
  serverRoleUpdate, //修改
  serverGetRolePage, //得到指定页面数据
  serverGetRolePageByName, //根据名称得到指定页面数据
} from '@/server/auth/SysRole'

//服务器返回到前端的类型
import {
  IServerResponseData,
  IServerSysRole,
  IServerPage,
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

const router = useRouter()

const dialogFormNewVisible = ref(false) //控制“新增对话框”是否显示
const dialogFormUpdateVisible = ref(false) //控制“修改对话框”是否显示
const formLabelWidth = '140px'
const ruleFormRef = ref<FormInstance>()
const form = reactive({
  id: '',
  name: '',
  note: '',
})
const loading = ref(false)

const searchText = ref('')
const searchSelect = ref('1')

const rules = reactive<FormRules>({
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
})

const pageNo = ref(1) //第几页
const pageSize = ref(getUserPageSize()) //每页多少数据

const rolesData = ref<IServerPage<IServerSysRole> | null>(null)

onMounted(async () => {
  await getRolesFromSever()
})

const tableData = computed(() => {
  return rolesData.value?.result
})

const totalCount = computed(() => {
  return Number(rolesData.value?.totalCount ?? 0)
})

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true
}

/**
 * 点击删除按钮，删除内容
 * @param index
 * @param row
 */
const onRowDeleteButtonClick = async (index: number, row: IServerSysRole) => {
  console.log(index, row)

  ElMessageBox.confirm('是否真的删除数据？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      await serverRoleDelete(row.id)
      await getRolesFromSever()
      ElMessage({
        type: 'success',
        message: '完成删除',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '删除失败',
      })
    })
}

/**
 * 提交新增内容，将用户填写的内容 提交到服务器
 * @param formEl
 */
const submitNewForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  loading.value = true
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      let ret = await serverRoleAdd(form.name, form.note)
      if (ret != null) {
        await getRolesFromSever()
      }
      dialogFormNewVisible.value = false

      ElMessage({
        type: 'success',
        message: '增加成功',
      })
    } else {
      ElMessage({
        type: 'info',
        message: '增加失败',
      })
    }
  })
  loading.value = false
}

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onRowEditButtonClick = async (index: number, row: IServerSysRole) => {
  console.log(index, row)
  form.id = row.id
  form.name = row.name
  form.note = row.note
  dialogFormUpdateVisible.value = true
}

/**
 * 提交修改内容，将用户 填写的内容提交到服务器
 * @param formEl
 */
const submitUpdateForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  loading.value = true
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      let ret = await serverRoleUpdate(form.id, form.name, form.note)
      if (ret != null) {
        await getRolesFromSever()
      }
      dialogFormUpdateVisible.value = false

      ElMessage({
        type: 'success',
        message: '修改成功',
      })
    } else {
      ElMessage({
        type: 'info',
        message: '修改失败',
      })
    }
  })
  loading.value = false
}

/**
 * 重置对话框内容
 * @param formEl
 */
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const getRolesFromSever = async () => {
  let ret = await serverGetRolePage(pageNo.value, pageSize.value)
  if (ret && ret.code == 200) {
    rolesData.value = ret.data
  }
}

const onPagePrevClick = (value: number) => { }
const onPageNextClick = (value: number) => { }
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value
  await getRolesFromSever()
}

const onPageSizeChange = async (value: number) => {
  pageSize.value = value
  setUserPageSize(value)
  await getRolesFromSever()
}

const onSearchClick = async () => {
  let search = searchText.value.trim()
  if (search) {
    pageNo.value = 1
    let ret = await serverGetRolePageByName(
      searchText.value,
      pageNo.value,
      pageSize.value,
    )
    if (ret && ret.code == 200) {
      rolesData.value = ret.data
    }
  } else await getRolesFromSever()
}

const goBack = () => {
  history.back()
}
</script>

<template>
  <!--新增对话框-->
  <el-dialog v-model="dialogFormNewVisible" title="增加角色" draggable>
    <el-form ref="ruleFormRef" :model="form" :rules="rules">
      <el-form-item label="角色名称" :label-width="formLabelWidth" prop="name">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="备注说明" :label-width="formLabelWidth" prop="note">
        <el-input v-model="form.note" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        <el-button @click="dialogFormNewVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNewForm(ruleFormRef)">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>

  <!--修改对话框-->
  <el-dialog v-model="dialogFormUpdateVisible" title="修改角色" draggable>
    <el-form ref="ruleFormRef" :model="form" :rules="rules">
      <el-form-item label="角色名称" :label-width="formLabelWidth" prop="name">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="备注说明" :label-width="formLabelWidth" prop="note">
        <el-input v-model="form.note" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        <el-button @click="dialogFormUpdateVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUpdateForm(ruleFormRef)">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px;">
    <template #content>
      <span class="text-large font-600 mr-3">角色管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
        新增
      </el-button>

      <!--搜索框-->
      <el-input v-model="searchText" placeholder="输入搜索内容" class="input-with-select">
        <template #prepend>
          <el-select v-model="searchSelect" placeholder="Select" style="width: 115px;">
            <el-option label="角色名称" value="1" />
          </el-select>
        </template>
        <template #append>
          <el-button :icon="Search" @click="onSearchClick" />
        </template>
      </el-input>
    </div>

    <!--表格显示内容-->

    <el-table :data="tableData" style="width: 100%;" v-loading="loading" stripe>
      <el-table-column label="名称" width="180">
        <template #default="scope">
          <div style="display: flex; align-items: center;">
            <span style="margin-left: 10px;">{{ scope.row.name }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="备注" width="180">
        <template #default="scope">
          <div style="display: flex; align-items: center;">
            <span style="margin-left: 10px;">{{ scope.row.note }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="small" @click="onRowEditButtonClick(scope.$index, scope.row)">
            编辑
          </el-button>
          <el-button size="small" type="danger" @click="onRowDeleteButtonClick(scope.$index, scope.row)">
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
</style>
