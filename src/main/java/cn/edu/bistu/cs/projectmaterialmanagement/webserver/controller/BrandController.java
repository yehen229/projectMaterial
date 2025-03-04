package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brand/v1")
@EnableMethodSecurity
public class BrandController {
    private final IBrandService brandService;

    BrandController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "get-by-id")
    public Brand getById(@RequestParam(value = "id") String id) {
        return brandService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody Brand brand) {
        return brandService.add(brand);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody Brand brand) {
        return brandService.delete(brand);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody Brand brand) {
        return brandService.update(brand);
    }


    @GetMapping(value = "page")
    public Page<Brand> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return brandService.getPage(pageNo, pageSize);
    }

}