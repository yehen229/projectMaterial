package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialRetestFile {
    private String id;//id,主键
    private String projectMaterialRetestId;//t_project_material_retest_id
    private String filePath;//file_path
    private Date deletedAt;//deleted_at


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectMaterialRetestId() {
        return projectMaterialRetestId;
    }

    public void setProjectMaterialRetestId(String projectMaterialRetestId) {
        this.projectMaterialRetestId = projectMaterialRetestId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}