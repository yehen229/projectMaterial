package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

public interface IProjectBusinessService {

    String add(ProjectForm projectForm);

    String addProjectMaterialReviewTempFile(String uploadReviewFilesDir,
                                            MultipartFile multipartFile);

    boolean deleteProjectMaterialReviewTempFile(String uploadReviewFilesDir,
                                                String fileName);

    String addUseMaterialNewBrandTempFile(String uploadReviewFilesDir,
                                          MultipartFile multipartFile);

    boolean deleteUseMaterialNewBrandTempFile(String uploadReviewFilesDir,
                                              String fileName);

    String addUseMaterialAppearanceReviewTempFile(String uploadReviewFilesDir,
                                                  MultipartFile multipartFile);

    boolean deleteUseMaterialAppearanceReviewTempFile(String uploadReviewFilesDir,
                                                      String fileName);

    String addBuyMaterialVerificationDocumentFile(String buyMaterialId,
                                                  MultipartFile multipartFile,
                                                  int fileType);

    int deleteBuyMaterialVerificationDocumentFileById(String projectMaterialVerificationDocumentFileId);


    String addForm(ProjectReviewForm projectReviewForm);

    String addForm(ProjectReviewDispatchForm projectReviewDispatchForm);

    String addForm(ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm,
                   String useMaterialBrandSelectId);

    String addFormOfEmployee(ProjectReviewEmployeeForm projectReviewEmployeeForm);

    String addFormOfManagerSummary(ProjectReviewManagerForm projectReviewManagerForm);


    //工程部项目经理直接审核影响外观的品牌、物料使用的申请
    String addFormOfManagerReviewDirectly(ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm,
                                          String useMaterialBrandSelectId);

