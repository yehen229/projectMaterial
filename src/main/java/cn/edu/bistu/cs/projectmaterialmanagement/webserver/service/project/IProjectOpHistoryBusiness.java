package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryView;

public interface IProjectOpHistoryBusiness {

    //材料审批
    int TableNameType_Project_Review = 0;//t_project_review
    int TableNameType_Project_Review_Mode = 1;//t_project_review_mode
    int TableNameType_Project_Review_User = 2;//t_project_review_user

    //影响外观（不影响外观）审批
    int TableNameType_Project_Appearance_Review = 10;//t_project_appearance_review
    int TableNameType_Project_Appearance_Review_Mode = 11;//t_project_appearance_review_mode
    int TableNameType_Project_Appearance_Review_User = 12;//t_project_appearance_review_user

    int TableNameType_Buy_Material = 20;//t_buy_material

    int TableNameType_Project_Retest = 30;//t_project_material_retest

    //项目验收审批
    int TableNameType_Project_Material_Acceptance_Batch = 40;//t_project_material_acceptance_batch
    int TableNameType_Project_Material_Acceptance_Review_Mode = 41;//t_project_material_acceptance_review_mode
    int TableNameType_Project_Material_Acceptance_Review = 42;//t_project_material_acceptance_review
    int TableNameType_Project_Material_Acceptance_Review_User = 43;//t_project_material_acceptance_review_user

    int TableNameType_Project_End = 100;

