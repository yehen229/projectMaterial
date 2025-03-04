package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectUserView {
    private ProjectUser projectUser;//t_project_user
    private User user;//外键：t_user_id,关联表为：t_user表,用户
    private Project project;//外键：t_project_id,关联表为：t_project表,
    private Role role;//外键：t_role_id,关联表为：t_role表,


    public ProjectUser getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(ProjectUser projectUser) {
        this.projectUser = projectUser;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}