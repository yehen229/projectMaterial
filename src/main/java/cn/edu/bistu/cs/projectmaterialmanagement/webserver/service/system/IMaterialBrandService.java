package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrandView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMaterialBrandService {
    String add(MaterialBrand materialBrand);

    int delete(MaterialBrand materialBrand);

    int update(MaterialBrand materialBrand);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    int deleteByBrandId(String brandId);

    int getCount();

    int getCountByMaterialId(String materialId);

    int getCountByBrandId(String brandId);

    MaterialBrand getById(String id);

    List<MaterialBrand> getByMaterialId(String materialId);

    List<MaterialBrand> getByBrandId(String brandId);

    MaterialBrandView getViewById(String Id);

    List<MaterialBrandView> getViewByMaterialId(String materialId);

    Page<MaterialBrand> getPage(int pageNo, int pageSize);

    Page<MaterialBrand> getPageByMaterialId(String materialId, int pageNo, int pageSize);

    Page<MaterialBrand> getPageByBrandId(String brandId, int pageNo, int pageSize);

    Page<MaterialBrandView> getPageView(int pageNo, int pageSize);

    Page<MaterialBrandView> getPageViewByMaterialId(String materialId, int pageNo, int pageSize);

    Page<MaterialBrandView> getPageViewByBrandId(String brandId, int pageNo, int pageSize);

    void importExcel(MultipartFile file);
}