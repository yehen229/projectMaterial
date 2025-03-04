package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end;

public class ProjectEndForm {
    private ProjectEnd projectEnd;
    private int reviewResult;//项目是否结项，0：不结束项目；1.结束项目
    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
    private String projectId;
    private String taskId;

    public ProjectEnd getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(ProjectEnd projectEnd) {
        this.projectEnd = projectEnd;
    }

    public String getReviewTempDir() {
        return reviewTempDir;
    }

    public void setReviewTempDir(String reviewTempDir) {
        this.reviewTempDir = reviewTempDir;
    }

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

    public int getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(int reviewResult) {
        this.reviewResult = reviewResult;
    }
}
