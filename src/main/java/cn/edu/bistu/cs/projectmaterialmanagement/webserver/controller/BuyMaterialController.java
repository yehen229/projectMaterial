package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IBuyMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("buymaterial/v1")
public class BuyMaterialController {
    private final IBuyMaterialService buyMaterialService;
    private final IProjectBusinessService projectBusinessService;

    BuyMaterialController(IBuyMaterialService buyMaterialService,
                          IProjectBusinessService projectBusinessService) {
        this.buyMaterialService = buyMaterialService;
        this.projectBusinessService = projectBusinessService;
    }
/*
    @GetMapping(value = "get-by-id")
    public BuyMaterial getById(@RequestParam(value = "id") String id) {
        return buyMaterialService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<BuyMaterial> getByUserId(@RequestParam(value = "userId") String userId) {
        return buyMaterialService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-use-material-id")
    public List<BuyMaterial> getByUseMaterialId(@RequestParam(value = "useMaterialId") String useMaterialId) {
        return buyMaterialService.getByUseMaterialId(useMaterialId);
    }


    @GetMapping(value = "get-by-project-material-brand-private-id")
    public List<BuyMaterial> getByProjectMaterialBrandPrivateId(@RequestParam(value = "projectMaterialBrandPrivateId") String projectMaterialBrandPrivateId) {
        return buyMaterialService.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }


    @GetMapping(value = "get-by-project-material-brand-public-id")
    public List<BuyMaterial> getByProjectMaterialBrandPublicId(@RequestParam(value = "projectMaterialBrandPublicId") String projectMaterialBrandPublicId) {
        return buyMaterialService.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody BuyMaterial buyMaterial) {
        return buyMaterialService.add(buyMaterial);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody BuyMaterial buyMaterial) {
        return buyMaterialService.delete(buyMaterial);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody BuyMaterial buyMaterial) {
        return buyMaterialService.update(buyMaterial);
    }
*/

    @GetMapping(value = "page-view-by-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<BuyMaterialView> getPageViewByProjectId(@RequestParam(value = "projectId") String projectId,
                                                        @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getBuyMaterialViewPageByProjectId(projectId, pageNo, pageSize);
    }


    @GetMapping(value = "page-verification-document-view-by-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialVerificationDocumentView> getPageDocumentViewByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                                        @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageDocumentViewByProjectId(projectId, pageNo, pageSize);
    }


    @GetMapping(value = "page-recheck-is-required-by-project-id")
    @PreAuthorize("""
            hasAnyRole('Admin') 
            or  @ProjectPermission.isInProject(#projectId)
            """)
    public Page<ProjectMaterialVerificationDocumentView> getPageReCheckIsRequiredByProjectId(@RequestParam(value = "projectId") String projectId,
                                                                                             @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getPageReCheckIsRequiredByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 增加临时文件
     *
     * @param projectId
     * @param uploadDocumentFilesDir
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "add-buy-material-verification-document-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public String addBuyMaterialVerificationDocumentTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadDocumentFilesDir") String uploadDocumentFilesDir,
            @RequestParam("file") MultipartFile multipartFile) {
        return projectBusinessService.addUseMaterialNewBrandTempFile(uploadDocumentFilesDir, multipartFile);
    }

    /**
     * 删除临时文件
     *
     * @param uploadDocumentFilesDir
     * @param fileName
     * @return
     */
    @PostMapping(value = "delete-buy-material-verification-document-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public boolean deleteBuyMaterialVerificationDocumentTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadDocumentFilesDir") String uploadDocumentFilesDir,
            @RequestParam("fileName") String fileName) {
        return projectBusinessService.deleteUseMaterialNewBrandTempFile(uploadDocumentFilesDir, fileName);
    }


    @PostMapping(value = "add-buy-material-verification-document-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public String addBuyMaterialVerificationDocumentFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("buyMaterialId") String buyMaterialId,
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("fileType") Integer fileType) {
        return projectBusinessService.addBuyMaterialVerificationDocumentFile(buyMaterialId, multipartFile, fileType);
    }

    @PostMapping(value = "delete-buy-material-verification-document-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public int deleteBuyMaterialVerificationDocumentFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("projectMaterialVerificationDocumentFileId") String projectMaterialVerificationDocumentFileId) {
        return projectBusinessService.deleteBuyMaterialVerificationDocumentFileById(
                projectMaterialVerificationDocumentFileId);
    }

    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value = "page-bought-material-view-by-project-id")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInGeneralContractorCompany(#projectId)")
    public Page<ProjectMaterialView> getBoughtMaterialViewPageByProjectId(
            @RequestParam(value = "projectId", required = true) String projectId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBusinessService.getBoughtMaterialViewPageByProjectId(projectId, pageNo, pageSize);
    }

}