    //工程部员工提交影响外观品牌、物料使用申请
    String addFormOfEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm,
                             String useMaterialBrandSelectId,
                             String projectAppearanceReviewModeId);


    //工程部经理汇总审核影响外观品牌、物料使用申请
    String addFormOfManagerSummary(ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm,
                                   String useMaterialBrandSelectId,
                                   String projectAppearanceReviewModeId);

    //设计公司提交影响外观的品牌、物料使用申请审核
    String addFormOfDesignCompanyEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm,
                                          String useMaterialBrandSelectId);


    //监理单位决定是否重新检测
    String submitSupervisionCompanyDecideWhetherToRecheck(List<ProjectMaterialRetest> projectMaterialRetestList,String tempFileDir);


    //项目材料验收：总包单位提交项目材料批次验收申请
    String addAcceptanceBatchForm(ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm);

    //项目材料验收：工程部项目经理直接审核
    String addAcceptanceFormOfManagerReviewDirectly(ProjectMaterialAcceptanceReviewManagerDirectForm projectMaterialAcceptanceReviewManagerDirectForm,
                                                    String projectMaterialAcceptanceBatchId);

    //项目材料验收：工程部项目经理派发审核
    String addForm(ProjectMaterialAcceptanceReviewDispatchForm projectMaterialAcceptanceReviewDispatchForm,
                   String projectMaterialAcceptanceBatchId);

    //项目材料验收：工程部项目员工审核
    String addFormOfEmployee(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm,
                             String projectMaterialAcceptanceBatchId,
                             String projectMaterialAcceptanceReviewModeId);

    //项目材料验收：工程部项目经理汇总审核
    String addFormOfManagerSummary(ProjectMaterialAcceptanceReviewManagerSummaryForm projectMaterialAcceptanceReviewManagerSummaryForm,
                                   String projectMaterialAcceptanceBatchId,
                                   String projectMaterialAcceptanceReviewModeId);


    //工程部项目经理结束项目
    String addFormOfEndProject(ProjectEndForm projectEndForm);

    List<ProjectReviewUser> getRemainingNotReviewedEmployees(String projectReviewId);

    List<ProjectAppearanceReviewUser> getRemainingAppearanceNotReviewedEmployees(String projectAppearanceReviewModeId);
    List<ProjectMaterialAcceptanceReviewUser> getRemainingNotMaterialAcceptanceReviewedEmployees(String projectMaterialAcceptanceReviewModeId);
    void delete(Project project);

    ProjectView getProjectViewById(String id);

    Page<ProjectView> getPageView(int pageNo,
                                  int pageSize);

    Page<ProjectView> getPageViewByUserId(String userId,
                                          int pageNo,
                                          int pageSize);

    Page<ProjectView> getPageViewByCompanyConstructionId(String companyConstructionId,
                                                         int pageNo,
                                                         int pageSize);

    Page<ProjectView> getCurrentLoginUserInGeneralContractorCompanyNotEndedProjectPageView(
            Integer pageNo,
            Integer pageSize);

    Page<ProjectView> getPageViewByCompanyDesignId(String companyDesignId,
                                                   int pageNo,
                                                   int pageSize);

    Page<ProjectView> getPageViewByProjectName(String projectName,
                                               int pageNo,
                                               int pageSize);
    Page<ProjectView> getPageViewByProjectLocation(String projectLocation,
                                           int pageNo,
                                           int pageSize);

    Page<ProjectView> getPageViewByParams(String name,
                                                   String location,
                                                   BigDecimal totalTaxIncluded,
                                                   BigDecimal totalTaxNotIncluded,
                                                   BigDecimal buildingAreaAboveGround,
                                                   BigDecimal buildingAreaUnderGround,
                                                   String companyConstructionId,
                                                   String companyDesignId,
                                                   String note,
                                                   Date createDatetime,
                                                   Date endDatetime,
                                                   int pageNo,
                                                   int pageSize);

    Page<ProjectView> getPageViewByKeyword(String keyword,
                                           int pageNo,
                                           int pageSize);

    //总包单位进行品牌选择与物料申请
    String addFormOfGeneralContractorBrandSelectAndUseMaterial(UseMaterialForm useMaterialForm);

    UseMaterialBrandSelectView getUseMaterialBrandSelectViewByCurrentLoginUser(String projectId,
                                                                               String useMaterialBrandSelectId);

    List<UseMaterialView> getUseMaterialViewListByUseMaterialBrandSelectId(String useMaterialBrandSelectId);

    //监理单位对总包单位的品牌选择与物料申请进行审核
    String addSupervisionCompanyReview(ProjectAppearanceReviewEmployeeForm appearanceReviewForm,
                                       String useMaterialBrandSelectId);

    //得到要审核的材料，是外观审核，还是非外观审核
    int getAppearanceTypeByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    //监理单位对项目材料验收进行审核
    String submitSupervisionCompanySubmitProjectMaterialBatchAcceptanceReview(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm,
                                                                              String projectMaterialAcceptanceBatchId);


    ProjectReviewStatistics getStatisticsOfAppearanceReviewUserViewByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);
    ProjectReviewStatistics getStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByReviewModeId(String projectMaterialAcceptanceReviewModeId);
    ProjectReviewStatistics getStatisticsOfProjectMaterialReviewUserViewByProjectReviewId(String projectReviewId);
    ProjectStatisticalAnalysis getStatisticsOfProjectViewByProjectId(String projectId);
    Page<ProjectAppearanceReviewUserView> getPageProjectAppearanceReviewUserViewByTaskId(String projectAppearanceReviewModeId,
                                                                                         Integer pageNo,
                                                                                         Integer pageSize);
    Page<ProjectMaterialAcceptanceReviewUserView> getPageProjectMaterialAcceptanceReviewUserViewByTaskId(String projectMaterialAcceptanceReviewModeId,
                                                                                                         Integer pageNo,
                                                                                                         Integer pageSize);
    List<ProjectAppearanceReviewUserView> getListOfProjectAppearanceReviewUserViewByTaskId(String projectAppearanceReviewModeId);
    List<ProjectReviewUserView> getProjectReviewUserViewListByProjectIdAndTaskId (String projectReviewId);

    boolean checkDesignDepartmentManagerExists(String projectId);

    boolean checkEngineeringDepartmentManagerExists(String projectId);

    boolean checkDesignCompanyEmployeeExists(String projectId);

    boolean checkSupervisionCompanyEmployeeExists(String projectId);

    boolean checkGeneralContractorCompanyEmployeeExists(String projectId);

    boolean isCurrentLoginUserInEngineeringDepartment();

    boolean isCurrentLoginUserInDesignDepartment();

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectId(String projectId,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectIdAndName(String projectId,
                                                                               String name,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectIdAndLocation(String projectId,
                                                                               String location,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectIdAndItemMark(String projectId,
                                                                               String itemMark,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectIdAndTechnology(String projectId,
                                                                               String technology,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectIdAndInstallation(String projectId,
                                                                               String installation,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectIdAndBrand(String projectId,
                                                                               String brand,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    void setProjectReviewedNewBrandByProjectId(String projectId);

    Page<ProjectReviewUserView> getProjectMaterialUserUserReViewPageByTaskId(String projectReviewId,
                                                                             String reviewUser,
                                                                             int reviewResult,
                                                                             Integer pageNo,
                                                                             Integer pageSize);
    String addFormOfGeneralContractorBuyMaterialSelect(BuyMaterialForm buyMaterialForm);
    List<String> addFormOfGeneralContractorBuyProjectMaterial(BuyMaterial[] buyMaterials);

    Page<BuyMaterialView> getBuyMaterialViewPageByProjectId(String projectId,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<ProjectMaterialVerificationDocumentView> getPageDocumentViewByProjectId(String projectId,
                                                                                 Integer pageNo,
                                                                                 Integer pageSize);


    Page<ProjectMaterialVerificationDocumentView> getPageReCheckIsRequiredByProjectId(String projectId,
                                                                                      Integer pageNo,
                                                                                      Integer pageSize);


    UseMaterialForm splitUseMaterialFormByIsAppearance(UseMaterialForm useMaterialForm,
                                                       int i);

    List<ProjectMaterialVerificationDocumentView> getListMaterialVerificationDocumentViewByBuyMaterialId(String buyMaterialId);

    List<ProjectMaterialVerificationDocumentView> getListProjectMaterialVerificationDocumentViewOfReCheckIsRequiredByBuyMaterialId(String buyMaterialId);

    List<ProjectMaterialVerificationDocumentView> getListMaterialVerificationDocumentViewByBuyMaterialBatchId(String buyMaterialBatchId);
    List<ProjectMaterialVerificationDocumentView> getListProjectMaterialVerificationDocumentViewOfReCheckIsRequiredByBuyMaterialBatchId(String buyMaterialBatchId);


    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProjectMaterialView> getBoughtMaterialViewPageByProjectId(String projectId,
                                                                   Integer pageNo,
                                                                   Integer pageSize);


    Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUser(String projectMaterialAcceptanceBatchId,
                                                                                               Integer pageNo,
                                                                                               Integer pageSize);

    ProjectReviewUserView getFeedbackOfProjectMaterialReviewedByReviewId(String projectReviewId);


    Page<ProjectMaterialView> getProjectMaterialPageViewByProjectIdAndCompanyId(String projectId,
                                                                                String companyId,
                                                                                String name,
                                                                                String location,
                                                                                String itemMark,
                                                                                String technology,
                                                                                String installation,
                                                                                String brandPublic,
                                                                                String brandPrivate,
                                                                                Integer pageNo,
                                                                                Integer pageSize);

    ProjectAppearanceReviewUserView getFeedbackOfSelectBrandReviewedByReviewId(String projectAppearanceReviewModeId);

    UseMaterialBrandSelectView getNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(String projectAppearanceReviewModeId);

    ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewedByReviewId(String projectAffectAppearanceReviewModeId);
    List<ProjectMaterialRetestView> getFeedbackOfProjectMaterialAcceptanceReviewedByRetestId(String projectMaterialRetestId);
    ProjectMaterialAcceptanceReviewUserView getFeedbackOfProjectMaterialAcceptanceReviewedByReviewId(String projectMaterialAcceptanceReviewModeId);

}
