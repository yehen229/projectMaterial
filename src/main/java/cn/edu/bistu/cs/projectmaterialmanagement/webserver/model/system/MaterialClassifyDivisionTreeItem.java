package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialClassifyDivisionTreeItem {
    private MaterialClassifyDivision materialClassifyDivision;
    private List<MaterialClassifyGroupTreeItem> children;

    public MaterialClassifyDivision getMaterialClassifyDivision() {
        return materialClassifyDivision;
    }

    public void setMaterialClassifyDivision(MaterialClassifyDivision materialClassifyDivision) {
        this.materialClassifyDivision = materialClassifyDivision;
    }

    public List<MaterialClassifyGroupTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<MaterialClassifyGroupTreeItem> children) {
        this.children = children;
    }
}
