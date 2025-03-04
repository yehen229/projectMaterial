package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 生成二维码
 */
@Service
public class GeneralContractorUseMaterials implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(GeneralContractorUseMaterials.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;

    public GeneralContractorUseMaterials(IProjectOpHistoryBusiness projectHistoryBusiness,
                                         IUserService userService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("工作流-监听器:GenerateQRCode:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");

        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();
        //增加审核历史过程
        projectHistoryBusiness.addGeneralContractorCompanyForConstructionUse(businessId, user.getId(),
                                                                             "使用物料",
                                                                             "使用物料");
    }
}