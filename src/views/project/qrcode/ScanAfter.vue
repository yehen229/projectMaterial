<script setup lang="ts">
import {
  ElDescriptions,
  ElDescriptionsItem,
  ElTag,
  ElMessage,
  FormItemProps,
  FormProps,
  ElMessageBox
} from 'element-plus';
import {computed, onMounted, reactive, ref, Ref} from 'vue'
import {
  deleteUrl,
  getBlobPartdata,
  getCompanyInfo, getFilebyqrcode,
  getListMaterialInfo,
  getmaterialbrand,
  getMaterialInfo, getmaterialPhoto, getOnlyBlob,
  getProjectInfo
} from "@/server/project/aboutqrcode";
import {serverGetMaterialPhotoFileById} from "@/server/system/materialphoto";
import {serverGetMaterialViewById} from "@/server/system/material";
import {serverGetHistoryViewPageByProjectId} from "@/server/project/projectmaterialflow";
import {formatDate} from "@/utils/utils";
import {serverDownloadBuyMaterialVerificationDocumentFilesById} from "@/server/project/usematerial";

import VueOfficeDocx from '@vue-office/docx';
import '@vue-office/docx/lib/index.css';
//引入VueOfficeExcel组件
import VueOfficeExcel from '@vue-office/excel'
//引入相关样式
import '@vue-office/excel/lib/index.css'
import VueOfficePdf from '@vue-office/pdf'

import VueOfficePptx from '@vue-office/pptx'
// import '@vue-office/pptx/lib/index.css'

onMounted(async () => {
  // refreshPage();
  getqrcode();
  await fetchUserInfo();
  await getprojectinfo();
  await getCompany();
  await getmaterialbrandInfo();
  await getImageList();
  await getmaterialClass();
  await getprojectTime();
  await getprojectFile();

});
// 获取存储在本地的qrcode
const qrcode = ref("")
const getqrcode = () => {
  qrcode.value = JSON.parse(localStorage.getItem('qrcode'));
  // console.log(qrcode.value)

}

// 刷新
function refreshPage() {
  if (!localStorage.getItem('hasRefreshed')) {
    window.location.reload();
    localStorage.setItem('hasRefreshed', 'true');
  }
}

// 获取材料品牌信息
const brandInfo = ref({})
const getmaterialbrandInfo = async () => {
  const response = await getmaterialbrand(qrcode.value)
  if (response.code == 200) {
    brandInfo.value = response.data;

  } else {
    ElMessage.error(response.message)
  }
};

// 获取项目公司信息接口
const companyInfo = ref({});

const companyConstruction = ref({});
let companyConstructioncompanyType = ref()
const companyDesign = ref();
const companySupervision = ref({});
const companyGeneralContract = ref({});
const getCompany = async () => {
  const response = await getCompanyInfo(qrcode.value)
  if (response.code == 200) {
    companyInfo.value = response.data;
    // const {  companyConstruction, companyDesign, companySupervision, companyGeneralContract } = response.data;

    companyConstruction.value = response.data.companyConstruction;

    // companyConstructioncompanyType=response.data.companyConstruction.companyType

    console.log(companyConstruction.value.companyType);
    companyDesign.value = response.data.companyDesignList;
    console.log(companyDesign.value);
    companySupervision.value = response.data.companySupervision;
    companyGeneralContract.value = response.data.companyGeneralContract;
    // console.log("companyInfo", companyInfo.value);
  } else {
    ElMessage.error(response.message)
  }
};
// 获取材料信息接口
const maerialId = ref("");
const material = ref({
  materialInfo: {
    id: "",
    materialClassifySectionId: "",
    name: "",
    itemMark: "",
    location: "",
    technology: "",
    material: "",
    color: "",
    dimension: "",
    fireRating: "",
    installation: "",
    deletedAt: "",
  },
  materialCount: "",
  materialUnit: "",
  batch: ""
});
const fetchUserInfo = async () => {
  const response = await getMaterialInfo(qrcode.value)
  if (response.code == 200) {
    material.value = response.data;
    maerialId.value = material.value.materialInfo.id;
    // console.log("maerialId",maerialId.value);
    // console.log("materials", materials.value);
  } else {
    ElMessage.error(response.message)
  }
};
const activeNames = ref(['1'])

