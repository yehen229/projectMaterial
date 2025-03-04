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
    int getCountByProjectBindType(int projectBindType);
    int getCountByMaterialClassifySectionId(String materialClassifySectionId);
    int getCountByMaterialClassifySectionId(String materialClassifySectionId,int materialBindType);
    int getCountByMaterialClassifySectionIdAndProjectBindType(String materialClassifySectionId,int materialBindType);

    int getCountByLikeItemMark(String itemMark);

    int getCountByLikeLocation(String location);

    int getCountByLikeName(String name);

    int getCountByLikeItemMarkAndProjectBindType(String itemMark, int projectBindType);

    int getCountByLikeLocationAndProjectBindType(String location, int projectBindType);

    int getCountByLikeNameAndProjectBindType(String name, int projectBindType);

    Material getById(String id);

    List<Material> getByMaterialClassifySectionId(String materialClassifySectionId);
    List<Material> getByMaterialClassifySectionId(String materialClassifySectionId,int materialBindType);
    List<Material> getByMaterialClassifySectionIdAndProjectBindType(String materialClassifySectionId,int projectBindType);

    Page<Material> getPage(int pageNo, int pageSize);

    Page<Material> getPageByMaterialClassifySectionId(String materialClassifySectionId, int pageNo, int pageSize);
    Page<Material> getPageByMaterialClassifySectionId(String materialClassifySectionId, int materialBindType,int pageNo, int pageSize);



    Page<Material> getPageByItemMark(String itemMark, Integer pageNo, Integer pageSize);

    Page<Material> getPageByLocation(String location, Integer pageNo, Integer pageSize);

    Page<Material> getPageByName(String name, Integer pageNo, Integer pageSize);


    Page<Material> getPageByProjectBindType(int projectBindType,int pageNo, int pageSize);

    Page<Material> getPageByMaterialClassifySectionIdAndProjectBindType(String materialClassifySectionId,int projectBindType, int pageNo, int pageSize);



    Page<Material> getPageByItemMarkAndProjectBindType(String itemMark,int projectBindType, Integer pageNo, Integer pageSize);

    Page<Material> getPageByLocationAndProjectBindType(String location,int projectBindType, Integer pageNo, Integer pageSize);


    Page<Material> getPageByNameAndProjectBindType(String name,
                                                   int projectBindType,
                                                   Integer pageNo,
                                                   Integer pageSize);


}