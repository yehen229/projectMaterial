package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectFileRepositoryImpl implements IProjectFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectFile projectFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_file(id,
                                        t_project_id,
                                        file_path)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectFile.getProjectId(),
                                projectFile.getFilePath()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectFile projectFile) {
        if (projectFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectFile.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_file
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
                                           UPDATE t_project_file
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
    public int update(ProjectFile projectFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_file
                                           SET t_project_id=?,
                                           file_path=?
                                           WHERE id=?
                                           """,
                                   projectFile.getProjectId(),
                                   projectFile.getFilePath(),
                                   projectFile.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_file
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
                                                        FROM t_project_file
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectFileMapper(), id);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectFile> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_file
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_file 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectFileMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectFile> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectFile> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectFile> getPageByProjectId(String projectId,
                                                int pageNo,
                                                int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectFile> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectFile> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_file
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectFile> getPageQueryByProjectId(String projectId,
                                                      int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_file
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectFileMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectFileMapper implements RowMapper<ProjectFile> {
        @Override
        public ProjectFile mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            ProjectFile projectFile = new ProjectFile();
            projectFile.setId(rs.getString("id"));
            projectFile.setProjectId(rs.getString("t_project_id"));
            projectFile.setFilePath(rs.getString("file_path"));
            projectFile.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectFile;
        }
    }

}