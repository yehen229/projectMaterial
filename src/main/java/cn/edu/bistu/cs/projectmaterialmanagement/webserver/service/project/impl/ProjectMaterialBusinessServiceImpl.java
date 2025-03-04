package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialBusinessServiceImpl implements IProjectMaterialBusinessService {
    private final IProjectService projectService;
    private final IProjectMaterialService projectMaterialService;
    private final IProjectBrandService projectBrandService;
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;
    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;
    private final IMaterialService materialService;
    private final IUserService userService;
    private final ICompanyUserService companyUserService;
    private final ICompanyService companyService;


    public ProjectMaterialBusinessServiceImpl(IProjectService projectService,
                                              IProjectMaterialService projectMaterialService,
                                              IProjectBrandService projectBrandService,
                                              IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService,
                                              IProjectMaterialBrandPublicService projectMaterialBrandPublicService,
                                              IMaterialService materialService,
                                              IUserService userService,
                                              ICompanyUserService companyUserService,
                                              ICompanyService companyService) {
        this.projectService = projectService;
        this.projectMaterialService = projectMaterialService;
        this.projectBrandService = projectBrandService;
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;
        this.materialService = materialService;
        this.userService = userService;
        this.companyUserService = companyUserService;
        this.companyService = companyService;
    }

    @Override
    public String addForm(ProjectMaterialForm projectMaterialForm) {
        if (projectMaterialForm == null)
            throw new BusinessException("项目物料表单不能为空");

        ProjectMaterial projectMaterial = projectMaterialForm.getProjectMaterial();
        if (projectMaterial.getProjectId() == null)
            throw new BusinessException("参数错误，添加失败");

        String companyId = projectMaterial.getCompanyId();
        if (companyId == null || companyId.isEmpty()) {

            User user = userService.getCurrentLoginUser();
            if (user == null)
                throw new BusinessException("用户未登录，添加失败");
            CompanyUser companyUser = companyUserService.getByUserId(user.getId());
            if (companyUser == null)
                throw new BusinessException("用户未加入任何公司，添加失败");
            if (!companyUserService.isDesignCompanyByUserId(user.getId()))
                throw new BusinessException("用户不是设计公司成员，添加失败");
            companyId = companyUser.getCompanyId();
        } else {
            Company company = companyService.getById(companyId);
            if (company == null)
                throw new BusinessException("公司不存在，添加失败");
            if (!company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_DESIGN))
                throw new BusinessException("公司不是设计公司，添加失败");
        }

        projectMaterial.setCompanyId(companyId);


        String projectMaterialId = projectMaterialService.add(projectMaterial);
        if (projectMaterialId == null)
            throw new BusinessException("项目物料添加失败");
        for (String projectBrandId : projectMaterialForm.getProjectBrandIds()) {
            ProjectMaterialBrandPrivate projectMaterialBrandPrivate = new ProjectMaterialBrandPrivate();
            projectMaterialBrandPrivate.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPrivate.setProjectBrandId(projectBrandId);
            if (projectMaterialBrandPrivateService.add(projectMaterialBrandPrivate) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }

        for (String projectBrandId : projectMaterialForm.getPublicBrandIds()) {
            ProjectMaterialBrandPublic projectMaterialBrandPublic = new ProjectMaterialBrandPublic();
            projectMaterialBrandPublic.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPublic.setBrandPublicId(projectBrandId);
            if (projectMaterialBrandPublicService.add(projectMaterialBrandPublic) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }
        return projectMaterialId;
    }

    @Override
    public int updateForm(ProjectMaterialForm projectMaterialForm) {
        if (projectMaterialForm == null)
            throw new BusinessException("项目物料表单不能为空");
        String projectMaterialId = projectMaterialForm.getProjectMaterial().getId();
        if (projectMaterialId == null)
            throw new BusinessException("参数为空，修改失败");
        ProjectMaterial projectMaterial = projectMaterialService.getById(projectMaterialId);
        if (projectMaterial.getProjectId() == null)
            throw new BusinessException("参数错误，修改失败");

        int ret = projectMaterialService.update(projectMaterialForm.getProjectMaterial());
        if (ret == 0)
            throw new BusinessException("发生错误，修改失败");

        //修改品牌
        projectMaterialBrandPrivateService.deleteByProjectMaterialId(projectMaterialId);
        for (String projectBrandId : projectMaterialForm.getProjectBrandIds()) {
            ProjectMaterialBrandPrivate projectMaterialBrandPrivate = new ProjectMaterialBrandPrivate();
            projectMaterialBrandPrivate.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPrivate.setProjectBrandId(projectBrandId);
            if (projectMaterialBrandPrivateService.add(projectMaterialBrandPrivate) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }

        projectMaterialBrandPublicService.deleteByProjectMaterialId(projectMaterialId);
        for (String projectBrandId : projectMaterialForm.getPublicBrandIds()) {
            ProjectMaterialBrandPublic projectMaterialBrandPublic = new ProjectMaterialBrandPublic();
            projectMaterialBrandPublic.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPublic.setBrandPublicId(projectBrandId);
            if (projectMaterialBrandPublicService.add(projectMaterialBrandPublic) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }
        return ret;
    }

    /**
     * Todo:删除项目
     *
     * @param projectId
     */
    @Override
    public void deleteByProjectId(String projectId) {
        projectBrandService.deleteByProjectId(projectId);
        projectMaterialService.deleteByProjectId(projectId);

    }

    @Override
    public ProjectMaterialView getViewById(String projectMaterialId) {
        ProjectMaterial projectMaterial = projectMaterialService.getById(projectMaterialId);
        if (projectMaterial == null) return null;
        ProjectMaterialView projectMaterialView = new ProjectMaterialView();
        projectMaterialView.setProjectMaterial(projectMaterial);
        projectMaterialView.setMaterial(materialService.getById(projectMaterial.getMaterialId()));
        projectMaterialView.setProject(projectService.getById(projectMaterial.getProjectId()));
        projectMaterialView.setProjectMaterialBrandPrivateViewList(
                projectMaterialBrandPrivateService.getViewListByProjectMaterialId(projectMaterialId));
        projectMaterialView.setProjectMaterialBrandPublicViewList(
                projectMaterialBrandPublicService.getViewListByProjectMaterialId(projectMaterialId));
        return projectMaterialView;
    }

    @Override
    public Page<ProjectMaterialView> getPageView(Integer pageNo,
                                                 Integer pageSize) {
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPage(pageNo, pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getPageViewByProjectId(String projectId,
                                                            Integer pageNo,
                                                            Integer pageSize) {
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPageByProjectId(projectId, pageNo,
                                                                                              pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getPageViewByCurrentUserAndProjectId(String projectId,
                                                                          Integer pageNo,
                                                                          Integer pageSize) {


        User user = userService.getCurrentLoginUser();
        if (user == null) return null;
        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        if (companyUser == null) return null;

        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPageByProjectIdAndCompanyId(projectId,
                                                                                                          companyUser.getCompanyId(),
                                                                                                          pageNo,
                                                                                                          pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getProjectMaterialPageViewByProjectIdAndCompanyId(String projectId,
                                                                                       String companyId,
                                                                                       Integer pageNo,
                                                                                       Integer pageSize) {
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPageByProjectIdAndCompanyId(projectId,
                                                                                                          companyId,
                                                                                                          pageNo,
                                                                                                          pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialPage 页面对象
     */
    private Page<ProjectMaterialView> convertProjectMaterialPage2PageView(Page<ProjectMaterial> projectMaterialPage,
                                                                          int pageNo,
                                                                          int pageSize) {
        if (projectMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialView> list = new ArrayList<>();
        for (ProjectMaterial projectMaterial : projectMaterialPage.getResult()) {
            ProjectMaterialView projectMaterialView = getViewById(projectMaterial.getId());
            if (projectMaterialView != null) list.add(projectMaterialView);
        }
        return new Page<>(startIndex, projectMaterialPage.getTotalCount(), pageSize, list);
    }

}
