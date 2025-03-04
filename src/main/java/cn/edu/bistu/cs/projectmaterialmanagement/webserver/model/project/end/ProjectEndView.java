package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectEndView {
    private ProjectEnd projectEnd;//t_project_end
    private Project projecct;//外键：t_projecct_id,关联表为：t_project表,
    private User user;//外键：t_user_id,关联表为：t_user表,
    private List<ProjectEndFile> projectEndFileList;

    public ProjectEnd getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(ProjectEnd projectEnd) {
        this.projectEnd = projectEnd;
    }

    public Project getProjecct() {
        return projecct;
    }

    public void setProjecct(Project projecct) {
        this.projecct = projecct;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProjectEndFile> getProjectEndFileList() {
        return projectEndFileList;
    }

    public void setProjectEndFileList(List<ProjectEndFile> projectEndFileList) {
        this.projectEndFileList = projectEndFileList;
    }
}