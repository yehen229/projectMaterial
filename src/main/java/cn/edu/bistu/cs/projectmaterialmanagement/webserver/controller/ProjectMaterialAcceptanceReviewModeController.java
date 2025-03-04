package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialAcceptanceReviewModeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialacceptancereviewmode/v1")
public class ProjectMaterialAcceptanceReviewModeController {
    private final IProjectMaterialAcceptanceReviewModeService projectMaterialAcceptanceReviewModeService;

    ProjectMaterialAcceptanceReviewModeController(IProjectMaterialAcceptanceReviewModeService projectMaterialAcceptanceReviewModeService) {
        this.projectMaterialAcceptanceReviewModeService = projectMaterialAcceptanceReviewModeService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialAcceptanceReviewMode getById(@RequestParam(value = "id") String id) {
        return projectMaterialAcceptanceReviewModeService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectMaterialAcceptanceReviewMode> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectMaterialAcceptanceReviewModeService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-material-acceptance-batch-id")
    public List<ProjectMaterialAcceptanceReviewMode> getByProjectMaterialAcceptanceBatchId(@RequestParam(value = "projectMaterialAcceptanceBatchId") String projectMaterialAcceptanceBatchId) {
        return projectMaterialAcceptanceReviewModeService.getByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return projectMaterialAcceptanceReviewModeService.add(projectMaterialAcceptanceReviewMode);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return projectMaterialAcceptanceReviewModeService.delete(projectMaterialAcceptanceReviewMode);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return projectMaterialAcceptanceReviewModeService.update(projectMaterialAcceptanceReviewMode);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialAcceptanceReviewMode> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialAcceptanceReviewModeService.getPage(pageNo, pageSize);
    }

}