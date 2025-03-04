package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialClassifyTree {
    private List<MaterialClassifyDivisionTreeItem> children;

    public List<MaterialClassifyDivisionTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<MaterialClassifyDivisionTreeItem> children) {
        this.children = children;
    }
}
