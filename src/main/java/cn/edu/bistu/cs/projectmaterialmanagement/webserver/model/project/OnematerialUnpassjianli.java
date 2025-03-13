package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;

public class OnematerialUnpassjianli {
    private String userid;
    private String reviewcotent;
    private ProjectMaterialRetest projectMaterialRetest;
    private User user;
    private String id;
    private Company company;

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

    public ProjectMaterialRetest getProjectMaterialRetest() {
        return projectMaterialRetest;
    }

    public void setProjectMaterialRetest(ProjectMaterialRetest projectMaterialRetest) {
        this.projectMaterialRetest = projectMaterialRetest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
