package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterial;

import java.util.List;

/**
 * ProjectMaterial Service Interface
 */
public interface IProjectMaterialRepository {

    String add(ProjectMaterial projectMaterial);

    int delete(ProjectMaterial projectMaterial);


    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int update(ProjectMaterial projectMaterial);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByProjectIdAndCompanyId(String projectId,
                                        String companyId);

    int getCountOfReviewedAndApprovedUseMaterialByProjectId(String projectId);

    ProjectMaterial getById(String id);

    List<ProjectMaterial> getByProjectId(String projectId);


    Page<ProjectMaterial> getPage(int pageNo,
                                  int pageSize);

    Page<ProjectMaterial> getPageByProjectId(String projectId,
                                             int pageNo,
                                             int pageSize);


    Page<ProjectMaterial> getReviewedAndApprovedUseMaterialViewPageByProjectId(String projectId,
                                                                               Integer pageNo,
                                                                               Integer pageSize);
    List<ProjectMaterial> getByMaterialId(String materialId);

    Page<ProjectMaterial> getPageByProjectIdAndCompanyId(String projectId,
                                                         String companyId,
                                                         Integer pageNo,
                                                         Integer pageSize);
}