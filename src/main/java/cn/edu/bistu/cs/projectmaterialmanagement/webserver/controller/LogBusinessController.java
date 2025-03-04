package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogBusinessService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logbusiness/v1")
@EnableMethodSecurity
public class LogBusinessController {
    private final ILogBusinessService logBusinessService;

    LogBusinessController(ILogBusinessService logBusinessService) {
        this.logBusinessService = logBusinessService;
    }

    @GetMapping(value = "get-by-id")
    public LogBusiness getById(@RequestParam(value = "id") String id) {
        return logBusinessService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody LogBusiness logBusiness) {
        return logBusinessService.add(logBusiness);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody LogBusiness logBusiness) {
        return logBusinessService.delete(logBusiness);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody LogBusiness logBusiness) {
        return logBusinessService.update(logBusiness);
    }


    @GetMapping(value = "page")
    public Page<LogBusiness> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return logBusinessService.getPage(pageNo, pageSize);
    }

}