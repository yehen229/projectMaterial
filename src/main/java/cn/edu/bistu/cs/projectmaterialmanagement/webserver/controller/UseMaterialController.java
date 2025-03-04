package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("usematerial/v1")
public class UseMaterialController {
    private final IUseMaterialService useMaterialService;
    private final IProjectBusinessService projectBusinessService;
    private final IUseMaterialBusinessService useMaterialBusinessService;

    UseMaterialController(IUseMaterialService useMaterialService,
                          IProjectBusinessService projectBusinessService,
                          IUseMaterialBusinessService useMaterialBusinessService) {
        this.useMaterialService = useMaterialService;
        this.projectBusinessService = projectBusinessService;
        this.useMaterialBusinessService = useMaterialBusinessService;
    }

    @GetMapping(value = "get-by-id")
    public UseMaterial getById(@RequestParam(value = "id") String id) {
        return useMaterialService.getById(id);
    }


    @GetMapping(value = "get-by-project-material-id")
    public List<UseMaterial> getByProjectMaterialId(@RequestParam(value = "projectMaterialId") String projectMaterialId) {
        return useMaterialService.getByProjectMaterialId(projectMaterialId);
    }


    @GetMapping(value = "get-by-user-id")
    public List<UseMaterial> getByUserId(@RequestParam(value = "userId") String userId) {
        return useMaterialService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-material-brand-private-id")
    public List<UseMaterial> getByProjectMaterialBrandPrivateId(@RequestParam(value = "projectMaterialBrandPrivateId") String projectMaterialBrandPrivateId) {
        return useMaterialService.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }


    @GetMapping(value = "get-by-project-material-brand-public-id")
    public List<UseMaterial> getByProjectMaterialBrandPublicId(@RequestParam(value = "projectMaterialBrandPublicId") String projectMaterialBrandPublicId) {
        return useMaterialService.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }


    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody UseMaterial useMaterial) {
        return useMaterialService.add(useMaterial);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody UseMaterial useMaterial) {
        return useMaterialService.delete(useMaterial);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody UseMaterial useMaterial) {
        return useMaterialService.update(useMaterial);
    }


    @GetMapping(value = "page")
    public Page<UseMaterial> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return useMaterialService.getPage(pageNo, pageSize);
    }

    /**
     * 增加临时文件
     *
     * @param projectId
     * @param uploadBrandFilesDir
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "add-use-material-new-brand-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public String addUseMaterialNewBrandTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadBrandFilesDir") String uploadBrandFilesDir,
            @RequestParam("file") MultipartFile multipartFile) {
        return projectBusinessService.addUseMaterialNewBrandTempFile(uploadBrandFilesDir, multipartFile);
    }

    /**
     * 删除临时文件
     *
     * @param uploadBrandFilesDir
     * @param fileName
     * @return
     */
    @PostMapping(value = "delete-use-material-new-brand-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public boolean deleteUseMaterialNewBrandTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadBrandFilesDir") String uploadBrandFilesDir,
            @RequestParam("fileName") String fileName) {
        return projectBusinessService.deleteUseMaterialNewBrandTempFile(uploadBrandFilesDir, fileName);
    }

    @GetMapping(value = "page-use-material-view-by-project-id")
    public Page<UseMaterialView> getUseMaterialViewPageByProjectId(
            @RequestParam(value = "projectId", required = true) String projectId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getReviewedAndApprovedUseMaterialViewPageByProjectId(projectId, pageNo, pageSize);
    }


}