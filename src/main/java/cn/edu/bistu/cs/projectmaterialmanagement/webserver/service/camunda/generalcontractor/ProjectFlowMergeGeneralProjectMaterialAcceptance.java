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
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectFlowMergeGeneralProjectMaterialAcceptance implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(ProjectFlowMergeGeneralProjectMaterialAcceptance.class);

    private final IProjectUserService projectUserService;
    private final IUserService userService;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    public ProjectFlowMergeGeneralProjectMaterialAcceptance(IProjectUserService projectUserService,
                                                            IUserService userService) {
        this.projectUserService = projectUserService;
        this.userService = userService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
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
                boolean isNewAcceptanceTask = Boolean.TRUE.equals(taskService.getVariable(task.getId(), "isNewAcceptanceTask"));
                if (task.getTaskDefinitionKey().equalsIgnoreCase("Activity_General_Contractor_Batch_Acceptance")) {
                    if (isNewAcceptanceTask) {
                        instancesReadyForOrder.add(instance);
                    } else {
                        instancesNotReadyForOrder.add(instance);
                    }
                }
            }
        }

        if (!instancesReadyForOrder.isEmpty()) {
            for (ProcessInstance instance : instancesReadyForOrder) {
                try {
                    runtimeService.deleteProcessInstance(instance.getId(), "Merged instances, keeping one");

                } catch (Exception e) {
                    logger.error("Error deleting instance: {}", instance.getId(), e);
                }
            }
        } else {

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
