package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectAllUserView {
    private List<ProjectCompanyAllUserView> projectUserViewListDesignCompany;//设计单位员工列表，包括项目经理和项目员工
    private List<ProjectUserView> projectUserViewListDesignDepartment;//设计部员工列表，包括项目经理和项目员工
    private List<ProjectUserView> projectUserViewListEngineeringDepartment;//工程部员工列表，包括项目经理和项目员工
    private List<ProjectUserView> projectUserViewListSupervisionCompany;//监理单位员工列表，包括项目经理和项目员工
    private List<ProjectUserView> projectUserViewListConstructionCompany;//总包单位员工列表，包括项目经理和项目员工
    private ProjectView projectView;//项目

    public List<ProjectCompanyAllUserView> getProjectUserViewListDesignCompany() {
        return projectUserViewListDesignCompany;
    }

    public void setProjectUserViewListDesignCompany(List<ProjectCompanyAllUserView> projectUserViewListDesignCompany) {
        this.projectUserViewListDesignCompany = projectUserViewListDesignCompany;
    }

    public List<ProjectUserView> getProjectUserViewListDesignDepartment() {
        return projectUserViewListDesignDepartment;
    }

    public void setProjectUserViewListDesignDepartment(List<ProjectUserView> projectUserViewListDesignDepartment) {
        this.projectUserViewListDesignDepartment = projectUserViewListDesignDepartment;
    }

    public List<ProjectUserView> getProjectUserViewListEngineeringDepartment() {
        return projectUserViewListEngineeringDepartment;
    }

    public void setProjectUserViewListEngineeringDepartment(List<ProjectUserView> projectUserViewListEngineeringDepartment) {
        this.projectUserViewListEngineeringDepartment = projectUserViewListEngineeringDepartment;
    }

    public List<ProjectUserView> getProjectUserViewListSupervisionCompany() {
        return projectUserViewListSupervisionCompany;
    }

    public void setProjectUserViewListSupervisionCompany(List<ProjectUserView> projectUserViewListSupervisionCompany) {
        this.projectUserViewListSupervisionCompany = projectUserViewListSupervisionCompany;
    }

    public List<ProjectUserView> getProjectUserViewListConstructionCompany() {
        return projectUserViewListConstructionCompany;
    }

    public void setProjectUserViewListConstructionCompany(List<ProjectUserView> projectUserViewListConstructionCompany) {
        this.projectUserViewListConstructionCompany = projectUserViewListConstructionCompany;
    }

    public ProjectView getProjectView() {
        return projectView;
    }

    public void setProjectView(ProjectView projectView) {
        this.projectView = projectView;
    }
}
