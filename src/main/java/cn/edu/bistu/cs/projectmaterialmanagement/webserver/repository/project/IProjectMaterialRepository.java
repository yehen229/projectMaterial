package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterial;

import java.math.BigDecimal;
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
    int update(String id, String materialId,
               BigDecimal materialCount, String materialUnit);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByProjectIdAndCompanyId(String projectId,
                                        String companyId);

    int getCountBySearch(String projectId,
                         String companyId,
                         String name,
                         String location,
                         String itemMark,
                         String technology,
                         String installation,
                         String brand,
                         String brandPrivate);


    int getCountBySearchNoCompanyId(String projectId,
                                                 String name,
                                                 String location,
                                                 String itemMark,
                                                 String technology,
                                                 String installation,
                                                 String brand,
                                                 String brandPrivate);

    int getCountOfReviewedAndApprovedUseMaterialByProjectId(String projectId);

    ProjectMaterial getById(String id);
    ProjectMaterial getByIdAndCompanyId(String companyId,String id);
    ProjectMaterial getByProjectIdAndMaterialOriginId(String projectId,String materialOriginId);
    ProjectMaterial getByProjectIdAndMaterialId(String projectId,String materialId);

    List<ProjectMaterial> getByProjectId(String projectId);


    Page<ProjectMaterial> getPage(int pageNo,
                                  int pageSize);

    Page<ProjectMaterial> getPageByProjectId(String projectId,
                                             int pageNo,
                                             int pageSize);

    Page<ProjectMaterial> getPageByProjectIdWithParams(String projectId,
                                                        String name,
                                                        String location,
                                                        String itemMark,
                                                        String technology,
                                                        String installation,
                                                        String brand,
                                                        String brandPrivate,
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

    Page<ProjectMaterial> getSearchPageByProjectIdAndCompanyId(String projectId,
                                                         String companyId,
                                                         String name,
                                                         String location,
                                                         String itemMark,
                                                         String technology,
                                                         String installation,
                                                         String brand,
                                                         String brandPrivate,
                                                         Integer pageNo,
                                                         Integer pageSize);


    List<ProjectMaterial> getByProjectIdandCompanyId(String projectId, String companyId);
}