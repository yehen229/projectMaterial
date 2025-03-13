package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectBackOrDeleteTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectUserCompletedTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectUserTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlow;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlowBack;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlowHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialflow/v1")
@EnableMethodSecurity
public class ProjectMaterialFlowController {

        private final ProjectFlow projectMaterialFlow;
    private final ProjectFlowBack projectMaterialFlowBack;
    private final ProjectFlowHistory projectMaterialFlowHistory;

    private final IProjectService projectService;
    private final IProjectBusinessService projectBusinessService;
    private final IProjectOpHistoryBusiness projectOpHistoryBusiness;

    ProjectMaterialFlowController(ProjectFlow projectMaterialFlow,
                                  ProjectFlowBack projectMaterialFlowBack,
                                  ProjectFlowHistory projectMaterialFlowHistory,
                                  IProjectService projectService,
                                  IProjectBusinessService projectBusinessService,
                                  IProjectOpHistoryBusiness projectOpHistoryBusiness) {

        this.projectMaterialFlow = projectMaterialFlow;
        this.projectMaterialFlowBack = projectMaterialFlowBack;
        this.projectMaterialFlowHistory = projectMaterialFlowHistory;
        this.projectService = projectService;
        this.projectBusinessService = projectBusinessService;
        this.projectOpHistoryBusiness = projectOpHistoryBusiness;
    }

    /**
     * 查询当前登录用户的待办任务
     * 权限范围：所有人
     *
     * @return 待办任务列表
     */
    @GetMapping(value = "get-task-by-current-login-user")
    public List<ProjectUserTask> getTaskByCurrentLoginUser() {

        return projectMaterialFlowHistory.getTaskByCurrentLoginUser();
    }


    /**
     * 获得当前登录用户在指定项目中的任务
     * 权限范围：所有人
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-task-by-current-login-user-and-project-id")
    public ProjectUserTask getTaskByCurrentLoginUserAndProjectId(@RequestParam(value = "projectId") String projectId) {

        return projectMaterialFlowHistory.getTaskByCurrentLoginUserAndProjectId(projectId);
    }

    /**
     * 获得当前登录用户在指定项目中的任务
     * 权限范围：所有人
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-task-by-current-login-user-and-project-id-and-task-id")
    public ProjectUserTask getTaskByCurrentLoginUserAndProjectIdAndTaskId(@RequestParam(value = "projectId") String projectId,
                                                                          @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlowHistory.getTaskByCurrentLoginUserAndProjectIdAndTaskId(projectId, taskId);
    }

    /**
     * 查询当前用户已经完成的任务
     * 权限范围：所有人
     *
     * @return
     */
    @GetMapping(value = "get-completed-task-by-current-login-user")
    public List<ProjectUserCompletedTask> getCompletedTaskByCurrentLoginUser() {

        return projectMaterialFlowHistory.getCompletedTaskByCurrentLoginUser();
    }

    /**
     * 获得项目的审批历史
     * <p>
     * 权限范围：
     * 所有设计部、工程部人员，项目的设计公司、监理单位、总包单位人员可以查看
     *
     * @param projectId 项目Id
     * @return 审批历史列表
     */
    @GetMapping(value = "page-history-view-by-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectOpHistoryView> getHistoryViewByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectOpHistoryBusiness.getPageViewByProjectId(projectId, pageNo, pageSize);
    }


    @GetMapping(value = "get-task-history-by-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isInProject(#projectId)
            """)
    public List<ProjectHistory> getTaskHistoryByProjectId(@RequestParam(value = "projectId") String projectId) {

        return projectMaterialFlowHistory.getTaskHistoryByProjectId(projectId);


    }

    /**
     * 撤回操作
     * 权限范围：所有人
     *
     * @param projectBackOrDeleteTask
     * @return
     */
    @PostMapping(value = "back-process")
    public String backProcess(@RequestBody ProjectBackOrDeleteTask projectBackOrDeleteTask) {
        return projectMaterialFlowBack.backProcess(projectBackOrDeleteTask);
    }


    /**
     * 启动流程，只有建设单位员工才可以建立项目、启动流程
     * 权限范围:设计部或工程部员工
     *
     * @param projectForm
     * @return
     */
    @PostMapping(value = "start-process")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.engineeringDepartment 
            or @ProjectPermission.designDepartment
            """)
    public String startProcess(@RequestBody ProjectForm projectForm) {
        //创建项目
        String projectId = projectBusinessService.add(projectForm);

        //启动业务流程
        return projectMaterialFlow.startProcessByProjectId(projectId);
    }

