package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryContentService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectOpHistoryBusinessImpl implements IProjectOpHistoryBusiness {

    private final IProjectOpHistoryService projectOpHistoryService;
    private final IProjectOpHistoryContentService projectOpHistoryContentService;
    private final IProjectService projectService;
    private final IUserService userService;
    private final ICompanyUserService companyUserService;
    private final ICompanyService companyService;

    private final String Project_History_Step_Phase_Create_Project = "项目建立";
    private final String Project_History_Step_Phase_Admin_Set_Employee = "管理员设置项目员工";
    private final String Project_History_Step_Phase_Design_Company_Submit_Project_Material = "设计单位提交项目材料参数";
    private final String Project_History_Step_Phase_Design_Department_Project_Material_Review = "设计单位审核项目材料";

    public ProjectOpHistoryBusinessImpl(IProjectOpHistoryService projectOpHistoryService,
                                        IProjectOpHistoryContentService projectOpHistoryContentService,
                                        IProjectService projectService,
                                        IUserService userService,
                                        ICompanyUserService companyUserService,
                                        ICompanyService companyService) {
        this.projectOpHistoryService = projectOpHistoryService;
        this.projectOpHistoryContentService = projectOpHistoryContentService;
        this.projectService = projectService;
        this.userService = userService;
        this.companyUserService = companyUserService;
        this.companyService = companyService;
    }

    private String addHistory(String projectId,
                              String userId,
                              String stepPhase,
                              String stepDescription) {
        ProjectOpHistory projectOpHistory = new ProjectOpHistory();
        projectOpHistory.setProjectId(projectId);
        projectOpHistory.setUserId(userId);
        projectOpHistory.setStepPhase(stepPhase);
        projectOpHistory.setStepDescription(stepDescription);
        return projectOpHistoryService.add(projectOpHistory);
    }

    private String addHistory(String projectId,
                              String userId,
                              String stepPhase,
                              String stepDescription,
                              String tableId,
                              int tableNameType) {
        ProjectOpHistory projectOpHistory = new ProjectOpHistory();
        projectOpHistory.setProjectId(projectId);
        projectOpHistory.setUserId(userId);
        projectOpHistory.setStepPhase(stepPhase);
        projectOpHistory.setStepDescription(stepDescription);
        String projectOpHistoryId = projectOpHistoryService.add(projectOpHistory);
        if (projectOpHistoryId != null) {
            ProjectOpHistoryContent projectOpHistoryContent = new ProjectOpHistoryContent();
            projectOpHistoryContent.setProjectOpHistoryId(projectOpHistoryId);
            projectOpHistoryContent.setOtherTableId(tableId);
            projectOpHistoryContent.setOtherTableIdType(tableNameType);
            projectOpHistoryContentService.add(projectOpHistoryContent);
        }
        return projectOpHistoryId;
    }


    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryView> getPageViewByUserId(String userId,
                                                          int pageNo,
                                                          int pageSize) {
        Page<ProjectOpHistory> projectOpHistoryPage = projectOpHistoryService.getPageByUserId(userId, pageNo, pageSize);
        return convertProjectOpHistoryPage2PageView(projectOpHistoryPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryView> getPageViewByProjectId(String projectId,
                                                             int pageNo,
                                                             int pageSize) {
        Page<ProjectOpHistory> projectOpHistoryPage = projectOpHistoryService.getPageByProjectId(projectId, pageNo,
                                                                                                 pageSize);
        return convertProjectOpHistoryPage2PageView(projectOpHistoryPage, pageNo, pageSize);
    }

    @Override
    public String addCreateProject(String projectId,
                                   String userId,
                                   String stepPhase,
                                   String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addAdminSetDesignAndEngineeringDepartmentUsers(String projectId,
                                                                 String userId,
                                                                 String stepPhase,
                                                                 String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addSubmitProjectMaterial(String projectId,
                                           String userId,
                                           String stepPhase,
                                           String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectMaterialReviewManagerDispatch(String projectId,
                                                          String userId,
                                                          String stepPhase,
                                                          String stepDescription,
                                                          String projectReviewId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectReviewId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Review);
    }

    @Override
    public String addProjectMaterialReviewManagerDirectly(String projectId,
                                                          String userId,
                                                          String stepPhase,
                                                          String stepDescription,
                                                          String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectMaterialReviewEmployee(String projectId,
                                                   String userId,
                                                   String stepPhase,
                                                   String stepDescription,
                                                   String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectMaterialReviewManagerWithoutEmployeeReviewed(String projectId,
                                                                         String userId,
                                                                         String stepPhase,
                                                                         String stepDescription,
                                                                         String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectMaterialReviewManagerSummary(String projectId,
                                                         String userId,
                                                         String stepPhase,
                                                         String stepDescription,
                                                         String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addSubmitProjectMaterialIntoStorage(String projectId,
                                                      String userId,
                                                      String stepPhase,
                                                      String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addTransferProjectMaterialHandbookToEngineeringDepartment(String projectId,
                                                                            String userId,
                                                                            String stepPhase,
                                                                            String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addDispatchProjectMaterial(String projectId,
                                             String userId,
                                             String stepPhase,
                                             String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addAdminSetSuperVisionAndGeneralContractorEmployee(String projectId,
                                                                     String userId,
                                                                     String stepPhase,
                                                                     String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addGeneralContractorSelectBrandAndUseMaterial(String projectId,
                                                                String userId,
                                                                String stepPhase,
                                                                String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addSupervisionCompanyReview(String projectId,
                                              String userId,
                                              String stepPhase,
                                              String stepDescription,
                                              String projectAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);

    }

    @Override
    public String addAffectAppearanceReviewEngineeringDepartmentManagerDispatch(String projectId,
                                                                                String userId,
                                                                                String stepPhase,
                                                                                String stepDescription,
                                                                                String projectAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_Mode);
    }

    @Override
    public String addAffectAppearanceReviewEngineeringDepartmentManagerDirectly(String projectId,
                                                                                String userId,
                                                                                String stepPhase,
                                                                                String stepDescription,
                                                                                String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addAffectAppearanceReviewEngineeringDepartmentEmployee(String projectId,
                                                                         String userId,
                                                                         String stepPhase,
                                                                         String stepDescription,
                                                                         String projectAppearanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addAffectAppearanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                               String userId,
                                                                                               String stepPhase,
                                                                                               String stepDescription,
                                                                                               String projectAppearanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addAffectAppearanceReviewEngineeringDepartmentManagerSummary(String projectId,
                                                                               String userId,
                                                                               String stepPhase,
                                                                               String stepDescription,
                                                                               String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);
    }

    @Override
    public String addAffectAppearanceReviewDesignCompany(String projectId,
                                                         String userId,
                                                         String stepPhase,
                                                         String stepDescription,
                                                         String projectAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);
    }

    @Override
    public String addAffectAppearanceReviewDesignDepartmentManagerDispatch(String projectId,
                                                                           String userId,
                                                                           String stepPhase,
                                                                           String stepDescription,
                                                                           String projectDesignDepartmentAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectDesignDepartmentAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);
    }

    @Override
    public String addAffectAppearanceReviewDesignDepartmentManagerDirectly(String projectId,
                                                                           String userId,
                                                                           String stepPhase,
                                                                           String stepDescription,
                                                                           String projectAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);
    }

    @Override
    public String addAffectAppearanceReviewDesignDepartmentEmployee(String projectId,
                                                                    String userId,
                                                                    String stepPhase,
                                                                    String stepDescription,
                                                                    String projectAppearanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addAffectAppearanceReviewDesignDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                          String userId,
                                                                                          String stepPhase,
                                                                                          String stepDescription,
                                                                                          String projectAppearanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addAffectAppearanceReviewDesignDepartmentManagerSummary(String projectId,
                                                                          String userId,
                                                                          String stepPhase,
                                                                          String stepDescription,
                                                                          String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addNotAffectAppearanceReviewEngineeringDepartmentManagerDispatch(String projectId,
                                                                                   String userId,
                                                                                   String stepPhase,
                                                                                   String stepDescription,
                                                                                   String projectAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);
    }

    @Override
    public String addNotAffectAppearanceReviewEngineeringDepartmentManagerDirectly(String projectId,
                                                                                   String userId,
                                                                                   String stepPhase,
                                                                                   String stepDescription,
                                                                                   String projectAppearanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_Mode);
    }

    @Override
    public String addNotAffectAppearanceReviewEngineeringDepartmentEmployee(String projectId,
                                                                            String userId,
                                                                            String stepPhase,
                                                                            String stepDescription,
                                                                            String projectAppearanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addNotAffectAppearanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                                  String userId,
                                                                                                  String stepPhase,
                                                                                                  String stepDescription,
                                                                                                  String projectAppearanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectAppearanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addNotAffectAppearanceReviewEngineeringDepartmentManagerSummary(String projectId,
                                                                                  String userId,
                                                                                  String stepPhase,
                                                                                  String stepDescription,
                                                                                  String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Appearance_Review_User);
    }

    @Override
    public String addNotAffectAppearanceReviewDesignDepartmentAutomaticReview(String projectId,
                                                                              String userId,
                                                                              String stepPhase,
                                                                              String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addNotAffectAppearanceReviewDesignCompanyAutomaticReview(String projectId,
                                                                           String userId,
                                                                           String stepPhase,
                                                                           String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectMaterialOrder(String projectId,
                                          String userId,
                                          String stepPhase,
                                          String stepDescription,
                                          String buyMaterialId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, buyMaterialId,
                          IProjectOpHistoryBusiness.TableNameType_Buy_Material);
    }

    @Override
    public String generateQRCode(String projectId,
                                 String userId,
                                 String stepPhase,
                                 String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectMaterialArrival(String projectId,
                                            String userId,
                                            String stepPhase,
                                            String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addGeneralContractorCompanyDocumentation(String projectId,
                                                           String userId,
                                                           String stepPhase,
                                                           String stepDescription,
                                                           String buyMaterialId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, buyMaterialId,
                          IProjectOpHistoryBusiness.TableNameType_Buy_Material);
    }

    @Override
    public String addQRCodeScan(String projectId,
                                String userId,
                                String stepPhase,
                                String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addSupervisionCompanyDecideWhetherReCheckIsRequired(String projectId,
                                                                      String userId,
                                                                      String stepPhase,
                                                                      String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addSupervisionCompanyDecidePassReCheck(String projectId,
                                                         String userId,
                                                         String stepPhase,
                                                         String stepDescription,
                                                         String projectMaterialRetestId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialRetestId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Retest);
    }

    @Override
    public String addGeneralContractorCompanyForConstructionUse(String projectId,
                                                                String userId,
                                                                String stepPhase,
                                                                String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addSupervisionCompanyNotificationProhibition(String projectId,
                                                               String userId,
                                                               String stepPhase,
                                                               String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addAcceptanceReviewGeneralContractorCompany(String projectId,
                                                              String userId,
                                                              String stepPhase,
                                                              String stepDescription,
                                                              String projectMaterialAcceptanceBatchId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialAcceptanceBatchId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Batch);
    }

    @Override
    public String addSupervisionCompanyAcceptanceReview(String projectId,
                                                        String userId,
                                                        String stepPhase,
                                                        String stepDescription,
                                                        String projectMaterialAcceptanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialAcceptanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_Mode);
    }

    @Override
    public String addAcceptanceReviewEngineeringDepartmentManagerDispatch(String projectId,
                                                                          String userId,
                                                                          String stepPhase,
                                                                          String stepDescription,
                                                                          String projectMaterialAcceptanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialAcceptanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_Mode);
    }

    @Override
    public String addAcceptanceReviewEngineeringDepartmentManagerDirectly(String projectId,
                                                                          String userId,
                                                                          String stepPhase,
                                                                          String stepDescription,
                                                                          String projectMaterialAcceptanceReviewModeId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialAcceptanceReviewModeId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_Mode);
    }

    @Override
    public String addAcceptanceReviewEngineeringDepartmentEmployee(String projectId,
                                                                   String userId,
                                                                   String stepPhase,
                                                                   String stepDescription,
                                                                   String projectMaterialAcceptanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialAcceptanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_User);
    }

    @Override
    public String addAcceptanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                         String userId,
                                                                                         String stepPhase,
                                                                                         String stepDescription,
                                                                                         String projectMaterialAcceptanceReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectMaterialAcceptanceReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_User);
    }

    @Override
    public String addAcceptanceReviewEngineeringDepartmentManagerSummary(String projectId,
                                                                         String userId,
                                                                         String stepPhase,
                                                                         String stepDescription,
                                                                         String projectReviewUserId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectReviewUserId,
                          IProjectOpHistoryBusiness.TableNameType_Project_Material_Acceptance_Review_User);
    }

    @Override
    public String addEngineeringDepartmentManagerDetermineWhetherTheProjectIsCompletion(String projectId,
                                                                                        String userId,
                                                                                        String stepPhase,
                                                                                        String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addEngineeringDepartmentManagerCompleteTheProjectClosureDocumentation(String projectId,
                                                                                        String userId,
                                                                                        String stepPhase,
                                                                                        String stepDescription) {
        return addHistory(projectId, userId, stepPhase, stepDescription);
    }

    @Override
    public String addProjectEnd(String projectId,
                                String userId,
                                String stepPhase,
                                String stepDescription,
                                String projectEndId) {
        return addHistory(projectId, userId, stepPhase, stepDescription, projectEndId,
                          IProjectOpHistoryBusiness.TableNameType_Project_End);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectOpHistoryView getProjectOpHistoryViewByProjectOpHistoryId(String id) {
        ProjectOpHistory projectOpHistory = projectOpHistoryService.getById(id);
        if (projectOpHistory == null) return null;
        ProjectOpHistoryView projectOpHistoryView = new ProjectOpHistoryView();
        projectOpHistoryView.setProject(projectService.getById(projectOpHistory.getProjectId()));
        projectOpHistoryView.setProjectOpHistory(projectOpHistory);
        projectOpHistoryView.setUser(userService.getById(projectOpHistory.getUserId()));

        projectOpHistoryView.setCompany(companyUserService.getCompanyByUserId(projectOpHistory.getUserId()));
        return projectOpHistoryView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectOpHistoryPage 页面对象
     */
    private Page<ProjectOpHistoryView> convertProjectOpHistoryPage2PageView(Page<ProjectOpHistory> projectOpHistoryPage,
                                                                            int pageNo,
                                                                            int pageSize) {
        if (projectOpHistoryPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistoryView> list = new ArrayList<>();
        for (ProjectOpHistory projectOpHistory : projectOpHistoryPage.getResult()) {
            ProjectOpHistoryView projectOpHistoryView = getProjectOpHistoryViewByProjectOpHistoryId(
                    projectOpHistory.getId());
            if (projectOpHistoryView != null) list.add(projectOpHistoryView);
        }
        return new Page<>(startIndex, projectOpHistoryPage.getTotalCount(), pageSize, list);
    }
}
