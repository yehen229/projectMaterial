package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectReviewModeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectreviewmode/v1")
@EnableMethodSecurity
public class ProjectReviewModeController {
    private final IProjectReviewModeService projectReviewModeService;

    ProjectReviewModeController(IProjectReviewModeService projectReviewModeService) {
        this.projectReviewModeService = projectReviewModeService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectReviewMode getById(@RequestParam(value = "id") String id) {
        return projectReviewModeService.getById(id);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectReviewMode> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectReviewModeService.getByProjectId(projectId);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectReviewMode> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectReviewModeService.getByUserId(userId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectReviewMode projectReviewMode) {
        return projectReviewModeService.add(projectReviewMode);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectReviewMode projectReviewMode) {
        return projectReviewModeService.delete(projectReviewMode);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectReviewMode projectReviewMode) {
        return projectReviewModeService.update(projectReviewMode);
    }


    @GetMapping(value = "page")
    public Page<ProjectReviewMode> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectReviewModeService.getPage(pageNo, pageSize);
    }

}