package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewUserFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectappearancereviewuserfile/v1")
@EnableMethodSecurity
public class ProjectAppearanceReviewUserFileController {
    private final IProjectAppearanceReviewUserFileService projectAppearanceReviewUserFileService;

    ProjectAppearanceReviewUserFileController(IProjectAppearanceReviewUserFileService projectAppearanceReviewUserFileService) {
        this.projectAppearanceReviewUserFileService = projectAppearanceReviewUserFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectAppearanceReviewUserFile getById(@RequestParam(value = "id") String id) {
        return projectAppearanceReviewUserFileService.getById(id);
    }


    @GetMapping(value = "get-by-project-appearance-review-user-id")
    public List<ProjectAppearanceReviewUserFile> getByProjectAppearanceReviewUserId(@RequestParam(value = "projectAppearanceReviewUserId") String projectAppearanceReviewUserId) {
        return projectAppearanceReviewUserFileService.getByProjectAppearanceReviewUserId(projectAppearanceReviewUserId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return projectAppearanceReviewUserFileService.add(projectAppearanceReviewUserFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return projectAppearanceReviewUserFileService.delete(projectAppearanceReviewUserFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return projectAppearanceReviewUserFileService.update(projectAppearanceReviewUserFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectAppearanceReviewUserFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectAppearanceReviewUserFileService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "download-use-material-appearance-review-file-by-id")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId) ")
    public DownloadFile downloadUseMaterialNewBrandFileById(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "projectAppearanceReviewUserFileId") String projectAppearanceReviewUserFileId,
            HttpServletRequest request,
            HttpServletResponse response) {
        projectAppearanceReviewUserFileService.downloadFileById(projectAppearanceReviewUserFileId, request, response);

        return new DownloadFile();
    }

}