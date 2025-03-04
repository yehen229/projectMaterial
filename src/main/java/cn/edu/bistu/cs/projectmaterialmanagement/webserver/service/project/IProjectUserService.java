package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAllUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserView;

import java.util.List;

public interface IProjectUserService {
    String add(ProjectUser projectUser);

    String addManager(ProjectUser projectUser);

    String addEmployee(ProjectUser projectUser);

    int delete(ProjectUser projectUser);

    int update(ProjectUser projectUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectId(String projectId);

    int deleteByRoleId(String roleId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectId(String projectId);

    int getCountByRoleId(String roleId);

    //用户是否是项目的项目经理
    boolean isManager(String userId, String projectId);

    //用户是否是项目的项目员工
    boolean isEmployee(String userId, String projectId);

    //是否存在项目经理
    boolean isExistManager(String projectId, String companyId);

    //用户是否参与了项目
    boolean isInProject(String projectId, String userId);

    boolean isCurrentLoginUserInProject(String projectId);

    boolean isCurrentLoginUserInDesignDepartment();

    boolean isCurrentLoginUserInEngineeringDepartment();

    //当前登录用户是否是项目的设计单位员工
    boolean isCurrentLoginUserDesignCompanyEmployee(String projectId);

    //当前登录用户是否是项目的设计部门员工
    boolean isCurrentLoginUserDesignDepartmentEmployee(String projectId);

    //当前登录用户是否是项目的设计部项目经理
    boolean isCurrentLoginUserDesignDepartmentManager(String projectId);

    //当前登录用户是否是项目的工程部门员工
    boolean isCurrentLoginUserEngineeringDepartmentEmployee(String projectId);

    //当前登录用户是否是工程部该项目的项目经理
    boolean isCurrentLoginUserEngineeringDepartmentManager(String projectId);

    //当前登录用户是否是项目的总包单位员工
    boolean isCurrentLoginUserGeneralContractorCompanyEmployee(String projectId);

    //当前登录用户是否是项目的监理单位员工
    boolean isCurrentLoginUserSupervisionCompanyEmployee(String projectId);


    //用户是否是设计部
    boolean isUserInDesignDepartment(String userId);

    //用户是否是工程部
    boolean isUserInEngineeringDepartment(String userId);

    //用户是否是项目的设计单位员工
    boolean isUserDesignCompanyEmployee(String projectId, String userId);

    //用户是否是项目的设计部门员工
    boolean isUserDesignDepartmentEmployee(String projectId, String userId);

    //用户是否是项目的设计部项目经理
    boolean isUserDesignDepartmentManager(String projectId, String userId);

    //用户是否是项目的工程部门员工
    boolean isUserEngineeringDepartmentEmployee(String projectId, String userId);

    //用户是否是工程部该项目的项目经理
    boolean isUserEngineeringDepartmentManager(String projectId, String userId);

    //用户是否是项目的总包单位员工
    boolean isUserGeneralContractorCompanyEmployee(String projectId, String userId);

    //用户是否是项目的监理单位员工
    boolean isUserSupervisionCompanyEmployee(String projectId, String userId);


    Role getRoleByUserIdAndProjectId(String userId, String projectId);


    ProjectUser getById(String id);

    ProjectAllUserView getProjectAllUserViewByProjectId(String projectId);


    //得到项目经理，注意，一个项目只能有一个项目经理
    ProjectUser getManager(String projectId, String companyId);

    //得到工程部门项目经理和员工
    ProjectUser getManagerOfEngineeringDepartment(String projectId);

    List<ProjectUser> getEmployeeOfEngineeringDepartment(String projectId);

    List<ProjectUser> getManagerAndEmployeeOfEngineeringDepartment(String projectId);

    List<User> getEmployeeUserOfEngineeringDepartment(String projectId);


    //得到设计部门项目经理和员工
    ProjectUser getManagerOfDesignDepartment(String projectId);

    List<ProjectUser> getEmployeeOfDesignDepartment(String projectId);

    List<ProjectUser> getManagerAndEmployeeOfDesignDepartment(String projectId);


    List<User> getEmployeeUserOfDesignDepartment(String projectId);


    List<ProjectUser> getEmployee(String projectId);

    //得到设计单位员工
    List<ProjectUser> getDesignCompanyEmployees(String projectId);


    //得到总包公司员工
    List<ProjectUser> getGeneralContractorCompanyEmployees(String projectId);

    //得到监理公司员工
    List<ProjectUser> getSupervisionCompanyEmployees(String projectId);

    ProjectUser getByUserIdAndProjectId(String userId, String projectId);

    List<ProjectUser> getByRoleIdAndProjectId(String roleId, String projectId);

    List<ProjectUser> getByUserIdIdAndRoleId(String userId, String roleId);

    List<Company> getCompanyByProjectId(String projectId);

    List<ProjectUser> getByUserId(String userId);

    List<ProjectUser> getByProjectId(String projectId);

    List<ProjectUser> getByRoleId(String roleId);

    List<ProjectUser> getManagers(String projectId);

    List<ProjectUser> getEmployee(String projectId, String companyId);

    Page<ProjectUser> getPage(int pageNo, int pageSize);

    Page<ProjectUser> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectUser> getPageByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectUser> getPageByRoleId(String roleId, int pageNo, int pageSize);

    Page<ProjectUserView> getPageView(int pageNo, int pageSize);

    Page<ProjectUserView> getPageViewByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectUserView> getPageViewByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectUserView> getPageViewByRoleId(String roleId, int pageNo, int pageSize);

    Page<ProjectAllUserView> getPageAllUserView(Integer pageNo, Integer pageSize);

    Page<ProjectAllUserView> getPageAllUserViewByProjectName(String projectName, Integer pageNo, Integer pageSize);

    Page<ProjectAllUserView> getPageAllUserViewByCompanyName(String companyName, Integer pageNo, Integer pageSize);

    Page<ProjectAllUserView> getPageAllUserViewByUserName(String userName, Integer pageNo, Integer pageSize);


    Page<CompanyUserView> convert(Page<CompanyUserView> companyUserViewPage, Integer pageNo, Integer pageSize);


}