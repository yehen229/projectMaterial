package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialAcceptanceBusinessServiceImpl implements IProjectMaterialAcceptanceBusinessService {

    private final IProjectMaterialAcceptanceBatchService projectMaterialAcceptanceBatchService;
    private final IProjectMaterialAcceptanceService projectMaterialAcceptanceService;
    private final IProjectMaterialAcceptanceReviewService projectMaterialAcceptanceReviewService;
    private final IProjectMaterialAcceptanceReviewModeService projectMaterialAcceptanceReviewModeService;
    private final IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService;
    private final IProjectMaterialAcceptanceReviewUserFileService projectMaterialAcceptanceReviewUserFileService;
    private final ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser;
    private final ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile;

    private final IUserService userService;
    private final IProjectMaterialBusinessService projectMaterialBusinessService;
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;
    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;


    public ProjectMaterialAcceptanceBusinessServiceImpl(IProjectMaterialAcceptanceBatchService projectMaterialAcceptanceBatchService,
                                                        IProjectMaterialAcceptanceService projectMaterialAcceptanceService,
                                                        IProjectMaterialAcceptanceReviewService projectMaterialAcceptanceReviewService,
                                                        IProjectMaterialAcceptanceReviewModeService projectMaterialAcceptanceReviewModeService,
                                                        IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService,
                                                        IProjectMaterialAcceptanceReviewUserFileService projectMaterialAcceptanceReviewUserFileService,
                                                        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser,
                                                        ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile,
                                                        IUserService userService,
                                                        IProjectMaterialService projectMaterialService,
                                                        IProjectMaterialBusinessService projectMaterialBusinessService,
                                                        IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService,
                                                        IProjectMaterialBrandPublicService projectMaterialBrandPublicService) {
        this.projectMaterialAcceptanceBatchService = projectMaterialAcceptanceBatchService;
        this.projectMaterialAcceptanceService = projectMaterialAcceptanceService;
        this.projectMaterialAcceptanceReviewService = projectMaterialAcceptanceReviewService;
        this.projectMaterialAcceptanceReviewModeService = projectMaterialAcceptanceReviewModeService;
        this.projectMaterialAcceptanceReviewUserService = projectMaterialAcceptanceReviewUserService;
        this.projectMaterialAcceptanceReviewUserFileService = projectMaterialAcceptanceReviewUserFileService;
        this.projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewUser;
        this.projectMaterialAcceptanceReviewUserFile = projectMaterialAcceptanceReviewUserFile;
        this.userService = userService;
        this.projectMaterialBusinessService = projectMaterialBusinessService;
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceView> getPageView(int pageNo,
                                                           int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPage(pageNo,
                                                                                                                 pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceView> getPageViewByUserId(String userId,
                                                                   int pageNo,
                                                                   int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPageByUserId(
                userId, pageNo, pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    @Override
    public ProjectMaterialAcceptanceReviewUserView getViewByprojectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewUserService.getById(
                projectMaterialAcceptanceReviewUserId);
        if (projectMaterialAcceptanceReviewUser == null) return null;
        ProjectMaterialAcceptanceReviewUserView projectMaterialAcceptanceReviewUserView = new ProjectMaterialAcceptanceReviewUserView();
        projectMaterialAcceptanceReviewUserView.setProjectMaterialAcceptanceReviewUser(projectMaterialAcceptanceReviewUser);
        projectMaterialAcceptanceReviewUserView.setProjectMaterialAcceptanceReview(
                projectMaterialAcceptanceReviewService.getById(projectMaterialAcceptanceReviewUser.getProjectMaterialAcceptanceReviewId()));
        projectMaterialAcceptanceReviewUserView.setUser(userService.getById(projectMaterialAcceptanceReviewUser.getUserId()));
        projectMaterialAcceptanceReviewUserView.setProjectMaterialAcceptanceReviewUserFileList(
                projectMaterialAcceptanceReviewUserFileService.getByProjectMaterialAcceptanceReviewUserId(projectMaterialAcceptanceReviewUserId
                )
        );
        return projectMaterialAcceptanceReviewUserView;
    }

    @Override
    public Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                             int pageNo,
                                                                                             int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPageByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, pageNo, pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialAcceptanceReviewUserView> getViewPageByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId, Integer pageNo, Integer pageSize) {
        Page<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserPage= projectMaterialAcceptanceReviewUserService.getViewPageByProjectMaterialAcceptanceReviewModeId(
                projectMaterialAcceptanceReviewModeId, pageNo, pageSize);
        return convertProjectMaterialAcceptanceUserPage2PageView(
                projectMaterialAcceptanceReviewUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialId(String projectMaterialId,
                                                                              int pageNo,
                                                                              int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPageByProjectMaterialId(
                projectMaterialId,
                pageNo, pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPageByProjectMaterialBrandPrivateId(
                projectMaterialBrandPrivateId, pageNo, pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                                         int pageNo,
                                                                                         int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPageByProjectMaterialBrandPublicId(
                projectMaterialBrandPublicId, pageNo, pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                                                      int pageNo,
                                                                                                                      int pageSize) {
        Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage = projectMaterialAcceptanceService.getPageByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, Page.getStartOfPage(pageNo, pageSize), pageSize);
        return convertProjectMaterialAcceptancePage2PageView(projectMaterialAcceptancePage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialAcceptanceView getProjectMaterialAcceptanceViewByProjectMaterialAcceptanceId(String id) {
        ProjectMaterialAcceptance projectMaterialAcceptance = projectMaterialAcceptanceService.getById(id);
        if (projectMaterialAcceptance == null) return null;
        ProjectMaterialAcceptanceView projectMaterialAcceptanceView = new ProjectMaterialAcceptanceView();
        projectMaterialAcceptanceView.setProjectMaterialAcceptance(projectMaterialAcceptance);
        projectMaterialAcceptanceView.setUser(userService.getById(projectMaterialAcceptance.getUserId()));
        projectMaterialAcceptanceView.setProjectMaterialView(
                projectMaterialBusinessService.getViewById(projectMaterialAcceptance.getProjectMaterialId()));
        projectMaterialAcceptanceView.setProjectMaterialBrandPrivateView(
                projectMaterialBrandPrivateService.getViewById(
                        projectMaterialAcceptance.getProjectMaterialBrandPrivateId()));
        projectMaterialAcceptanceView.setProjectMaterialBrandPublicView(projectMaterialBrandPublicService.getViewById(
                projectMaterialAcceptance.getProjectMaterialBrandPublicId()));
        return projectMaterialAcceptanceView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialAcceptancePage 页面对象
     */
    private Page<ProjectMaterialAcceptanceView> convertProjectMaterialAcceptancePage2PageView(Page<ProjectMaterialAcceptance> projectMaterialAcceptancePage,
                                                                                              int pageNo,
                                                                                              int pageSize) {
        if (projectMaterialAcceptancePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceView> list = new ArrayList<>();
        for (ProjectMaterialAcceptance projectMaterialAcceptance : projectMaterialAcceptancePage.getResult()) {
            ProjectMaterialAcceptanceView projectMaterialAcceptanceView = getProjectMaterialAcceptanceViewByProjectMaterialAcceptanceId(
                    projectMaterialAcceptance.getId());
            if (projectMaterialAcceptanceView != null) list.add(projectMaterialAcceptanceView);
        }
        return new Page<>(startIndex, projectMaterialAcceptancePage.getTotalCount(), pageSize, list);
    }
    private Page<ProjectMaterialAcceptanceReviewUserView> convertProjectMaterialAcceptanceUserPage2PageView(Page<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserPage,
                                                                                                int pageNo,
                                                                                                int pageSize){
        if(projectMaterialAcceptanceReviewUserPage == null) return null;
        int startIndex=Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUserView> list= convertProjectMaterialAcceptanceUserListPage2ListPageView(
                projectMaterialAcceptanceReviewUserPage.getResult());
        if (list == null || list.isEmpty()) return null;
        return new Page<>(startIndex, projectMaterialAcceptanceReviewUserPage.getTotalCount(), pageSize, list);

    }
    private List<ProjectMaterialAcceptanceReviewUserView> convertProjectMaterialAcceptanceUserListPage2ListPageView(
            List<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserList){
        if (projectMaterialAcceptanceReviewUserList == null || projectMaterialAcceptanceReviewUserList.isEmpty()) return null;
        List<ProjectMaterialAcceptanceReviewUserView> list=new ArrayList<>();
        for (ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser : projectMaterialAcceptanceReviewUserList){
            ProjectMaterialAcceptanceReviewUserView projectMaterialAcceptanceReviewUserView = getViewByprojectMaterialAcceptanceReviewUserId(
                    projectMaterialAcceptanceReviewUser.getId());
            if (projectMaterialAcceptanceReviewUserView != null) list.add(projectMaterialAcceptanceReviewUserView);
        }

         return list;
    }
}
