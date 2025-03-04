package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl.CompanyRepositoryImpl;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectUserRepositoryImpl implements IProjectUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectUser projectUser) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_user(id,
                                        t_user_id,
                                        t_project_id,
                                        t_role_id)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectUser.getUserId(),
                                projectUser.getProjectId(),
                                projectUser.getRoleId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectUser projectUser) {
        if (projectUser == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_project_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectUser.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_user
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * 根据projectId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_user
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
                                   new Date(),
                                   projectId);

    }

    /**
     * 根据roleId删除记录
     */
    @Override
    public int deleteByRoleId(String roleId) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_user
                                           SET deleted_at=? 
                                           WHERE t_role_id=?
                                           """,
                                   new Date(),
                                   roleId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectUser projectUser) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_user
                                           SET t_user_id=?,
                                           t_project_id=?,
                                           t_role_id=?
                                           WHERE id=?
                                           """,
                                   projectUser.getUserId(),
                                   projectUser.getProjectId(),
                                   projectUser.getRoleId(),
                                   projectUser.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
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
                                                        FROM t_project_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectId得到总数量
     */
    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键RoleId得到总数量
     */
    @Override
    public int getCountByRoleId(String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_role_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, roleId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByUserIdAndProjectIdAndRoleId(String userId,
                                                     String projectId,
                                                     String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_user_id=? AND t_project_id=? AND t_role_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, roleId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectUser getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_user
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectUserMapper(), id);
    }

    @Override
    public ProjectUser getByUserIdAndProjectId(String userId,
                                               String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user 
                                                        WHERE t_user_id=? AND t_project_id=? AND deleted_at IS NULL
                                                        """, Integer.class, userId, projectId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_user
                                                    WHERE t_user_id=? AND t_project_id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectUserMapper(), userId, projectId);
    }

    @Override
    public List<ProjectUser> getByRoleIdAndProjectId(String roleId,
                                                     String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_role_id=? AND t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, roleId, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user 
                                          WHERE t_role_id=? AND t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), roleId, projectId);
    }

    @Override
    public List<ProjectUser> getByUserIdIdAndRoleId(String userId,
                                                    String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_user_id=? AND t_role_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId, roleId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user 
                                          WHERE t_user_id=? AND t_role_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), userId, roleId);
    }

    @Override
    public List<Company> getCompanyListByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count( distinct (t_company.id) ) 
                                                        FROM t_company
                                                        LEFT JOIN t_company_user ON t_company.id = t_company_user.t_company_id
                                                        LEFT JOIN t_project_user ON t_project_user.t_user_id=t_company_user.t_user_id
                                                        WHERE t_project_user.t_project_id=? AND t_project_user.deleted_at IS NULL
                                                            AND t_company_user.deleted_at IS NULL
                                                            AND t_company.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT  distinct (t_company.id)  
                                          FROM t_company
                                          LEFT JOIN t_company_user ON t_company.id = t_company_user.t_company_id
                                          LEFT JOIN t_project_user ON t_project_user.t_user_id=t_company_user.t_user_id
                                          WHERE t_project_user.t_project_id=? AND t_project_user.deleted_at IS NULL
                                              AND t_company_user.deleted_at IS NULL
                                              AND t_company.deleted_at IS NULL
                                          """,
                                  new CompanyRepositoryImpl.CompanyMapper(), projectId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectUser> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), userId);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectUser> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), projectId);
    }

    /**
     * 根据roleId得到记录
     */
    @Override
    public List<ProjectUser> getByRoleId(String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        WHERE t_role_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, roleId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user 
                                          WHERE t_role_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), roleId);
    }

    @Override
    public List<ProjectUser> getProjectUserListByProjectIdAndCompanyId(String projectId,
                                                                       String companyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        LEFT JOIN t_company_user on t_project_user.t_user_id=t_company_user.t_user_id
                                                        WHERE
                                                            t_company_user.t_company_id=? AND t_company_user.deleted_at IS NULL
                                                            AND t_project_id= ? AND t_project_user.deleted_at IS NULL
                                                        """,
                                                Integer.class, companyId, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user
                                          LEFT JOIN t_company_user on t_project_user.t_user_id=t_company_user.t_user_id
                                          WHERE
                                              t_company_user.t_company_id=? AND t_company_user.deleted_at IS NULL
                                              AND t_project_id= ? AND t_project_user.deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), companyId, projectId);
    }

    /**
     * 获得指定项目、指定公司、指定角色的所有记录
     *
     * @param projectId
     * @param companyId
     * @param roleId
     * @return
     */
    @Override
    public List<ProjectUser> getProjectUserListByProjectIdAndCompanyIdAndRoleId(String projectId,
                                                                                String companyId,
                                                                                String roleId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_user
                                                        LEFT JOIN t_company_user on t_project_user.t_user_id=t_company_user.t_user_id
                                                        WHERE
                                                            t_company_user.t_company_id=? AND t_company_user.deleted_at IS NULL
                                                            AND t_role_id=? AND t_project_id= ? AND t_project_user.deleted_at IS NULL
                                                        """,
                                                Integer.class, companyId, roleId, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user
                                          LEFT JOIN t_company_user on t_project_user.t_user_id=t_company_user.t_user_id
                                          WHERE
                                              t_company_user.t_company_id=? AND t_company_user.deleted_at IS NULL
                                              AND t_role_id=? AND t_project_id= ? AND t_project_user.deleted_at IS NULL
                                          """,
                                  new ProjectUserMapper(), companyId, roleId, projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectUser> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectUser> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectUser> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectUser> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectUser> getPageByProjectId(String projectId,
                                                int pageNo,
                                                int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectUser> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
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
    public Page<ProjectUser> getPageByRoleId(String roleId,
                                             int pageNo,
                                             int pageSize) {
        long totalCount = getCountByRoleId(roleId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectUser> resultData = getPageQueryByRoleId(roleId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectUser> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectUserMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectUser> getPageQueryByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectUserMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectUser> getPageQueryByProjectId(String projectId,
                                                      int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectUserMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_role_id）+获得指定页面数据
     *
     * @param roleId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectUser> getPageQueryByRoleId(String roleId,
                                                   int pageNo,
                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_user
                                          WHERE t_role_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectUserMapper(), roleId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectUserMapper implements RowMapper<ProjectUser> {
        @Override
        public ProjectUser mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            ProjectUser projectUser = new ProjectUser();
            projectUser.setId(rs.getString("id"));
            projectUser.setUserId(rs.getString("t_user_id"));
            projectUser.setProjectId(rs.getString("t_project_id"));
            projectUser.setRoleId(rs.getString("t_role_id"));
            projectUser.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectUser;
        }
    }

}