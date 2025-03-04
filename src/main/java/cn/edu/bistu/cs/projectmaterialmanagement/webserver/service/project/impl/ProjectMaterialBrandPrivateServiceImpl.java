package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivate;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivateView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandPrivateRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialBrandPrivateService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialBrandPrivateServiceImpl implements IProjectMaterialBrandPrivateService {
    private final IProjectMaterialBrandPrivateRepository projectMaterialBrandPrivateRepository;
    private final IProjectMaterialService projectMaterialService;
    private final IProjectBrandService projectBrandService;

    public ProjectMaterialBrandPrivateServiceImpl(IProjectMaterialBrandPrivateRepository projectMaterialBrandPrivateRepository,
                                                  IProjectMaterialService projectMaterialService,
                                                  IProjectBrandService projectBrandService) {
        this.projectMaterialBrandPrivateRepository = projectMaterialBrandPrivateRepository;
        this.projectMaterialService = projectMaterialService;
        this.projectBrandService = projectBrandService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return projectMaterialBrandPrivateRepository.add(projectMaterialBrandPrivate);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return projectMaterialBrandPrivateRepository.delete(projectMaterialBrandPrivate);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return projectMaterialBrandPrivateRepository.update(projectMaterialBrandPrivate);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialBrandPrivateRepository.deleteById(id);
    }

    /**
     * 根据projectMaterialId删除记录
     *
     * @param projectMaterialId
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return projectMaterialBrandPrivateRepository.deleteByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据projectBrandId删除记录
     *
     * @param projectBrandId
     */
    @Override
    public int deleteByProjectBrandId(String projectBrandId) {
        return projectMaterialBrandPrivateRepository.deleteByProjectBrandId(projectBrandId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialBrandPrivateRepository.getCount();
    }

    /**
     * 根据projectMaterialId得到数量
     *
     * @param projectMaterialId
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        return projectMaterialBrandPrivateRepository.getCountByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据projectBrandId得到数量
     *
     * @param projectBrandId
     */
    @Override
    public int getCountByProjectBrandId(String projectBrandId) {
        return projectMaterialBrandPrivateRepository.getCountByProjectBrandId(projectBrandId);
    }

    /**
     * 根据id得到ProjectMaterialBrandPrivate
     *
     * @param id
     */
    @Override
    public ProjectMaterialBrandPrivate getById(String id) {
        return projectMaterialBrandPrivateRepository.getById(id);
    }

    @Override
    public ProjectMaterialBrandPrivate getByProjectMaterialIdAndProjectBrandId(String projectMaterialId,
                                                                               String projectBrandId) {
        return projectMaterialBrandPrivateRepository.getByProjectMaterialIdAndProjectBrandId(projectMaterialId,
                                                                                             projectBrandId);
    }

    @Override
    public ProjectMaterialBrandPrivateView getViewById(String id) {
        return getProjectMaterialBrandPrivateViewByProjectMaterialBrandPrivateId(id);
    }

    /**
     * 根据projectMaterialId得到ProjectMaterialBrandPrivate
     *
     * @param projectMaterialId
     */
    @Override
    public List<ProjectMaterialBrandPrivate> getByProjectMaterialId(String projectMaterialId) {
        return projectMaterialBrandPrivateRepository.getByProjectMaterialId(projectMaterialId);
    }

    @Override
    public List<ProjectMaterialBrandPrivateView> getViewListByProjectMaterialId(String projectMaterialId) {
        List<ProjectMaterialBrandPrivate> projectMaterialBrandPrivates = projectMaterialBrandPrivateRepository.getByProjectMaterialId(
                projectMaterialId);
        return convertProjectMaterialBrandPrivateList2ListView(projectMaterialBrandPrivates);
    }

    /**
     * 根据projectBrandId得到ProjectMaterialBrandPrivate
     *
     * @param projectBrandId
     */
    @Override
    public List<ProjectMaterialBrandPrivate> getByProjectBrandId(String projectBrandId) {
        return projectMaterialBrandPrivateRepository.getByProjectBrandId(projectBrandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivate> getPage(int pageNo,
                                                     int pageSize) {
        return projectMaterialBrandPrivateRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivate> getPageByProjectMaterialId(String projectMaterialId,
                                                                        int pageNo,
                                                                        int pageSize) {
        return projectMaterialBrandPrivateRepository.getPageByProjectMaterialId(projectMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectBrandId
     * @param pageNo         页号，从1开始
     * @param pageSize       每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivate> getPageByProjectBrandId(String projectBrandId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return projectMaterialBrandPrivateRepository.getPageByProjectBrandId(projectBrandId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivateView> getPageView(int pageNo,
                                                             int pageSize) {
        Page<ProjectMaterialBrandPrivate> projectMaterialBrandPrivatePage = getPage(pageNo, pageSize);
        return convertProjectMaterialBrandPrivatePage2PageView(projectMaterialBrandPrivatePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivateView> getPageViewByProjectMaterialId(String projectMaterialId,
                                                                                int pageNo,
                                                                                int pageSize) {
        Page<ProjectMaterialBrandPrivate> projectMaterialBrandPrivatePage = getPageByProjectMaterialId(
                projectMaterialId, pageNo, pageSize);
        return convertProjectMaterialBrandPrivatePage2PageView(projectMaterialBrandPrivatePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectBrandId
     * @param pageNo         页号，从1开始
     * @param pageSize       每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivateView> getPageViewByProjectBrandId(String projectBrandId,
                                                                             int pageNo,
                                                                             int pageSize) {
        Page<ProjectMaterialBrandPrivate> projectMaterialBrandPrivatePage = getPageByProjectBrandId(projectBrandId,
                                                                                                    pageNo, pageSize);
        return convertProjectMaterialBrandPrivatePage2PageView(projectMaterialBrandPrivatePage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialBrandPrivateView getProjectMaterialBrandPrivateViewByProjectMaterialBrandPrivateId(String id) {
        ProjectMaterialBrandPrivate projectMaterialBrandPrivate = getById(id);
        if (projectMaterialBrandPrivate == null) return null;
        ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView = new ProjectMaterialBrandPrivateView();
        projectMaterialBrandPrivateView.setProjectBrandView(
                projectBrandService.getViewById(projectMaterialBrandPrivate.getProjectBrandId()));
        projectMaterialBrandPrivateView.setProjectMaterial(
                projectMaterialService.getById(projectMaterialBrandPrivate.getProjectMaterialId()));
        projectMaterialBrandPrivateView.setProjectMaterialBrandPrivate(projectMaterialBrandPrivate);

        return projectMaterialBrandPrivateView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialBrandPrivatePage 页面对象
     */
    private Page<ProjectMaterialBrandPrivateView> convertProjectMaterialBrandPrivatePage2PageView(Page<ProjectMaterialBrandPrivate> projectMaterialBrandPrivatePage,
                                                                                                  int pageNo,
                                                                                                  int pageSize) {
        if (projectMaterialBrandPrivatePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPrivateView> list = new ArrayList<>();
        for (ProjectMaterialBrandPrivate projectMaterialBrandPrivate : projectMaterialBrandPrivatePage.getResult()) {
            ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView = getProjectMaterialBrandPrivateViewByProjectMaterialBrandPrivateId(
                    projectMaterialBrandPrivate.getId());
            if (projectMaterialBrandPrivateView != null) list.add(projectMaterialBrandPrivateView);
        }
        return new Page<>(startIndex, projectMaterialBrandPrivatePage.getTotalCount(), pageSize, list);
    }

    private List<ProjectMaterialBrandPrivateView> convertProjectMaterialBrandPrivateList2ListView(List<ProjectMaterialBrandPrivate> projectMaterialBrandPrivateList) {
        if (projectMaterialBrandPrivateList == null) return null;

        List<ProjectMaterialBrandPrivateView> list = new ArrayList<>();
        for (ProjectMaterialBrandPrivate projectMaterialBrandPrivate : projectMaterialBrandPrivateList) {
            ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView = getProjectMaterialBrandPrivateViewByProjectMaterialBrandPrivateId(
                    projectMaterialBrandPrivate.getId());
            if (projectMaterialBrandPrivateView != null) list.add(projectMaterialBrandPrivateView);
        }
        return list;
    }

}