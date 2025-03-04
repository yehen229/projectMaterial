package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("log/v1")
@EnableMethodSecurity
public class LogController {
    private final ILogService logService;

    LogController(ILogService logService) {
        this.logService = logService;
    }

    @GetMapping(value = "get-by-id")
    public Log getById(@RequestParam(value = "id") String id) {
        return logService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<Log> getByUserId(@RequestParam(value = "userId") String userId) {
        return logService.getByUserId(userId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody Log log) {
        return logService.add(log);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody Log log) {
        return logService.delete(log);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody Log log) {
        return logService.update(log);
    }


    @GetMapping(value = "page")
    public Page<Log> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return logService.getPage(pageNo, pageSize);
    }

}