    /**
     * 管理员设置设计部和工程部项目经理、项目员工
     * <p>
     * 权限范围：管理员
     *
     * @param project
     * @return
     */
    @PostMapping(value = "admin-set-design-and-engineering-department-users")
    @PreAuthorize("hasAnyRole('Admin')")
    public String startAdminSetDesignAndEngineeringDepartmentUsers(@RequestBody Project project) {

        return projectMaterialFlow.startAdminSetDesignAndEngineeringDepartmentUsers(project);
    }

    /**
     * 管理员设置监理单位和总包单位项目员工
     * <p>
     * 权限范围：管理员
     *
     * @param project
     * @return
     */
    @PostMapping(value = "admin-set-supervision-and-general-contractor-employee")
    @PreAuthorize("hasAnyRole('Admin')")
    public String startAdminSetSuperVisionAndGeneralContractorEmployee(@RequestBody Project project) {

        return projectMaterialFlow.startAdminSetSuperVisionAndGeneralContractorEmployee(project);
    }


    /**
     * 设计单位提交项目材料
     * <p>
     * 权限访问：项目的设计单位的员工
     *
     * @return
     */

    @PostMapping(value = "design-company-submit-project-material")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isInDesignCompany(#project.id)
            """)
    public String submitProcess(@RequestBody Project project) {
        return projectMaterialFlow.designCompanySubmitProjectMaterial(project);
    }

    /**
     * 送审操作：设计部经理直接审核
     * <p>
     * 权限范围：设计部项目经理
     *
     * @param projectReviewForm
     * @return
     */
    @PostMapping(value = "design-department-manager-direct")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isDesignDepartmentManager(#projectReviewForm.projectReviewMode.projectId)
            """)
    public String submitProjectMaterialReviewOfManagerDirect(@RequestBody ProjectReviewForm projectReviewForm) {
        return projectMaterialFlow.submitProjectMaterialReviewOfManagerDirect(projectReviewForm);
    }

    /**
     * 送审操作：设计部经理分配给员工
     * <p>
     * 权限范围：设计部项目经理
     *
     * @param projectReviewDispatchForm
     * @return
     */
    @PostMapping(value = "design-department-manager-dispatch-to-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isDesignDepartmentManager(#projectReviewDispatchForm.projectReviewMode.projectId)
            """)
    public String submitProjectMaterialReviewManagerDistributeToEmployees(@RequestBody ProjectReviewDispatchForm projectReviewDispatchForm) {
        return projectMaterialFlow.submitProjectMaterialReviewManagerDistributeToEmployees(projectReviewDispatchForm);
    }


    /**
     * 送审操作：设计部员工审核
     * <p>
     * 权限范围：设计部员工（包括项目经理和项目员工）
     *
     * @param projectReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "design-department-review-of-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isInDesignDepartment(#projectReviewEmployeeForm.projectId)
            """)
    public String submitProjectMaterialReviewOfEmployee(@RequestBody ProjectReviewEmployeeForm projectReviewEmployeeForm) {
        return projectMaterialFlow.submitProjectMaterialReviewOfEmployee(projectReviewEmployeeForm);
    }

    @PostMapping(value = "design-department-remain-review-manager")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isDesignDepartmentManager(#projectReviewEmployeeForm.projectId) 
            """)
    public String submitProjectMaterialReviewOfManager(@RequestBody ProjectReviewEmployeeForm projectReviewEmployeeForm) {
        return projectMaterialFlow.submitProjectMaterialReviewOfManager(
                projectReviewEmployeeForm);
    }

    /**
     * 送审操作：设计部经理汇总
     * <p>
     * 权限范围：设计部项目经理
     *
     * @param projectReviewManagerForm
     * @return
     */
    @PostMapping(value = "design-department-review-manager-summary")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isDesignDepartmentManager(#projectReviewManagerForm.projectId)
            """)
    public String submitProjectMaterialReviewOfManagerSummary(@RequestBody ProjectReviewManagerForm projectReviewManagerForm) {
        return projectMaterialFlow.submitProjectMaterialReviewOfManagerSummary(projectReviewManagerForm);
    }

