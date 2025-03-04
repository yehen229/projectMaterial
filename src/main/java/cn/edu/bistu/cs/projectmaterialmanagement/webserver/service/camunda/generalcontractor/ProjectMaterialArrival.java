package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectMaterialArrival implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialArrival.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;

    public ProjectMaterialArrival(IProjectOpHistoryBusiness projectHistoryBusiness,
                                  IUserService userService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
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

    }
}