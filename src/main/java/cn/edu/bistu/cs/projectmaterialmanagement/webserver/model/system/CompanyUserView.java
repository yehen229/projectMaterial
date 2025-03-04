package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUserView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyUserView {
    private CompanyUser companyUser;//t_company_user
    private Company company;//外键：t_company_id,关联表为：t_company表,
    private User user;//外键：t_user_id,关联表为：t_user表,
    private List<ProjectUserView> projectUserViewList;//用户参与的项目列表


    public CompanyUser getCompanyUser() {
        return companyUser;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.companyUser = companyUser;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProjectUserView> getProjectUserViewList() {
        return projectUserViewList;
    }

    public void setProjectUserViewList(List<ProjectUserView> projectUserViewList) {
        this.projectUserViewList = projectUserViewList;
    }
}