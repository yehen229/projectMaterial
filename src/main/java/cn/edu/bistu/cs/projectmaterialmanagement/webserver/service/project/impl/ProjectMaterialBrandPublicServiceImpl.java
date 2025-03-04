package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublicView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandPublicRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialBrandPublicService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandPublicService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialBrandPublicServiceImpl implements IProjectMaterialBrandPublicService {
    private final IProjectMaterialBrandPublicRepository projectMaterialBrandPublicRepository;
    private final IBrandPublicService brandPublicService;
    private final IProjectMaterialService projectMaterialService;

    public ProjectMaterialBrandPublicServiceImpl(IProjectMaterialBrandPublicRepository projectMaterialBrandPublicRepository,
                                                 IBrandPublicService brandPublicService, IProjectMaterialService projectMaterialService) {
        this.projectMaterialBrandPublicRepository = projectMaterialBrandPublicRepository;
        this.brandPublicService = brandPublicService;

        this.projectMaterialService = projectMaterialService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return projectMaterialBrandPublicRepository.add(projectMaterialBrandPublic);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return projectMaterialBrandPublicRepository.delete(projectMaterialBrandPublic);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return projectMaterialBrandPublicRepository.update(projectMaterialBrandPublic);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialBrandPublicRepository.deleteById(id);
    }

    /**
     * 根据projectMaterialId删除记录
     *
     * @param projectMaterialId
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return projectMaterialBrandPublicRepository.deleteByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据brandPublicId删除记录
     *
     * @param brandPublicId
     */
    @Override
    public int deleteByBrandPublicId(String brandPublicId) {
        return projectMaterialBrandPublicRepository.deleteByBrandPublicId(brandPublicId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialBrandPublicRepository.getCount();
    }

    /**
     * 根据projectMaterialId得到数量
     *
     * @param projectMaterialId
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        return projectMaterialBrandPublicRepository.getCountByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据brandPublicId得到数量
     *
     * @param brandPublicId
     */
    @Override
    public int getCountByBrandPublicId(String brandPublicId) {
        return projectMaterialBrandPublicRepository.getCountByBrandPublicId(brandPublicId);
    }

    /**
     * 根据id得到ProjectMaterialBrandPublic
     *
     * @param id
     */
    @Override
    public ProjectMaterialBrandPublic getById(String id) {
        return projectMaterialBrandPublicRepository.getById(id);
    }

    @Override
    public ProjectMaterialBrandPublicView getViewById(String id) {
        return getProjectMaterialBrandPublicViewByProjectMaterialBrandPublicId(id);
    }

    /**
     * 根据projectMaterialId得到ProjectMaterialBrandPublic
     *
     * @param projectMaterialId
     */
    @Override
    public List<ProjectMaterialBrandPublic> getByProjectMaterialId(String projectMaterialId) {
        return projectMaterialBrandPublicRepository.getByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据brandPublicId得到ProjectMaterialBrandPublic
     *
     * @param brandPublicId
     */
    @Override
    public List<ProjectMaterialBrandPublic> getByBrandPublicId(String brandPublicId) {
        return projectMaterialBrandPublicRepository.getByBrandPublicId(brandPublicId);
    }

    @Override
    public List<ProjectMaterialBrandPublicView> getViewListByProjectMaterialId(String projectMaterialId) {
        List<ProjectMaterialBrandPublic> projectMaterialBrandPublicList = getByProjectMaterialId(projectMaterialId);
        return convertProjectMaterialBrandPublicList2ViewList(projectMaterialBrandPublicList);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublic> getPage(int pageNo, int pageSize) {
        return projectMaterialBrandPublicRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublic> getPageByProjectMaterialId(String projectMaterialId, int pageNo, int pageSize) {
        return projectMaterialBrandPublicRepository.getPageByProjectMaterialId(projectMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandPublicId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublic> getPageByBrandPublicId(String brandPublicId, int pageNo, int pageSize) {
        return projectMaterialBrandPublicRepository.getPageByBrandPublicId(brandPublicId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublicView> getPageView(int pageNo, int pageSize) {
        Page<ProjectMaterialBrandPublic> projectMaterialBrandPublicPage = getPage(pageNo, pageSize);
        return convertProjectMaterialBrandPublicPage2PageView(projectMaterialBrandPublicPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublicView> getPageViewByProjectMaterialId(String projectMaterialId, int pageNo, int pageSize) {
        Page<ProjectMaterialBrandPublic> projectMaterialBrandPublicPage = getPageByProjectMaterialId(projectMaterialId, pageNo, pageSize);
        return convertProjectMaterialBrandPublicPage2PageView(projectMaterialBrandPublicPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param brandPublicId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublicView> getPageViewByBrandPublicId(String brandPublicId, int pageNo, int pageSize) {
        Page<ProjectMaterialBrandPublic> projectMaterialBrandPublicPage = getPageByBrandPublicId(brandPublicId, pageNo, pageSize);
        return convertProjectMaterialBrandPublicPage2PageView(projectMaterialBrandPublicPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialBrandPublicView getProjectMaterialBrandPublicViewByProjectMaterialBrandPublicId(String id) {
        ProjectMaterialBrandPublic projectMaterialBrandPublic = getById(id);
        if (projectMaterialBrandPublic == null) return null;
        ProjectMaterialBrandPublicView projectMaterialBrandPublicView = new ProjectMaterialBrandPublicView();
        projectMaterialBrandPublicView.setProjectMaterialBrandPublic(projectMaterialBrandPublic);
        projectMaterialBrandPublicView.setBrandPublicView(brandPublicService.getViewById(projectMaterialBrandPublic.getBrandPublicId()));
        projectMaterialBrandPublicView.setProjectMaterial(projectMaterialService.getById(projectMaterialBrandPublic.getProjectMaterialId()));
        return projectMaterialBrandPublicView;

    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialBrandPublicPage 页面对象
     */
    private Page<ProjectMaterialBrandPublicView> convertProjectMaterialBrandPublicPage2PageView(Page<ProjectMaterialBrandPublic> projectMaterialBrandPublicPage, int pageNo, int pageSize) {
        if (projectMaterialBrandPublicPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPublicView> list = new ArrayList<>();
        for (ProjectMaterialBrandPublic projectMaterialBrandPublic : projectMaterialBrandPublicPage.getResult()) {
            ProjectMaterialBrandPublicView projectMaterialBrandPublicView = getProjectMaterialBrandPublicViewByProjectMaterialBrandPublicId(projectMaterialBrandPublic.getId());
            if (projectMaterialBrandPublicView != null) list.add(projectMaterialBrandPublicView);
        }
        return new Page<>(startIndex, projectMaterialBrandPublicPage.getTotalCount(), pageSize, list);
    }

    private List<ProjectMaterialBrandPublicView> convertProjectMaterialBrandPublicList2ViewList(List<ProjectMaterialBrandPublic> projectMaterialBrandPublicList) {
        if (projectMaterialBrandPublicList == null) return null;

        List<ProjectMaterialBrandPublicView> list = new ArrayList<>();
        for (ProjectMaterialBrandPublic projectMaterialBrandPublic : projectMaterialBrandPublicList) {
            ProjectMaterialBrandPublicView projectMaterialBrandPublicView = getProjectMaterialBrandPublicViewByProjectMaterialBrandPublicId(projectMaterialBrandPublic.getId());
            if (projectMaterialBrandPublicView != null) list.add(projectMaterialBrandPublicView);
        }
        return list;
    }


}