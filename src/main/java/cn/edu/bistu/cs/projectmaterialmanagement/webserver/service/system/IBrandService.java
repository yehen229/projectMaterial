package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandView;

public interface IBrandService {
    String add(Brand brand);

    int delete(Brand brand);

    int update(Brand brand);

    int deleteById(String id);

    int getCount();

    Brand getById(String id);
    Brand getByNameAndMaterialClassifySectionIdAndPosition(String brandName,
                                                           String materialClassifySectionId,
                                                           String position);

    Page<Brand> getPage(int pageNo,
                        int pageSize);

    BrandView getViewById(String brandId);
}