package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialVerificationDocumentFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialVerificationDocumentFileRepositoryImpl implements IProjectMaterialVerificationDocumentFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialVerificationDocumentFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_verification_document_file(id,
                                        t_project_material_verification_document_id,
                                        file_path,
                                        file_type)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectMaterialVerificationDocumentFile.getProjectMaterialVerificationDocumentId(),
                                projectMaterialVerificationDocumentFile.getFilePath(),
                                projectMaterialVerificationDocumentFile.getFileType()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        if (projectMaterialVerificationDocumentFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectMaterialVerificationDocumentFile.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectMaterialVerificationDocumentId删除记录
     */
    @Override
    public int deleteByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document_file
                                           SET deleted_at=? 
                                           WHERE t_project_material_verification_document_id=?
                                           """,
                                   new Date(),
                                   projectMaterialVerificationDocumentId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document_file
                                           SET t_project_material_verification_document_id=?,
                                           file_path=?,
                                           file_type=?
                                           WHERE id=?
                                           """,
                                   projectMaterialVerificationDocumentFile.getProjectMaterialVerificationDocumentId(),
                                   projectMaterialVerificationDocumentFile.getFilePath(),
                                   projectMaterialVerificationDocumentFile.getFileType(),
                                   projectMaterialVerificationDocumentFile.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialVerificationDocumentId得到总数量
     */
    @Override
    public int getCountByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document_file
                                                        WHERE t_project_material_verification_document_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialVerificationDocumentId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialVerificationDocumentFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document_file  
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_verification_document_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialVerificationDocumentFileMapper(), id);
    }

    /**
     * 根据projectMaterialVerificationDocumentId得到记录
     */
    @Override
    public List<ProjectMaterialVerificationDocumentFile> getByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document_file
                                                        WHERE t_project_material_verification_document_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialVerificationDocumentId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document_file 
                                          WHERE t_project_material_verification_document_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialVerificationDocumentFileMapper(),
                                  projectMaterialVerificationDocumentId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentFile> getPage(int pageNo,
                                                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocumentFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialVerificationDocumentId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentFile> getPageByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId,
                                                                                                        int pageNo,
                                                                                                        int pageSize) {
        long totalCount = getCountByProjectMaterialVerificationDocumentId(projectMaterialVerificationDocumentId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocumentFile> resultData = getPageQueryByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialVerificationDocumentFile> getPageQuery(int pageNo,
                                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document_file
                                          WHERE deleted_at IS NULL AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialVerificationDocumentFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_verification_document_id）+获得指定页面数据
     *
     * @param projectMaterialVerificationDocumentId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    private List<ProjectMaterialVerificationDocumentFile> getPageQueryByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId,
                                                                                                              int pageNo,
                                                                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document_file
                                          WHERE t_project_material_verification_document_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialVerificationDocumentFileMapper(),
                                  projectMaterialVerificationDocumentId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialVerificationDocumentFileMapper implements RowMapper<ProjectMaterialVerificationDocumentFile> {
        @Override
        public ProjectMaterialVerificationDocumentFile mapRow(ResultSet rs,
                                                              int rowNum) throws SQLException {
            ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile = new ProjectMaterialVerificationDocumentFile();
            projectMaterialVerificationDocumentFile.setId(rs.getString("id"));
            projectMaterialVerificationDocumentFile.setProjectMaterialVerificationDocumentId(
                    rs.getString("t_project_material_verification_document_id"));
            projectMaterialVerificationDocumentFile.setFilePath(rs.getString("file_path"));
            projectMaterialVerificationDocumentFile.setFileType(rs.getInt("file_type"));
            projectMaterialVerificationDocumentFile.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectMaterialVerificationDocumentFile;
        }
    }

}