package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyDivision;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyGroup;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseMaterialNewBrandView {
    private UseMaterialNewBrand useMaterialNewBrand;//t_use_material_new_brand
    private UseMaterial useMaterial;//外键：t_use_material_id,关联表为：t_use_material表,
    private MaterialClassifyDivision materialClassifyDivision;//外键：t_material_classify_division_id,关联表为：t_material_classify_division表,大类专业
    private MaterialClassifyGroup materialClassifyGroup;//外键：t_material_classify_group_id,关联表为：t_material_classify_group表,中类材料分类
    private MaterialClassifySection materialClassifySection;//外键：t_material_classify_section_id,关联表为：t_material_classify_section表,小类材料名称
    private ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView;
    private List<UseMaterialNewBrandFileView> useMaterialNewBrandFileViewList;

    public UseMaterialNewBrand getUseMaterialNewBrand() {
        return useMaterialNewBrand;
    }

    public void setUseMaterialNewBrand(UseMaterialNewBrand useMaterialNewBrand) {
        this.useMaterialNewBrand = useMaterialNewBrand;
    }

    public UseMaterial getUseMaterial() {
        return useMaterial;
    }

    public void setUseMaterial(UseMaterial useMaterial) {
        this.useMaterial = useMaterial;
    }

    public MaterialClassifyDivision getMaterialClassifyDivision() {
        return materialClassifyDivision;
    }

    public void setMaterialClassifyDivision(MaterialClassifyDivision materialClassifyDivision) {
        this.materialClassifyDivision = materialClassifyDivision;
    }

    public MaterialClassifyGroup getMaterialClassifyGroup() {
        return materialClassifyGroup;
    }

    public void setMaterialClassifyGroup(MaterialClassifyGroup materialClassifyGroup) {
        this.materialClassifyGroup = materialClassifyGroup;
    }

    public MaterialClassifySection getMaterialClassifySection() {
        return materialClassifySection;
    }

    public void setMaterialClassifySection(MaterialClassifySection materialClassifySection) {
        this.materialClassifySection = materialClassifySection;
    }

    public List<UseMaterialNewBrandFileView> getUseMaterialNewBrandFileViewList() {
        return useMaterialNewBrandFileViewList;
    }

    public void setUseMaterialNewBrandFileViewList(List<UseMaterialNewBrandFileView> useMaterialNewBrandFileViewList) {
        this.useMaterialNewBrandFileViewList = useMaterialNewBrandFileViewList;
    }

    public ProjectMaterialBrandPrivateView getProjectMaterialBrandPrivateView() {
        return projectMaterialBrandPrivateView;
    }

    public void setProjectMaterialBrandPrivateView(ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView) {
        this.projectMaterialBrandPrivateView = projectMaterialBrandPrivateView;
    }
}