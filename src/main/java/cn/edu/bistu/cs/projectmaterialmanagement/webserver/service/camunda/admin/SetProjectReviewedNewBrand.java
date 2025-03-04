package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.admin;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 总包选择了品牌、物料申请后，将审批通过的新品牌增加到项目私有库中
 */
@Service
public class SetProjectReviewedNewBrand implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(SetProjectReviewedNewBrand.class);

    private final IProjectBusinessService projectBusinessService;

    public SetProjectReviewedNewBrand(IProjectBusinessService projectBusinessService) {
        this.projectBusinessService = projectBusinessService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("工作流-监听器:SetProjectReviewedNewBrand:" + delegateExecution.getEventName());
        
        //将批准的新品牌增加到项目私有库中
        String projectId = delegateExecution.getBusinessKey();
        projectBusinessService.setProjectReviewedNewBrandByProjectId(projectId);
    }
}