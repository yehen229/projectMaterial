package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectCompanyAllUserView {
    private List<ProjectUserView> projectUserViewList;//设计单位员工列表，包括项目经理和项目员工
    private Company company;

    public List<ProjectUserView> getProjectUserViewList() {
        return projectUserViewList;
    }

    public void setProjectUserViewList(List<ProjectUserView> projectUserViewList) {
        this.projectUserViewList = projectUserViewList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
