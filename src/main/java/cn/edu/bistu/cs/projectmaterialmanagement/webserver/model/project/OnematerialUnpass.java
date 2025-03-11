package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;

public class OnematerialUnpass {
    private String userid;
    private String reviewcotent;
    private ProjectReviewUser projectReviewUser;
    private User user;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReviewcotent() {
        return reviewcotent;
    }

    public void setReviewcotent(String reviewcotent) {
        this.reviewcotent = reviewcotent;
    }

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
}
