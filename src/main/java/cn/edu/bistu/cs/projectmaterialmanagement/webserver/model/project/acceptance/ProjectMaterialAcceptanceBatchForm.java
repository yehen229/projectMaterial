package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMaterialAcceptanceBatchForm {
    private String projectId;
    private String taskId;
    private ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch;
    private List<ProjectMaterialAcceptance> projectMaterialAcceptanceList;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ProjectMaterialAcceptanceBatch getProjectMaterialAcceptanceBatch() {
        return projectMaterialAcceptanceBatch;
    }

    public void setProjectMaterialAcceptanceBatch(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        this.projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatch;
    }

    public List<ProjectMaterialAcceptance> getProjectMaterialAcceptanceList() {
        return projectMaterialAcceptanceList;
    }

    public void setProjectMaterialAcceptanceList(List<ProjectMaterialAcceptance> projectMaterialAcceptanceList) {
        this.projectMaterialAcceptanceList = projectMaterialAcceptanceList;
    }
}