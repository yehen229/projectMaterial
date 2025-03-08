package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatchFile;

import java.util.List;

/**
 * ProjectMaterialRetestFile Service Interface
 */
public interface IProjectMaterialRetestBatchFileRepository {

    String add(ProjectMaterialRetestBatchFile projectMaterialRetestFile);

    int delete(ProjectMaterialRetestBatchFile projectMaterialRetestFile);

    int update(ProjectMaterialRetestBatchFile projectMaterialRetestFile);

    int deleteById(String id);

    int getCount();

    ProjectMaterialRetestBatchFile getById(String id);

    Page<ProjectMaterialRetestBatchFile> getPage(int pageNo,
                                                 int pageSize);
    List<ProjectMaterialRetestBatchFile> getByRetestBatchId(String retestBatchId);


}