// 获取项目信息
const project = ref({})

const getprojectinfo = async () => {
  const response = await getProjectInfo(qrcode.value);
  if (response.code == 200) {
    project.value = response.data;
    // 对地点进行处理
    let locationArray = JSON.parse(project.value.location)
    // console.log(locationArray);
    if (locationArray[0] == locationArray[1]) {
      project.value.location = locationArray[0];
    } else {
      let locationString = locationArray.join(':');

      // console.log(locationString);
    }

    project.value.endDatetime = formatDateToYMD(project.value.endDatetime)
    project.value.createDatetime = formatDateToYMD(project.value.createDatetime)
    if (project.value.endDatetime == null) {
      project.value.endDatetime = "至今"
    }
    // console.log("project", project.value);
  } else {
    ElMessage.error(response.message)
  }
}
// 格式化年月日
const formatDateToYMD = (isoDateString) => {
  if (isoDateString == null) {
    return
  }
  const date = new Date(isoDateString);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，所以加1
  const day = date.getDate().toString().padStart(2, '0');
  console.log("年月日：", year, month, day);
  return `${year}-${month}-${day}`;
}

// 照片信息
const srcList = ref(["src/assets/img.png"])
// 描述列表尺寸
const size = ref("large")
const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '6px',
    small: '4px',
  }
  return {
    marginRight: marginMap[size.value] || marginMap.default,
  }
})

/*获取图片列表*/
const materialphotoList = ref([])
const imageList = ref([])
const getImageList = async () => {
  let response = await getmaterialPhoto(qrcode.value)
  if (response && response.code == 200) {
    materialphotoList.value = response.data;
    // console.log("materialphotoList",materialphotoList.value);
    //   遍历循环materialphotoList取出id
    for (let item of materialphotoList.value) {
      let materialphotoId = item.id; // 假设每个item都有一个materialId属性

      // 通过materialId发起请求获取图片列表
      let imageResponse = await serverGetMaterialPhotoFileById(materialphotoId); // 假设getReviewImage是一个函数，用于根据materialId获取图片列表
      if (imageResponse && imageResponse.code == 200) {
        // 将获取到的图片列表存储到imageList中
        imageList.value.push(imageResponse.data);
      }
    }
    // console.log("imageList",imageList);
    //   通过materialId发起请求获取图片列表存入imageList

  }
  return "";
}
/*获取材料类别*/
const materialClass = ref("")
const getmaterialClass = async () => {
  let response = await serverGetMaterialViewById(maerialId.value)
  if (response.code == 200) {
    let materialClassifyDivision = response.data.materialClassifySectionView.materialClassifyDivision.name
    let materialClassifyGroup = response.data.materialClassifySectionView.materialClassifyGroup.name
    let materialClassifySection = response.data.materialClassifySectionView.materialClassifySection.name
    materialClass.value = materialClassifyDivision + "/" + materialClassifyGroup + "/" + materialClassifySection
    // console.log(materialClassifyDivision,materialClassifyGroup,materialClassifySection)
    // console.log("materialClass",materialClass.value)
  }
}

// 项目进程信息
const projectTimeProcess = ref([])
const getprojectTime = async () => {
  let response = await serverGetHistoryViewPageByProjectId(project.value.id, 1, 10000);
  if (response.code == 200) {
    projectTimeProcess.value = response.data.result;
    console.log("projectTimeProcess", projectTimeProcess.value)
  }
}

// 获取材料文件信息
const projectFile = ref()
const projectid = ref("")
const getprojectFile = async () => {
  let response = await getFilebyqrcode(qrcode.value);
  if (response.code == 200) {
    projectFile.value = response.data[0].projectMaterialVerificationDocumentFileList;
    projectid.value = response.data[0].buyMaterialView.useMaterialView.projectMaterialView.projectMaterial.projectId;
    userInfo.value = response.data[0].user
    console.log(projectid.value)
    console.log("projectFile", projectFile.value)
  }
}

