package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRole;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.IUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IRoleService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserRoleService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceImpl.class);


    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;

    public UserServiceImpl(IUserRepository userRepository, IRoleService roleService, IUserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    /**
     * 增加
     */
    @Override
    public String add(User user) {

        if (user == null || user.getUserName() == null || user.getRealName() == null || user.getUserName().isEmpty())
            return null;

        //如果存在该用户，则不需要增加
        boolean exist = userRepository.ExistUser(user);
        if (exist != false)
            return null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //bcrypt加密
        String password = encoder.encode(USER_DEFAULT_PWD);
        user.setPassword(password);

        String sysUserId = userRepository.add(user);
        if (sysUserId == null)
            throw new BusinessException("增加用户失败：底层数据库添加失败");

        //添加用户角色
        //	if (user.getUserType().equals(USER_TYPE_TEACHER))
        //		sysUserRoleService.addTeacherRole(sysUserId);
        //	else if (user.getUserType().equals(USER_TYPE_STUDENT))
        //		sysUserRoleService.addStudentRole(sysUserId);
        //	else if (user.getUserType().equals(USER_TYPE_ADMIN))
        //		sysUserRoleService.addAdminRole(sysUserId);


        return sysUserId;
    }

    /**
     * 删除
     */
    @Override
    public int delete(User user) {
        return userRepository.delete(user);
    }

    /**
     * 更新
     */
    @Override
    public int update(User user) {
        return userRepository.update(user);
    }

    /**
     * 更新密码
     *
     * @param user
     * @return
     */
    @Override
    public int updateOwnPwd(User user) {
        if (user == null || user.getId() == null)
            return 0;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        User temp = getById(user.getId());
        if (temp == null)
            throw new BusinessException("参数错误");


        if (!encoder.matches(user.getPassword(), temp.getPassword()))
            throw new BusinessException("旧密码错误");

        String password = encoder.encode(user.getRealName());
        return userRepository.resetPwd(user.getId(), password);

    }

    @Override
    public int updateOwnInfo(User user) {
        return 0;
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public int resetPassword(User user) {
        if (user == null || user.getId() == null)
            return 0;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //bcrypt加密
        String password = encoder.encode(USER_DEFAULT_PWD);


        return userRepository.resetPwd(user.getId(), password);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return userRepository.getCount();
    }

    @Override
    public boolean isExist(String id) {
        return false;
    }

    /**
     * 根据id得到User
     *
     * @param id
     */
    @Override
    public User getById(String id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public User getByRealName(String realName) {
        return userRepository.getByRealName(realName);
    }

    @Override
    public boolean isAdmin(String id) {
        return false;
    }

    @Override
    public boolean isAdmin(User user) {
        return false;
    }

    @Override
    public boolean currentLoginUserIsAdmin() {
        return false;
    }

    @Override
    public User getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("当前无用户登录");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.getByUserName(userDetails.getUsername());
    }

    @Override
    public List<User> getAllAdminUsers() {
        Role role = roleService.getAdmin();
        List<UserRole> userRoleList = userRoleService.getByRoleId(role.getId());
        if (userRoleList == null || userRoleList.isEmpty()) return null;
        List<User> userList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            userList.add(getById(userRole.getUserId()));
        }
        return userList;
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<User> getPage(int pageNo, int pageSize) {
        return userRepository.getPage(pageNo, pageSize);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据用户账号（user_name）查询
        User user = getByUserName(username);


        if (user != null) {

            // 2.根据当前登录的账号查询到关联的权限信息
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
         /*   List<SysPermission> sysPermissionList = getSysPermissionsBySysUser(sysUser);
            if (sysPermissionList != null && sysPermissionList.size() > 0) {
                for (SysPermission sysPermission : sysPermissionList) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(sysPermission.getName()));
                }
            }

          */
            List<Role> sysRoleList = roleService.getRolesByUserId(user.getId());
            for (Role sysRole : sysRoleList) {
                //角色前面一定要加上"ROLE_"前缀，否则Spring Security视为无效
                grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_".concat(sysRole.getName())));
            }


            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorityList);

        }

        throw new UsernameNotFoundException("用户不存在");
    }

}