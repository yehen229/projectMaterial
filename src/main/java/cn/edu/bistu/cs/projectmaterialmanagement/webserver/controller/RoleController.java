package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role/v1")
@EnableMethodSecurity
public class RoleController {
    private final IRoleService roleService;

    RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "get-by-id")
    public Role getById(@RequestParam(value = "id") String id) {
        return roleService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody Role role) {
        return roleService.add(role);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody Role role) {
        return roleService.delete(role);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody Role role) {
        return roleService.update(role);
    }

    @GetMapping(value = "page")
    public Page<Role> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return roleService.getPage(pageNo, pageSize);
    }

}