package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brandexcel;

import java.util.List;

/**
 * ProjectBrand Service Interface
 */
public interface IProjectBrandRepository {

    String add(ProjectBrand projectBrand);

    int delete(ProjectBrand projectBrand);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int deleteByBrandId(String brandId);

    int update(ProjectBrand projectBrand);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByProjectIdAndPosition(String projectId, String position);

    int getCountByProjectIdAndBrandName(String projectId, String brandName);

    int getCountByBrandId(String brandId);

    ProjectBrand getById(String id);

    ProjectBrand getByProjectIdAndBrandId(String projectId,
                                          String brandId);

    List<ProjectBrand> getByProjectId(String projectId);

    List<ProjectBrand> getByBrandId(String brandId);

    Page<ProjectBrand> getPage(int pageNo,
                               int pageSize);

    Page<ProjectBrand> getPageByProjectId(String projectId,
                                          int pageNo,
                                          int pageSize);
    Page<ProjectBrand> getPageByProjectIdAndPosition(String projectId,
                                          String position,
                                          int pageNo,
                                          int pageSize);
    Page<ProjectBrand> getPageByProjectIdAndBrandName(String projectId,
                                          String brandName,
                                          int pageNo,
                                          int pageSize);

    Page<ProjectBrand> getPageByBrandId(String brandId,
                                        int pageNo,
                                        int pageSize);

    List<Brandexcel> getExcelListByProjectId(String projectId);

}