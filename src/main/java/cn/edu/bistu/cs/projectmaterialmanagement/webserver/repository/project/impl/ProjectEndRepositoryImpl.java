package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectEndRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectEndRepositoryImpl implements IProjectEndRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectEndRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectEnd projectEnd) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_end(id,
                                        t_project_id,
                                        t_user_id,
                                        create_datetime)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectEnd.getProjectId(),
                                projectEnd.getUserId(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectEnd projectEnd) {
        if (projectEnd == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_end
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectEnd.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projecctId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
                                   new Date(),
                                   projectId);

    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectEnd projectEnd) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end
                                           SET t_project_id=?,
                                           t_user_id=?
                                           WHERE id=?
                                           """,
                                   projectEnd.getProjectId(),
                                   projectEnd.getUserId(),
                                   projectEnd.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjecctId得到总数量
     */
    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectEnd getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_end
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectEndMapper(), id);
    }

    /**
     * 根据projecctId得到记录
     */
    @Override
    public List<ProjectEnd> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectEndMapper(), projectId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectEnd> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectEndMapper(), userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEnd> getPage(int pageNo,
                                    int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEnd> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projecctId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<ProjectEnd> getPageByProjectId(String projecctId,
                                               int pageNo,
                                               int pageSize) {
        long totalCount = getCountByProjectId(projecctId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEnd> resultData = getPageQueryByProjectId(projecctId, pageNo - 1, pageSize);
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
    public Page<ProjectEnd> getPageByUserId(String userId,
                                            int pageNo,
                                            int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEnd> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectEnd> getPageQuery(int pageNo,
                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectEndMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_projecct_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectEnd> getPageQueryByProjectId(String projectId,
                                                     int pageNo,
                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectEndMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectEnd> getPageQueryByUserId(String userId,
                                                  int pageNo,
                                                  int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectEndMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectEndMapper implements RowMapper<ProjectEnd> {
        @Override
        public ProjectEnd mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
            ProjectEnd projectEnd = new ProjectEnd();
            projectEnd.setId(rs.getString("id"));
            projectEnd.setProjectId(rs.getString("t_project_id"));
            projectEnd.setUserId(rs.getString("t_user_id"));
            projectEnd.setCreateDatetime(rs.getTimestamp("create_datetime"));
            projectEnd.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectEnd;
        }
    }

}