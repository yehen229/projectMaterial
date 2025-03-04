package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivate;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialBrandPrivateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialbrandprivate/v1")
@EnableMethodSecurity
public class ProjectMaterialBrandPrivateController {
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;

    ProjectMaterialBrandPrivateController(IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService) {
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialBrandPrivate getById(@RequestParam(value = "id") String id) {
        return projectMaterialBrandPrivateService.getById(id);
    }


    @GetMapping(value = "get-by-project-material-id")
    public List<ProjectMaterialBrandPrivate> getByProjectMaterialId(@RequestParam(value = "projectMaterialId") String projectMaterialId) {
        return projectMaterialBrandPrivateService.getByProjectMaterialId(projectMaterialId);
    }


    @GetMapping(value = "get-by-project-brand-id")
    public List<ProjectMaterialBrandPrivate> getByProjectBrandId(@RequestParam(value = "projectBrandId") String projectBrandId) {
        return projectMaterialBrandPrivateService.getByProjectBrandId(projectBrandId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return projectMaterialBrandPrivateService.add(projectMaterialBrandPrivate);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return projectMaterialBrandPrivateService.delete(projectMaterialBrandPrivate);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return projectMaterialBrandPrivateService.update(projectMaterialBrandPrivate);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialBrandPrivate> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialBrandPrivateService.getPage(pageNo, pageSize);
    }

}