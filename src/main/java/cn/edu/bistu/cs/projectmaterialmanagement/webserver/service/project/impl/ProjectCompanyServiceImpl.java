package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompanyView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectCompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectCompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectCompanyServiceImpl implements IProjectCompanyService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectCompanyServiceImpl.class);

    private final IProjectCompanyRepository projectCompanyRepository;
    private final ICompanyService companyService;
    private final IUserService userService;

    public ProjectCompanyServiceImpl(IProjectCompanyRepository projectCompanyRepository, ICompanyService companyService, IUserService userService) {
        this.projectCompanyRepository = projectCompanyRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectCompany projectCompany) {
        return projectCompanyRepository.add(projectCompany);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectCompany projectCompany) {
        return projectCompanyRepository.delete(projectCompany);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectCompany projectCompany) {
        return projectCompanyRepository.update(projectCompany);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectCompanyRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectCompanyRepository.deleteByUserId(userId);
    }

    /**
     * 根据totalPackageCompanyId删除记录
     *
     * @param totalPackageCompanyId
     */
    @Override
    public int deleteByTotalPackageCompanyId(String totalPackageCompanyId) {
        return projectCompanyRepository.deleteByGeneralContractorCompanyId(totalPackageCompanyId);
    }

    /**
     * 根据supervisionCompanyId删除记录
     *
     * @param supervisionCompanyId
     */
    @Override
    public int deleteBySupervisionCompanyId(String supervisionCompanyId) {
        return projectCompanyRepository.deleteBySupervisionCompanyId(supervisionCompanyId);
    }

    @Override
    public int deleteByProjectId(String projectId) {
        return projectCompanyRepository.deleteByProjectId(projectId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectCompanyRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectCompanyRepository.getCountByUserId(userId);
    }

    /**
     * 根据totalPackageCompanyId得到数量
     *
     * @param totalPackageCompanyId
     */
    @Override
    public int getCountByTotalPackageCompanyId(String totalPackageCompanyId) {
        return projectCompanyRepository.getCountByGeneralContractorCompanyId(totalPackageCompanyId);
    }

    /**
     * 根据supervisionCompanyId得到数量
     *
     * @param supervisionCompanyId
     */
    @Override
    public int getCountBySupervisionCompanyId(String supervisionCompanyId) {
        return projectCompanyRepository.getCountBySupervisionCompanyId(supervisionCompanyId);
    }

    @Override
    public int getCountByProjectId(String projectId) {
        return projectCompanyRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据id得到ProjectCompany
     *
     * @param id
     */
    @Override
    public ProjectCompany getById(String id) {
        return projectCompanyRepository.getById(id);
    }

    /**
     * 根据userId得到ProjectCompany
     *
     * @param userId
     */
    @Override
    public List<ProjectCompany> getByUserId(String userId) {
        return projectCompanyRepository.getByUserId(userId);
    }

    /**
     * 根据totalPackageCompanyId得到ProjectCompany
     *
     * @param totalPackageCompanyId
     */
    @Override
    public List<ProjectCompany> getByTotalPackageCompanyId(String totalPackageCompanyId) {
        return projectCompanyRepository.getByGeneralContractorCompanyId(totalPackageCompanyId);
    }

    /**
     * 根据supervisionCompanyId得到ProjectCompany
     *
     * @param supervisionCompanyId
     */
    @Override
    public List<ProjectCompany> getBySupervisionCompanyId(String supervisionCompanyId) {
        return projectCompanyRepository.getBySupervisionCompanyId(supervisionCompanyId);
    }

    @Override
    public ProjectCompany getByProjectId(String projectId) {
        return projectCompanyRepository.getByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPage(int pageNo, int pageSize) {
        return projectCompanyRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPageByUserId(String userId, int pageNo, int pageSize) {
        return projectCompanyRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param totalPackageCompanyId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPageByTotalPackageCompanyId(String totalPackageCompanyId, int pageNo, int pageSize) {
        return projectCompanyRepository.getPageByGeneralContractorCompanyId(totalPackageCompanyId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param supervisionCompanyId
     * @param pageNo               页号，从1开始
     * @param pageSize             每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPageBySupervisionCompanyId(String supervisionCompanyId, int pageNo, int pageSize) {
        return projectCompanyRepository.getPageBySupervisionCompanyId(supervisionCompanyId, pageNo, pageSize);
    }

    @Override
    public Page<ProjectCompany> getPageByProjectId(String projectId, int pageNo, int pageSize) {
        return projectCompanyRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectCompanyView> getPageView(int pageNo, int pageSize) {
        Page<ProjectCompany> projectCompanyPage = getPage(pageNo, pageSize);
        return convertProjectCompanyPage2PageView(projectCompanyPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectCompanyView> getPageViewByUserId(String userId, int pageNo, int pageSize) {
        Page<ProjectCompany> projectCompanyPage = getPageByUserId(userId, pageNo, pageSize);
        return convertProjectCompanyPage2PageView(projectCompanyPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param totalPackageCompanyId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<ProjectCompanyView> getPageViewByTotalPackageCompanyId(String totalPackageCompanyId, int pageNo, int pageSize) {
        Page<ProjectCompany> projectCompanyPage = getPageByTotalPackageCompanyId(totalPackageCompanyId, pageNo, pageSize);
        return convertProjectCompanyPage2PageView(projectCompanyPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param supervisionCompanyId
     * @param pageNo               页号，从1开始
     * @param pageSize             每页的记录数
     */
    @Override
    public Page<ProjectCompanyView> getPageViewBySupervisionCompanyId(String supervisionCompanyId, int pageNo, int pageSize) {
        Page<ProjectCompany> projectCompanyPage = getPageBySupervisionCompanyId(supervisionCompanyId, pageNo, pageSize);
        return convertProjectCompanyPage2PageView(projectCompanyPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectCompanyView> getPageViewByProjectId(String projectId, int pageNo, int pageSize) {
        Page<ProjectCompany> projectCompanyPage = getPageByProjectId(projectId, pageNo, pageSize);
        return convertProjectCompanyPage2PageView(projectCompanyPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectCompanyView getProjectCompanyViewByProjectCompanyId(String id) {
        ProjectCompany projectCompany = getById(id);
        if (projectCompany == null) return null;
        ProjectCompanyView projectCompanyView = new ProjectCompanyView();
        projectCompanyView.setProjectCompany(projectCompany);
        projectCompanyView.setSupervisionCompany(companyService.getById(projectCompany.getSupervisionCompanyId()));
        projectCompanyView.setTotalPackageCompany(companyService.getById(projectCompany.getGeneralContractorCompanyId()));
        projectCompanyView.setUser(userService.getById(projectCompany.getUserId()));
        return projectCompanyView;

    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectCompanyPage 页面对象
     */
    private Page<ProjectCompanyView> convertProjectCompanyPage2PageView(Page<ProjectCompany> projectCompanyPage, int pageNo, int pageSize) {
        if (projectCompanyPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectCompanyView> list = new ArrayList<>();
        for (ProjectCompany projectCompany : projectCompanyPage.getResult()) {
            ProjectCompanyView projectCompanyView = getProjectCompanyViewByProjectCompanyId(projectCompany.getId());
            if (projectCompanyView != null) list.add(projectCompanyView);
        }
        return new Page<>(startIndex, projectCompanyPage.getTotalCount(), pageSize, list);
    }

}