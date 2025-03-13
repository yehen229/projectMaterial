package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptance;

import java.util.List;

/**
 * ProjectMaterialAcceptance Service Interface
 */
public interface IProjectMaterialAcceptanceRepository {

    String add(ProjectMaterialAcceptance projectMaterialAcceptance);

    int delete(ProjectMaterialAcceptance projectMaterialAcceptance);

    int update(ProjectMaterialAcceptance projectMaterialAcceptance);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId);

    int deleteByProjectMaterialId(String projectMaterialId);

    int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId);
    int getCountByProjectMaterialAcceptanceBatchIdAndName(String projectMaterialAcceptanceBatchId, String name);
    int getCountByProjectMaterialAcceptanceBatchIdAndLocation(String projectMaterialAcceptanceBatchId, String location);
    int getCountByProjectMaterialAcceptanceBatchIdAndItemMark(String projectMaterialAcceptanceBatchId, String itemMark);
    int getCountByProjectMaterialAcceptanceBatchIdAndTechnology(String projectMaterialAcceptanceBatchId, String technology);
    int getCountByProjectMaterialAcceptanceBatchIdAndInstallation(String projectMaterialAcceptanceBatchId, String installation);
    int getCountByProjectMaterialAcceptanceBatchIdAndBrand(String projectMaterialAcceptanceBatchId, String brand);

    int getCountByProjectMaterialId(String projectMaterialId);

    int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    ProjectMaterialAcceptance getById(String id);

    List<ProjectMaterialAcceptance> getByUserId(String userId);

    List<ProjectMaterialAcceptance> getByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId);

    List<ProjectMaterialAcceptance> getByProjectMaterialId(String projectMaterialId);

    List<ProjectMaterialAcceptance> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    List<ProjectMaterialAcceptance> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    Page<ProjectMaterialAcceptance> getPage(int pageNo,
                                            int pageSize);

    Page<ProjectMaterialAcceptance> getPageByUserId(String userId,
                                                    int pageNo,
                                                    int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchIdAndName(String projectMaterialAcceptanceBatchId,
                                                                              String name,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchIdAndLocation(String projectMaterialAcceptanceBatchId,
                                                                              String location,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchIdAndItemMark(String projectMaterialAcceptanceBatchId,
                                                                              String itemMark,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchIdAndTechnology(String projectMaterialAcceptanceBatchId,
                                                                              String technology,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchIdAndInstallation(String projectMaterialAcceptanceBatchId,
                                                                              String installation,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchIdAndBrand(String projectMaterialAcceptanceBatchId,
                                                                              String brand,
                                                                              int pageNo,
                                                                              int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialId(String projectMaterialId,
                                                               int pageNo,
                                                               int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                           int pageNo,
                                                                           int pageSize);

    Page<ProjectMaterialAcceptance> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                          int pageNo,
                                                                          int pageSize);

}