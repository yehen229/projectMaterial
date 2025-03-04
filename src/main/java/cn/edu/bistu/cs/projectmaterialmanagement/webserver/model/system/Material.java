package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Material {
    private String id;//id,主键
    private String materialClassifySectionId;//t_material_classify_section_id
    private String name;//name,名称名称
    private String itemMark;//item_mark,编号编号
    private String location;//location,位置位置
    private String technology;//technology,技术要求技术要求
    private String material;//material,材料材质材料材质
    private String color;//color,颜色颜色
    private String dimension;//dimension,规格规格
    private String fireRating;//fire_rating,防火等级防火等级
    private String installation;//installation,施工要求施工要求
    private int materialProjectBindType;//material_project_bind_type,类型：0：原始材料；1：和项目绑定的、基于原始材料修改的
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间

    public String getMaterialClassifySectionId() {
        return materialClassifySectionId;
    }

    public void setMaterialClassifySectionId(String materialClassifySectionId) {
        this.materialClassifySectionId = materialClassifySectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemMark() {
        return itemMark;
    }

    public void setItemMark(String itemMark) {
        this.itemMark = itemMark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getFireRating() {
        return fireRating;
    }

    public void setFireRating(String fireRating) {
        this.fireRating = fireRating;
    }

    public String getInstallation() {
        return installation;
    }

    public void setInstallation(String installation) {
        this.installation = installation;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getMaterialProjectBindType() {
        return materialProjectBindType;
    }

    public void setMaterialProjectBindType(int materialProjectBindType) {
        this.materialProjectBindType = materialProjectBindType;
    }
}