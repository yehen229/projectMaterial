package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import org.springframework.stereotype.Component;

@Component
public class ProjectCompanyView{
	private ProjectCompany projectCompany;//t_project_company
	private User user;//外键：t_user_id,关联表为：t_user表,用户，工程部员工
	private Company totalPackageCompany;//外键：t_total_package_company_id,关联表为：t_company表,总包单位
	private Company supervisionCompany;//外键：t_supervision_company_id,关联表为：t_company表,监理单位


	public ProjectCompany getProjectCompany(){
		return projectCompany;
	}
	public void setProjectCompany(ProjectCompany projectCompany){
		this.projectCompany=projectCompany;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user=user;
	}
	public Company getTotalPackageCompany(){
		return totalPackageCompany;
	}
	public void setTotalPackageCompany(Company totalPackageCompany){
		this.totalPackageCompany=totalPackageCompany;
	}
	public Company getSupervisionCompany(){
		return supervisionCompany;
	}
	public void setSupervisionCompany(Company supervisionCompany){
		this.supervisionCompany=supervisionCompany;
	}
}