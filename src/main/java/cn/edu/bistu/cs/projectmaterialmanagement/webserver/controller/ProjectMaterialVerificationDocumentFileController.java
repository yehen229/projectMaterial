package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialVerificationDocumentFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialverificationdocumentfile/v1")
@EnableMethodSecurity
public class ProjectMaterialVerificationDocumentFileController {
    private final IProjectMaterialVerificationDocumentFileService projectMaterialVerificationDocumentFileService;

    ProjectMaterialVerificationDocumentFileController(IProjectMaterialVerificationDocumentFileService projectMaterialVerificationDocumentFileService) {
        this.projectMaterialVerificationDocumentFileService = projectMaterialVerificationDocumentFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialVerificationDocumentFile getById(@RequestParam(value = "id") String id) {
        return projectMaterialVerificationDocumentFileService.getById(id);
    }


    @GetMapping(value = "get-by-project-material-verification-document-id")
    public List<ProjectMaterialVerificationDocumentFile> getByProjectMaterialVerificationDocumentId(@RequestParam(value = "projectMaterialVerificationDocumentId") String projectMaterialVerificationDocumentId) {
        return projectMaterialVerificationDocumentFileService.getByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return projectMaterialVerificationDocumentFileService.add(projectMaterialVerificationDocumentFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return projectMaterialVerificationDocumentFileService.delete(projectMaterialVerificationDocumentFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return projectMaterialVerificationDocumentFileService.update(projectMaterialVerificationDocumentFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialVerificationDocumentFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialVerificationDocumentFileService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "download-buy-material-verification-document-file-by-id")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId) ")
    public DownloadFile downloadUseMaterialNewBrandFileById(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "projectMaterialVerificationDocumentFileId") String projectMaterialVerificationDocumentFileId,
            HttpServletRequest request,
            HttpServletResponse response) {
        projectMaterialVerificationDocumentFileService.downloadFileById(projectMaterialVerificationDocumentFileId,
                                                                        request, response);

        return new DownloadFile();
    }


}