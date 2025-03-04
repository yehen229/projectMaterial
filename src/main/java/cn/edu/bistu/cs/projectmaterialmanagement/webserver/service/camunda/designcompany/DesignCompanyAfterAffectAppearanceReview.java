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
public class DesignCompanyAfterAffectAppearanceReview implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialIntoStorage.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;

    public DesignCompanyAfterAffectAppearanceReview(IProjectOpHistoryBusiness projectHistoryBusiness,
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

        //最多有八个设计单位
        int nAffectAppearanceDesignCompanyReviewResult0 = -1;
        int nAffectAppearanceDesignCompanyReviewResult1 = -1;
        int nAffectAppearanceDesignCompanyReviewResult2 = -1;
        int nAffectAppearanceDesignCompanyReviewResult3 = -1;
        int nAffectAppearanceDesignCompanyReviewResult4 = -1;
        int nAffectAppearanceDesignCompanyReviewResult5 = -1;
        int nAffectAppearanceDesignCompanyReviewResult6 = -1;
        int nAffectAppearanceDesignCompanyReviewResult7 = -1;


        Object objAffectAppearanceDesignCompanyReviewResult0 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult0");
        if (objAffectAppearanceDesignCompanyReviewResult0 != null)
            nAffectAppearanceDesignCompanyReviewResult0 = (int) objAffectAppearanceDesignCompanyReviewResult0;

        Object objAffectAppearanceDesignCompanyReviewResult1 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult1");
        if (objAffectAppearanceDesignCompanyReviewResult1 != null)
            nAffectAppearanceDesignCompanyReviewResult1 = (int) objAffectAppearanceDesignCompanyReviewResult1;

        Object objAffectAppearanceDesignCompanyReviewResult2 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult2");
        if (objAffectAppearanceDesignCompanyReviewResult2 != null)
            nAffectAppearanceDesignCompanyReviewResult2 = (int) objAffectAppearanceDesignCompanyReviewResult2;

        Object objAffectAppearanceDesignCompanyReviewResult3 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult3");
        if (objAffectAppearanceDesignCompanyReviewResult3 != null)
            nAffectAppearanceDesignCompanyReviewResult3 = (int) objAffectAppearanceDesignCompanyReviewResult3;

        Object objAffectAppearanceDesignCompanyReviewResult4 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult4");
        if (objAffectAppearanceDesignCompanyReviewResult4 != null)
            nAffectAppearanceDesignCompanyReviewResult4 = (int) objAffectAppearanceDesignCompanyReviewResult4;

        Object objAffectAppearanceDesignCompanyReviewResult5 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult5");
        if (objAffectAppearanceDesignCompanyReviewResult5 != null)
            nAffectAppearanceDesignCompanyReviewResult5 = (int) objAffectAppearanceDesignCompanyReviewResult5;

        Object objAffectAppearanceDesignCompanyReviewResult6 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult6");
        if (objAffectAppearanceDesignCompanyReviewResult6 != null)
            nAffectAppearanceDesignCompanyReviewResult6 = (int) objAffectAppearanceDesignCompanyReviewResult6;

        Object objAffectAppearanceDesignCompanyReviewResult7 = delegateExecution.getVariable(
                "nAffectAppearanceDesignCompanyReviewResult7");
        if (objAffectAppearanceDesignCompanyReviewResult7 != null)
            nAffectAppearanceDesignCompanyReviewResult7 = (int) objAffectAppearanceDesignCompanyReviewResult7;

        int nAffectAppearanceDesignCompanyReviewResult = 0;
        int nCount = 0;
        int nSum = 0;
        if (nAffectAppearanceDesignCompanyReviewResult0 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult0;
        }

        if (nAffectAppearanceDesignCompanyReviewResult1 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult1;
        }

        if (nAffectAppearanceDesignCompanyReviewResult2 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult2;
        }

        if (nAffectAppearanceDesignCompanyReviewResult3 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult3;
        }

        if (nAffectAppearanceDesignCompanyReviewResult4 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult4;
        }

        if (nAffectAppearanceDesignCompanyReviewResult5 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult5;
        }

        if (nAffectAppearanceDesignCompanyReviewResult6 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult6;
        }

        if (nAffectAppearanceDesignCompanyReviewResult7 >= 0) {
            nCount++;
            nSum += nAffectAppearanceDesignCompanyReviewResult7;
        }

        if (nCount == nSum)
            delegateExecution.setVariable("nAffectAppearanceDesignCompanyReviewResult",
                                          1);
        else
            delegateExecution.setVariable("nAffectAppearanceDesignCompanyReviewResult",
                                          0);


    }
}