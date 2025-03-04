package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryBusiness;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 生成二维码
 */
@Service
public class ProjectFlowMerge implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(ProjectFlowMerge.class);
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IUserService userService;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;

    public ProjectFlowMerge(IProjectOpHistoryBusiness projectHistoryBusiness,
                            IUserService userService) {
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.userService = userService;
    }

    //        1.根据项目号找到所有的流程
//        2。在这些流程中，找到走到订购的流程，然后保留1个
//                                    （从头最远走到订购）
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String businessId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();
        //增加审核历史过程

    }}
