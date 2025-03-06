package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("projectmaterial/v1")
@EnableMethodSecurity
public class ProjectMaterialController {
    private final IProjectMaterialService projectMaterialService;
    private final IProjectMaterialBusinessService projectMaterialBusinessService;


    ProjectMaterialController(IProjectMaterialService projectMaterialService,
                              IProjectMaterialBusinessService projectMaterialBusinessService) {
        this.projectMaterialService = projectMaterialService;
        this.projectMaterialBusinessService = projectMaterialBusinessService;

    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterial getById(@RequestParam(value = "id") String id) {
        return projectMaterialService.getById(id);
    }
    @GetMapping(value = "get-view-by-id")
    public ProjectMaterialView getViewById(@RequestParam(value = "id") String id) {
        return projectMaterialBusinessService.getViewById(id);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectMaterial> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectMaterialService.getByProjectId(projectId);
    }


    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterial projectMaterial) {
        return projectMaterialService.add(projectMaterial);
    }

    /**
     * 新增项目材料
     * @param projectMaterialForm 项目材料表单
     * @return ID
     */
    @PostMapping(value = "add-form")
    @PreAuthorize("hasRole('Admin')  or  @ProjectPermission.isInDesignCompany(#projectMaterialForm.projectMaterial.projectId)")
    public String addForm(@RequestBody ProjectMaterialForm projectMaterialForm) {
        return projectMaterialBusinessService.addForm(projectMaterialForm);
    }


    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInDesignCompany(#projectMaterial.projectId)")
    public int delete(@RequestBody ProjectMaterial projectMaterial) {
        return projectMaterialService.delete(projectMaterial);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInDesignCompany(#projectMaterialForm.projectMaterial.projectId)")
    public int update(@RequestBody ProjectMaterial projectMaterial) {
        return projectMaterialService.update(projectMaterial);
    }

    @PostMapping(value = "update-form")
    @PreAuthorize("hasRole('Admin') or @ProjectPermission.isInDesignCompany(#projectMaterialForm.projectMaterial.projectId)")
    public int updateForm(@RequestBody ProjectMaterialForm projectMaterialForm) {
        return projectMaterialBusinessService.updateForm(projectMaterialForm);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterial> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-view")
    public Page<ProjectMaterialView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialBusinessService.getPageView(pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-project-id")
    public Page<ProjectMaterialView> getPageViewByProjectId(
            @RequestParam(value = "projectId", required = true) String projectId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "itemMark", required = false) String itemMark,
            @RequestParam(value = "technology", required = false) String technology,
            @RequestParam(value = "installation", required = false) String installation,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "brandPrivate", required = false) String brandPrivate,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialBusinessService.getPageViewByProjectId(projectId, name, location, itemMark, technology, installation, brand, brandPrivate, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-design-company-of-current-user-and-project-id")
    public Page<ProjectMaterialView> getPageViewByCurrentUserAndProjectId(
            @RequestParam(value = "projectId", required = true) String projectId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialBusinessService.getPageViewByCurrentUserAndProjectId(projectId, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-search")
    public Page<ProjectMaterialView> getPageViewBySearch(
            @RequestParam(value = "projectId", required = true) String projectId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "itemMark", required = false) String itemMark,
            @RequestParam(value = "technology", required = false) String technology,
            @RequestParam(value = "installation", required = false) String installation,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "brandPrivate", required = false) String brandPrivate,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialBusinessService.getSearchViewByCurrentUserAndProjectId(projectId, name, location, itemMark, technology, installation, brand, brandPrivate, pageNo, pageSize);
    }

    @GetMapping(value = "download-project-material-excel-by-design-company-of-current-user-and-project-id")
    public DownloadFile downloadProjectMaterialExcelByDesignCompanyOfCurrentUserAndProjectId(
            @RequestParam(value = "projectId", required = true) String projectId,
            HttpServletRequest request,
            HttpServletResponse response) {
        projectMaterialBusinessService.getProjectMaterialExcel(projectId, request, response);

        return new DownloadFile();
    }

    /**
     * 通过Excel文件导入材料
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping(value = "add-project-material-excel")
    public int addProjectMaterialExcel(
            @RequestParam("file")
            MultipartFile multipartFile)
            throws Exception {
        return projectMaterialBusinessService.addProjectMaterialExcel(multipartFile);
    }

}