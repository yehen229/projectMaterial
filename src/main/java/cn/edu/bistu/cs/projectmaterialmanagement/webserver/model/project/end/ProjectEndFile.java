package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectEndFile {
    private String id;//id,主键
    private String filePath;//file_path
    private String projectEndId;//t_project_end_id,外键,	t_project_end_id<-表t_project_end.id
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getProjectEndId() {
        return projectEndId;
    }

    public void setProjectEndId(String projectEndId) {
        this.projectEndId = projectEndId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}