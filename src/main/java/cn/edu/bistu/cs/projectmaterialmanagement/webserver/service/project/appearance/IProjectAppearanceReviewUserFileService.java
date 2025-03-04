package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUserFileView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IProjectAppearanceReviewUserFileService {
    String add(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile);

    String copyFile(String projectAppearanceReviewUserFileId,
                    String newProjectAppearanceReviewUserFileId);

    int delete(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile);

    int update(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile);

    int deleteById(String id);

    int deleteByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    int getCount();

    int getCountByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    ProjectAppearanceReviewUserFile getById(String id);

    ProjectAppearanceReviewUserFileView getViewById(String id);

    List<ProjectAppearanceReviewUserFile> getByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    Page<ProjectAppearanceReviewUserFile> getPage(int pageNo,
                                                  int pageSize);

    Page<ProjectAppearanceReviewUserFile> getPageByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId,
                                                                                 int pageNo,
                                                                                 int pageSize);

    Page<ProjectAppearanceReviewUserFileView> getPageView(int pageNo,
                                                          int pageSize);

    Page<ProjectAppearanceReviewUserFileView> getPageViewByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId,
                                                                                         int pageNo,
                                                                                         int pageSize);

    void downloadFileById(String projectAppearanceReviewUserFileId,
                          HttpServletRequest request,
                          HttpServletResponse response);
}