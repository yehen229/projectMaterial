package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("projectappearancereview/v1")
@EnableMethodSecurity
public class ProjectAppearanceReviewController {
    private final IProjectAppearanceReviewService projectAppearanceReviewService;
    private final IProjectBusinessService projectBusinessService;

    ProjectAppearanceReviewController(IProjectAppearanceReviewService projectAppearanceReviewService,
                                      IProjectBusinessService projectBusinessService) {
        this.projectAppearanceReviewService = projectAppearanceReviewService;
        this.projectBusinessService = projectBusinessService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectAppearanceReview getById(@RequestParam(value = "id") String id) {
        return projectAppearanceReviewService.getById(id);
    }


    @GetMapping(value = "get-by-use-material-id")
    public List<ProjectAppearanceReview> getByUseMaterialId(@RequestParam(value = "useMaterialId") String useMaterialId) {
        return projectAppearanceReviewService.getByUseMaterialId(useMaterialId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectAppearanceReview projectAppearanceReview) {
        return projectAppearanceReviewService.add(projectAppearanceReview);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectAppearanceReview projectAppearanceReview) {
        return projectAppearanceReviewService.delete(projectAppearanceReview);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectAppearanceReview projectAppearanceReview) {
        return projectAppearanceReviewService.update(projectAppearanceReview);
    }


    @GetMapping(value = "page")
    public Page<ProjectAppearanceReview> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectAppearanceReviewService.getPage(pageNo, pageSize);
    }

    /**
     * 增加临时文件
     * <p>
     * 权限范围：设计部、工程部、监理单位
     *
     * @param projectId
     * @param uploadReviewFilesDir
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "add-use-material-appearance-review-temp-file")
    @PreAuthorize("""
            hasAnyRole('Admin')
            or  @ProjectPermission.isInDesignDepartment(#projectId)
            or  @ProjectPermission.isInEngineeringDepartment(#projectId)
            or  @ProjectPermission.isInSupervisionCompany(#projectId)
            or  @ProjectPermission.isInDesignCompany(#projectId)
            """)
    public String addUseMaterialAppearanceReviewTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadReviewFilesDir") String uploadReviewFilesDir,
            @RequestParam("file") MultipartFile multipartFile) {
        return projectBusinessService.addUseMaterialAppearanceReviewTempFile(uploadReviewFilesDir, multipartFile);
    }

    /**
     * 删除临时文件
     * <p>
     * 权限范围：设计部、工程部、监理单位
     *
     * @param uploadReviewFilesDir
     * @param fileName
     * @return
     */
    @PostMapping(value = "delete-use-material-appearance-review-temp-file")
    @PreAuthorize("""
            hasAnyRole('Admin')
            or  @ProjectPermission.isInDesignDepartment(#projectId)
            or  @ProjectPermission.isInEngineeringDepartment(#projectId)
            or  @ProjectPermission.isInSupervisionCompany(#projectId)
            or  @ProjectPermission.isInDesignCompany(#projectId)
            """)
    public boolean deleteUseMaterialAppearanceReviewTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadReviewFilesDir") String uploadReviewFilesDir,
            @RequestParam("fileName") String fileName) {
        return projectBusinessService.deleteUseMaterialAppearanceReviewTempFile(uploadReviewFilesDir, fileName);
    }

}