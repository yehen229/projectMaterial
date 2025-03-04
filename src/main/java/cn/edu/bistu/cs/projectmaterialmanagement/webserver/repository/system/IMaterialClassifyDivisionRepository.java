package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyDivision;

import java.util.List;

/**
 * MaterialClassifyDivision Service Interface
 */
public interface IMaterialClassifyDivisionRepository {

    String add(MaterialClassifyDivision materialClassifyDivision);

    int delete(MaterialClassifyDivision materialClassifyDivision);

    int update(MaterialClassifyDivision materialClassifyDivision);

    int deleteById(String id);

    int getCount();

    MaterialClassifyDivision getById(String id);

    Page<MaterialClassifyDivision> getPage(int pageNo, int pageSize);

    List<MaterialClassifyDivision> getAllList();
}