package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewMode;

import java.util.List;

/**
 * ProjectReviewMode Service Interface
 */
public interface IProjectReviewModeRepository {

    String add(ProjectReviewMode projectReviewMode);

    int delete(ProjectReviewMode projectReviewMode);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int deleteByUserId(String userId);

    int update(ProjectReviewMode projectReviewMode);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByUserId(String userId);

    ProjectReviewMode getById(String id);

    List<ProjectReviewMode> getByProjectId(String projectId);

    List<ProjectReviewMode> getByUserId(String userId);

    Page<ProjectReviewMode> getPage(int pageNo, int pageSize);

    Page<ProjectReviewMode> getPageByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectReviewMode> getPageByUserId(String userId, int pageNo, int pageSize);

}