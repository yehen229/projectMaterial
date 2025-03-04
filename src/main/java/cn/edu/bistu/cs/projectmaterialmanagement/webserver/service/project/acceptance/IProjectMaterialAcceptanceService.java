package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptance;

import java.util.List;

public interface IProjectMaterialAcceptanceService {
    String add(ProjectMaterialAcceptance projectMaterialAcceptance);

    int delete(ProjectMaterialAcceptance projectMaterialAcceptance);

    int update(ProjectMaterialAcceptance projectMaterialAcceptance);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectMaterialId(String projectMaterialId);

    int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectMaterialId(String projectMaterialId);

    int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    ProjectMaterialAcceptance getById(String id);

    List<ProjectMaterialAcceptance> getByUserId(String userId);

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