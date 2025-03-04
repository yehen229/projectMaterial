package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewView;

import java.util.List;

public interface IProjectReviewService {
    //审核状态
    int PROJECT_REVIEW_STATUS_UNREVIEWED = 0;//未分配审核方式
    int PROJECT_REVIEW_STATUS_REVIEWING = 1;//已经分配审核方式，正处于审核状态
    int PROJECT_REVIEW_STATUS_REVIEWED = 2;//审核结束


    int PROJECT_REVIEW_RESULT_UNKNOWN = 0;//未知
    int PROJECT_REVIEW_RESULT_ACCEPTED = 1; //通过
    int PROJECT_REVIEW_RESULT_REJECTED = 2;//未通过

    String add(ProjectReview projectReview);

    int delete(ProjectReview projectReview);

    int update(ProjectReview projectReview);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int getCount();

    int getCountByProjectId(String projectId);

    ProjectReview getById(String id);

    ProjectReviewView getViewById(String id);

    List<ProjectReview> getByProjectId(String projectId);

    Page<ProjectReview> getPage(int pageNo,
                                int pageSize);

    Page<ProjectReview> getPageByProjectId(String projectId,
                                           int pageNo,
                                           int pageSize);

    Page<ProjectReviewView> getPageView(int pageNo,
                                        int pageSize);

    Page<ProjectReviewView> getPageViewByProjectId(String projectId,
                                                   int pageNo,
                                                   int pageSize);

}