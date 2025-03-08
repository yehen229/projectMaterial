import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";

import {
  IServerProjectReviewMode,
  IServerProjectReviewModeView,
  IServerProjectReview,
  IServerProjectReviewView,
  IServerProjectReviewUser,
  IServerProjectReviewUserView,
  IServerProjectReviewUserFile,
  IServerProjectReviewUserFileView,
  IServerProjectReviewForm,
  IServerProjectReviewDispatchForm,
  IServerProjectReviewEmployeeForm,
  IServerProjectCompany,
  IServerProjectReviewManagerForm,
  IServerUseMaterialForm,
  IServerUseMaterialBrandSelectView,
  IServerAppearanceReviewForm,
  IServerProjectAppearanceReviewEmployeeForm,
  IServerProjectAppearanceReviewDispatchForm,
  IServerProjectAppearanceReviewManagerSummaryForm,
  IServerProjectAppearanceReviewManagerDirectForm,
  IServerProjectAppearanceReviewUserView,
  IServerBuyMaterial,
  IServerBuyMaterialForm,
  IServerProjectMaterialRetestForm,
  IServerProjectMaterialVerificationDocumentView,
  IServerUseMaterialView,
  IServerProjectMaterialAcceptanceBatchForm,
  IServerProjectMaterialAcceptanceView,
  IServerProjectMaterialAcceptanceReviewEmployeeForm,
  IServerProjectMaterialAcceptanceReviewManagerDirectForm,
  IServerProjectMaterialAcceptanceReviewManagerSummaryForm,
  IServerProjectMaterialAcceptanceReviewDispatchForm,
  IServerProjectMaterialAcceptanceReviewUserView,
  IServerGeneralContractorSubProjectMaterialVerificationDocumentForm,
} from "@/server/types/project/review";

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
  IServerProject,
  IServerProjectBrand,
  IServerProjectBrandView,
  IServerProjectBrandForm,
  IServerProjectMaterial,
  IServerProjectMaterialView,
  IServerProjectMaterialForm,
  IServerProjectForm,
} from "@/server/types/project/project";

import {
  IServerProjectUserTask,
  IServerProjectHistory,
  IServerProjectOpHistoryView,
  IServerProjectUserCompletedTask,
} from "@/server/types/project/flow";

import {
  IServerProjectEnd,
  IServerProjectEndFile,
  IServerProjectEndFileView,
  IServerProjectEndForm,
  IServerProjectEndView,
} from "@/server/types/project/end";

/**
 * 启动流程
 * @param project 项目
 * @returns
 */
