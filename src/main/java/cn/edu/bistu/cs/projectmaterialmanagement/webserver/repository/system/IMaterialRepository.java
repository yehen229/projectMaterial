package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;

import java.util.List;

/**
 * Material Service Interface
 */
public interface IMaterialRepository {

    String add(Material material);

    int delete(Material material);

    int deleteById(String id);

    int update(Material material);


    int getCount();

    int getCountByMaterialClassifySectionId(String materialClassifySectionId);

    int getCountByLikeItemMark(String itemMark);

    int getCountByLikeLocation(String location);

    int getCountByLikeName(String name);

    Material getById(String id);

    List<Material> getByMaterialClassifySectionId(String materialClassifySectionId);

    Page<Material> getPage(int pageNo, int pageSize);

    Page<Material> getPageByMaterialClassifySectionId(String materialClassifySectionId, int pageNo, int pageSize);


    Page<Material> getPageByItemMark(String itemMark, Integer pageNo, Integer pageSize);

    Page<Material> getPageByLocation(String location, Integer pageNo, Integer pageSize);

    Page<Material> getPageByName(String name, Integer pageNo, Integer pageSize);

}