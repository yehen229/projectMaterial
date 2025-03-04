package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialView;

import java.util.List;

public interface IMaterialBusinessService {
    String addForm(MaterialForm materialForm);

    int updateForm(MaterialForm materialForm);

    MaterialView getViewByMaterialId(String id);

    List<MaterialView> getListViewByMaterialClassifySectionId(String materialClassifySectionId);

    Page<MaterialView> getPageView(Integer pageNo, Integer pageSize);

    Page<MaterialView> getPageViewByName(String name, Integer pageNo, Integer pageSize);

    Page<MaterialView> getPageViewByLocation(String location, Integer pageNo, Integer pageSize);

    Page<MaterialView> getPageViewByItemMark(String itemMark, Integer pageNo, Integer pageSize);

    Page<MaterialView> getPageByMaterialClassifySectionId(String classifySectionId, Integer pageNo, Integer pageSize);

    int delete(Material material);


}
