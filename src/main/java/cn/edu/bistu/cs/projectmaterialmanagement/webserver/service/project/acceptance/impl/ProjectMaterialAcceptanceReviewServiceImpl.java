package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialAcceptanceReviewServiceImpl implements IProjectMaterialAcceptanceReviewService {
    private final IProjectMaterialAcceptanceReviewRepository projectMaterialAcceptanceReviewRepository;

    public ProjectMaterialAcceptanceReviewServiceImpl(IProjectMaterialAcceptanceReviewRepository projectMaterialAcceptanceReviewRepository) {
        this.projectMaterialAcceptanceReviewRepository = projectMaterialAcceptanceReviewRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return projectMaterialAcceptanceReviewRepository.add(projectMaterialAcceptanceReview);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return projectMaterialAcceptanceReviewRepository.delete(projectMaterialAcceptanceReview);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return projectMaterialAcceptanceReviewRepository.update(projectMaterialAcceptanceReview);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialAcceptanceReviewRepository.deleteById(id);
    }

    /**
     * 根据projectMaterialReportId删除记录
     *
     * @param projectMaterialAcceptanceModeId
     */
    @Override
    public int deleteByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        return projectMaterialAcceptanceReviewRepository.deleteByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialAcceptanceReviewRepository.getCount();
    }

    /**
     * 根据projectMaterialReportId得到数量
     *
     * @param projectMaterialAcceptanceModeId
     */
    @Override
    public int getCountByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        return projectMaterialAcceptanceReviewRepository.getCountByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId);
    }

    @Override
    public List<ProjectMaterialAcceptanceReview> getByMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId) {
        return projectMaterialAcceptanceReviewRepository.getByProjectMaterialAcceptanceModeId(projectMaterialAcceptanceReviewModeId);
    }

    /**
     * 根据id得到ProjectMaterialAcceptanceReview
     *
     * @param id
     */
    @Override
    public ProjectMaterialAcceptanceReview getById(String id) {
        return projectMaterialAcceptanceReviewRepository.getById(id);
    }

    /**
     * 根据projectMaterialReportId得到ProjectMaterialAcceptanceReview
     *
     * @param projectMaterialAcceptanceModeId
     */
    @Override
    public List<ProjectMaterialAcceptanceReview> getByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        return projectMaterialAcceptanceReviewRepository.getByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReview> getPage(int pageNo,
                                                         int pageSize) {
        return projectMaterialAcceptanceReviewRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceModeId
     * @param pageNo                          页号，从1开始
     * @param pageSize                        每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReview> getPageByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        return projectMaterialAcceptanceReviewRepository.getPageByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId,
                pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewView> getPageView(int pageNo,
                                                                 int pageSize) {
        Page<ProjectMaterialAcceptanceReview> projectMaterialAcceptanceReviewPage = getPage(pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewPage2PageView(projectMaterialAcceptanceReviewPage, pageNo,
                                                                   pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialAcceptanceModeId
     * @param pageNo                          页号，从1开始
     * @param pageSize                        每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewView> getPageViewByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                                  int pageNo,
                                                                                                  int pageSize) {
        Page<ProjectMaterialAcceptanceReview> projectMaterialAcceptanceReviewPage = getPageByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId, pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewPage2PageView(projectMaterialAcceptanceReviewPage, pageNo,
                                                                   pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialAcceptanceReviewView getProjectMaterialAcceptanceReviewViewByProjectMaterialAcceptanceReviewId(String id) {
        ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = getById(id);
        if (projectMaterialAcceptanceReview == null) return null;
        ProjectMaterialAcceptanceReviewView projectMaterialAcceptanceReviewView = new ProjectMaterialAcceptanceReviewView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialAcceptanceReviewPage 页面对象
     */
    private Page<ProjectMaterialAcceptanceReviewView> convertProjectMaterialAcceptanceReviewPage2PageView(Page<ProjectMaterialAcceptanceReview> projectMaterialAcceptanceReviewPage,
                                                                                                          int pageNo,
                                                                                                          int pageSize) {
        if (projectMaterialAcceptanceReviewPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewView> list = new ArrayList<>();
        for (ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview : projectMaterialAcceptanceReviewPage.getResult()) {
            ProjectMaterialAcceptanceReviewView projectMaterialAcceptanceReviewView = getProjectMaterialAcceptanceReviewViewByProjectMaterialAcceptanceReviewId(
                    projectMaterialAcceptanceReview.getId());
            if (projectMaterialAcceptanceReviewView != null) list.add(projectMaterialAcceptanceReviewView);
        }
        return new Page<>(startIndex, projectMaterialAcceptanceReviewPage.getTotalCount(), pageSize, list);
    }

}