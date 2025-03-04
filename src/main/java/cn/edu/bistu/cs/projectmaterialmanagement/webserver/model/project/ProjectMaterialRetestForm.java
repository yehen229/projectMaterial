package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialRetestForm {
    private ProjectMaterialRetest projectMaterialRetest;
    private String projectId;
    private String taskId;
    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中

    public ProjectMaterialRetest getProjectMaterialRetest() {
        return projectMaterialRetest;
    }

    public void setProjectMaterialRetest(ProjectMaterialRetest projectMaterialRetest) {
        this.projectMaterialRetest = projectMaterialRetest;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getReviewTempDir() {
        return reviewTempDir;
    }

    public void setReviewTempDir(String reviewTempDir) {
        this.reviewTempDir = reviewTempDir;
    }
}
