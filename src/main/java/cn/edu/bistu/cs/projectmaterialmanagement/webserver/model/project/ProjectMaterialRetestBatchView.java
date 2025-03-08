package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMaterialRetestBatchView{
	private ProjectMaterialRetestBatch projectMaterialRetestBatch;//t_project_material_retest_batch
	private User user;//外键：t_user_id,关联表为：t_user表,
	private Project project;//外键：t_project_id,关联表为：t_project表,
	private BuyMaterialBatch buyMaterialBatch;//外键：t_buy_material_batch_id,关联表为：t_buy_material_batch表,
	private List<ProjectMaterialRetestBatchFile> projectMaterialRetestFileList;
	private List<ProjectMaterialRetestView> projectMaterialRetestViewList;

	public ProjectMaterialRetestBatch getProjectMaterialRetestBatch(){
		return projectMaterialRetestBatch;
	}

	public void setProjectMaterialRetestBatch(ProjectMaterialRetestBatch projectMaterialRetestBatch){
		this.projectMaterialRetestBatch=projectMaterialRetestBatch;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user=user;
	}
	public Project getProject(){
		return project;
	}
	public void setProject(Project project){
		this.project=project;
	}
	public BuyMaterialBatch getBuyMaterialBatch(){
		return buyMaterialBatch;
	}
	public void setBuyMaterialBatch(BuyMaterialBatch buyMaterialBatch){
		this.buyMaterialBatch=buyMaterialBatch;
	}

    public List<ProjectMaterialRetestView> getProjectMaterialRetestViewList() {
        return projectMaterialRetestViewList;
    }

    public void setProjectMaterialRetestViewList(List<ProjectMaterialRetestView> projectMaterialRetestViewList) {
        this.projectMaterialRetestViewList = projectMaterialRetestViewList;
    }

    public List<ProjectMaterialRetestBatchFile> getProjectMaterialRetestFileList() {
        return projectMaterialRetestFileList;
    }

    public void setProjectMaterialRetestFileList(List<ProjectMaterialRetestBatchFile> projectMaterialRetestFileList) {
        this.projectMaterialRetestFileList = projectMaterialRetestFileList;
    }
}