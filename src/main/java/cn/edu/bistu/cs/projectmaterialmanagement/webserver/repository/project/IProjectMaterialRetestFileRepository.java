package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestFile;

import java.util.List;

/**
 * ProjectMaterialRetestFile Service Interface
 */
public interface IProjectMaterialRetestFileRepository {

    String add(ProjectMaterialRetestFile projectMaterialRetestFile);

    int delete(ProjectMaterialRetestFile projectMaterialRetestFile);

    int update(ProjectMaterialRetestFile projectMaterialRetestFile);

    int deleteById(String id);

    int getCount();

    ProjectMaterialRetestFile getById(String id);

    Page<ProjectMaterialRetestFile> getPage(int pageNo,
                                            int pageSize);
    List<ProjectMaterialRetestFile> getByRetestId(String retestId);


}