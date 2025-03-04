package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelect;

import java.util.List;

/**
 * UseMaterialBrandSelect Service Interface
 */
public interface IUseMaterialBrandSelectRepository {

    String add(UseMaterialBrandSelect useMaterialBrandSelect);

    int delete(UseMaterialBrandSelect useMaterialBrandSelect);

    int update(UseMaterialBrandSelect useMaterialBrandSelect);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectId(String projectId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectId(String projectId);

    UseMaterialBrandSelect getById(String id);

    List<UseMaterialBrandSelect> getByUserId(String userId);

    List<UseMaterialBrandSelect> getByProjectId(String projectId);

    Page<UseMaterialBrandSelect> getPage(int pageNo, int pageSize);

    Page<UseMaterialBrandSelect> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<UseMaterialBrandSelect> getPageByProjectId(String projectId, int pageNo, int pageSize);

}