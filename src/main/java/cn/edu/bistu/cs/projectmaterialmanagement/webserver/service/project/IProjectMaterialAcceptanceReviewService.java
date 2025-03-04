package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewView;

import java.util.List;

public interface IProjectMaterialAcceptanceReviewService {
    String add(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview);

    int delete(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview);

    int update(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview);

    int deleteById(String id);

    int deleteByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId);

    int getCount();

    int getCountByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId);
    List<ProjectMaterialAcceptanceReview> getByMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId);
    ProjectMaterialAcceptanceReview getById(String id);

    List<ProjectMaterialAcceptanceReview> getByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId);

    Page<ProjectMaterialAcceptanceReview> getPage(int pageNo,
                                                  int pageSize);

    Page<ProjectMaterialAcceptanceReview> getPageByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                   int pageNo,
                                                                                   int pageSize);

    Page<ProjectMaterialAcceptanceReviewView> getPageView(int pageNo,
                                                          int pageSize);

    Page<ProjectMaterialAcceptanceReviewView> getPageViewByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                           int pageNo,
                                                                                           int pageSize);

}