package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceBatchView {
    private ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch;//t_project_material_acceptance_batch
    private User user;//外键：t_user_id,关联表为：t_user表,
    private Project project;//外键：t_project_id,关联表为：t_project表,


    public ProjectMaterialAcceptanceBatch getProjectMaterialAcceptanceBatch() {
        return projectMaterialAcceptanceBatch;
    }

    public void setProjectMaterialAcceptanceBatch(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        this.projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}