package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MaterialClassifySection {
    private String id;//id,主键
    private String name;//name
    private String note;//note
    private String materialClassifyGroupId;//t_material_classify_group_id,外键,	t_material_classify_group_id<-表t_material_classify_group.id
    private Date deletedAt;//deleted_at


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMaterialClassifyGroupId() {
        return materialClassifyGroupId;
    }

    public void setMaterialClassifyGroupId(String materialClassifyGroupId) {
        this.materialClassifyGroupId = materialClassifyGroupId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}