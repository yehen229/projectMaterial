package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogBusinessRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class LogBusinessRepositoryImpl implements ILogBusinessRepository {
    private final JdbcTemplate jdbcTemplate;

    public LogBusinessRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(LogBusiness logBusiness) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_log_business(id,
                                        business_name,
                                        note)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                logBusiness.getBusinessName(),
                                logBusiness.getNote()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(LogBusiness logBusiness) {
        if (logBusiness == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_log_business
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   logBusiness.getId());


    }

    /**
     * update
     */
    @Override
    public int update(LogBusiness logBusiness) {
        return jdbcTemplate.update("""
                                           UPDATE t_log_business
                                           SET business_name=?,
                                           note=?,
                                           WHERE id=?
                                           """,
                                   logBusiness.getBusinessName(),
                                   logBusiness.getNote(),
                                   logBusiness.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_log_business
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_business
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public LogBusiness getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_business 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_log_business
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new LogBusinessMapper(), id);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<LogBusiness> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogBusiness> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<LogBusiness> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_business
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new LogBusinessMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class LogBusinessMapper implements RowMapper<LogBusiness> {
        @Override
        public LogBusiness mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            LogBusiness logBusiness = new LogBusiness();
            logBusiness.setId(rs.getString("id"));
            logBusiness.setBusinessName(rs.getString("business_name"));
            logBusiness.setNote(rs.getString("note"));
            logBusiness.setDeletedAt(rs.getTimestamp("deleted_at"));
            return logBusiness;
        }
    }

}