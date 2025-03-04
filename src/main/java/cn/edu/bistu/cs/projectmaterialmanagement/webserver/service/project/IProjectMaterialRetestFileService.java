package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IProjectMaterialRetestFileService {
    String add(ProjectMaterialRetestFile projectMaterialRetestFile);

    int delete(ProjectMaterialRetestFile projectMaterialRetestFile);

    int update(ProjectMaterialRetestFile projectMaterialRetestFile);

    int deleteById(String id);

    int getCount();

    ProjectMaterialRetestFile getById(String id);

    Page<ProjectMaterialRetestFile> getPage(int pageNo,
                                            int pageSize);

    void downloadFileById(String projectMaterialRetestFileId,
                          HttpServletRequest request,
                          HttpServletResponse response);
    List<ProjectMaterialRetestFile> getByRetestId(String retestId);

}