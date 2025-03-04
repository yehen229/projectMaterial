package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectReviewView {
    private ProjectReview projectReview;//t_project_review
    private Project project;//外键：t_project_id,关联表为：t_project表,
    private ProjectReviewMode projectReviewMode;

    public ProjectReview getProjectReview() {
        return projectReview;
    }

    public void setProjectReview(ProjectReview projectReview) {
        this.projectReview = projectReview;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectReviewMode getProjectReviewMode() {
        return projectReviewMode;
    }

    public void setProjectReviewMode(ProjectReviewMode projectReviewMode) {
        this.projectReviewMode = projectReviewMode;
    }
}