    /**
     * 送审操作：工程部经理分发监理单位与总包单位
     * <p>
     * 权限范围：管理员、
     *
     * @param projectCompany
     * @return
     */
    @PostMapping(value = "engineering-department-manager-dispatch")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isEngineeringDepartmentManager(#projectCompany.projectId)
            """)
    public String submitEngineeringDepartmentManagerDispatch(@RequestBody ProjectCompany projectCompany) {
        return projectMaterialFlow.submitEngineeringDepartmentManagerDispatch(projectCompany);
    }


    /**
     * 总包单位选择品牌、申请物料等
     *
     * @param useMaterialForm
     * @return
     */
    @PostMapping(value = "general-contractor-select-brand")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#useMaterialForm.projectId)
            """)
    public String submitGeneralContractorSelectBrand(@RequestBody UseMaterialForm useMaterialForm) {
        return projectMaterialFlow.submitGeneralContractorSelectBrand(useMaterialForm);
    }

    /**
     * 总包单位新建任务：选择品牌、申请物料等
     *
     * @param useMaterialForm
     * @return
     */
    @PostMapping(value = "general-contractor-new-task-of-select-brand")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#useMaterialForm.projectId)
            """)
    public String submitGeneralContractorNewTaskOfSelectBrand(@RequestBody UseMaterialForm useMaterialForm) {
        return projectMaterialFlow.submitGeneralContractorNewTaskOfSelectBrand(useMaterialForm);
    }


    /**
     * 监理单位对总包单位提交的品牌、物料进行审核
     * <p>
     * 权限范围：管理员、监理单位员工
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "supervision-company-review-select-brand")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInSupervisionCompany(#projectAppearanceReviewEmployeeForm.projectId)
            """)
    public String submitSupervisionCompanyReviewSelectBrand(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitSupervisionCompanyReviewSelectBrand(projectAppearanceReviewEmployeeForm);
    }

    /**
     * 影响外观：送审操作：工程部经理直接审核
     * 权限范围：管理员、
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @return
     */
    @PostMapping(value = "affect-appearance-engineering-department-review-manager-direct")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewManagerDirectForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(@RequestBody ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(
                projectAppearanceReviewManagerDirectForm);
    }

    /**
     * 影响外观：送审操作：工程部经理分发给员工
     * 权限范围：管理员、工程部项目经理
     *
     * @param projectAppearanceReviewDispatchForm
     * @return
     */
    @PostMapping(value = "affect-appearance-engineering-department-manager-dispatch-to-employee")
    @PreAuthorize("""
            hasAnyRole('Admin')
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewDispatchForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(@RequestBody ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(
                projectAppearanceReviewDispatchForm);
    }

    /**
     * 影响外观：送审操作：工程部员工审核
     * 权限范围：管理员、工程部项目员工
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "affect-appearance-engineering-department-review-of-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewEmployeeForm.projectId) 
            or @ProjectPermission.isEngineeringDepartmentEmployee(#projectAppearanceReviewEmployeeForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfEngineeringDepartmentEmployee(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfEngineeringDepartmentEmployee(
                projectAppearanceReviewEmployeeForm);
    }

    @PostMapping(value = "affect-appearance-engineering-department-remain-review-manager")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewEmployeeForm.projectId) 
            """)
    public String submitAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(
                projectAppearanceReviewEmployeeForm);
    }

    /**
     * 影响外观：送审操作：工程部经理汇总
     * 权限范围：管理员、工程部项目经理
     *
     * @param projectAppearanceReviewManagerSummaryForm
     * @return
     */
    @PostMapping(value = "affect-appearance-engineering-department-review-manager-summary")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewManagerSummaryForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(@RequestBody ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(
                projectAppearanceReviewManagerSummaryForm);
    }

    /**
     * 影响外观：送审操作：设计公司进行审核
     * 权限范围：管理员、设计公司项目员工
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "affect-appearance-design-company-review")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInDesignCompany(#projectAppearanceReviewEmployeeForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfDesignCompany(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfDesignCompany(projectAppearanceReviewEmployeeForm);
    }

    /**
     * 影响外观：送审操作：设计部经理直接审核
     * 权限范围：管理员、
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @return
     */
    @PostMapping(value = "affect-appearance-design-department-review-manager-direct")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isDesignDepartmentManager(#projectAppearanceReviewManagerDirectForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfDesignDepartmentManagerDirect(@RequestBody ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfDesignDepartmentManagerDirect(
                projectAppearanceReviewManagerDirectForm);
    }

    /**
     * 影响外观：送审操作：设计部经理分发给员工
     * 权限范围：管理员、设计部项目经理
     *
     * @param projectAppearanceReviewDispatchForm
     * @return
     */
    @PostMapping(value = "affect-appearance-design-department-review-manager-dispatch-to-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isDesignDepartmentManager(#projectAppearanceReviewDispatchForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfDesignDepartmentManagerDispatchToEmployee(@RequestBody ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfDesignDepartmentManagerDispatchToEmployee(
                projectAppearanceReviewDispatchForm);
    }

    /**
     * 影响外观：送审操作：设计部员工审核
     * 权限范围：管理员、设计部项目经理或员工
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "affect-appearance-design-department-review-of-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isDesignDepartmentManager(#projectAppearanceReviewEmployeeForm.projectId) 
            or @ProjectPermission.isDesignDepartmentEmployee(#projectAppearanceReviewEmployeeForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfDesignDepartmentEmployee(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfDesignDepartmentEmployee(
                projectAppearanceReviewEmployeeForm);
    }

    @PostMapping(value = "affect-appearance-design-department-remain-review-manager")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isDesignDepartmentManager(#projectAppearanceReviewEmployeeForm.projectId) 
            """)
    public String submitAffectAppearanceRemainReviewOfDesignDepartmentOfManager(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitAffectAppearanceRemainReviewOfDesignDepartmentOfManager(
                projectAppearanceReviewEmployeeForm);
    }

    /**
     * 影响外观：送审操作：设计部经理汇总
     * 权限范围：管理员、设计部项目经理
     *
     * @param projectAppearanceReviewManagerSummaryForm
     * @return
     */
    @PostMapping(value = "affect-appearance-design-department-review-manager-summary")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isDesignDepartmentManager(#projectAppearanceReviewManagerSummaryForm.projectId)
            """)
    public String submitAffectAppearanceReviewOfDesignDepartmentManagerSummary(@RequestBody ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm) {
        return projectMaterialFlow.submitAffectAppearanceReviewOfDesignDepartmentManagerSummary(
                projectAppearanceReviewManagerSummaryForm);
    }

    /**
     * 不影响外观：送审操作：工程部经理直接审核
     * 权限范围：管理员、
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @return
     */
    @PostMapping(value = "not-affect-appearance-engineering-department-review-manager-direct")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewManagerDirectForm.projectId)""")
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(@RequestBody ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm) {
        return projectMaterialFlow.submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(
                projectAppearanceReviewManagerDirectForm);
    }

    /**
     * 不影响外观：送审操作：工程部经理分发给员工
     * 权限范围：管理员、工程部项目经理
     *
     * @param projectAppearanceReviewDispatchForm
     * @return
     */
    @PostMapping(value = "not-affect-appearance-engineering-department-review-manager-dispatch-to-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewDispatchForm.projectId)
            """)
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(@RequestBody ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm) {
        return projectMaterialFlow.submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(
                projectAppearanceReviewDispatchForm);
    }

    /**
     * 不影响外观：送审操作：工程部员工审核
     * 权限范围：管理员、工程部项目员工
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "not-affect-appearance-engineering-department-review-of-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or @ProjectPermission.isInEngineeringDepartment(#projectAppearanceReviewEmployeeForm.projectId) 
            """)
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentEmployee(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitNotAffectAppearanceReviewOfEngineeringDepartmentEmployee(
                projectAppearanceReviewEmployeeForm);
    }

    @PostMapping(value = "not-affect-appearance-engineering-department-remain-review-manager")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewEmployeeForm.projectId) 
            or @ProjectPermission.isEngineeringDepartmentEmployee(#projectAppearanceReviewEmployeeForm.projectId)
            """)
    public String submitNotAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(@RequestBody ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        return projectMaterialFlow.submitNotAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(
                projectAppearanceReviewEmployeeForm);
    }


    /**
     * 不影响外观：送审操作：工程部经理汇总
     * 权限范围：管理员、工程部项目经理
     *
     * @param projectAppearanceReviewManagerSummaryForm
     * @return
     */
    @PostMapping(value = "not-affect-appearance-engineering-department-review-manager-summary")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectAppearanceReviewManagerSummaryForm.projectId)
            """)
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(@RequestBody ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm) {
        return projectMaterialFlow.submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(
                projectAppearanceReviewManagerSummaryForm);
    }


    /**
     * 总包单位订购物料
     *
     * @param buyMaterialForm
     * @return
     */
    @PostMapping(value = "general-contractor-buy-project-materials")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#buyMaterialForm.projectId)
            """)
    public String submitGeneralContractorBuyProjectMaterial(@RequestBody BuyMaterialForm buyMaterialForm) {
        return projectMaterialFlow.submitGeneralContractorBuyProjectMaterial(buyMaterialForm);
    }

    @PostMapping(value = "general-contractor-new-task-of-buy-project-materials")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#buyMaterialForm.projectId)
            """)
    public String submitGeneralContractorNewTaskOfBuyProjectMaterial(@RequestBody BuyMaterialForm buyMaterialForm) {
        return projectMaterialFlow.submitGeneralContractorNewTaskOfBuyProjectMaterial(buyMaterialForm);
    }


    /**
     * 总包单位填报工程材料、设备报验资料
     *
     * @param
     * @return
     */
    @PostMapping(value = "general-contractor-submit-material-verification-document")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#generalContractorSubProjectMaterialVerificationDocumentForm.project.id)
            """)
    public String submitGeneralContractorSubProjectMaterialVerificationDocument(@RequestBody GeneralContractorSubProjectMaterialVerificationDocumentForm generalContractorSubProjectMaterialVerificationDocumentForm) {
        return projectMaterialFlow.submitGeneralContractorSubProjectMaterialVerificationDocument(generalContractorSubProjectMaterialVerificationDocumentForm);
    }

    /**
     * 监理单位确定是否需要复检
     * 权限范围：管理员、监理单位员工
     *
     * @param projectMaterialRetestForm
     * @return
     */
    @PostMapping(value = "supervision-company-decide-whether-to-recheck")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInSupervisionCompany(#projectMaterialRetestForm.projectId)
            """)
    public String submitSupervisionCompanyDecideWhetherToRecheck(@RequestBody ProjectMaterialRetestForm projectMaterialRetestForm) {
        return projectMaterialFlow.submitSupervisionCompanyDecideWhetherToRecheck(projectMaterialRetestForm);
    }

    /**
     * 总包单位申报项目材料批次验收
     *
     * @param projectMaterialAcceptanceBatchForm
     * @return
     */
    @PostMapping(value = "general-contractor-submit-project-material-batch-acceptance")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#projectMaterialAcceptanceBatchForm.projectId)
            """)
    public String submitGeneralContractorProjectMaterialBatchAcceptance(@RequestBody
                                                                        ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm) {
        return projectMaterialFlow.submitGeneralContractorProjectMaterialBatchAcceptance(
                projectMaterialAcceptanceBatchForm);
    }
    @PostMapping(value = "general-contractor-submit-new-task-of-project-material-batch-acceptance")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInGeneralContractorCompany(#projectMaterialAcceptanceBatchForm.projectId)
            """)
    public String submitGeneralContractorNewTaskOfProjectMaterialAcceptance(@RequestBody
                                                                        ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm) {
        return projectMaterialFlow.submitGeneralContractorNewTaskOfProjectMaterialAcceptance(
                projectMaterialAcceptanceBatchForm);
    }



    /**
     * 监理单位对待复检项目材料进行审核
     * 权限范围：管理员、监理单位员工
     *
     * @param projectMaterialAcceptanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "supervision-company-project-material-batch-acceptance-review")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInSupervisionCompany(#projectMaterialAcceptanceReviewEmployeeForm.projectId)
            """)
    public String submitSupervisionCompanyProjectMaterialBatchAcceptanceReview(@RequestBody ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm) {
        return projectMaterialFlow.submitSupervisionCompanyProjectMaterialBatchAcceptanceReview(
                projectMaterialAcceptanceReviewEmployeeForm);
    }


    /**
     * 项目材料验收：送审操作：工程部经理直接审核
     * 权限范围：管理员、
     *
     * @param projectMaterialAcceptanceReviewManagerDirectForm
     * @return
     */
    @PostMapping(value = "project-material-acceptance-engineering-department-review-manager-direct")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectMaterialAcceptanceReviewManagerDirectForm.projectId)
            """)
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDirect(@RequestBody ProjectMaterialAcceptanceReviewManagerDirectForm projectMaterialAcceptanceReviewManagerDirectForm) {
        return projectMaterialFlow.submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDirect(
                projectMaterialAcceptanceReviewManagerDirectForm);
    }

    /**
     * 项目材料验收：送审操作：工程部经理分发给员工
     * 权限范围：管理员、工程部项目经理
     *
     * @param projectMaterialAcceptanceReviewDispatchForm
     * @return
     */
    @PostMapping(value = "project-material-acceptance-engineering-department-manager-dispatch-to-employee")
    @PreAuthorize("""
            hasAnyRole('Admin')
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectMaterialAcceptanceReviewDispatchForm.projectId)
            """)
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(@RequestBody ProjectMaterialAcceptanceReviewDispatchForm projectMaterialAcceptanceReviewDispatchForm) {
        return projectMaterialFlow.submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(
                projectMaterialAcceptanceReviewDispatchForm);
    }

    /**
     * 项目材料验收：送审操作：工程部员工审核
     * 权限范围：管理员、工程部项目员工
     *
     * @param projectMaterialAcceptanceReviewEmployeeForm
     * @return
     */
    @PostMapping(value = "project-material-acceptance-engineering-department-review-of-employee")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectMaterialAcceptanceReviewEmployeeForm.projectId) 
            or @ProjectPermission.isEngineeringDepartmentEmployee(#projectMaterialAcceptanceReviewEmployeeForm.projectId)
            """)
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentEmployee(@RequestBody ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm) {
        return projectMaterialFlow.submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentEmployee(
                projectMaterialAcceptanceReviewEmployeeForm);
    }

    @PostMapping(value = "project-material-acceptance-engineering-department-remain-review-manager")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectMaterialAcceptanceReviewEmployeeForm.projectId) 
            or @ProjectPermission.isEngineeringDepartmentEmployee(#projectMaterialAcceptanceReviewEmployeeForm.projectId)
            """)
    public String submitProjectMaterialAcceptanceRemainReviewOfEngineeringDepartmentOfManager(@RequestBody ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm) {
        return projectMaterialFlow.submitProjectMaterialAcceptanceRemainReviewOfEngineeringDepartmentOfManager(
                projectMaterialAcceptanceReviewEmployeeForm);
    }

    /**
     * 项目材料验收：送审操作：工程部经理汇总
     * 权限范围：管理员、工程部项目经理
     *
     * @param projectMaterialAcceptanceReviewManagerSummaryForm
     * @return
     */
    @PostMapping(value = "project-material-acceptance-engineering-department-review-manager-summary")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectMaterialAcceptanceReviewManagerSummaryForm.projectId)
            """)
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerSummary(@RequestBody ProjectMaterialAcceptanceReviewManagerSummaryForm projectMaterialAcceptanceReviewManagerSummaryForm) {
        return projectMaterialFlow.submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerSummary(
                projectMaterialAcceptanceReviewManagerSummaryForm);
    }


    /**
     * 工程部项目经理判断项目是否已经完工
     *
     * @param projectEndForm
     * @return
     */
    @PostMapping(value = "engineering-department-manager-end-the-project")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isEngineeringDepartmentManager(#projectEndForm.projectId)
            """)
    public String submitEngineeringDepartmentManagerEndTheProject(@RequestBody ProjectEndForm projectEndForm) {
        return projectMaterialFlow.submitEngineeringDepartmentManagerEndTheProject(
                projectEndForm);
    }

    /**
     * 查询总包单位的品牌选择与物料申请
     * 权限范围：管理员、或者项目参与人员（设计单位、设计部、建设部、监理单位、总包单位等项目经理或项目员工）
     *
     * @return
     */
    @GetMapping(value = "get-use-material-brand-select-view-by-current-login-user-and-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public UseMaterialBrandSelectView getUseMaterialBrandSelectViewByCurrentLoginUser(@RequestParam(value = "projectId") String projectId,
                                                                                      @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getUseMaterialBrandSelectViewByCurrentLoginUser(projectId, taskId);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUser(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUser(projectId, taskId, pageNo,
                                                                                          pageSize);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id-and-name")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUserAndName(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "name") String name,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUserAndName(projectId, taskId, name, pageNo,
                                                                                          pageSize);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id-and-location")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUserAndLocation(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "location") String location,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUserAndLocation(projectId, taskId, location, pageNo,
                                                                                          pageSize);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id-and-item-mark")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUserAndItemMark(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "itemMark") String itemMark,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUserAndItemMark(projectId, taskId, itemMark, pageNo,
                                                                                          pageSize);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id-and-technology")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUserAndTechnology(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "technology") String technology,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUserAndTechnology(projectId, taskId, technology, pageNo,
                                                                                          pageSize);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id-and-installation")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUserAndInstallation(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "installation") String installation,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUserAndInstallation(projectId, taskId, installation, pageNo,
                                                                                          pageSize);
    }

    @GetMapping(value = "page-project-material-acceptance-view-by-current-login-user-and-project-id-and-task-id-and-brand")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUserAndBrand(@RequestParam(value = "projectId") String projectId,
                                                                                                      @RequestParam(value = "taskId") String taskId,
                                                                                                      @RequestParam(value = "brand") String brand,
                                                                                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        return projectMaterialFlow.getProjectMaterialAcceptanceViewListByCurrentLoginUserAndBrand(projectId, taskId, brand, pageNo,
                                                                                          pageSize);
    }


    @GetMapping(value = "statistics-project-material-review-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectReviewStatistics getStatisticsOfProjectMaterialReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "designCompanyIndex") int designCompanyIndex) {

        return projectMaterialFlow.getStatisticsOfProjectMaterialReviewUserViewByTaskId(taskId, projectId ,designCompanyIndex);
    }


    @GetMapping(value = "statistics-appearance-review-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectReviewStatistics getStatisticsOfAppearanceReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getStatisticsOfAppearanceReviewUserViewByTaskId(taskId);
    }

    @GetMapping(value = "statistics-design-department-appearance-review-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectReviewStatistics getStatisticsOfDesignDepartmentAppearanceReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getStatisticsOfDesignDepartmentAppearanceReviewUserViewByProjectIdAndTaskId(
                projectId, taskId);
    }
    @GetMapping(value = "statistics-engineering-department-material-acceptance-review-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectReviewStatistics getStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByTaskId(
                projectId, taskId);
    }
    @GetMapping(value = "statistics-project-review-by-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectStatisticalAnalysis getStatisticsOfProjectViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId
            ) {

        return projectMaterialFlow.getStatisticsOfProjectViewByTaskId(taskId, projectId);
    }



    @GetMapping(value = "page-appearance-review-user-view-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectAppearanceReviewUserView> getPageOfProjectAppearanceReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialFlow.getPageProjectAppearanceReviewUserViewByProjectIdAndTaskId(projectId, taskId, pageNo,
                                                                                              pageSize);
    }

        @GetMapping(value = "page-material-acceptance--review-user-view-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialAcceptanceReviewUserView> getPageOfProjectMaterialAccptanceReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialFlow.getPageProjectMaterialAcceptanceReviewUserViewByProjectIdAndTaskId(projectId, taskId, pageNo,
                                                                                              pageSize);
    }


    @GetMapping(value = "page-appearance-review-design-department-user-view-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectAppearanceReviewUserView> getPageOfProjectAppearanceReviewDesignDepartmentUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialFlow.getPageOfProjectAppearanceReviewDesignDepartmentUserViewByProjectIdAndTaskId(
                projectId, taskId,
                pageNo,
                pageSize);
    }

    @GetMapping(value = "list-appearance-review-user-view-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public List<ProjectAppearanceReviewUserView> getListOfProjectAppearanceReviewUserViewByTaskId(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {
        return projectMaterialFlow.getListOfProjectAppearanceReviewUserViewByProjectIdAndTaskId(projectId, taskId);
    }

    @GetMapping(value = "page-project-material-user-review-by-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectReviewUserView> getProjectMaterialUserUserReViewPageByTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "designCompanyIndex" ) int designCompanyIndex,
            @RequestParam(value = "reviewUser") String reviewUser,
            @RequestParam(value = "reviewResult") int reviewResult,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialFlow.getProjectMaterialUserUserReViewPageByProjectIdAndTaskId(projectId, taskId, designCompanyIndex, reviewUser, reviewResult, pageNo,
                                                                                            pageSize);
    }


    @GetMapping(value = "list-verification-document-view-by-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public List<ProjectMaterialVerificationDocumentView> getBuyMaterialVerificationDocumentPageViewByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getBuyMaterialVerificationDocumentViewByProjectIdAndTaskId(projectId, taskId);
    }


    @GetMapping(value = "list-recheck-is-required-by-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public List<ProjectMaterialVerificationDocumentView> getBuyMaterialRecheckIsRequiredByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getBuyMaterialRecheckIsRequiredByProjectIdAndTaskId(projectId, taskId);
    }

    @GetMapping(value = "get-feedback-of-project-material-reviewed-by-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectReviewUserView getFeedbackOfProjectMaterialReviewedByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId
         ) {

        return projectMaterialFlow.getFeedbackOfProjectMaterialReviewedByProjectIdAndTaskId(projectId, taskId);
    }

    @GetMapping(value = "get-feedback-of-supervision-company-select-brand-reviewed-by-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public ProjectAppearanceReviewUserView getFeedbackOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getFeedbackOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(projectId,
                                                                                                            taskId);
    }
    @GetMapping(value ="get-feedback-of-affect-appearance-review-of-engineeringdepartment-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)
    """)
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewOfEngineeringDepartmentByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfAffectAppearanceReviewedOfEngineeringDepartmentByProjectAndTaskId(
                projectId, taskId);
    }
    @GetMapping(value ="get-feedback-of--not-affect-appearance-review-of-engineeringdepartment-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)
    """)
    public ProjectAppearanceReviewUserView getFeedbackOfNotAffectAppearanceReviewOfEngineeringDepartmentByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfNotAffectAppearanceReviewedOfEngineeringDepartmentByProjectAndTaskId(
                projectId, taskId);
    }
    @GetMapping(value ="get-feedback-of-affect-appearance-review-of-designdcompany-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)
    """)
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewOfDesignCompanyByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfAffectAppearanceReviewedOfDesignCompanyByProjectAndTaskId(
                projectId, taskId);
    }
    @GetMapping(value ="get-feedback-of-affect-appearance-review-of-designdepartment-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)
    """)
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewOfDesignDepartmentByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfAffectAppearanceReviewedOfDesignDepartmentByProjectAndTaskId(
                projectId, taskId);
    }
      @GetMapping(value ="get-feedback-of-need-recheck-not-passed-of-supervision-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)""")
    public List<ProjectMaterialRetestView> getFeedbackOfNeedReCheckNotPassedOfSupervisionCompanyByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfNeedReCheckNotPassedOfSupervisionCompanyByProjectIdAndTaskId(
                projectId, taskId);
    }
    @GetMapping(value ="get-feedback-of-material-acceptance-review-of-supervisioncompany-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)
    """)
    public ProjectMaterialAcceptanceReviewUserView getFeedbackOfProjectMaterialAcceptanceReviewedOfSupervisionCompanyByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfProjectMaterialAcceptanceReviewedOfSupervisionCompanyByProjectIdAndTaskId(
                projectId, taskId);
    }
    @GetMapping(value ="get-feedback-of-material-acceptance-review-of-engineeringdepartment-by-project-id-and-task-id")
    @PreAuthorize("""
    hasAnyRole('Admin')
    or @ProjectPermission.isInProject(#projectId)
    """)
    public ProjectMaterialAcceptanceReviewUserView getFeedbackOfProjectMaterialAcceptanceReviewedOfEngineeringDepartmentByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId){

        return projectMaterialFlow.getFeedbackOfProjectMaterialAcceptanceReviewedOfEngineeringDepartmentByProjectIdAndTaskId(
                projectId, taskId);
    }




    @GetMapping(value = "get-not-passed-brand-supervision-company-select-brand-reviewed-by-project-id-and-task-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public UseMaterialBrandSelectView getNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId) {

        return projectMaterialFlow.getNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
                projectId,
                taskId);
    }


    @GetMapping(value = "page-view-project-material-by-task-id-and-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialView> getProjectMaterialPageViewByTaskIdAndProjectId(
            @RequestParam("projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "designCompanyIndex") Integer designCompanyIndex,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "itemMark", required = false) String itemMark,
            @RequestParam(value = "technology", required = false) String technology,
            @RequestParam(value = "installation", required = false) String installation,
            @RequestParam(value = "brandPublic", required = false) String brandPublic,
            @RequestParam(value = "brandPrivate", required = false) String brandPrivate,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialFlow.getProjectMaterialPageViewByTaskIdAndProjectId(projectId, taskId, designCompanyIndex,
                                                                                  name, location, itemMark, technology, installation, brandPublic, brandPrivate,
                                                                                  pageNo, pageSize);
    }
}