// 文件信息                blob:http://localhost:5175/0bef4978-8192-4d17-af15-de7c86c3a207
const url = ref("")
const fileextension = ref("")
const userInfo = ref()
const blobdata = ref()
const childRef = ref(null);
const getView = async (item, path: string) => {
  console.log(item)
  const ret = await getBlobPartdata(
      projectid.value,
      item.id,
      path
  );
  const blob = await getOnlyBlob(
      projectid.value,
      item.id,
      path
  );
  //   获取尾数的类型
  const extension = path.split('.').pop().toLowerCase();
  if (extension != "docx" && extension != "xlsx" && extension != "pdf" && extension != "pptx" && extension != "txt") {
    ElMessageBox.confirm(
        '该文件类型不支持在线预览，是否选择下载',
        {
          confirmButtonText: '确认下载',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
        .then(() => {
          ElMessage({
            type: 'success',
            message: '开始下载',
          })
          serverDownloadBuyMaterialVerificationDocumentFilesById(projectid.value, item.id, path)
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '取消成功',
          })
        })
    return;
  }


  url.value = ret;
  fileextension.value = extension;
  blobdata.value = blob

  if (extension == "docx") {


  }

  console.log("文件信息：", ret);
  console.log("bolbdata", blob)
  console.log("extension", extension)
  dialogVisible.value = true
}


const processengineeringfileName = (filename: string, user, index, filetype) => {
  console.log(filetype)
  if (filetype == 0) {
    index = index + 1
    //   获取尾数的类型
    const extension = filename.split('.').pop().toLowerCase();
    console.log(extension);
    let name = user.realName + '_' + user.tel + "附件" + index + '.' + extension;
    return name;
  }

}

const processequipmentfileName = (filename: string, user, index, filetype) => {
  console.log(filetype)
  if (filetype == 1) {
    index = index + 1
    //   获取尾数的类型
    const extension = filename.split('.').pop().toLowerCase();
    console.log(extension);
    let name = user.realName + '_' + user.tel + "附件" + index + '.' + extension;
    return name;
  }

}

// 弹出框
const dialogVisible = ref(false)
const handleClose = async (done: () => void) => {
  const ret = await deleteUrl(url.value)
  console.log("退出弹出框", ret + '__' + url.value)
  dialogVisible.value = false
}
const actives = ref(['1','2','3','4'])

</script>

