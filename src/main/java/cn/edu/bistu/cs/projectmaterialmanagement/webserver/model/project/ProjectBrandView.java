package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandView;
import org.springframework.stereotype.Component;

@Component
public class ProjectBrandView {
    private ProjectBrand projectBrand;//t_project_brand
    private Project project;//外键：t_project_id,关联表为：t_project表,
    private BrandView brandView;//外键：t_brand_id,关联表为：t_brand表,


    public ProjectBrand getProjectBrand() {
        return projectBrand;
    }

    public void setProjectBrand(ProjectBrand projectBrand) {
        this.projectBrand = projectBrand;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BrandView getBrandView() {
        return brandView;
    }

    public void setBrandView(BrandView brandView) {
        this.brandView = brandView;
    }
}