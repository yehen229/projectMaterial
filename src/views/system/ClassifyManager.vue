<script setup lang="ts">
/**
 * 对单位进行管理 *
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

import {
  IServerMaterial,
  IServerMaterialBrand,
  IServerMaterialBrandView,
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyDivisionTreeItem,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifyGroupTreeItem,
  IServerMaterialClassifyGroupView,
  IServerMaterialClassifySection,
  IServerMaterialClassifySectionView,
  IServerMaterialClassifyTree,
  IServerMaterialPhoto,
  IServerMaterialPhotoView,
} from "@/server/types/system/material";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import {
  serverMaterialClassifyDivisionAdd,
  serverMaterialClassifyDivisionDelete,
  serverMaterialClassifyDivisionDeleteById,
  serverMaterialClassifyDivisionUpdate,
  serverGetMaterialClassifyDivisionById,
  serverGetMaterialClassifyDivisionPage,
  serverGetMaterialClassifyDivisionAllList,
} from "@/server/system/materialclassifydivision";

import {
  serverMaterialClassifyGroupAdd,
  serverMaterialClassifyGroupDelete,
  serverMaterialClassifyGroupDeleteById,
  serverMaterialClassifyGroupUpdate,
  serverGetMaterialClassifyGroupById,
  serverGetMaterialClassifyGroupPage,
  serverGetMaterialClassifyGroupTree,
} from "@/server/system/materialclassifygroup";

import {
  serverMaterialClassifySectionAdd,
  serverMaterialClassifySectionDelete,
  serverMaterialClassifySectionDeleteById,
  serverMaterialClassifySectionUpdate,
  serverGetMaterialClassifySectionById,
  serverGetMaterialClassifyTree,
  serverGetMaterialClassifySectionPage,
} from "@/server/system/materialclassifysection";

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

import type Node from "element-plus/es/components/tree/src/model/node";

import NewClassifyDivisionDialog from "@/components/system/classify/NewClassifyDivisionDialog.vue";
import UpdateClassifyDivisionDialog from "@/components/system/classify/UpdateClassifyDivisionDialog.vue";

import NewClassifyGroupDialog from "@/components/system/classify/NewClassifyGroupDialog.vue";
import UpdateClassifyGroupDialog from "@/components/system/classify/UpdateClassifyGroupDialog.vue";

import NewClassifySectionDialog from "@/components/system/classify/NewClassifySectionDialog.vue";
import UpdateClassifySectionDialog from "@/components/system/classify/UpdateClassifySectionDialog.vue";

import UploadExcelClassifyDialog from "@/components/system/classify/UploadExcelClassifyDialog.vue";

interface Tree {
  id: string;
  label: string;

  /**
   * 节点,
   * 如果是大类，则只有materialClassifyDivision有值，materialClassifyGroup和materialClassifySection为空
   * 如果是中类，则只有materialClassifyDivision和materialClassifyGroup有值，materialClassifySection为空
   * 如果是小类，则三个都有值
   * */
  materialClassifySection: IServerMaterialClassifySection | undefined; //t_material_classify_section
  materialClassifyGroup: IServerMaterialClassifyGroup | undefined; //外键：t_material_classify_group_id,关联表为：t_material_classify_group表,
  materialClassifyDivision: IServerMaterialClassifyDivision;

  //孩子
  children?: Tree[];
}
let id = 1000;

const dataSource = ref<Tree[]>([]);

const router = useRouter();

const dialogNewMaterialClassifyDivisionVisible = ref(false); //控制“修改对话框”是否显示
const dialogUpdateMaterialClassifyDivisionVisible = ref(false); //控制“修改对话框”是否显示
const updateMaterialClassifyDivision = ref<IServerMaterialClassifyDivision>();
const dialogNewMaterialClassifyGroupVisible = ref(false); //控制“修改对话框”是否显示
const dialogUpdateMaterialClassifyGroupVisible = ref(false); //控制“修改对话框”是否显示
const updateMaterialClassifyGroup = ref<IServerMaterialClassifyGroup>();
const dialogNewMaterialClassifySectionVisible = ref(false); //控制“修改对话框”是否显示
const dialogUpdateMaterialClassifySectionVisible = ref(false); //控制“修改对话框”是否显示
const updateMaterialClassifySection = ref<IServerMaterialClassifySection>();
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示

