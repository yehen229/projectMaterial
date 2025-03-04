package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectOpHistoryContentRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectOpHistoryContentRepositoryImpl implements IProjectOpHistoryContentRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectOpHistoryContentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectOpHistoryContent projectOpHistoryContent) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_op_history_content(id,
                                        t_project_op_history_id,
                                        t_other_table_id,
                                        t_other_table_id_type,
                                        description)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                projectOpHistoryContent.getProjectOpHistoryId(),
                                projectOpHistoryContent.getOtherTableId(),
                                projectOpHistoryContent.getOtherTableIdType(),
                                projectOpHistoryContent.getDescription()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectOpHistoryContent projectOpHistoryContent) {
        if (projectOpHistoryContent == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history_content
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectOpHistoryContent.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectOpHistoryContent projectOpHistoryContent) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history_content
                                           SET t_project_op_history_id=?,
                                           t_other_table_id=?,
                                           t_other_table_id_type=?,
                                           description=?
                                           WHERE id=?
                                           """,
                                   projectOpHistoryContent.getProjectOpHistoryId(),
                                   projectOpHistoryContent.getOtherTableId(),
                                   projectOpHistoryContent.getOtherTableIdType(),
                                   projectOpHistoryContent.getDescription(),
                                   projectOpHistoryContent.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history_content
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    /**
     * 根据projectOpHistoryId删除记录
     */
    @Override
    public int deleteByProjectOpHistoryId(String projectOpHistoryId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history_content
                                           SET deleted_at=? 
                                           WHERE t_project_op_history_id=?
                                           """, new Date(),
                                   projectOpHistoryId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history_content
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectOpHistoryId得到总数量
     */
    @Override
    public int getCountByProjectOpHistoryId(String projectOpHistoryId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history_content
                                                        WHERE t_project_op_history_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectOpHistoryId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectOpHistoryContent getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history_content 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_op_history_content
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectOpHistoryContentMapper(), id);
    }

    /**
     * 根据projectOpHistoryId得到记录
     */
    @Override
    public List<ProjectOpHistoryContent> getByProjectOpHistoryId(String projectOpHistoryId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history_content
                                                        WHERE t_project_op_history_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectOpHistoryId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history_content 
                                          WHERE t_project_op_history_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectOpHistoryContentMapper(), projectOpHistoryId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryContent> getPage(int pageNo,
                                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistoryContent> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectOpHistoryId
     * @param pageNo             页号，从1开始
     * @param pageSize           每页的记录数
     */
    @Override
    public Page<ProjectOpHistoryContent> getPageByProjectOpHistoryId(String projectOpHistoryId,
                                                                     int pageNo,
                                                                     int pageSize) {
        long totalCount = getCountByProjectOpHistoryId(projectOpHistoryId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistoryContent> resultData = getPageQueryByProjectOpHistoryId(projectOpHistoryId, pageNo - 1,
                                                                                    pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectOpHistoryContent> getPageQuery(int pageNo,
                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history_content
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectOpHistoryContentMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_op_history_id）+获得指定页面数据
     *
     * @param projectOpHistoryId
     * @param pageNo             页号，从1开始
     * @param pageSize           每页的记录数
     */
    private List<ProjectOpHistoryContent> getPageQueryByProjectOpHistoryId(String projectOpHistoryId,
                                                                           int pageNo,
                                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history_content
                                          WHERE t_project_op_history_id=? AND deleted_at IS NULL 
                                          LIMIT ?,?
                                          """,
                                  new ProjectOpHistoryContentMapper(), projectOpHistoryId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectOpHistoryContentMapper implements RowMapper<ProjectOpHistoryContent> {
        @Override
        public ProjectOpHistoryContent mapRow(ResultSet rs,
                                              int rowNum) throws SQLException {
            ProjectOpHistoryContent projectOpHistoryContent = new ProjectOpHistoryContent();
            projectOpHistoryContent.setId(rs.getString("id"));
            projectOpHistoryContent.setProjectOpHistoryId(rs.getString("t_project_op_history_id"));
            projectOpHistoryContent.setOtherTableId(rs.getString("t_other_table_id"));
            projectOpHistoryContent.setOtherTableIdType(rs.getInt("t_other_table_id_type"));
            projectOpHistoryContent.setDescription(rs.getString("description"));
            projectOpHistoryContent.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectOpHistoryContent;
        }
    }

}