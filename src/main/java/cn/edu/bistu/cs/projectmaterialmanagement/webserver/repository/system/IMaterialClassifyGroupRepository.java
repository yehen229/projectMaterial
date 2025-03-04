package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyGroup;

import java.util.List;

/**
 * MaterialClassifyGroup Service Interface
 */
public interface IMaterialClassifyGroupRepository {

    String add(MaterialClassifyGroup materialClassifyGroup);

    int delete(MaterialClassifyGroup materialClassifyGroup);

    int update(MaterialClassifyGroup materialClassifyGroup);

    int deleteById(String id);

    int deleteByMaterialClassifyDivisionId(String materialClassifyDivisionId);

    int getCount();

    int getCountByMaterialClassifyDivisionId(String materialClassifyDivisionId);

    MaterialClassifyGroup getById(String id);

    List<MaterialClassifyGroup> getByMaterialClassifyDivisionId(String materialClassifyDivisionId);

    Page<MaterialClassifyGroup> getPage(int pageNo, int pageSize);

    Page<MaterialClassifyGroup> getPageByMaterialClassifyDivisionId(String materialClassifyDivisionId, int pageNo, int pageSize);

}