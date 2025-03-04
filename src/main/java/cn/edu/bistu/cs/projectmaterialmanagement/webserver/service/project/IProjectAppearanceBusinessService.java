package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUserView;

import java.util.List;

public interface IProjectAppearanceBusinessService {
    ProjectAppearanceReviewUserView getViewByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    Page<ProjectAppearanceReviewUserView> getViewPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                     Integer pageNo,
                                                                                     Integer pageSize);

    Page<ProjectAppearanceReviewUserView> getPageView(int pageNo,
                                                      int pageSize);

    Page<ProjectAppearanceReviewUserView> getPageViewByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize);

    Page<ProjectAppearanceReviewUserView> getPageViewByProjectAppearanceReviewId(String projectAppearanceReviewId,
                                                                                 int pageNo,
                                                                                 int pageSize);

    List<ProjectAppearanceReviewUserView> getViewListByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);
}
