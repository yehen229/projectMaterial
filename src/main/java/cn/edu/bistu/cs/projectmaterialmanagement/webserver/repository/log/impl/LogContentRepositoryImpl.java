package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogContentRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class LogContentRepositoryImpl implements ILogContentRepository {
    private final JdbcTemplate jdbcTemplate;

    public LogContentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(LogContent logContent) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_log_content(id,
                                        t_log_id,
                                        table_column,
                                        old_value,
                                        new_value)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                logContent.getLogId(),
                                logContent.getTableColumn(),
                                logContent.getOldValue(),
                                logContent.getNewValue()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(LogContent logContent) {
        if (logContent == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_log_content
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   logContent.getId());


    }

    /**
     * update
     */
    @Override
    public int update(LogContent logContent) {
        return jdbcTemplate.update("""
                                           UPDATE t_log_content
                                           SET t_log_id=?,
                                           table_column=?,
                                           old_value=?,
                                           new_value=?,
                                           WHERE id=?
                                           """,
                                   logContent.getLogId(),
                                   logContent.getTableColumn(),
                                   logContent.getOldValue(),
                                   logContent.getNewValue(),
                                   logContent.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_log_content
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * 根据logId删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteByLogId(String logId) {

        return jdbcTemplate.update("""
                                           UPDATE t_log_content
                                           SET deleted_at=? 
                                           WHERE t_log_id=?
                                           """,
                                   new Date(),
                                   logId);

    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_content
                                                        WHERE  deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键LogId得到总数量
     */
    @Override
    public int getCountByLogId(String logId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_content
                                                        WHERE t_log_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, logId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public LogContent getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_content 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_log_content
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new LogContentMapper(), id);
    }

    /**
     * 根据logId得到记录
     */
    @Override
    public List<LogContent> getByLogId(String logId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_content
                                                        WHERE t_log_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, logId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_content 
                                          WHERE t_log_id=? AND deleted_at IS NULL
                                          """,
                                  new LogContentMapper(), logId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<LogContent> getPage(int pageNo,
                                    int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogContent> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param logId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<LogContent> getPageByLogId(String logId,
                                           int pageNo,
                                           int pageSize) {
        long totalCount = getCountByLogId(logId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogContent> resultData = getPageQueryByLogId(logId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<LogContent> getPageQuery(int pageNo,
                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_content
                                          WHERE  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogContentMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_log_id）+获得指定页面数据
     *
     * @param logId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<LogContent> getPageQueryByLogId(String logId,
                                                 int pageNo,
                                                 int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_content
                                          WHERE t_log_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogContentMapper(), logId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class LogContentMapper implements RowMapper<LogContent> {
        @Override
        public LogContent mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
            LogContent logContent = new LogContent();
            logContent.setId(rs.getString("id"));
            logContent.setLogId(rs.getString("t_log_id"));
            logContent.setTableColumn(rs.getString("table_column"));
            logContent.setOldValue(rs.getString("old_value"));
            logContent.setNewValue(rs.getString("new_value"));
            logContent.setDeletedAt(rs.getTimestamp("deleted_at"));
            return logContent;
        }
    }

}