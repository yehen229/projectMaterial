package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end;

import org.springframework.stereotype.Component;

@Component
public class ProjectEndFileView {
    private ProjectEndFile projectEndFile;//t_project_end_file
    private ProjectEnd projectEnd;//外键：t_project_end_id,关联表为：t_project_end表,


    public ProjectEndFile getProjectEndFile() {
        return projectEndFile;
    }

    public void setProjectEndFile(ProjectEndFile projectEndFile) {
        this.projectEndFile = projectEndFile;
    }

    public ProjectEnd getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(ProjectEnd projectEnd) {
        this.projectEnd = projectEnd;
    }
}