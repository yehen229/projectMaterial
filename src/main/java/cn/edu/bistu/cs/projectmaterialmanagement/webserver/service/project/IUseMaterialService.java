package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;

import java.util.List;

public interface IUseMaterialService {
    String add(UseMaterial useMaterial);

    int delete(UseMaterial useMaterial);

    int update(UseMaterial useMaterial);

    int deleteById(String id);

    int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId);

    int deleteByProjectMaterialId(String projectMaterialId);

    int deleteByUserId(String userId);

    int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    int getCount();

    int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId);

    int getCountByProjectMaterialId(String projectMaterialId);

    int getCountByUserId(String userId);

    int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    UseMaterial getById(String id);

    List<UseMaterial> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId);

    List<UseMaterial> getByProjectMaterialId(String projectMaterialId);

    List<UseMaterial> getByUserId(String userId);

    List<UseMaterial> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    List<UseMaterial> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    List<UseMaterial> getReviewedAndApprovedListByProjectId(String projectId);

    boolean isExistByProjectMaterialIdAndBrandPublicIdAndAppearance(String projectMaterialId,
                                                                          String projectMaterialBrandPublicId,
                                                                          int isAppearance);

    Page<UseMaterial> getPage(int pageNo,
                              int pageSize);

    Page<UseMaterial> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                        int pageNo,
                                                        int pageSize);

    Page<UseMaterial> getPageByProjectMaterialId(String projectMaterialId,
                                                 int pageNo,
                                                 int pageSize);

    Page<UseMaterial> getPageByUserId(String userId,
                                      int pageNo,
                                      int pageSize);

    Page<UseMaterial> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                             int pageNo,
                                                             int pageSize);

    Page<UseMaterial> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                            int pageNo,
                                                            int pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectId(String projectId,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndName(String projectId,
                                                            String name,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndLocation(String projectId,
                                                            String location,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndItemMark(String projectId,
                                                            String itemMark,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndTechnology(String projectId,
                                                            String technology,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndInstallation(String projectId,
                                                            String installation,
                                                            Integer pageNo,
                                                            Integer pageSize);

    Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndBrand(String projectId,
                                                            String brand,
                                                            Integer pageNo,
                                                            Integer pageSize);

}