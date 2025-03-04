package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewUserFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectAppearanceReviewUserFileRepositoryImpl implements IProjectAppearanceReviewUserFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectAppearanceReviewUserFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_appearance_review_user_file(id,
                                        t_project_appearance_review_user_id,
                                        file_path)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectAppearanceReviewUserFile.getProjectAppearanceReviewUserId(),
                                projectAppearanceReviewUserFile.getFilePath()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        if (projectAppearanceReviewUserFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReviewUserFile.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectAppearanceReviewUserId删除记录
     */
    @Override
    public int deleteByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user_file
                                           SET deleted_at=? 
                                           WHERE t_project_appearance_review_user_id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReviewUserId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user_file
                                           SET t_project_appearance_review_user_id=?,
                                           file_path=?
                                           WHERE id=?
                                           """,
                                   projectAppearanceReviewUserFile.getProjectAppearanceReviewUserId(),
                                   projectAppearanceReviewUserFile.getFilePath(),
                                   projectAppearanceReviewUserFile.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectAppearanceReviewUserId得到总数量
     */
    @Override
    public int getCountByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user_file
                                                        WHERE t_project_appearance_review_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewUserId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectAppearanceReviewUserFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_appearance_review_user_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectAppearanceReviewUserFileMapper(), id);
    }

    /**
     * 根据projectAppearanceReviewUserId得到记录
     */
    @Override
    public List<ProjectAppearanceReviewUserFile> getByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user_file
                                                        WHERE t_project_appearance_review_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewUserId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user_file 
                                          WHERE t_project_appearance_review_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewUserFileMapper(), projectAppearanceReviewUserId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserFile> getPage(int pageNo,
                                                         int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUserFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectAppearanceReviewUserId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserFile> getPageByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId,
                                                                                        int pageNo,
                                                                                        int pageSize) {
        long totalCount = getCountByProjectAppearanceReviewUserId(projectAppearanceReviewUserId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUserFile> resultData = getPageQueryByProjectAppearanceReviewUserId(
                projectAppearanceReviewUserId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectAppearanceReviewUserFile> getPageQuery(int pageNo,
                                                               int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user_file
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewUserFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_appearance_review_user_id）+获得指定页面数据
     *
     * @param projectAppearanceReviewUserId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    private List<ProjectAppearanceReviewUserFile> getPageQueryByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId,
                                                                                              int pageNo,
                                                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user_file
                                          WHERE t_project_appearance_review_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewUserFileMapper(), projectAppearanceReviewUserId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectAppearanceReviewUserFileMapper implements RowMapper<ProjectAppearanceReviewUserFile> {
        @Override
        public ProjectAppearanceReviewUserFile mapRow(ResultSet rs,
                                                      int rowNum) throws SQLException {
            ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = new ProjectAppearanceReviewUserFile();
            projectAppearanceReviewUserFile.setId(rs.getString("id"));
            projectAppearanceReviewUserFile.setProjectAppearanceReviewUserId(
                    rs.getString("t_project_appearance_review_user_id"));
            projectAppearanceReviewUserFile.setFilePath(rs.getString("file_path"));
            projectAppearanceReviewUserFile.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectAppearanceReviewUserFile;
        }
    }

}