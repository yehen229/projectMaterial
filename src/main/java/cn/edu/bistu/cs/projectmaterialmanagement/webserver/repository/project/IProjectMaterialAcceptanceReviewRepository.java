package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReview;

import java.util.List;

/**
 * ProjectMaterialAcceptanceReview Service Interface
 */
public interface IProjectMaterialAcceptanceReviewRepository {

    String add(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview);

    int delete(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview);

    int update(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview);

    int deleteById(String id);

    int deleteByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId);

    int getCount();

    int getCountByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId);

    ProjectMaterialAcceptanceReview getById(String id);

    List<ProjectMaterialAcceptanceReview> getByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId);

    Page<ProjectMaterialAcceptanceReview> getPage(int pageNo,
                                                  int pageSize);

    Page<ProjectMaterialAcceptanceReview> getPageByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                   int pageNo,
                                                                                   int pageSize);

}