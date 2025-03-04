package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectReviewUserFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectReviewUserFileRepositoryImpl implements IProjectReviewUserFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectReviewUserFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectReviewUserFile projectReviewUserFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_review_user_file(id,
                                        t_project_review_user_id,
                                        file_path)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectReviewUserFile.getProjectReviewUserId(),
                                projectReviewUserFile.getFilePath()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectReviewUserFile projectReviewUserFile) {
        if (projectReviewUserFile == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectReviewUserFile.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectReviewUserId删除记录
     */
    @Override
    public int deleteByProjectReviewUserId(String projectReviewUserId) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user_file
                                           SET deleted_at=? 
                                           WHERE t_project_review_user_id=?
                                           """,
                                   new Date(),
                                   projectReviewUserId);


    }

    /**
     * update
     */
    @Override
    public int update(ProjectReviewUserFile projectReviewUserFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user_file
                                           SET t_project_review_user_id=?,
                                           file_path=?
                                           WHERE id=?
                                           """,
                                   projectReviewUserFile.getProjectReviewUserId(),
                                   projectReviewUserFile.getFilePath(),
                                   projectReviewUserFile.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectReviewUserId得到总数量
     */
    @Override
    public int getCountByProjectReviewUserId(String projectReviewUserId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user_file
                                                        WHERE t_project_review_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectReviewUserId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectReviewUserFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_review_user_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectReviewUserFileMapper(), id);
    }

    /**
     * 根据projectReviewUserId得到记录
     */
    @Override
    public List<ProjectReviewUserFile> getByProjectReviewUserId(String projectReviewUserId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user_file
                                                        WHERE t_project_review_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectReviewUserId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user_file 
                                          WHERE t_project_review_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectReviewUserFileMapper(), projectReviewUserId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewUserFile> getPage(int pageNo,
                                               int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUserFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectReviewUserId
     * @param pageNo              页号，从1开始
     * @param pageSize            每页的记录数
     */
    @Override
    public Page<ProjectReviewUserFile> getPageByProjectReviewUserId(String projectReviewUserId,
                                                                    int pageNo,
                                                                    int pageSize) {
        long totalCount = getCountByProjectReviewUserId(projectReviewUserId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUserFile> resultData = getPageQueryByProjectReviewUserId(projectReviewUserId, pageNo - 1,
                                                                                   pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectReviewUserFile> getPageQuery(int pageNo,
                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user_file
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewUserFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_review_user_id）+获得指定页面数据
     *
     * @param projectReviewUserId
     * @param pageNo              页号，从1开始
     * @param pageSize            每页的记录数
     */
    private List<ProjectReviewUserFile> getPageQueryByProjectReviewUserId(String projectReviewUserId,
                                                                          int pageNo,
                                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user_file
                                          WHERE t_project_review_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewUserFileMapper(), projectReviewUserId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectReviewUserFileMapper implements RowMapper<ProjectReviewUserFile> {
        @Override
        public ProjectReviewUserFile mapRow(ResultSet rs,
                                            int rowNum) throws SQLException {
            ProjectReviewUserFile projectReviewUserFile = new ProjectReviewUserFile();
            projectReviewUserFile.setId(rs.getString("id"));
            projectReviewUserFile.setProjectReviewUserId(rs.getString("t_project_review_user_id"));
            projectReviewUserFile.setFilePath(rs.getString("file_path"));
            projectReviewUserFile.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectReviewUserFile;
        }
    }

}