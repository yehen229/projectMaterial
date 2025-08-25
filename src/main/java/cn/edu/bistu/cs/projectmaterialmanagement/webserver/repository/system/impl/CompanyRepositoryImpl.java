package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.ICompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class CompanyRepositoryImpl implements ICompanyRepository {
    private final JdbcTemplate jdbcTemplate;

    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean ExistCompany(Company company){
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company
                                                        WHERE name=? AND company_type=?
                                                        """,
                Integer.class,company.getName(),company.getCompanyType());
        if( i > 0){
            return true;
        }
        return false;
    }

    /**
     * insert
     */
    @Override
    public String add(Company company) {
        String newId = GUID.getGUID();
        if (ExistCompany(company))
            return null;
        if (jdbcTemplate.update("""
                                        INSERT INTO t_company(id,
                                        name,
                                        company_type,
                                        note)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                company.getName(),
                                company.getCompanyType(),
                                company.getNote()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(Company company) {
        if (company == null) return 0;
        // 第一条更新公司列表，第二条更新用户公司连接表，第三条更新用户表
        int i = jdbcTemplate.update("""
                                           UPDATE t_company
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                new Date(),
                                company.getId());
        int j = jdbcTemplate.update("""
                                           UPDATE t_company_user 
                                           SET deleted_at=? 
                                           WHERE t_company_id=?
                                           """,
                                new Date(),
                                company.getId());
        int k = jdbcTemplate.update("""
                                           UPDATE t_user 
                                           SET deleted_at=? 
                                           WHERE id IN (SELECT t_user_id FROM  t_company_user WHERE t_company_id=?);
                                           """,
                                new Date(),
                                company.getId());
        if (i > 0 && j > 0 && k > 0)
            return 1;
        return 0;
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_company
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * update
     */
    @Override
    public int update(Company company) {
        return jdbcTemplate.update("""
                                           UPDATE t_company
                                           SET name=?,
                                           company_type=?,
                                           note=?
                                           WHERE id=?
                                           """,
                                   company.getName(),
                                   company.getCompanyType(),
                                   company.getNote(),
                                   company.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByLikeName(String companyName) {

        companyName = "%" + companyName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company                       
                                                        WHERE t_company.name LIKE ?  AND t_company.deleted_at IS NULL                              
                                                        """,
                                                Integer.class, companyName);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByLikeCompanyType(String companyType) {
        companyType = "%" + companyType.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company                       
                                                        WHERE t_company.company_type LIKE ?  AND t_company.deleted_at IS NULL                              
                                                        """,
                                                Integer.class, companyType);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public Company getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_company
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new CompanyMapper(), id);
    }

    @Override
    public Company getByName(String name) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_company 
                                                        WHERE name=? AND deleted_at IS NULL
                                                        """, Integer.class, name);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_company
                                                   WHERE name=? AND deleted_at IS NULL
                                                   """,
                                           new CompanyMapper(), name);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Company> getPage(int pageNo,
                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Company> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Company> getPageByCompanyName(String companyName,
                                              int pageNo,
                                              int pageSize) {
        long totalCount = getCountByLikeName(companyName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Company> resultData = getPageQueryByCompanyName(companyName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Company> getPageByCompanyType(String companyType,
                                              int pageNo,
                                              int pageSize) {
        long totalCount = getCountByLikeCompanyType(companyType);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Company> resultData = getPageQueryByCompanyType(companyType, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<Company> getAllCompanyList() {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company
                                          WHERE deleted_at IS NULL
                                          """,
                                  new CompanyMapper());
    }

    @Override
    public List<Company> getFactoryList() {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company
                                          WHERE deleted_at IS NULL
                                          AND company_type='厂家'
                                          """,
                                  new CompanyMapper());
    }

    @Override
    public List<Company> getCompanyListByType(String companyType) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company
                                          WHERE company_type = ? AND deleted_at IS NULL
                                          """,
                                  new CompanyMapper(), companyType);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<Company> getPageQuery(int pageNo,
                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyMapper(), pageNo * pageSize, pageSize);
    }

    private List<Company> getPageQueryByCompanyName(String companyName,
                                                    int pageNo,
                                                    int pageSize) {
        companyName = "%" + companyName.trim() + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company
                                          WHERE name LIKE ? AND  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyMapper(), companyName, pageNo * pageSize, pageSize);
    }

    private List<Company> getPageQueryByCompanyType(String companyType,
                                                    int pageNo,
                                                    int pageSize) {
        companyType = "%" + companyType.trim() + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_company
                                          WHERE company_type LIKE  ? AND  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new CompanyMapper(), companyType, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    public static final class CompanyMapper implements RowMapper<Company> {
        @Override
        public Company mapRow(ResultSet rs,
                              int rowNum) throws SQLException {
            Company company = new Company();
            company.setId(rs.getString("id"));
            company.setName(rs.getString("name"));
            company.setCompanyType(rs.getString("company_type"));
            company.setNote(rs.getString("note"));
            company.setDeletedAt(rs.getTimestamp("deleted_at"));
            return company;
        }
    }

}