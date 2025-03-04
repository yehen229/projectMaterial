package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designdepartment;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DesignDepartmentAutomaticManagerDirectlyReviewOfProjectMaterial implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialIntoStorage.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;

    public DesignDepartmentAutomaticManagerDirectlyReviewOfProjectMaterial(IProjectOpHistoryBusiness projectHistoryBusiness,
                                                                           IUserService userService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("工作流-监听器:DesignDepartmentAutomaticReview:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");

        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();
        
        //增加审核历史过程
        projectHistoryBusiness.addProjectMaterialReviewManagerDirectly(businessId, user.getId(),
                                                                       "设计部审核项目材料",
                                                                       "项目经理对项目材料进行审核，并给出审核结果",
                                                                       "");
    }
}