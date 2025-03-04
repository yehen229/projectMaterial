package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReview;

import java.util.List;

/**
 * ProjectAppearanceReview Service Interface
 */
public interface IProjectAppearanceReviewRepository {

    String add(ProjectAppearanceReview projectAppearanceReview);

    int delete(ProjectAppearanceReview projectAppearanceReview);

    int deleteById(String id);

    int deleteByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    int deleteByUseMaterialId(String useMaterialId);

    int update(ProjectAppearanceReview projectAppearanceReview);

    int getCount();

    int getCountByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    int getCountByUseMaterialId(String useMaterialId);

    ProjectAppearanceReview getById(String id);

    List<ProjectAppearanceReview> getByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    List<ProjectAppearanceReview> getByUseMaterialId(String useMaterialId);

    Page<ProjectAppearanceReview> getPage(int pageNo,
                                          int pageSize);

    Page<ProjectAppearanceReview> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                         int pageNo,
                                                                         int pageSize);

    Page<ProjectAppearanceReview> getPageByUseMaterialId(String useMaterialId,
                                                         int pageNo,
                                                         int pageSize);

}