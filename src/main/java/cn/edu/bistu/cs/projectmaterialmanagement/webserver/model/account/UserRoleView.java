package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account;

import org.springframework.stereotype.Component;

@Component
public class UserRoleView{
	private UserRole userRole;//t_user_role
	private User user;//外键：t_user_id,关联表为：t_user表,
	private Role role;//外键：t_role_id,关联表为：t_role表,


	public UserRole getUserRole(){
		return userRole;
	}
	public void setUserRole(UserRole userRole){
		this.userRole=userRole;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user=user;
	}
	public Role getRole(){
		return role;
	}
	public void setRole(Role role){
		this.role=role;
	}
}