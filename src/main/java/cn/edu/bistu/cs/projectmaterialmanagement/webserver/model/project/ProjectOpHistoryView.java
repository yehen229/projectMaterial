package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import org.springframework.stereotype.Component;

@Component
public class ProjectOpHistoryView {
    private ProjectOpHistory projectOpHistory;//t_project_op_history
    private User user;//外键：t_user_id,关联表为：t_user表,操作或审批人
    private Project project;//外键：t_project_id,关联表为：t_project表,审批的项目
    private Company company;//用户所在单位或部门


    public ProjectOpHistory getProjectOpHistory() {
        return projectOpHistory;
    }

    public void setProjectOpHistory(ProjectOpHistory projectOpHistory) {
        this.projectOpHistory = projectOpHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}