package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

@Component
public class BuyMaterialBatchView{
	private BuyMaterialBatch buyMaterialBatch;//t_buy_material_batch
	private User user;//外键：t_user_id,关联表为：t_user表,用户
	private Project project;//外键：t_project,关联表为：t_project表,项目


	public BuyMaterialBatch getBuyMaterialBatch(){
		return buyMaterialBatch;
	}
	public void setBuyMaterialBatch(BuyMaterialBatch buyMaterialBatch){
		this.buyMaterialBatch=buyMaterialBatch;
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
}