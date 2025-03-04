package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBussinessOp;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogBussinessOpService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logbussinessop/v1")
@EnableMethodSecurity
public class LogBussinessOpController {
    private final ILogBussinessOpService logBussinessOpService;

    LogBussinessOpController(ILogBussinessOpService logBussinessOpService) {
        this.logBussinessOpService = logBussinessOpService;
    }

    @GetMapping(value = "get-by-id")
    public LogBussinessOp getById(@RequestParam(value = "id") String id) {
        return logBussinessOpService.getById(id);
    }


    @GetMapping(value = "get-by-log-id")
    public List<LogBussinessOp> getByLogId(@RequestParam(value = "logId") String logId) {
        return logBussinessOpService.getByLogId(logId);
    }


    @GetMapping(value = "get-by-log-business-id")
    public List<LogBussinessOp> getByLogBusinessId(@RequestParam(value = "logBusinessId") String logBusinessId) {
        return logBussinessOpService.getByLogBusinessId(logBusinessId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody LogBussinessOp logBussinessOp) {
        return logBussinessOpService.add(logBussinessOp);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody LogBussinessOp logBussinessOp) {
        return logBussinessOpService.delete(logBussinessOp);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody LogBussinessOp logBussinessOp) {
        return logBussinessOpService.update(logBussinessOp);
    }


    @GetMapping(value = "page")
    public Page<LogBussinessOp> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return logBussinessOpService.getPage(pageNo, pageSize);
    }

}