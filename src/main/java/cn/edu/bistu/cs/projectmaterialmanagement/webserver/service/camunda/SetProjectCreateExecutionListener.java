package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 流程创建时，设置项目创建者
 */
@Service
public class SetProjectCreateExecutionListener implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetProjectCreateExecutionListener.class);

    private final ICompanyUserService companyUserService;

    public SetProjectCreateExecutionListener(ICompanyUserService companyUserService) {
        this.companyUserService = companyUserService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        List<CompanyUser> companyUserList = companyUserService.getByCompanyType("建设单位");

        List<String> projectCreatorList = new ArrayList<String>(); //分配任务的人员

        //设置总包人员，即总包公司的每个人（包括项目经理和项目员工均可以审批）


        if (companyUserList != null && !companyUserList.isEmpty()) {
            String users = "";
            for (CompanyUser companyUser : companyUserList) {
                projectCreatorList.add(companyUser.getUserId());
            }

        }

        //对应流程图Collection
        delegateExecution.setVariable("projectCreatorList", projectCreatorList);
    }
}
