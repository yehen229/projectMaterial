package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialVerificationDocument {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
    private String buyMaterialId;//t_buy_material_id,外键,	t_project_material_id<-表t_project_material.id
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


    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getBuyMaterialId() {
        return buyMaterialId;
    }

    public void setBuyMaterialId(String buyMaterialId) {
        this.buyMaterialId = buyMaterialId;
    }
}