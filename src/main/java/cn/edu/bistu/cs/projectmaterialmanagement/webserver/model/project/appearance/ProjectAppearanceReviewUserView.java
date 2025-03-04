package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectAppearanceReviewUserView {
    private ProjectAppearanceReviewUser projectAppearanceReviewUser;//t_project_appearance_review_user
    private User user;//外键：t_user_id,关联表为：t_user表,
    private ProjectAppearanceReview projectAppearanceReview;//外键：t_project_appearance_review_id,关联表为：t_project_appearance_review表,
    private List<ProjectAppearanceReviewUserFile> projectAppearanceReviewUserFileList;

    public ProjectAppearanceReviewUser getProjectAppearanceReviewUser() {
        return projectAppearanceReviewUser;
    }

    public void setProjectAppearanceReviewUser(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        this.projectAppearanceReviewUser = projectAppearanceReviewUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectAppearanceReview getProjectAppearanceReview() {
        return projectAppearanceReview;
    }

    public void setProjectAppearanceReview(ProjectAppearanceReview projectAppearanceReview) {
        this.projectAppearanceReview = projectAppearanceReview;
    }

    public List<ProjectAppearanceReviewUserFile> getProjectAppearanceReviewUserFileList() {
        return projectAppearanceReviewUserFileList;
    }

    public void setProjectAppearanceReviewUserFileList(List<ProjectAppearanceReviewUserFile> projectAppearanceReviewUserFileList) {
        this.projectAppearanceReviewUserFileList = projectAppearanceReviewUserFileList;
    }
}