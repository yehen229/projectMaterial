package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import java.util.List;

/**
 * Role Service Interface
 */
public interface IRoleRepository {

    String add(Role role);

    int delete(Role role);

    int update(Role role);

    int deleteById(String id);

    int getCount();

    Role getById(String id);

    Role getByName(String roleName);

    Page<Role> getPage(int pageNo, int pageSize);

    List<Role> getRolesByUserId(String sysUserId);

    List<Role> getAllRoles();


}