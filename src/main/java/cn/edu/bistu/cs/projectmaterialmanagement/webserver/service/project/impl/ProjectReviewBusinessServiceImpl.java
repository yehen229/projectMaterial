package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewStatistics;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectReviewBusinessServiceImpl implements IProjectReviewBusinessService {

    private final IProjectReviewUserService projectReviewUserService;
    private final IProjectReviewService projectReviewService;
    private final IProjectService projectService;
    private final IProjectReviewUserFileService projectReviewUserFileService;
    private final IProjectUserService projectUserService;
    private final IUserService userService;
  

    public ProjectReviewBusinessServiceImpl(IProjectReviewUserService projectReviewUserService,
                                            IProjectReviewService projectReviewService,
                                            IProjectService projectService,
                                            IProjectReviewUserFileService projectReviewUserFileService,
                                            IProjectUserService projectUserService,
                                            IUserService userService) {
        this.projectReviewUserService = projectReviewUserService;
        this.projectReviewService = projectReviewService;
        this.projectService = projectService;
        this.projectReviewUserFileService = projectReviewUserFileService;
        this.projectUserService = projectUserService;
        this.userService = userService;
    }

    @Override
    public ProjectReviewUserView getViewByProjectReviewIdAndUserId(String projectReviewId,
                                                                   String userId) {

        ProjectReviewUser projectReviewUser = projectReviewUserService.getByUserIdAndProjectReviewId(userId,
                                                                                                     projectReviewId
        );

        if (projectReviewUser != null)
            return getProjectReviewUserViewByProjectReviewUserId(projectReviewUser.getId());
        return null;

    }

    @Override
    public Page<ProjectReviewUserView> getPageView(int pageNo,
                                                   int pageSize) {
        Page<ProjectReviewUser> projectReviewUserPage = projectReviewUserService.getPage(pageNo, pageSize);
        return convertProjectReviewUserPage2PageView(projectReviewUserPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectReviewUserView> getPageViewByUserId(String userId,
                                                           int pageNo,
                                                           int pageSize) {
        Page<ProjectReviewUser> projectReviewUserPage = projectReviewUserService.getPageByUserId(userId, pageNo,
                                                                                                 pageSize);
        return convertProjectReviewUserPage2PageView(projectReviewUserPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectReviewUserView> getPageViewByProjectReviewId(String projectReviewId,
                                                                    int pageNo,
                                                                    int pageSize) {
        Page<ProjectReviewUser> projectReviewUserPage = projectReviewUserService.getPageByProjectReviewId(
                projectReviewId, pageNo, pageSize);
        return convertProjectReviewUserPage2PageView(projectReviewUserPage, pageNo, pageSize);
    }

    @Override
    public ProjectReviewStatistics getStatisticsOfProjectReviewUserViewByProjectReviewId(String projectReviewId) {
        int countAccept = projectReviewUserService.getCountByProjectReviewIdAndResult(projectReviewId,
                                                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        int countReject = projectReviewUserService.getCountByProjectReviewIdAndResult(projectReviewId,
                                                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
        int countUnreviewed = projectReviewUserService.getCountByProjectReviewIdAndResult(projectReviewId,
                                                                                          IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
        ProjectReviewStatistics projectReviewStatistics = new ProjectReviewStatistics();
        projectReviewStatistics.setProjectReviewView(projectReviewService.getViewById(projectReviewId));
        projectReviewStatistics.setReviewResultAccept(countAccept);
        projectReviewStatistics.setReviewResultReject(countReject);
        projectReviewStatistics.setReviewResultUnreviewed(countUnreviewed);
        return projectReviewStatistics;

    }


    public List<ProjectReviewUserView> getProjectReviewUserViewListByProjectIdAndTaskId(String projectReviewId) {
        List<ProjectReviewUser> projectReviewUserList = projectReviewUserService.getByProjectReviewId(projectReviewId);
        return convertProjectReviewUserList2ListView(projectReviewUserList);
    }


    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectReviewUserView getProjectReviewUserViewByProjectReviewUserId(String id) {
        ProjectReviewUser projectReviewUser = projectReviewUserService.getById(id);
        if (projectReviewUser == null) return null;
        ProjectReviewUserView projectReviewUserView = new ProjectReviewUserView();
        projectReviewUserView.setProjectReviewUser(projectReviewUser);
        projectReviewUserView.setUser(userService.getById(projectReviewUser.getUserId()));
        projectReviewUserView.setProjectReview(projectReviewService.getById(projectReviewUser.getProjectReviewId()));
        projectReviewUserView.setProjectReviewUserFileList(
                projectReviewUserFileService.getByProjectReviewUserId(
                        projectReviewUser.getId()
                ));
        return projectReviewUserView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectReviewUserPage 页面对象
     */
    private Page<ProjectReviewUserView> convertProjectReviewUserPage2PageView(Page<ProjectReviewUser> projectReviewUserPage,
                                                                              int pageNo,
                                                                              int pageSize) {
        if (projectReviewUserPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUserView> list = new ArrayList<>();
        for (ProjectReviewUser projectReviewUser : projectReviewUserPage.getResult()) {
            ProjectReviewUserView projectReviewUserView = getProjectReviewUserViewByProjectReviewUserId(
                    projectReviewUser.getId());
            if (projectReviewUserView != null) list.add(projectReviewUserView);
        }
        return new Page<>(startIndex, projectReviewUserPage.getTotalCount(), pageSize, list);
    }

    private List<ProjectReviewUserView> convertProjectReviewUserList2ListView(List<ProjectReviewUser> projectReviewUserList) {
        if (projectReviewUserList == null) return null;

        List<ProjectReviewUserView> list = new ArrayList<>();
        for (ProjectReviewUser projectReviewUser : projectReviewUserList) {
            ProjectReviewUserView projectReviewUserView = getProjectReviewUserViewByProjectReviewUserId(
                    projectReviewUser.getId());
            if (projectReviewUserView != null) list.add(projectReviewUserView);
        }
        return list;
    }


}
