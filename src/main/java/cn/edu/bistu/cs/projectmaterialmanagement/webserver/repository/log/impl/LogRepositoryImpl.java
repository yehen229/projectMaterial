package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class LogRepositoryImpl implements ILogRepository {
    private final JdbcTemplate jdbcTemplate;

    public LogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(Log log) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_log(id,
                                        t_user_id,
                                        op_type,
                                        table_name,
                                        op_datetime)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                log.getUserId(),
                                log.getOpType(),
                                log.getTableName(),
                                log.getOpDatetime()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(Log log) {
        if (log == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_log
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   log.getId());


    }

    /**
     * update
     */
    @Override
    public int update(Log log) {
        return jdbcTemplate.update("""
                                           UPDATE t_log
                                           SET t_user_id=?,
                                           op_type=?,
                                           table_name=?,
                                           op_datetime=?
                                           WHERE id=?
                                           """,
                                   log.getUserId(),
                                   log.getOpType(),
                                   log.getTableName(),
                                   log.getOpDatetime(),
                                   log.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_log
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * 根据userId删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteByUserId(String userId) {

        return jdbcTemplate.update("""
                                           UPDATE t_log
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);


    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log 
                                                        WHERE  deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public Log getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_log
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new LogMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<Log> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new LogMapper(), userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Log> getPage(int pageNo,
                             int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Log> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Log> getPageByUserId(String userId,
                                     int pageNo,
                                     int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Log> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<Log> getPageQuery(int pageNo,
                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<Log> getPageQueryByUserId(String userId,
                                           int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log
                                          WHERE t_user_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class LogMapper implements RowMapper<Log> {
        @Override
        public Log mapRow(ResultSet rs,
                          int rowNum) throws SQLException {
            Log log = new Log();
            log.setId(rs.getString("id"));
            log.setUserId(rs.getString("t_user_id"));
            log.setOpType(rs.getInt("op_type"));
            log.setTableName(rs.getString("table_name"));
            log.setOpDatetime(rs.getTimestamp("op_datetime"));
            log.setDeletedAt(rs.getTimestamp("deleted_at"));
            return log;
        }
    }

}