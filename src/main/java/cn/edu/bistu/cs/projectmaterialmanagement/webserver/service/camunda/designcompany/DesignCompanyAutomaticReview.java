package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designcompany;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designdepartment.ProjectMaterialIntoStorage;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DesignCompanyAutomaticReview implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialIntoStorage.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;

    public DesignCompanyAutomaticReview(IProjectOpHistoryBusiness projectHistoryBusiness,
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
        projectHistoryBusiness.addNotAffectAppearanceReviewDesignCompanyAutomaticReview(businessId, user.getId(),
                                                                                        "设计单位自动审核不影响外观",
                                                                                        "设计单位自动审核不影响外观");
    }
}