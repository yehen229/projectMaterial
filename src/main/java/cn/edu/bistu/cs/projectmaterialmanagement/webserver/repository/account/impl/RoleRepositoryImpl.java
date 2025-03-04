package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.Role;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.IRoleRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(Role role) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_role(id,
                                        name,
                                        note)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                role.getName(),
                                role.getNote()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(Role role) {
        if (role == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_role
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   role.getId());
    }

    /**
     * update
     */
    @Override
    public int update(Role role) {
        return jdbcTemplate.update("""
                                           UPDATE t_role
                                           SET name=?,
                                           note=?
                                           WHERE id=?
                                           """,
                                   role.getName(),
                                   role.getNote(),
                                   role.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_role
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_role
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public Role getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_role 
                                                        WHERE id=?
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_role
                                                   WHERE id=?
                                                   """,
                                           new RoleMapper(), id);
    }

    @Override
    public Role getByName(String roleName) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_role 
                                                        WHERE name=?
                                                        """, Integer.class, roleName);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_role
                                                   WHERE name=?
                                                   """,
                                           new RoleMapper(), roleName);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Role> getPage(int pageNo,
                              int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Role> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<Role> getRolesByUserId(String sysUserId) {
        return jdbcTemplate.query("""
                                          SELECT t_role.* 
                                          FROM t_role
                                          LEFT JOIN t_user_role ON t_role.id=t_user_role.t_role_id
                                          WHERE t_user_id=?
                                          """,
                                  new Object[]{sysUserId},
                                  new int[]{Types.CHAR},
                                  new RoleMapper());
    }

    @Override
    public List<Role> getAllRoles() {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_role			
                                          """,
                                  new RoleMapper());
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<Role> getPageQuery(int pageNo,
                                    int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_role
                                          LIMIT ?,?
                                          """,
                                  new RoleMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class RoleMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet rs,
                           int rowNum) throws SQLException {
            Role role = new Role();
            role.setId(rs.getString("id"));
            role.setName(rs.getString("name"));
            role.setNote(rs.getString("note"));
            role.setDeletedAt(rs.getTimestamp("deleted_at"));
            return role;
        }
    }

}