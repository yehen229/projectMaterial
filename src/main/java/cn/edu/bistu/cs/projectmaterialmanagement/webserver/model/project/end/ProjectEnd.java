package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectEnd {
    private String id;//id,主键
    private String projectId;//t_projecct_id,外键,	t_projecct_id<-表t_project.id
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
    private Date createDatetime;//create_datetime
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}