package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhoto;

import java.util.List;

/**
 * MaterialPhoto Service Interface
 */
public interface IMaterialPhotoRepository {

    String add(MaterialPhoto materialPhoto);

    int delete(MaterialPhoto materialPhoto);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    int update(MaterialPhoto materialPhoto);

    int getCount();

    int getCountByMaterialId(String materialId);

    MaterialPhoto getById(String id);

    List<MaterialPhoto> getByMaterialId(String materialId);

    Page<MaterialPhoto> getPage(int pageNo, int pageSize);

    Page<MaterialPhoto> getPageByMaterialId(String materialId, int pageNo, int pageSize);

}