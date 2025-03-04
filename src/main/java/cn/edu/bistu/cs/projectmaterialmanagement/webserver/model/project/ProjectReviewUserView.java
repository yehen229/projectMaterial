package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectReviewUserView {
    //评阅记录用户
    private ProjectReviewUser projectReviewUser;//t_project_review_user

    //评阅用户
    private User user;//外键：t_user_id,关联表为：t_user表,

    //评阅记录
    private ProjectReview projectReview;//外键：t_project_review_id,关联表为：t_project_review表,

    //评阅的附件
    private List<ProjectReviewUserFile> projectReviewUserFileList;


    public ProjectReviewUser getProjectReviewUser() {
        return projectReviewUser;
    }

    public void setProjectReviewUser(ProjectReviewUser projectReviewUser) {
        this.projectReviewUser = projectReviewUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectReview getProjectReview() {
        return projectReview;
    }

    public void setProjectReview(ProjectReview projectReview) {
        this.projectReview = projectReview;
    }


    public List<ProjectReviewUserFile> getProjectReviewUserFileList() {
        return projectReviewUserFileList;
    }

    public void setProjectReviewUserFileList(List<ProjectReviewUserFile> projectReviewUserFileList) {
        this.projectReviewUserFileList = projectReviewUserFileList;
    }
}