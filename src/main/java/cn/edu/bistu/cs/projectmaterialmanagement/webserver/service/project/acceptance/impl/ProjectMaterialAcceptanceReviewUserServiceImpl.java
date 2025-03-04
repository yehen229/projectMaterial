package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceReviewUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialAcceptanceReviewUserServiceImpl implements IProjectMaterialAcceptanceReviewUserService {
    private final IProjectMaterialAcceptanceReviewUserRepository projectMaterialAcceptanceReviewUserRepository;

    public ProjectMaterialAcceptanceReviewUserServiceImpl(IProjectMaterialAcceptanceReviewUserRepository projectMaterialAcceptanceReviewUserRepository) {
        this.projectMaterialAcceptanceReviewUserRepository = projectMaterialAcceptanceReviewUserRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return projectMaterialAcceptanceReviewUserRepository.add(projectMaterialAcceptanceReviewUser);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return projectMaterialAcceptanceReviewUserRepository.delete(projectMaterialAcceptanceReviewUser);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return projectMaterialAcceptanceReviewUserRepository.update(projectMaterialAcceptanceReviewUser);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialAcceptanceReviewUserRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectMaterialAcceptanceReviewUserRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectMaterialAcceptanceReviewId删除记录
     *
     * @param projectMaterialAcceptanceReviewId
     */
    @Override
    public int deleteByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId) {
        return projectMaterialAcceptanceReviewUserRepository.deleteByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialAcceptanceReviewUserRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectMaterialAcceptanceReviewUserRepository.getCountByUserId(userId);
    }
    @Override
    public int getCountByProjectId(String projectId){
        return projectMaterialAcceptanceReviewUserRepository.getCountByProjectId(projectId);
    }
    @Override
    public int getCountByProjectIdAndResult(String projectId,int nReviewResult){
        return projectMaterialAcceptanceReviewUserRepository.getCountByProjectIdAndResult(projectId,nReviewResult);
    }

    /**
     * 根据projectMaterialAcceptanceReviewId得到数量
     *
     * @param projectMaterialAcceptanceReviewId
     */
    @Override
    public int getCountByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId) {
        return projectMaterialAcceptanceReviewUserRepository.getCountByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId);
    }

    /**
     * 根据id得到ProjectMaterialAcceptanceReviewUser
     *
     * @param id
     */
    @Override
    public ProjectMaterialAcceptanceReviewUser getById(String id) {
        return projectMaterialAcceptanceReviewUserRepository.getById(id);
    }

    @Override
    public ProjectMaterialAcceptanceReviewUser getByUserIdAndModeId(String userId,
                                                                    String projectMaterialAcceptanceBatchId,
                                                                    String projectMaterialAcceptanceReviewModeId) {
        return projectMaterialAcceptanceReviewUserRepository.getByUserIdAndModeId(userId,
                                                                                  projectMaterialAcceptanceBatchId,
                                                                                  projectMaterialAcceptanceReviewModeId);
    }

    /**
     * 根据userId得到ProjectMaterialAcceptanceReviewUser
     *
     * @param userId
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getByUserId(String userId) {
        return projectMaterialAcceptanceReviewUserRepository.getByUserId(userId);
    }

    /**
     * 根据projectMaterialAcceptanceReviewId得到ProjectMaterialAcceptanceReviewUser
     *
     * @param projectMaterialAcceptanceReviewId
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId) {
        return projectMaterialAcceptanceReviewUserRepository.getByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId);
    }

    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getByMaterialAcceptanceModeIdAndNotReviewed(String projectMaterialAcceptanceReviewModeId) {
        return projectMaterialAcceptanceReviewUserRepository.getByMaterialAcceptanceModeIdAndNotReviewed(projectMaterialAcceptanceReviewModeId,
                IProjectReviewUserService.PROJECT_REVIEW_RESULT_UNKNOWN);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getPage(int pageNo,
                                                             int pageSize) {
        return projectMaterialAcceptanceReviewUserRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getPageByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return projectMaterialAcceptanceReviewUserRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceReviewId
     * @param pageNo                            页号，从1开始
     * @param pageSize                          每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getPageByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                                int pageNo,
                                                                                                int pageSize) {
        return projectMaterialAcceptanceReviewUserRepository.getPageByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserView> getPageView(int pageNo,
                                                                     int pageSize) {
        Page<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserPage = getPage(pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewUserPage2PageView(projectMaterialAcceptanceReviewUserPage, pageNo,
                                                                       pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserView> getPageViewByUserId(String userId,
                                                                             int pageNo,
                                                                             int pageSize) {
        Page<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserPage = getPageByUserId(userId,
                                                                                                            pageNo,
                                                                                                            pageSize);
        return convertProjectMaterialAcceptanceReviewUserPage2PageView(projectMaterialAcceptanceReviewUserPage, pageNo,
                                                                       pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialAcceptanceReviewId
     * @param pageNo                            页号，从1开始
     * @param pageSize                          每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserView> getPageViewByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                                        int pageNo,
                                                                                                        int pageSize) {
        Page<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserPage = getPageByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId, pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewUserPage2PageView(projectMaterialAcceptanceReviewUserPage, pageNo,
                                                                       pageSize);
    }

    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getViewPageByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId, Integer pageNo, Integer pageSize) {
      return projectMaterialAcceptanceReviewUserRepository.getViewPageByProjectMaterialAcceptanceReviewModeId(projectMaterialAcceptanceReviewModeId, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialAcceptanceReviewUserView getProjectMaterialAcceptanceReviewUserViewByProjectMaterialAcceptanceReviewUserId(String id) {
        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = getById(id);
        if (projectMaterialAcceptanceReviewUser == null) return null;
        ProjectMaterialAcceptanceReviewUserView projectMaterialAcceptanceReviewUserView = new ProjectMaterialAcceptanceReviewUserView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialAcceptanceReviewUserPage 页面对象
     */
    private Page<ProjectMaterialAcceptanceReviewUserView> convertProjectMaterialAcceptanceReviewUserPage2PageView(Page<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserPage,
                                                                                                                  int pageNo,
                                                                                                                  int pageSize) {
        if (projectMaterialAcceptanceReviewUserPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUserView> list = new ArrayList<>();
        for (ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser : projectMaterialAcceptanceReviewUserPage.getResult()) {
            ProjectMaterialAcceptanceReviewUserView projectMaterialAcceptanceReviewUserView = getProjectMaterialAcceptanceReviewUserViewByProjectMaterialAcceptanceReviewUserId(
                    projectMaterialAcceptanceReviewUser.getId());
            if (projectMaterialAcceptanceReviewUserView != null) list.add(projectMaterialAcceptanceReviewUserView);
        }
        return new Page<>(startIndex, projectMaterialAcceptanceReviewUserPage.getTotalCount(), pageSize, list);
    }

}