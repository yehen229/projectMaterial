package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Project Service Interface
 */
public interface IProjectRepository {

    String add(Project project);

    int delete(Project project);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByCompanyConstructionId(String companyConstructionId);

//    int deleteByCompanyDesignId(String companyDesignId);

    int update(Project project);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByCompanyConstructionId(String companyConstructionId);

    int getCountNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId);

//    int getCountByCompanyDesignId(String companyDesignId);

    Project getById(String id);

    List<Project> getByUserId(String userId);

    List<Project> getByCompanyConstructionId(String companyConstructionId);

//    List<Project> getByCompanyDesignId(String companyDesignId);

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

    // 项目编号、项目名称、关键词
    Page<Project> getPageByProjectId(String projectId,
                                     int pageNo,
                                     int pageSize);

    Page<Project> getPageByProjectName(String projectName,
                                       int pageNo,
                                       int pageSize);

    Page<Project> getPageByProjectLocation(String projectLocation, int pageNo, int pageSize);

    Page<Project> getPageByParams(String name,
                                  String location,
                                  BigDecimal totalTaxIncluded,
                                  BigDecimal totalTaxNotIncluded,
                                  BigDecimal buildingAreaAboveGround,
                                  BigDecimal buildingAreaUnderGround,
                                  String companyConstructionId,
                                  String companyDesignId,
                                  String note,
                                  Date createDatetime,
                                  Date endDatetime,
                                  int pageNo,
                                  int pageSize);

    Page<Project> getPageByKeyword(String keyword,
                                   int pageNo,
                                   int pageSize);

    Page<Project> getNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId,
                                                                   Integer pageNo,
                                                                   Integer pageSize);
//    Page<Project> getPageByAdvancedSearch(String name ,String location, int pageNo, int pageSize);

    Page<Project> getPageByAdvancedSearch(String name, String location, double withTaxMin, double withTaxMax, double outTaxMin, double outTaxMax, double areaAboveGroundMin, double areaAboveGroundMax, double areaUnderGroundMin, double areaUnderGroundMax, String companyConstructionName, String companyDesignName, Date startDatetime, Date endDatetime, Integer pageNo, Integer pageSize);

    List<Project> getPageQueryByAdvancedSearch(String name, String location, double withTaxMin, double withTaxMax, double outTaxMin, double outTaxMax, double areaAboveGroundMin, double areaAboveGroundMax, double areaUnderGroundMin, double areaUnderGroundMax, String companyConstructionName, String companyDesignName, Date startDatetime, Date endDatetime, Integer pageNo, Integer pageSize);

    List<Project> getAllList();
}