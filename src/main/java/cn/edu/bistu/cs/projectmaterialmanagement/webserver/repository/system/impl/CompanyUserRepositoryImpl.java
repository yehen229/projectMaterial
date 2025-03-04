package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.ICompanyUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class CompanyUserRepositoryImpl implements ICompanyUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CompanyUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(CompanyUser companyUser) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_company_user(id,
                                        t_company_id,
                                        t_user_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                companyUser.getCompanyId(),
                                companyUser.getUserId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(CompanyUser companyUser) {
        if (companyUser == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_company_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   companyUser.getId());


    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_company_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * 根据companyId删除记录
     */
    @Override
    public int deleteByCompanyId(String companyId) {

        return jdbcTemplate.update("""
                                           UPDATE t_company_user
                                           SET deleted_at=? 
                                           WHERE t_company_id=?
                                           """,
                                   new Date(),
                                   companyId);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {

        return jdbcTemplate.update("""
                                           UPDATE t_company_user
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);
    }

    /**
     * update
     */
    @Override
    public int update(CompanyUser companyUser) {
        return jdbcTemplate.update("""
                                           UPDATE t_company_user
                                           SET t_company_id=?,
                                           t_user_id=?
                                           WHERE id=?
                                           """,
                                   companyUser.getCompanyId(),
                                   companyUser.getUserId(),
                                   companyUser.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键CompanyId得到总数量
     */
    @Override
    public int getCountByCompanyId(String companyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        WHERE t_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, companyId);
        return i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i;
    }

    /**
     * 根据外键UserName得到总数量
     *
     * @param userName 用户登录id或者用户真实姓名
     * @return
     */
    @Override
    public int getCountByUserName(String userName) {
        userName = "%" + userName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        LEFT JOIN t_user ON t_company_user.t_user_id=t_user.id
                                                        WHERE user_name LIKE ? or real_name LIKE ? 
                                                              AND t_company_user.deleted_at IS NULL
                                                              AND t_user.deleted_at IS NULL 
                                                        """,
                                                Integer.class, userName, userName);
        return i;
    }

    @Override
    public int getCountByProjectName(String projectName) {
        projectName = "%" + projectName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        LEFT JOIN t_user ON t_company_user.t_user_id=t_user.id
                                                        LEFT JOIN t_project_user ON t_project_user.t_user_id=t_user.id
                                                        LEFT JOIN t_project ON t_project.id=t_project_user.t_project_id
                                                        WHERE t_project.name LIKE ?
                                                            AND t_company_user.deleted_at IS NULL
                                                            AND t_user.deleted_at IS NULL
                                                            AND t_project_user.deleted_at IS NULL
                                                            AND t_project.deleted_at IS NULL 
                                                        """,
                                                Integer.class, projectName);
        return i;
    }

    /**
     * 根据外键CompanyName得到总数量
     *
     * @param companyName 公司名称
     * @return 包含该公司名称的公司数量
     */
    @Override
    public int getCountByCompanyName(String companyName) {
        companyName = "%" + companyName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                                        WHERE t_company.name LIKE ?
                                                            AND t_company.deleted_at IS NULL 
                                                            AND t_company_user.deleted_at IS NULL 
                                                        """,
                                                Integer.class, companyName);
        return i;
    }

    @Override
    public int getCountByCompanyType(String companyType) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                                        WHERE t_company.company_type = ?
                                                            AND t_company.deleted_at IS NULL 
                                                            AND t_company_user.deleted_at IS NULL 
                                                        """,
                                                Integer.class, companyType);
        return i;
    }

    @Override
    public int getCountByCompanyNameAndType(String companyName,
                                            String companyType) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                                        WHERE t_company.name = ? AND t_company.company_type= ?
                                                            AND t_company.deleted_at IS NULL 
                                                            AND t_company_user.deleted_at IS NULL 
                                                        """,
                                                Integer.class, companyName, companyType);
        return i;
    }

    @Override
    public int getCountExcludingByCompanyId(String companyId,
                                            List<String> userIdList) {

        StringBuilder sql= new StringBuilder("""
                                                     SELECT count(*) 
                                                     FROM t_company_user                                                       
                                                     WHERE t_company_user.t_company_id= ?                                                             
                                                         AND t_company_user.deleted_at IS NULL 
                                                     """);
        for (String userId : userIdList) {
            sql.append(" AND t_company_user.t_user_id != '").append(userId).append("'");
        }

        return jdbcTemplate.queryForObject(sql.toString(),
                                           Integer.class, companyId);
    }

    /**
     * 根据id得到记录
     */
    @Override
    public CompanyUser getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_company_user
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new CompanyUserMapper(), id);
    }

    /**
     * 根据companyId得到记录
     */
    @Override
    public List<CompanyUser> getByCompanyId(String companyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        WHERE t_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, companyId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company_user 
                                          WHERE t_company_id=? AND deleted_at IS NULL
                                          """,
                                  new CompanyUserMapper(), companyId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public CompanyUser getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_company_user
                                                   WHERE t_user_id=? AND deleted_at IS NULL
                                                   """,
                                           new CompanyUserMapper(), userId);
    }

    @Override
    public List<CompanyUser> getAllList() {
        if (getCount() == 0) return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company_user  
                                          WHERE deleted_at IS NULL
                                          """,
                                  new CompanyUserMapper());
    }

    @Override
    public List<CompanyUser> getListByUserName(String userName) {
        if (getCountByUserName(userName) == 0) return null;
        return jdbcTemplate.query("""
                                          SELECT count(*) 
                                          FROM t_company_user
                                          LEFT JOIN t_user ON t_company_user.t_user_id=t_user.id
                                          WHERE user_name LIKE ? or real_name LIKE ?
                                              AND t_company_user.deleted_at IS NULL
                                              AND t_user.deleted_at IS NULL
                                          """,
                                  new CompanyUserMapper());
    }

    @Override
    public List<CompanyUser> getListByProjectName(String projectName) {
        if (getCountByProjectName(projectName) == 0) return null;
        return jdbcTemplate.query("""
                                          SELECT t_company_user.*
                                          FROM t_company_user
                                          LEFT JOIN t_user ON t_company_user.t_user_id=t_user.id
                                          LEFT JOIN t_project_user ON t_project_user.t_user_id=t_user.id
                                          LEFT JOIN t_project ON t_project.id=t_project_user.t_project_id
                                          WHERE t_project.name LIKE ?
                                              AND t_company_user.deleted_at IS NULL
                                              AND t_user.deleted_at IS NULL
                                              AND t_project_user.deleted_at IS NULL
                                              AND t_project.deleted_at IS NULL
                                          """,
                                  new CompanyUserMapper());
    }

    @Override
    public List<CompanyUser> getListByCompanyName(String companyName) {
        if (getCountByCompanyName(companyName) == 0) return null;

        return jdbcTemplate.query("""
                                           SELECT t_company_user.* 
                                          FROM t_company_user
                                          LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                          WHERE t_company.name LIKE ?
                                              AND t_company_user.deleted_at IS NULL
                                              AND t_company.deleted_at IS NULL
                                          """,
                                  new CompanyUserMapper());
    }

    @Override
    public List<CompanyUser> getByCompanyType(String companyType) {
        companyType = "%" + companyType.trim() + "%";

        return jdbcTemplate.query("""
                                          SELECT t_company_user.* 
                                          FROM t_company_user
                                          LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                          WHERE t_company.company_type LIKE ?
                                          AND t_company.deleted_at IS NULL
                                          AND t_company_user.deleted_at IS NULL
                                          """,
                                  new CompanyUserMapper(), companyType);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<CompanyUser> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param companyId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<CompanyUser> getPageByCompanyId(String companyId,
                                                int pageNo,
                                                int pageSize) {
        long totalCount = getCountByCompanyId(companyId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByCompanyId(companyId, pageNo - 1, pageSize);
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
    public Page<CompanyUser> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<CompanyUser> getPageByUserName(String userName,
                                               int pageNo,
                                               int pageSize) {
        long totalCount = getCountByUserName(userName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByUserName(userName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<CompanyUser> getPageByProjectName(String projectName,
                                                  int pageNo,
                                                  int pageSize) {
        long totalCount = getCountByProjectName(projectName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByProjectName(projectName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<CompanyUser> getPageByCompanyName(String companyName,
                                                  int pageNo,
                                                  int pageSize) {
        long totalCount = getCountByCompanyName(companyName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByCompanyName(companyName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<CompanyUser> getPageByCompanyType(String companyType,
                                                  Integer pageNo,
                                                  Integer pageSize) {
        long totalCount = getCountByCompanyType(companyType);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByCompanyType(companyType, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<CompanyUser> getPageByNameAndType(String companyName,
                                                  String companyType,
                                                  Integer pageNo,
                                                  Integer pageSize) {
        long totalCount = getCountByCompanyNameAndType(companyName, companyType);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryByCompanyNameAndType(companyName, companyType, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);

    }

    @Override
    public Page<CompanyUser> getPageExcludingByCompanyId(String companyId,
                                                         List<String> userIdList,
                                                         Integer pageNo,
                                                         Integer pageSize) {
        long totalCount = getCountExcludingByCompanyId(companyId, userIdList);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUser> resultData = getPageQueryExcludingByCompanyId(companyId, userIdList, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    private List<CompanyUser> getPageQueryExcludingByCompanyId(String companyId,
                                                               List<String> userIdList,
                                                               int pageNo,
                                                               Integer pageSize) {

        StringBuilder sql= new StringBuilder("""
                                                     SELECT t_company_user.* 
                                                     FROM t_company_user                  
                                                     WHERE t_company_user.t_company_id = ?                   
                                                     AND t_company_user.deleted_at IS NULL                                          
                                                     """);
        for (String userId : userIdList) {
            sql.append(" AND t_company_user.t_user_id != '").append(userId).append("'");
        }



        sql.append(" ORDER BY  t_user_id LIMIT ?,?");
        return jdbcTemplate.query(sql.toString(),
                                  new CompanyUserMapper(), companyId,  pageNo * pageSize, pageSize);


    }

    private List<CompanyUser> getPageQueryByCompanyNameAndType(String companyName,
                                                               String companyType,
                                                               int pageNo,
                                                               Integer pageSize) {
        return jdbcTemplate.query("""
                                          SELECT t_company_user.*
                                          FROM t_company_user
                                          LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                          WHERE t_company.name = ? AND t_company.company_type = ? 
                                          AND t_company.deleted_at IS NULL
                                          AND t_company_user.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), companyName, companyType, pageNo * pageSize, pageSize);
    }

    private List<CompanyUser> getPageQueryByCompanyType(String companyType,
                                                        int pageNo,
                                                        Integer pageSize) {
        return jdbcTemplate.query("""
                                          SELECT t_company_user.* 
                                          FROM t_company_user
                                          LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                          WHERE t_company.company_type = ?
                                          AND t_company.deleted_at IS NULL
                                          AND t_company_user.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), companyType, pageNo * pageSize, pageSize);
    }

    private List<CompanyUser> getPageQueryByUserName(String userName,
                                                     int pageNo,
                                                     int pageSize) {

        userName = "%" + userName.trim() + "%";

        return jdbcTemplate.query("""
                                             SELECT t_company_user.* 
                                             FROM t_company_user
                                            LEFT JOIN t_user ON t_company_user.t_user_id=t_user.id
                                             WHERE user_name LIKE ? or real_name LIKE ?
                                                  AND t_company_user.deleted_at IS NULL
                                                  AND t_user.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), userName, userName, pageNo * pageSize, pageSize);
    }

    private List<CompanyUser> getPageQueryByProjectName(String projectName,
                                                        int pageNo,
                                                        int pageSize) {
        projectName = "%" + projectName.trim() + "%";
        return jdbcTemplate.query("""
                                          SELECT t_company_user.* 
                                          FROM t_company_user
                                          LEFT JOIN t_user ON t_company_user.t_user_id=t_user.id
                                          LEFT JOIN t_project_user ON t_project_user.t_user_id=t_user.id
                                          LEFT JOIN t_project ON t_project.id=t_project_user.t_project_id
                                          WHERE t_project.name LIKE ?
                                              AND t_company_user.deleted_at IS NULL
                                              AND t_user.deleted_at IS NULL
                                              AND t_project_user.deleted_at IS NULL
                                              AND t_project.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), projectName, pageNo * pageSize, pageSize);
    }

    private List<CompanyUser> getPageQueryByCompanyName(String companyName,
                                                        int pageNo,
                                                        int pageSize) {

        companyName = "%" + companyName.trim() + "%";

        return jdbcTemplate.query("""
                                          SELECT t_company_user.* 
                                          FROM t_company_user
                                          LEFT JOIN t_company ON t_company_user.t_company_id=t_company.id
                                          WHERE t_company.name LIKE ?
                                          AND t_company.deleted_at IS NULL
                                          AND t_company_user.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), companyName, pageNo * pageSize, pageSize);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<CompanyUser> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company_user
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_company_id）+获得指定页面数据
     *
     * @param companyId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<CompanyUser> getPageQueryByCompanyId(String companyId,
                                                      int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company_user
                                          WHERE t_company_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), companyId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<CompanyUser> getPageQueryByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company_user
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyUserMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class CompanyUserMapper implements RowMapper<CompanyUser> {
        @Override
        public CompanyUser mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            CompanyUser companyUser = new CompanyUser();
            companyUser.setId(rs.getString("id"));
            companyUser.setCompanyId(rs.getString("t_company_id"));
            companyUser.setUserId(rs.getString("t_user_id"));
            companyUser.setDeletedAt(rs.getTimestamp("deleted_at"));
            return companyUser;
        }
    }

}