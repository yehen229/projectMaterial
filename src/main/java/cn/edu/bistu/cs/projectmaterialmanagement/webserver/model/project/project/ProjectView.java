package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectView {
    private Project project;//t_project
    private User user;//外键：t_user_id,关联表为：t_user表,创建者
    private Company companyConstruction;//外键：t_company_construction_id,关联表为：t_company表,建设单位：建设单位类型、内部（设计部、工程部）
    private Company companySupervision;//监理单位
    private Company companyGeneralContract;//总包单位
    private List<Company> companyDesignList;//设计单位

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

    public Company getCompanyConstruction() {
        return companyConstruction;
    }

    public void setCompanyConstruction(Company companyConstruction) {
        this.companyConstruction = companyConstruction;
    }

    public Company getCompanySupervision() {
        return companySupervision;
    }

    public void setCompanySupervision(Company companySupervision) {
        this.companySupervision = companySupervision;
    }

    public Company getCompanyGeneralContract() {
        return companyGeneralContract;
    }

    public void setCompanyGeneralContract(Company companyGeneralContract) {
        this.companyGeneralContract = companyGeneralContract;
    }

    public List<Company> getCompanyDesignList() {
        return companyDesignList;
    }

    public void setCompanyDesignList(List<Company> companyDesignList) {
        this.companyDesignList = companyDesignList;
    }
}