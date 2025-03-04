package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;/**
 * Created with IntelliJ IDEA.
 *
 * @Author: cyliu
 * @Date: 2024/11/21 20:18
 * @Description:
 */

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;

/**
 * @param
 * @return
 */
public class GeneralContractorSubProjectMaterialVerificationDocumentForm {
    private Project project;
    private String taskId;

    public Project getProject() {
        return project;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

