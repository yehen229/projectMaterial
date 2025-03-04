package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialAcceptanceReviewUserFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialAcceptanceReviewUserFileRepositoryImpl implements IProjectMaterialAcceptanceReviewUserFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialAcceptanceReviewUserFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_acceptance_review_user_file(id,
                                        file_path,
                                        t_project_material_acceptance_review_user_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectMaterialAcceptanceReviewUserFile.getFilePath(),
                                projectMaterialAcceptanceReviewUserFile.getProjectMaterialAcceptanceReviewUserId()
        ) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        if (projectMaterialAcceptanceReviewUserFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceReviewUserFile.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user_file
                                           SET file_path=?,
                                           t_project_material_acceptance_review_user_id=?
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptanceReviewUserFile.getFilePath(),
                                   projectMaterialAcceptanceReviewUserFile.getProjectMaterialAcceptanceReviewUserId(),
                                   projectMaterialAcceptanceReviewUserFile.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    /**
     * 根据projectMaterialAcceptanceReviewUserId删除记录
     */
    @Override
    public int deleteByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user_file
                                           SET deleted_at=? 
                                           WHERE t_project_material_acceptance_review_user_id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceReviewUserId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialAcceptanceReviewUserId得到总数量
     */
    @Override
    public int getCountByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user_file
                                                        WHERE t_project_material_acceptance_review_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceReviewUserId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialAcceptanceReviewUserFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance_review_user_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialAcceptanceReviewUserFileMapper(), id);
    }

    /**
     * 根据projectMaterialAcceptanceReviewUserId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewUserFile> getByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user_file
                                                        WHERE t_project_material_acceptance_review_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceReviewUserId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user_file 
                                          WHERE t_project_material_acceptance_review_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserFileMapper(),
                                  projectMaterialAcceptanceReviewUserId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserFile> getPage(int pageNo,
                                                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUserFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceReviewUserId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserFile> getPageByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                        int pageNo,
                                                                                                        int pageSize) {
        long totalCount = getCountByProjectMaterialAcceptanceReviewUserId(projectMaterialAcceptanceReviewUserId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUserFile> resultData = getPageQueryByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialAcceptanceReviewUserFile> getPageQuery(int pageNo,
                                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user_file
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_acceptance_review_user_id）+获得指定页面数据
     *
     * @param projectMaterialAcceptanceReviewUserId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    private List<ProjectMaterialAcceptanceReviewUserFile> getPageQueryByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                              int pageNo,
                                                                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user_file
                                          WHERE t_project_material_acceptance_review_user_id=? 
                                          AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserFileMapper(),
                                  projectMaterialAcceptanceReviewUserId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialAcceptanceReviewUserFileMapper implements RowMapper<ProjectMaterialAcceptanceReviewUserFile> {
        @Override
        public ProjectMaterialAcceptanceReviewUserFile mapRow(ResultSet rs,
                                                              int rowNum) throws SQLException {
            ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = new ProjectMaterialAcceptanceReviewUserFile();
            projectMaterialAcceptanceReviewUserFile.setId(rs.getString("id"));
            projectMaterialAcceptanceReviewUserFile.setFilePath(rs.getString("file_path"));
            projectMaterialAcceptanceReviewUserFile.setProjectMaterialAcceptanceReviewUserId(
                    rs.getString("t_project_material_acceptance_review_user_id"));
            projectMaterialAcceptanceReviewUserFile.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialAcceptanceReviewUserFile;
        }
    }

}