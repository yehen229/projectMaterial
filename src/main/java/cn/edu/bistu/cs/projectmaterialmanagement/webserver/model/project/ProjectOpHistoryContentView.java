package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import org.springframework.stereotype.Component;

@Component
public class ProjectOpHistoryContentView {
    private ProjectOpHistoryContent projectOpHistoryContent;//t_project_op_history_content
    private ProjectOpHistory projectOpHistory;//外键：t_project_op_history_id,关联表为：t_project_op_history表,


    public ProjectOpHistoryContent getProjectOpHistoryContent() {
        return projectOpHistoryContent;
    }

    public void setProjectOpHistoryContent(ProjectOpHistoryContent projectOpHistoryContent) {
        this.projectOpHistoryContent = projectOpHistoryContent;
    }

    public ProjectOpHistory getProjectOpHistory() {
        return projectOpHistory;
    }

    public void setProjectOpHistory(ProjectOpHistory projectOpHistory) {
        this.projectOpHistory = projectOpHistory;
    }
}