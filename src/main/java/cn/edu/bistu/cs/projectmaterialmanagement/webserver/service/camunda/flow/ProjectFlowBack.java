package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectBackOrDeleteTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;


/**
 * 撤销、退回等操作
 */
@Service
public class ProjectFlowBack {


    private final IUserService userService;
    private final IProjectUserService projectUserService;
    private final IProjectService projectService;
    private final IProjectBusinessService projectBusinessService;
    private final IProjectReviewService projectReviewService;
    private final IProjectCompanyService projectCompanyService;
    private final ICompanyUserService companyUserService;

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private IdentityService identityService;
    @Resource
    private RepositoryService repositoryService;


    public ProjectFlowBack(IUserService userService,
                           IProjectUserService projectUserService,
                           IProjectService projectService,
                           IProjectBusinessService projectBusinessService, IProjectReviewService projectReviewService, IProjectCompanyService projectCompanyService, ICompanyUserService companyUserService) {
        this.userService = userService;
        this.projectUserService = projectUserService;
        this.projectService = projectService;
        this.projectBusinessService = projectBusinessService;
        this.projectReviewService = projectReviewService;
        this.projectCompanyService = projectCompanyService;
        this.companyUserService = companyUserService;
    }

    /**
     * 执行撤回操作
     *
     * @param projectBackOrDeleteTask
     * @return
     */
    public String backProcess(ProjectBackOrDeleteTask projectBackOrDeleteTask) {
        if (projectBackOrDeleteTask == null)
            throw new RuntimeException("参数错误");

        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new RuntimeException("当前用户未登录");

        String instanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(projectBackOrDeleteTask.getProjectId()).singleResult().getId();
        String taskId = projectBackOrDeleteTask.getTaskId();

        boolean taskCanBack = canBack(instanceId, taskId, user.getId());
        if (!taskCanBack)
            return "无法撤回";

        //流程撤回
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).taskAssignee(user.getId()).singleResult();
        if (historicTaskInstance == null)
            throw new RuntimeException("当前任务不存在");

        return "";

    }

    /**
     * 流程是否可以撤回
     *
     * @return
     */
    private boolean canBack(String instanceId, String taskId, String userId) {

        /**
         * 是否可以撤回？需要如下条件：
         * 1.上一个节点是当前用户审核的
         * 2.下一个节点审核人还未处理
         * 满足上面两个要求的才可以撤回
         */

        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId)
                .asc()
                .list();

        boolean canBack = false;

        boolean hasUserTask = false;
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            if (historicTaskInstance.getId().equalsIgnoreCase(taskId)
                    && historicTaskInstance.getAssignee().equalsIgnoreCase(userId)) {
                //找到了处理人处理的任务
                hasUserTask = true;
                continue;
            }
            if (hasUserTask) {
                //上一个任务是处理人处理的，下一个任务还未处理
                hasUserTask = false;
                if (historicTaskInstance.getEndTime() == null) {
                    canBack = true;
                    break;
                }
            }
        }

        return canBack;
    }


}
