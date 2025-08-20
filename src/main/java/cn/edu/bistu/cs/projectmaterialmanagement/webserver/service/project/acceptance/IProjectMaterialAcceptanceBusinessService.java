package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceView;

import java.util.List;


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
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchIdAndName(String projectMaterialAcceptanceBatchId,
                                                                                      String name,
                                                                                      int pageNo,
                                                                                      int pageSize);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchIdAndLocation(String projectMaterialAcceptanceBatchId,
                                                                                      String location,
                                                                                      int pageNo,
                                                                                      int pageSize);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchIdAndItemMark(String projectMaterialAcceptanceBatchId,
                                                                                      String itemMark,
                                                                                      int pageNo,
                                                                                      int pageSize);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchIdAndTechnology(String projectMaterialAcceptanceBatchId,
                                                                                      String technology,
                                                                                      int pageNo,
                                                                                      int pageSize);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchIdAndInstallation(String projectMaterialAcceptanceBatchId,
                                                                                      String installation,
                                                                                      int pageNo,
                                                                                      int pageSize);
    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchIdAndBrand(String projectMaterialAcceptanceBatchId,
                                                                                      String brand,
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

    List<ProjectMaterialAcceptanceReviewUserView> getViewListByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId);

    Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                                  int pageNo,
                                                                                  int pageSize);

    Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                                               int pageNo,
                                                                                                               int pageSize);

}
