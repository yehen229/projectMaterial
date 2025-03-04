package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceView;

import java.awt.event.PaintEvent;


public interface IProjectMaterialAcceptanceBusinessService {

    Page<ProjectMaterialAcceptanceView> getPageView(int pageNo,
                                                    int pageSize);

    Page<ProjectMaterialAcceptanceView> getPageViewByUserId(String userId,
                                                            int pageNo,
                                                            int pageSize);
    ProjectMaterialAcceptanceReviewUserView getViewByprojectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                      int pageNo,
                                                                                      int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserView> getViewPageByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId,
                                                                                                     Integer pageNo,
                                                                                                     Integer pageSize);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialId(String projectMaterialId,
                                                                       int pageNo,
                                                                       int pageSize);

    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                                   int pageNo,
                                                                                   int pageSize);

    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                                  int pageNo,
                                                                                  int pageSize);

    Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                                               int pageNo,
                                                                                                               int pageSize);

}
