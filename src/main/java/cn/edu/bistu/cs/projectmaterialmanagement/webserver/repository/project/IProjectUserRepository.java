package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;

import java.util.List;

/**
 * ProjectUser Service Interface
 */
public interface IProjectUserRepository {

    String add(ProjectUser projectUser);

    int delete(ProjectUser projectUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectId(String projectId);

    int deleteByRoleId(String roleId);

    int update(ProjectUser projectUser);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectId(String projectId);

    int getCountByRoleId(String roleId);

    int getCountByUserIdAndProjectIdAndRoleId(String userId, String projectId, String roleId);


    ProjectUser getById(String id);

    ProjectUser getByUserIdAndProjectId(String userId, String projectId);

    List<ProjectUser> getByRoleIdAndProjectId(String roleId, String projectId);

    List<ProjectUser> getByUserIdIdAndRoleId(String userId, String roleId);


    List<Company> getCompanyListByProjectId(String projectId);

    List<ProjectUser> getByUserId(String userId);

    List<ProjectUser> getByProjectId(String projectId);

    List<ProjectUser> getByRoleId(String roleId);

    List<ProjectUser> getProjectUserListByProjectIdAndCompanyId(String projectId, String companyId);

    List<ProjectUser> getProjectUserListByProjectIdAndCompanyIdAndRoleId(String projectId, String companyId, String roleId);

    Page<ProjectUser> getPage(int pageNo, int pageSize);

    Page<ProjectUser> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectUser> getPageByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectUser> getPageByRoleId(String roleId, int pageNo, int pageSize);


}