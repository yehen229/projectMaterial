package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;

import java.math.BigDecimal;

public class MaterialInfo {
    private Material materialInfo;
    private BigDecimal materialCount;
    private String materialUnit;
    private int batch;

    @Override
    public String toString() {
        return "MaterialInfo{" +
                "materialInfo=" + materialInfo +
                ", materialCount=" + materialCount +
                ", materialUnit='" + materialUnit + '\'' +
                ", batch=" + batch +
                '}';
    }

    public Material getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(Material materialInfo) {
        this.materialInfo = materialInfo;
    }

    public BigDecimal getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(BigDecimal materialCount) {
        this.materialCount = materialCount;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }
}
