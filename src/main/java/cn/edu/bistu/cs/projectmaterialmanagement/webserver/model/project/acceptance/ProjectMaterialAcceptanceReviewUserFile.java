package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialAcceptanceReviewUserFile {
    private String id;//id,主键
    private String filePath;//file_path
    private String projectMaterialAcceptanceReviewUserId;//t_project_material_acceptance_review_user_id,外键,	t_project_material_acceptance_review_user_id<-表t_project_material_acceptance_review_user.id
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

    public String getProjectMaterialAcceptanceReviewUserId() {
        return projectMaterialAcceptanceReviewUserId;
    }

    public void setProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        this.projectMaterialAcceptanceReviewUserId = projectMaterialAcceptanceReviewUserId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}