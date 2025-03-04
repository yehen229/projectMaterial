package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialNewBrandFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usematerialnewbrandfile/v1")
public class UseMaterialNewBrandFileController {
    private final IUseMaterialNewBrandFileService useMaterialNewBrandFileService;

    UseMaterialNewBrandFileController(IUseMaterialNewBrandFileService useMaterialNewBrandFileService) {
        this.useMaterialNewBrandFileService = useMaterialNewBrandFileService;
    }

    @GetMapping(value = "get-by-id")
    public UseMaterialNewBrandFile getById(@RequestParam(value = "id") String id) {
        return useMaterialNewBrandFileService.getById(id);
    }


    @GetMapping(value = "get-by-use-material-new-brand-id")
    public List<UseMaterialNewBrandFile> getByUseMaterialNewBrandId(@RequestParam(value = "useMaterialNewBrandId") String useMaterialNewBrandId) {
        return useMaterialNewBrandFileService.getByUseMaterialNewBrandId(useMaterialNewBrandId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return useMaterialNewBrandFileService.add(useMaterialNewBrandFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return useMaterialNewBrandFileService.delete(useMaterialNewBrandFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return useMaterialNewBrandFileService.update(useMaterialNewBrandFile);
    }


    @GetMapping(value = "page")
    public Page<UseMaterialNewBrandFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return useMaterialNewBrandFileService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "download-use-material-new-brand-file-by-id")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId) ")
    public DownloadFile downloadUseMaterialNewBrandFileById(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "useMaterialNewBrandFileId") String useMaterialNewBrandFileId,
            HttpServletRequest request,
            HttpServletResponse response) {
        useMaterialNewBrandFileService.downloadFileById(useMaterialNewBrandFileId, request, response);

        return new DownloadFile();
    }


}