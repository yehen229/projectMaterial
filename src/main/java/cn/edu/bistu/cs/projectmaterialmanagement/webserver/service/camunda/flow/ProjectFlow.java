package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ProjectFlow {


    private final IUserService userService;
    private final ICompanyUserService companyUserService;
    private final IProjectDesignCompanyService projectDesignCompanyService;
    private final IProjectService projectService;
    private final IProjectBusinessService projectBusinessService;
    private final IProjectReviewService projectReviewService;
    private final IProjectCompanyService projectCompanyService;
    private final IProjectOpHistoryBusiness projectHistoryBusiness;
    private final IProjectUserService projectUserService;
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
    @Autowired
    private CompanyUser companyUser;

    public ProjectFlow(IUserService userService,
                       IProjectUserService projectUserService,
                       ICompanyUserService companyUserService,
                       IProjectDesignCompanyService projectDesignCompanyService,
                       IProjectService projectService,
                       IProjectBusinessService projectBusinessService,
                       IProjectReviewService projectReviewService,
                       IProjectCompanyService projectCompanyService,
                       IProjectOpHistoryBusiness projectHistoryBusiness,
                       IProjectUserService projectUserService1) {
        this.userService = userService;
        this.companyUserService = companyUserService;
        this.projectDesignCompanyService = projectDesignCompanyService;
        this.projectService = projectService;
        this.projectBusinessService = projectBusinessService;
        this.projectReviewService = projectReviewService;
        this.projectCompanyService = projectCompanyService;
        this.projectHistoryBusiness = projectHistoryBusiness;
        this.projectUserService = projectUserService1;
    }

    /**
     * 根据用户id和项目id，获得该用户所在的设计单位的序号，依靠此序号设置相关变量
     *
     * @param userId
     * @param projectId
     * @return
     */
    private int getDesignCompanyIndexByUserIdAndProjectId(String taskId,
                                                          String userId,
                                                          String projectId) {
        Project project = projectService.getById(projectId);
        if (project == null)
            return -1;
        CompanyUser companyUser = companyUserService.getByUserId(userId);
        if (companyUser == null)
            return -1;

        ProjectUser projectUser = projectUserService.getByUserIdAndProjectId(userId, projectId);
        if (projectUser == null)
            return -1;

        ProjectDesignCompany projectDesignCompany = projectDesignCompanyService.getByProjectIdAndDesignCompanyId(
                projectId, companyUser.getCompanyId());

        if (projectDesignCompany == null)
            return -1;

        //最多只有八个设计单位，因此这里循环8次，找到对应的序号
        for (int i = 0; i < 8; i++) {
            Object object = taskService.getVariable(taskId, "nDesignCompanyId" + i);
            if (object != null && object.toString().equalsIgnoreCase(projectDesignCompany.getDesignCompanyId()))
                return i;
        }
        return -1;


    }

    private int getDesignCompanyIndexByTaskIdAndDesignCompanyIndex(String taskId,
                                                                   Integer designCompanyIndex) {


        //最多只有八个设计单位，因此这里循环8次，找到对应的序号

        Object object = taskService.getVariable(taskId, "nDesignCompanyId" + designCompanyIndex);
        if (object != null && !object.toString().isEmpty())
            return designCompanyIndex;
        return -1;
    }

    private String getDesignCompanyIdByTaskIdAndDesignCompanyIndex(String taskId,
                                                                   Integer designCompanyIndex) {


        //最多只有八个设计单位，因此这里循环8次，找到对应的序号

        Object object = taskService.getVariable(taskId, "nDesignCompanyId" + designCompanyIndex);
        if (object != null && !object.toString().isEmpty())
            return object.toString();
        return null;
    }


    /**
     * 设置任务完成人
     *
     * @param taskId
     * @param userId
     */
    private void setTaskCompletedUser(String taskId,
                                      String userId) {
        taskService.setVariableLocal(taskId, IProjectVariable.TASK_VARIABLE_COMPLETE_USER, userId);
    }

    /**
     * 设置任务已经完成
     *
     * @param task               任务
     * @param commentDescription 任务注释
     */
    private void setTaskComplete(Task task,
                                 String commentDescription,
                                 User user) {

        //设置任务完成人
        setTaskCompletedUser(task.getId(), user.getId());

        Comment comment = taskService.createComment(task.getId(), task.getProcessInstanceId(), commentDescription);

        //完成任务
        taskService.complete(task.getId());
    }

    /**
     * 增加历史记录
     */
    private void addHistory() {
        logger.info("历史记录");

    }

    /**
     * 部署流程
     */
    public void deployFlow() {


        String processKey = "Process_Project_Material";


        repositoryService.getDeploymentResourceNames(processKey);
        Deployment deployment = repositoryService.createDeployment().name(processKey).addClasspathResource(
                "process.bpmn").deploy();
        deployment.getName();
        deployment.getId();

    }

    /**
     * 删除流程
     *
     * @param project
     */
    public void deleteProcess(Project project) {
        String businessKey = project.getId();

        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(
                businessKey).list();
        if (processInstanceList == null) {
            return;
        }

        for (ProcessInstance processInstance : processInstanceList) {
            runtimeService.deleteProcessInstance(processInstance.getId(), "删除");
        }

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
     * 启动流程，即：执行 “开始”,“设计部或工程部人员发起项目”
     *
     * @param projectId
     * @return
     */
    public String startProcessByProjectId(String projectId) {
        Project project = projectService.getById(projectId);
        if (project == null || project.getId() == null) {
            throw new BusinessException("项目为空，请建立项目");
        }

        //部署流程
        deployFlow();

        deleteProcess(project);

        //deleteHistoryProcess();
        // return null;

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        String processKey = "Process_Project_Material";

        //业务ID(这里是项目ID),通过该字段与业务单据进行绑定，以后就可以根据业务ID来查询流程实例
        String businessId = project.getId();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey, businessId);

        startProject(project);
        return instance.getRootProcessInstanceId();
    }

    /**
     * 启动项目，设计部或工程部人员发起项目
     * 执行：“发起项目”
     *
     * @return
     */
    public String startProject(Project project) {

        if (project == null || project.getId() == null) {
            throw new BusinessException("项目为空，请建立项目");
        }

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }
        //得到项目号，根据项目号找到当前任务
        String businessId = project.getId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            result = "未找到待办任务";
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            result = "没有审核权限，当前不是项目创建者";
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */


            //完成任务
            setTaskComplete(task, "创建项目", user);

            //增加审核历史过程
            projectHistoryBusiness.addCreateProject(project.getId(), user.getId(), "创建项目", "创建项目");

            result = "项目创建成功";
        }

        return result;
    }

    /**
     * 设计单位执行"提交项目材料参数"
     *
     * @param project
     * @return
     */
    public String designCompanySubmitProjectMaterial(Project project) {

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }
        //得到项目号，根据项目号找到当前任务
        String businessId = project.getId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            result = "未找到待办任务";
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            result = "没有审核权限，制单人不是当前待办任务，无法提交项目材料参数";
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */


            //指定制单人或送审人员
            // Map<String, Object> map = new HashMap<>();
            // map.put("design_department_manager", projectUser.getUserId());
            // taskService.setVariables(task.getId(), map);
            // taskService.setAssignee(task.getId(), projectUser.getUserId());


            //完成任务
            setTaskComplete(task, "设计公司提交项目材料参数", user);


            //增加审核历史过程
            projectHistoryBusiness.addSubmitProjectMaterial(project.getId(), user.getId(), "设计公司提交项目材料参数",
                                                            "设计公司提交项目材料参数");

            result = "提交项目材料参数成功";
        }

        return result;
    }

    /**
     * 项目经理直接对项目材料进行审核
     *
     * @param projectReviewForm
     * @return
     */
    public String submitProjectMaterialReviewOfManagerDirect(ProjectReviewForm projectReviewForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectReviewForm.getProjectReviewMode().getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectReviewForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法对项目材料进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //获得当前设计单位序号
            String companyId = getDesignCompanyIdByTaskIdAndDesignCompanyIndex(projectReviewForm.getTaskId(),
                                                                               projectReviewForm.getDesignCompanyIndex());
            int designCompanyIndex = projectReviewForm.getDesignCompanyIndex();
            if (designCompanyIndex < 0) {
                throw new BusinessException("当前用户不是设计单位人员，无法审核项目材料");

            }

            String projectReviewId = projectBusinessService.addForm(projectReviewForm);


            //项目经理直接审批

            taskService.setVariable(task.getId(),
                                    "nProjectMaterialDesignDepartmentManagerDispatchReviewResult" + designCompanyIndex,
                                    1);

            //项目经理审批结果
            int nReviewResult = projectReviewForm.getProjectReivewUser().getReviewResult();

            //项目经理审批结果,流程图中0拒绝，1通过
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;


            taskService.setVariable(task.getId(),
                                    "nProjectMaterialDesignDepartmentManagerReviewResult" + designCompanyIndex,
                                    nReviewResult);

            //不通过的话，需要记录下来，供上一个节点查看原因
            if (nReviewResult == 0) {
                taskService.setVariable(task.getId(), "projectReviewId" + designCompanyIndex, projectReviewId);
            }


            //完成任务
            setTaskComplete(task, "项目经理对项目材料分发给项目经理进行直接审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addProjectMaterialReviewManagerDispatch(businessId, user.getId(),
                                                                           "设计部审核项目材料",
                                                                           "项目经理分发审核项目材料，交给项目经理直接审核",
                                                                           projectReviewId);


            result = "项目经理对项目材料分发给项目经理进行直接审核成功";


        }

        return result;


    }


    /**
     * 项目经理将项目材料分发给项目员工进行审核
     *
     * @param projectReviewDispatchForm
     * @return
     */
    public String submitProjectMaterialReviewManagerDistributeToEmployees(ProjectReviewDispatchForm projectReviewDispatchForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        //得到项目号，根据项目号找到当前任务
        String businessId = projectReviewDispatchForm.getProjectReviewMode().getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectReviewDispatchForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //获得当前设计单位序号
            int designCompanyIndex = projectReviewDispatchForm.getDesignCompanyIndex();
            if (designCompanyIndex < 0) {
                throw new BusinessException("当前用户不是设计单位人员，无法审核项目材料");

            }

            //执行业务
            //将审核分派保存到数据库
            String projectReviewId = projectBusinessService.addForm(projectReviewDispatchForm);



            //项目经理分派任务，先由项目员工审批，然后再有项目经理审批
            taskService.setVariable(task.getId(),
                                    "nProjectMaterialDesignDepartmentManagerDispatchReviewResult" + designCompanyIndex,
                                    0);
            taskService.setVariable(task.getId(), "projectReviewId" + designCompanyIndex, projectReviewId);//设置项目审核Id


            //完成任务
            setTaskComplete(task, "项目经理将项目材料分发给项目员工进行审核", user);


            //增加审核历史过程
            projectHistoryBusiness.addProjectMaterialReviewManagerDispatch(businessId, user.getId(),
                                                                           "设计部审核项目材料",
                                                                           "项目经理将项目材料分发给项目员工进行审核",
                                                                           "");


            result = "项目经理将项目材料分发给项目员工进行审核成功";
        }

        return result;
    }

    public String submitProjectMaterialReviewOfEmployee(ProjectReviewEmployeeForm projectReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        ProjectReviewUser projectReviewUser = projectReviewEmployeeForm.getProjectReviewUser();
        if (projectReviewUser == null)
            throw new BusinessException("参数为空");


        //得到项目号，根据项目号找到当前任务
        String businessId = projectReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //获得当前设计单位序号
            int designCompanyIndex = projectReviewEmployeeForm.getDesignCompanyIndex();
            if (designCompanyIndex < 0) {
                throw new BusinessException("当前用户不是设计单位人员，无法审核项目材料");

            }


            String projectReviewId = (String) taskService.getVariable(task.getId(),
                                                                      "projectReviewId" + designCompanyIndex);//项目审核Id
            projectReviewEmployeeForm.getProjectReviewUser().setProjectReviewId(projectReviewId);
            //项目员工审核项目
            String projectReviewUserId = projectBusinessService.addFormOfEmployee(projectReviewEmployeeForm);
            if (projectReviewUserId == null)
                throw new BusinessException("项目员工审核项目失败");

            //项目员工审核，后续需要项目经理汇总操作
            taskService.setVariable(task.getId(), "nDesignDepartmentManagerReview" + designCompanyIndex, 0);


            //完成任务
            setTaskComplete(task, "项目员工对项目材料进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addProjectMaterialReviewEmployee(businessId, user.getId(),
                                                                    "设计部审核项目材料",
                                                                    "项目员工对项目材料进行审核，并给出审核结果", "");


            result = "项目经理将项目材料分发给项目员工进行审核成功";
        }

        return result;
    }

    public String submitProjectMaterialReviewOfManager(ProjectReviewEmployeeForm projectReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        ProjectReviewUser projectReviewUser = projectReviewEmployeeForm.getProjectReviewUser();
        if (projectReviewUser == null)
            throw new BusinessException("参数为空");

        //得到项目号，根据项目号找到当前任务
        String businessId = projectReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //获得当前设计单位序号
            int designCompanyIndex = getDesignCompanyIndexByTaskIdAndDesignCompanyIndex(task.getId(),
                                                                                        projectReviewEmployeeForm.getDesignCompanyIndex());
            if (designCompanyIndex < 0) {
                throw new BusinessException("当前用户不是设计单位人员，无法审核项目材料");

            }

            //项目员工审核项目
            String projectReviewId = (String) taskService.getVariable(task.getId(),
                                                                      "projectReviewId" + designCompanyIndex);//项目审核Id

            projectReviewEmployeeForm.getProjectReviewUser().setProjectReviewId(projectReviewId);
            String projectReviewUserId = projectBusinessService.addFormOfEmployee(projectReviewEmployeeForm);
            if (projectReviewUserId == null)
                throw new BusinessException("项目员工审核项目失败");

            //项目经理直接审核
            taskService.setVariable(task.getId(), "nDesignDepartmentManagerReview" + designCompanyIndex, 1);


            //最终审核结果
            //项目经理审批结果,流程图中0拒绝，1通过
            //项目经理审批结果
            int nReviewResult = projectReviewUser.getReviewResult();
            //项目经理审批结果,流程图中0拒绝，1通过
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(),
                                    "nProjectMaterialDesignDepartmentManagerReviewResult" + designCompanyIndex,
                                    nReviewResult);
            if (nReviewResult == 0) {
                taskService.setVariable(task.getId(), "projectReviewId" + designCompanyIndex, projectReviewId);
            }
            //完成任务
            setTaskComplete(task, "项目经理对项目材料进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addProjectMaterialReviewManagerWithoutEmployeeReviewed(businessId, user.getId(),
                                                                                          "设计部审核项目材料",
                                                                                          "项目经理对项目材料进行审核，并给出审核结果",
                                                                                          "");


            result = "项目经理对材料品牌审核成功";
        }

        return result;
    }

    /**
     * 设计部项目经理填写总结意见
     *
     * @param projectReviewManagerForm
     * @return
     */
    public String submitProjectMaterialReviewOfManagerSummary(ProjectReviewManagerForm projectReviewManagerForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        ProjectReviewUser projectReviewUser = projectReviewManagerForm.getProjectReviewUser();
        if (projectReviewUser == null)
            throw new BusinessException("参数为空");


        //得到项目号，根据项目号找到当前任务
        String businessId = projectReviewManagerForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectReviewManagerForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //获得当前设计单位序号
            int designCompanyIndex = projectReviewManagerForm
                    .getDesignCompanyIndex();
            if (designCompanyIndex < 0) {
                throw new BusinessException("当前用户不是设计单位人员，无法审核项目材料");

            }

            //项目员工审核项目
            String projectReviewId = (String) taskService.getVariable(task.getId(), "projectReviewId" + designCompanyIndex);//项目审核Id
            projectReviewManagerForm.getProjectReviewUser().setProjectReviewId(projectReviewId);

            String projectReviewUserId = projectBusinessService.addFormOfManagerSummary(projectReviewManagerForm);
            if (projectReviewUserId == null)
                throw new BusinessException("添加项目经理项目审核汇总意见失败");


            //项目经理审批结果
            int nReviewResult = projectReviewUser.getReviewResult();
            //项目经理审批结果,流程图中0拒绝，1通过
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;

            taskService.setVariable(task.getId(),
                                    "nProjectMaterialDesignDepartmentManagerReviewResult" + designCompanyIndex,
                                    nReviewResult);

            //项目经理已经填写好了意见，不需要等待其它项目员工填写意见了，走向下一个节点
            taskService.setVariable(task.getId(),
                                    "designDepartmentManagerProjectMaterialSummaryReview" + designCompanyIndex, 1);
            if (nReviewResult == 0) {
                taskService.setVariable(task.getId(), "projectReviewId" + designCompanyIndex, projectReviewId);
            }

            //完成任务
            setTaskComplete(task, "项目经理项目审核汇总意见", user);

            //增加审核历史过程
            projectHistoryBusiness.addProjectMaterialReviewManagerSummary(businessId, user.getId(),
                                                                          "设计部审核项目材料",
                                                                          "项目经理项目审核汇总意见，并给出审核结果",
                                                                          "");


            result = "项目经理项目审核汇总成功";
        }

        return result;
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

    Task getTaskByBusinessKey(String projectId,
                              String userId,
                              String taskId) {

        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(
                projectId).list();
        if (instanceList == null || instanceList.isEmpty()) {
            return null;
        }
        for (ProcessInstance instance : instanceList) {

            //List<Task> taskList = taskService.createTaskQuery().processInstanceId(instance.getId()).active().list();


            Task task = taskService.createTaskQuery().processInstanceId(
                    instance.getId()).active().taskAssignee(
                    userId).taskId(taskId).singleResult();
            if (task != null) return task;
        }
        return null;
    }

    public UseMaterialBrandSelectView getUseMaterialBrandSelectViewByCurrentLoginUser(String projectId,
                                                                                      String taskId) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        Project project = projectService.getById(projectId);
        if (project == null)
            throw new BusinessException("项目不存在");

        //得到项目号，根据项目号找到当前任务
        String businessId = projectId;
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), taskId);
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目经理无法分发监理单位与总包单位");
        } else {
            String useMaterialBrandSelectId = taskService.getVariable(task.getId(),
                                                                      "useMaterialBrandSelectId").toString();
            if (useMaterialBrandSelectId != null)
                return projectBusinessService.getUseMaterialBrandSelectViewByCurrentLoginUser(projectId,
                                                                                              useMaterialBrandSelectId);


        }

        return null;


    }

    /**
     * 提交工程部经理分发监理单位与总包单位
     *
     * @param projectCompany
     * @return
     */
    public String submitEngineeringDepartmentManagerDispatch(ProjectCompany projectCompany) {

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        String projectCompanyId = projectCompanyService.add(projectCompany);
        if (projectCompanyId == null) {
            throw new BusinessException("添加失败");
        }
        //得到项目号，根据项目号找到当前任务
        String businessId = projectCompany.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目经理无法分发监理单位与总包单位");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */


            //完成任务
            setTaskComplete(task, "项目经理分发监理单位与总包单位", user);

            //增加审核历史过程
            projectHistoryBusiness.addDispatchProjectMaterial(businessId, user.getId(),
                                                              "工程部分发监理单位与总包单位",
                                                              "工程部项目经理分发监理单位与总包单位");


            result = "项目经理分发监理单位与总包单位成功";
        }

        return result;
    }

    /**
     * 启动管理员设置设计单位、设计部、工程部等员工
     *
     * @param project
     * @return
     */
    public String startAdminSetDesignAndEngineeringDepartmentUsers(Project project) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (project == null) {
            throw new BusinessException("项目为null");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = project.getId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */
            //检查设计单位、设计部、工程部等员工是否存在
            if (!projectBusinessService.checkDesignCompanyEmployeeExists(project.getId()))
                throw new BusinessException("设计单位员工不存在");
            if (!projectBusinessService.checkDesignDepartmentManagerExists(project.getId()))
                throw new BusinessException("未找到设计部经理");
            if (!projectBusinessService.checkEngineeringDepartmentManagerExists(project.getId()))
                throw new BusinessException("未找到工程部经理");

            taskService.setVariable(task.getId(), IProjectVariable.TASK_VARIABLE_PROJECT_ID, businessId);//设置项目号

            //完成任务
            setTaskComplete(task, "管理员设置设计单位、设计部、工程部等员工", user);

            //增加审核历史过程
            projectHistoryBusiness.addAdminSetDesignAndEngineeringDepartmentUsers(businessId, user.getId(),
                                                                                  "管理员设置员工",
                                                                                  "管理员设置设计单位员工，设置设计部、工程部等项目经理和项目员工");


            result = "管理员设置设计单位、设计部、工程部等员工成功";
        }

        return result;
    }

    /**
     * 管理员设置监理单位、总包单位人员
     *
     * @param project
     * @return
     */
    public String startAdminSetSuperVisionAndGeneralContractorEmployee(Project project) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (project == null) {
            throw new BusinessException("项目为null");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = project.getId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());

        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //检查设计单位、设计部、工程部等员工是否存在


            if (!projectBusinessService.checkSupervisionCompanyEmployeeExists(project.getId()))
                throw new BusinessException("未找到监理单位员工");


            if (!projectBusinessService.checkGeneralContractorCompanyEmployeeExists(project.getId()))
                throw new BusinessException("未找到总包单位员工");


            //完成任务
            setTaskComplete(task, "管理员设置监理单位、总包单位员工", user);

            //增加审核历史过程
            projectHistoryBusiness.addAdminSetSuperVisionAndGeneralContractorEmployee(businessId, user.getId(),
                                                                                      "管理员设置员工",
                                                                                      "管理员设置监理单位、总包单位员工");


            result = "管理员设置监理单位、总包单位员工成功";
        }

        return result;
    }

    /**
     * 总包单位提交品牌选择和物料申请
     *
     * @param useMaterialForm
     * @return
     */
    public String submitGeneralContractorSelectBrand(UseMaterialForm useMaterialForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (useMaterialForm == null)
            throw new BusinessException("参数为空");

        //得到项目号，根据项目号找到当前任务
        String businessId = useMaterialForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), useMaterialForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //根据是否影响外观来拆分
