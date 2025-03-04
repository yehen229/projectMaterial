package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectAppearanceBusinessServiceImpl implements IProjectAppearanceBusinessService {

    private final IProjectAppearanceReviewUserService projectAppearanceReviewUserService;
    private final IProjectAppearanceReviewService projectAppearanceReviewService;
    private final IProjectAppearanceReviewModeService projectAppearanceReviewModeService;
    private final IProjectAppearanceReviewUserFileService projectAppearanceReviewUserFileService;
    private final IUserService userService;

    public ProjectAppearanceBusinessServiceImpl(IProjectAppearanceReviewUserService projectAppearanceReviewUserService,
                                                IProjectAppearanceReviewService projectAppearanceReviewService,
                                                IProjectAppearanceReviewModeService projectAppearanceReviewModeService,
                                                IProjectAppearanceReviewUserFileService projectAppearanceReviewUserFileService,
                                                IUserService userService) {
        this.projectAppearanceReviewUserService = projectAppearanceReviewUserService;
        this.projectAppearanceReviewService = projectAppearanceReviewService;
        this.projectAppearanceReviewModeService = projectAppearanceReviewModeService;
        this.projectAppearanceReviewUserFileService = projectAppearanceReviewUserFileService;
        this.userService = userService;
    }

    @Override
    public ProjectAppearanceReviewUserView getViewByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        ProjectAppearanceReviewUser projectAppearanceReviewUser = projectAppearanceReviewUserService.getById(
                projectAppearanceReviewUserId);
        if (projectAppearanceReviewUser == null) return null;
        ProjectAppearanceReviewUserView projectAppearanceReviewUserView = new ProjectAppearanceReviewUserView();
        projectAppearanceReviewUserView.setProjectAppearanceReviewUser(projectAppearanceReviewUser);
        projectAppearanceReviewUserView.setProjectAppearanceReview(
                projectAppearanceReviewService.getById(projectAppearanceReviewUser.getProjectAppearanceReviewId()));
        projectAppearanceReviewUserView.setUser(userService.getById(projectAppearanceReviewUser.getUserId()));
        projectAppearanceReviewUserView.setProjectAppearanceReviewUserFileList(
                projectAppearanceReviewUserFileService.getByProjectAppearanceReviewUserId(
                        projectAppearanceReviewUserId));

        return projectAppearanceReviewUserView;
    }

    @Override
    public Page<ProjectAppearanceReviewUserView> getViewPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                            Integer pageNo,
                                                                                            Integer pageSize) {
        Page<ProjectAppearanceReviewUser> projectAppearanceReviewUserPage = projectAppearanceReviewUserService.getPageByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId,
                pageNo,
                pageSize);
        return convertProjectAppearanceReviewUserPage2PageView(projectAppearanceReviewUserPage, pageNo, pageSize);

    }


    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserView> getPageView(int pageNo,
                                                             int pageSize) {
        Page<ProjectAppearanceReviewUser> projectAppearanceReviewUserPage = projectAppearanceReviewUserService.getPage(
                pageNo, pageSize);
        return convertProjectAppearanceReviewUserPage2PageView(projectAppearanceReviewUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserView> getPageViewByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        Page<ProjectAppearanceReviewUser> projectAppearanceReviewUserPage = projectAppearanceReviewUserService.getPageByUserId(
                userId, pageNo, pageSize);
        return convertProjectAppearanceReviewUserPage2PageView(projectAppearanceReviewUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectAppearanceReviewId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserView> getPageViewByProjectAppearanceReviewId(String projectAppearanceReviewId,
                                                                                        int pageNo,
                                                                                        int pageSize) {
        Page<ProjectAppearanceReviewUser> projectAppearanceReviewUserPage = projectAppearanceReviewUserService.getPageByProjectAppearanceReviewId(
                projectAppearanceReviewId, pageNo, pageSize);
        return convertProjectAppearanceReviewUserPage2PageView(projectAppearanceReviewUserPage, pageNo, pageSize);
    }

    @Override
    public List<ProjectAppearanceReviewUserView> getViewListByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        List<ProjectAppearanceReviewUser> projectAppearanceReviewUserList = projectAppearanceReviewUserService.getByAppearanceModeId(
                projectAppearanceReviewModeId);
        return convertProjectAppearanceReviewUserList2ListView(projectAppearanceReviewUserList);
    }


    /**
     * 将页面转换为视图页面
     *
     * @param projectAppearanceReviewUserPage 页面对象
     */
    private Page<ProjectAppearanceReviewUserView> convertProjectAppearanceReviewUserPage2PageView(Page<ProjectAppearanceReviewUser> projectAppearanceReviewUserPage,
                                                                                                  int pageNo,
                                                                                                  int pageSize) {
        if (projectAppearanceReviewUserPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUserView> list = convertProjectAppearanceReviewUserList2ListView(
                projectAppearanceReviewUserPage.getResult());

        if (list == null || list.isEmpty()) return null;

        return new Page<>(startIndex, projectAppearanceReviewUserPage.getTotalCount(), pageSize, list);
    }

    private List<ProjectAppearanceReviewUserView> convertProjectAppearanceReviewUserList2ListView(List<ProjectAppearanceReviewUser> projectAppearanceReviewUserList) {
        if (projectAppearanceReviewUserList == null || projectAppearanceReviewUserList.isEmpty()) return null;
        List<ProjectAppearanceReviewUserView> list = new ArrayList<>();
        for (ProjectAppearanceReviewUser projectAppearanceReviewUser : projectAppearanceReviewUserList) {
            ProjectAppearanceReviewUserView projectAppearanceReviewUserView = getViewByProjectAppearanceReviewUserId(
                    projectAppearanceReviewUser.getId());
            if (projectAppearanceReviewUserView != null) list.add(projectAppearanceReviewUserView);
        }
        return list;
    }
}
