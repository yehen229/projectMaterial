package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialHistoryView{
	private ProjectMaterialHistory projectMaterialHistory;//t_project_material_history
	private Material material;//外键：t_material_id,关联表为：t_material表,材料
	private Company company;//外键：t_company_id,关联表为：t_company表,提交的公司
	private Project project;//外键：t_project_id,关联表为：t_project表,项目
	private ProjectMaterial projectMaterial;//外键：t_project_material_id,关联表为：t_project_material表,

	public ProjectMaterialHistory getProjectMaterialHistory(){
		return projectMaterialHistory;
	}
	public void setProjectMaterialHistory(ProjectMaterialHistory projectMaterialHistory){
		this.projectMaterialHistory=projectMaterialHistory;
	}
	public Material getMaterial(){
		return material;
	}
	public void setMaterial(Material material){
		this.material=material;
	}
	public Company getCompany(){
		return company;
	}
	public void setCompany(Company company){
		this.company=company;
	}
	public Project getProject(){
		return project;
	}
	public void setProject(Project project){
		this.project=project;
	}
	public ProjectMaterial getProjectMaterial(){
		return projectMaterial;
	}
	public void setProjectMaterial(ProjectMaterial projectMaterial){
		this.projectMaterial=projectMaterial;
	}
}