package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrand;

import java.util.List;

/**
 * MaterialBrand Service Interface
 */
public interface IMaterialBrandRepository {

    String add(MaterialBrand materialBrand);

    int delete(MaterialBrand materialBrand);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    int deleteByBrandId(String brandId);

    int update(MaterialBrand materialBrand);

    int getCount();

    int getCountByMaterialId(String materialId);

    int getCountByBrandId(String brandId);

    MaterialBrand getById(String id);

    List<MaterialBrand> getByMaterialId(String materialId);

    List<MaterialBrand> getByBrandId(String brandId);

    Page<MaterialBrand> getPage(int pageNo, int pageSize);

    Page<MaterialBrand> getPageByMaterialId(String materialId, int pageNo, int pageSize);

    Page<MaterialBrand> getPageByBrandId(String brandId, int pageNo, int pageSize);

}