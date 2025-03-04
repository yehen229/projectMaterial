package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivate;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivateView;

import java.util.List;

public interface IProjectMaterialBrandPrivateService {
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

    ProjectMaterialBrandPrivateView getViewById(String id);

    List<ProjectMaterialBrandPrivate> getByProjectMaterialId(String projectMaterialId);

    List<ProjectMaterialBrandPrivateView> getViewListByProjectMaterialId(String projectMaterialId);


    List<ProjectMaterialBrandPrivate> getByProjectBrandId(String projectBrandId);

    Page<ProjectMaterialBrandPrivate> getPage(int pageNo,
                                              int pageSize);

    Page<ProjectMaterialBrandPrivate> getPageByProjectMaterialId(String projectMaterialId,
                                                                 int pageNo,
                                                                 int pageSize);

    Page<ProjectMaterialBrandPrivate> getPageByProjectBrandId(String projectBrandId,
                                                              int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialBrandPrivateView> getPageView(int pageNo,
                                                      int pageSize);

    Page<ProjectMaterialBrandPrivateView> getPageViewByProjectMaterialId(String projectMaterialId,
                                                                         int pageNo,
                                                                         int pageSize);

    Page<ProjectMaterialBrandPrivateView> getPageViewByProjectBrandId(String projectBrandId,
                                                                      int pageNo,
                                                                      int pageSize);

}