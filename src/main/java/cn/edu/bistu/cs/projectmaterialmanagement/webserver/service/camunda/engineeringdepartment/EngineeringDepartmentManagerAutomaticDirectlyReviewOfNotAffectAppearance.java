package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.engineeringdepartment;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designdepartment.ProjectMaterialIntoStorage;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class EngineeringDepartmentManagerAutomaticDirectlyReviewOfNotAffectAppearance implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialIntoStorage.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;

    public EngineeringDepartmentManagerAutomaticDirectlyReviewOfNotAffectAppearance(IProjectOpHistoryBusiness projectHistoryBusiness,
                                                                                    IUserService userService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(
                "工作流-监听器:ProjectMaterialManualHandedOverToEngineeringDepartment:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");

        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();


        //增加审核历史过程
        projectHistoryBusiness.addNotAffectAppearanceReviewEngineeringDepartmentManagerDirectly(
                businessId,
                user.getId(),
                "工程部审核不影响外观的物料使用申请",
                "项目经理直接对不影响外观的品牌、物料申请等进行审核",
                ""
        );
    }
}