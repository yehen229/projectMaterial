package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFile;

import java.util.List;

/**
 * ProjectMaterialAcceptanceReviewUserFile Service Interface
 */
public interface IProjectMaterialAcceptanceReviewUserFileRepository {

    String add(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile);

    int delete(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile);

    int update(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile);

    int deleteById(String id);

    int deleteByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);

    int getCount();

    int getCountByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);

    ProjectMaterialAcceptanceReviewUserFile getById(String id);

    List<ProjectMaterialAcceptanceReviewUserFile> getByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);

    Page<ProjectMaterialAcceptanceReviewUserFile> getPage(int pageNo,
                                                          int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserFile> getPageByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                 int pageNo,
                                                                                                 int pageSize);

}