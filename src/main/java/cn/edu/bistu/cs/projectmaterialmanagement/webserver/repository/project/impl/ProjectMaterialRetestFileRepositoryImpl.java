package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRetestFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialRetestFileRepositoryImpl implements IProjectMaterialRetestFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialRetestFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialRetestFile projectMaterialRetestFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_retest_file(id,
                                        t_project_material_retest_id,
                                        file_path,
                                        deleted_at)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectMaterialRetestFile.getProjectMaterialRetestId(),
                                projectMaterialRetestFile.getFilePath(),
                                projectMaterialRetestFile.getDeletedAt()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialRetestFile projectMaterialRetestFile) {
        if (projectMaterialRetestFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest_file
                                           SET deleted_at=?
                                           WHERE id=?
                                           """, new Date(),
                                   projectMaterialRetestFile.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialRetestFile projectMaterialRetestFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest_file
                                           SET t_project_material_retest_id=?,
                                           file_path=?,
                                           deleted_at=? 
                                           WHERE id=?
                                           """,
                                   projectMaterialRetestFile.getProjectMaterialRetestId(),
                                   projectMaterialRetestFile.getFilePath(),
                                   projectMaterialRetestFile.getDeletedAt(),
                                   projectMaterialRetestFile.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialRetestFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_retest_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialRetestFileMapper(), id);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialRetestFile> getPage(int pageNo,
                                                   int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialRetestFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialRetestFile> getPageQuery(int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest_file
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialRetestFileMapper(), pageNo * pageSize, pageSize);
    }
    @Override
    public List<ProjectMaterialRetestFile> getByRetestId(String RetestId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user_file
                                                        WHERE t_project_material_retest_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, RetestId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest_file 
                                          WHERE t_project_material_retest_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialRetestFileMapper(), RetestId);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialRetestFileMapper implements RowMapper<ProjectMaterialRetestFile> {
        @Override
        public ProjectMaterialRetestFile mapRow(ResultSet rs,
                                                int rowNum) throws SQLException {
            ProjectMaterialRetestFile projectMaterialRetestFile = new ProjectMaterialRetestFile();
            projectMaterialRetestFile.setId(rs.getString("id"));
            projectMaterialRetestFile.setProjectMaterialRetestId(rs.getString("t_project_material_retest_id"));
            projectMaterialRetestFile.setFilePath(rs.getString("file_path"));
            projectMaterialRetestFile.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialRetestFile;
        }
    }

}