    Page<ProjectOpHistoryView> getPageViewByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize);

    Page<ProjectOpHistoryView> getPageViewByProjectId(String projectId,
                                                      int pageNo,
                                                      int pageSize);

    //设计部或工程部人员发起项目
    String addCreateProject(String projectId,
                            String userId,
                            String stepPhase,
                            String stepDescription
    );

    //管理员设置项目员工
    String addAdminSetDesignAndEngineeringDepartmentUsers(String projectId,
                                                          String userId,
                                                          String stepPhase,
                                                          String stepDescription
    );

    //设计单位提交项目材料参数
    String addSubmitProjectMaterial(String projectId,
                                    String userId,
                                    String stepPhase,
                                    String stepDescription
    );

    //设计单位提交的项目材料参数审核：设计部项目经理分发审核
    String addProjectMaterialReviewManagerDispatch(String projectId,
                                                   String userId,
                                                   String stepPhase,
                                                   String stepDescription,
                                                   String projectReviewId
    );

    //设计单位提交的项目材料参数审核：设计部项目经理直接填写审核意见
    String addProjectMaterialReviewManagerDirectly(String projectId,
                                                   String userId,
                                                   String stepPhase,
                                                   String stepDescription,
                                                   String projectReviewUserId
    );

    //设计单位提交的项目材料参数审核：设计部项目项目员工填写审核意见
    String addProjectMaterialReviewEmployee(String projectId,
                                            String userId,
                                            String stepPhase,
                                            String stepDescription,
                                            String projectReviewUserId
    );

    //设计单位提交的项目材料参数审核：设计部项目经理在分发后、没有员工填写意见的情况下提交审核意见
    String addProjectMaterialReviewManagerWithoutEmployeeReviewed(String projectId,
                                                                  String userId,
                                                                  String stepPhase,
                                                                  String stepDescription,
                                                                  String projectReviewUserId
    );

    //设计单位提交的项目材料参数审核：设计部项目经理汇总审核意见
    String addProjectMaterialReviewManagerSummary(String projectId,
                                                  String userId,
                                                  String stepPhase,
                                                  String stepDescription,
                                                  String projectReviewUserId
    );

    //主材信息入库
    String addSubmitProjectMaterialIntoStorage(String projectId,
                                               String userId,
                                               String stepPhase,
                                               String stepDescription
    );

    //物料手册移交工程部
    String addTransferProjectMaterialHandbookToEngineeringDepartment(String projectId,
                                                                     String userId,
                                                                     String stepPhase,
                                                                     String stepDescription
    );

    //工程部项目经理分发施工、监理
    String addDispatchProjectMaterial(String projectId,
                                      String userId,
                                      String stepPhase,
                                      String stepDescription
    );

    //管理员设置监理单位、总包单位管理员工
    String addAdminSetSuperVisionAndGeneralContractorEmployee(String projectId,
                                                              String userId,
                                                              String stepPhase,
                                                              String stepDescription
    );

    //总包单位品牌选择与物料使用申请
    String addGeneralContractorSelectBrandAndUseMaterial(String projectId,
                                                         String userId,
                                                         String stepPhase,
                                                         String stepDescription
    );

    //总包单位品牌选择与物料使用申请审核：监理审核提交的总包单位品牌选择与物料使用申请
    String addSupervisionCompanyReview(String projectId,
                                       String userId,
                                       String stepPhase,
                                       String stepDescription,
                                       String projectAppearanceReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，工程部部项目经理分发审核
    String addAffectAppearanceReviewEngineeringDepartmentManagerDispatch(String projectId,
                                                                         String userId,
                                                                         String stepPhase,
                                                                         String stepDescription,
                                                                         String projectReviewModeId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，工程部项目经理直接填写审核意见
    String addAffectAppearanceReviewEngineeringDepartmentManagerDirectly(String projectId,
                                                                         String userId,
                                                                         String stepPhase,
                                                                         String stepDescription,
                                                                         String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，工程部项目项目员工填写审核意见
    String addAffectAppearanceReviewEngineeringDepartmentEmployee(String projectId,
                                                                  String userId,
                                                                  String stepPhase,
                                                                  String stepDescription,
                                                                  String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，工程部项目经理在分发后、没有员工填写意见的情况下提交审核意见
    String addAffectAppearanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                        String userId,
                                                                                        String stepPhase,
                                                                                        String stepDescription,
                                                                                        String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，工程部项目经理汇总审核意见
    String addAffectAppearanceReviewEngineeringDepartmentManagerSummary(String projectId,
                                                                        String userId,
                                                                        String stepPhase,
                                                                        String stepDescription,
                                                                        String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：设计单位审核提交的总包单位品牌选择与物料使用申请
    String addAffectAppearanceReviewDesignCompany(String projectId,
                                                  String userId,
                                                  String stepPhase,
                                                  String stepDescription,
                                                  String projectAppearanceReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，设计部项目经理分发审核
    String addAffectAppearanceReviewDesignDepartmentManagerDispatch(String projectId,
                                                                    String userId,
                                                                    String stepPhase,
                                                                    String stepDescription,
                                                                    String projectReviewModeId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，设计部项目经理直接填写审核意见
    String addAffectAppearanceReviewDesignDepartmentManagerDirectly(String projectId,
                                                                    String userId,
                                                                    String stepPhase,
                                                                    String stepDescription,
                                                                    String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，设计部项目项目员工填写审核意见
    String addAffectAppearanceReviewDesignDepartmentEmployee(String projectId,
                                                             String userId,
                                                             String stepPhase,
                                                             String stepDescription,
                                                             String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，设计部项目经理在分发后、没有员工填写意见的情况下提交审核意见
    String addAffectAppearanceReviewDesignDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                   String userId,
                                                                                   String stepPhase,
                                                                                   String stepDescription,
                                                                                   String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：影响外观的情况下，设计部项目经理汇总审核意见
    String addAffectAppearanceReviewDesignDepartmentManagerSummary(String projectId,
                                                                   String userId,
                                                                   String stepPhase,
                                                                   String stepDescription,
                                                                   String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，工程部项目经理分发审核
    String addNotAffectAppearanceReviewEngineeringDepartmentManagerDispatch(String projectId,
                                                                            String userId,
                                                                            String stepPhase,
                                                                            String stepDescription,
                                                                            String projectReviewModeId
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，工程部项目经理直接填写审核意见
    String addNotAffectAppearanceReviewEngineeringDepartmentManagerDirectly(String projectId,
                                                                            String userId,
                                                                            String stepPhase,
                                                                            String stepDescription,
                                                                            String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，工程部项目项目员工填写审核意见
    String addNotAffectAppearanceReviewEngineeringDepartmentEmployee(String projectId,
                                                                     String userId,
                                                                     String stepPhase,
                                                                     String stepDescription,
                                                                     String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，工程部项目经理在分发后、没有员工填写意见的情况下提交审核意见
    String addNotAffectAppearanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                           String userId,
                                                                                           String stepPhase,
                                                                                           String stepDescription,
                                                                                           String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，工程部项目经理汇总审核意见
    String addNotAffectAppearanceReviewEngineeringDepartmentManagerSummary(String projectId,
                                                                           String userId,
                                                                           String stepPhase,
                                                                           String stepDescription,
                                                                           String projectReviewUserId
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，设计部自动审核
    String addNotAffectAppearanceReviewDesignDepartmentAutomaticReview(String projectId,
                                                                       String userId,
                                                                       String stepPhase,
                                                                       String stepDescription
    );

    //总包单位品牌选择与物料使用申请审核：不影响外观的情况下，设计公司自动审核
    String addNotAffectAppearanceReviewDesignCompanyAutomaticReview(String projectId,
                                                                    String userId,
                                                                    String stepPhase,
                                                                    String stepDescription
    );


    //总包公司订购
    String addProjectMaterialOrder(String projectId,
                                   String userId,
                                   String stepPhase,
                                   String stepDescription,
                                   String buyMaterialId
    );

    //生成二维码
    String generateQRCode(String projectId,
                          String userId,
                          String stepPhase,
                          String stepDescription);

    //总包单位材料到货
    String addProjectMaterialArrival(String projectId,
                                     String userId,
                                     String stepPhase,
                                     String stepDescription
    );

    //总包单位填报工程材料、设备报验资料
    String addGeneralContractorCompanyDocumentation(String projectId,
                                                    String userId,
                                                    String stepPhase,
                                                    String stepDescription,
                                                    String buyMaterialId
    );

    //扫描二维码，监理单位和施工单位共同确定材料正确
    String addQRCodeScan(String projectId,
                         String userId,
                         String stepPhase,
                         String stepDescription
    );

    //监理判断是否需要复试
    String addSupervisionCompanyDecideWhetherReCheckIsRequired(String projectId,
                                                               String userId,
                                                               String stepPhase,
                                                               String stepDescription
    );

    //监理单位判定是否通过复试
    String addSupervisionCompanyDecidePassReCheck(String projectId,
                                                  String userId,
                                                  String stepPhase,
                                                  String stepDescription,
                                                  String projectMaterialRetestId
    );

    //施工使用
    String addGeneralContractorCompanyForConstructionUse(String projectId,
                                                         String userId,
                                                         String stepPhase,
                                                         String stepDescription
    );

    //通知禁止使用
    String addSupervisionCompanyNotificationProhibition(String projectId,
                                                        String userId,
                                                        String stepPhase,
                                                        String stepDescription
    );

    //总包单位发起批次验收
    String addAcceptanceReviewGeneralContractorCompany(String projectId,
                                                       String userId,
                                                       String stepPhase,
                                                       String stepDescription,
                                                       String projectMaterialAcceptanceBatchId
    );

    //监理对该批次验收进行审核
    String addSupervisionCompanyAcceptanceReview(String projectId,
                                                 String userId,
                                                 String stepPhase,
                                                 String stepDescription,
                                                 String projectMaterialAcceptanceReviewModeId
    );


    //批次验收进行审核：影响外观的情况下，工程部部项目经理分发审核
    String addAcceptanceReviewEngineeringDepartmentManagerDispatch(String projectId,
                                                                   String userId,
                                                                   String stepPhase,
                                                                   String stepDescription,
                                                                   String projectReviewModeId
    );

    //批次验收进行审核：影响外观的情况下，工程部项目经理直接填写审核意见
    String addAcceptanceReviewEngineeringDepartmentManagerDirectly(String projectId,
                                                                   String userId,
                                                                   String stepPhase,
                                                                   String stepDescription,
                                                                   String projectMaterialAcceptanceReviewModeId
    );

    //批次验收进行审核：影响外观的情况下，工程部项目项目员工填写审核意见
    String addAcceptanceReviewEngineeringDepartmentEmployee(String projectId,
                                                            String userId,
                                                            String stepPhase,
                                                            String stepDescription,
                                                            String projectReviewUserId
    );

    //批次验收进行审核：影响外观的情况下，工程部项目经理在分发后、没有员工填写意见的情况下提交审核意见
    String addAcceptanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(String projectId,
                                                                                  String userId,
                                                                                  String stepPhase,
                                                                                  String stepDescription,
                                                                                  String projectReviewUserId
    );

    //批次验收进行审核：影响外观的情况下，工程部项目经理汇总审核意见
    String addAcceptanceReviewEngineeringDepartmentManagerSummary(String projectId,
                                                                  String userId,
                                                                  String stepPhase,
                                                                  String stepDescription,
                                                                  String projectReviewUserId
    );

    //工程部项目经理确定是否整个项目结束
    String addEngineeringDepartmentManagerDetermineWhetherTheProjectIsCompletion(String projectId,
                                                                                 String userId,
                                                                                 String stepPhase,
                                                                                 String stepDescription
    );

    //工程部项目经理填写项目结题材料
    String addEngineeringDepartmentManagerCompleteTheProjectClosureDocumentation(String projectId,
                                                                                 String userId,
                                                                                 String stepPhase,
                                                                                 String stepDescription
    );

    //项目结束
    String addProjectEnd(String projectId,
                         String userId,
                         String stepPhase,
                         String stepDescription,
                         String projectEndId
    );

}
