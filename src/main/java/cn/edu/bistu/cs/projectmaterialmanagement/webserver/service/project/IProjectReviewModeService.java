package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewModeView;

import java.util.List;

public interface IProjectReviewModeService {
    int PROJECT_REVIEW_MODE_DISPATCH = 0;//分发，交由项目员工审核
    int PROJECT_REVIEW_MODE_MANAGER_REVIEW = 1;//项目经理直接审核
    

    String add(ProjectReviewMode projectReviewMode);

    int delete(ProjectReviewMode projectReviewMode);

    int update(ProjectReviewMode projectReviewMode);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int deleteByUserId(String userId);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByUserId(String userId);

    ProjectReviewMode getById(String id);

    List<ProjectReviewMode> getByProjectId(String projectId);

    List<ProjectReviewMode> getByUserId(String userId);

    Page<ProjectReviewMode> getPage(int pageNo, int pageSize);

    Page<ProjectReviewMode> getPageByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectReviewMode> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectReviewModeView> getPageView(int pageNo, int pageSize);

    Page<ProjectReviewModeView> getPageViewByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectReviewModeView> getPageViewByUserId(String userId, int pageNo, int pageSize);

}