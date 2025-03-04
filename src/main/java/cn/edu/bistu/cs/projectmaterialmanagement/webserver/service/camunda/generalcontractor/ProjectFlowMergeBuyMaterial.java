package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectFlowMergeBuyMaterial implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(ProjectFlowMergeBuyMaterial.class);

    private final IProjectUserService projectUserService;
    private final IUserService userService;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;

    public ProjectFlowMergeBuyMaterial(IProjectUserService projectUserService,
                                       IUserService userService) {
        this.projectUserService = projectUserService;
        this.userService = userService;

    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        //项目的总包单位员工
        String projectId = delegateExecution.getBusinessKey();
        User user = userService.getCurrentLoginUser();
        logger.info("Start merging instances for project ID: {}, user ID: {}", projectId, user.getId());

        ProjectFlowMerge(projectId, user.getId(), delegateExecution);

    }
    private void ProjectFlowMerge(String projectId, String userId, DelegateExecution delegateExecution) {
        List<ProcessInstance> allInstances = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(projectId)
                .list();

        if (allInstances == null || allInstances.isEmpty()) {

            return;
        }

        List<ProcessInstance> instancesReadyForOrder = new ArrayList<>();
        List<ProcessInstance> instancesNotReadyForOrder = new ArrayList<>();

        for (ProcessInstance instance : allInstances) {
            List<Task> activeTasks = taskService.createTaskQuery()
                    .processInstanceId(instance.getId())
                    .active()
                    .list();

            for (Task task : activeTasks) {
                boolean isNewOrderTask = Boolean.TRUE.equals(taskService.getVariable(task.getId(), "newOrderTask"));
                if (task.getTaskDefinitionKey().equalsIgnoreCase("Activity_General_Contractor_Order")) {
                    if (isNewOrderTask) {
                        instancesReadyForOrder.add(instance);
                    } else {
                        instancesNotReadyForOrder.add(instance);
                    }
                }
            }
        }

        if (!instancesReadyForOrder.isEmpty()) {
            // 删除新准备订购的实例
            for (ProcessInstance instance : instancesReadyForOrder) {
                try {
                    runtimeService.deleteProcessInstance(instance.getId(), "Merged instances, keeping one");

                } catch (Exception e) {
                    logger.error("Error deleting instance: {}", instance.getId(), e);
                }
            }
        } else {
            // 保留一个准备订购的实例
            for (int i = 1; i < instancesNotReadyForOrder.size(); i++) {
                try {
                    runtimeService.deleteProcessInstance(instancesNotReadyForOrder.get(i).getId(), "Merged instances, keeping one");

                } catch (Exception e) {
                    logger.error("Error deleting instance: {}", instancesNotReadyForOrder.get(i).getId(), e);
                }
            }
        }
    }
}
