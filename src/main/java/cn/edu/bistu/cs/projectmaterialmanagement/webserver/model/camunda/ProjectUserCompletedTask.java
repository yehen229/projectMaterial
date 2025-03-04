package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectUserCompletedTask {
    private ProjectView projectView;
    private String taskId;
    private String taskName;
    private User assignee;
    private String processInstanceId;
    private String taskDefinitionKey;
    private Date operateDate;

    public ProjectView getProjectView() {
        return projectView;
    }

    public void setProjectView(ProjectView projectView) {
        this.projectView = projectView;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }
}
