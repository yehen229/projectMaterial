package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projectmaterialretestfile/v1")
public class ProjectMaterialRetestFileController {
    private final IProjectMaterialRetestFileService projectMaterialRetestFileService;

    ProjectMaterialRetestFileController(IProjectMaterialRetestFileService projectMaterialRetestFileService) {
        this.projectMaterialRetestFileService = projectMaterialRetestFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialRetestFile getById(@RequestParam(value = "id") String id) {
        return projectMaterialRetestFileService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialRetestFile projectMaterialRetestFile) {
        return projectMaterialRetestFileService.add(projectMaterialRetestFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialRetestFile projectMaterialRetestFile) {
        return projectMaterialRetestFileService.delete(projectMaterialRetestFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialRetestFile projectMaterialRetestFile) {
        return projectMaterialRetestFileService.update(projectMaterialRetestFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialRetestFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialRetestFileService.getPage(pageNo, pageSize);
    }

}