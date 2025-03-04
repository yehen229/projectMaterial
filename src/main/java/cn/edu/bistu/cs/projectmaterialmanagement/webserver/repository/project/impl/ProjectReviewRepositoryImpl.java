package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectReviewRepositoryImpl implements IProjectReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectReviewRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectReview projectReview) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_review(id,
                                        t_project_id,
                                        t_project_review_mode_id,
                                        review_result,
                                        review_status,
                                        review_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectReview.getProjectId(),
                                projectReview.getProjectReviewModeId(),
                                projectReview.getReviewResult(),
                                projectReview.getReviewStatus(),
                                projectReview.getReviewDatetime()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectReview projectReview) {
        if (projectReview == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_project_review
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectReview.getId());


    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review
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
                                           UPDATE t_project_review
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
                                   new Date(),
                                   projectId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectReview projectReview) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_review
                                           SET t_project_id=?,
                                           t_project_review_mode_id=?,
                                           review_result=?,
                                           review_status=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectReview.getProjectId(),
                                   projectReview.getProjectReviewModeId(),
                                   projectReview.getReviewResult(),
                                   projectReview.getReviewStatus(),
                                   projectReview.getReviewDatetime(),
                                   projectReview.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review
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
                                                        FROM t_project_review
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectReview getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_review
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectReviewMapper(), id);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectReview> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectReviewMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReview> getPage(int pageNo,
                                       int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReview> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectReview> getPageByProjectId(String projectId,
                                                  int pageNo,
                                                  int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReview> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectReview> getPageQuery(int pageNo,
                                             int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectReview> getPageQueryByProjectId(String projectId,
                                                        int pageNo,
                                                        int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectReviewMapper implements RowMapper<ProjectReview> {
        @Override
        public ProjectReview mapRow(ResultSet rs,
                                    int rowNum) throws SQLException {
            ProjectReview projectReview = new ProjectReview();
            projectReview.setId(rs.getString("id"));
            projectReview.setProjectId(rs.getString("t_project_id"));
            projectReview.setProjectReviewModeId(rs.getString("t_project_review_mode_id"));
            projectReview.setReviewResult(rs.getInt("review_result"));
            projectReview.setReviewStatus(rs.getInt("review_status"));
            projectReview.setReviewDatetime(rs.getTimestamp("review_datetime"));
            projectReview.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectReview;
        }
    }

}