const formLabelWidth = "140px";
const ruleFormRef = ref<FormInstance>();
const form = reactive({
  id: "",
  name: "",
  note: "",
});
const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("名称");

const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入公司名称", trigger: "blur" }],
});

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const radioUserType = ref(0);

onMounted(async () => {
  await getTreeFromSever();
});

const generateSectionTree = (
  groupTreeChildren: IServerMaterialClassifySectionView[]
) => {
  if (!groupTreeChildren) return [];
  let temp: Tree[] = [];
  groupTreeChildren.forEach((sectionTreeItem) => {
    const newChild: Tree = {
      id: sectionTreeItem.materialClassifySection.id,
      label: sectionTreeItem.materialClassifySection.name,
      materialClassifySection: sectionTreeItem.materialClassifySection,
      materialClassifyGroup: sectionTreeItem.materialClassifyGroup,
      materialClassifyDivision: sectionTreeItem.materialClassifyDivision,
      children: [],
    };
    temp.push(newChild);
  });
  return temp;
};

const generateGroupTree = (
  divisionTreeChildren: IServerMaterialClassifyGroupTreeItem[]
) => {
  if (!divisionTreeChildren) return [];
  let temp: Tree[] = [];
  divisionTreeChildren.forEach((groupTreeItem) => {
    let children: Tree[] = generateSectionTree(groupTreeItem.children);
    const newChild: Tree = {
      id: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.id,
      label: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.name,
      materialClassifySection: undefined,
      materialClassifyGroup:
        groupTreeItem.materialClassifyGroupView.materialClassifyGroup,
      materialClassifyDivision:
        groupTreeItem.materialClassifyGroupView.materialClassifyDivision,
      children: children,
    };
    temp.push(newChild);
  });
  return temp;
};

const generateTree = (tree: IServerMaterialClassifyTree) => {
  dataSource.value = [];
  tree.children.forEach((divisionTreeItem) => {
    const children: Tree[] = generateGroupTree(divisionTreeItem.children);
    const newChild: Tree = {
      id: divisionTreeItem.materialClassifyDivision.id,
      label: divisionTreeItem.materialClassifyDivision.name,
      materialClassifySection: undefined,
      materialClassifyGroup: undefined,
      materialClassifyDivision: divisionTreeItem.materialClassifyDivision,
      children: children,
    };

    console.log(newChild);
    dataSource.value.push(newChild);
  });
};

const getTreeFromSever = async () => {
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
        generateTree(ret.data);
      }
    } else if (searchSelect.value == "1") {
      //单位名称
      const ret = await serverGetCompanyPageByCompanyType(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        generateTree(ret.data);
      }
    }
  } else {
    const ret = await serverGetMaterialClassifyTree();
    console.log(ret);
    if (ret && ret.code == 200) {
      generateTree(ret.data);
    }
  }
};

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewMaterialClassifyDivisionButtonClick = () => {
  dialogNewMaterialClassifyDivisionVisible.value = true;
};

/**
 * 用户在“新增”对话框中点击了“取消”按钮
 */
const onNewMaterialClassifyDivisionDialogCancel = async () => {
  dialogNewMaterialClassifyDivisionVisible.value = false;
};

/**
 * 用户在“新增”对话框中点击了“确认”按钮
 */
