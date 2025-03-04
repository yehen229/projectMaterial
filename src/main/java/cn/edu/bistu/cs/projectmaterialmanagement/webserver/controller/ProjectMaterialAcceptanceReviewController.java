package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialacceptancereview/v1")
public class ProjectMaterialAcceptanceReviewController {
    private final IProjectMaterialAcceptanceReviewService projectMaterialAcceptanceReviewService;

    ProjectMaterialAcceptanceReviewController(IProjectMaterialAcceptanceReviewService projectMaterialAcceptanceReviewService) {
        this.projectMaterialAcceptanceReviewService = projectMaterialAcceptanceReviewService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialAcceptanceReview getById(@RequestParam(value = "id") String id) {
        return projectMaterialAcceptanceReviewService.getById(id);
    }


    @GetMapping(value = "get-by-project-material-acceptance-mode-id")
    public List<ProjectMaterialAcceptanceReview> getByProjectMaterialAcceptanceModeId(@RequestParam(value = "projectMaterialAcceptanceModeId") String projectMaterialAcceptanceModeId) {
        return projectMaterialAcceptanceReviewService.getByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return projectMaterialAcceptanceReviewService.add(projectMaterialAcceptanceReview);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return projectMaterialAcceptanceReviewService.delete(projectMaterialAcceptanceReview);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return projectMaterialAcceptanceReviewService.update(projectMaterialAcceptanceReview);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialAcceptanceReview> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialAcceptanceReviewService.getPage(pageNo, pageSize);
    }

}