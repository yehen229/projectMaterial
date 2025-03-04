package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewStatistics;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUserView;

import java.util.List;

public interface IProjectReviewBusinessService {

    ProjectReviewUserView getViewByProjectReviewIdAndUserId(String projectReviewId,
                                                            String userId);

    Page<ProjectReviewUserView> getPageView(int pageNo,
                                            int pageSize);

    Page<ProjectReviewUserView> getPageViewByUserId(String userId,
                                                    int pageNo,
                                                    int pageSize);

    Page<ProjectReviewUserView> getPageViewByProjectReviewId(String projectReviewId,
                                                             int pageNo,
                                                             int pageSize);

    ProjectReviewStatistics getStatisticsOfProjectReviewUserViewByProjectReviewId(String projectReviewId);

    List<ProjectReviewUserView> getProjectReviewUserViewListByProjectIdAndTaskId(String projectReviewId);
}
