package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialRetestBatchFile {
    private String id;//id,主键
    private String projectMaterialRetestBatchId;//t_project_material_retest_batch_id
    private String filePath;//file_path
    private Date deletedAt;//deleted_at


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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getProjectMaterialRetestBatchId() {
        return projectMaterialRetestBatchId;
    }

    public void setProjectMaterialRetestBatchId(String projectMaterialRetestBatchId) {
        this.projectMaterialRetestBatchId = projectMaterialRetestBatchId;
    }
}