package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseMaterialForm {
    private String projectId;//项目号
    private String userId;
    private String taskId;
    private List<UseMaterialFormItem> useMaterialFormItems;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<UseMaterialFormItem> getUseMaterialFormItems() {
        return useMaterialFormItems;
    }

    public void setUseMaterialFormItems(List<UseMaterialFormItem> useMaterialFormItems) {
        this.useMaterialFormItems = useMaterialFormItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
