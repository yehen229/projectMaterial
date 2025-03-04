package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocument;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialVerificationDocumentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialverificationdocument/v1")
@EnableMethodSecurity
public class ProjectMaterialVerificationDocumentController {
    private final IProjectMaterialVerificationDocumentService projectMaterialVerificationDocumentService;

    ProjectMaterialVerificationDocumentController(IProjectMaterialVerificationDocumentService projectMaterialVerificationDocumentService) {
        this.projectMaterialVerificationDocumentService = projectMaterialVerificationDocumentService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialVerificationDocument getById(@RequestParam(value = "id") String id) {
        return projectMaterialVerificationDocumentService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectMaterialVerificationDocument> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectMaterialVerificationDocumentService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-buy-material-id")
    public List<ProjectMaterialVerificationDocument> getByBuyMaterialId(@RequestParam(value = "buyMaterialId") String buyMaterialId) {
        return projectMaterialVerificationDocumentService.getByBuyMaterialId(buyMaterialId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return projectMaterialVerificationDocumentService.add(projectMaterialVerificationDocument);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return projectMaterialVerificationDocumentService.delete(projectMaterialVerificationDocument);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return projectMaterialVerificationDocumentService.update(projectMaterialVerificationDocument);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialVerificationDocument> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialVerificationDocumentService.getPage(pageNo, pageSize);
    }

}