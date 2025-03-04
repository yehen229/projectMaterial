package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Brand {
    private String id;//id,主键
    private String name;//name
    private String materialClassifySectionId;//t_material_classify_section_id
    private String position;//position,定位：合资、国产等定位：合资、国产等
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


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

    public String getMaterialClassifySectionId() {
        return materialClassifySectionId;
    }

    public void setMaterialClassifySectionId(String materialClassifySectionId) {
        this.materialClassifySectionId = materialClassifySectionId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}