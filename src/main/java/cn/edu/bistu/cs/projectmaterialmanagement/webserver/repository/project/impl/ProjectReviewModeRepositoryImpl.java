package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectReviewModeRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectReviewModeRepositoryImpl implements IProjectReviewModeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectReviewModeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectReviewMode projectReviewMode) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_review_mode(id,
                                        t_project_id,
                                        t_user_id,
                                        mode,
                                        create_datetime)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                projectReviewMode.getProjectId(),
                                projectReviewMode.getUserId(),
                                projectReviewMode.getMode(), new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectReviewMode projectReviewMode) {
        if (projectReviewMode == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_mode
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectReviewMode.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_mode
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_mode
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
                                           UPDATE t_project_review_mode
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
    public int update(ProjectReviewMode projectReviewMode) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_review_mode
                                           SET t_project_id=?,
                                           t_user_id=?,
                                           mode=?
                                           WHERE id=?
                                           """,
                                   projectReviewMode.getProjectId(),
                                   projectReviewMode.getUserId(),
                                   projectReviewMode.getMode(),
                                   projectReviewMode.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_mode
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectId得到总数量
     */
    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_mode
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
                                                        FROM t_project_review_mode
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectReviewMode getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_mode 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_review_mode
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectReviewModeMapper(), id);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectReviewMode> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_mode
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_mode 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          ORDER BY create_datetime DESC 
                                          """,
                                  new ProjectReviewModeMapper(), projectId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectReviewMode> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_mode
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_mode 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          ORDER BY create_datetime DESC 
                                          """,
                                  new ProjectReviewModeMapper(), userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewMode> getPage(int pageNo,
                                           int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewMode> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectReviewMode> getPageByProjectId(String projectId,
                                                      int pageNo,
                                                      int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewMode> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
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
    public Page<ProjectReviewMode> getPageByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewMode> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectReviewMode> getPageQuery(int pageNo,
                                                 int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_mode
                                          WHERE deleted_at IS NULL
                                          ORDER BY create_datetime DESC
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewModeMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectReviewMode> getPageQueryByProjectId(String projectId,
                                                            int pageNo,
                                                            int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_mode
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          ORDER BY create_datetime DESC 
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewModeMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectReviewMode> getPageQueryByUserId(String userId,
                                                         int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_mode
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          ORDER BY create_datetime DESC 
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewModeMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectReviewModeMapper implements RowMapper<ProjectReviewMode> {
        @Override
        public ProjectReviewMode mapRow(ResultSet rs,
                                        int rowNum) throws SQLException {
            ProjectReviewMode projectReviewMode = new ProjectReviewMode();
            projectReviewMode.setId(rs.getString("id"));
            projectReviewMode.setProjectId(rs.getString("t_project_id"));
            projectReviewMode.setUserId(rs.getString("t_user_id"));
            projectReviewMode.setMode(rs.getInt("mode"));
            projectReviewMode.setDeletedAt(rs.getTimestamp("deleted_at"));
            projectReviewMode.setCreateDatetime(rs.getTimestamp("create_datetime"));
            return projectReviewMode;
        }
    }

}