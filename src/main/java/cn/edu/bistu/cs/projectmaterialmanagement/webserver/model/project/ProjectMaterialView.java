package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMaterialView {
    private ProjectMaterial projectMaterial;//t_project_material
    private Project project;//外键：t_project_id,关联表为：t_project表,
    private Material material;//外键：t_material_count_id,关联表为：t_material_count表,
    private List<ProjectMaterialBrandPrivateView> projectMaterialBrandPrivateViewList;
    private List<ProjectMaterialBrandPublicView> projectMaterialBrandPublicViewList;


    public ProjectMaterial getProjectMaterial() {
        return projectMaterial;
    }

    public void setProjectMaterial(ProjectMaterial projectMaterial) {
        this.projectMaterial = projectMaterial;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<ProjectMaterialBrandPrivateView> getProjectMaterialBrandPrivateViewList() {
        return projectMaterialBrandPrivateViewList;
    }

    public void setProjectMaterialBrandPrivateViewList(List<ProjectMaterialBrandPrivateView> projectMaterialBrandPrivateViewList) {
        this.projectMaterialBrandPrivateViewList = projectMaterialBrandPrivateViewList;
    }

    public List<ProjectMaterialBrandPublicView> getProjectMaterialBrandPublicViewList() {
        return projectMaterialBrandPublicViewList;
    }

    public void setProjectMaterialBrandPublicViewList(List<ProjectMaterialBrandPublicView> projectMaterialBrandPublicViewList) {
        this.projectMaterialBrandPublicViewList = projectMaterialBrandPublicViewList;
    }
}