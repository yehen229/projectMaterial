package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.supervision;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
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
public class NoticeOfProhibitedUse implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(NoticeOfProhibitedUse.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IProjectUserService projectUserService;
    private final IUserService userService;

    public NoticeOfProhibitedUse(IProjectOpHistoryBusiness projectHistoryBusiness,
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
        List<ProjectUser> projectUserList = projectUserService.getGeneralContractorCompanyEmployees(delegateExecution.getBusinessKey());

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
        projectHistoryBusiness.addSupervisionCompanyNotificationProhibition(businessId, user.getId(),
                                                                            "通知禁止使用",
                                                                            "通知禁止使用");
    }
}