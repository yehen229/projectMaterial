package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryContentView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectOpHistoryContentRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectOpHistoryContentServiceImpl implements IProjectOpHistoryContentService {
    private final IProjectOpHistoryContentRepository projectOpHistoryContentRepository;

    public ProjectOpHistoryContentServiceImpl(IProjectOpHistoryContentRepository projectOpHistoryContentRepository) {
        this.projectOpHistoryContentRepository = projectOpHistoryContentRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectOpHistoryContent projectOpHistoryContent) {
        return projectOpHistoryContentRepository.add(projectOpHistoryContent);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectOpHistoryContent projectOpHistoryContent) {
        return projectOpHistoryContentRepository.delete(projectOpHistoryContent);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectOpHistoryContent projectOpHistoryContent) {
        return projectOpHistoryContentRepository.update(projectOpHistoryContent);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectOpHistoryContentRepository.deleteById(id);
    }

    /**
     * 根据projectOpHistoryId删除记录
     *
     * @param projectOpHistoryId
     */
    @Override
    public int deleteByProjectOpHistoryId(String projectOpHistoryId) {
        return projectOpHistoryContentRepository.deleteByProjectOpHistoryId(projectOpHistoryId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectOpHistoryContentRepository.getCount();
    }

    /**
     * 根据projectOpHistoryId得到数量
     *
     * @param projectOpHistoryId
     */
    @Override
    public int getCountByProjectOpHistoryId(String projectOpHistoryId) {
        return projectOpHistoryContentRepository.getCountByProjectOpHistoryId(projectOpHistoryId);
    }

    /**
     * 根据id得到ProjectOpHistoryContent
     *
     * @param id
     */
    @Override
    public ProjectOpHistoryContent getById(String id) {
        return projectOpHistoryContentRepository.getById(id);
    }

    /**
     * 根据projectOpHistoryId得到ProjectOpHistoryContent
     *
     * @param projectOpHistoryId
     */
    @Override
    public List<ProjectOpHistoryContent> getByProjectOpHistoryId(String projectOpHistoryId) {
        return projectOpHistoryContentRepository.getByProjectOpHistoryId(projectOpHistoryId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryContent> getPage(int pageNo,
                                                 int pageSize) {
        return projectOpHistoryContentRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectOpHistoryId
     * @param pageNo             页号，从1开始
     * @param pageSize           每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryContent> getPageByProjectOpHistoryId(String projectOpHistoryId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return projectOpHistoryContentRepository.getPageByProjectOpHistoryId(projectOpHistoryId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryContentView> getPageView(int pageNo,
                                                         int pageSize) {
        Page<ProjectOpHistoryContent> projectOpHistoryContentPage = getPage(pageNo, pageSize);
        return convertProjectOpHistoryContentPage2PageView(projectOpHistoryContentPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectOpHistoryId
     * @param pageNo             页号，从1开始
     * @param pageSize           每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryContentView> getPageViewByProjectOpHistoryId(String projectOpHistoryId,
                                                                             int pageNo,
                                                                             int pageSize) {
        Page<ProjectOpHistoryContent> projectOpHistoryContentPage = getPageByProjectOpHistoryId(projectOpHistoryId,
                                                                                                pageNo, pageSize);
        return convertProjectOpHistoryContentPage2PageView(projectOpHistoryContentPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectOpHistoryContentView getProjectOpHistoryContentViewByProjectOpHistoryContentId(String id) {
        ProjectOpHistoryContent projectOpHistoryContent = getById(id);
        if (projectOpHistoryContent == null) return null;
        ProjectOpHistoryContentView projectOpHistoryContentView = new ProjectOpHistoryContentView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectOpHistoryContentPage 页面对象
     */
    private Page<ProjectOpHistoryContentView> convertProjectOpHistoryContentPage2PageView(Page<ProjectOpHistoryContent> projectOpHistoryContentPage,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        if (projectOpHistoryContentPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistoryContentView> list = new ArrayList<>();
        for (ProjectOpHistoryContent projectOpHistoryContent : projectOpHistoryContentPage.getResult()) {
            ProjectOpHistoryContentView projectOpHistoryContentView = getProjectOpHistoryContentViewByProjectOpHistoryContentId(
                    projectOpHistoryContent.getId());
            if (projectOpHistoryContentView != null) list.add(projectOpHistoryContentView);
        }
        return new Page<>(startIndex, projectOpHistoryContentPage.getTotalCount(), pageSize, list);
    }

}