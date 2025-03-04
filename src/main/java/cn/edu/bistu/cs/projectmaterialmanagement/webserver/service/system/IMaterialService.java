package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;

import java.util.List;

public interface IMaterialService {
    String add(Material material);


    int delete(Material material);

    int update(Material material);

    int deleteById(String id);

    int getCount();

    Material getById(String id);

    List<Material> getByMaterialClassifySectionId(String materialClassifySectionId);
    List<Material> getByMaterialClassifySectionIdAndProjectBindType(String materialClassifySectionId, int projectBindType);


    Page<Material> getPage(int pageNo, int pageSize);


    Page<Material> getPageByMaterialClassifySectionId(String classifySectionId, Integer pageNo, Integer pageSize);

    Page<Material> getPageByItemMark(String itemMark, Integer pageNo, Integer pageSize);

    Page<Material> getPageByLocation(String location, Integer pageNo, Integer pageSize);

    Page<Material> getPageByName(String name, Integer pageNo, Integer pageSize);



    Page<Material> getPageByProjectBindType(int projectBindType,
                                            Integer pageNo,
                                            Integer pageSize);

    Page<Material> getPageByMaterialClassifySectionIdAndProjectBindType(String classifySectionId, int projectBindType,Integer pageNo, Integer pageSize);

    Page<Material> getPageByItemMarkAndProjectBindType(String itemMark,int projectBindType, Integer pageNo, Integer pageSize);

    Page<Material> getPageByLocationAndProjectBindType(String location,int projectBindType, Integer pageNo, Integer pageSize);

    Page<Material> getPageByNameAndProjectBindType(String name, int projectBindType,Integer pageNo, Integer pageSize);


}