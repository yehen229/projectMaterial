package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("project/v1")
@EnableMethodSecurity
public class ProjectController {
    private final IProjectService projectService;
    private final IProjectBusinessService projectBusinessService;

    ProjectController(IProjectService projectService,
                      IProjectBusinessService projectBusinessService) {
        this.projectService = projectService;
        this.projectBusinessService = projectBusinessService;
    }

    @GetMapping(value = "get-by-id")
    public Project getById(@RequestParam(value = "id") String id) {
        return projectService.getById(id);
    }


    @GetMapping(value = "get-view-by-id")
    public ProjectView getViewById(@RequestParam(value = "id") String id) {
        return projectBusinessService.getProjectViewById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<Project> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-company-construction-id")
    public List<Project> getByCompanyConstructionId(@RequestParam(value = "companyConstructionId") String companyConstructionId) {
        return projectService.getByCompanyConstructionId(companyConstructionId);
    }


    @GetMapping(value = "get-by-company-design-id")
    public List<Project> getByCompanyDesignId(@RequestParam(value = "companyDesignId") String companyDesignId) {
        return projectService.getByCompanyDesignId(companyDesignId);
    }

    /**
     * 创建工程，只有建设单位（设计部或工程部）才能够创建项目
     *
     * @param project
     * @return
     */
    @PostMapping(value = "add")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.designDepartmentEmployee or @ProjectPermission.engineeringDepartmentEmployee")
    public String add(@RequestBody Project project) {
        return projectService.add(project);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.designDepartmentEmployee or @ProjectPermission.engineeringDepartmentEmployee")
    public int delete(@RequestBody Project project) {
        return projectService.delete(project);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.designDepartmentEmployee or @ProjectPermission.engineeringDepartmentEmployee")
    public int update(@RequestBody Project project) {
        return projectService.update(project);
    }


    @GetMapping(value = "page")
    public Page<Project> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-view")
    public Page<ProjectView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageView(pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-user-id")
    public Page<ProjectView> getPageViewByUserId(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByUserId(userId, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-company-construction-id")
    public Page<ProjectView> getPageViewByCompanyConstructionId(
            @RequestParam(value = "companyConstructionId", required = true) String companyConstructionId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByCompanyConstructionId(companyConstructionId, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-company-design-id")
    public Page<ProjectView> getPageViewByCompanyDesignId(
            @RequestParam(value = "companyDesignId", required = true) String companyDesignId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByCompanyDesignId(companyDesignId, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-project-name")
    public Page<ProjectView> getPageViewByProjectName(
            @RequestParam(value = "projectName", required = true) String projectName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByProjectName(projectName, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-project-location")
    public Page<ProjectView> getPageViewByProjectLocation(
            @RequestParam(value = "projectLocation", required = true) String projectLocation,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByProjectLocation(projectLocation, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-params")
    public Page<ProjectView> getPageViewByParams(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "totalTaxIncluded", required = false) BigDecimal totalTaxIncluded,
            @RequestParam(value = "totalTaxNotIncluded", required = false) BigDecimal totalTaxNotIncluded,
            @RequestParam(value = "buildingAreaAboveGround", required = false) BigDecimal buildingAreaAboveGround,
            @RequestParam(value = "buildingAreaUnderGround", required = false) BigDecimal buildingAreaUnderGround,
            @RequestParam(value = "companyConstructionId", required = false) String companyConstructionId,
            @RequestParam(value = "companyDesignId", required = false) String companyDesignId,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "createDatetime", required = false) Date createDatetime,
            @RequestParam(value = "endDatetime", required = false) Date endDatetime,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByParams(name, location, totalTaxIncluded, totalTaxNotIncluded, buildingAreaAboveGround, buildingAreaUnderGround, companyConstructionId, companyDesignId, note, createDatetime, endDatetime, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-keyword")
    public Page<ProjectView> getPageViewByKeyword(
            @RequestParam(value = "keyword", required = true) String keyword,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageViewByKeyword(keyword, pageNo, pageSize);
    }


    /**
     * 得到总包单位参与的没有结项的项目页面
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value = "page-view-not-ended-of-current-login-user-in-general-contractor-company")
    public Page<ProjectView> getCurrentLoginUserInGeneralContractorCompanyNotEndedProjectPageView(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getCurrentLoginUserInGeneralContractorCompanyNotEndedProjectPageView(
                pageNo, pageSize);
    }
}