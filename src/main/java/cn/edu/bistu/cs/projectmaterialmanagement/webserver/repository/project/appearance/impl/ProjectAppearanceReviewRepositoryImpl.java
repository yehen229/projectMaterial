package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectAppearanceReviewRepositoryImpl implements IProjectAppearanceReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectAppearanceReviewRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectAppearanceReview projectAppearanceReview) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_appearance_review(id,
                                        t_project_appearance_review_mode_id,
                                        t_use_material_id,
                                        review_status,
                                        review_result,
                                        review_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectAppearanceReview.getProjectAppearanceReviewModeId(),
                                projectAppearanceReview.getUseMaterialId(),
                                projectAppearanceReview.getReviewStatus(),
                                projectAppearanceReview.getReviewResult(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectAppearanceReview projectAppearanceReview) {
        if (projectAppearanceReview == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReview.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    @Override
    public int deleteByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review
                                           SET deleted_at=? 
                                           WHERE t_project_appearance_review_mode_id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReviewModeId);
    }


    /**
     * 根据useMaterialId删除记录
     */
    @Override
    public int deleteByUseMaterialId(String useMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review
                                           SET deleted_at=? 
                                           WHERE t_use_material_id=?
                                           """,
                                   new Date(),
                                   useMaterialId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectAppearanceReview projectAppearanceReview) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review
                                           SET t_project_appearance_review_mode_id=?,
                                           t_use_material_id=?,
                                           review_status=?,
                                           review_result=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectAppearanceReview.getProjectAppearanceReviewModeId(),
                                   projectAppearanceReview.getUseMaterialId(),
                                   projectAppearanceReview.getReviewStatus(),
                                   projectAppearanceReview.getReviewResult(),
                                   projectAppearanceReview.getReviewDatetime(),
                                   projectAppearanceReview.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review
                                                        WHERE t_project_appearance_review_mode_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewModeId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UseMaterialId得到总数量
     */
    @Override
    public int getCountByUseMaterialId(String useMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review
                                                        WHERE t_use_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectAppearanceReview getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_appearance_review
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectAppearanceReviewMapper(), id);
    }

    @Override
    public List<ProjectAppearanceReview> getByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review
                                                        WHERE t_project_appearance_review_mode_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewModeId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review 
                                          WHERE t_project_appearance_review_mode_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewMapper(), projectAppearanceReviewModeId);
    }

    /**
     * 根据useMaterialId得到记录
     */
    @Override
    public List<ProjectAppearanceReview> getByUseMaterialId(String useMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review
                                                        WHERE t_use_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review 
                                          WHERE t_use_material_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewMapper(), useMaterialId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReview> getPage(int pageNo,
                                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReview> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectAppearanceReview> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                int pageNo,
                                                                                int pageSize) {
        long totalCount = getCountByProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReview> resultData = getPageQueryByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReview> getPageByUseMaterialId(String useMaterialId,
                                                                int pageNo,
                                                                int pageSize) {
        long totalCount = getCountByUseMaterialId(useMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReview> resultData = getPageQueryByUseMaterialId(useMaterialId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectAppearanceReview> getPageQuery(int pageNo,
                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewMapper(), pageNo * pageSize, pageSize);
    }


    /**
     * 根据外键（t_project_appearance_review_mode_id）+获得指定页面数据
     *
     * @param projectAppearanceReviewModeId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    private List<ProjectAppearanceReview> getPageQueryByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                      int pageNo,
                                                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review
                                          WHERE t_project_appearance_review_mode_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewMapper(), projectAppearanceReviewModeId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * 根据外键（t_use_material_id）+获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo
     * @param pageSize
     * @return
     */
    private List<ProjectAppearanceReview> getPageQueryByUseMaterialId(String useMaterialId,
                                                                      int pageNo,
                                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review
                                          WHERE t_use_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewMapper(), useMaterialId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectAppearanceReviewMapper implements RowMapper<ProjectAppearanceReview> {
        @Override
        public ProjectAppearanceReview mapRow(ResultSet rs,
                                              int rowNum) throws SQLException {
            ProjectAppearanceReview projectAppearanceReview = new ProjectAppearanceReview();
            projectAppearanceReview.setId(rs.getString("id"));
            projectAppearanceReview.setProjectAppearanceReviewModeId(
                    rs.getString("t_project_appearance_review_mode_id"));
            projectAppearanceReview.setUseMaterialId(rs.getString("t_use_material_id"));
            projectAppearanceReview.setReviewStatus(rs.getInt("review_status"));
            projectAppearanceReview.setReviewResult(rs.getInt("review_result"));
            projectAppearanceReview.setReviewDatetime(rs.getTimestamp("review_datetime"));
            projectAppearanceReview.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectAppearanceReview;
        }
    }

}