export async function serverStartProcess(
  projectForm: IServerProjectForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "start-process",
      projectForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverStartProcessByProjectId(
  projectId: string
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "start-process-by-project-id",
      projectId
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 提交审核
 * @param project 项目
 * @returns
 */
export async function serverDesignCompanySubmitProjectMaterial(
  project: IServerProject
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "design-company-submit-project-material",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverSubmitAdminSetDesignAndEngineeringDepartmentUsers(
  project: IServerProject
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "admin-set-design-and-engineering-department-users",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverSubmitAdminSetSuperVisionAndGeneralContractorEmployee(
  project: IServerProject
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "admin-set-supervision-and-general-contractor-employee",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 设计部项目经理直接终审项目物料
 * @param projectReviewForm 项目审核表单
 * @returns
 */
export async function serverSubmitProjectMaterialReviewOfManagerDirect(
  projectReviewForm: IServerProjectReviewForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "design-department-manager-direct",
      projectReviewForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 设计部项目经理将项目材料分发给项目员工进行初审
 * @param projectReviewDispatchForm
 * @returns
 */
export async function serverSubmitProjectMaterialReviewManagerDistributeToEmployees(
  projectReviewDispatchForm: IServerProjectReviewDispatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "design-department-manager-dispatch-to-employee",
      projectReviewDispatchForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 设计部项目员工审核项目物料
 * @param projectReviewEmployeeForm 项目员工物料审核表单
 * @returns
 */
export async function serverSubmitProjectMaterialReviewOfEmployee(
  projectReviewEmployeeForm: IServerProjectReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "design-department-review-of-employee",
      projectReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverSubmitProjectMaterialRemainReviewOfManager(
  projectReviewEmployeeForm: IServerProjectReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "design-department-remain-review-manager",
      projectReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 设计部项目经理对项目材料进行汇总终审
 * @param projectReviewManagerForm 汇总终审表单
 * @returns
 */
export async function serverSubmitProjectMaterialReviewOfManagerSummary(
  projectReviewManagerForm: IServerProjectReviewManagerForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "design-department-review-manager-summary",
      projectReviewManagerForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 工程部项目经理分派项目的监理单位与总包单位
 * @param projectCompany 项目的监理单位、总包单位
 * @returns
 */
export async function serverSubmitEngineeringDepartmentManagerDispatch(
  projectCompany: IServerProjectCompany
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "engineering-department-manager-dispatch",
      projectCompany
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 总包单位提交物料的品牌选择
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitGeneralContractorSelectBrand(
  useMaterialForm: IServerUseMaterialForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "general-contractor-select-brand",
      useMaterialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 总包单位新建任务：提交物料的品牌选择
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitGeneralContractorNewTaskOfSelectBrand(
  useMaterialForm: IServerUseMaterialForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "general-contractor-new-task-of-select-brand",
      useMaterialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 监理单位对总包单位提交的品牌、物料进行审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitSupervisionCompanyReviewSelectBrand(
  appearanceReviewForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "supervision-company-review-select-brand",
      appearanceReviewForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：工程部经理直接审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(
  projectAppearanceReviewManagerDirectForm: IServerProjectAppearanceReviewManagerDirectForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-engineering-department-review-manager-direct",
      projectAppearanceReviewManagerDirectForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：工程部经理分发给员工
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(
  useMaterialForm: IServerProjectAppearanceReviewDispatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-engineering-department-manager-dispatch-to-employee",
      useMaterialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：工程部员工审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfEngineeringDepartmentEmployee(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-engineering-department-review-of-employee",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：工程部项目经理审核

 * @param projectAppearanceReviewEmployeeForm 
 * @returns 
 */
export async function serverSubmitAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-engineering-department-remain-review-manager",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：工程部经理汇总
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(
  projectAppearanceReviewManagerSummaryForm: IServerProjectAppearanceReviewManagerSummaryForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-engineering-department-review-manager-summary",
      projectAppearanceReviewManagerSummaryForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：设计公司进行审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfDesignCompany(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "affect-appearance-design-company-review",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：设计部经理直接审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfDesignDepartmentManagerDirect(
  projectAppearanceReviewManagerDirectForm: IServerProjectAppearanceReviewManagerDirectForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-design-department-review-manager-direct",
      projectAppearanceReviewManagerDirectForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：设计部经理分发给员工
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfDesignDepartmentManagerDispatchToEmployee(
  projectReviewDispatchForm: IServerProjectAppearanceReviewDispatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-design-department-review-manager-dispatch-to-employee",
      projectReviewDispatchForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：设计部员工审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitAffectAppearanceReviewOfDesignDepartmentEmployee(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-design-department-review-of-employee",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverSubmitAffectAppearanceRemainReviewOfDesignDepartmentOfManager(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-design-department-remain-review-manager",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 影响外观：送审操作：设计部部经理汇总

 * @param projectAppearanceReviewManagerSummaryForm 
 * @returns 
 */
export async function serverSubmitAffectAppearanceReviewOfDesignDepartmentManagerSummary(
  projectAppearanceReviewManagerSummaryForm: IServerProjectAppearanceReviewManagerSummaryForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "affect-appearance-design-department-review-manager-summary",
      projectAppearanceReviewManagerSummaryForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 不影响外观：送审操作：工程部经理直接审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(
  projectAppearanceReviewManagerDirectForm: IServerProjectAppearanceReviewManagerDirectForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "not-affect-appearance-engineering-department-review-manager-direct",
      projectAppearanceReviewManagerDirectForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 不影响外观：送审操作：工程部经理分发给员工
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(
  projectReviewDispatchForm: IServerProjectAppearanceReviewDispatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "not-affect-appearance-engineering-department-review-manager-dispatch-to-employee",
      projectReviewDispatchForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 不影响外观：送审操作：工程部员工审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitNotAffectAppearanceReviewOfEngineeringDepartmentEmployee(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "not-affect-appearance-engineering-department-review-of-employee",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 不影响外观：送审操作：工程部经理填写意见
 * @param projectAppearanceReviewEmployeeForm
 * @returns
 */
export async function serverSubmitNotAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(
  projectAppearanceReviewEmployeeForm: IServerProjectAppearanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "not-affect-appearance-engineering-department-remain-review-manager",
      projectAppearanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 不影响外观：送审操作：工程部经理汇总
 * @param projectReviewManagerForm
 * @returns
 */
export async function serverSubmitNotAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(
  projectAppearanceReviewManagerSummaryForm: IServerProjectAppearanceReviewManagerSummaryForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "not-affect-appearance-engineering-department-review-manager-summary",
      projectAppearanceReviewManagerSummaryForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 总包单位订购
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitGeneralContractorBuyProjectMaterial(
  buyMaterialForm: IServerBuyMaterialForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow + "general-contractor-buy-project-materials",
      buyMaterialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function submitGeneralContractorNewTaskOfBuyProjectMaterial(
  buyMaterialForm: IServerBuyMaterialForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "general-contractor-new-task-of-buy-project-materials",
      buyMaterialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 总包单位填报工程材料、设备报验资料
 * @param project
 * @returns
 */
export async function serverSubmitGeneralContractorSubProjectMaterialVerificationDocument(
  project: IServerGeneralContractorSubProjectMaterialVerificationDocumentForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "general-contractor-submit-material-verification-document",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 监理判断是否需要复检
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitSupervisionCompanyDecideWhetherToRecheck(
  projectMaterialRetestForm: IServerProjectMaterialRetestForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "supervision-company-decide-whether-to-recheck",
      projectMaterialRetestForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 材料验收：总包单位提交项目验收
 */
export async function serverSubmitGeneralContractorProjectMaterialBatchAcceptancet(
  projectMaterialAcceptanceBatchForm: IServerProjectMaterialAcceptanceBatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "general-contractor-submit-project-material-batch-acceptance",
      projectMaterialAcceptanceBatchForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 材料验收：总包单位多次项目验收
 */
export async function serversubmitGeneralContractorNewTaskOfProjectMaterialAcceptance(
  projectMaterialAcceptanceBatchForm: IServerProjectMaterialAcceptanceBatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "general-contractor-submit-new-task-of-project-material-batch-acceptance",
      projectMaterialAcceptanceBatchForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 材料验收：监理单位对项目验收进行审核
 */
export async function serverSubmitSupervisionCompanyProjectMaterialBatchAcceptanceReview(
  projectMaterialAcceptanceReviewEmployeeForm: IServerProjectMaterialAcceptanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "supervision-company-project-material-batch-acceptance-review",
      projectMaterialAcceptanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 材料验收：送审操作：工程部经理直接审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDirect(
  projectMaterialAcceptanceReviewManagerDirectForm: IServerProjectMaterialAcceptanceReviewManagerDirectForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "project-material-acceptance-engineering-department-review-manager-direct",
      projectMaterialAcceptanceReviewManagerDirectForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 材料验收：送审操作：工程部经理分发给员工
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(
  projectReviewDispatchForm: IServerProjectMaterialAcceptanceReviewDispatchForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "project-material-acceptance-engineering-department-manager-dispatch-to-employee",
      projectReviewDispatchForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 材料验收：送审操作：工程部员工审核
 * @param useMaterialForm
 * @returns
 */
export async function serverSubmitProjectMaterialAcceptanceReviewOfEngineeringDepartmentEmployee(
  projectMatrialAcceptanceReviewEmployeeForm: IServerProjectMaterialAcceptanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "project-material-acceptance-engineering-department-review-of-employee",
      projectMatrialAcceptanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverSubmitProjectMaterialAcceptanceRemainReviewOfEngineeringDepartmentOfManager(
  projectMatrialAcceptanceReviewEmployeeForm: IServerProjectMaterialAcceptanceReviewEmployeeForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "project-material-acceptance-engineering-department-remain-review-manager",
      projectMatrialAcceptanceReviewEmployeeForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 材料验收：送审操作：工程部经理汇总
 * @param projectReviewManagerForm
 * @returns
 */
export async function serverSubmitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerSummary(
  projectMatrialAcceptanceReviewManagerSummaryForm: IServerProjectMaterialAcceptanceReviewManagerSummaryForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "project-material-acceptance-engineering-department-review-manager-summary",
      projectMatrialAcceptanceReviewManagerSummaryForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 工程部项目经理结项
 * @param projectAppearanceReviewManagerSummaryForm
 * @returns
 */

export async function serverSubmitEngineeringDepartmentCloseProject(
  projectEndForm: IServerProjectEndForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterialflow +
        "engineering-department-manager-end-the-project",
      projectEndForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 获取当前登录用户任务
 * @returns
 */
export async function serverGetTaskByCurrentLoginUser(): Promise<
  IServerResponseData<IServerProjectUserTask[]>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectUserTask[]>
    >(BASEURL.projectmaterialflow + "get-task-by-current-login-user");
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 获得当前登录用户已完成的任务
 * @returns
 */
export async function serverGetCompletedTaskByCurrentLoginUser(): Promise<
  IServerResponseData<IServerProjectUserCompletedTask[]>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectUserCompletedTask[]>
    >(BASEURL.projectmaterialflow + "get-completed-task-by-current-login-user");
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 获得当前用户在指定项目中的任务
 * @param projectId 项目id
 * @returns
 */
export async function serverGetTaskByCurrentLoginUserAndProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectUserTask>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUserTask>>(
      BASEURL.projectmaterialflow +
        "get-task-by-current-login-user-and-project-id",
      {
        params: {
          projectId: projectId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 获得当前用户在指定项目中的任务
 * @param projectId 项目id
 * @returns
 */
export async function serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectUserTask>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUserTask>>(
      BASEURL.projectmaterialflow +
        "get-task-by-current-login-user-and-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据项目号获取项目的历史审批记录
 * @param projectId
 * @returns
 */
export async function serverGetTaskHistoryByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectHistory[]>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectHistory[]>
    >(BASEURL.projectmaterialflow + "get-history-by-project-id", {
      params: {
        projectId: projectId,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetHistoryViewPageByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectOpHistoryView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectOpHistoryView>>
    >(BASEURL.projectmaterialflow + "page-history-view-by-project-id", {
      params: {
        projectId: projectId,
        pageNo: pageNo,
        pageSize: pageSize,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerUseMaterialBrandSelectView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerUseMaterialBrandSelectView>
    >(
      BASEURL.projectmaterialflow +
        "get-use-material-brand-select-view-by-current-login-user-and-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjecMaterialAcceptancePageViewByTaskId(
  projectId: string,
  taskId: string,
  pageNo: number,
  pageSize: number
): Promise<
  IServerResponseData<IServerPage<IServerProjectMaterialAcceptanceView>>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialAcceptanceView>>
    >(
      BASEURL.projectmaterialflow +
        "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectAppearanceReviewUserViewPageByTaskId(
  projectId: string,
  taskId: string,
  pageNo: number,
  pageSize: number
): Promise<
  IServerResponseData<IServerPage<IServerProjectAppearanceReviewUserView>>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectAppearanceReviewUserView>>
    >(
      BASEURL.projectmaterialflow +
        "page-appearance-review-user-view-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetPageProjectMaterialAcceptanceReviewUserViewByProjectIdAndTaskId(
  projectId: string,
  taskId: string,
  pageNo: number,
  pageSize: number
): Promise<
  IServerResponseData<
    IServerPage<IServerProjectMaterialAcceptanceReviewUserView>
  >
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<
        IServerPage<IServerProjectMaterialAcceptanceReviewUserView>
      >
    >(
      BASEURL.projectmaterialflow +
        "page-material-acceptance--review-user-view-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectAppearanceReviewDesignDepartmentUserViewPageByTaskId(
  projectId: string,
  taskId: string,
  pageNo: number,
  pageSize: number
): Promise<
  IServerResponseData<IServerPage<IServerProjectAppearanceReviewUserView>>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectAppearanceReviewUserView>>
    >(
      BASEURL.projectmaterialflow +
        "page-appearance-review-design-department-user-view-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectAppearanceReviewUserViewListByTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectAppearanceReviewUserView[]>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAppearanceReviewUserView[]>
    >(
      BASEURL.projectmaterialflow +
        "list-appearance-review-user-view-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetBuyMaterialRecheckIsRequiredByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<
  IServerResponseData<IServerProjectMaterialVerificationDocumentView[]>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectMaterialVerificationDocumentView[]>
    >(
      BASEURL.projectmaterialflow +
        "list-recheck-is-required-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetBuyMaterialVerificationDocumentViewByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<
  IServerResponseData<IServerProjectMaterialVerificationDocumentView[]>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectMaterialVerificationDocumentView[]>
    >(
      BASEURL.projectmaterialflow +
        "list-verification-document-view-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetFeedbackOfProjectMaterialReviewedByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectReviewUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-project-material-reviewed-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetFeedbackOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectAppearanceReviewUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAppearanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-supervision-company-select-brand-reviewed-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetFeedbackOfProjectMaterialAcceptanceReviewedOfSupervisionCompanyByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<
  IServerResponseData<IServerProjectMaterialAcceptanceReviewUserView>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectMaterialAcceptanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-material-acceptance-review-of-supervisioncompany-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetFeedbackOfProjectMaterialAcceptanceReviewedOfEngineeringDepartmentByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<
  IServerResponseData<IServerProjectMaterialAcceptanceReviewUserView>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectMaterialAcceptanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-material-acceptance-review-of-engineeringdepartment-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetFeedbackOfAffectAppeaReviewedOfEngineeringDepartmentByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectAppearanceReviewUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAppearanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-affect-appearance-review-of-engineeringdepartment-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetFeedbackOfNeedReCheckNotPassedOfSupervisionCompanyByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectMaterialRetestForm>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectMaterialRetestForm>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-need-recheck-not-passed-of-supervision-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetFeedbackOfNotAffectAppeaReviewedOfEngineeringDepartmentByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectAppearanceReviewUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAppearanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of--not-affect-appearance-review-of-engineeringdepartment-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function servergetFeedbackOfAffectAppearanceReviewOfDesignCompanyByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectAppearanceReviewUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAppearanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-affect-appearance-review-of-designdcompany-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetFeedbackOfAffectAppearanceReviewOfDesignDepartmentByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectAppearanceReviewUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAppearanceReviewUserView>
    >(
      BASEURL.projectmaterialflow +
        "get-feedback-of-affect-appearance-review-of-designdepartment-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerUseMaterialBrandSelectView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerUseMaterialBrandSelectView>
    >(
      BASEURL.projectmaterialflow +
        "get-not-passed-brand-supervision-company-select-brand-reviewed-by-project-id-and-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