const onNewMaterialClassifyDivisionDialogOk = async (
  materialClassifyDivision: IServerMaterialClassifyDivision
) => {
  const ret = await serverMaterialClassifyDivisionAdd(materialClassifyDivision);
  if (ret && ret.code == 200) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
    await getTreeFromSever();
  } else {
    ElMessage({
      type: "warning",
      message: "增加失败",
    });
  }

  dialogNewMaterialClassifyDivisionVisible.value = false;
};

const onUpdateMaterialClassifyDivisionDialogCancel = () => {
  dialogUpdateMaterialClassifyDivisionVisible.value = false;
};

const onUpdateMaterialClassifyDivisionDialogOk = async (
  materialClassifyDivision: IServerMaterialClassifyDivision
) => {
  const ret = await serverMaterialClassifyDivisionUpdate(
    materialClassifyDivision
  );

  if (ret && ret.code == 200) {
    ElMessage({
      type: "success",
      message: "修改成功",
    });
    await getTreeFromSever();
  } else {
    ElMessage({
      type: "warning",
      message: "修改失败",
    });
  }

  dialogUpdateMaterialClassifyDivisionVisible.value = false;
};

const onNewMaterialClassifyGroupDialogOk = async (
  materialClassifyGroup: IServerMaterialClassifyGroup
) => {
  const ret = await serverMaterialClassifyGroupAdd(materialClassifyGroup);
  if (ret && ret.code == 200) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
    await getTreeFromSever();
  } else {
    ElMessage({
      type: "warning",
      message: "增加失败",
    });
  }
  dialogNewMaterialClassifyGroupVisible.value = false;
};

const onNewMaterialClassifyGroupDialogCancel = async () => {
  dialogNewMaterialClassifyGroupVisible.value = false;
};

const onUpdateMaterialClassifyGroupDialogOk = async (
  materialClassifyGroup: IServerMaterialClassifyGroup
) => {
  const ret = await serverMaterialClassifyGroupUpdate(materialClassifyGroup);
  if (ret && ret.code == 200) {
    ElMessage({
      type: "success",
      message: "修改成功",
    });
    await getTreeFromSever();
  } else {
    ElMessage({
      type: "warning",
      message: "修改失败",
    });
  }
  dialogUpdateMaterialClassifyGroupVisible.value = false;
};
const onUpdateMaterialClassifyGroupDialogCancel = () => {
  dialogUpdateMaterialClassifyGroupVisible.value = false;
};

const onNewMaterialClassifySectionDialogOk = async (
  materialClassifySection: IServerMaterialClassifySection
) => {
  const ret = await serverMaterialClassifySectionAdd(materialClassifySection);
  if (ret && ret.code == 200) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
    await getTreeFromSever();
  } else {
    ElMessage({
      type: "warning",
      message: "增加失败",
    });
  }
  dialogNewMaterialClassifySectionVisible.value = false;
};

const onNewMaterialClassifySectionDialogCancel = () => {
  dialogNewMaterialClassifySectionVisible.value = false;
};

const onUpdateMaterialClassifySectionDialogOk = async (
  materialClassifySection: IServerMaterialClassifySection
) => {
  const ret = await serverMaterialClassifySectionUpdate(
    materialClassifySection
  );
  if (ret && ret.code == 200) {
    ElMessage({
      type: "success",
      message: "修改成功",
    });
    await getTreeFromSever();
  } else {
    ElMessage({
      type: "warning",
      message: "修改失败",
    });
  }
  dialogUpdateMaterialClassifySectionVisible.value = false;
};
const onUpdateMaterialClassifySectionDialogCancel = () => {
  dialogUpdateMaterialClassifySectionVisible.value = false;
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getTreeFromSever();
};

const goBack = () => {
  history.back();
};

//得到节点类型：0大类、1中类、2小类
const getDataType = (data: Tree) => {
  if (!data.materialClassifyGroup) {
    return 0;
  } else if (!data.materialClassifySection) {
    return 1;
  }
  return 2;
};
const getAddButtonTitle = (data: Tree) => {
  const type = getDataType(data);
  if (type == 0) {
    return "新增中类（材料分类）";
  } else if (type == 1) {
    return "新增小类（材料名称）";
  }
};

