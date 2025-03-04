package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublicView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandPublicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

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