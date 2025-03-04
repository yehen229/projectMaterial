package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAllUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
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

    @GetMapping(value = "page-all-user-view-by-user-name")
    public Page<ProjectAllUserView> getPageAllUserViewByUserName(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectUserService.getPageAllUserViewByUserName(userName, pageNo, pageSize);
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