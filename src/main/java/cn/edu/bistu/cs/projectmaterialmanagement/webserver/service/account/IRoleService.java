package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import java.util.List;

public interface IRoleService {
    String ROLE_ADMIN = "Admin";
    String ROLE_MANAGER = "Manager";
    String ROLE_EMPLOYEE = "Employee";

    String add(Role role);

    int delete(Role role);

    int update(Role role);

    int deleteById(String id);

    int getCount();

    Role getById(String id);

    Role getManager();

    Role getEmployee();

    Role getAdmin();

    Page<Role> getPage(int pageNo, int pageSize);

    List<Role> getRolesByUserId(String userId);

    List<Role> getAllRoles();
}