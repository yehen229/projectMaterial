package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFileView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IProjectMaterialAcceptanceReviewUserFileService {
    String add(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile);

    String copyFile(String projectMaterialAcceptanceReviewUserFileId,
                    String newProjectMaterialAcceptanceReviewUserFileId);

    int delete(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile);

    int update(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile);

    int deleteById(String id);

    int deleteByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);

    int getCount();

    int getCountByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);

    ProjectMaterialAcceptanceReviewUserFile getById(String id);

    List<ProjectMaterialAcceptanceReviewUserFile> getByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId);

    Page<ProjectMaterialAcceptanceReviewUserFile> getPage(int pageNo,
                                                          int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserFile> getPageByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                 int pageNo,
                                                                                                 int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserFileView> getPageView(int pageNo,
                                                                  int pageSize);

    Page<ProjectMaterialAcceptanceReviewUserFileView> getPageViewByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                         int pageNo,
                                                                                                         int pageSize);

    void downloadFileById(String projectMaterialAcceptanceReviewUserId,
                          HttpServletRequest request,
                          HttpServletResponse response);

}