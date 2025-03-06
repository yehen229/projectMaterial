package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectMaterialArrival implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialArrival.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;
    private final ILogService logService;
    public ProjectMaterialArrival(IProjectOpHistoryBusiness projectHistoryBusiness,
                                  IUserService userService,
                                  ILogService logService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
        this.logService= logService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("工作流-监听器:DesignCompanyAutomaticReview:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");
        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();
        //增加审核历史过程
        projectHistoryBusiness.addProjectMaterialArrival(businessId, user.getId(),
                                                         "总包单位材料到货",
                                                         "总包单位材料到货");
        Log log = new Log(user.getId(), businessId, "总包单位材料到货", 0 , new Date());
        logService.add(log);

    }
}