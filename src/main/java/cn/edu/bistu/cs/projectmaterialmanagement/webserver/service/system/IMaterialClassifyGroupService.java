package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyGroup;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyGroupView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyTree;

import java.util.List;

public interface IMaterialClassifyGroupService {
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

    Page<MaterialClassifyGroupView> getPageView(int pageNo, int pageSize);

    Page<MaterialClassifyGroupView> getPageViewByMaterialClassifyDivisionId(String materialClassifyDivisionId, int pageNo, int pageSize);

    MaterialClassifyGroupView getMaterialClassifyGroupView(MaterialClassifyGroup materialClassifyGroup);

    MaterialClassifyGroupView getMaterialClassifyGroupViewById(String id);

    MaterialClassifyTree getTree();
}