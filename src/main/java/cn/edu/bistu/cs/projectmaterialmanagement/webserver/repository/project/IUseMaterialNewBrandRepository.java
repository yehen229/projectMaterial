package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrand;

import java.util.List;

/**
 * UseMaterialNewBrand Service Interface
 */
public interface IUseMaterialNewBrandRepository {

    String add(UseMaterialNewBrand useMaterialNewBrand);

    int delete(UseMaterialNewBrand useMaterialNewBrand);

    int update(UseMaterialNewBrand useMaterialNewBrand);

    int updateProjectMaterialBrandPrivateId(String id,
                                            String projectMaterialBrandPrivateId);

    int deleteById(String id);

    int deleteByUseMaterialId(String useMaterialId);

    int deleteByMaterialClassifyDivisionId(String materialClassifyDivisionId);

    int deleteByMaterialClassifyGroupId(String materialClassifyGroupId);

    int deleteByMaterialClassifySectionId(String materialClassifySectionId);

    int getCount();

    int getCountByUseMaterialId(String useMaterialId);

    int getCountByMaterialClassifyDivisionId(String materialClassifyDivisionId);

    int getCountByMaterialClassifyGroupId(String materialClassifyGroupId);

    int getCountByMaterialClassifySectionId(String materialClassifySectionId);

    UseMaterialNewBrand getById(String id);

    List<UseMaterialNewBrand> getByUseMaterialId(String useMaterialId);

    List<UseMaterialNewBrand> getByMaterialClassifyDivisionId(String materialClassifyDivisionId);

    List<UseMaterialNewBrand> getByMaterialClassifyGroupId(String materialClassifyGroupId);

    List<UseMaterialNewBrand> getByMaterialClassifySectionId(String materialClassifySectionId);

    Page<UseMaterialNewBrand> getPage(int pageNo,
                                      int pageSize);

    Page<UseMaterialNewBrand> getPageByUseMaterialId(String useMaterialId,
                                                     int pageNo,
                                                     int pageSize);

    Page<UseMaterialNewBrand> getPageByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                  int pageNo,
                                                                  int pageSize);

    Page<UseMaterialNewBrand> getPageByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                               int pageNo,
                                                               int pageSize);

    Page<UseMaterialNewBrand> getPageByMaterialClassifySectionId(String materialClassifySectionId,
                                                                 int pageNo,
                                                                 int pageSize);

}