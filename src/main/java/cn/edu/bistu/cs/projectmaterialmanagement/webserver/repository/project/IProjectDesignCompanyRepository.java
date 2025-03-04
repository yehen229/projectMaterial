package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;

import java.util.List;

/**
 * Project Service Interface
 */
public interface IProjectDesignCompanyRepository {

    String add(ProjectDesignCompany projectDesignCompany);

    int delete(ProjectDesignCompany projectDesignCompany);

    int deleteById(String id);

    int deleteByProjectId(String projectId);


    int deleteByCompanyDesignId(String companyDesignId);

    int update(ProjectDesignCompany projectDesignCompany);

    int getCount();

    int getCountByProjectId(String projectId);


    int getCountByCompanyDesignId(String companyDesignId);

    ProjectDesignCompany getById(String id);

    ProjectDesignCompany getByProjectIdAndDesignCompanyId(String projectId,
                                                          String companyId);

    List<ProjectDesignCompany> getByProjectId(String projectId);


    List<ProjectDesignCompany> getByCompanyDesignId(String companyDesignId);

    Page<ProjectDesignCompany> getPage(int pageNo,
                                       int pageSize);


    Page<ProjectDesignCompany> getPageByCompanyDesignId(String companyDesignId,
                                                        int pageNo,
                                                        int pageSize);

    // 项目编号、项目名称、关键词
    Page<ProjectDesignCompany> getPageByProjectId(String projectId,
                                                  int pageNo,
                                                  int pageSize);


}