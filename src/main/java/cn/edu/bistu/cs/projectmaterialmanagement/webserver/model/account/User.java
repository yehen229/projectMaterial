package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class User {
	private String id;//id,主键
	private String userName;//login_id,登录id登录id
	private String realName;//name,姓名姓名
	private String password;//password,密码密码
	private String tel;//tel,电话电话
	private String email;//email,邮箱邮箱
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel=tel;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}