package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;

import java.util.List;

public interface IProjectService {
    String add(Project project);

    int delete(Project project);

    int update(Project project);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByCompanyConstructionId(String companyConstructionId);

    int deleteByCompanyDesignId(String companyDesignId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByCompanyConstructionId(String companyConstructionId);

    int getCountByCompanyDesignId(String companyDesignId);

    Project getById(String id);


    List<Project> getByUserId(String userId);

    List<Project> getByCompanyConstructionId(String companyConstructionId);

    List<Project> getByCompanyDesignId(String companyDesignId);

    Page<Project> getPage(int pageNo,
                          int pageSize);

    Page<Project> getPageByUserId(String userId,
                                  int pageNo,
                                  int pageSize);

    Page<Project> getPageByCompanyConstructionId(String companyConstructionId,
                                                 int pageNo,
                                                 int pageSize);

    Page<Project> getPageByCompanyDesignId(String companyDesignId,
                                           int pageNo,
                                           int pageSize);


    // 根据项目名称、项目编号、关键字查询
    Page<Project> getPageByProjectName(String projectName,
                                       int pageNo,
                                       int pageSize);

    Page<Project> getPageByProjectId(String projectId,
                                     int pageNo,
                                     int pageSize);

    Page<Project> getPageByKeyword(String keyword,
                                   int pageNo,
                                   int pageSize);


    Page<Project> getNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId,
                                                                   Integer pageNo,
                                                                   Integer pageSize);
}