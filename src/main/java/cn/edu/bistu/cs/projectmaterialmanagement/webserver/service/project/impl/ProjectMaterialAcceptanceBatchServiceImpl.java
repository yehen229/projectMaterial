package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceBatchView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialAcceptanceBatchRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialAcceptanceBatchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialAcceptanceBatchServiceImpl implements IProjectMaterialAcceptanceBatchService {
    private final IProjectMaterialAcceptanceBatchRepository projectMaterialAcceptanceBatchRepository;

    public ProjectMaterialAcceptanceBatchServiceImpl(IProjectMaterialAcceptanceBatchRepository projectMaterialAcceptanceBatchRepository) {
        this.projectMaterialAcceptanceBatchRepository = projectMaterialAcceptanceBatchRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return projectMaterialAcceptanceBatchRepository.add(projectMaterialAcceptanceBatch);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return projectMaterialAcceptanceBatchRepository.delete(projectMaterialAcceptanceBatch);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return projectMaterialAcceptanceBatchRepository.update(projectMaterialAcceptanceBatch);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialAcceptanceBatchRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectMaterialAcceptanceBatchRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectMaterialAcceptanceBatchRepository.deleteByProjectId(projectId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialAcceptanceBatchRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectMaterialAcceptanceBatchRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectMaterialAcceptanceBatchRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据id得到ProjectMaterialAcceptanceBatch
     *
     * @param id
     */
    @Override
    public ProjectMaterialAcceptanceBatch getById(String id) {
        return projectMaterialAcceptanceBatchRepository.getById(id);
    }

    /**
     * 根据userId得到ProjectMaterialAcceptanceBatch
     *
     * @param userId
     */
    @Override
    public List<ProjectMaterialAcceptanceBatch> getByUserId(String userId) {
        return projectMaterialAcceptanceBatchRepository.getByUserId(userId);
    }

    /**
     * 根据projectId得到ProjectMaterialAcceptanceBatch
     *
     * @param projectId
     */
    @Override
    public List<ProjectMaterialAcceptanceBatch> getByProjectId(String projectId) {
        return projectMaterialAcceptanceBatchRepository.getByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatch> getPage(int pageNo,
                                                        int pageSize) {
        return projectMaterialAcceptanceBatchRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatch> getPageByUserId(String userId,
                                                                int pageNo,
                                                                int pageSize) {
        return projectMaterialAcceptanceBatchRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatch> getPageByProjectId(String projectId,
                                                                   int pageNo,
                                                                   int pageSize) {
        return projectMaterialAcceptanceBatchRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatchView> getPageView(int pageNo,
                                                                int pageSize) {
        Page<ProjectMaterialAcceptanceBatch> projectMaterialAcceptanceBatchPage = getPage(pageNo, pageSize);
        return convertProjectMaterialAcceptanceBatchPage2PageView(projectMaterialAcceptanceBatchPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatchView> getPageViewByUserId(String userId,
                                                                        int pageNo,
                                                                        int pageSize) {
        Page<ProjectMaterialAcceptanceBatch> projectMaterialAcceptanceBatchPage = getPageByUserId(userId, pageNo,
                                                                                                  pageSize);
        return convertProjectMaterialAcceptanceBatchPage2PageView(projectMaterialAcceptanceBatchPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatchView> getPageViewByProjectId(String projectId,
                                                                           int pageNo,
                                                                           int pageSize) {
        Page<ProjectMaterialAcceptanceBatch> projectMaterialAcceptanceBatchPage = getPageByProjectId(projectId, pageNo,
                                                                                                     pageSize);
        return convertProjectMaterialAcceptanceBatchPage2PageView(projectMaterialAcceptanceBatchPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialAcceptanceBatchView getProjectMaterialAcceptanceBatchViewByProjectMaterialAcceptanceBatchId(String id) {
        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = getById(id);
        if (projectMaterialAcceptanceBatch == null) return null;
        ProjectMaterialAcceptanceBatchView projectMaterialAcceptanceBatchView = new ProjectMaterialAcceptanceBatchView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialAcceptanceBatchPage 页面对象
     */
    private Page<ProjectMaterialAcceptanceBatchView> convertProjectMaterialAcceptanceBatchPage2PageView(Page<ProjectMaterialAcceptanceBatch> projectMaterialAcceptanceBatchPage,
                                                                                                        int pageNo,
                                                                                                        int pageSize) {
        if (projectMaterialAcceptanceBatchPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceBatchView> list = new ArrayList<>();
        for (ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch : projectMaterialAcceptanceBatchPage.getResult()) {
            ProjectMaterialAcceptanceBatchView projectMaterialAcceptanceBatchView = getProjectMaterialAcceptanceBatchViewByProjectMaterialAcceptanceBatchId(
                    projectMaterialAcceptanceBatch.getId());
            if (projectMaterialAcceptanceBatchView != null) list.add(projectMaterialAcceptanceBatchView);
        }
        return new Page<>(startIndex, projectMaterialAcceptanceBatchPage.getTotalCount(), pageSize, list);
    }

}