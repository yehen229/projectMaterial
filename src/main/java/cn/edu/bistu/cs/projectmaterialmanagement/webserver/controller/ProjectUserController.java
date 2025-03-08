package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAllUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectuser/v1")
@EnableMethodSecurity
public class ProjectUserController {
    private final IProjectUserService projectUserService;
    private final ICompanyUserService companyUserService;

    ProjectUserController(IProjectUserService projectUserService,
                          ICompanyUserService companyUserService) {
        this.projectUserService = projectUserService;
        this.companyUserService = companyUserService;
    }

    /**
     * 当前登录是否项目设计公司员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-design-company-employee")
    public boolean getCurrentLoginUserIsDesignCompanyEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserDesignCompanyEmployee(projectId);
    }

    @GetMapping(value = "get-current-login-user-is-in-design-or-engineering-department")
    public boolean getCurrentLoginUserIsInDesignOrEngineeringDepartment() {
        return projectUserService.isCurrentLoginUserInDesignDepartment() || projectUserService.isCurrentLoginUserInEngineeringDepartment();
    }


    /**
     * 当前登录用户单位是否为设计部
     *
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-in-design-department")
    public boolean getCurrentLoginUserIsInDesignDepartment() {
        return projectUserService.isCurrentLoginUserInDesignDepartment();
    }

    /**
     * 当前登录是否设计部项目员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-design-department-employee")
    public boolean getCurrentLoginUserIsDesignDepartmentProjectEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserDesignDepartmentEmployee(projectId);
    }

    /**
     * 当前登录是否设计部项目经理
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-design-department-manager")
    public boolean getCurrentLoginUserIsDesignDepartmentManager(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserDesignDepartmentManager(projectId);
    }

    /**
     * 当前登录是否设计部员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-design-department-manager-or-employee")
    public boolean getCurrentLoginUserIsDesignDepartmentManagerOrEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserDesignDepartmentEmployee(projectId) ||
                projectUserService.isCurrentLoginUserDesignDepartmentManager(projectId);
    }


    /**
     * 得到设计部项目经理人选(即去除掉项目员工后的人员)
     * @param projectId
     * @param pageNo
     * @return
     */
    @GetMapping(value = "page-design-department-manager-candidates-view-by-project-id")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInProject(#projectId)")
    public Page<CompanyUserView> getDesignDepartmentManagerCandidatesViewPageByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                                   @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize)  {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getDesignDepartmentManagerCandidatesViewPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 得到设计部项目员工人选(即去除掉项目经理后的人员)
     * @param projectId
     * @param pageNo
     * @return
     */
    @GetMapping(value = "page-design-department-employee-candidates-view-by-project-id")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInProject(#projectId)")
    public Page<CompanyUserView> getDesignDepartmentEmployeeCandidatesViewPageByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                                         @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize)  {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getDesignDepartmentEmployeeCandidatesViewPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 当前登录用户是否为工程部员工
     *
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-in-engineering-department")
    public boolean getCurrentLoginUserIsInEngineeringDepartment() {
        return projectUserService.isCurrentLoginUserInEngineeringDepartment();
    }


    /**
     * 当前登录是否工程部员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-engineering-department-employee")
    public boolean getCurrentLoginUserIsEngineeringDepartmentEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserEngineeringDepartmentEmployee(projectId);
    }

    /**
     * 当前登录是否工程部经理
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-engineering-department-manager")
    public boolean getCurrentLoginUserIsEngineeringDepartmentManager(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserEngineeringDepartmentManager(projectId);
    }

    /**
     * 当前登录是否工程部经理或员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-engineering-department-manager-or-employee")
    public boolean getCurrentLoginUserIsEngineeringDepartmentManagerOrEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserEngineeringDepartmentManager(projectId)
                || projectUserService.isCurrentLoginUserEngineeringDepartmentEmployee(projectId);
    }


    /**
     * 项目是否存在设计部经理
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-exists-design-department-manager")
    public boolean getExistsDesignDepartmentManager(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getExistsDesignDepartmentManager(projectId);
    }


    /**
     * 项目是否存在工程部经理
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-exists-engineering-department-manager")
    public boolean getExistsEngineeringDepartmentManager(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getExistsEngineeringDepartmentManager(projectId);
    }

    /**
     * 项目设计部经理
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-design-department-manager")
    public ProjectUserView getDesignDepartmentManager(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getDesignDepartmentManager(projectId);
    }


    /**
     * 项目工程部经理
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-engineering-department-manager")
    public ProjectUserView getEngineeringDepartmentManager(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getEngineeringDepartmentManager(projectId);
    }


    /**
     * 得到工程部项目经理人选(即去除掉项目员工后的人员)
     * @param projectId
     * @param pageNo
     * @return
     */
    @GetMapping(value = "page-engineering-department-manager-candidates-view-by-project-id")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInProject(#projectId)")
    public Page<CompanyUserView> getEngineeringDepartmentManagerCandidatesViewPageByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                                         @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize)  {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getEngineeringDepartmentManagerCandidatesViewPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 得到工程部项目员工人选(即去除掉项目经理后的人员)
     * @param projectId
     * @param pageNo
     * @return
     */
    @GetMapping(value = "page-engineering-department-employee-candidates-view-by-project-id")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInProject(#projectId)")
    public Page<CompanyUserView> getEngineeringDepartmentEmployeeCandidatesViewPageByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                                          @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize)  {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getEngineeringDepartmentEmployeeCandidatesViewPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 当前登录是否为总包公司员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-general-contractor-company-employee")
    public boolean getCurrentLoginUserIsGeneralContractorCompanyEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserGeneralContractorCompanyEmployee(projectId);
    }

    /**
     * 当前登录是否为监理公司员工
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-supervision-company-employee")
    public boolean getCurrentLoginUserIsSupervisionCompanyEmployee(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.isCurrentLoginUserSupervisionCompanyEmployee(projectId);
    }





    @GetMapping(value = "get-by-id")
    public ProjectUser getById(@RequestParam(value = "id") String id) {
        return projectUserService.getById(id);
    }


    @GetMapping(value = "get-project-all-user-view-by-project-id")
    public ProjectAllUserView getProjectAllUserViewByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getProjectAllUserViewByProjectId(projectId);
    }

    @GetMapping(value = "get-by-user-id")
    public List<ProjectUser> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectUserService.getByUserId(userId);
    }

    /**
     * 得到有人员参与该项目的所有公司（从project_user中得到）
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-company-by-project-id")
    public List<Company> getCompanyByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getCompanyByProjectId(projectId);
    }


    /**
     * 得到某个项目下的所有项目用户
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "get-by-project-id")
    public List<ProjectUser> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getByProjectId(projectId);
    }


    @GetMapping(value = "get-role-by-user-id-and-project-id")
    public Role getByRole(@RequestParam(value = "userId") String userId,
                          @RequestParam(value = "projectId") String projectId) {
        return projectUserService.getRoleByUserIdAndProjectId(userId, projectId);
    }

    @GetMapping(value = "get-by-role-id")
    public List<ProjectUser> getByRoleId(@RequestParam(value = "roleId") String roleId) {
        return projectUserService.getByRoleId(roleId);
    }

    @GetMapping(value = "get-design-department-employee-by-project-id")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.isInProject(#projectId)")
    public List<ProjectUser> getDesignDepartmentEmployeeByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getEmployeeOfDesignDepartment(projectId);
    }

    @GetMapping(value = "get-design-department-employee-user-by-project-id")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.isInProject(#projectId)")
    public List<User> getEmployeeUserOfDesignDepartment(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getEmployeeUserOfDesignDepartment(projectId);
    }

    @GetMapping(value = "get-engineering-department-employee-by-project-id")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.isInProject(#projectId)")
    public List<ProjectUser> getEngineeringDepartmentEmployeeByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getEmployeeOfEngineeringDepartment(projectId);
    }

    @GetMapping(value = "get-engineering-department-employee-user-by-project-id")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.isInProject(#projectId)")
    public List<User> getEmployeeUserOfEngineeringDepartment(@RequestParam(value = "projectId") String projectId) {
        return projectUserService.getEmployeeUserOfEngineeringDepartment(projectId);
    }


    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectUser projectUser) {
        return projectUserService.add(projectUser);
    }

    @PostMapping(value = "add-manager")
    @PreAuthorize("hasRole('Admin')")
    public String addManager(@RequestBody ProjectUser projectUser) {
        return projectUserService.addManager(projectUser);
    }

    @PostMapping(value = "add-employee")
    @PreAuthorize("hasRole('Admin')")
    public String addEmployee(@RequestBody ProjectUser projectUser) {
        return projectUserService.addEmployee(projectUser);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectUser projectUser) {
        return projectUserService.delete(projectUser);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectUser projectUser) {
        return projectUserService.update(projectUser);
    }


    @GetMapping(value = "page")
    public Page<ProjectUser> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-all-user-view")
    public Page<ProjectAllUserView> getPageAllUserView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getPageAllUserView(pageNo, pageSize);
    }

    @GetMapping(value = "page-all-user-view-by-real-name")
    public Page<ProjectAllUserView> getPageAllUserViewByRealName(
            @RequestParam(value = "realName") String realName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getPageAllUserViewByRealName(realName, pageNo, pageSize);
    }

    @GetMapping(value = "page-all-user-view-by-company-name")
    public Page<ProjectAllUserView> getPageAllUserViewByCompanyName(
            @RequestParam(value = "companyName") String companyName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getPageAllUserViewByCompanyName(companyName, pageNo, pageSize);
    }

    @GetMapping(value = "page-all-user-view-by-project-name")
    public Page<ProjectAllUserView> getPageAllUserViewByProjectName(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getPageAllUserViewByProjectName(projectName, pageNo, pageSize);
    }


}