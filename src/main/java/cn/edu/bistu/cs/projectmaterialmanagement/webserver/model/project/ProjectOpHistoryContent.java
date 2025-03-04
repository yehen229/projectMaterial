package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectOpHistoryContent {
    private String id;//id,主键
    private String projectOpHistoryId;//t_project_op_history_id,外键,	t_project_op_history_id<-表t_project_op_history.id
    private String otherTableId;//t_other_table_id
    private int otherTableIdType;//t_other_table_id_type
    private String description;//description
    private Date deletedAt;//deleted_at

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectOpHistoryId() {
        return projectOpHistoryId;
    }

    public void setProjectOpHistoryId(String projectOpHistoryId) {
        this.projectOpHistoryId = projectOpHistoryId;
    }

    public String getOtherTableId() {
        return otherTableId;
    }

    public void setOtherTableId(String otherTableId) {
        this.otherTableId = otherTableId;
    }

    public int getOtherTableIdType() {
        return otherTableIdType;
    }

    public void setOtherTableIdType(int otherTableIdType) {
        this.otherTableIdType = otherTableIdType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}