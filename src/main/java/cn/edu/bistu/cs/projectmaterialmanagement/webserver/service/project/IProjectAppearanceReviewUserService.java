package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUser;

import java.util.List;

public interface IProjectAppearanceReviewUserService {
    String add(ProjectAppearanceReviewUser projectAppearanceReviewUser);

    int delete(ProjectAppearanceReviewUser projectAppearanceReviewUser);

    int update(ProjectAppearanceReviewUser projectAppearanceReviewUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectAppearanceReviewId(String projectAppearanceReviewId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectAppearanceReviewId(String projectAppearanceReviewId);

    ProjectAppearanceReviewUser getById(String id);

    ProjectAppearanceReviewUser getByUserIdAndUseMaterialBrandSelectId(String userId,
                                                                       String useMaterialBrandSelectId,
                                                                       String projectAppearanceReviewModeId);

    List<ProjectAppearanceReviewUser> getByUserId(String userId);

    List<ProjectAppearanceReviewUser> getByProjectAppearanceReviewId(String projectAppearanceReviewId);

    List<ProjectAppearanceReviewUser> getByAppearanceModeIdAndNotReviewed(String projectAppearanceReviewModeId);

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
}