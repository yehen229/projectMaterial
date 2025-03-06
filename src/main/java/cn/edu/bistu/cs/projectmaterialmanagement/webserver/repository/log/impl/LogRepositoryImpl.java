package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl.ProjectRepositoryImpl;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LogRepositoryImpl implements ILogRepository {

    private final JdbcTemplate jdbcTemplate;


    public LogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert,添加日志信息
     */
    @Override
    public String add(Log log) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_log_admin(id,
                                        t_user_id,
                                        t_project_id,
                                        step_description,
                                        op_type,
                                        op_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                newId,
                log.getT_user_id(),
                log.getT_project_id(),
                log.getStep_description(),
                log.getOp_type(),
                log.getOp_datetime()) > 0)
            return newId;
        return null;
    }
    /**
     * getCount,日志
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_admin
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
                                                        FROM t_log_admin 
                                                        WHERE t_user_id=?
                                                        """,
                Integer.class, userId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_admin 
                                                        WHERE t_project_id=?
                                                        """,
                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public Log getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_admin 
                                                        WHERE id=?
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_log_admin 
                                                   WHERE id=?
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
                                                        FROM t_log_admin 
                                                        WHERE t_user_id=?
                                                        """,
                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_admin 
                                          WHERE t_user_id=?
                                          """,
                new LogMapper(), userId);
    }

    @Override
    public List<Log> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_log_admin 
                                                        WHERE t_project_id=?
                                                        """,
                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_log_admin 
                                          WHERE t_project_id=?
                                          """,
                new LogMapper(), projectId);
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

    @Override
    public List<Log> getAllLog() {
        long totalCount = getCount();
        if (totalCount < 1) return new ArrayList<>();
        List<Log> resultData = getAllQuery();
        return resultData;
    }

    private List<Log> getAllQuery() {

        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_log_admin
                        """,
                Integer.class);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_log_admin
                        order by op_datetime desc
                        """,
                new LogMapper());
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

    @Override
    public Page<Log> getPageByProjectId(String projectId, int pageNo, int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Log> resultData = getPageQueryByUserId(projectId, pageNo - 1, pageSize);
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
                                          FROM t_log_admin 
                                          order by op_datetime desc
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
                                          FROM t_log_admin 
                                          WHERE t_user_id=? 
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
            log.setT_user_id(rs.getString("t_user_id"));
            log.setT_project_id(rs.getString("t_project_id"));
            log.setStep_description(rs.getString("step_description"));
            log.setOp_type(rs.getInt("op_type"));
            log.setOp_datetime(rs.getTimestamp("op_datetime"));
            return log;
        }
    }

}