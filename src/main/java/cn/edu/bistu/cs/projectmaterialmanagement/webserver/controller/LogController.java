package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.ProjectLogView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("log/v1")
@EnableMethodSecurity
public class LogController {
    private final ILogService logService;
    private final ILogRepository logRepository;

    LogController(ILogService logService, ILogRepository logRepository) {
        this.logService = logService;
        this.logRepository = logRepository;
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

    @PostMapping(value = "addtest")
    @PreAuthorize("hasRole('Admin')")
    public String addTest() {
        Log log1 = new Log("18138900153", "46846416165146894163", "测试1", 0b0001, new Date());
        logService.add(log1);
        return "123124";
    }


//    @PostMapping(value = "delete")
//    @PreAuthorize("hasRole('Admin')")
//    public int delete(@RequestBody Log log) {
//        return logService.delete(log);
//    }

//    @PostMapping(value = "update")
//    @PreAuthorize("hasRole('Admin')")
//    public int update(@RequestBody Log log) {
//        return logService.update(log);
//    }


    @GetMapping(value = "page")
    public Page<ProjectLogView> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return logService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "searchpage")
    public Page<ProjectLogView> getSearchPage(@RequestParam(value = "projectName", required = true) String projectName,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
//        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 1 ? pageSize : 2);
        return logService.getbynamePage(projectName, pageNo, pageSize);
    }
}