//增加孩子
const newClassifyNode = (data: Tree) => {
  let type = getDataType(data);
  if (type == 0) {
    updateMaterialClassifyDivision.value = data.materialClassifyDivision;
    dialogNewMaterialClassifyGroupVisible.value = true;
  } else if (type == 1) {
    updateMaterialClassifyDivision.value = data.materialClassifyDivision;
    updateMaterialClassifyGroup.value = data.materialClassifyGroup;
    dialogNewMaterialClassifySectionVisible.value = true;
  }
};

/**
 * 删除节点
 */
const remove = (node: Node, data: Tree) => {
  let type = getDataType(data);

  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let ret;
      if (type == 0) {
        //删除大类
        ret = await serverMaterialClassifyDivisionDeleteById(data.id);
      } else if (type == 1) {
        //删除中类
        ret = await serverMaterialClassifyGroupDeleteById(data.id);
      } else if (type == 2) {
        //删除小类
        ret = await serverMaterialClassifySectionDeleteById(data.id);
      }
      if (ret && ret.code == 200)
        ElMessage({
          type: "success",
          message: "完成删除",
        });
      else
        ElMessage({
          type: "warning",
          message: "删除失败",
        });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "删除时发生错误",
      });
    });
};

//修改
const updateNode = (node: Node, data: Tree) => {
  let type = getDataType(data);
  if (type == 0) {
    updateMaterialClassifyDivision.value = data.materialClassifyDivision;
    dialogUpdateMaterialClassifyDivisionVisible.value = true;
  } else if (type == 1) {
    updateMaterialClassifyDivision.value = data.materialClassifyDivision;
    updateMaterialClassifyGroup.value = data.materialClassifyGroup;
    dialogUpdateMaterialClassifyGroupVisible.value = true;
  } else if (type == 2) {
    updateMaterialClassifyDivision.value = data.materialClassifyDivision;
    updateMaterialClassifyGroup.value = data.materialClassifyGroup;
    updateMaterialClassifySection.value = data.materialClassifySection;
    dialogUpdateMaterialClassifySectionVisible.value = true;
  }
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

/**
 * 下载用户名单
 * @param index
 * @param row
 */
const onDownloadExcelButtonClick = async () => {
  const downloadFilename = "用户名单";

  loading.value = true;
  let search = searchText.value.trim();

  if (search) {
    if (searchSelect.value == "用户名称") {
      //用户名称
      const ret = await serverDownloadCompanyUserByUserNamer(
        searchText.value,
        downloadFilename
      );
    } else if (searchSelect.value == "项目名称") {
      //项目名称
      const ret = await serverDownloadCompanyUserByProjectName(
        searchText.value,
        downloadFilename
      );
    } else if (searchSelect.value == "单位名称") {
      //单位名称
      const ret = await serverDownloadCompanyUserByCompanyName(
        searchText.value,
        downloadFilename
      );
    }
  } else {
    await serverDownloadAllCompanyUser(downloadFilename);
  }

  loading.value = false;
};
</script>

<template>
  <!--新增对话框-->
  <NewClassifyDivisionDialog
    :dialogVisible="dialogNewMaterialClassifyDivisionVisible"
    @onDilalogCancel="onNewMaterialClassifyDivisionDialogCancel"
    @onDilalogOk="onNewMaterialClassifyDivisionDialogOk"
  ></NewClassifyDivisionDialog>

  <!--修改对话框-->
  <UpdateClassifyDivisionDialog
    :dialogVisible="dialogUpdateMaterialClassifyDivisionVisible"
    :materialClassifyDivision="updateMaterialClassifyDivision"
    @onDilalogCancel="onUpdateMaterialClassifyDivisionDialogCancel"
    @onDilalogOk="onUpdateMaterialClassifyDivisionDialogOk"
  ></UpdateClassifyDivisionDialog>

  <!--新增中类对话框-->
  <NewClassifyGroupDialog
    :dialogVisible="dialogNewMaterialClassifyGroupVisible"
    :materialClassifyDivision="updateMaterialClassifyDivision"
    @onDilalogCancel="onNewMaterialClassifyGroupDialogCancel"
    @onDilalogOk="onNewMaterialClassifyGroupDialogOk"
  ></NewClassifyGroupDialog>

  <!--修改中类对话框-->
  <UpdateClassifyGroupDialog
    :dialogVisible="dialogUpdateMaterialClassifyGroupVisible"
    :materialClassifyDivision="updateMaterialClassifyDivision"
    :materialClassifyGroup="updateMaterialClassifyGroup"
    @onDilalogCancel="onUpdateMaterialClassifyGroupDialogCancel"
    @onDilalogOk="onUpdateMaterialClassifyGroupDialogOk"
  ></UpdateClassifyGroupDialog>

  <!--新增小类对话框-->
  <NewClassifySectionDialog
    :dialogVisible="dialogNewMaterialClassifySectionVisible"
    :materialClassifyDivision="updateMaterialClassifyDivision"
    :materialClassifyGroup="updateMaterialClassifyGroup"
    @onDilalogCancel="onNewMaterialClassifySectionDialogCancel"
    @onDilalogOk="onNewMaterialClassifySectionDialogOk"
  ></NewClassifySectionDialog>

  <!--修改小类对话框-->
  <UpdateClassifySectionDialog
    :dialogVisible="dialogUpdateMaterialClassifySectionVisible"
    :materialClassifyGroup="updateMaterialClassifyGroup"
    :materialClassifySection="updateMaterialClassifySection"
    @onDilalogCancel="onUpdateMaterialClassifySectionDialogCancel"
    @onDilalogOk="onUpdateMaterialClassifySectionDialogOk"
  ></UpdateClassifySectionDialog>

  <!--上传Excel文件对话框-->
  <UploadExcelClassifyDialog
    :dialogVisible="dialogFormExcelVisible"
    @onDilalogCancel="onExcelUploadDialogCancel"
    @onDilalogOk="onExcelUploadDialogOk"
  ></UploadExcelClassifyDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">分类管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <el-button
          :icon="Plus"
          type="primary"
          @click="onNewMaterialClassifyDivisionButtonClick"
        >
          新增大类(专业)
        </el-button>
        <el-button :icon="Upload" @click="onExcelUploadButtonClick">
          导入分类（Excel）
        </el-button>
        <el-button :icon="Download" @click="onDownloadExcelButtonClick">
          导出分类（Excel）
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
              <el-option label="名称" value="名称" />
            </el-select>
          </template>
          <template #append>
            <el-button :icon="Search" @click="onSearchClick" />
          </template>
        </el-input>
      </div>
    </div>

    <!--显示内容-->
    <div class="tree-container">
      <el-tree
        :data="dataSource"
        node-key="id"
        accordion
        default-expand-all
        :expand-on-click-node="false"
      >
        <template #default="{ node, data }">
          <span class="custom-tree-node">
            <span>{{ node.label }}</span>
            <span>
              <span
                class="custom-tree-node-item"
                @click="newClassifyNode(data)"
              >
                {{ getAddButtonTitle(data) }}
              </span>
              <span class="custom-tree-node-item" @click="remove(node, data)">
                删除
              </span>
              <span
                class="custom-tree-node-item"
                @click="updateNode(node, data)"
              >
                修改
              </span>
            </span>
          </span>
        </template>
      </el-tree>
    </div>
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

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.tree-container {
  margin: 10px;
}

.custom-tree-node-item {
  margin-left: 10px;
}

.custom-tree-node-item:hover {
  cursor: pointer;
  color: #409eff;

  font-weight: bold;
  font-size: 14px;
}
</style>
