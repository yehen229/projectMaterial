package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class MaterialClassifySectionView {
    private MaterialClassifySection materialClassifySection;//t_material_classify_section
    private MaterialClassifyGroup materialClassifyGroup;//外键：t_material_classify_group_id,关联表为：t_material_classify_group表,
    private MaterialClassifyDivision materialClassifyDivision;//外键：t_material_classify_division_id,关联表为：t_material_classify_division表,

    public MaterialClassifySection getMaterialClassifySection() {
        return materialClassifySection;
    }

    public void setMaterialClassifySection(MaterialClassifySection materialClassifySection) {
        this.materialClassifySection = materialClassifySection;
    }

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