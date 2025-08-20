package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserView;

import java.util.List;

public interface IProjectMaterialAcceptanceReviewUserService {
     int PROJECT_REVIEW_RESULT_UNKNOWN = 0;//未知
    int PROJECT_REVIEW_RESULT_REJECTED = 1;//未通过
    int PROJECT_REVIEW_RESULT_ACCEPTED = 2; //通过
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

    List<ProjectMaterialAcceptanceReviewUser> getByUserId(String userId);

    List<ProjectMaterialAcceptanceReviewUser> getByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId);
    List<ProjectMaterialAcceptanceReviewUser> getByMaterialAcceptanceModeIdAndNotReviewed(String projectMaterialAcceptanceReviewModeId);

    Page<ProjectMaterialAcceptanceReviewUser> getPage(int pageNo,
                                                      int pageSize);

    Page<ProjectMaterialAcceptanceReviewUser> getPageByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialAcceptanceReviewUser> getPageByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                         int pageNo,
                                                                                         int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserView> getPageView(int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserView> getPageViewByUserId(String userId,
                                                                      int pageNo,
                                                                      int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserView> getPageViewByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                                 int pageNo,
                                                                                                 int pageSize);
    Page<ProjectMaterialAcceptanceReviewUser> getViewPageByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId,
                                                                                                 Integer pageNo,
                                                                                                 Integer pageSize);

    List<ProjectMaterialAcceptanceReviewUser> getByAcceptanceModeId(String projectMaterialAcceptanceReviewModeId);
}