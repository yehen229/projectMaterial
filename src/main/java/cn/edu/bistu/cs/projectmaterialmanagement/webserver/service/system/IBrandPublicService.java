package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublicView;

import java.util.List;

public interface IBrandPublicService {
    String add(Brand brand);

    String add(BrandPublic brandPublic);

    int delete(BrandPublic brandPublic);

    int update(BrandPublic brandPublic);

    int update(Brand brand);

    int deleteById(String id);

    int deleteByBrandId(String brandId);

    int getCount();

    int getCountByBrandId(String brandId);

    BrandPublic getById(String id);

    BrandPublicView getViewById(String id);

    List<BrandPublic> getByBrandId(String brandId);

    List<BrandPublicView> getByMaterialClassifySectionId(String materialClassifySectionId);

    Page<BrandPublic> getPage(int pageNo, int pageSize);

    Page<BrandPublic> getPageByBrandId(String brandId, int pageNo, int pageSize);

    Page<BrandPublic> getPageByBrandName(String brandName, int pageNo, int pageSize);

    Page<BrandPublic> getPageByBrandPosition(String brandPosition, int pageNo, int pageSize);

    Page<BrandPublicView> getPageView(int pageNo, int pageSize);

    Page<BrandPublicView> getPageViewByBrandId(String brandId, int pageNo, int pageSize);

    Page<BrandPublicView> getPageViewByBrandName(String brandName, Integer pageNo, Integer pageSize);

    Page<BrandPublicView> getPageViewByBrandPosition(String brandPosition, Integer pageNo, Integer pageSize);

}