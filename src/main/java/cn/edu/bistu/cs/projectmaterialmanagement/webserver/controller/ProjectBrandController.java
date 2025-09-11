package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandPublicService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("projectbrand/v1")
@EnableMethodSecurity
public class ProjectBrandController {
    private final IProjectBrandService projectBrandService;
    private final IBrandPublicService brandPublicService;
    private final IBrandService brandService;

    ProjectBrandController(IProjectBrandService projectBrandService, IBrandPublicService brandPublicService, IBrandService brandService) {
        this.projectBrandService = projectBrandService;
        this.brandPublicService = brandPublicService;
        this.brandService = brandService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectBrand getById(@RequestParam(value = "id") String id) {
        return projectBrandService.getById(id);
    }


    @GetMapping(value = "get-list-by-project-id")
    public List<ProjectBrand> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectBrandService.getByProjectId(projectId);
    }

    @GetMapping(value = "get-view-list-by-project-id")
    public List<ProjectBrandView> getViewListByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectBrandService.getViewListByProjectId(projectId);
    }


    @GetMapping(value = "get-by-brand-id")
    public List<ProjectBrand> getByBrandId(@RequestParam(value = "brandId") String brandId) {
        return projectBrandService.getByBrandId(brandId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectBrand projectBrand) {
        return projectBrandService.add(projectBrand);
    }

    @PostMapping(value = "add-form")
    @PreAuthorize("hasRole('Admin')")
    public String addForm(@RequestBody ProjectBrandForm projectBrandForm) {
        return projectBrandService.addForm(projectBrandForm);
    }


    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectBrand projectBrand) {
        return projectBrandService.delete(projectBrand);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectBrand projectBrand) {
        return projectBrandService.update(projectBrand);
    }


    @GetMapping(value = "page")
    public Page<ProjectBrand> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-view")
    public Page<ProjectBrandView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPageView(pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-project-id")
    public Page<ProjectBrandView> getPageView(@RequestParam(value = "projectId", required = true) String projectId,
                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPageViewByProjectId(projectId, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-project-id-position")
    public Page<ProjectBrandView> getPageViewByPosition(@RequestParam(value = "projectId", required = true) String projectId,
                                                        @RequestParam(value = "position", required = true) String position,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPageViewByProjectIdAndPosition(projectId, position, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-project-id-brand-name")
    public Page<ProjectBrandView> getPageViewByBrandName(@RequestParam(value = "projectId", required = true) String projectId,
                                                         @RequestParam(value = "brandName", required = true) String brandName,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectBrandService.getPageViewByProjectIdAndBrandName(projectId, brandName, pageNo, pageSize);
    }

    @PostMapping(value = "PrivateExceladd")
    @PreAuthorize("hasRole('Admin')")
    public String PrivateExceladd(@RequestParam(value = "projectId", required = true) String projectId,
            @RequestParam("file") MultipartFile file) {
        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Brand brand = new Brand();
                ProjectBrand projectBrand = new ProjectBrand();
                String name = row.getCell(4).getStringCellValue().trim();
                String divisionclassname = row.getCell(0).getStringCellValue().trim();
                String groupclassname = row.getCell(1).getStringCellValue().trim();
                String sectionclassname = row.getCell(2).getStringCellValue().trim();
                String position = row.getCell(3).getStringCellValue().trim();
                String value = row.getCell(5) != null ? row.getCell(5).toString().trim() : "";
                if (!value.isEmpty()) {
                    value=brandPublicService.FId(value);
                    brand.setFactory_id(value);
                }
                brand.setName(name);
                brand.setMaterialClassifySectionId(brandPublicService.MId(divisionclassname,groupclassname,sectionclassname));
                brand.setPosition(position);
                brand.setId(brandService.addAndGetId(brand));
                projectBrand.setProjectId(projectId);
                projectBrand.setBrandId(brand.getId());
                projectBrandService.add(projectBrand);
            }
            wb.close();
            return "ok";
        } catch (Exception e) {
            return "faill";
        }
    }

    @PostMapping(value = "PrivateExceldown")
    @PreAuthorize("hasRole('Admin')")
    public void PrivateExceldown(@RequestParam(value = "projectId", required = true) String projectId,
            HttpServletResponse response) throws IOException {
        projectBrandService.PrivateExceldown(projectId, response);
    }

}