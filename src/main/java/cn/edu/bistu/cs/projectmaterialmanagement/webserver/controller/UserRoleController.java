package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRole;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserRoleService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userrole/v1")
@EnableMethodSecurity
public class UserRoleController {
    private final IUserRoleService userRoleService;

    UserRoleController(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping(value = "get-by-id")
    public UserRole getById(@RequestParam(value = "id") String id) {
        return userRoleService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<UserRole> getByUserId(@RequestParam(value = "userId") String userId) {
        return userRoleService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-role-id")
    public List<UserRole> getByRoleId(@RequestParam(value = "roleId") String roleId) {
        return userRoleService.getByRoleId(roleId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody UserRole userRole) {
        return userRoleService.add(userRole);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody UserRole userRole) {
        return userRoleService.delete(userRole);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody UserRole userRole) {
        return userRoleService.update(userRole);
    }


    @GetMapping(value = "page")
    public Page<UserRole> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return userRoleService.getPage(pageNo, pageSize);
    }

}