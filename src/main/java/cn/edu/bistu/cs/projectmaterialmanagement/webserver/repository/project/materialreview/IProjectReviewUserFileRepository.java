package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUserFile;

import java.util.List;

/**
 * ProjectReviewUserFile Service Interface
 */
public interface IProjectReviewUserFileRepository {

    String add(ProjectReviewUserFile projectReviewUserFile);

    int delete(ProjectReviewUserFile projectReviewUserFile);

    int deleteById(String id);

    int deleteByProjectReviewUserId(String projectReviewUserId);

    int update(ProjectReviewUserFile projectReviewUserFile);

    int getCount();

    int getCountByProjectReviewUserId(String projectReviewUserId);

    ProjectReviewUserFile getById(String id);

    List<ProjectReviewUserFile> getByProjectReviewUserId(String projectReviewUserId);

    Page<ProjectReviewUserFile> getPage(int pageNo, int pageSize);

    Page<ProjectReviewUserFile> getPageByProjectReviewUserId(String projectReviewUserId, int pageNo, int pageSize);

}