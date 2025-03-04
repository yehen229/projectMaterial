package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.end.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.end.IProjectEndFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectEndFileRepositoryImpl implements IProjectEndFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectEndFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectEndFile projectEndFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_end_file(id,
                                        file_path,
                                        t_project_end_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectEndFile.getFilePath(),
                                projectEndFile.getProjectEndId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectEndFile projectEndFile) {
        if (projectEndFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_end_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectEndFile.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectEndId删除记录
     */
    @Override
    public int deleteByProjectEndId(String projectEndId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end_file
                                           SET deleted_at=? 
                                           WHERE t_project_end_id=?
                                           """,
                                   new Date(),
                                   projectEndId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectEndFile projectEndFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_end_file
                                           SET file_path=?,
                                           t_project_end_id=?
                                           WHERE id=?
                                           """,
                                   projectEndFile.getFilePath(),
                                   projectEndFile.getProjectEndId(),
                                   projectEndFile.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectEndId得到总数量
     */
    @Override
    public int getCountByProjectEndId(String projectEndId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end_file
                                                        WHERE t_project_end_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectEndId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectEndFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_end_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectEndFileMapper(), id);
    }

    /**
     * 根据projectEndId得到记录
     */
    @Override
    public List<ProjectEndFile> getByProjectEndId(String projectEndId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_end_file
                                                        WHERE t_project_end_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectEndId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end_file 
                                          WHERE t_project_end_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectEndFileMapper(), projectEndId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEndFile> getPage(int pageNo,
                                        int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEndFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectEndId
     * @param pageNo       页号，从1开始
     * @param pageSize     每页的记录数
     */
    @Override
    public Page<ProjectEndFile> getPageByProjectEndId(String projectEndId,
                                                      int pageNo,
                                                      int pageSize) {
        long totalCount = getCountByProjectEndId(projectEndId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEndFile> resultData = getPageQueryByProjectEndId(projectEndId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectEndFile> getPageQuery(int pageNo,
                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end_file
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectEndFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_end_id）+获得指定页面数据
     *
     * @param projectEndId
     * @param pageNo       页号，从1开始
     * @param pageSize     每页的记录数
     */
    private List<ProjectEndFile> getPageQueryByProjectEndId(String projectEndId,
                                                            int pageNo,
                                                            int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_end_file
                                          WHERE t_project_end_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectEndFileMapper(), projectEndId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectEndFileMapper implements RowMapper<ProjectEndFile> {
        @Override
        public ProjectEndFile mapRow(ResultSet rs,
                                     int rowNum) throws SQLException {
            ProjectEndFile projectEndFile = new ProjectEndFile();
            projectEndFile.setId(rs.getString("id"));
            projectEndFile.setFilePath(rs.getString("file_path"));
            projectEndFile.setProjectEndId(rs.getString("t_project_end_id"));
            projectEndFile.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectEndFile;
        }
    }

}