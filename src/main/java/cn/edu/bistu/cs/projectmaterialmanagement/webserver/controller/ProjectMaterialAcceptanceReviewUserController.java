package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialAcceptanceReviewUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialacceptancereviewuser/v1")
public class ProjectMaterialAcceptanceReviewUserController {
    private final IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService;

    ProjectMaterialAcceptanceReviewUserController(IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService) {
        this.projectMaterialAcceptanceReviewUserService = projectMaterialAcceptanceReviewUserService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialAcceptanceReviewUser getById(@RequestParam(value = "id") String id) {
        return projectMaterialAcceptanceReviewUserService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectMaterialAcceptanceReviewUser> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectMaterialAcceptanceReviewUserService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-material-acceptance-review-id")
    public List<ProjectMaterialAcceptanceReviewUser> getByProjectMaterialAcceptanceReviewId(@RequestParam(value = "projectMaterialAcceptanceReviewId") String projectMaterialAcceptanceReviewId) {
        return projectMaterialAcceptanceReviewUserService.getByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return projectMaterialAcceptanceReviewUserService.add(projectMaterialAcceptanceReviewUser);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return projectMaterialAcceptanceReviewUserService.delete(projectMaterialAcceptanceReviewUser);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return projectMaterialAcceptanceReviewUserService.update(projectMaterialAcceptanceReviewUser);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialAcceptanceReviewUser> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialAcceptanceReviewUserService.getPage(pageNo, pageSize);
    }

}