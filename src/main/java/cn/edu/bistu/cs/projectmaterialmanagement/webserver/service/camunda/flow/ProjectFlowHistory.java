package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectBackOrDeleteTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectUserCompletedTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda.ProjectUserTask;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * 查看历史流程、当前任务等
 */
@Service
public class ProjectFlowHistory {


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


    public ProjectFlowHistory(IUserService userService,
                              IProjectUserService projectUserService,
                              IProjectService projectService,
                              IProjectBusinessService projectBusinessService,
                              IProjectReviewService projectReviewService,
                              IProjectCompanyService projectCompanyService,
                              ICompanyUserService companyUserService) {
        this.userService = userService;
        this.projectUserService = projectUserService;
        this.projectService = projectService;
        this.projectBusinessService = projectBusinessService;
        this.projectReviewService = projectReviewService;
        this.projectCompanyService = projectCompanyService;
        this.companyUserService = companyUserService;
    }


    public void deleteHistoryProcess() {

        Page<Project> projectPage = projectService.getPage(1, 1000);
        for (Project project : projectPage.getResult()) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                                                            processInstanceBusinessKey(project.getId()).singleResult();

            if (processInstance != null)
                runtimeService.deleteProcessInstance(processInstance.getId(), "删除");

            projectBusinessService.delete(project);


        }

        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
        }


    }


    /**
     * 查询当前登录用户的Id
     *
     * @return
     */
    public List<ProjectUserTask> getTaskByCurrentLoginUser() {

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getId()).list();


        List<ProjectUserTask> projectUserTaskList = new ArrayList<>();
        for (Task task : taskList) {

            String projectId = (String) taskService.getVariable(task.getId(),
                                                                IProjectVariable.TASK_VARIABLE_PROJECT_ID);

            logger.info("当前登录用户任务-projectId:" + projectId);

            if (projectId != null) {
                ProjectUserTask projectUserTask = new ProjectUserTask();
                projectUserTask.setTaskId(task.getId());//待办任务ID
                projectUserTask.setTaskName(task.getName());//待办任务名称
                projectUserTask.setTaskDefinitionKey(task.getTaskDefinitionKey());//Activiti 任务定义ID
                projectUserTask.setProcessInstanceId(task.getProcessInstanceId());//流程实例ID

                User assigneeUser = userService.getById(task.getAssignee());

                if (assigneeUser != null) {
                    projectUserTask.setAssignee(assigneeUser);//待办任务接收人
                    projectUserTask.setDesignCompanyEmployee(
                            projectUserService.isUserDesignCompanyEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setDesignDepartmentEmployee(
                            projectUserService.isUserDesignDepartmentEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setDesignDepartmentManager(
                            projectUserService.isUserDesignDepartmentManager(projectId, assigneeUser.getId()));
                    projectUserTask.setEngineeringDepartmentEmployee(
                            projectUserService.isUserEngineeringDepartmentEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setEngineeringDepartmentManager(
                            projectUserService.isUserEngineeringDepartmentManager(projectId, assigneeUser.getId()));
                    projectUserTask.setGeneralContractorEmployee(
                            projectUserService.isUserGeneralContractorCompanyEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setSupervisionCompanyEmployee(
                            projectUserService.isUserSupervisionCompanyEmployee(projectId, assigneeUser.getId()));

                }


                projectUserTask.setProjectView(projectBusinessService.getProjectViewById(projectId));
                projectUserTaskList.add(projectUserTask);
            }

        }

        return projectUserTaskList;
    }

    /**
     * 得到当前用户在指定项目上的任务
     *
     * @param projectId
     * @return
     */
    public ProjectUserTask getTaskByCurrentLoginUserAndProjectId(String projectId) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        List<TaskEntity> taskList = (List) taskService.createTaskQuery().taskAssignee(user.getId()).list();


        for (Task task : taskList) {

            String projectIdVariable = (String) taskService.getVariable(task.getId(),
                                                                        IProjectVariable.TASK_VARIABLE_PROJECT_ID);

            if (projectIdVariable != null && projectIdVariable.equalsIgnoreCase(projectId)) {

                logger.info("当前登录用户任务-projectId:" + projectId);

                ProjectUserTask projectUserTask = new ProjectUserTask();
                projectUserTask.setTaskId(task.getId());//待办任务ID
                projectUserTask.setTaskName(task.getName());//待办任务名称
                projectUserTask.setProcessInstanceId(task.getProcessInstanceId());//流程实例ID
                projectUserTask.setTaskDefinitionKey(task.getTaskDefinitionKey());//Activiti 任务定义ID

                User assigneeUser = userService.getById(task.getAssignee());

                if (assigneeUser != null) {
                    projectUserTask.setAssignee(assigneeUser);//待办任务接收人
                    projectUserTask.setDesignCompanyEmployee(
                            projectUserService.isUserDesignCompanyEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setDesignDepartmentEmployee(
                            projectUserService.isUserDesignDepartmentEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setDesignDepartmentManager(
                            projectUserService.isUserDesignDepartmentManager(projectId, assigneeUser.getId()));
                    projectUserTask.setEngineeringDepartmentEmployee(
                            projectUserService.isUserEngineeringDepartmentEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setEngineeringDepartmentManager(
                            projectUserService.isUserEngineeringDepartmentManager(projectId, assigneeUser.getId()));
                    projectUserTask.setGeneralContractorEmployee(
                            projectUserService.isUserGeneralContractorCompanyEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setSupervisionCompanyEmployee(
                            projectUserService.isUserSupervisionCompanyEmployee(projectId, assigneeUser.getId()));

                }

                //项目材料ID
                String projectReviewId = (String) taskService.getVariable(task.getId(), "projectReviewId");
                projectUserTask.setProjectReviewId(projectReviewId);

                //总包单位选择品牌id
                String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                                   "useMaterialBrandSelectId");
                projectUserTask.setUseMaterialBrandSelectId(useMaterialBrandSelectId);

                projectUserTask.setProjectView(projectBusinessService.getProjectViewById(projectId));

                return projectUserTask;
            }


        }
        return null;
    }



    public ProjectUserTask getTaskByCurrentLoginUserAndProjectIdAndTaskId(String projectId,
                                                                          String taskId) {

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        List<TaskEntity> taskList = (List) taskService.createTaskQuery().taskAssignee(user.getId()).list();


        for (Task task : taskList) {
            if (!task.getId().equalsIgnoreCase(taskId))
                continue;

            String projectIdVariable = (String) taskService.getVariable(task.getId(),
                                                                        IProjectVariable.TASK_VARIABLE_PROJECT_ID);

            if (projectIdVariable != null && projectIdVariable.equalsIgnoreCase(projectId)) {

                logger.info("当前登录用户任务-projectId:" + projectId);

                ProjectUserTask projectUserTask = new ProjectUserTask();
                projectUserTask.setTaskId(task.getId());//待办任务ID
                projectUserTask.setTaskName(task.getName());//待办任务名称
                projectUserTask.setProcessInstanceId(task.getProcessInstanceId());//流程实例ID
                projectUserTask.setTaskDefinitionKey(task.getTaskDefinitionKey());//Activiti 任务定义ID

                User assigneeUser = userService.getById(task.getAssignee());

                if (assigneeUser != null) {
                    projectUserTask.setAssignee(assigneeUser);//待办任务接收人
                    projectUserTask.setDesignCompanyEmployee(
                            projectUserService.isUserDesignCompanyEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setDesignDepartmentEmployee(
                            projectUserService.isUserDesignDepartmentEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setDesignDepartmentManager(
                            projectUserService.isUserDesignDepartmentManager(projectId, assigneeUser.getId()));
                    projectUserTask.setEngineeringDepartmentEmployee(
                            projectUserService.isUserEngineeringDepartmentEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setEngineeringDepartmentManager(
                            projectUserService.isUserEngineeringDepartmentManager(projectId, assigneeUser.getId()));
                    projectUserTask.setGeneralContractorEmployee(
                            projectUserService.isUserGeneralContractorCompanyEmployee(projectId, assigneeUser.getId()));
                    projectUserTask.setSupervisionCompanyEmployee(
                            projectUserService.isUserSupervisionCompanyEmployee(projectId, assigneeUser.getId()));

                }

                //项目材料ID
                String projectReviewId = (String) taskService.getVariable(task.getId(), "projectReviewId");
                projectUserTask.setProjectReviewId(projectReviewId);

                //总包单位选择品牌id
                String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                                   "useMaterialBrandSelectId");
                projectUserTask.setUseMaterialBrandSelectId(useMaterialBrandSelectId);

                projectUserTask.setProjectView(projectBusinessService.getProjectViewById(projectId));

                return projectUserTask;
            }


        }
        return null;
    }


    /**
     * 列出当前登录用户已经办理的任务
     *
     * @return
     */
    public List<ProjectUserCompletedTask> getCompletedTaskByCurrentLoginUser() {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().taskAssignee(
                user.getId()).finished().list();


        List<ProjectUserCompletedTask> projectUserCompletedTaskList = new ArrayList<>();
        for (HistoricTaskInstance task : taskList) {
            try {

                String businessKey = historyService.createHistoricProcessInstanceQuery()
                                                   .processInstanceId(task.getProcessInstanceId())
                                                   .singleResult().getBusinessKey();

                String projectId = businessKey;
                //   if (task.getId() != null)
                //      projectId = (String) taskService.getVariable(task.getId(), IProjectVariable.TASK_VARIABLE_PROJECT_ID);

                System.out.println("businessKey:" + businessKey);
                System.out.println("name:" + task.getName());

                if (projectId != null) {
                    ProjectUserCompletedTask projectUserCompletedTask = new ProjectUserCompletedTask();
                    projectUserCompletedTask.setTaskId(task.getId());//待办任务ID
                    projectUserCompletedTask.setTaskName(task.getName());//待办任务名称
                    projectUserCompletedTask.setProcessInstanceId(task.getProcessInstanceId());//流程实例ID
                    projectUserCompletedTask.setTaskDefinitionKey(task.getTaskDefinitionKey());//Activiti 任务定义ID
                    User user1 = userService.getById(task.getAssignee());
                    projectUserCompletedTask.setAssignee(user1);//待办任务接收人
                    projectUserCompletedTask.setProjectView(projectBusinessService.getProjectViewById(projectId));
                    projectUserCompletedTask.setOperateDate(task.getStartTime());
                    projectUserCompletedTaskList.add(projectUserCompletedTask);
                }
            } catch (Exception e) {
                logger.info("历史任务获取失败");
            }
        }

        projectUserCompletedTaskList.sort((o1, o2) -> o2.getOperateDate().compareTo(o1.getOperateDate()));
        return projectUserCompletedTaskList;
    }


    Task getTaskByBusinessKey(String projectId,
                              String userId) {

        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(
                projectId).singleResult();
        if (instance == null) {
            return null;
        }

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instance.getId()).active().list();
        if (taskList.size() == 1) return taskList.get(0);

        return taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).active().taskAssignee(
                userId).singleResult();
    }


    // 历史活动查看(某一次流程的执行经历的多少步)

    public void queryHistoricActivityInstance(String processInstanceId) {
        System.out.println("processInstanceId:" + processInstanceId);
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                                                            // 过滤条件
                                                            .processInstanceId(processInstanceId)
                                                            // 分页条件
//     .listPage(firstResult, maxResults)
                                                            // 排序条件
                                                            .orderByHistoricActivityInstanceStartTime().asc()
                                                            // 执行查询
                                                            .list();
        for (HistoricActivityInstance hai : hais) {


            if (hai.getTaskId() != null) {
                System.out.println(
                        "=======================================================================================");
                System.out.print("activitiId:" + hai.getActivityId() + "，");
                System.out.print("name:" + hai.getActivityName() + "，");
                System.out.print("type:" + matching(hai.getActivityType()) + "，");
                System.out.println("pid:" + hai.getProcessInstanceId() + "，");
                System.out.println("TaskId:" + hai.getTaskId() + "，");
                System.out.print(
                        "startTime:" + DateFormatUtils.format(hai.getStartTime(), "yyyy-MM-dd HH:mm:ss") + "，");
                System.out.print("endTime:" + hai.getEndTime() + "，");
                System.out.print("duration:" + hai.getDurationInMillis());
                System.out.print("costTime:" + getDatePoor(hai.getEndTime(), hai.getStartTime()));

                String userId = hai.getAssignee();
                if (userId != null) {
                    User user = userService.getById(userId);
                    if (user != null)
                        System.out.println("assignee:" + user.getUserName() + "-" + user.getRealName() + "，");
                } else {
                    System.out.println("assignee:null，");
                }


                String taskId = hai.getTaskId();
                List<Comment> taskComments = taskService.getTaskComments(taskId);
                for (Comment comment : taskComments) {
                    System.out.println("comment:" + comment.getFullMessage());
                }

                String completedUser = (String) taskService.getVariable(hai.getTaskId(),
                                                                        IProjectVariable.TASK_VARIABLE_COMPLETE_USER);
                if (completedUser != null && !completedUser.equals("null")) {
                    User user = userService.getById(completedUser);
                    if (user != null)
                        System.out.println("任务完成人:" + user.getUserName() + "-" + user.getRealName());
                }


                List<UserOperationLogEntry> userOperationLogEntryList = historyService.createUserOperationLogQuery()
                                                                                      .taskId(hai.getTaskId())
                                                                                      .operationType(
                                                                                              UserOperationLogEntry.OPERATION_TYPE_EXECUTE)
                                                                                      .list();

                if (userOperationLogEntryList != null && !userOperationLogEntryList.isEmpty()) {


                    for (UserOperationLogEntry userOperationLogEntry : userOperationLogEntryList) {
                        userId = userOperationLogEntry.getUserId();
                        if (userId != null && !userId.equals("null")) {
                            User user = userService.getById(userId);
                            if (user != null)
                                System.out.println(
                                        "userOperationLogEntry:" + user.getUserName() + "-" + user.getRealName());
                        }

                    }


                    System.out.println();
                    System.out.println();
                    System.out.println();
                }
            }
        }
    }

    public void queryHistoricTaskInstance(String processInstanceId,
                                          String businessKey) {
        List<HistoricTaskInstance> htiL = historyService.createHistoricTaskInstanceQuery()
                                                        .processInstanceId(processInstanceId)
                                                        .orderByHistoricActivityInstanceStartTime()
                                                        .asc()
                                                        .list();

        for (HistoricTaskInstance hti : htiL) {
            if (hti.getEndTime() == null)
                continue;


            String taskId = hti.getId();
            System.out.println(
                    "=======================================================================================");


            List<Comment> taskComments = taskService.getTaskComments(taskId);
            for (Comment comment : taskComments) {
                System.out.println("comment:" + comment.getFullMessage());
            }


            System.out.println("name:" + hti.getName() + "，");
            System.out.println("id:" + hti.getId() + "，");
            String userId = hti.getAssignee();
            if (userId != null) {
                User user = userService.getById(userId);
                if (user != null)
                    System.out.println("assignee:" + user.getUserName() + "-" + user.getRealName() + "，");
            } else {
                System.out.println("assignee:null，");
            }


        }
    }


    public void queryHistoricVariableInstance(String processInstanceId) {
        List<HistoricVariableInstance> hviL = historyService.createHistoricVariableInstanceQuery()
                                                            .processInstanceId(processInstanceId)
                                                            .orderByVariableName()
                                                            .asc()
                                                            .list();

        for (HistoricVariableInstance hvi : hviL) {
            System.out.println(
                    "=======================================================================================");
            System.out.println("name:" + hvi.getVariableName() + "，");
            System.out.println("value:" + hvi.getValue());
            System.out.println("type:" + hvi.getVariableTypeName());
            System.out.println("taskId:" + hvi.getTaskId());
            System.out.println("procInstId:" + hvi.getProcessInstanceId());
            System.out.println("executionId:" + hvi.getExecutionId());


        }
    }


    /**
     * 获得项目的历史记录
     *
     * @param projectId 项目Id
     * @return
     */
    public List<ProjectHistory> getTaskHistoryByProjectId(String projectId) {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                                                           .processInstanceBusinessKey(projectId)
                                                           .orderByProcessInstanceStartTime().asc().list();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(
                projectId).singleResult();
        List<Map<String, Object>> result = new ArrayList<>(list.size());
        System.out.println(list.size());
        for (HistoricProcessInstance historicProcessInstance : list) {

            historicProcessInstance.getStartTime();
            System.out.println("historicProcessInstance.getStartTime():" + historicProcessInstance.getStartTime());
            String superProcessInstanceId = historicProcessInstance.getSuperProcessInstanceId();
            historicProcessInstance.getId();
            //queryHistoricActivityInstance(historicProcessInstance.getId());
            //queryHistoricVariableInstance(processInstance.getProcessInstanceId());
            // queryHistoricTaskInstance(processInstance.getProcessInstanceId(), projectId);
            Map<String, Object> map = new HashMap<>(5);
            String taskId = historicProcessInstance.getBusinessKey();
            List<Comment> taskComments = taskService.getTaskComments(taskId);
            System.out.println(taskComments.size());
            //   map.put("activityName", historicActivityInstance.getActivityName());
            //   map.put("activityType", matching(historicActivityInstance.getActivityType()));
            //   map.put("assignee", historicActivityInstance.getAssignee() == null ? "无" : historicActivityInstance.getAssignee());
            //   map.put("startTime", DateFormatUtils.format(historicActivityInstance.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
            //   map.put("endTime", DateFormatUtils.format(historicActivityInstance.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            //   map.put("costTime", getDatePoor(historicActivityInstance.getEndTime(), historicActivityInstance.getStartTime()));


            result.add(map);
        }

        return null;

    }


    private String matching(String ActivityType) {
        String value = "";
        switch (ActivityType) {
            case "startEvent":
                value = "流程开始";
                break;
            case "userTask":
                value = "用户处理任务";
                break;
            case "noneEndEvent":
                value = "流程结束";
                break;
            case "exclusiveGateway":
                value = "排他网关";
                break;
            case "serviceTask":
                value = "服务任务";
                break;
            case "multiInstanceBody":
                value = "多实例任务";
                break;
            default:
                value = "未知节点";
                break;
        }
        return ActivityType + "-" + value;
    }

    public String getDatePoor(Date endDate,
                              Date nowDate) {

        if (endDate == null || nowDate == null)
            return "";
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
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

        String instanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(
                projectBackOrDeleteTask.getProjectId()).singleResult().getId();
        String taskId = projectBackOrDeleteTask.getTaskId();

        boolean taskCanBack = canBack(instanceId, taskId, user.getId());
        if (!taskCanBack)
            return "无法撤回";

        //流程撤回
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                instanceId).singleResult();
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(
                taskId).taskAssignee(user.getId()).singleResult();
        if (historicTaskInstance == null)
            throw new RuntimeException("当前任务不存在");

        return "";

    }

    /**
     * 流程是否可以撤回
     *
     * @return
     */
    private boolean canBack(String instanceId,
                            String taskId,
                            String userId) {

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
