package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.IRoleRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    private static final Logger log =
            LoggerFactory.getLogger(RoleServiceImpl.class);

    private final IRoleRepository roleRepository;

    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(Role role) {
        return roleRepository.add(role);
    }

    /**
     * 删除
     */
    @Override
    public int delete(Role role) {
        return roleRepository.delete(role);
    }

    /**
     * 更新
     */
    @Override
    public int update(Role role) {
        return roleRepository.update(role);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return roleRepository.deleteById(id);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return roleRepository.getCount();
    }

    /**
     * 根据id得到Role
     *
     * @param id
     */
    @Override
    public Role getById(String id) {
        return roleRepository.getById(id);
    }

    @Override
    public Role getManager() {
        return roleRepository.getByName(ROLE_MANAGER);
    }

    @Override
    public Role getEmployee() {
        return roleRepository.getByName(ROLE_EMPLOYEE);
    }

    @Override
    public Role getAdmin() {
        return roleRepository.getByName(ROLE_ADMIN);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Role> getPage(int pageNo, int pageSize) {
        return roleRepository.getPage(pageNo, pageSize);
    }

    @Override
    public List<Role> getRolesByUserId(String userId) {
        return roleRepository.getRolesByUserId(userId);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

}