package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.supervision;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 通知禁止使用
 */
@Service
public class SupervisionCheckWhetherPassReCheck implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(SupervisionCheckWhetherPassReCheck.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IProjectUserService projectUserService;
    private final IUserService userService;

    public SupervisionCheckWhetherPassReCheck(IProjectOpHistoryBusiness projectHistoryBusiness,
                                              IUserService userService,
                                              IProjectUserService projectUserService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
        this.projectUserService = projectUserService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("工作流-监听器:NoticeOfProhibitedUse:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");

        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();

    }
}