package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;

import java.util.List;

/** User Service Interface 
*/
public interface IUserRepository{

	String add(User user);
	int delete(User user);
	int delete_company_user(User user);
	int update(User user);
	int deleteById(String id);

	int updateOwnInfo(User user);
	int isCorrectPwd(String id,
					 String pwd);
	int resetPwd(String id,
				 String pwd);

	int getCount();
	int getCountLikeRealName(String realName);
	int getCountLikeTel(String tel);
	int getCountLikeEmail(String email);
	User getById(String id);
	User getByUserName(String userName);
	User getByRealName(String realName);
	User getByTel(String tel);
	User getByEmail(String email);

	Page<User> getPageLikeRealName(String realName,int pageNo, int pageSize);
	Page<User> getPageLikeTel(String tel,int pageNo, int pageSize);
	Page<User> getPageLikeEmail(String email,int pageNo, int pageSize);


	Page<User> getPage(int pageNo, int pageSize);
	boolean ExistUser(User user);
	boolean ExistUserName(User user);
	boolean isAdmin(String id);
}