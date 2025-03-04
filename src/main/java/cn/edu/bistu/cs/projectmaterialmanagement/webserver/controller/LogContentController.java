package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogContentService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logcontent/v1")
@EnableMethodSecurity
public class LogContentController {
    private final ILogContentService logContentService;

    LogContentController(ILogContentService logContentService) {
        this.logContentService = logContentService;
    }

    @GetMapping(value = "get-by-id")
    public LogContent getById(@RequestParam(value = "id") String id) {
        return logContentService.getById(id);
    }


    @GetMapping(value = "get-by-log-id")
    public List<LogContent> getByLogId(@RequestParam(value = "logId") String logId) {
        return logContentService.getByLogId(logId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody LogContent logContent) {
        return logContentService.add(logContent);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody LogContent logContent) {
        return logContentService.delete(logContent);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody LogContent logContent) {
        return logContentService.update(logContent);
    }


    @GetMapping(value = "page")
    public Page<LogContent> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return logContentService.getPage(pageNo, pageSize);
    }

}