<template>

  <el-dialog
      v-model="dialogVisible"
      title="预览文件"
      width="70%"
      :before-close="handleClose"
      :fullscreen="true"
  >
    <el-card>

      <!--    pdf-->
      <iframe v-if=" fileextension =='txt'" :src="url"
              style="width: 100%;height: 100%;min-height: 1000px;"></iframe>
      <vue-office-pdf
          v-if="fileextension =='pdf'"
          :src="url"
          style="height: 100vh"
      />
      <!-- docx 文件-->
      <vue-office-docx v-if="fileextension =='docx'" :src="url"/>
      <!--    excel文件-->
      <vue-office-excel
          v-if="fileextension =='xlsx'"
          :src="url"
          style="height: 100vh;width: 100%"
      />
      <!-- ppt文件   -->
      <vue-office-pptx
          v-if="fileextension =='pptx'"
          :src="url"
          style="height: 100vh;margin-left: 25%;margin-top: 10%"
      />
    </el-card>
  </el-dialog>


  <el-card>
    <el-collapse v-model="actives">
      <el-collapse-item title="项目信息" name="1">
        <el-descriptions
            class="margin-top"
            :column="3"
            :size="size"
            border
        >

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <user/>
                </el-icon>
                项目名称
              </div>
            </template>
            {{ project.name }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <location/>
                </el-icon>
                项目地点
              </div>
            </template>
            {{ project.location }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <office-building/>
                </el-icon>
                {{ companyConstruction.companyType }}
                <!--             {{companyConstructioncompanyType}}-->
              </div>
            </template>
            {{ companyConstruction.name }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <tickets/>
                </el-icon>
                设计单位

                <!--                {{ companyDesign.companyType }}-->
              </div>
            </template>
            <span v-for="(item, index) in companyDesign ">
                    <div>{{ item.name }}</div>
                </span>
            <!--            {{ companyDesign.name }}-->
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <office-building/>
                </el-icon>
                {{ companySupervision.companyType }}
              </div>
            </template>
            {{ companySupervision.name }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <office-building/>
                </el-icon>
                {{ companyGeneralContract.companyType }}
              </div>
            </template>
            {{ companyGeneralContract.name }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <office-building/>
                </el-icon>
                项目起止时间
              </div>
            </template>
            {{ project.createDatetime + ' - ' + project.endDatetime }}
          </el-descriptions-item>
        </el-descriptions>

      </el-collapse-item>

      <el-collapse-item title="材料订购信息" name="2" v-model="activeNames">
        <el-descriptions style="margin-top: 1%" title="" border>
          <!--          <el-descriptions-item label="归属项目">{{ project.name }}</el-descriptions-item>-->
          <el-descriptions-item label="名字">{{ material.materialInfo.name }}</el-descriptions-item>
          <el-descriptions-item label="材料类别">{{ materialClass }}</el-descriptions-item>

          <el-descriptions-item label="品牌名字">{{ brandInfo.name }}</el-descriptions-item>
          <el-descriptions-item label="产地">{{ brandInfo.position }}</el-descriptions-item>

          <el-descriptions-item label="编号">{{ material.materialInfo.itemMark }}</el-descriptions-item>
          <el-descriptions-item label="材料数量">{{ material.materialCount }}</el-descriptions-item>

          <el-descriptions-item label="材料数量单位">{{ material.materialUnit }}</el-descriptions-item>

          <el-descriptions-item label="材料批次">{{ material.batch }}</el-descriptions-item>

          <el-descriptions-item label="存放位置">{{ material.materialInfo.location }}</el-descriptions-item>


          <el-descriptions-item label="颜色">
            {{ material.materialInfo.color }}
          </el-descriptions-item>
          <el-descriptions-item label="材料材质">{{ material.materialInfo.material }}</el-descriptions-item>

          <el-descriptions-item label="防火等级">{{ material.materialInfo.fireRating }}</el-descriptions-item>
          <el-descriptions-item label="规格">{{ material.materialInfo.dimension }}</el-descriptions-item>


          <el-descriptions-item label="施工要求">{{ material.materialInfo.installation }}</el-descriptions-item>
          <el-descriptions-item
              :rowspan="5"
              :width="140"
              label="照片（更多照片请点击图片查看）"
              align="center"
          >
            <el-image
                style="width: 100%; height: 100%"
                :src=imageList[0]
                :preview-src-list=imageList
            />
          </el-descriptions-item>


          <el-descriptions-item label="技术要求" :rowspan="4">{{
              material.materialInfo.technology
            }}
          </el-descriptions-item>

        </el-descriptions>
      </el-collapse-item>

      <el-collapse-item title="文件信息" name="3">
        <el-descriptions
            :column="1"
            border
        >
          <el-descriptions-item label="工程材料">
            <div>
              <div v-for="(item, index) in projectFile" :key="index">
                <div @click="getView(item,item.filePath)" style="color: #409eff;margin-top: 6px">
                  {{ processengineeringfileName(item.filePath, userInfo, index, item.fileType) }}
                </div>
              </div>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="设备报验材料">
            <div>
              <div v-for="(item, index) in projectFile" :key="index" style="color: #409eff;margin-top: 6px">
                <div @click="getView(item,item.filePath)">
                  {{ processequipmentfileName(item.filePath, userInfo, index, item.fileType) }}
                </div>
              </div>
            </div>
          </el-descriptions-item>
        </el-descriptions>

      </el-collapse-item>
      <el-collapse-item title="项目历程信息" name="4">

        <el-timeline style="max-width:50%">
          <el-timeline-item
              type="primary"
              :hollow=true
              placement="top"
              v-for="(item, index) in projectTimeProcess"
              :key="index"
              :timestamp="formatDate(item.projectOpHistory.opDatetime)"
          >
            <el-card>
              <el-descriptions
                  :column="1"
                  border
              >
                <el-descriptions-item label="阶段">
                  <el-tag type="primary"> {{ item.projectOpHistory.stepPhase }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="操作">{{ item.projectOpHistory.stepDescription }}</el-descriptions-item>
                <el-descriptions-item label="操作人员">{{ item.user.realName }}</el-descriptions-item>
                <el-descriptions-item label="单位或部门" v-if="item.company">{{
                    item.company.name
                  }}
                </el-descriptions-item>

              </el-descriptions>
            </el-card>
          </el-timeline-item>

        </el-timeline>

      </el-collapse-item>

    </el-collapse>
  </el-card>

</template>

<style scoped>
.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>