package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFile;

import java.util.List;

/**
 * UseMaterialNewBrandFile Service Interface
 */
public interface IUseMaterialNewBrandFileRepository {

    String add(UseMaterialNewBrandFile useMaterialNewBrandFile);

    int delete(UseMaterialNewBrandFile useMaterialNewBrandFile);

    int update(UseMaterialNewBrandFile useMaterialNewBrandFile);

    int deleteById(String id);

    int deleteByUseMaterialNewBrandId(String useMaterialNewBrandId);

    int getCount();

    int getCountByUseMaterialNewBrandId(String useMaterialNewBrandId);

    UseMaterialNewBrandFile getById(String id);

    List<UseMaterialNewBrandFile> getByUseMaterialNewBrandId(String useMaterialNewBrandId);

    Page<UseMaterialNewBrandFile> getPage(int pageNo, int pageSize);

    Page<UseMaterialNewBrandFile> getPageByUseMaterialNewBrandId(String useMaterialNewBrandId, int pageNo, int pageSize);

}