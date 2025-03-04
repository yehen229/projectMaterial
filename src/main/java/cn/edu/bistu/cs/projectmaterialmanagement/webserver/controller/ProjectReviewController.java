package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlow;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("projectreview/v1")
@EnableMethodSecurity
public class ProjectReviewController {
    private final ProjectFlow projectMaterialFlow;
    private final IProjectReviewService projectReviewService;

    private final IProjectBusinessService projectBusinessService;
    private final IProjectReviewBusinessService projectReviewBusinessService;

    ProjectReviewController(IProjectReviewService projectReviewService,
                            IProjectBusinessService projectBusinessService,
                            IProjectReviewBusinessService projectReviewBusinessService,
                            ProjectFlow projectMaterialFlow) {
        this.projectReviewService = projectReviewService;
        this.projectBusinessService = projectBusinessService;
        this.projectReviewBusinessService = projectReviewBusinessService;
        this.projectMaterialFlow = projectMaterialFlow;
    }

    @GetMapping(value = "get-by-id")
    public ProjectReview getById(@RequestParam(value = "id") String id) {
        return projectReviewService.getById(id);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectReview> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectReviewService.getByProjectId(projectId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectReview projectReview) {
        return projectReviewService.add(projectReview);
    }

    @PostMapping(value = "add-form")
    @PreAuthorize("hasRole('Admin')")
    public String addForm(@RequestBody ProjectReviewForm projectReviewForm) {
        return projectBusinessService.addForm(projectReviewForm);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectReview projectReview) {
        return projectReviewService.delete(projectReview);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectReview projectReview) {
        return projectReviewService.update(projectReview);
    }


    /**
     * 增加临时文件
     *
     * @param uploadReviewFilesDir
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "add-project-material-review-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId)")
    public String addProjectMaterialReviewTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadReviewFilesDir") String uploadReviewFilesDir,
            @RequestParam("file") MultipartFile multipartFile) {
        return projectBusinessService.addProjectMaterialReviewTempFile(uploadReviewFilesDir, multipartFile);
    }

    /**
     * 删除临时文件
     *
     * @param uploadReviewFilesDir
     * @param fileName
     * @return
     */
    @PostMapping(value = "delete-project-material-review-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId)")
    public boolean deleteProjectMaterialReviewTempFile(
            @RequestParam("projectId") String projectId,
            @RequestParam("uploadPhotoFilesDir") String uploadReviewFilesDir,
            @RequestParam("fileName") String fileName) {
        return projectBusinessService.deleteProjectMaterialReviewTempFile(uploadReviewFilesDir, fileName);
    }


    @GetMapping(value = "page")
    public Page<ProjectReview> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectReviewService.getPage(pageNo, pageSize);
    }


    @GetMapping(value = "list-user-view-by-project-review-id")
    public List<ProjectReviewUserView> getProjectReviewUserViewListByProjectIdAndTaskId(
           @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value ="designCompanyIndex") int designCompanyIndex ) {

        return projectMaterialFlow.getProjectReviewUserViewListByProjectIdAndTaskId(projectId, taskId , designCompanyIndex);
    }



}