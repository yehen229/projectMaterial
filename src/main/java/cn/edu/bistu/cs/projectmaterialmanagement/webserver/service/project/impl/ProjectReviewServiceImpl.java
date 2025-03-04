package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectReviewService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectReviewServiceImpl implements IProjectReviewService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectReviewServiceImpl.class);

    private final IProjectReviewRepository projectReviewRepository;
    private final IProjectService projectService;

    public ProjectReviewServiceImpl(IProjectReviewRepository projectReviewRepository, IProjectService projectService) {
        this.projectReviewRepository = projectReviewRepository;
        this.projectService = projectService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectReview projectReview) {
        if (projectReview == null)
            return null;
        projectReview.setReviewDatetime(new Date());
        return projectReviewRepository.add(projectReview);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectReview projectReview) {
        return projectReviewRepository.delete(projectReview);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectReview projectReview) {
        return projectReviewRepository.update(projectReview);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectReviewRepository.deleteById(id);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectReviewRepository.deleteByProjectId(projectId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectReviewRepository.getCount();
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectReviewRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据id得到ProjectReview
     *
     * @param id
     */
    @Override
    public ProjectReview getById(String id) {
        return projectReviewRepository.getById(id);
    }

    @Override
    public ProjectReviewView getViewById(String id) {
        ProjectReview projectReview = getById(id);
        if (projectReview == null) return null;
        ProjectReviewView projectReviewView = new ProjectReviewView();
        projectReviewView.setProjectReview(projectReview);
        projectReviewView.setProject(projectService.getById(projectReview.getProjectId()));
        return projectReviewView;
    }

    /**
     * 根据projectId得到ProjectReview
     *
     * @param projectId
     */
    @Override
    public List<ProjectReview> getByProjectId(String projectId) {
        return projectReviewRepository.getByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReview> getPage(int pageNo, int pageSize) {
        return projectReviewRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectReview> getPageByProjectId(String projectId, int pageNo, int pageSize) {
        return projectReviewRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewView> getPageView(int pageNo, int pageSize) {
        Page<ProjectReview> projectReviewPage = getPage(pageNo, pageSize);
        return convertProjectReviewPage2PageView(projectReviewPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectReviewView> getPageViewByProjectId(String projectId, int pageNo, int pageSize) {
        Page<ProjectReview> projectReviewPage = getPageByProjectId(projectId, pageNo, pageSize);
        return convertProjectReviewPage2PageView(projectReviewPage, pageNo, pageSize);
    }


    /**
     * 将页面转换为视图页面
     *
     * @param projectReviewPage 页面对象
     */
    private Page<ProjectReviewView> convertProjectReviewPage2PageView(Page<ProjectReview> projectReviewPage, int pageNo, int pageSize) {
        if (projectReviewPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewView> list = new ArrayList<>();
        for (ProjectReview projectReview : projectReviewPage.getResult()) {
            ProjectReviewView projectReviewView = getViewById(projectReview.getId());
            if (projectReviewView != null) list.add(projectReviewView);
        }
        return new Page<>(startIndex, projectReviewPage.getTotalCount(), pageSize, list);
    }

}