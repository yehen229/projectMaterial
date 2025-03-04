package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBussinessOp;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogBussinessOpRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class LogBussinessOpRepositoryImpl implements ILogBussinessOpRepository {
    private final JdbcTemplate jdbcTemplate;

    public LogBussinessOpRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(LogBussinessOp logBussinessOp) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_log_bussiness_op(id,
                                        t_log_id,
                                        t_log_business_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                logBussinessOp.getLogId(),
                                logBussinessOp.getLogBusinessId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(LogBussinessOp logBussinessOp) {
        if (logBussinessOp == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_log_bussiness_op
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   logBussinessOp.getId());


    }

    /**
     * update
     */
    @Override
    public int update(LogBussinessOp logBussinessOp) {
        return jdbcTemplate.update("""
                                           UPDATE t_log_bussiness_op
                                           SET t_log_id=?,
                                           t_log_business_id=?,
                                           WHERE id=?
                                           """,
                                   logBussinessOp.getLogId(),
                                   logBussinessOp.getLogBusinessId(),
                                   logBussinessOp.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_log_bussiness_op
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
                                           UPDATE t_log_bussiness_op
                                           SET deleted_at=? 
                                           WHERE t_log_id=?
                                           """,
                                   new Date(),
                                   logId);


    }

    /**
     * 根据logBusinessId删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteByLogBusinessId(String logBusinessId) {

        return jdbcTemplate.update("""
                                           UPDATE t_log_bussiness_op
                                           SET deleted_at=? 
                                           WHERE t_log_business_id=?
                                           """,
                                   new Date(),
                                   logBusinessId);


    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_bussiness_op
                                                        WHERE deleted_at IS NULL
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
                                                        FROM t_log_bussiness_op
                                                        WHERE t_log_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, logId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键LogBusinessId得到总数量
     */
    @Override
    public int getCountByLogBusinessId(String logBusinessId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_bussiness_op
                                                        WHERE t_log_business_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, logBusinessId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public LogBussinessOp getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_bussiness_op 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_log_bussiness_op
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new LogBussinessOpMapper(), id);
    }

    /**
     * 根据logId得到记录
     */
    @Override
    public List<LogBussinessOp> getByLogId(String logId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_bussiness_op
                                                        WHERE t_log_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, logId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_bussiness_op 
                                          WHERE t_log_id=? AND deleted_at IS NULL
                                          """,
                                  new LogBussinessOpMapper(), logId);
    }

    /**
     * 根据logBusinessId得到记录
     */
    @Override
    public List<LogBussinessOp> getByLogBusinessId(String logBusinessId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_bussiness_op
                                                        WHERE t_log_business_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, logBusinessId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_bussiness_op 
                                          WHERE t_log_business_id=? AND deleted_at IS NULL
                                          """,
                                  new LogBussinessOpMapper(), logBusinessId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<LogBussinessOp> getPage(int pageNo,
                                        int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogBussinessOp> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<LogBussinessOp> getPageByLogId(String logId,
                                               int pageNo,
                                               int pageSize) {
        long totalCount = getCountByLogId(logId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogBussinessOp> resultData = getPageQueryByLogId(logId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param logBusinessId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<LogBussinessOp> getPageByLogBusinessId(String logBusinessId,
                                                       int pageNo,
                                                       int pageSize) {
        long totalCount = getCountByLogBusinessId(logBusinessId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogBussinessOp> resultData = getPageQueryByLogBusinessId(logBusinessId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<LogBussinessOp> getPageQuery(int pageNo,
                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_bussiness_op
                                          WHERE   deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogBussinessOpMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_log_id）+获得指定页面数据
     *
     * @param logId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<LogBussinessOp> getPageQueryByLogId(String logId,
                                                     int pageNo,
                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_bussiness_op
                                          WHERE t_log_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogBussinessOpMapper(), logId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_log_business_id）+获得指定页面数据
     *
     * @param logBusinessId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    private List<LogBussinessOp> getPageQueryByLogBusinessId(String logBusinessId,
                                                             int pageNo,
                                                             int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_bussiness_op
                                          WHERE t_log_business_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogBussinessOpMapper(), logBusinessId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class LogBussinessOpMapper implements RowMapper<LogBussinessOp> {
        @Override
        public LogBussinessOp mapRow(ResultSet rs,
                                     int rowNum) throws SQLException {
            LogBussinessOp logBussinessOp = new LogBussinessOp();
            logBussinessOp.setId(rs.getString("id"));
            logBussinessOp.setLogId(rs.getString("t_log_id"));
            logBussinessOp.setLogBusinessId(rs.getString("t_log_business_id"));
            logBussinessOp.setDeletedAt(rs.getTimestamp("deleted_at"));
            return logBussinessOp;
        }
    }

}