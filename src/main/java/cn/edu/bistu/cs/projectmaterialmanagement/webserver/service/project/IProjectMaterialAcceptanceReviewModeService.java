package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewModeView;

import java.util.List;

public interface IProjectMaterialAcceptanceReviewModeService {
    String add(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode);

    int delete(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode);

    int update(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId);

    ProjectMaterialAcceptanceReviewMode getById(String id);

    List<ProjectMaterialAcceptanceReviewMode> getByUserId(String userId);

    List<ProjectMaterialAcceptanceReviewMode> getByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId);

    Page<ProjectMaterialAcceptanceReviewMode> getPage(int pageNo,
                                                      int pageSize);

    Page<ProjectMaterialAcceptanceReviewMode> getPageByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialAcceptanceReviewMode> getPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                        int pageNo,
                                                                                        int pageSize);

    Page<ProjectMaterialAcceptanceReviewModeView> getPageView(int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialAcceptanceReviewModeView> getPageViewByUserId(String userId,
                                                                      int pageNo,
                                                                      int pageSize);

    Page<ProjectMaterialAcceptanceReviewModeView> getPageViewByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                                int pageNo,
                                                                                                int pageSize);

}