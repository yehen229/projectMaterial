package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectFileView {
    private ProjectFile projectFile;//t_project_file
    private Project project;//外键：t_project_id,关联表为：t_project表,


    public ProjectFile getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(ProjectFile projectFile) {
        this.projectFile = projectFile;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}