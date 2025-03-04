package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialClassifyGroupTreeItem {
    private MaterialClassifyGroupView materialClassifyGroupView;
    private List<MaterialClassifySectionView> children;

    public MaterialClassifyGroupView getMaterialClassifyGroupView() {
        return materialClassifyGroupView;
    }

    public void setMaterialClassifyGroupView(MaterialClassifyGroupView materialClassifyGroupView) {
        this.materialClassifyGroupView = materialClassifyGroupView;
    }

    public List<MaterialClassifySectionView> getChildren() {
        return children;
    }

    public void setChildren(List<MaterialClassifySectionView> children) {
        this.children = children;
    }
}
