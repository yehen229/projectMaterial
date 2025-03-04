package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetest;

import java.util.List;

/**
 * ProjectMaterialRetest Service Interface
 */
public interface IProjectMaterialRetestRepository {

    String add(ProjectMaterialRetest projectMaterialRetest);

    int delete(ProjectMaterialRetest projectMaterialRetest);

    int deleteById(String id);

    int deleteByBuyMaterialId(String buyMaterialId);

    int deleteByUserId(String userId);

    int update(ProjectMaterialRetest projectMaterialRetest);

    int getCount();

    int getCountByBuyMaterialId(String buyMaterialId);

    int getCountByUserId(String userId);

    ProjectMaterialRetest getById(String id);

    List<ProjectMaterialRetest> getByBuyMaterialId(String buyMaterialId);

    List<ProjectMaterialRetest> getByUserId(String userId);

    Page<ProjectMaterialRetest> getPage(int pageNo, int pageSize);

    Page<ProjectMaterialRetest> getPageByBuyMaterialId(String buyMaterialId, int pageNo, int pageSize);

    Page<ProjectMaterialRetest> getPageByUserId(String userId, int pageNo, int pageSize);

}