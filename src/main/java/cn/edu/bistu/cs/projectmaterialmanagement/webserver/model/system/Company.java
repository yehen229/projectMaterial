package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Company{
	private String id;//id,主键
	private String name;//name,单位名称单位名称
	private String companyType;//company_type,单位类型：品牌厂家、设计单位、设计部、工程部、监理单位、总包单位等单位类型：设计单位、设计部、工程部、监理单位、总包单位等
	private String note;//note,备注备注
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getCompanyType(){
		return companyType;
	}
	public void setCompanyType(String companyType){
		this.companyType=companyType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}