package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectReviewModeView {
    private ProjectReviewMode projectReviewMode;//t_project_review_mode
    private Project project;//外键：t_project_id,关联表为：t_project表,
    private User user;//外键：t_user_id,关联表为：t_user表,项目经理ID


    public ProjectReviewMode getProjectReviewMode() {
        return projectReviewMode;
    }

    public void setProjectReviewMode(ProjectReviewMode projectReviewMode) {
        this.projectReviewMode = projectReviewMode;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}