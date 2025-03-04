package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectAppearanceReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectAppearanceReviewModeService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectAppearanceReviewService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectAppearanceReviewServiceImpl implements IProjectAppearanceReviewService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectAppearanceReviewServiceImpl.class);

    private final IProjectAppearanceReviewRepository projectAppearanceReviewRepository;
    private final IProjectAppearanceReviewModeService projectAppearanceReviewModeService;
    private final IUseMaterialService useMaterialService;

    public ProjectAppearanceReviewServiceImpl(IProjectAppearanceReviewRepository projectAppearanceReviewRepository,
                                              IProjectAppearanceReviewModeService projectAppearanceReviewModeService,
                                              IUseMaterialService useMaterialService) {
        this.projectAppearanceReviewRepository = projectAppearanceReviewRepository;
        this.projectAppearanceReviewModeService = projectAppearanceReviewModeService;
        this.useMaterialService = useMaterialService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectAppearanceReview projectAppearanceReview) {
        return projectAppearanceReviewRepository.add(projectAppearanceReview);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectAppearanceReview projectAppearanceReview) {
        return projectAppearanceReviewRepository.delete(projectAppearanceReview);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectAppearanceReview projectAppearanceReview) {
        return projectAppearanceReviewRepository.update(projectAppearanceReview);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectAppearanceReviewRepository.deleteById(id);
    }

    @Override
    public int deleteByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        return projectAppearanceReviewRepository.deleteByProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
    }


    /**
     * 根据useMaterialId删除记录
     *
     * @param useMaterialId
     */
    @Override
    public int deleteByUseMaterialId(String useMaterialId) {
        return projectAppearanceReviewRepository.deleteByUseMaterialId(useMaterialId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectAppearanceReviewRepository.getCount();
    }

    @Override
    public int getCountByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        return projectAppearanceReviewRepository.getCountByProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
    }


    /**
     * 根据useMaterialId得到数量
     *
     * @param useMaterialId
     */
    @Override
    public int getCountByUseMaterialId(String useMaterialId) {
        return projectAppearanceReviewRepository.getCountByUseMaterialId(useMaterialId);
    }

    /**
     * 根据id得到ProjectAppearanceReview
     *
     * @param id
     */
    @Override
    public ProjectAppearanceReview getById(String id) {
        return projectAppearanceReviewRepository.getById(id);
    }

    @Override
    public ProjectAppearanceReviewView getViewById(String id) {
        ProjectAppearanceReview projectAppearanceReview = getById(id);
        if (projectAppearanceReview == null) return null;
        ProjectAppearanceReviewView projectAppearanceReviewView = new ProjectAppearanceReviewView();
        projectAppearanceReviewView.setProjectAppearanceReview(projectAppearanceReview);
        projectAppearanceReviewView.setProjectAppearanceReviewMode(
                projectAppearanceReviewModeService.getById(
                        projectAppearanceReview.getProjectAppearanceReviewModeId()));
        projectAppearanceReviewView.setUseMaterial(getUseMaterialById(id));
        return projectAppearanceReviewView;

    }


    @Override
    public UseMaterial getUseMaterialById(String id) {
        ProjectAppearanceReview projectAppearanceReview = getById(id);
        if (projectAppearanceReview == null) return null;
        return useMaterialService.getById(projectAppearanceReview.getUseMaterialId());
    }

    @Override
    public List<ProjectAppearanceReview> getByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        return projectAppearanceReviewRepository.getByProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
    }


    /**
     * 根据useMaterialId得到ProjectAppearanceReview
     *
     * @param useMaterialId
     */
    @Override
    public List<ProjectAppearanceReview> getByUseMaterialId(String useMaterialId) {
        return projectAppearanceReviewRepository.getByUseMaterialId(useMaterialId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReview> getPage(int pageNo,
                                                 int pageSize) {
        return projectAppearanceReviewRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ProjectAppearanceReview> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                int pageNo,
                                                                                int pageSize) {
        return projectAppearanceReviewRepository.getPageByProjectAppearanceReviewModeId(projectAppearanceReviewModeId,
                                                                                        pageNo, pageSize);
    }


    /**
     * 获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReview> getPageByUseMaterialId(String useMaterialId,
                                                                int pageNo,
                                                                int pageSize) {
        return projectAppearanceReviewRepository.getPageByUseMaterialId(useMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewView> getPageView(int pageNo,
                                                         int pageSize) {
        Page<ProjectAppearanceReview> projectAppearanceReviewPage = getPage(pageNo, pageSize);
        return convertProjectAppearanceReviewPage2PageView(projectAppearanceReviewPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectAppearanceReviewView> getPageViewByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                        int pageNo,
                                                                                        int pageSize) {
        Page<ProjectAppearanceReview> projectAppearanceReviewPage = getPageByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId, pageNo,
                pageSize);
        return convertProjectAppearanceReviewPage2PageView(projectAppearanceReviewPage, pageNo, pageSize);
    }


    /**
     * 获得指定页面视图数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewView> getPageViewByUseMaterialId(String useMaterialId,
                                                                        int pageNo,
                                                                        int pageSize) {
        Page<ProjectAppearanceReview> projectAppearanceReviewPage = getPageByUseMaterialId(useMaterialId, pageNo,
                                                                                           pageSize);
        return convertProjectAppearanceReviewPage2PageView(projectAppearanceReviewPage, pageNo, pageSize);
    }


    /**
     * 将页面转换为视图页面
     *
     * @param projectAppearanceReviewPage 页面对象
     */
    private Page<ProjectAppearanceReviewView> convertProjectAppearanceReviewPage2PageView(Page<ProjectAppearanceReview> projectAppearanceReviewPage,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        if (projectAppearanceReviewPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewView> list = new ArrayList<>();
        for (ProjectAppearanceReview projectAppearanceReview : projectAppearanceReviewPage.getResult()) {
            ProjectAppearanceReviewView projectAppearanceReviewView = getViewById(
                    projectAppearanceReview.getId());
            if (projectAppearanceReviewView != null) list.add(projectAppearanceReviewView);
        }
        return new Page<>(startIndex, projectAppearanceReviewPage.getTotalCount(), pageSize, list);
    }

}