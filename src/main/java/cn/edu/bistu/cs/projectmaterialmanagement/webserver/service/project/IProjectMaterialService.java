package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterial;

import java.math.BigDecimal;
import java.util.List;

public interface IProjectMaterialService {
    String add(ProjectMaterial projectMaterial);

    int delete(ProjectMaterial projectMaterial);

    int update(ProjectMaterial projectMaterial);
    int update(String id, String materialId,
               BigDecimal materialCount, String materialUnit);
    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int getCount();

    int getCountByProjectId(String projectId);
    List<ProjectMaterial> getByMaterialId(String materialId);

    ProjectMaterial getById(String id);
    ProjectMaterial getByProjectIdAndMaterialOriginId(String projectId,String materialOriginId);
    ProjectMaterial getByProjectIdAndMaterialId(String projectId,String materialId);

    List<ProjectMaterial> getByProjectId(String projectId);

    Page<ProjectMaterial> getPage(int pageNo,
                                  int pageSize);

    Page<ProjectMaterial> getPageByProjectId(String projectId,
                                             int pageNo,
                                             int pageSize);


    Page<ProjectMaterial> getReviewedAndApprovedUseMaterialViewPageByProjectId(String projectId,
                                                                               Integer pageNo,
                                                                               Integer pageSize);

    Page<ProjectMaterial> getPageByProjectIdAndCompanyId(String projectId,
                                                         String companyId,
                                                         Integer pageNo,
                                                         Integer pageSize);

    List<ProjectMaterial> getListByProjectId(String projectId, String companyId);
}