package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewUserFileService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("projectmaterialacceptancereviewuserfile/v1")
public class ProjectMaterialAcceptanceReviewUserFileController {
    private final IProjectMaterialAcceptanceReviewUserFileService projectMaterialAcceptanceReviewUserFileService;

    ProjectMaterialAcceptanceReviewUserFileController(IProjectMaterialAcceptanceReviewUserFileService projectMaterialAcceptanceReviewUserFileService) {
        this.projectMaterialAcceptanceReviewUserFileService = projectMaterialAcceptanceReviewUserFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialAcceptanceReviewUserFile getById(@RequestParam(value = "id") String id) {
        return projectMaterialAcceptanceReviewUserFileService.getById(id);
    }


    @GetMapping(value = "get-by-project-material-acceptance-review-user-id")
    public List<ProjectMaterialAcceptanceReviewUserFile> getByProjectMaterialAcceptanceReviewUserId(@RequestParam(value = "projectMaterialAcceptanceReviewUserId") String projectMaterialAcceptanceReviewUserId) {
        return projectMaterialAcceptanceReviewUserFileService.getByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return projectMaterialAcceptanceReviewUserFileService.add(projectMaterialAcceptanceReviewUserFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return projectMaterialAcceptanceReviewUserFileService.delete(projectMaterialAcceptanceReviewUserFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return projectMaterialAcceptanceReviewUserFileService.update(projectMaterialAcceptanceReviewUserFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialAcceptanceReviewUserFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialAcceptanceReviewUserFileService.getPage(pageNo, pageSize);
    }
    @GetMapping(value = "download-project-material-appearance-review-file-by-id")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId) ")
    public DownloadFile downloadProjectMaterialAcceptanceFileById(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "projectMaterialAppearanceReviewUserFileId") String projectMatetailAppearanceReviewUserFileId,
            HttpServletRequest request,
            HttpServletResponse response) {
        projectMaterialAcceptanceReviewUserFileService.downloadFileById(projectMatetailAppearanceReviewUserFileId, request, response);

        return new DownloadFile();
    }


}