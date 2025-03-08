package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySection;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySectionView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyTree;

import java.util.List;

public interface IMaterialClassifySectionService {
    String add(MaterialClassifySection materialClassifySection);

    int delete(MaterialClassifySection materialClassifySection);

    int update(MaterialClassifySection materialClassifySection);

    int deleteById(String id);

    int deleteByMaterialClassifyGroupId(String materialClassifyGroupId);

    int getCount();

    int getCountByMaterialClassifyGroupId(String materialClassifyGroupId);

    MaterialClassifySection getById(String id);

    MaterialClassifySectionView getViewById(String id);

    List<MaterialClassifySection> getByMaterialClassifyGroupId(String materialClassifyGroupId);

    Page<MaterialClassifySection> getPage(int pageNo, int pageSize);

    Page<MaterialClassifySection> getPageByMaterialClassifyGroupId(String materialClassifyGroupId, int pageNo, int pageSize);

    Page<MaterialClassifySectionView> getPageView(int pageNo, int pageSize);

    Page<MaterialClassifySectionView> getPageViewByMaterialClassifyGroupId(String materialClassifyGroupId, int pageNo, int pageSize);


    MaterialClassifyTree getTree();

    MaterialClassifyTree getTreeByName(String name);


}