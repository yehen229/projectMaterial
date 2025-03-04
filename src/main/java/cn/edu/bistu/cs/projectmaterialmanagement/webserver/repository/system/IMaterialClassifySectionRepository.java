package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySection;

import java.util.List;

/**
 * MaterialClassifySection Service Interface
 */
public interface IMaterialClassifySectionRepository {

    String add(MaterialClassifySection materialClassifySection);

    int delete(MaterialClassifySection materialClassifySection);

    int update(MaterialClassifySection materialClassifySection);

    int deleteById(String id);

    int deleteByMaterialClassifyGroupId(String materialClassifyGroupId);

    int getCount();

    int getCountByMaterialClassifyGroupId(String materialClassifyGroupId);

    MaterialClassifySection getById(String id);

    List<MaterialClassifySection> getByMaterialClassifyGroupId(String materialClassifyGroupId);

    Page<MaterialClassifySection> getPage(int pageNo, int pageSize);

    Page<MaterialClassifySection> getPageByMaterialClassifyGroupId(String materialClassifyGroupId, int pageNo, int pageSize);

}