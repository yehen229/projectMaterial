package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublicView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brandexcel;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IBrandPublicRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandPublicService;
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

@RestController
@RequestMapping("brandpublic/v1")
@EnableMethodSecurity
public class BrandPublicController {
    private final IBrandPublicService brandPublicService;

    BrandPublicController(IBrandPublicService brandPublicService) {
        this.brandPublicService = brandPublicService;
    }

    @GetMapping(value = "get-by-id")
    public BrandPublic getById(@RequestParam(value = "id") String id) {
        return brandPublicService.getById(id);
    }


    @GetMapping(value = "get-by-brand-id")
    public List<BrandPublic> getByBrandId(@RequestParam(value = "brandId") String brandId) {
        return brandPublicService.getByBrandId(brandId);
    }

    @GetMapping(value = "get-by-material-classify-section-id")
    public List<BrandPublicView> getByMaterialClassifySectionId(@RequestParam(value = "materialClassifySectionId") String materialClassifySectionId) {
        return brandPublicService.getByMaterialClassifySectionId(materialClassifySectionId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody Brand brand) {
        return brandPublicService.add(brand);
    }
    @PostMapping(value = "Exceladd")
    @PreAuthorize("hasRole('Admin')")
    public String Exceladd(@RequestParam("file") MultipartFile file) {
        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Brand brand = new Brand();
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
                brand.setMaterialClassifySectionId(brandPublicService.MId(divisionclassname, groupclassname, sectionclassname));
                brand.setPosition(position);
                brandPublicService.add(brand);
            }
            wb.close();
            return "ok";
        } catch (Exception e) {
            return "faill";
        }
    }

    @PostMapping(value = "Exceldown")
    @PreAuthorize("hasRole('Admin')")
    public void Exceldown(HttpServletResponse response) throws IOException {

        brandPublicService.Exceldown(response);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody BrandPublic brandPublic) {
        return brandPublicService.delete(brandPublic);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody Brand brand) {
        return brandPublicService.update(brand);
    }


    @GetMapping(value = "page")
    public Page<BrandPublic> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return brandPublicService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-view")
    public Page<BrandPublicView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return brandPublicService.getPageView(pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-name")
    public Page<BrandPublicView> getPageViewByBrandName(
            @RequestParam(value = "brandName") String brandName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return brandPublicService.getPageViewByBrandName(brandName, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-position")
    public Page<BrandPublicView> getPageViewByPosition(@RequestParam(value = "brandPosition") String brandPosition,
                                                       @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return brandPublicService.getPageViewByBrandPosition(brandPosition, pageNo, pageSize);
    }

}