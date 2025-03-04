package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;

/**
 * Brand Service Interface
 */
public interface IBrandRepository {

    String add(Brand brand);

    int update(Brand brand);

    int deleteById(String id);

    int delete(Brand brand);

    int getCount();

    Brand getById(String id);

    Page<Brand> getPage(int pageNo,
                        int pageSize);

    Brand getByNameAndMaterialClassifySectionIdAndPosition(String brandName,
                                                           String materialClassifySectionId,
                                                           String position);
}