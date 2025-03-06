package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUser;

import java.util.List;

/**
 * ProjectReviewUser Service Interface
 */
public interface IProjectReviewUserRepository {

    String add(ProjectReviewUser projectReviewUser);

    int delete(ProjectReviewUser projectReviewUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectReviewId(String projectReviewId);

    int update(ProjectReviewUser projectReviewUser);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectReviewId(String projectReviewId);
    int getCountByProjectReviewIdAndUserAndResult(String projectReviewId, String reviewUser, int reviewResult);

    int getCountByProjectReviewIdAndResult(String projectReviewId, int nReviewResult);
    int getCountByProjecId(String projectId);
    int getCountByProjectIdAndResult(String projectId, int nReviewResult);
    ProjectReviewUser getById(String id);


    ProjectReviewUser getByUserIdAndProjectReviewId(String userId, String projectReviewId);


    List<ProjectReviewUser> getByUserId(String userId);

    List<ProjectReviewUser> getByProjectReviewId(String projectReviewId);

    List<ProjectReviewUser> getByProjectReviewIdAndNotReviewed(String projectReviewId, int reviewResult);


    Page<ProjectReviewUser> getPage(int pageNo, int pageSize);

    Page<ProjectReviewUser> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectReviewUser> getPageByProjectReviewId(String projectReviewId, int pageNo, int pageSize);

    Page<ProjectReviewUser> getPageByProjectReviewIdAndUserAndResult(String projectReviewId, String reviewUser, int reviewResult, int pageNo, int pageSize);

}