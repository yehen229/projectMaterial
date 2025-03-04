package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptance;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialacceptance/v1")
public class ProjectMaterialAcceptanceController {
    private final IProjectMaterialAcceptanceService projectMaterialAcceptanceService;

    ProjectMaterialAcceptanceController(IProjectMaterialAcceptanceService projectMaterialAcceptanceService) {
        this.projectMaterialAcceptanceService = projectMaterialAcceptanceService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialAcceptance getById(@RequestParam(value = "id") String id) {
        return projectMaterialAcceptanceService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectMaterialAcceptance> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectMaterialAcceptanceService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-material-id")
    public List<ProjectMaterialAcceptance> getByProjectMaterialId(@RequestParam(value = "projectMaterialId") String projectMaterialId) {
        return projectMaterialAcceptanceService.getByProjectMaterialId(projectMaterialId);
    }


    @GetMapping(value = "get-by-project-material-brand-private-id")
    public List<ProjectMaterialAcceptance> getByProjectMaterialBrandPrivateId(@RequestParam(value = "projectMaterialBrandPrivateId") String projectMaterialBrandPrivateId) {
        return projectMaterialAcceptanceService.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }


    @GetMapping(value = "get-by-project-material-brand-public-id")
    public List<ProjectMaterialAcceptance> getByProjectMaterialBrandPublicId(@RequestParam(value = "projectMaterialBrandPublicId") String projectMaterialBrandPublicId) {
        return projectMaterialAcceptanceService.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialAcceptance projectMaterialAcceptance) {
        return projectMaterialAcceptanceService.add(projectMaterialAcceptance);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialAcceptance projectMaterialAcceptance) {
        return projectMaterialAcceptanceService.delete(projectMaterialAcceptance);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialAcceptance projectMaterialAcceptance) {
        return projectMaterialAcceptanceService.update(projectMaterialAcceptance);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialAcceptance> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialAcceptanceService.getPage(pageNo, pageSize);
    }

}