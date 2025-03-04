package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterial;

import java.util.List;

/**
 * BuyMaterial Service Interface
 */
public interface IBuyMaterialRepository {

    String add(BuyMaterial buyMaterial);

    int delete(BuyMaterial buyMaterial);

    int update(BuyMaterial buyMaterial);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByUseMaterialId(String useMaterialId);

    int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectId(String projectId);

    int getCountReCheckIsRequiredByProjectId(String projectId);

    int getCountByUseMaterialId(String useMaterialId);

    int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    BuyMaterial getById(String id);

    List<BuyMaterial> getByUserId(String userId);

    List<BuyMaterial> getByUseMaterialId(String useMaterialId);

    List<BuyMaterial> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);

    List<BuyMaterial> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

    Page<BuyMaterial> getPage(int pageNo,
                              int pageSize);

    Page<BuyMaterial> getPageByUserId(String userId,
                                      int pageNo,
                                      int pageSize);

    Page<BuyMaterial> getPageByUseMaterialId(String useMaterialId,
                                             int pageNo,
                                             int pageSize);

    Page<BuyMaterial> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                             int pageNo,
                                                             int pageSize);

    Page<BuyMaterial> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                            int pageNo,
                                                            int pageSize);

    Page<BuyMaterial> getPageByProjectId(String projectId,
                                         Integer pageNo,
                                         Integer pageSize);

    Page<BuyMaterial> getPageReCheckIsRequiredByProjectId(String projectId,
                                                          Integer pageNo,
                                                          Integer pageSize);

    Page<String> getBoughtMaterialIdPageByProjectId(String projectId,
                                                    Integer pageNo,
                                                    Integer pageSize);


    List<String> getBoughtProjectMaterialBrandPrivateIdListPageByProjectIdAndMaterialId(String projectId,
                                                                                        String materialId);

    List<String> getBoughtProjectMaterialBrandPublicIdListPageByProjectIdAndMaterialId(String projectId,
                                                                                       String materialId);
}