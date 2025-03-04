package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewModeView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceReviewModeRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewModeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialAcceptanceReviewModeServiceImpl implements IProjectMaterialAcceptanceReviewModeService {
    private final IProjectMaterialAcceptanceReviewModeRepository projectMaterialAcceptanceReviewModeRepository;

    public ProjectMaterialAcceptanceReviewModeServiceImpl(IProjectMaterialAcceptanceReviewModeRepository projectMaterialAcceptanceReviewModeRepository) {
        this.projectMaterialAcceptanceReviewModeRepository = projectMaterialAcceptanceReviewModeRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return projectMaterialAcceptanceReviewModeRepository.add(projectMaterialAcceptanceReviewMode);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return projectMaterialAcceptanceReviewModeRepository.delete(projectMaterialAcceptanceReviewMode);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return projectMaterialAcceptanceReviewModeRepository.update(projectMaterialAcceptanceReviewMode);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialAcceptanceReviewModeRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectMaterialAcceptanceReviewModeRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectMaterialAcceptanceBatchId删除记录
     *
     * @param projectMaterialAcceptanceBatchId
     */
    @Override
    public int deleteByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        return projectMaterialAcceptanceReviewModeRepository.deleteByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialAcceptanceReviewModeRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectMaterialAcceptanceReviewModeRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectMaterialAcceptanceBatchId得到数量
     *
     * @param projectMaterialAcceptanceBatchId
     */
    @Override
    public int getCountByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        return projectMaterialAcceptanceReviewModeRepository.getCountByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId);
    }

    /**
     * 根据id得到ProjectMaterialAcceptanceReviewMode
     *
     * @param id
     */
    @Override
    public ProjectMaterialAcceptanceReviewMode getById(String id) {
        return projectMaterialAcceptanceReviewModeRepository.getById(id);
    }

    /**
     * 根据userId得到ProjectMaterialAcceptanceReviewMode
     *
     * @param userId
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewMode> getByUserId(String userId) {
        return projectMaterialAcceptanceReviewModeRepository.getByUserId(userId);
    }

    /**
     * 根据projectMaterialAcceptanceBatchId得到ProjectMaterialAcceptanceReviewMode
     *
     * @param projectMaterialAcceptanceBatchId
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewMode> getByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        return projectMaterialAcceptanceReviewModeRepository.getByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewMode> getPage(int pageNo,
                                                             int pageSize) {
        return projectMaterialAcceptanceReviewModeRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewMode> getPageByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return projectMaterialAcceptanceReviewModeRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceBatchId
     * @param pageNo                           页号，从1开始
     * @param pageSize                         每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewMode> getPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                               int pageNo,
                                                                                               int pageSize) {
        return projectMaterialAcceptanceReviewModeRepository.getPageByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewModeView> getPageView(int pageNo,
                                                                     int pageSize) {
        Page<ProjectMaterialAcceptanceReviewMode> projectMaterialAcceptanceReviewModePage = getPage(pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewModePage2PageView(projectMaterialAcceptanceReviewModePage, pageNo,
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
    public Page<ProjectMaterialAcceptanceReviewModeView> getPageViewByUserId(String userId,
                                                                             int pageNo,
                                                                             int pageSize) {
        Page<ProjectMaterialAcceptanceReviewMode> projectMaterialAcceptanceReviewModePage = getPageByUserId(userId,
                                                                                                            pageNo,
                                                                                                            pageSize);
        return convertProjectMaterialAcceptanceReviewModePage2PageView(projectMaterialAcceptanceReviewModePage, pageNo,
                                                                       pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialAcceptanceBatchId
     * @param pageNo                           页号，从1开始
     * @param pageSize                         每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewModeView> getPageViewByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                                       int pageNo,
                                                                                                       int pageSize) {
        Page<ProjectMaterialAcceptanceReviewMode> projectMaterialAcceptanceReviewModePage = getPageByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewModePage2PageView(projectMaterialAcceptanceReviewModePage, pageNo,
                                                                       pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialAcceptanceReviewModeView getProjectMaterialAcceptanceReviewModeViewByProjectMaterialAcceptanceReviewModeId(String id) {
        ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode = getById(id);
        if (projectMaterialAcceptanceReviewMode == null) return null;
        ProjectMaterialAcceptanceReviewModeView projectMaterialAcceptanceReviewModeView = new ProjectMaterialAcceptanceReviewModeView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialAcceptanceReviewModePage 页面对象
     */
    private Page<ProjectMaterialAcceptanceReviewModeView> convertProjectMaterialAcceptanceReviewModePage2PageView(Page<ProjectMaterialAcceptanceReviewMode> projectMaterialAcceptanceReviewModePage,
                                                                                                                  int pageNo,
                                                                                                                  int pageSize) {
        if (projectMaterialAcceptanceReviewModePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewModeView> list = new ArrayList<>();
        for (ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode : projectMaterialAcceptanceReviewModePage.getResult()) {
            ProjectMaterialAcceptanceReviewModeView projectMaterialAcceptanceReviewModeView = getProjectMaterialAcceptanceReviewModeViewByProjectMaterialAcceptanceReviewModeId(
                    projectMaterialAcceptanceReviewMode.getId());
            if (projectMaterialAcceptanceReviewModeView != null) list.add(projectMaterialAcceptanceReviewModeView);
        }
        return new Page<>(startIndex, projectMaterialAcceptanceReviewModePage.getTotalCount(), pageSize, list);
    }

}