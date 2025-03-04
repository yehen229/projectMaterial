package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandView;

import java.util.List;

public interface IProjectBrandService {
    String add(ProjectBrand projectBrand);

    String addForm(ProjectBrandForm projectBrandForm);

    int delete(ProjectBrand projectBrand);

    int update(ProjectBrand projectBrand);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int deleteByBrandId(String brandId);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByBrandId(String brandId);

    ProjectBrand getById(String id);

    ProjectBrand getByProjectIdAndBrandId(String projectId,
                                          String brandId);

    ProjectBrandView getViewById(String id);

    List<ProjectBrand> getByProjectId(String projectId);

    List<ProjectBrand> getByBrandId(String brandId);

    Page<ProjectBrand> getPage(int pageNo,
                               int pageSize);

    Page<ProjectBrand> getPageByProjectId(String projectId,
                                          int pageNo,
                                          int pageSize);

    Page<ProjectBrand> getPageByBrandId(String brandId,
                                        int pageNo,
                                        int pageSize);

    Page<ProjectBrandView> getPageView(int pageNo,
                                       int pageSize);

    Page<ProjectBrandView> getPageViewByProjectId(String projectId,
                                                  int pageNo,
                                                  int pageSize);

    Page<ProjectBrandView> getPageViewByBrandId(String brandId,
                                                int pageNo,
                                                int pageSize);

    List<ProjectBrandView> getViewListByProjectId(String projectId);
}