package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUserFileView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IProjectReviewUserFileService {
    String add(ProjectReviewUserFile projectReviewUserFile);

    String copyProjectReviewUserFile(String projectReviewUserFileId,
                                     String newProjectReviewUserId);

    int delete(ProjectReviewUserFile projectReviewUserFile);

    int update(ProjectReviewUserFile projectReviewUserFile);

    int deleteById(String id);

    int deleteByProjectReviewUserId(String projectReviewUserId);

    int getCount();

    int getCountByProjectReviewUserId(String projectReviewUserId);

    ProjectReviewUserFile getById(String id);

    ProjectReviewUserFileView getViewById(String id);


    List<ProjectReviewUserFile> getByProjectReviewUserId(String projectReviewUserId);

    Page<ProjectReviewUserFile> getPage(int pageNo,
                                        int pageSize);

    Page<ProjectReviewUserFile> getPageByProjectReviewUserId(String projectReviewUserId,
                                                             int pageNo,
                                                             int pageSize);

    Page<ProjectReviewUserFileView> getPageView(int pageNo,
                                                int pageSize);

    Page<ProjectReviewUserFileView> getPageViewByProjectReviewUserId(String projectReviewUserId,
                                                                     int pageNo,
                                                                     int pageSize);

    void downloadFileById(String projectReviewUserFileId,
                          HttpServletRequest request,
                          HttpServletResponse response);
}