package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialAcceptanceReviewRepositoryImpl implements IProjectMaterialAcceptanceReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialAcceptanceReviewRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_acceptance_review(id,
                                        t_project_material_acceptance_mode_id,
                                        review_status,
                                        review_result,
                                        review_datetime)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                projectMaterialAcceptanceReview.getProjectMaterialAcceptanceModeId(),
                                projectMaterialAcceptanceReview.getReviewStatus(),
                                projectMaterialAcceptanceReview.getReviewResult(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        if (projectMaterialAcceptanceReview == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceReview.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review
                                           SET t_project_material_acceptance_mode_id=?,
                                           review_status=?,
                                           review_result=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptanceReview.getProjectMaterialAcceptanceModeId(),
                                   projectMaterialAcceptanceReview.getReviewStatus(),
                                   projectMaterialAcceptanceReview.getReviewResult(),
                                   projectMaterialAcceptanceReview.getReviewDatetime(),
                                   projectMaterialAcceptanceReview.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    /**
     * 根据projectMaterialModeId删除记录
     */
    @Override
    public int deleteByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review
                                           SET deleted_at=? 
                                           WHERE t_project_material_acceptance_mode_id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceModeId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialModeId得到总数量
     */
    @Override
    public int getCountByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review
                                                        WHERE t_project_material_acceptance_mode_id=?
                                                        AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceModeId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialAcceptanceReview getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance_review
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialAcceptanceReviewMapper(), id);
    }

    /**
     * 根据projectMaterialModeId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceReview> getByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review
                                                        WHERE t_project_material_acceptance_mode_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceModeId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review 
                                          WHERE t_project_material_acceptance_mode_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewMapper(), projectMaterialAcceptanceModeId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReview> getPage(int pageNo,
                                                         int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReview> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceModeId
     * @param pageNo                          页号，从1开始
     * @param pageSize                        每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReview> getPageByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        long totalCount = getCountByProjectMaterialAcceptanceModeId(projectMaterialAcceptanceModeId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReview> resultData = getPageQueryByProjectMaterialAcceptanceModeId(
                projectMaterialAcceptanceModeId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialAcceptanceReview> getPageQuery(int pageNo,
                                                               int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_mode_id）+获得指定页面数据
     *
     * @param projectMaterialAcceptanceModeId
     * @param pageNo                          页号，从1开始
     * @param pageSize                        每页的记录数
     */
    private List<ProjectMaterialAcceptanceReview> getPageQueryByProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId,
                                                                                                int pageNo,
                                                                                                int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review
                                          WHERE t_project_material_acceptance_mode_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewMapper(), projectMaterialAcceptanceModeId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialAcceptanceReviewMapper implements RowMapper<ProjectMaterialAcceptanceReview> {
        @Override
        public ProjectMaterialAcceptanceReview mapRow(ResultSet rs,
                                                      int rowNum) throws SQLException {
            ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = new ProjectMaterialAcceptanceReview();
            projectMaterialAcceptanceReview.setId(rs.getString("id"));
            projectMaterialAcceptanceReview.setProjectMaterialAcceptanceModeId(
                    rs.getString("t_project_material_acceptance_mode_id"));
            projectMaterialAcceptanceReview.setReviewStatus(rs.getInt("review_status"));
            projectMaterialAcceptanceReview.setReviewResult(rs.getInt("review_result"));
            projectMaterialAcceptanceReview.setReviewDatetime(rs.getDate("review_datetime"));
            projectMaterialAcceptanceReview.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialAcceptanceReview;
        }
    }

}