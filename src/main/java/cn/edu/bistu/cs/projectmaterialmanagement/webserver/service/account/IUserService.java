package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {
    String USER_DEFAULT_PWD = "123456";//用户默认密码

    String add(User user);

    int delete(User user);

    int update(User user);

    int updateOwnPwd(User user);

    int updateOwnInfo(User user);

    int deleteById(String id);

    int resetPassword(User user);

    int getCount();

    boolean isExist(String id);

    User getById(String id);

    User getByUserName(String userName);

    User getByRealName(String realName);

    boolean isAdmin(String id);

    boolean isAdmin(User user);

    boolean currentLoginUserIsAdmin();

    User getCurrentLoginUser();


    List<User> getAllAdminUsers();

    Page<User> getPage(int pageNo, int pageSize);

    boolean ExistUser(User user);
    boolean ExistUserName(User user);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}