package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class MaterialClassifyGroupView {
    private MaterialClassifyGroup materialClassifyGroup;//t_material_classify_group
    private MaterialClassifyDivision materialClassifyDivision;//外键：t_material_classify_division_id,关联表为：t_material_classify_division表,


    public MaterialClassifyGroup getMaterialClassifyGroup() {
        return materialClassifyGroup;
    }

    public void setMaterialClassifyGroup(MaterialClassifyGroup materialClassifyGroup) {
        this.materialClassifyGroup = materialClassifyGroup;
    }

    public MaterialClassifyDivision getMaterialClassifyDivision() {
        return materialClassifyDivision;
    }

    public void setMaterialClassifyDivision(MaterialClassifyDivision materialClassifyDivision) {
        this.materialClassifyDivision = materialClassifyDivision;
    }
}