//            UseMaterialForm useMaterialForm0 = projectBusinessService.splitUseMaterialFormByIsAppearance(
//                    useMaterialForm, 0);
//            UseMaterialForm useMaterialForm1 = projectBusinessService.splitUseMaterialFormByIsAppearance(
//                    useMaterialForm, 1);
//
//            //影响外观和不影响外观的材料数量
//            int nLen0 = useMaterialForm0.getUseMaterialFormItems().size();
//            int nlen1 = useMaterialForm1.getUseMaterialFormItems().size();
            if (useMaterialForm.getUseMaterialFormItems().size() == 1) {
                //根据材料批次拆分


                //项目员工审核项目
                String useMaterialBrandSelectId = projectBusinessService.addFormOfGeneralContractorBrandSelectAndUseMaterial(
                        useMaterialForm);
                if (useMaterialBrandSelectId == null)
                    throw new BusinessException("添加总包单位选择品牌失败");


                //总包单位提交品牌选择和物料申请的ID，后续审核依靠此ID，根据此ID找到审核的品牌、物料等信息
                taskService.setVariable(task.getId(), "useMaterialBrandSelectId", useMaterialBrandSelectId);
                //完成任务
                setTaskComplete(task, "总包单位选择品牌，物料使用申请", user);
                //增加审核历史过程
                projectHistoryBusiness.addGeneralContractorSelectBrandAndUseMaterial(businessId, user.getId(),
                                                                                     "总包单位选择品牌，物料使用申请",
                                                                                     "总包单位选择品牌，物料使用申请");
            } else if (useMaterialForm.getUseMaterialFormItems().size() > 1) {
                //需要拆分任务

                //1.第一个，仍然在原来的流程实例中
                //项目员工审核项目
                UseMaterialForm useMaterialForm0 = new UseMaterialForm();
                useMaterialForm0.setProjectId(useMaterialForm.getProjectId());
                useMaterialForm0.setTaskId(useMaterialForm.getTaskId());
                useMaterialForm0.setUserId(useMaterialForm.getUserId());
                useMaterialForm0.setUseMaterialFormItems(Collections.singletonList(
                        useMaterialForm.getUseMaterialFormItems().get(0)));

                String useMaterialBrandSelectId0 = projectBusinessService.addFormOfGeneralContractorBrandSelectAndUseMaterial(
                        useMaterialForm0);
                if (useMaterialBrandSelectId0 == null)
                    throw new BusinessException("添加总包单位选择品牌失败");
                //总包单位提交品牌选择和物料申请的ID，后续审核依靠此ID，根据此ID找到审核的品牌、物料等信息
                taskService.setVariable(task.getId(), "useMaterialBrandSelectId", useMaterialBrandSelectId0);
                //完成任务

                //2.后面的，新建一个流程实例
                List<UseMaterialFormItem> remainingItems = useMaterialForm.getUseMaterialFormItems().subList(1, useMaterialForm.getUseMaterialFormItems().size());
                for (UseMaterialFormItem item : remainingItems) {
                    // 为每个剩余材料新建一个独立的 UseMaterialForm
                    UseMaterialForm singleMaterialForm = new UseMaterialForm();
                    singleMaterialForm.setProjectId(useMaterialForm.getProjectId());
                    singleMaterialForm.setUserId(useMaterialForm.getUserId());
                    singleMaterialForm.setTaskId(useMaterialForm.getTaskId());
                    singleMaterialForm.setUseMaterialFormItems(Collections.singletonList(item));
                    // 新建一个流程实例
                    String useMaterialBrandSelectId = projectBusinessService.addFormOfGeneralContractorBrandSelectAndUseMaterial(
                            singleMaterialForm);
                    setTaskComplete(task, "总包单位选择品牌，物料使用申请", user);

                    //增加审核历史过程
                    projectHistoryBusiness.addGeneralContractorSelectBrandAndUseMaterial(businessId, user.getId(),
                            "总包单位选择品牌，物料使用申请",
                            "总包单位选择品牌，物料使用申请");
                    if (useMaterialBrandSelectId == null)
                        throw new BusinessException("添加总包单位选择品牌失败");

                    String processKey = "Process_Project_Material";

                    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessId);
                    runtimeService.createProcessInstanceModification(processInstance.getId())
                            .cancelAllForActivity("Activity_Construction_Company_Create_Project")
                            .startBeforeActivity("Activity_Split_Brand_By_Appearance")
                            .setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, businessId)
                            .setVariable("useMaterialBrandSelectId", useMaterialBrandSelectId)
