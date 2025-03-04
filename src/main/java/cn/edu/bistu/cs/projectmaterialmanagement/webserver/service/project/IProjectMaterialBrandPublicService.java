package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublicView;

import java.util.List;

public interface IProjectMaterialBrandPublicService {
    String add(ProjectMaterialBrandPublic projectMaterialBrandPublic);

    int delete(ProjectMaterialBrandPublic projectMaterialBrandPublic);

    int update(ProjectMaterialBrandPublic projectMaterialBrandPublic);

    int deleteById(String id);

    int deleteByProjectMaterialId(String projectMaterialId);

    int deleteByBrandPublicId(String brandPublicId);

    int getCount();

    int getCountByProjectMaterialId(String projectMaterialId);

    int getCountByBrandPublicId(String brandPublicId);
    int getCountByProjectMaterialIdAndBrandPublicId(String projectMaterialId,
                                                     String brandPublicId);

    ProjectMaterialBrandPublic getById(String id);

    ProjectMaterialBrandPublicView getViewById(String id);

    List<ProjectMaterialBrandPublic> getByProjectMaterialId(String projectMaterialId);

    List<ProjectMaterialBrandPublic> getByBrandPublicId(String brandPublicId);

    List<ProjectMaterialBrandPublicView> getViewListByProjectMaterialId(String projectMaterialId);


    Page<ProjectMaterialBrandPublic> getPage(int pageNo, int pageSize);

    Page<ProjectMaterialBrandPublic> getPageByProjectMaterialId(String projectMaterialId, int pageNo, int pageSize);

    Page<ProjectMaterialBrandPublic> getPageByBrandPublicId(String brandPublicId, int pageNo, int pageSize);

    Page<ProjectMaterialBrandPublicView> getPageView(int pageNo, int pageSize);

    Page<ProjectMaterialBrandPublicView> getPageViewByProjectMaterialId(String projectMaterialId, int pageNo, int pageSize);

    Page<ProjectMaterialBrandPublicView> getPageViewByBrandPublicId(String brandPublicId, int pageNo, int pageSize);


}