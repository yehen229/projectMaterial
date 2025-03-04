package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivate;

import java.util.List;

/**
 * ProjectMaterialBrandPrivate Service Interface
 */
public interface IProjectMaterialBrandPrivateRepository {

    String add(ProjectMaterialBrandPrivate projectMaterialBrandPrivate);

    int delete(ProjectMaterialBrandPrivate projectMaterialBrandPrivate);

    int update(ProjectMaterialBrandPrivate projectMaterialBrandPrivate);

    int deleteById(String id);

    int deleteByProjectMaterialId(String projectMaterialId);

    int deleteByProjectBrandId(String projectBrandId);

    int getCount();

    int getCountByProjectMaterialId(String projectMaterialId);

    int getCountByProjectBrandId(String projectBrandId);

    ProjectMaterialBrandPrivate getById(String id);

    ProjectMaterialBrandPrivate getByProjectMaterialIdAndProjectBrandId(String projectMaterialId,
                                                                        String projectBrandId);

    List<ProjectMaterialBrandPrivate> getByProjectMaterialId(String projectMaterialId);

    List<ProjectMaterialBrandPrivate> getByProjectIdAndMaterialId(String projectId,String materialId       );

    List<ProjectMaterialBrandPrivate> getByProjectBrandId(String projectBrandId);

    Page<ProjectMaterialBrandPrivate> getPage(int pageNo,
                                              int pageSize);

    Page<ProjectMaterialBrandPrivate> getPageByProjectMaterialId(String projectMaterialId,
                                                                 int pageNo,
                                                                 int pageSize);

    Page<ProjectMaterialBrandPrivate> getPageByProjectBrandId(String projectBrandId,
                                                              int pageNo,
                                                              int pageSize);




}