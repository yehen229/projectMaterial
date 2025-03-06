package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IRoleService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectCompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectDesignCompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectUserServiceImpl implements IProjectUserService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectUserServiceImpl.class);

    private final IProjectUserRepository projectUserRepository;
    private final IProjectService projectService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final ICompanyService companyService;
    private final ICompanyUserService companyUserService;
    private final IProjectCompanyService projectCompanyService;
    private final IProjectDesignCompanyService projectDesignCompanyService;

    public ProjectUserServiceImpl(IProjectUserRepository projectUserRepository,
                                  IProjectService projectService,
                                  IUserService userService,
                                  IRoleService roleService,
                                  ICompanyService companyService,
                                  ICompanyUserService companyUserService,
                                  IProjectCompanyService projectCompanyService,
                                  IProjectDesignCompanyService projectDesignCompanyService) {
        this.projectUserRepository = projectUserRepository;
        this.projectService = projectService;
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
        this.companyUserService = companyUserService;
        this.projectCompanyService = projectCompanyService;
        this.projectDesignCompanyService = projectDesignCompanyService;
    }


    private ProjectView getProjectViewByProjectId(String projectId) {
        ProjectView projectView = new ProjectView();
        ProjectCompany projectCompany = projectCompanyService.getByProjectId(projectId);
        Project project = projectService.getById(projectId);
        projectView.setProject(projectService.getById(projectId));
        projectView.setCompanyConstruction(companyService.getById(project.getCompanyConstructionId()));
        projectView.setCompanyDesignList(projectDesignCompanyService.getCompanyByProjectId(projectId));
        projectView.setUser(userService.getById(project.getUserId()));

        if (projectCompany != null) {

            projectView.setCompanySupervision(companyService.getById(projectCompany.getSupervisionCompanyId()));
            projectView.setCompanyGeneralContract(
                    companyService.getById(projectCompany.getGeneralContractorCompanyId()));
        }

        return projectView;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectUser projectUser) {
        return projectUserRepository.add(projectUser);
    }

    /**
     * 增加项目经理，注意，项目经理只能有一个，不能多个
     *
     * @param projectUser
     * @return
     */
    @Override
    public String addManager(ProjectUser projectUser) {
        if (projectUser != null) {
            Role role = roleService.getManager();
            if (role == null)
                throw new BusinessException("角色不存在，不能添加");


            //是否参与了项目，已经参与的话，则不能添加。即用户在每个项目中只能是一个角色：项目经理、项目员工
            if (isInProject(projectUser.getProjectId(), projectUser.getUserId()))
                throw new BusinessException("用户已经参与了项目，不能添加");

            CompanyUser companyUser = companyUserService.getByUserId(projectUser.getUserId());
            if (companyUser == null)
                throw new BusinessException("用户不是公司成员，不能添加");

            //查找是否存在项目经理
            if (isExistManager(projectUser.getProjectId(), companyUser.getCompanyId()))
                throw new BusinessException("项目经理已经存在，不能添加");

            projectUser.setRoleId(role.getId());
            return add(projectUser);

        }

        return "";
    }

    @Override
    public String addEmployee(ProjectUser projectUser) {
        if (projectUser != null) {

            //是否参与了项目，已经参与的话，则不能添加。即用户在每个项目中只能是一个角色：项目经理、项目员工
            if (isInProject(projectUser.getProjectId(), projectUser.getUserId()))
                throw new BusinessException("用户已经参与了项目，不能添加");

            Role role = roleService.getEmployee();
            if (role != null) {
                projectUser.setRoleId(role.getId());
                return add(projectUser);
            }
        }

        return "";
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectUser projectUser) {
        return projectUserRepository.delete(projectUser);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectUser projectUser) {
        return projectUserRepository.update(projectUser);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectUserRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectUserRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectUserRepository.deleteByProjectId(projectId);
    }

    /**
     * 根据roleId删除记录
     *
     * @param roleId
     */
    @Override
    public int deleteByRoleId(String roleId) {
        return projectUserRepository.deleteByRoleId(roleId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectUserRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectUserRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectUserRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据roleId得到数量
     *
     * @param roleId
     */
    @Override
    public int getCountByRoleId(String roleId) {
        return projectUserRepository.getCountByRoleId(roleId);
    }

    /**
     * 判断用户是否是项目经理
     *
     * @param userId
     * @param projectId
     * @return
     */
    @Override
    public boolean isManager(String userId,
                             String projectId) {
        if (projectId == null || userId == null)
            return false;
        ProjectUser projectUser = projectUserRepository.getByUserIdAndProjectId(userId, projectId);
        if (projectUser != null) {
            Role role = roleService.getManager();
            return role.getId().equals(projectUser.getRoleId());
        }
        return false;
    }

    @Override
    public boolean isEmployee(String userId,
                              String projectId) {
        if (projectId == null || userId == null)
            return false;
        ProjectUser projectUser = projectUserRepository.getByUserIdAndProjectId(userId, projectId);
        if (projectUser != null) {
            Role role = roleService.getEmployee();
            return role.getId().equals(projectUser.getRoleId());
        }
        return false;
    }

    /**
     * 判断是否存在项目经理。注意，一个项目在一个单位内只能有一个项目经理
     *
     * @param projectId 项目Id
     * @return 存在项目经理返回true，否则返回false
     */
    @Override
    public boolean isExistManager(String projectId,
                                  String companyId) {
        if (projectId == null || companyId == null)
            return false;
        ProjectUser projectUser = getManager(projectId, companyId);
        return projectUser != null;
    }

    /**
     * 判断用户是否在项目中。
     *
     * @param projectId 项目Id
     * @param userId    用户id
     * @return 参与项目返回true，否则返回false
     */
    @Override
    public boolean isInProject(String projectId,
                               String userId) {
        if (projectId == null || userId == null)
            return false;
        Project project = projectService.getById(projectId);
        //项目创建者
        //if (project.getUserId().equalsIgnoreCase(userId))
        //    return true;
        return projectUserRepository.getByUserIdAndProjectId(userId, projectId) != null;
    }


    /**
     * 当前登录用户是否在项目中。
     *
     * @param projectId 项目Id
     * @return 参与项目返回true，否则返回false
     */
    @Override
    public boolean isCurrentLoginUserInProject(String projectId) {
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isInProject(projectId, currentUser.getId());
    }


    /**
     * 当前登录用户是否在设计部。
     *
     * @return true表示在，false表示不在
     */
    @Override
    public boolean isCurrentLoginUserInDesignDepartment() {
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserInDesignDepartment(currentUser.getId());
    }

    @Override
    public boolean isCurrentLoginUserInEngineeringDepartment() {
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserInEngineeringDepartment(currentUser.getId());
    }

    /**
     * 当前登录用户是否是设计单位员工
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserDesignCompanyEmployee(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserDesignCompanyEmployee(projectId, currentUser.getId());

    }

    /**
     * 当前登录用户是否是设计部项目员工
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserDesignDepartmentEmployee(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserDesignDepartmentEmployee(projectId, currentUser.getId());
    }

    /**
     * 当前登录用户是否是设计部项目经理
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserDesignDepartmentManager(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserDesignDepartmentManager(projectId, currentUser.getId());
    }

    /**
     * 当前登录用户是否是工程部项目员工
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserEngineeringDepartmentEmployee(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserEngineeringDepartmentEmployee(projectId, currentUser.getId());
    }

    /**
     * 当前登录用户是否是工程部项目经理
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserEngineeringDepartmentManager(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserEngineeringDepartmentManager(projectId, currentUser.getId());
    }

    /**
     * 当前登录用户是否是总包单位员工
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserGeneralContractorCompanyEmployee(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserGeneralContractorCompanyEmployee(projectId, currentUser.getId());
    }

    /**
     * 当前登录用户是否是监理单位员工
     *
     * @param projectId 项目Id
     * @return
     */
    @Override
    public boolean isCurrentLoginUserSupervisionCompanyEmployee(String projectId) {
        if (projectId == null)
            return false;
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null)
            return false;
        return isUserSupervisionCompanyEmployee(projectId, currentUser.getId());
    }

    /**
     * 判断用户是否是设计部。
     *
     * @param userId 用户id
     * @return 用户在设计部返回true，否则返回false
     */
    @Override
    public boolean isUserInDesignDepartment(String userId) {
        if (userId == null)
            return false;
        return companyUserService.isDesignDepartmentByUserId(userId);
    }


    /**
     * 判断用户是否是工程部。
     *
     * @param userId 用户id
     * @return 用户在工程部返回true，否则返回false
     */
    @Override
    public boolean isUserInEngineeringDepartment(String userId) {
        if (userId == null)
            return false;
        return companyUserService.isEngineeringDepartmentByUserId(userId);
    }


    /**
     * 判断用户是否是设计单位员工。
     *
     * @param projectId 项目Id
     * @param userId    用户ID
     * @return true表示是，false表示不是
     */
    @Override
    public boolean isUserDesignCompanyEmployee(String projectId,
                                               String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是设计单位员工
        boolean companyRight = companyUserService.isDesignCompanyByUserId(userId);
        if (!companyRight)
            return false;


        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isEmployee() || role.isManager();
        return false;
    }

    @Override
    public boolean isUserDesignDepartmentEmployee(String projectId,
                                                  String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是设计单位员工
        boolean departmentRight = companyUserService.isDesignDepartmentByUserId(userId);
        if (!departmentRight)
            return false;

        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isEmployee();
        return false;
    }

    @Override
    public boolean isUserDesignDepartmentManager(String projectId,
                                                 String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是设计单位员工
        boolean departmentRight = companyUserService.isDesignDepartmentByUserId(userId);
        if (!departmentRight)
            return false;

        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isManager();
        return false;
    }

    @Override
    public boolean isUserEngineeringDepartmentEmployee(String projectId,
                                                       String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是工程部
        boolean departmentRight = companyUserService.isEngineeringDepartmentByUserId(userId);
        if (!departmentRight)
            return false;

        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isEmployee();
        return false;
    }

    @Override
    public boolean isUserEngineeringDepartmentManager(String projectId,
                                                      String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是工程部
        boolean departmentRight = companyUserService.isEngineeringDepartmentByUserId(userId);
        if (!departmentRight)
            return false;

        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isManager();
        return false;
    }

    @Override
    public boolean isUserGeneralContractorCompanyEmployee(String projectId,
                                                          String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是总包单位员工
        boolean companyRight = companyUserService.isGeneralContractorCompanyByUserId(userId);
        if (!companyRight)
            return false;

        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isEmployee() || role.isManager();
        return false;
    }

    @Override
    public boolean isUserSupervisionCompanyEmployee(String projectId,
                                                    String userId) {
        if (projectId == null || userId == null)
            return false;

        //判断是不是监理单位员工
        boolean companyRight = companyUserService.isSupervisionCompanyByUserId(userId);
        if (!companyRight)
            return false;

        Role role = getRoleByUserIdAndProjectId(userId, projectId);
        if (role != null)
            return role.isEmployee() || role.isManager();
        return false;
    }

    @Override
    public boolean getExistsDesignDepartmentManager(String projectId) {
        ProjectUser projectUser = getManagerOfDesignDepartment(projectId);
        return projectUser != null;
    }

    @Override
    public boolean getExistsEngineeringDepartmentManager(String projectId) {
        ProjectUser projectUser = getManagerOfEngineeringDepartment(projectId);
        return projectUser != null;
    }

    /**
     * 根据userId和projectId得到Role,获得某个用户在某个项目中的角色
     *
     * @param userId
     * @param projectId
     * @return
     */
    @Override
    public Role getRoleByUserIdAndProjectId(String userId,
                                            String projectId) {
        ProjectUser projectUser = projectUserRepository.getByUserIdAndProjectId(userId, projectId);
        if (projectUser != null)
            return roleService.getById(projectUser.getRoleId());
        return null;
    }

    /**
     * 根据id得到ProjectUser
     *
     * @param id
     */
    @Override
    public ProjectUser getById(String id) {
        return projectUserRepository.getById(id);
    }

    @Override
    public ProjectAllUserView getProjectAllUserViewByProjectId(String projectId) {
        Project project = projectService.getById(projectId);

        return getProjectAllUserViewByProject(project);
    }

    /**
     * 得到指定项目、指定单位的项目经理
     *
     * @param projectId
     * @return
     */
    @Override
    public ProjectUser getManager(String projectId,
                                  String companyId) {
        Role role = roleService.getManager();

        List<ProjectUser> projectUserList = projectUserRepository.getProjectUserListByProjectIdAndCompanyIdAndRoleId(
                projectId, companyId, role.getId());
        if (projectUserList != null && !projectUserList.isEmpty()) return projectUserList.getFirst();
        return null;
    }

    @Override
    public ProjectUser getManagerOfEngineeringDepartment(String projectId) {
        String companyId = companyService.getByName("工程部").getId();
        return getManager(projectId, companyId);
    }

    @Override
    public List<ProjectUser> getEmployeeOfEngineeringDepartment(String projectId) {
        String companyId = companyService.getByName("工程部").getId();
        return getEmployee(projectId, companyId);
    }

    @Override
    public List<ProjectUser> getManagerAndEmployeeOfEngineeringDepartment(String projectId) {
        String companyId = companyService.getByName("工程部").getId();
        return projectUserRepository.getProjectUserListByProjectIdAndCompanyId(projectId, companyId);
    }

    @Override
    public List<User> getEmployeeUserOfEngineeringDepartment(String projectId) {
        List<ProjectUser> projectUserList = getEmployeeOfEngineeringDepartment(projectId);
        if (projectUserList != null) {
            return projectUserList.stream().map(projectUser -> userService.getById(projectUser.getUserId())).collect(
                    Collectors.toList());
        }
        return null;
    }

    @Override
    public ProjectUser getManagerOfDesignDepartment(String projectId) {
        String companyId = companyService.getByName("设计部").getId();
        return getManager(projectId, companyId);
    }

    @Override
    public List<ProjectUser> getEmployeeOfDesignDepartment(String projectId) {
        String companyId = companyService.getByName("设计部").getId();
        return getEmployee(projectId, companyId);

    }

    @Override
    public List<ProjectUser> getManagerAndEmployeeOfDesignDepartment(String projectId) {
        String companyId = companyService.getByName("设计部").getId();
        return projectUserRepository.getProjectUserListByProjectIdAndCompanyId(projectId, companyId);
    }

    @Override
    public List<User> getEmployeeUserOfDesignDepartment(String projectId) {
        List<ProjectUser> projectUserList = getEmployeeOfDesignDepartment(projectId);
        if (projectUserList != null) {
            return projectUserList.stream().map(projectUser -> userService.getById(projectUser.getUserId())).collect(
                    Collectors.toList());
        }
        return null;
    }

    /**
     * 得到项目员工
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectUser> getEmployee(String projectId) {
        Role role = roleService.getEmployee();
        return projectUserRepository.getByRoleIdAndProjectId(role.getId(), projectId);
    }

    /**
     * 得到设计单位员工（包括项目经理和项目员工）
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectUser> getDesignCompanyEmployees(String projectId) {
        List<ProjectUser> projectUserList = projectUserRepository.getByProjectId(projectId);
        List<ProjectUser> projectUserArrayList = new ArrayList<>();
        if (projectUserList != null) {
            for (ProjectUser projectUser : projectUserList) {
                if (companyUserService.isDesignCompanyByUserId(projectUser.getUserId()))
                    projectUserArrayList.add(projectUser);
            }
        }

        return projectUserArrayList;
    }

    /**
     * 得到项目中总包单位的所有员工（包括项目经理和项目员工）
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectUser> getGeneralContractorCompanyEmployees(String projectId) {

        List<ProjectUser> projectUserList = projectUserRepository.getByProjectId(projectId);
        List<ProjectUser> projectUserArrayList = new ArrayList<>();//总包单位员工列表，包括项目经理和项目员工
        if (projectUserList != null) {
            for (ProjectUser projectUser : projectUserList) {
                if (companyUserService.isGeneralContractorCompanyByUserId(projectUser.getUserId()))
                    projectUserArrayList.add(projectUser);
            }
        }

        return projectUserArrayList;
    }

    /**
     * 得到项目中监理单位的所有员工（包括项目经理和项目员工）
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectUser> getSupervisionCompanyEmployees(String projectId) {
        List<ProjectUser> projectUserList = projectUserRepository.getByProjectId(projectId);
        List<ProjectUser> projectUserArrayList = new ArrayList<>();//监理单位员工列表，包括项目经理和项目员工
        if (projectUserList != null) {
            for (ProjectUser projectUser : projectUserList) {
                if (companyUserService.isSupervisionCompanyByUserId(projectUser.getUserId()))
                    projectUserArrayList.add(projectUser);
            }
        }

        return projectUserArrayList;
    }

    /**
     * 得到某个用户在某个项目中的ProjectUser。
     * 注意，一个用户在一个项目中只能有一个身份，要么是项目经理、要么是项目员工。
     * 一个用户在某个项目中，不能同时为项目经理和项目员工
     *
     * @param userId
     * @param projectId
     * @return
     */
    @Override
    public ProjectUser getByUserIdAndProjectId(String userId,
                                               String projectId) {
        return projectUserRepository.getByUserIdAndProjectId(userId, projectId);
    }

    /**
     * 根据roleId和projectId得到ProjectUser，即某个角色在某个项目中的所有用户。例如项目员工在某个项目中的所有用户
     *
     * @param roleId
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectUser> getByRoleIdAndProjectId(String roleId,
                                                     String projectId) {
        return projectUserRepository.getByRoleIdAndProjectId(roleId, projectId);
    }

    /**
     * 根据userId和roleId得到ProjectUser，即得到某个用户以某个角色参与的项目，例如张三以项目经理身份参与了哪些项目
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public List<ProjectUser> getByUserIdIdAndRoleId(String userId,
                                                    String roleId) {
        return projectUserRepository.getByUserIdIdAndRoleId(userId, roleId);
    }

    /**
     * 得到指定的项目的所有公司
     *
     * @param projectId
     * @return
     */
    @Override
    public List<Company> getCompanyByProjectId(String projectId) {
        return projectUserRepository.getCompanyListByProjectId(projectId);
    }

    /**
     * 根据userId得到ProjectUser
     *
     * @param userId
     */
    @Override
    public List<ProjectUser> getByUserId(String userId) {
        return projectUserRepository.getByUserId(userId);
    }

    /**
     * 根据projectId得到ProjectUser
     *
     * @param projectId
     */
    @Override
    public List<ProjectUser> getByProjectId(String projectId) {
        return projectUserRepository.getByProjectId(projectId);
    }

    /**
     * 根据roleId得到ProjectUser
     *
     * @param roleId
     */
    @Override
    public List<ProjectUser> getByRoleId(String roleId) {
        return projectUserRepository.getByRoleId(roleId);
    }

    /**
     * 得到指定项目项目经理
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectUser> getManagers(String projectId) {
        Role role = roleService.getManager();
        return projectUserRepository.getByRoleIdAndProjectId(role.getId(), projectId);

    }

    /**
     * 得到指定项目、指定公司的员工
     *
     * @param projectId
     * @param companyId
     * @return
     */
    @Override
    public List<ProjectUser> getEmployee(String projectId,
                                         String companyId) {
        Role role = roleService.getEmployee();

        return projectUserRepository.getProjectUserListByProjectIdAndCompanyIdAndRoleId(projectId, companyId,
                                                                                        role.getId());
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUser> getPage(int pageNo,
                                     int pageSize) {
        return projectUserRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUser> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        return projectUserRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectUser> getPageByProjectId(String projectId,
                                                int pageNo,
                                                int pageSize) {
        return projectUserRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param roleId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUser> getPageByRoleId(String roleId,
                                             int pageNo,
                                             int pageSize) {
        return projectUserRepository.getPageByRoleId(roleId, pageNo, pageSize);
    }

    @Override
    public ProjectUserView getDesignDepartmentManager(String projectId) {
        ProjectUser projectUser = getManagerOfDesignDepartment(projectId);
        return getProjectUserViewByProjectUserId(projectUser.getId());
    }

    @Override
    public ProjectUserView getEngineeringDepartmentManager(String projectId) {
        ProjectUser projectUser = getManagerOfEngineeringDepartment(projectId);
        return getProjectUserViewByProjectUserId(projectUser.getId());
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUserView> getPageView(int pageNo,
                                             int pageSize) {
        Page<ProjectUser> projectUserPage = getPage(pageNo, pageSize);
        return convertProjectUserPage2PageView(projectUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUserView> getPageViewByUserId(String userId,
                                                     int pageNo,
                                                     int pageSize) {
        Page<ProjectUser> projectUserPage = getPageByUserId(userId, pageNo, pageSize);
        return convertProjectUserPage2PageView(projectUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectUserView> getPageViewByProjectId(String projectId,
                                                        int pageNo,
                                                        int pageSize) {
        Page<ProjectUser> projectUserPage = getPageByProjectId(projectId, pageNo, pageSize);
        return convertProjectUserPage2PageView(projectUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param roleId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUserView> getPageViewByRoleId(String roleId,
                                                     int pageNo,
                                                     int pageSize) {
        Page<ProjectUser> projectUserPage = getPageByRoleId(roleId, pageNo, pageSize);
        return convertProjectUserPage2PageView(projectUserPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectAllUserView> getPageAllUserView(Integer pageNo,
                                                       Integer pageSize) {
        Page<Project> projectPage = projectService.getPage(pageNo, pageSize);
        if (projectPage != null) {
            int startIndex = Page.getStartOfPage(pageNo, pageSize);
            List<ProjectAllUserView> list = new ArrayList<>();
            for (Project project : projectPage.getResult()) {
                ProjectAllUserView projectAllUserView = getProjectAllUserViewByProject(project);
                list.add(projectAllUserView);
            }
            return new Page<>(startIndex, projectPage.getTotalCount(), pageSize, list);
        }
        return null;
    }

    @Override
    public Page<ProjectAllUserView> getPageAllUserViewByProjectName(String projectName,
                                                                    Integer pageNo,
                                                                    Integer pageSize) {
        Page<Project> projectPage = projectService.getPageByProjectName(projectName, pageNo, pageSize);
        if (projectPage != null) {
            int startIndex = Page.getStartOfPage(pageNo, pageSize);
            List<ProjectAllUserView> list = new ArrayList<>();
            for (Project project : projectPage.getResult()) {
                ProjectAllUserView projectAllUserView = getProjectAllUserViewByProject(project);
                list.add(projectAllUserView);
            }
            return new Page<>(startIndex, projectPage.getTotalCount(), pageSize, list);
        }
        return null;
    }

    @Override
    public Page<ProjectAllUserView> getPageAllUserViewByCompanyName(String companyName,
                                                                    Integer pageNo,
                                                                    Integer pageSize) {
        return null;
    }

    @Override
    public Page<ProjectAllUserView> getPageAllUserViewByUserName(String userName,
                                                                 Integer pageNo,
                                                                 Integer pageSize) {
        User user = userService.getByUserName(userName);
        if (user == null) return null;

        Page<ProjectUser> projectUserPage = projectUserRepository.getPageByUserId(user.getId(), pageNo, pageSize);

        if (projectUserPage != null) {
            int startIndex = Page.getStartOfPage(pageNo, pageSize);
            List<ProjectAllUserView> list = new ArrayList<>();
            for (ProjectUser projectUser : projectUserPage.getResult()) {
                ProjectAllUserView projectAllUserView = getProjectAllUserViewByProject(
                        projectService.getById(projectUser.getProjectId()));
                list.add(projectAllUserView);
            }
            return new Page<>(startIndex, projectUserPage.getTotalCount(), pageSize, list);
        }
        return null;
    }
 @Override
    public Page<ProjectAllUserView> getPageAllUserViewByRealName(String realName,
                                                                 Integer pageNo,
                                                                 Integer pageSize) {
        User user = userService.getByRealName(realName);
        if (user == null) return null;

        Page<ProjectUser> projectUserPage = projectUserRepository.getPageByUserId(user.getId(), pageNo, pageSize);

        if (projectUserPage != null) {
            int startIndex = Page.getStartOfPage(pageNo, pageSize);
            List<ProjectAllUserView> list = new ArrayList<>();
            for (ProjectUser projectUser : projectUserPage.getResult()) {
                ProjectAllUserView projectAllUserView = getProjectAllUserViewByProject(
                        projectService.getById(projectUser.getProjectId()));
                list.add(projectAllUserView);
            }
            return new Page<>(startIndex, projectUserPage.getTotalCount(), pageSize, list);
        }
        return null;
    }

    @Override
    public Page<CompanyUserView> convert(Page<CompanyUserView> companyUserViewPage,
                                         Integer pageNo,
                                         Integer pageSize) {
        if (companyUserViewPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUserView> list = new ArrayList<>();
        for (CompanyUserView companyUserView : companyUserViewPage.getResult()) {
            List<ProjectUser> projectUserList = projectUserRepository.getByUserId(
                    companyUserView.getCompanyUser().getUserId());
            if (projectUserList != null && !projectUserList.isEmpty()) {
                List<ProjectUserView> projectUserViewList = new ArrayList<>();
                for (ProjectUser projectUser : projectUserList) {
                    ProjectUserView projectUserView = getProjectUserViewByProjectUserId(projectUser.getId());
                    projectUserViewList.add(projectUserView);
                }
                companyUserView.setProjectUserViewList(projectUserViewList);
            }
        }
        return companyUserViewPage;
        //return new Page<>(startIndex, projectUserPage.getTotalCount(), pageSize, list);
    }

    /**
     * 得到工程部项目经理人选(即去除掉项目员工后的人员)
     * @param projectId 项目号
     * @param pageNo 页号
     * @param pageSize 每页的记录数
     * @return
     */
    @Override
    public Page<CompanyUserView> getEngineeringDepartmentManagerCandidatesViewPageByProjectId(String projectId,
                                                                                              Integer pageNo,
                                                                                              Integer pageSize) {

        //如果已经存在项目经理，则说明不需要再次选择
        ProjectUser projectUserManager=getManagerOfEngineeringDepartment(projectId);
        if(projectUserManager!=null)
            return new Page<>(0,0,0,null);

        //1.得到项目的所有项目员工
        List<ProjectUser> projectUserList = getEmployeeOfEngineeringDepartment(projectId);

        List<String> userIdList = new ArrayList<>();
        if(projectUserList!=null && !projectUserList.isEmpty())
        for (ProjectUser projectUser : projectUserList) {
            userIdList.add(projectUser.getUserId());
        }


        //2.得到去除项目员工后的人员页面
        Company company = companyService.getEngineeringDepartment();
        return companyUserService.getPageViewExcludingByCompanyId(company.getId(),userIdList,pageNo, pageSize);


    }

    /**
     * 得到设计部部项目经理人选(即去除掉项目员工后的人员)
     * @param projectId 项目号
     * @param pageNo 页号
     * @param pageSize 每页的记录数
     * @return
     */
    @Override
    public Page<CompanyUserView> getDesignDepartmentManagerCandidatesViewPageByProjectId(String projectId,
                                                                                         Integer pageNo,
                                                                                         Integer pageSize) {

        //如果已经存在项目经理，则说明不需要再次选择
        ProjectUser projectUserManager=getManagerOfDesignDepartment(projectId);
        if(projectUserManager!=null)
            return new Page<>(0,0,0,null);

        //1.得到项目的所有项目员工
        List<ProjectUser> projectUserList = getEmployeeOfDesignDepartment(projectId);

        List<String> userIdList = new ArrayList<>();
        if(projectUserList!=null && !projectUserList.isEmpty())
        for (ProjectUser projectUser : projectUserList) {
            userIdList.add(projectUser.getUserId());
        }


        //2.得到去除项目员工后的人员页面
        Company company = companyService.getDesignDepartment();
        return companyUserService.getPageViewExcludingByCompanyId(company.getId(),userIdList,pageNo, pageSize);

    }

    /**
     * 得到设计部项目员工人选(即去除掉项目经理后的人员)
     * @param projectId 项目号
     * @param pageNo 页号
     * @param pageSize 每页的记录数
     * @return
     */
    @Override
    public Page<CompanyUserView> getDesignDepartmentEmployeeCandidatesViewPageByProjectId(String projectId,
                                                                                          Integer pageNo,
                                                                                          Integer pageSize) {

        Company company = companyService.getDesignDepartment();
        List<String> userIdList = new ArrayList<>();
        //1.得到项目的所有项目员工
        ProjectUser projectUser= getManagerOfDesignDepartment(projectId);
        if(projectUser!=null)
            userIdList.add(projectUser.getUserId());


        //得到项目员工
        List<ProjectUser>  projectUserList=getEmployeeOfDesignDepartment(projectId);
        if(projectUserList!=null && !projectUserList.isEmpty())
            for (ProjectUser projectUserTemp : projectUserList) {
                userIdList.add(projectUserTemp.getUserId());
            }

        //2.得到去除项目员工后的人员页面
        if(userIdList!=null &&!userIdList.isEmpty())
            return companyUserService.getPageViewExcludingByCompanyId(company.getId(), userIdList, pageNo, pageSize);

        else return companyUserService.getPageViewByCompanyId(company.getId(), pageNo, pageSize);

    }

    /**
     * 得到工程部项目员工人选(即去除掉项目经理后的人员)
     * @param projectId 项目号
     * @param pageNo 页号
     * @param pageSize 每页的记录数
     * @return
     */
    @Override
    public Page<CompanyUserView> getEngineeringDepartmentEmployeeCandidatesViewPageByProjectId(String projectId,
                                                                                               Integer pageNo,
                                                                                               Integer pageSize) {




        Company company = companyService.getEngineeringDepartment();

        List<String> userIdList = new ArrayList<>();
        //1.得到项目经理
        ProjectUser projectUser= getManagerOfEngineeringDepartment(projectId);
        if(projectUser!=null) {
            userIdList.add(projectUser.getUserId());
        }

        //得到项目员工
        List<ProjectUser>  projectUserList=getEmployeeOfEngineeringDepartment(projectId);
        if(projectUserList!=null && !projectUserList.isEmpty())
        for (ProjectUser projectUserTemp : projectUserList) {
            userIdList.add(projectUserTemp.getUserId());
        }




            //2.得到去除项目员工后的人员页面
        if(userIdList!=null &&!userIdList.isEmpty())
            return companyUserService.getPageViewExcludingByCompanyId(company.getId(), userIdList, pageNo, pageSize);

        else return companyUserService.getPageViewByCompanyId(company.getId(),pageNo,pageSize);

    }

    private ProjectAllUserView getProjectAllUserViewByProject(Project project) {
        ProjectAllUserView projectAllUserView = new ProjectAllUserView();
        projectAllUserView.setProjectView(getProjectViewByProjectId(project.getId()));

        List<ProjectUser> projectUserList = projectUserRepository.getByProjectId(project.getId());
        if (projectUserList != null) {


            List<ProjectUserView> projectUserViewListDesignCompany = new ArrayList<>();//设计单位员工列表，包括项目经理和项目员工
            List<ProjectUserView> projectUserViewListDesignDepartment = new ArrayList<>();//设计部员工列表，包括项目经理和项目员工
            List<ProjectUserView> projectUserViewListEngineeringDepartment = new ArrayList<>();//工程部员工列表，包括项目经理和项目员工
            List<ProjectUserView> projectUserViewListSupervisionCompany = new ArrayList<>();//监理单位员工列表，包括项目经理和项目员工
            List<ProjectUserView> projectUserViewListGeneralContractorCompany = new ArrayList<>();//总包单位员工列表，包括项目经理和项目员工

            for (ProjectUser projectUser : projectUserList) {
                ProjectUserView projectUserView = getProjectUserViewByProjectUserId(projectUser.getId());
                if (projectUserView != null) {
                    if (companyUserService.isDesignDepartmentByUserId(projectUserView.getUser().getId()))
                        projectUserViewListDesignDepartment.add(projectUserView);
                    else if (companyUserService.isEngineeringDepartmentByUserId(projectUserView.getUser().getId()))
                        projectUserViewListEngineeringDepartment.add(projectUserView);
                    else if (companyUserService.isDesignCompanyByUserId(projectUserView.getUser().getId()))
                        projectUserViewListDesignCompany.add(projectUserView);
                    else if (companyUserService.isSupervisionCompanyByUserId(projectUserView.getUser().getId()))
                        projectUserViewListSupervisionCompany.add(projectUserView);
                    else if (companyUserService.isGeneralContractorCompanyByUserId(projectUserView.getUser().getId()))
                        projectUserViewListGeneralContractorCompany.add(projectUserView);
                }
            }
            projectAllUserView.setProjectUserViewListConstructionCompany(
                    projectUserViewListGeneralContractorCompany);//总包单位


            projectAllUserView.setProjectUserViewListDesignDepartment(projectUserViewListDesignDepartment);//设计部
            projectAllUserView.setProjectUserViewListEngineeringDepartment(
                    projectUserViewListEngineeringDepartment);//工程部
            projectAllUserView.setProjectUserViewListSupervisionCompany(projectUserViewListSupervisionCompany);//监理

        }

        //获得设计公司
        List<ProjectCompanyAllUserView> projectUserViewListDesignCompany = new ArrayList<>();

        List<ProjectDesignCompany> projectDesignCompanyList = projectDesignCompanyService.getByProjectId(
                project.getId());
        if (projectDesignCompanyList != null && !projectDesignCompanyList.isEmpty()) {
            projectDesignCompanyList.forEach(projectDesignCompany -> {
                List<ProjectUser> projectUserListTemp = projectUserRepository.getProjectUserListByProjectIdAndCompanyId(
                        project.getId(),
                        projectDesignCompany.getDesignCompanyId());
                ProjectCompanyAllUserView projectCompanyAllUserView = new ProjectCompanyAllUserView();
                projectCompanyAllUserView.setCompany(companyService.getById(projectDesignCompany.getDesignCompanyId()));
                if (projectUserListTemp != null && !projectUserListTemp.isEmpty()) {
                    List<ProjectUserView> projectUserViewList = new ArrayList<>();
                    for (ProjectUser projectUser : projectUserListTemp) {
                        ProjectUserView projectUserView = getProjectUserViewByProjectUserId(projectUser.getId());
                        if (projectUserView != null)
                            projectUserViewList.add(projectUserView);
                    }
                    projectCompanyAllUserView.setProjectUserViewList(projectUserViewList);
                }
                projectUserViewListDesignCompany.add(projectCompanyAllUserView);

            });
        }

        projectAllUserView.setProjectUserViewListDesignCompany(projectUserViewListDesignCompany);//设计单位

        return projectAllUserView;

    }


    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectUserView getProjectUserViewByProjectUserId(String id) {
        ProjectUser projectUser = getById(id);
        if (projectUser == null) return null;
        ProjectUserView projectUserView = new ProjectUserView();
        projectUserView.setProject(projectService.getById(projectUser.getProjectId()));
        projectUserView.setProjectUser(projectUser);
        projectUserView.setRole(roleService.getById(projectUser.getRoleId()));
        projectUserView.setUser(userService.getById(projectUser.getUserId()));
        return projectUserView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectUserPage 页面对象
     */
    private Page<ProjectUserView> convertProjectUserPage2PageView(Page<ProjectUser> projectUserPage,
                                                                  int pageNo,
                                                                  int pageSize) {
        if (projectUserPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectUserView> list = new ArrayList<>();
        for (ProjectUser projectUser : projectUserPage.getResult()) {
            ProjectUserView projectUserView = getProjectUserViewByProjectUserId(projectUser.getId());
            if (projectUserView != null) list.add(projectUserView);
        }
        return new Page<>(startIndex, projectUserPage.getTotalCount(), pageSize, list);
    }

}