package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectBrandServiceImpl implements IProjectBrandService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectBrandServiceImpl.class);

    private final IProjectBrandRepository projectBrandRepository;
    private final IProjectService projectService;
    private final IBrandService brandService;

    public ProjectBrandServiceImpl(IProjectBrandRepository projectBrandRepository,
                                   IProjectService projectService,
                                   IBrandService brandService) {
        this.projectBrandRepository = projectBrandRepository;
        this.projectService = projectService;
        this.brandService = brandService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectBrand projectBrand) {
        return projectBrandRepository.add(projectBrand);
    }

    @Override
    public String addForm(ProjectBrandForm projectBrandForm) {
        if (projectBrandForm == null)
            throw new BusinessException("参数不能为空");
        Project project = projectBrandForm.getProject();
        if (project == null)
            throw new BusinessException("参数不能为空");
        Brand brand = projectBrandForm.getBrand();
        if (brand == null)
            throw new BusinessException("参数不能为空");
        String brandId = brandService.add(brand);
        if (brandId == null)
            throw new BusinessException("添加失败");
        ProjectBrand projectBrand = new ProjectBrand();
        projectBrand.setBrandId(brandId);
        projectBrand.setProjectId(project.getId());
        return add(projectBrand);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectBrand projectBrand) {
        return projectBrandRepository.delete(projectBrand);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectBrand projectBrand) {
        return projectBrandRepository.update(projectBrand);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectBrandRepository.deleteById(id);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectBrandRepository.deleteByProjectId(projectId);
    }

    /**
     * 根据brandId删除记录
     *
     * @param brandId
     */
    @Override
    public int deleteByBrandId(String brandId) {
        return projectBrandRepository.deleteByBrandId(brandId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectBrandRepository.getCount();
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectBrandRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据brandId得到数量
     *
     * @param brandId
     */
    @Override
    public int getCountByBrandId(String brandId) {
        return projectBrandRepository.getCountByBrandId(brandId);
    }

    /**
     * 根据id得到ProjectBrand
     *
     * @param id
     */
    @Override
    public ProjectBrand getById(String id) {
        return projectBrandRepository.getById(id);
    }

    @Override
    public ProjectBrand getByProjectIdAndBrandId(String projectId,
                                                 String brandId) {
        return projectBrandRepository.getByProjectIdAndBrandId(projectId, brandId);
    }

    @Override
    public ProjectBrandView getViewById(String id) {
        return getProjectBrandViewByProjectBrandId(id);
    }

    /**
     * 根据projectId得到ProjectBrand
     *
     * @param projectId
     */
    @Override
    public List<ProjectBrand> getByProjectId(String projectId) {
        return projectBrandRepository.getByProjectId(projectId);
    }

    /**
     * 根据brandId得到ProjectBrand
     *
     * @param brandId
     */
    @Override
    public List<ProjectBrand> getByBrandId(String brandId) {
        return projectBrandRepository.getByBrandId(brandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPage(int pageNo,
                                      int pageSize) {
        return projectBrandRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByProjectId(String projectId,
                                                 int pageNo,
                                                 int pageSize) {
        return projectBrandRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByBrandId(String brandId,
                                               int pageNo,
                                               int pageSize) {
        return projectBrandRepository.getPageByBrandId(brandId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageView(int pageNo,
                                              int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPage(pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageViewByProjectId(String projectId,
                                                         int pageNo,
                                                         int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPageByProjectId(projectId, pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageViewByBrandId(String brandId,
                                                       int pageNo,
                                                       int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPageByBrandId(brandId, pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    @Override
    public List<ProjectBrandView> getViewListByProjectId(String projectId) {
        List<ProjectBrand> projectBrandList = projectBrandRepository.getByProjectId(projectId);
        return convertProjectBrandList2ListView(projectBrandList);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectBrandView getProjectBrandViewByProjectBrandId(String id) {
        ProjectBrand projectBrand = getById(id);
        if (projectBrand == null) return null;
        ProjectBrandView projectBrandView = new ProjectBrandView();
        projectBrandView.setProjectBrand(projectBrand);
        projectBrandView.setProject(projectService.getById(projectBrand.getProjectId()));
        projectBrandView.setBrandView(brandService.getViewById(projectBrand.getBrandId()));
        return projectBrandView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectBrandPage 页面对象
     */
    private Page<ProjectBrandView> convertProjectBrandPage2PageView(Page<ProjectBrand> projectBrandPage,
                                                                    int pageNo,
                                                                    int pageSize) {
        if (projectBrandPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrandView> list = new ArrayList<>();
        for (ProjectBrand projectBrand : projectBrandPage.getResult()) {
            ProjectBrandView projectBrandView = getProjectBrandViewByProjectBrandId(projectBrand.getId());
            if (projectBrandView != null) list.add(projectBrandView);
        }
        return new Page<>(startIndex, projectBrandPage.getTotalCount(), pageSize, list);
    }

    private List<ProjectBrandView> convertProjectBrandList2ListView(List<ProjectBrand> projectBrandList) {
        if (projectBrandList == null) return null;

        List<ProjectBrandView> list = new ArrayList<>();
        for (ProjectBrand projectBrand : projectBrandList) {
            ProjectBrandView projectBrandView = getProjectBrandViewByProjectBrandId(projectBrand.getId());
            if (projectBrandView != null) list.add(projectBrandView);
        }
        return list;
    }

}