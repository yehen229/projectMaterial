package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialBrandPublicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialbrandpublic/v1")
@EnableMethodSecurity
public class ProjectMaterialBrandPublicController {
    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;

    ProjectMaterialBrandPublicController(IProjectMaterialBrandPublicService projectMaterialBrandPublicService) {
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialBrandPublic getById(@RequestParam(value = "id") String id) {
        return projectMaterialBrandPublicService.getById(id);
    }


    @GetMapping(value = "get-by-project-material-id")
    public List<ProjectMaterialBrandPublic> getByProjectMaterialId(@RequestParam(value = "projectMaterialId") String projectMaterialId) {
        return projectMaterialBrandPublicService.getByProjectMaterialId(projectMaterialId);
    }


    @GetMapping(value = "get-by-brand-public-id")
    public List<ProjectMaterialBrandPublic> getByBrandPublicId(@RequestParam(value = "brandPublicId") String brandPublicId) {
        return projectMaterialBrandPublicService.getByBrandPublicId(brandPublicId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return projectMaterialBrandPublicService.add(projectMaterialBrandPublic);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return projectMaterialBrandPublicService.delete(projectMaterialBrandPublic);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return projectMaterialBrandPublicService.update(projectMaterialBrandPublic);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialBrandPublic> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialBrandPublicService.getPage(pageNo, pageSize);
    }

}