package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReview;

import java.util.List;

/**
 * ProjectReview Service Interface
 */
public interface IProjectReviewRepository {

    String add(ProjectReview projectReview);

    int delete(ProjectReview projectReview);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int update(ProjectReview projectReview);

    int getCount();

    int getCountByProjectId(String projectId);

    ProjectReview getById(String id);

    List<ProjectReview> getByProjectId(String projectId);

    Page<ProjectReview> getPage(int pageNo, int pageSize);

    Page<ProjectReview> getPageByProjectId(String projectId, int pageNo, int pageSize);

}