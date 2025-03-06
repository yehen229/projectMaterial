package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designdepartment.ProjectMaterialIntoStorage;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class GeneralContractorNewProjectMaterialAcceptance implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectMaterialIntoStorage.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;
    private final IProjectUserService projectUserService;
    private final ILogService logService;
    public GeneralContractorNewProjectMaterialAcceptance(IProjectOpHistoryBusiness projectHistoryBusiness,
                                                         IUserService userService,
                                                         IProjectUserService projectUserService,
                                                         ILogService logService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
        this.projectUserService = projectUserService;
        this.logService= logService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(
                "工作流-监听器:ProjectMaterialManualHandedOverToEngineeringDepartment:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");

        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();
        List<ProjectUser> projectUserList = projectUserService.getGeneralContractorCompanyEmployees(
                delegateExecution.getBusinessKey());

        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

        //设置总包人员，即总包公司的每个人（包括项目经理和项目员工均可以审批）
        if (projectUserList != null && !projectUserList.isEmpty()) {
            String users = "";
            for (ProjectUser projectUser : projectUserList) {
                //delegateExecution.addCandidateUser(projectUser.getUserId());
                assigneeList.add(projectUser.getUserId());
            }

        }

        //对应流程图Collection
        delegateExecution.setVariable("generalContractorCompanyEmployeeList", assigneeList);

        //增加审核历史过程
        projectHistoryBusiness.addAcceptanceReviewEngineeringDepartmentManagerDirectly(
                businessId,
                user.getId(),
                "总包单位发起新建项目验收",
                "总包单位发起项目验收",
                ""
        );
        Log log = new Log(user.getId(), businessId, "总包单位发起项目验收", 0 , new Date());
        logService.add(log);
    }
}