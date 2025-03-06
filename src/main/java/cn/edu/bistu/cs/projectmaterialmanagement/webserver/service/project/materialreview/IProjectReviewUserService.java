package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUser;

import java.util.List;

public interface IProjectReviewUserService {
    int PROJECT_REVIEW_RESULT_UNKNOWN = 0;//未知
    int PROJECT_REVIEW_RESULT_REJECTED = 1;//未通过
    int PROJECT_REVIEW_RESULT_ACCEPTED = 2; //通过

    String add(ProjectReviewUser projectReviewUser);

    int delete(ProjectReviewUser projectReviewUser);

    int update(ProjectReviewUser projectReviewUser);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectReviewId(String projectReviewId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectReviewId(String projectReviewId);

    ProjectReviewUser getById(String id);

    ProjectReviewUser getByUserIdAndProjectReviewId(String userId,
                                                    String projectReviewId);

    List<ProjectReviewUser> getByUserId(String userId);

    List<ProjectReviewUser> getByProjectReviewId(String projectReviewId);

    List<ProjectReviewUser> getByProjectReviewIdAndNotReviewed(String projectReviewId);


    Page<ProjectReviewUser> getPage(int pageNo,
                                    int pageSize);

    Page<ProjectReviewUser> getPageByUserId(String userId,
                                            int pageNo,
                                            int pageSize);

    Page<ProjectReviewUser> getPageByProjectReviewId(String projectReviewId,
                                                     int pageNo,
                                                     int pageSize);

    Page<ProjectReviewUser> getPageByProjectReviewIdAndUserAndResult(String projectReviewId,
                                                     String reviewUser,
                                                     int reviewResult,
                                                     int pageNo,
                                                     int pageSize);


    int getCountByProjectReviewIdAndResult(String projectReviewId,
                                           int nReviewResult);
   int getCountByProjectId(String projectId);
   int getCountByProjectIdAndResult(String projectId, int nReviewResult);

}