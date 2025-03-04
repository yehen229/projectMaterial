package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFileView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IProjectEndFileService {
    String add(ProjectEndFile projectEndFile);

    int delete(ProjectEndFile projectEndFile);

    int update(ProjectEndFile projectEndFile);

    int deleteById(String id);

    int deleteByProjectEndId(String projectEndId);

    int getCount();

    int getCountByProjectEndId(String projectEndId);

    ProjectEndFile getById(String id);

    ProjectEndFileView getViewById(String id);

    List<ProjectEndFile> getByProjectEndId(String projectEndId);

    Page<ProjectEndFile> getPage(int pageNo,
                                 int pageSize);

    Page<ProjectEndFile> getPageByProjectEndId(String projectEndId,
                                               int pageNo,
                                               int pageSize);

    Page<ProjectEndFileView> getPageView(int pageNo,
                                         int pageSize);

    Page<ProjectEndFileView> getPageViewByProjectEndId(String projectEndId,
                                                       int pageNo,
                                                       int pageSize);

    void downloadFileById(String projectEndFileId,
                          HttpServletRequest request,
                          HttpServletResponse response);
}