package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;

import java.util.List;

/**
 * ProjectMaterialAcceptanceReviewUser Service Interface
 */
public interface IProjectMaterialAcceptanceReviewUserRepository {

    String add(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser);

    int delete(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser);

    int update(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId);

    int getCount();

    int getCountByUserId(String userId);
    int getCountByProjectId(String projectId);
    int getCountByProjectIdAndResult(String projectId, int nReviewResult);
    int getCountByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId);

    ProjectMaterialAcceptanceReviewUser getById(String id);

    ProjectMaterialAcceptanceReviewUser getByUserIdAndModeId(String userId,
                                                             String projectMaterialAcceptanceBatchId,
                                                             String projectMaterialAcceptanceReviewModeId);
    List<ProjectMaterialAcceptanceReviewUser> getByMaterialAcceptanceModeIdAndNotReviewed (String projectMaterialAcceptanceReviewModeId, int reviewResult);

    List<ProjectMaterialAcceptanceReviewUser> getByMaterialAcceptanceModeId(String projectAppearanceReviewModeId);

    List<ProjectMaterialAcceptanceReviewUser> getByUserId(String userId);

    List<ProjectMaterialAcceptanceReviewUser> getByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId);

    Page<ProjectMaterialAcceptanceReviewUser> getPage(int pageNo,
                                                      int pageSize);

    Page<ProjectMaterialAcceptanceReviewUser> getPageByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialAcceptanceReviewUser> getPageByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                         int pageNo,
                                                                                         int pageSize);
    Page<ProjectMaterialAcceptanceReviewUser> getViewPageByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId, Integer pageNo, Integer pageSize);

    List<ProjectMaterialAcceptanceReviewUser> getByAcceptanceModeId(String projectAcceptanceReviewModeId);
}