//                            .setVariable("taskId", task.getId())
                            .execute();
                }
            } else {
                throw new BusinessException("参数错误");
            }


            result = "总包单位选择品牌，物料使用申请成功";
        }

        return result;
    }

    /**
     * 总包单位新建任务：提交品牌选择和物料申请
     *
     * @param useMaterialForm
     * @return
     */
    public String submitGeneralContractorNewTaskOfSelectBrand(UseMaterialForm useMaterialForm) {

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (useMaterialForm == null)
            throw new BusinessException("参数为空");
        String businessId = useMaterialForm.getProjectId();
        String processKey = "Process_Project_Material";


        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessId);
        runtimeService.createProcessInstanceModification(processInstance.getId())
                      .cancelAllForActivity("Activity_Construction_Company_Create_Project")
                      .startBeforeActivity("Activity_General_Contractor_Company_New_Task_Of_Select_Brand")
                      .setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, businessId)
                      .execute();
        return "";
        /*


        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(
                businessId).list();

        ProcessInstance processInstance = processInstanceList.getFirst();

        runtimeService.createProcessInstanceModification(processInstance.getId())
                      .startBeforeActivity("Activity_General_Contractor_Company_New_Task_Of_Select_Brand")
                      .execute();
        return "";

         */

//        //得到项目号，根据项目号找到当前任务
//        String businessId = useMaterialForm.getProjectId();
//        String result = "";
//        Task task = getTaskByBusinessKey(businessId, user.getId());
//        if (task == null) {
//            result = "未找到待办任务";
//        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
//            result = "没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌";
//        } else {
//
//            /*
//             * 基本步骤：
//             * 1.完成业务逻辑，例如将相关数据添加到数据库中
//             * 2.设置特定的任务变量,供后面使用，例如projectId等
//             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
//             * 4.完成任务
//             */
//
//
//            //项目员工审核项目
//            String useMaterialBrandSelectId = projectBusinessService.addFormOfGeneralContractorBrandSelectAndUseMaterial(
//                    useMaterialForm);
//            if (useMaterialBrandSelectId == null)
//                throw new BusinessException("添加总包单位选择品牌失败");
//
//
//            //总包单位提交品牌选择和物料申请的ID，后续审核依靠此ID，根据此ID找到审核的品牌、物料等信息
//            taskService.setVariable(task.getId(), "useMaterialBrandSelectId", useMaterialBrandSelectId);
//
//
//            //完成任务
//            setTaskComplete(task, "总包单位选择品牌，物料使用申请", user);
//
//            //增加审核历史过程
//            projectHistoryBusiness.addGeneralContractorSelectBrandAndUseMaterial(businessId, user.getId(),
//                                                                                 "总包单位选择品牌，物料使用申请",
//                                                                                 "总包单位选择品牌，物料使用申请");
//
//
//            result = "总包单位选择品牌，物料使用申请成功";
//        }
//
//        return result;
    }

    /**
     * 监理单位对总包单位提交的品牌、物料进行审核
     *
     * @param appearanceReviewForm
     * @return
     */
    public String submitSupervisionCompanyReviewSelectBrand(ProjectAppearanceReviewEmployeeForm appearanceReviewForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (appearanceReviewForm == null)
            throw new BusinessException("参数为空");

        //得到项目号，根据项目号找到当前任务
        ProjectAppearanceReviewUser appearanceReviewUser = appearanceReviewForm.getProjectAppearanceReviewUser();
        if (appearanceReviewUser == null)
            throw new BusinessException("参数为空");

        //  String useMaterialBrandSelectId = appearanceReviewForm.getUseMaterialBrandSelectId();
        //  if (useMaterialBrandSelectId == null)
        //      throw new BusinessException("参数为空");

        //   String projectAppearanceReviewId = appearanceReviewUser.getProjectAppearanceReviewId();
        //  if (projectAppearanceReviewId == null)
        //      throw new BusinessException("参数为空");


        //  ProjectMaterial projectMaterial = projectAppearanceReviewService.getProjectMaterialById(
        //         projectAppearanceReviewId);
        // if (projectMaterial == null)
        //    throw new BusinessException("参数为空");

        String businessId = appearanceReviewForm.getProjectId();
        if (businessId == null)
            throw new BusinessException("参数为空");
        String taskId = appearanceReviewForm.getTaskId();

        String result = "";
        Task task;
        if (taskId == null || taskId.isEmpty())
            task = getTaskByBusinessKey(businessId, user.getId());
        else task = getTaskByBusinessKey(businessId, user.getId(), taskId);
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */


            //完成业务规则
            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = projectBusinessService.addSupervisionCompanyReview(
                    appearanceReviewForm,
                    useMaterialBrandSelectId);

            if (projectAppearanceReviewModeId == null)
                throw new BusinessException("添加监理单位审核记录失败");


            //设置参数:审批通过还是未通过
            int nBrandReviewResult = appearanceReviewUser.getReviewResult();
            if (nBrandReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nBrandReviewResult = 0;
            else nBrandReviewResult = 1;

            taskService.setVariable(task.getId(), "nBrandReviewResult", nBrandReviewResult);


            //设置参数：外观审核，还是非外观审核
            int nAppearanceReviewResult = projectBusinessService.getAppearanceTypeByProjectAppearanceReviewModeId(
                    projectAppearanceReviewModeId);
            taskService.setVariable(task.getId(), "nAppearanceReviewResult", nAppearanceReviewResult);

            //不通过的话，需要记录下来，供总包单位查看不通过的意见
            if (nBrandReviewResult == 0) {
                taskService.setVariable(task.getId(), "supervisionCompanySelectBrandReviewResultOfNotPassed",
                                        projectAppearanceReviewModeId);
            }


            //完成任务
            setTaskComplete(task, "监理单位对总包单位提交的品牌、物料使用申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addSupervisionCompanyReview(businessId, user.getId(),
                                                               "监理单位审核品牌、物料使用申请",
                                                               "监理单位对总包单位提交的品牌、物料使用申请进行审核",
                                                               projectAppearanceReviewModeId);


            result = "监理单位对总包单位提交的品牌、物料使用申请进行审核成功";
        }

        return result;
    }


    /**
     * 影响外观：送审操作：工程部经理直接审核
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @return
     */
    public String submitAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewManagerDirectForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),
                                         projectAppearanceReviewManagerDirectForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");


            //项目员工审核项目
            String projectAppearanceReviewModeId = projectBusinessService.addFormOfManagerReviewDirectly(
                    projectAppearanceReviewManagerDirectForm,
                    useMaterialBrandSelectId);

            //项目经理直接审核
            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerDispatchReviewResult", 1);




            //项目经理审批结果
            int nReviewResult = projectAppearanceReviewManagerDirectForm.getProjectAppearanceReviewUser().getReviewResult();

            //项目经理审批结果,流程图中0拒绝，1通过
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            //项目经理审批结果
            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerReviewResult",
                                    nReviewResult);
            if(nReviewResult == 0){

                taskService.setVariable(task.getId(),"AffectAppearanceReviewModeId",projectAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "工程部项目经理将总包单位提交的品牌、物料使用申请分发给项目经理进行直接审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewEngineeringDepartmentManagerDispatch(businessId,
                    user.getId(),
                    "工程部审核影响外观的物料使用申请",
                    "工程部项目经理将总包单位提交的品牌、物料使用申请分发给项目经理进行直接审核",
                    projectAppearanceReviewModeId
            );
            result = "工程部项目经理将总包单位提交的品牌、物料使用申请分发给项目经理成功";
        }


        return result;
    }


    /**
     * 影响外观：送审操作：工程部经理分发给员工
     *
     * @param projectAppearanceReviewDispatchForm
     * @return
     */
    public String submitAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewDispatchForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //执行业务
            //将审核分派保存到数据库

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = projectBusinessService.addForm(projectAppearanceReviewDispatchForm,
                                                                                  useMaterialBrandSelectId);

            //分配方式ModeId,后面依靠此变量获取审核记录
            taskService.setVariable(task.getId(), "projectAppearanceReviewModeId", projectAppearanceReviewModeId);

            //项目经理分派任务，先由项目员工审批，然后再有项目经理审批
            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerDispatchReviewResult", 0);


            //完成任务
            setTaskComplete(task, "项目经理将项目材料分发给项目员工进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewEngineeringDepartmentManagerDispatch(businessId,
                                                                                                 user.getId(),
                                                                                                 "工程部审核影响外观的物料使用申请",
                                                                                                 "项目经理将项目材料分发给项目员工进行审核",
                                                                                                 projectAppearanceReviewModeId
            );


            result = "项目经理将项目材料分发给项目员工进行审核成功";
        }

        return result;
    }


    /**
     * 影响外观：送审操作：工程部员工审核
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    public String submitAffectAppearanceReviewOfEngineeringDepartmentEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                    "projectAppearanceReviewModeId");


            //项目员工审核项目
            String projectAppearanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectAppearanceReviewEmployeeForm, useMaterialBrandSelectId,
                    projectAppearanceReviewModeId);

            //员工首先审核，需要项目经理汇总
            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerDirectlyReviewResult", 0);


            //完成任务
            setTaskComplete(task, "项目员工对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewEngineeringDepartmentEmployee(businessId,
                                                                                          user.getId(),
                                                                                          "工程部审核影响外观的物料使用申请",
                                                                                          "项目员工对项目材料品牌、物料申请进行审核",
                                                                                          projectAppearanceReviewUserId
            );


            result = "项目员工对材料品牌、物料申请审核成功";
        }

        return result;
    }

    public String submitAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                    "projectAppearanceReviewModeId");

            //项目员工审核项目
            String projectAppearanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectAppearanceReviewEmployeeForm, useMaterialBrandSelectId,
                    projectAppearanceReviewModeId);

            //此时没有员工进行过审核,项目经理直接审核
            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerDirectlyReviewResult", 1);


            //最终审核结果
            //项目经理审批结果,流程图中0拒绝，1通过
            int nReviewResult = projectAppearanceReviewEmployeeForm.getProjectAppearanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerReviewResult", nReviewResult);

            //完成任务
            setTaskComplete(task, "项目经理对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(
                    businessId,
                    user.getId(),
                    "工程部审核影响外观的物料使用申请",
                    "项目经理对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewUserId
            );


            result = "项目经理对材料品牌、物料申请审核成功";
        }

        return result;
    }

    /**
     * 影响外观：送审操作：工程部经理汇总
     *
     * @param projectAppearanceReviewManagerSummaryForm
     * @return
     */
    public String submitAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewManagerSummaryForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */
            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");


            String projectAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                    "projectAppearanceReviewModeId");

            //项目员工审核项目
            String projectReviewUserId = projectBusinessService.addFormOfManagerSummary(
                    projectAppearanceReviewManagerSummaryForm, useMaterialBrandSelectId, projectAppearanceReviewModeId);
            if (projectReviewUserId == null)
                throw new BusinessException("添加项目经理项目审核汇总意见失败");


            //最终审核结果
            int nReviewResult = projectAppearanceReviewManagerSummaryForm.getProjectAppearanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;

            taskService.setVariable(task.getId(), "nAffectAppearanceEngineeringManagerReviewResult", nReviewResult);

            //项目经理已经填写好了意见，不需要等待其它项目员工填写意见了，走向下一个节点
            taskService.setVariable(task.getId(), "engineeringDepartmentManagerAffectAppearanceSummaryReview", 1);
             if(nReviewResult == 0){

                taskService.setVariable(task.getId(),"AffectAppearanceReviewModeId",projectAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "项目经理项目审核汇总意见", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewEngineeringDepartmentManagerSummary(
                    businessId,
                    user.getId(),
                    "工程部审核影响外观的物料使用申请",
                    "项目经理项目审核汇总意见，并给出审核结果",
                    projectReviewUserId
            );


            result = "项目经理项目审核汇总成功";
        }

        return result;
    }

    /**
     * 影响外观：送审操作：设计单位进行审核
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    public String submitAffectAppearanceReviewOfDesignCompany(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),projectAppearanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            //获得当前设计单位序号

            //项目员工审核项目
            String projectAppearanceReviewModeId = projectBusinessService.addFormOfDesignCompanyEmployee(
                    projectAppearanceReviewEmployeeForm,
                    useMaterialBrandSelectId);

            //员工审核结果,根据结果确定是否需要继续送审
            int nAffectAppearanceDesignCompanyReviewResult = projectAppearanceReviewEmployeeForm.getProjectAppearanceReviewUser().getReviewResult();
            if (nAffectAppearanceDesignCompanyReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED)
                nAffectAppearanceDesignCompanyReviewResult = 0;
            else nAffectAppearanceDesignCompanyReviewResult = 1;

            taskService.setVariable(task.getId(), "nAffectAppearanceDesignCompanyReviewResult",
                                    nAffectAppearanceDesignCompanyReviewResult);



            if (nAffectAppearanceDesignCompanyReviewResult == 0){
                 taskService.setVariable(task.getId(),"DesignCompanyAffectAppearanceReviewModeId",
                         projectAppearanceReviewModeId);

             }

            //完成任务
            setTaskComplete(task, "设计公司项目员工对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewDesignCompany(
                    businessId,
                    user.getId(),
                    "设计公司审核影响外观的物料使用申请",
                    "设计公司项目员工对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewModeId
            );


            result = "设计公司项目员工对材料品牌、物料申请审核成功";
        }

        return result;
    }


    /**
     * 影响外观：送审操作：设计部经理直接送审
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @return
     */
    public String submitAffectAppearanceReviewOfDesignDepartmentManagerDirect(ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewManagerDirectForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),projectAppearanceReviewManagerDirectForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");


            //项目员工审核项目
            String projectAppearanceReviewModeId = projectBusinessService.addFormOfManagerReviewDirectly(
                    projectAppearanceReviewManagerDirectForm,
                    useMaterialBrandSelectId);

            //项目经理直接审核
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerDispatchReviewResult", 1);


            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewDesignDepartmentManagerDirectly(
                    businessId,
                    user.getId(),
                    "设计部审核影响外观的物料使用申请",
                    "设计部项目员工对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewModeId
            );


            //项目经理审批结果
            int nReviewResult = projectAppearanceReviewManagerDirectForm.getProjectAppearanceReviewUser().getReviewResult();

            //项目经理审批结果
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerReviewResult",
                                    nReviewResult);
            if (nReviewResult == 0)
            {
                taskService.setVariable(task.getId(),"DesignDepartmentAffectAppearanceReviewModeId",projectAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "设计部项目经理将审核分发给项目经理进行直接审核", user);


            result = "设计部项目员工对材料品牌、物料申请审核成功";
        }

        return result;
    }


    /**
     * 影响外观：送审操作：设计部经理分发给员工
     *
     * @param projectAppearanceReviewDispatchForm
     * @return
     */
    public String submitAffectAppearanceReviewOfDesignDepartmentManagerDispatchToEmployee(ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewDispatchForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewDispatchForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //执行业务
            //将审核分派保存到数据库

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectDesignDepartmentAppearanceReviewModeId = projectBusinessService.addForm(
                    projectAppearanceReviewDispatchForm,
                    useMaterialBrandSelectId);


            taskService.setVariable(task.getId(), "projectDesignDepartmentAppearanceReviewModeId",
                                    projectDesignDepartmentAppearanceReviewModeId);//设置项目审核Id

            //项目经理分派任务，先由项目员工审批，然后再有项目经理审批
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerDispatchReviewResult", 0);


            //完成任务
            setTaskComplete(task, "项目经理将项目材料分发给项目员工进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewDesignDepartmentManagerDispatch(
                    businessId,
                    user.getId(),
                    "设计部审核影响外观的物料使用申请",
                    "项目经理将项目材料分发给项目员工进行审核",
                    projectDesignDepartmentAppearanceReviewModeId
            );


            result = "项目经理将项目材料分发给项目员工进行审核成功";
        }

        return result;
    }

    /**
     * 影响外观：送审操作：设计部员工审核
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    public String submitAffectAppearanceReviewOfDesignDepartmentEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectDesignDepartmentAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                                    "projectDesignDepartmentAppearanceReviewModeId");

            //项目员工审核项目
            String projectAppearanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectAppearanceReviewEmployeeForm, useMaterialBrandSelectId,
                    projectDesignDepartmentAppearanceReviewModeId);


            //项目员工审核，后续需要项目经理汇总操作
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerDirectlyReviewResult", 0);


            //完成任务
            setTaskComplete(task, "项目员工对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewDesignDepartmentEmployee(
                    businessId,
                    user.getId(),
                    "设计部审核影响外观的物料使用申请",
                    "项目员工对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewUserId
            );


            result = "项目员工对材料品牌、物料申请审核成功";
        }

        return result;
    }

    public String submitAffectAppearanceRemainReviewOfDesignDepartmentOfManager(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectDesignDepartmentAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                                    "projectDesignDepartmentAppearanceReviewModeId");

            //项目员工审核项目
            String projectAppearanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectAppearanceReviewEmployeeForm, useMaterialBrandSelectId,
                    projectDesignDepartmentAppearanceReviewModeId);

            //此时没有员工进行过审核,项目经理直接审核
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerDirectlyReviewResult", 1);


            //最终审核结果
            int nReviewResult = projectAppearanceReviewEmployeeForm.getProjectAppearanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerReviewResult",
                                    nReviewResult);
            if (nReviewResult == 0)
            {
                taskService.setVariable(task.getId(),"DesignDepartmentAffectAppearanceReviewModeId",projectDesignDepartmentAppearanceReviewModeId);
            }
            //完成任务
            setTaskComplete(task, "项目经理对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewDesignDepartmentManagerWithoutEmployeeReviewed(
                    businessId,
                    user.getId(),
                    "设计部审核影响外观的物料使用申请",
                    "项目经理对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewUserId
            );


            result = "项目经理对材料品牌、物料申请审核成功";
        }

        return result;
    }

    /**
     * 影响外观：送审操作：设计部经理汇总
     *
     * @param projectAppearanceReviewManagerSummaryForm
     * @return
     */
    public String submitAffectAppearanceReviewOfDesignDepartmentManagerSummary(ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewManagerSummaryForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewManagerSummaryForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */
            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");


            String projectDesignDepartmentAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                                    "projectDesignDepartmentAppearanceReviewModeId");

            //项目员工审核项目
            String projectReviewUserId = projectBusinessService.addFormOfManagerSummary(
                    projectAppearanceReviewManagerSummaryForm, useMaterialBrandSelectId,
                    projectDesignDepartmentAppearanceReviewModeId);
            if (projectReviewUserId == null)
                throw new BusinessException("添加项目经理项目审核汇总意见失败");


            //最终审核结果
            int nReviewResult = projectAppearanceReviewManagerSummaryForm.getProjectAppearanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nAffectAppearanceDesignDepartmentManagerReviewResult",
                                    nReviewResult);

            //项目经理已经填写好了意见，不需要等待其它项目员工填写意见了，走向下一个节点
            taskService.setVariable(task.getId(), "designDepartmentManagerAppearanceSummaryReview", 1);
            if (nReviewResult == 0)
            {
                taskService.setVariable(task.getId(),"DesignDepartmentAffectAppearanceReviewModeId",projectDesignDepartmentAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "项目经理项目审核汇总意见", user);

            //增加审核历史过程
            projectHistoryBusiness.addAffectAppearanceReviewDesignDepartmentManagerSummary(
                    businessId,
                    user.getId(),
                    "设计部审核影响外观的物料使用申请",
                    "项目经理项目审核汇总意见，并给出审核结果",
                    projectReviewUserId
            );


            result = "项目经理项目审核汇总成功";
        }

        return result;
    }

    /**
     * 不影响外观：送审操作：工程部经理直接汇总
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @return
     */
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDirect(ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewManagerDirectForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),
                                         projectAppearanceReviewManagerDirectForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");


            //项目员工审核项目
            String projectAppearanceReviewModeId = projectBusinessService.addFormOfManagerReviewDirectly(
                    projectAppearanceReviewManagerDirectForm,
                    useMaterialBrandSelectId);
            String AffectAppearanceReviewModeId= projectAppearanceReviewModeId;

            //项目经理直接审核
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerDispatchReviewResult", 1);


            //增加审核历史过程
            projectHistoryBusiness.addNotAffectAppearanceReviewEngineeringDepartmentManagerDirectly(
                    businessId,
                    user.getId(),
                    "工程部审核不影响外观的物料使用申请",
                    "设计部项目经理分发给项目经理直接审核",
                    projectAppearanceReviewModeId
            );


            //项目经理审批结果
            int nReviewResult = projectAppearanceReviewManagerDirectForm.getProjectAppearanceReviewUser().getReviewResult();

            //项目经理审批结果
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerReviewResult",
                                    nReviewResult);
            if (nReviewResult == 0){
                taskService.setVariable(task.getId(), "AffectAppearanceReviewModeId", AffectAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "设计部项目经理分发给项目经理直接审核", user);


            result = "设计部项目员工对材料品牌、物料申请审核成功";
        }

        return result;
    }


    /**
     * 不影响外观：送审操作：工程部经理分发给员工
     *
     * @param projectAppearanceReviewDispatchForm
     * @return
     */
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewDispatchForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewDispatchForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //执行业务
            //将审核分派保存到数据库

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = projectBusinessService.addForm(projectAppearanceReviewDispatchForm,
                                                                                  useMaterialBrandSelectId);


            taskService.setVariable(task.getId(), "projectAppearanceReviewModeId",
                                    projectAppearanceReviewModeId);//设置项目审核Id

            //项目经理分派任务，先由项目员工审批，然后再有项目经理审批
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerDispatchReviewResult", 0);


            //完成任务
            setTaskComplete(task, "项目经理将项目材料分发给项目员工进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addNotAffectAppearanceReviewEngineeringDepartmentManagerDispatch(
                    businessId,
                    user.getId(),
                    "工程部审核不影响外观的物料使用申请",
                    "项目经理将项目材料分发给项目员工进行审核",
                    projectAppearanceReviewModeId
            );


            result = "项目经理将项目材料分发给项目员工进行审核成功";
        }

        return result;
    }

    /**
     * 不影响外观：送审操作：工程部员工审核
     *
     * @param projectAppearanceReviewEmployeeForm
     * @return
     */
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                    "projectAppearanceReviewModeId");

            //项目员工审核项目
            String projectAppearanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectAppearanceReviewEmployeeForm, useMaterialBrandSelectId,
                    projectAppearanceReviewModeId);


            //员工首先审核，需要项目经理汇总
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerDirectlyReviewResult", 0);


            //完成任务
            setTaskComplete(task, "项目员工对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addNotAffectAppearanceReviewEngineeringDepartmentEmployee(
                    businessId,
                    user.getId(),
                    "工程部审核不影响外观的物料使用申请",
                    "项目员工对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewUserId
            );


            result = "项目员工对材料品牌、物料申请审核成功";
        }

        return result;
    }

    public String submitNotAffectAppearanceRemainReviewOfEngineeringDepartmentOfManager(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");

            String projectAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                    "projectAppearanceReviewModeId");

            //项目员工审核项目
            String projectAppearanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectAppearanceReviewEmployeeForm, useMaterialBrandSelectId,
                    projectAppearanceReviewModeId);

            //此时没有员工进行过审核,项目经理直接审核
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerDirectlyReviewResult", 1);


            //最终审核结果
            int nReviewResult = projectAppearanceReviewEmployeeForm.getProjectAppearanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerReviewResult", nReviewResult
            );
            if (nReviewResult == 0){
                taskService.setVariable(task.getId(), "notAffectAppearanceReviewModeId", projectAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "项目经理对项目材料品牌、物料申请进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addNotAffectAppearanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(
                    businessId,
                    user.getId(),
                    "工程部审核不影响外观的物料使用申请",
                    "项目经理对项目材料品牌、物料申请进行审核",
                    projectAppearanceReviewUserId
            );


            result = "项目经理对材料品牌、物料申请审核成功";
        }

        return result;
    }

    /**
     * 不影响外观：送审操作：工程部经理汇总
     *
     * @param projectAppearanceReviewManagerSummaryForm
     * @return
     */
    public String submitNotAffectAppearanceReviewOfEngineeringDepartmentManagerSummary(ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectAppearanceReviewManagerSummaryForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectAppearanceReviewManagerSummaryForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */
            String useMaterialBrandSelectId = (String) taskService.getVariable(task.getId(),
                                                                               "useMaterialBrandSelectId");


            String projectDesignDepartmentAppearanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                                    "projectAppearanceReviewModeId");

            //项目员工审核项目
            String projectReviewUserId = projectBusinessService.addFormOfManagerSummary(
                    projectAppearanceReviewManagerSummaryForm, useMaterialBrandSelectId,
                    projectDesignDepartmentAppearanceReviewModeId);
            if (projectReviewUserId == null)
                throw new BusinessException("添加项目经理项目审核汇总意见失败");


            //最终审核结果
            int nReviewResult = projectAppearanceReviewManagerSummaryForm.getProjectAppearanceReviewUser()
                                                                         .getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nNotAffectAppearanceEngineeringManagerReviewResult", nReviewResult);

            //项目经理已经填写好了意见，不需要等待其它项目员工填写意见了，走向下一个节点
            taskService.setVariable(task.getId(), "engineeringDepartmentManagerNotAffectAppearanceSummaryReview", 1);
             if (nReviewResult == 0){
                taskService.setVariable(task.getId(), "notAffectAppearanceReviewModeId", projectDesignDepartmentAppearanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "工程部项目经理项目审核汇总意见", user);

            //增加审核历史过程
            projectHistoryBusiness.addNotAffectAppearanceReviewEngineeringDepartmentManagerSummary(
                    businessId,
                    user.getId(),
                    "工程部审核不影响外观的物料使用申请",
                    "工程部项目经理项目审核汇总意见，并给出审核结果",
                    projectReviewUserId
            );


            result = "工程部项目经理项目审核汇总成功";
        }

        return result;
    }

    /**
     * 总包单位订购物料
     *
     * @param buyMaterialForm
     * @return
     */
    public String submitGeneralContractorBuyProjectMaterial(BuyMaterialForm buyMaterialForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (buyMaterialForm == null || buyMaterialForm.getBuyMaterials().length == 0)
            throw new BusinessException("参数为空");

        //得到项目号，根据项目号找到当前任务
        String businessId = buyMaterialForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),buyMaterialForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */



            List<String> buyMaterialIdList = projectBusinessService.addFormOfGeneralContractorBuyProjectMaterial(
                    buyMaterialForm.getBuyMaterials());
            if (buyMaterialIdList == null)
                throw new BusinessException("总包单位订购失败");

            /*
            如果购买的物品有多个，则需要分裂任务，每个任务处理一个物品
            */
            String buyMaterialIdFirst = buyMaterialIdList.getFirst();
            if (buyMaterialIdList.size() > 1) {
                //在此分裂任务,将订购物品分裂成多个任务
                String processKey = "Process_Project_Material";
                for (int i = 1; i < buyMaterialIdList.size(); i++) {
                    String buyMaterialId = buyMaterialIdList.get(i);

                    //增加审核历史过程
                    projectHistoryBusiness.addProjectMaterialOrder(businessId, user.getId(),
                                                                   "总包单位订购物料",
                                                                   "总包单位订购物料",
                                                                   buyMaterialId);

                    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessId);
                    runtimeService.createProcessInstanceModification(processInstance.getId())
                                  .cancelAllForActivity("Activity_Construction_Company_Create_Project")
                                  .startBeforeActivity("Activity_Generate_QRCode")
                                  .setVariable("buyMaterialId", buyMaterialId)
                                  .setVariable("projectId", businessId)
                                  .execute();
                }
            }


            taskService.setVariable(task.getId(), "buyMaterialId", buyMaterialIdFirst);


            //完成任务
            setTaskComplete(task, "总包单位订购物料使用申请", user);

            //增加审核历史过程
            projectHistoryBusiness.addProjectMaterialOrder(businessId, user.getId(),
                                                           "总包单位订购物料",
                                                           "总包单位订购物料",
                                                           buyMaterialIdFirst);


            result = "总包单位订购物料成功";
        }

        return result;
    }
    /**
     * 总包单位多次购买物料
     *
     * @param
     * @return
     */
    public String submitGeneralContractorNewTaskOfBuyProjectMaterial(BuyMaterialForm buyMaterialForm){
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (buyMaterialForm == null)
            throw new BusinessException("参数为空");

//           0.总包选择 新订购，如果 总包有订购 任务，不增加新的流程实例，啥也不做；
//        如果 总包 没有订购任务， 增加新的流程实例；
//        1.根据项目号找到所有的流程
//        2。在这些流程中，找到走到订购的流程，然后保留1个
//                                    （从头最远走到订购）
        String businessId = buyMaterialForm.getProjectId();
        if (getExitOrderTaskByUserAndProjectId(businessId)) return "";
        else {
            String processKey = "Process_Project_Material";

            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessId);
            runtimeService.createProcessInstanceModification(processInstance.getId())
                    .cancelAllForActivity("Activity_Construction_Company_Create_Project")
                    .startBeforeActivity("Activity_General_Contractor_Company_New_Task_Of_Buy_Project_Material")
                    .setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, businessId)
                    .setVariable("newOrderTask",1)
                    .execute();
            return "";
        }
    }
    public boolean getExitOrderTaskByUserAndProjectId(String projectId){

        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        List<TaskEntity> taskList = (List) taskService.createTaskQuery().taskAssignee(user.getId()).list();

        for (Task task : taskList) {
            if (task.getId().equalsIgnoreCase("Activity_General_Contractor_Order")){
                return true;
            }
        }
        return false;

    }



    /**
     * 总包单位填报工程材料、设备报验资料
     *
     * @param
     * @return
     */
    public String submitGeneralContractorSubProjectMaterialVerificationDocument(GeneralContractorSubProjectMaterialVerificationDocumentForm generalContractorSubProjectMaterialVerificationDocumentForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId =generalContractorSubProjectMaterialVerificationDocumentForm.getProject().getId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId() ,generalContractorSubProjectMaterialVerificationDocumentForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.完成任务
             */
            String buyMaterialId = (String) taskService.getVariable(task.getId(), "buyMaterialId");

            //完成任务
            setTaskComplete(task, "总包单位填报工程材料、设备报验资料", user);


            //增加审核历史过程
            projectHistoryBusiness.addGeneralContractorCompanyDocumentation(businessId, user.getId(),
                                                                            "总包单位填报工程材料、设备报验资料",
                                                                            "总包单位填报工程材料、设备报验资料",
                                                                            buyMaterialId);


            result = "总总包单位填报工程材料、设备报验资料";
        }

        return result;
    }

    /**
     * 监理公司决定不需要复检
     *
     * @param projectMaterialRetestForm
     * @return
     */
    public String submitSupervisionCompanyDecideWhetherToRecheck(ProjectMaterialRetestForm projectMaterialRetestForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialRetestForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectMaterialRetestForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.完成任务
             */

            String buyMaterialId = projectMaterialRetestForm.getProjectMaterialRetest().getBuyMaterialId();

            String buyMaterialIdOfTask = (String) taskService.getVariable(task.getId(), "buyMaterialId");
            if (!buyMaterialIdOfTask.equalsIgnoreCase(buyMaterialId))
                throw new BusinessException("任务中不存在此物料，请刷新页面后重试");

            String projectMaterialRetestId = projectBusinessService.submitSupervisionCompanyDecideWhetherToRecheck(
                    projectMaterialRetestForm);

            if (projectMaterialRetestId == null)
                throw new BusinessException("监理判断是否需要复试失败");

            //设置复检结果，根据此结果走不同的流程
            int nNeedReCheckReviewResult = projectMaterialRetestForm.getProjectMaterialRetest().getNeedRetest();


            //前端传过来的值：0不需要复检，1需要复检
            if (nNeedReCheckReviewResult != 0) nNeedReCheckReviewResult = 1;

            int nReCheckReviewResult = projectMaterialRetestForm.getProjectMaterialRetest().getReviewResult();
            if (nReCheckReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReCheckReviewResult =0;
            else nReCheckReviewResult = 1;

            taskService.setVariable(task.getId(), "nNeedReCheckReviewResult", nNeedReCheckReviewResult);
            taskService.setVariable(task.getId(), "nReCheckReviewResult", nReCheckReviewResult);


            if (nReCheckReviewResult == 0){
                taskService.setVariable(task.getId(),"projectMaterialRetestId",projectMaterialRetestId);
            }
            //完成任务
            setTaskComplete(task, "监理判断是否需要复试", user);

            //增加审核历史过程
            projectHistoryBusiness.addSupervisionCompanyDecidePassReCheck(businessId, user.getId(),
                                                                          "监理判断是否需要复试",
                                                                          "监理判断是否需要复试",
                                                                          projectMaterialRetestId);


            result = "监理判断不需要复试";
        }

        return result;
    }


    /**
     * 总包单位申报项目材料批次验收
     *
     * @param projectMaterialAcceptanceBatchForm
     * @return
     */
    public String submitGeneralContractorProjectMaterialBatchAcceptance(ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceBatchForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectMaterialAcceptanceBatchForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.完成任务
             */


           if (projectMaterialAcceptanceBatchForm.getProjectMaterialAcceptanceList().size() == 1){
               String projectMaterialAcceptanceBatchId = projectBusinessService.addAcceptanceBatchForm(
                       projectMaterialAcceptanceBatchForm);
               if (projectMaterialAcceptanceBatchId == null) throw new BusinessException("总包单位申报项目材料批次验收失败");
               taskService.setVariable(task.getId(), "projectMaterialAcceptanceBatchId",projectMaterialAcceptanceBatchId);
               setTaskComplete(task, "总包单位申报项目材料批次验收", user);
               projectHistoryBusiness.addAcceptanceReviewGeneralContractorCompany(businessId, user.getId(),
                            "总包单位申报项目材料批次验",
                            "总包单位申报项目材料批次验收",
                            projectMaterialAcceptanceBatchId);
           }else if (projectMaterialAcceptanceBatchForm.getProjectMaterialAcceptanceList().size() > 1){
               ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm0 = new ProjectMaterialAcceptanceBatchForm();
               projectMaterialAcceptanceBatchForm0.setProjectId(projectMaterialAcceptanceBatchForm.getProjectId());
               projectMaterialAcceptanceBatchForm0.setTaskId(projectMaterialAcceptanceBatchForm.getTaskId());
               projectMaterialAcceptanceBatchForm0.setProjectMaterialAcceptanceBatch(projectMaterialAcceptanceBatchForm.
                       getProjectMaterialAcceptanceBatch());
               projectMaterialAcceptanceBatchForm0.setProjectMaterialAcceptanceList(Collections.singletonList(projectMaterialAcceptanceBatchForm.
                       getProjectMaterialAcceptanceList().get(0)));
               String projectMaterialAcceptanceBatchId0 = projectBusinessService.addAcceptanceBatchForm(projectMaterialAcceptanceBatchForm0);
               if (projectMaterialAcceptanceBatchId0 == null) throw new BusinessException("总包单位申报项目材料批次验收失败");
               taskService.setVariable(task.getId(), "projectMaterialAcceptanceBatchId",projectMaterialAcceptanceBatchId0);

               List<ProjectMaterialAcceptance> projectMaterialAcceptanceList = projectMaterialAcceptanceBatchForm.
                       getProjectMaterialAcceptanceList().subList(1,projectMaterialAcceptanceBatchForm.getProjectMaterialAcceptanceList().size());
               for (ProjectMaterialAcceptance projectMaterialAcceptance : projectMaterialAcceptanceList){
                   ProjectMaterialAcceptanceBatchForm singleProjectMaterialAcceptanceBatchForm = new ProjectMaterialAcceptanceBatchForm();
                   singleProjectMaterialAcceptanceBatchForm.setProjectId(projectMaterialAcceptanceBatchForm.getProjectId());
                   singleProjectMaterialAcceptanceBatchForm.setTaskId(projectMaterialAcceptanceBatchForm.getTaskId());
                   singleProjectMaterialAcceptanceBatchForm.setProjectMaterialAcceptanceBatch(projectMaterialAcceptanceBatchForm.
                           getProjectMaterialAcceptanceBatch());
                   singleProjectMaterialAcceptanceBatchForm.setProjectMaterialAcceptanceList(Collections.singletonList(projectMaterialAcceptance));
                   String projectMaterialAcceptanceBatchId = projectBusinessService.addAcceptanceBatchForm(singleProjectMaterialAcceptanceBatchForm);
                   setTaskComplete(task, "总包单位申报项目材料批次验", user);
                   projectHistoryBusiness.addAcceptanceReviewGeneralContractorCompany(businessId, user.getId(),
                            "总包单位申报项目材料批次验",
                            "总包单位申报项目材料批次验收",
                            projectMaterialAcceptanceBatchId);
                   String processKey = "Process_Project_Material";
                   ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessId);
                   runtimeService.createProcessInstanceModification(processInstance.getId())
                           .cancelAllForActivity("Activity_Construction_Company_Create_Project")
                           .startBeforeActivity("Activity_Flow_Need")
                           .setVariable("projectMaterialAcceptanceBatchId", projectMaterialAcceptanceBatchId)
                           .setVariable("projectId", businessId)
                           .execute();
               }

           }
           else throw new BusinessException("参数错误");
            result = "总包单位申报项目材料批次验收";
        }

        return result;
    }
    /**
     * 总包单位多次验收
     *
     * @param
     * @return
     */
    public String submitGeneralContractorNewTaskOfProjectMaterialAcceptance(ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm){
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        if (projectMaterialAcceptanceBatchForm == null)
            throw new BusinessException("参数为空");

//           0.总包选择 新订购，如果 总包有订购 任务，不增加新的流程实例，啥也不做；
//        如果 总包 没有订购任务， 增加新的流程实例；
//        1.根据项目号找到所有的流程
//        2。在这些流程中，找到走到订购的流程，然后保留1个
//                                    （从头最远走到订购）
        String businessId = projectMaterialAcceptanceBatchForm.getProjectId();
        if (getExitOrderTaskByUserAndProjectId(businessId)) return "";
        else {
            String processKey = "Process_Project_Material";

            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessId);
            runtimeService.createProcessInstanceModification(processInstance.getId())
                    .cancelAllForActivity("Activity_Construction_Company_Create_Project")
                    .startBeforeActivity("Activity_General_Contractor_Company_New_Task_Of_Project_Material_Acceptance")
                    .setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, businessId)
                    .setVariable("isNewAcceptanceTask",1)
                    .execute();
            return "";
        }
    }

    /**
     * 监理单位对材料批次验收进行审核
     *
     * @param projectMaterialAcceptanceReviewEmployeeForm
     * @return
     */
    public String submitSupervisionCompanyProjectMaterialBatchAcceptanceReview(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),
                                         projectMaterialAcceptanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，无法进行总包单位选择品牌");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.完成任务
             */


            String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(task.getId(),
                                                                                       "projectMaterialAcceptanceBatchId");


            String projectMaterialAcceptanceReviewModeId = projectBusinessService.submitSupervisionCompanySubmitProjectMaterialBatchAcceptanceReview(
                    projectMaterialAcceptanceReviewEmployeeForm, projectMaterialAcceptanceBatchId);

            if (projectMaterialAcceptanceReviewModeId == null)
                throw new BusinessException("监理单位对材料批次验收审核失败");

            //最终审核结果
            int nReviewResult = projectMaterialAcceptanceReviewEmployeeForm.getProjectMaterialAcceptanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nSupervisionCompanyProjectMaterialAcceptanceReviewResult",
                                    nReviewResult);
            if (nReviewResult == 0){
                taskService.setVariable(task.getId(), "supervisionCompanyMaterialAcceptanceReviewResultOfNotPassed",projectMaterialAcceptanceReviewModeId);
            }

            //完成任务
            setTaskComplete(task, "监理单位对材料批次验收进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addSupervisionCompanyAcceptanceReview(businessId, user.getId(),
                                                                         "监理单位审核材料批次验收",
                                                                         "监理单位对材料批次验收进行审核",
                                                                         projectMaterialAcceptanceReviewModeId);


            result = "监理单位对材料批次验收进行审核";
        }

        return result;
    }


    /**
     * 项目材料验收：送审操作：工程部经理直接审核
     *
     * @param projectMaterialAcceptanceReviewManagerDirectForm
     * @return
     */
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDirect(ProjectMaterialAcceptanceReviewManagerDirectForm projectMaterialAcceptanceReviewManagerDirectForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceReviewManagerDirectForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectMaterialAcceptanceReviewManagerDirectForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(task.getId(),
                                                                                       "projectMaterialAcceptanceBatchId");


            //项目员工审核项目
            String projectMaterialAcceptanceReviewModeId = projectBusinessService.addAcceptanceFormOfManagerReviewDirectly(
                    projectMaterialAcceptanceReviewManagerDirectForm,
                    projectMaterialAcceptanceBatchId);

            //项目经理直接审核
            taskService.setVariable(task.getId(), "nProjectMaterialAcceptanceEngineeringManagerDispatchReviewResult",
                                    1);


            //增加审核历史过程
            projectHistoryBusiness.addAcceptanceReviewEngineeringDepartmentManagerDirectly(
                    businessId,
                    user.getId(),
                    "工程部审核项目材料验收",
                    "项目经理直接验收项目材料",
                    projectMaterialAcceptanceReviewModeId
            );


            //项目经理审批结果
            int nReviewResult = projectMaterialAcceptanceReviewManagerDirectForm.getProjectMaterialAcceptanceReviewUser().getReviewResult();

            //项目经理审批结果
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nEngineeringDepartmentManagerProjectMaterialAcceptanceReviewResult",
                                    nReviewResult);
           if (nReviewResult == 0){
               taskService.setVariable(task.getId(),"DepartmentManagerProjectMaterialAcceptanceReviewNotPassed",projectMaterialAcceptanceReviewModeId);
           }

            //完成任务
            setTaskComplete(task, "项目经理将验收任务分发给项目经理审核", user);


            result = "项目员工项目材料验收审核成功";
        }

        return result;
    }


    /**
     * 项目材料验收：送审操作：工程部经理分发给员工
     *
     * @param projectMaterialAcceptanceReviewDispatchForm
     * @return
     */
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerDispatchToEmployee(ProjectMaterialAcceptanceReviewDispatchForm projectMaterialAcceptanceReviewDispatchForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }

        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceReviewDispatchForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId() ,projectMaterialAcceptanceReviewDispatchForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            //执行业务
            //将审核分派保存到数据库

            String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(task.getId(),
                                                                                       "projectMaterialAcceptanceBatchId");

            String projectMaterialAcceptanceReviewModeId = projectBusinessService.addForm(
                    projectMaterialAcceptanceReviewDispatchForm,
                    projectMaterialAcceptanceBatchId);

            //分配方式ModeId,后面依靠此变量获取审核记录
            taskService.setVariable(task.getId(), "projectMaterialAcceptanceReviewModeId",
                                    projectMaterialAcceptanceReviewModeId);

            //项目经理分派任务，先由项目员工审批，然后再有项目经理审批
            taskService.setVariable(task.getId(), "nProjectMaterialAcceptanceEngineeringManagerDispatchReviewResult",
                                    0);


            //完成任务
            setTaskComplete(task, "项目经理将项目材料分发给项目员工进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAcceptanceReviewEngineeringDepartmentManagerDispatch(
                    businessId,
                    user.getId(),
                    "工程部材料验收",
                    "项目经理将项目材料分发给项目员工进行审核",
                    projectMaterialAcceptanceReviewModeId
            );


            result = "项目经理将项目材料分发给项目员工进行审核成功";
        }

        return result;
    }


    /**
     * 项目材料验收：送审操作：工程部员工审核
     *
     * @param projectMaterialAcceptanceReviewEmployeeForm
     * @return
     */
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentEmployee(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),projectMaterialAcceptanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(task.getId(),
                                                                                       "projectMaterialAcceptanceBatchId");

            String projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                            "projectMaterialAcceptanceReviewModeId");

            //项目员工审核项目
            String projectMaterialAcceptanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectMaterialAcceptanceReviewEmployeeForm,
                    projectMaterialAcceptanceBatchId,
                    projectMaterialAcceptanceReviewModeId);

            if (projectMaterialAcceptanceReviewUserId == null)
                throw new BusinessException("项目员工审核失败");

            //员工首先审核，需要项目经理汇总
            taskService.setVariable(task.getId(), "nProjectMaterialAcceptanceEngineeringManagerDirectlyReviewResult",
                                    0);


            //完成任务
            setTaskComplete(task, "项目员工对项目材料验收进行审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAcceptanceReviewEngineeringDepartmentEmployee(
                    businessId,
                    user.getId(),
                    "工程部材料验收",
                    "项目员工对项目材料验收进行审核",
                    projectMaterialAcceptanceReviewUserId
            );


            result = "项目员工对项目材料验收进行审核成功";
        }

        return result;
    }

    public String submitProjectMaterialAcceptanceRemainReviewOfEngineeringDepartmentOfManager(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceReviewEmployeeForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectMaterialAcceptanceReviewEmployeeForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException("没有审核权限，制单人不是当前待办任务，项目员工无法对材料品牌、物料申请进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */

            String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(task.getId(),
                                                                                       "projectMaterialAcceptanceBatchId");

            String projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                            "projectMaterialAcceptanceReviewModeId");

            //项目员工审核项目
            String projectMaterialAcceptanceReviewUserId = projectBusinessService.addFormOfEmployee(
                    projectMaterialAcceptanceReviewEmployeeForm,
                    projectMaterialAcceptanceBatchId,
                    projectMaterialAcceptanceReviewModeId);

            if (projectMaterialAcceptanceReviewUserId == null)
                throw new BusinessException("项目员工审核失败");

            //此时没有员工进行过审核,项目经理直接审核
            taskService.setVariable(task.getId(), "nProjectMaterialAcceptanceEngineeringManagerDirectlyReviewResult",
                                    1);


            //最终审核结果
            int nReviewResult = projectMaterialAcceptanceReviewEmployeeForm.getProjectMaterialAcceptanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nEngineeringDepartmentManagerProjectMaterialAcceptanceReviewResult",
                                    nReviewResult);
            if (nReviewResult == 0){
                taskService.setVariable(task.getId(),"DepartmentManagerProjectMaterialAcceptanceReviewNotPassed",projectMaterialAcceptanceReviewModeId);
           }

            //完成任务
            setTaskComplete(task, "项目经理审核", user);

            //增加审核历史过程
            projectHistoryBusiness.addAcceptanceReviewEngineeringDepartmentManagerWithoutEmployeeReviewed(
                    businessId,
                    user.getId(),
                    "工程部材料验收",
                    "项目经理审核",
                    projectMaterialAcceptanceReviewUserId
            );


            result = "项目经理审核成功";
        }

        return result;
    }

    /**
     * 项目材料验收：送审操作：工程部经理汇总
     *
     * @param projectMaterialAcceptanceReviewManagerSummaryForm
     * @return
     */
    public String submitProjectMaterialAcceptanceReviewOfEngineeringDepartmentManagerSummary(ProjectMaterialAcceptanceReviewManagerSummaryForm projectMaterialAcceptanceReviewManagerSummaryForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectMaterialAcceptanceReviewManagerSummaryForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(), projectMaterialAcceptanceReviewManagerSummaryForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */
            String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(task.getId(),
                                                                                       "projectMaterialAcceptanceBatchId");


            String projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(task.getId(),
                                                                                            "projectMaterialAcceptanceReviewModeId");

            //项目员工审核项目
            String projectReviewUserId = projectBusinessService.addFormOfManagerSummary(
                    projectMaterialAcceptanceReviewManagerSummaryForm, projectMaterialAcceptanceBatchId,
                    projectMaterialAcceptanceReviewModeId);
            if (projectReviewUserId == null)
                throw new BusinessException("添加项目经理项目审核汇总意见失败");


            //最终审核结果
            int nReviewResult = projectMaterialAcceptanceReviewManagerSummaryForm.getProjectMaterialAcceptanceReviewUser().getReviewResult();
            if (nReviewResult == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED) nReviewResult = 0;
            else nReviewResult = 1;
            taskService.setVariable(task.getId(), "nEngineeringDepartmentManagerProjectMaterialAcceptanceReviewResult",
                                    nReviewResult);

            //项目经理已经填写好了意见，不需要等待其它项目员工填写意见了，走向下一个节点
            taskService.setVariable(task.getId(), "engineeringDepartmentManagerProjectMaterialAcceptanceSummaryReview",
                                    1);

            if (nReviewResult == 0){
                taskService.setVariable(task.getId(),"DepartmentManagerProjectMaterialAcceptanceReviewNotPassed",projectMaterialAcceptanceReviewModeId);
            }
            //完成任务
            setTaskComplete(task, "项目经理项目审核汇总意见", user);

            //增加审核历史过程
            projectHistoryBusiness.addAcceptanceReviewEngineeringDepartmentManagerSummary(
                    businessId,
                    user.getId(),
                    "工程部材料验收",
                    "项目经理项目审核汇总意见",
                    projectReviewUserId
            );


            result = "项目经理项目审核汇总成功";
        }

        return result;
    }

    /**
     * 工程部经理结束项目：结束项目操作
     *
     * @param projectEndForm
     * @return
     */
    public String submitEngineeringDepartmentManagerEndTheProject(ProjectEndForm projectEndForm) {
        User user = userService.getCurrentLoginUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }


        //得到项目号，根据项目号找到当前任务
        String businessId = projectEndForm.getProjectId();
        String result = "";
        Task task = getTaskByBusinessKey(businessId, user.getId(),projectEndForm.getTaskId());
        if (task == null) {
            throw new BusinessException("未找到待办任务");
        } else if (!task.getAssignee().equalsIgnoreCase(user.getId())) {
            throw new BusinessException(
                    "没有审核权限，制单人不是当前待办任务，项目经理无法将项目材料分发给项目员工进行审核");
        } else {

            /*
             * 基本步骤：
             * 1.完成业务逻辑，例如将相关数据添加到数据库中
             * 2.设置特定的任务变量,供后面使用，例如projectId等
             * 3.设置下一个阶段步骤，前端用来区分显示哪个页面
             * 4.完成任务
             */


            //项目员工审核项目
            String projectEndId = projectBusinessService.addFormOfEndProject(projectEndForm);
            if (projectEndId == null)
                throw new BusinessException("添加项目经理结束项目失败");

            //最终审核结果
            int nReviewResult = projectEndForm.getReviewResult();
            if (nReviewResult != 0) nReviewResult = 1;
            else nReviewResult = 0;
            taskService.setVariable(task.getId(), "nProjectCompletionReviewResult",
                                    nReviewResult);


            //完成任务
            setTaskComplete(task, "项目经理结束项目", user);

            //增加审核历史过程
            projectHistoryBusiness.addProjectEnd(
                    businessId,
                    user.getId(),
                    "项目结项",
                    "工程部项目经理项目结束项目",
                    projectEndId
            );


            result = "项目经理项目结束项目";
        }

        return result;
    }


    public ProjectReviewStatistics getStatisticsOfAppearanceReviewUserViewByTaskId(String taskId) {
        String projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                "projectAppearanceReviewModeId");
        if (projectAppearanceReviewModeId == null) return null;
        return projectBusinessService.getStatisticsOfAppearanceReviewUserViewByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId);
    }

    public Page<ProjectAppearanceReviewUserView> getPageProjectAppearanceReviewUserViewByProjectIdAndTaskId(String projectId,
                                                                                                            String taskId,
                                                                                                            Integer pageNo,
                                                                                                            Integer pageSize) {

        String projectAppearanceReviewModeId = null;
        if (projectBusinessService.isCurrentLoginUserInEngineeringDepartment()) {

            projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                             "projectAppearanceReviewModeId");
        } else if (projectBusinessService.isCurrentLoginUserInDesignDepartment()) {

            projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                             "projectDesignDepartmentAppearanceReviewModeId");
        }
        if (projectAppearanceReviewModeId == null) return null;
        return projectBusinessService.getPageProjectAppearanceReviewUserViewByTaskId(
                projectAppearanceReviewModeId, pageNo, pageSize);
    }
    public  List<ProjectReviewUserView> getProjectReviewUserViewListByProjectIdAndTaskId(String projectId, String taskId ,int designCompanyIndex){

        String projectReviewId = null;
        projectReviewId = (String) taskService.getVariable(taskId,
                                                               "projectReviewId" + designCompanyIndex);

        return projectBusinessService.getProjectReviewUserViewListByProjectIdAndTaskId(projectReviewId);
    }
    public Page<ProjectMaterialAcceptanceReviewUserView> getPageProjectMaterialAcceptanceReviewUserViewByProjectIdAndTaskId(String projectId,
                                                                                                            String taskId,
                                                                                                            Integer pageNo,
                                                                                                            Integer pageSize){
        String projectMaterialAcceptanceReviewModeId = null;
        if (projectBusinessService.isCurrentLoginUserInEngineeringDepartment()) {

            projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                             "projectMaterialAcceptanceReviewModeId");
        }else if(projectBusinessService.isCurrentLoginUserInDesignDepartment()){
            projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                             "projectDesignDepartmentMaterialAcceptanceReviewModeId");
        }
        return projectBusinessService.getPageProjectMaterialAcceptanceReviewUserViewByTaskId(
                projectMaterialAcceptanceReviewModeId, pageNo, pageSize);
    }


    public Page<ProjectAppearanceReviewUserView> getPageOfProjectAppearanceReviewDesignDepartmentUserViewByProjectIdAndTaskId(String projectId,
                                                                                                                              String taskId,
                                                                                                                              Integer pageNo,
                                                                                                                              Integer pageSize) {

        String projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                "projectDesignDepartmentAppearanceReviewModeId");
        if (projectAppearanceReviewModeId == null) return null;
        return projectBusinessService.getPageProjectAppearanceReviewUserViewByTaskId(
                projectAppearanceReviewModeId, pageNo, pageSize);
    }

    public List<ProjectAppearanceReviewUserView> getListOfProjectAppearanceReviewUserViewByProjectIdAndTaskId(String projectId,
                                                                                                              String taskId) {
        String projectAppearanceReviewModeId = null;
        if (projectBusinessService.isCurrentLoginUserInEngineeringDepartment()) {

            projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                             "projectAppearanceReviewModeId");
        } else if (projectBusinessService.isCurrentLoginUserInDesignDepartment()) {

            projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                             "projectDesignDepartmentAppearanceReviewModeId");
        }
        if (projectAppearanceReviewModeId == null) return null;
        return projectBusinessService.getListOfProjectAppearanceReviewUserViewByTaskId(
                projectAppearanceReviewModeId);
    }

    public ProjectReviewStatistics getStatisticsOfDesignDepartmentAppearanceReviewUserViewByProjectIdAndTaskId(String projectId,
                                                                                                               String taskId) {
        String projectDesignDepartmentAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                                "projectDesignDepartmentAppearanceReviewModeId");
        if (projectDesignDepartmentAppearanceReviewModeId == null) return null;
        return projectBusinessService.getStatisticsOfAppearanceReviewUserViewByProjectAppearanceReviewModeId(
                projectDesignDepartmentAppearanceReviewModeId);
    }
    public ProjectReviewStatistics getStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByTaskId(String projectId,
                                                                                                                String taskId){
        String projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                         "projectMaterialAcceptanceReviewModeId");
        if (projectMaterialAcceptanceReviewModeId == null) return null;
        return projectBusinessService.getStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByReviewModeId(
                projectMaterialAcceptanceReviewModeId);
    }


    public ProjectReviewStatistics getStatisticsOfProjectMaterialReviewUserViewByTaskId(String taskId,
                                                                                        String projectId,
                                                                                        int designCompanyIndex) {

        if (designCompanyIndex < 0) {
            return null;
        }
        String projectReviewId = (String) taskService.getVariable(taskId,
                                                                  "projectReviewId" + designCompanyIndex);
        return projectBusinessService.getStatisticsOfProjectMaterialReviewUserViewByProjectReviewId(projectReviewId);

    }

    public Page<ProjectReviewUserView> getProjectMaterialUserUserReViewPageByProjectIdAndTaskId(String projectId,
                                                                                                String taskId,
                                                                                                int designCompanyIndex,
                                                                                                Integer pageNo,
                                                                                                Integer pageSize) {

        if (designCompanyIndex < 0) {
            return null;
        }

        String projectReviewId = (String) taskService.getVariable(taskId,
                                                                  "projectReviewId" + designCompanyIndex);
        return projectBusinessService.getProjectMaterialUserUserReViewPageByTaskId(projectReviewId, pageNo, pageSize);
    }


    public List<ProjectMaterialVerificationDocumentView> getBuyMaterialRecheckIsRequiredByProjectIdAndTaskId(String projectId,
                                                                                                             String taskId) {

        String buyMaterialId = (String) taskService.getVariable(taskId,
                                                                "buyMaterialId");
        return projectBusinessService.getListProjectMaterialVerificationDocumentViewOfReCheckIsRequiredByBuyMaterialId(
                buyMaterialId);
    }

    public List<ProjectMaterialVerificationDocumentView> getBuyMaterialVerificationDocumentViewByProjectIdAndTaskId(String projectId,
                                                                                                                    String taskId) {
        String buyMaterialId = (String) taskService.getVariable(taskId,
                                                                "buyMaterialId");
        return projectBusinessService.getListMaterialVerificationDocumentViewByBuyMaterialId(buyMaterialId);
    }


    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUser(String projectId,
                                                                                                      String taskId,
                                                                                                      Integer pageNo,
                                                                                                      Integer pageSize) {
        String projectMaterialAcceptanceBatchId = (String) taskService.getVariable(taskId,
                                                                                   "projectMaterialAcceptanceBatchId");
        return projectBusinessService.getProjectMaterialAcceptanceViewListByCurrentLoginUser(
                projectMaterialAcceptanceBatchId, pageNo, pageSize);
    }

    public ProjectReviewUserView getFeedbackOfProjectMaterialReviewedByProjectIdAndTaskId(String projectId,
                                                                                          String taskId
    ) {
        User user = userService.getCurrentLoginUser();
        //获得当前设计单位序号
        int designCompanyIndex = getDesignCompanyIndexByUserIdAndProjectId(taskId, user.getId(), projectId);
        if (designCompanyIndex < 0) {
            return null;
        }

        String projectReviewId = (String) taskService.getVariable(taskId,
                                                                  "projectReviewId" + designCompanyIndex);
        return projectBusinessService.getFeedbackOfProjectMaterialReviewedByReviewId(
                projectReviewId);
    }

    public Page<ProjectMaterialView> getProjectMaterialPageViewByTaskIdAndProjectId(String projectId,
                                                                                    String taskId,
                                                                                    Integer designCompanyIndex,
                                                                                    Integer pageNo,
                                                                                    Integer pageSize) {
        String companyId = getDesignCompanyIdByTaskIdAndDesignCompanyIndex(taskId, designCompanyIndex);
        return projectBusinessService.getProjectMaterialPageViewByProjectIdAndCompanyId(projectId, companyId, pageNo,
                                                                                        pageSize);

    }

    public ProjectAppearanceReviewUserView getFeedbackOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(String projectId,
                                                                                                                    String taskId) {
        String projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                "supervisionCompanySelectBrandReviewResultOfNotPassed");
        if (projectAppearanceReviewModeId == null)
            return null;
        return projectBusinessService.getFeedbackOfSelectBrandReviewedByReviewId(
                projectAppearanceReviewModeId);
    }
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewedOfEngineeringDepartmentByProjectAndTaskId(String projectId,
                                                                                                                String taskId){
        String projectAffectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                "AffectAppearanceReviewModeId");
        if (projectAffectAppearanceReviewModeId == null)
            return null;
        return projectBusinessService.getFeedbackOfAffectAppearanceReviewedByReviewId(
                projectAffectAppearanceReviewModeId);
    }
    public ProjectAppearanceReviewUserView getFeedbackOfNotAffectAppearanceReviewedOfEngineeringDepartmentByProjectAndTaskId(String projectId,
                                                                                                                          String taskId){
        String projectNotAffectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                "notAffectAppearanceReviewModeId");
        if (projectNotAffectAppearanceReviewModeId == null)
            return null;
        return projectBusinessService.getFeedbackOfAffectAppearanceReviewedByReviewId(
                projectNotAffectAppearanceReviewModeId);
    }
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewedOfDesignCompanyByProjectAndTaskId(String projectId,
                                                                                                                String taskId){
        String projectAffectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                "DesignCompanyAffectAppearanceReviewModeId");
        if (projectAffectAppearanceReviewModeId == null)
            return null;
        return projectBusinessService.getFeedbackOfAffectAppearanceReviewedByReviewId(
                projectAffectAppearanceReviewModeId);

    }
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewedOfDesignDepartmentByProjectAndTaskId(String projectId,
                                                                                                                String taskId){
        String projectAffectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                "DesignDepartmentAffectAppearanceReviewModeId");
        if (projectAffectAppearanceReviewModeId == null)
            return null;
        return projectBusinessService.getFeedbackOfAffectAppearanceReviewedByReviewId(projectAffectAppearanceReviewModeId);
    }
    public ProjectMaterialRetestView getFeedbackOfNeedReCheckNotPassedOfSupervisionCompanyByProjectIdAndTaskId(String projectId,
                                                                                                                     String taskId) {
        String projectMaterialRetestId = (String) taskService.getVariable(taskId, "projectMaterialRetestId");
        return projectBusinessService.getFeedbackOfProjectMaterialAcceptanceReviewedByRetestId(
                projectMaterialRetestId);

    }

    public ProjectMaterialAcceptanceReviewUserView getFeedbackOfProjectMaterialAcceptanceReviewedOfSupervisionCompanyByProjectIdAndTaskId(String projectId,
                                                                                                                     String taskId) {
        String projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                     "supervisionCompanyMaterialAcceptanceReviewResultOfNotPassed");
        return projectBusinessService.getFeedbackOfProjectMaterialAcceptanceReviewedByReviewId(
                projectMaterialAcceptanceReviewModeId);

    }
    public ProjectMaterialAcceptanceReviewUserView getFeedbackOfProjectMaterialAcceptanceReviewedOfEngineeringDepartmentByProjectIdAndTaskId(String projectId,
                                                                                                                                          String taskId) {
        String projectMaterialAcceptanceReviewModeId = (String) taskService.getVariable(taskId,
                "DepartmentManagerProjectMaterialAcceptanceReviewNotPassed");
        return projectBusinessService.getFeedbackOfProjectMaterialAcceptanceReviewedByReviewId(
                projectMaterialAcceptanceReviewModeId);

    }



    public UseMaterialBrandSelectView getNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(String projectId,
                                                                                                                     String taskId) {
        String projectAppearanceReviewModeId = (String) taskService.getVariable(taskId,
                                                                                "supervisionCompanySelectBrandReviewResultOfNotPassed");
        return projectBusinessService.getNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
                projectAppearanceReviewModeId);
    }
}
