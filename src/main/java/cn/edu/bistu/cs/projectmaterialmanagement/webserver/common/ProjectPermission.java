package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.springframework.stereotype.Component;

@Component("ProjectPermission")
public class ProjectPermission {
    private final IUserService userService;
    private final IProjectService projectService;
    private final IProjectUserService projectUserService;
    private final ICompanyUserService companyUserService;

    public ProjectPermission(IUserService userService,
                             IProjectService projectService,
                             IProjectUserService projectUserService,
                             ICompanyUserService companyUserService) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectUserService = projectUserService;
        this.companyUserService = companyUserService;
    }

    /**
     * 是否参与了指定的项目
     * 当前登录用户是否是项目的设计单位、设计部、工程部、监理单位、总包单位等员工（项目经理或项目员工都可以）
     *
     * @param projectId
     * @return
     */
    public boolean isInProject(String projectId) {
        return projectUserService.isCurrentLoginUserInProject(projectId);
    }

    /**
     * 是否是设计部门员工
     *
     * @return
     */

    public boolean isDesignDepartment() {
        User user = userService.getCurrentLoginUser();
        return companyUserService.isDesignDepartmentByUserId(user.getId());
    }

    /**
     * 当前登录用户是否为指定项目的设计部项目员工
     *
     * @param projectId
     * @return
     */

    public boolean isInDesignDepartment(String projectId) {

        return isDesignDepartmentManager(projectId) || isDesignDepartmentEmployee(projectId);
    }


    /**
     * 当前登录用户是否为指定项目的设计部项目经理
     *
     * @param projectId
     * @return
     */
    public boolean isDesignDepartmentManager(String projectId) {
        return projectUserService.isCurrentLoginUserDesignDepartmentManager(projectId);
    }

    /**
     * 当前登录用户是否为指定项目的设计部项目员工
     *
     * @param projectId
     * @return
     */
    public boolean isDesignDepartmentEmployee(String projectId) {
        return projectUserService.isCurrentLoginUserDesignDepartmentEmployee(projectId);
    }

    /**
     * 是否是工程部门员工
     *
     * @return
     */
    public boolean isEngineeringDepartment() {
        User user = userService.getCurrentLoginUser();
        return companyUserService.isEngineeringDepartmentByUserId(user.getId());
    }

    public boolean isInEngineeringDepartment(String projectId) {
        User user = userService.getCurrentLoginUser();
        return isEngineeringDepartmentEmployee(projectId) || isEngineeringDepartmentManager(projectId);
    }


    /**
     * 当前登录用户是否为指定项目的工程部工程员工
     *
     * @param projectId
     * @return
     */
    public boolean isEngineeringDepartmentEmployee(String projectId) {
        return projectUserService.isCurrentLoginUserEngineeringDepartmentEmployee(projectId);
    }


    /**
     * 当前登录用户是否为指定项目的工程部项目经理
     *
     * @param projectId
     * @return
     */
    public boolean isEngineeringDepartmentManager(String projectId) {
        return projectUserService.isCurrentLoginUserEngineeringDepartmentManager(projectId);
    }


    /**
     * 是否是设计单位员工
     *
     * @return
     */
    public boolean isDesignCompany() {
        User user = userService.getCurrentLoginUser();
        return companyUserService.isDesignCompanyByUserId(user.getId());
    }

    /**
     * 当前登录用户是否为项目的设计单位员工
     *
     * @param projectId
     * @return
     */
    public boolean isInDesignCompany(String projectId) {
        return projectUserService.isCurrentLoginUserDesignCompanyEmployee(projectId);
    }

    /**
     * 是否是总包单位员工
     *
     * @return
     */

    public boolean isGeneralContractorCompany() {
        User user = userService.getCurrentLoginUser();
        return companyUserService.isGeneralContractorCompanyByUserId(user.getId());
    }

    /**
     * 当前登录用户是否为项目的总包单位员工
     *
     * @param projectId 项目Id
     * @return
     */
    public boolean isInGeneralContractorCompany(String projectId) {
        return projectUserService.isCurrentLoginUserGeneralContractorCompanyEmployee(projectId);
    }

    /**
     * 是否是监理单位员工
     *
     * @return
     */

    public boolean isSupervisionCompany() {
        User user = userService.getCurrentLoginUser();
        return companyUserService.isSupervisionCompanyByUserId(user.getId());
    }


    /**
     * 当前登录用户是否为项目的监理单位员工
     *
     * @param projectId 项目Id
     * @return
     */
    public boolean isInSupervisionCompany(String projectId) {

        return projectUserService.isCurrentLoginUserSupervisionCompanyEmployee(projectId);
    }


}
