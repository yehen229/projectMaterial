package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublic;

import java.util.List;

/**
 * BrandPublic Service Interface
 */
public interface IBrandPublicRepository {

    String add(BrandPublic brandPublic);

    int update(BrandPublic brandPublic);

    int deleteById(String id);

    int deleteByBrandId(String brandId);

    int delete(BrandPublic brandPublic);

    int getCount();

    int getCountByBrandId(String brandId);

    int getCountByBrandName(String brandName);

    int getCountByBrandPosition(String brandPosition);

    BrandPublic getById(String id);

    List<BrandPublic> getByBrandId(String brandId);

    List<BrandPublic> getByMaterialClassifySectionId(String materialClassifySectionId);

    Page<BrandPublic> getPage(int pageNo, int pageSize);

    Page<BrandPublic> getPageByBrandId(String brandId, int pageNo, int pageSize);

    Page<BrandPublic> getPageByBrandName(String brandName, int pageNo, int pageSize);

    Page<BrandPublic> getPageByBrandPosition(String brandPosition, int pageNo, int pageSize);

}