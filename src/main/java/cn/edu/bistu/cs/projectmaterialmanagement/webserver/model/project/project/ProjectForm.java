package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectForm {
    private Project project;
    private List<Company> companyDesignList;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Company> getCompanyDesignList() {
        return companyDesignList;
    }

    public void setCompanyDesignList(List<Company> companyDesignList) {
        this.companyDesignList = companyDesignList;
    }
}