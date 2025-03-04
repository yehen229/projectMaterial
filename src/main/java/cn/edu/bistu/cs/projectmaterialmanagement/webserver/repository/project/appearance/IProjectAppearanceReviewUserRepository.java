package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUser;

import java.util.List;

/**
 * ProjectAppearanceReviewUser Service Interface
 */
public interface IProjectAppearanceReviewUserRepository {

    String add(ProjectAppearanceReviewUser projectAppearanceReviewUser);

    int delete(ProjectAppearanceReviewUser projectAppearanceReviewUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectAppearanceReviewId(String projectAppearanceReviewId);

    int update(ProjectAppearanceReviewUser projectAppearanceReviewUser);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectAppearanceReviewId(String projectAppearanceReviewId);

    int getCountByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    ProjectAppearanceReviewUser getById(String id);

    ProjectAppearanceReviewUser getByUserIdAndUseMaterialBrandSelectId(String userId,
                                                                       String useMaterialBrandSelectId,
                                                                       String projectAppearanceReviewModeId);

    List<ProjectAppearanceReviewUser> getByUserId(String userId);

    List<ProjectAppearanceReviewUser> getByProjectAppearanceReviewId(String projectAppearanceReviewId);

    List<ProjectAppearanceReviewUser> getByAppearanceModeIdAndNotReviewed(String projectAppearanceReviewModeId,
                                                                          int reviewResult);

    List<ProjectAppearanceReviewUser> getByAppearanceModeId(String projectAppearanceReviewModeId);

    Page<ProjectAppearanceReviewUser> getPage(int pageNo,
                                              int pageSize);

    Page<ProjectAppearanceReviewUser> getPageByUserId(String userId,
                                                      int pageNo,
                                                      int pageSize);

    Page<ProjectAppearanceReviewUser> getPageByProjectAppearanceReviewId(String projectAppearanceReviewId,
                                                                         int pageNo,
                                                                         int pageSize);


    Page<ProjectAppearanceReviewUser> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                             Integer pageNo,
                                                                             Integer pageSize);
    int getCountByProjectId(String projectId);
    int getCountByProjectIdAndResult(String projectId,int nReviewResult);

}