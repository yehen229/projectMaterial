package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRole;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.IUserRoleRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UserRoleRepositoryImpl implements IUserRoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(UserRole userRole) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_user_role(id,
                                        t_user_id,
                                        t_role_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                userRole.getUserId(),
                                userRole.getRoleId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(UserRole userRole) {
        if (userRole == null) return 0;
        return jdbcTemplate.update("""
                                           DELETE FROM t_user_role 
                                           WHERE id=?
                                           """,
                                   userRole.getId());
    }

    /**
     * update
     */
    @Override
    public int update(UserRole userRole) {
        return jdbcTemplate.update("""
                                           UPDATE t_user_role
                                           SET t_user_id=?,
                                           t_role_id=?
                                           WHERE id=?
                                           """,
                                   userRole.getUserId(),
                                   userRole.getRoleId(),
                                   userRole.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_user_role
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * 根据userId删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteByUserId(String userId) {

        return jdbcTemplate.update("""
                                           UPDATE t_user_role
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);


    }

    /**
     * 根据roleId删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteByRoleId(String roleId) {

        return jdbcTemplate.update("""
                                           UPDATE t_user_role
                                           SET deleted_at=? 
                                           WHERE t_role_id=?
                                           """,
                                   new Date(),
                                   roleId);


    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user_role
                                                        WHERE deleted_at IS NULL 
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user_role
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键RoleId得到总数量
     */
    @Override
    public int getCountByRoleId(String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user_role
                                                        WHERE t_role_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, roleId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public UserRole getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user_role 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_user_role
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new UserRoleMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<UserRole> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user_role
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user_role 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new UserRoleMapper(), userId);
    }

    /**
     * 根据roleId得到记录
     */
    @Override
    public List<UserRole> getByRoleId(String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user_role
                                                        WHERE t_role_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, roleId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user_role 
                                          WHERE t_role_id=? AND deleted_at IS NULL
                                          """,
                                  new UserRoleMapper(), roleId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UserRole> getPage(int pageNo,
                                  int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UserRole> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UserRole> getPageByUserId(String userId,
                                          int pageNo,
                                          int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UserRole> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param roleId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UserRole> getPageByRoleId(String roleId,
                                          int pageNo,
                                          int pageSize) {
        long totalCount = getCountByRoleId(roleId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UserRole> resultData = getPageQueryByRoleId(roleId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<UserRole> getPageQuery(int pageNo,
                                        int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user_role
                                          WHERE  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UserRoleMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<UserRole> getPageQueryByUserId(String userId,
                                                int pageNo,
                                                int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user_role
                                          WHERE t_user_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UserRoleMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_role_id）+获得指定页面数据
     *
     * @param roleId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<UserRole> getPageQueryByRoleId(String roleId,
                                                int pageNo,
                                                int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user_role
                                          WHERE t_role_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UserRoleMapper(), roleId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class UserRoleMapper implements RowMapper<UserRole> {
        @Override
        public UserRole mapRow(ResultSet rs,
                               int rowNum) throws SQLException {
            UserRole userRole = new UserRole();
            userRole.setId(rs.getString("id"));
            userRole.setUserId(rs.getString("t_user_id"));
            userRole.setRoleId(rs.getString("t_role_id"));
            userRole.setDeletedAt(rs.getTimestamp("deleted_at"));
            return userRole;
        }
    }

}