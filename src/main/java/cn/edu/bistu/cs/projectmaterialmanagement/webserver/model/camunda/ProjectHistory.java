package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;

import java.util.Date;

public class ProjectHistory {
    private ProjectView projectView;
    private String taskId;
    private String taskName;
    private User assignee;
    private String processInstanceId;
    private Date operateDate;
}
