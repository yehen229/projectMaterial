package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFileView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IProjectMaterialVerificationDocumentFileService {
    String add(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile);

    String copyFile(String ProjectMaterialVerificationDocumentFileId,
                    String newProjectMaterialVerificationDocumentFileId);

    int delete(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile);

    int update(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile);

    int deleteById(String id);

    int deleteByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId);

    int getCount();

    int getCountByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId);

    ProjectMaterialVerificationDocumentFile getById(String id);

    ProjectMaterialVerificationDocumentFileView getViewById(String id);

    List<ProjectMaterialVerificationDocumentFile> getByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId);

    Page<ProjectMaterialVerificationDocumentFile> getPage(int pageNo,
                                                          int pageSize);

    Page<ProjectMaterialVerificationDocumentFile> getPageByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId,
                                                                                                 int pageNo,
                                                                                                 int pageSize);

    Page<ProjectMaterialVerificationDocumentFileView> getPageView(int pageNo,
                                                                  int pageSize);

    Page<ProjectMaterialVerificationDocumentFileView> getPageViewByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId,
                                                                                                         int pageNo,
                                                                                                         int pageSize);

    void downloadFileById(String projectMaterialVerificationDocumentFileId,
                          HttpServletRequest request,
                          HttpServletResponse response);

}