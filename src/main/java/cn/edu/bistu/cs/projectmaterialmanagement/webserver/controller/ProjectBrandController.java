package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBrandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectbrand/v1")
@EnableMethodSecurity
public class ProjectBrandController {
    private final IProjectBrandService projectBrandService;

    ProjectBrandController(IProjectBrandService projectBrandService) {
        this.projectBrandService = projectBrandService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectBrand getById(@RequestParam(value = "id") String id) {
        return projectBrandService.getById(id);
    }


    @GetMapping(value = "get-list-by-project-id")
    public List<ProjectBrand> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectBrandService.getByProjectId(projectId);
    }

    @GetMapping(value = "get-view-list-by-project-id")
    public List<ProjectBrandView> getViewListByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectBrandService.getViewListByProjectId(projectId);
    }


    @GetMapping(value = "get-by-brand-id")
    public List<ProjectBrand> getByBrandId(@RequestParam(value = "brandId") String brandId) {
        return projectBrandService.getByBrandId(brandId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectBrand projectBrand) {
        return projectBrandService.add(projectBrand);
    }

    @PostMapping(value = "add-form")
    @PreAuthorize("hasRole('Admin')")
    public String addForm(@RequestBody ProjectBrandForm projectBrandForm) {
        return projectBrandService.addForm(projectBrandForm);
    }


    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectBrand projectBrand) {
        return projectBrandService.delete(projectBrand);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectBrand projectBrand) {
        return projectBrandService.update(projectBrand);
    }


    @GetMapping(value = "page")
    public Page<ProjectBrand> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-view")
    public Page<ProjectBrandView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPageView(pageNo, pageSize);
    }

}