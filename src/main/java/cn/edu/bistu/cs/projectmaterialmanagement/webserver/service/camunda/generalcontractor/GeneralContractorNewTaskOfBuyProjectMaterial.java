package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
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
 * @param
 * @return
 */
@Service
public class GeneralContractorNewTaskOfBuyProjectMaterial implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(GeneralContractorNewTaskOfSelectBrand.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;
    private final IProjectUserService projectUserService;

    public GeneralContractorNewTaskOfBuyProjectMaterial(IProjectOpHistoryBusiness projectHistoryBusiness,
                                                 IUserService userService,
                                                 IProjectUserService projectUserService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
        this.projectUserService = projectUserService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("工作流-监听器:GeneralContractorNewTaskOfBuyProjectMaterial:" + delegateExecution.getEventName());

        logger.info("工作流-监听器:Processing request by '" + delegateExecution.getVariable("customerId") + "'...");

        //项目的总包单位员工
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

    }
}


