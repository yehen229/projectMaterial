package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectCompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectCompanyRepositoryImpl implements IProjectCompanyRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectCompanyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectCompany projectCompany) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_company(id,
                                        t_user_id,
                                        t_project_id,
                                        t_general_contractor_company_id,
                                        t_supervision_company_id,
                                        create_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectCompany.getUserId(),
                                projectCompany.getProjectId(),
                                projectCompany.getGeneralContractorCompanyId(),
                                projectCompany.getSupervisionCompanyId(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectCompany projectCompany) {
        if (projectCompany == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectCompany.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * 根据totalPackageCompanyId删除记录
     */
    @Override
    public int deleteByGeneralContractorCompanyId(String totalPackageCompanyId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET deleted_at=? 
                                           WHERE t_general_contractor_company_id=?
                                           """,
                                   new Date(),
                                   totalPackageCompanyId);

    }

    /**
     * 根据supervisionCompanyId删除记录
     */
    @Override
    public int deleteBySupervisionCompanyId(String supervisionCompanyId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET deleted_at=? 
                                           WHERE t_supervision_company_id=?
                                           """,
                                   new Date(),
                                   supervisionCompanyId);

    }

    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
                                   new Date(),
                                   projectId);
    }

    /**
     * update
     */
    @Override
    public int update(ProjectCompany projectCompany) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_company
                                           SET t_user_id=?,
                                           t_project_id=?,
                                           t_general_contractor_company_id=?,
                                           t_supervision_company_id=?
                                           WHERE id=?
                                           """,
                                   projectCompany.getUserId(),
                                   projectCompany.getProjectId(),
                                   projectCompany.getGeneralContractorCompanyId(),
                                   projectCompany.getSupervisionCompanyId(),
                                   projectCompany.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE deleted_at IS NULL
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
                                                        FROM t_project_company
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键TotalPackageCompanyId得到总数量
     */
    @Override
    public int getCountByGeneralContractorCompanyId(String totalPackageCompanyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE t_general_contractor_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, totalPackageCompanyId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键SupervisionCompanyId得到总数量
     */
    @Override
    public int getCountBySupervisionCompanyId(String supervisionCompanyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE t_supervision_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, supervisionCompanyId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectCompany getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_company
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectCompanyMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectCompany> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectCompanyMapper(), userId);
    }

    /**
     * 根据totalPackageCompanyId得到记录
     */
    @Override
    public List<ProjectCompany> getByGeneralContractorCompanyId(String totalPackageCompanyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE t_general_contractor_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, totalPackageCompanyId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company 
                                          WHERE t_general_contractor_company_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectCompanyMapper(), totalPackageCompanyId);
    }

    /**
     * 根据supervisionCompanyId得到记录
     */
    @Override
    public List<ProjectCompany> getBySupervisionCompanyId(String supervisionCompanyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company
                                                        WHERE t_supervision_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, supervisionCompanyId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company 
                                          WHERE t_supervision_company_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectCompanyMapper(), supervisionCompanyId);
    }

    @Override
    public ProjectCompany getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_company 
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """, Integer.class, projectId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_company
                                                   WHERE t_project_id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectCompanyMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPage(int pageNo,
                                        int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectCompany> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectCompany> getPageByUserId(String userId,
                                                int pageNo,
                                                int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectCompany> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param totalPackageCompanyId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPageByGeneralContractorCompanyId(String totalPackageCompanyId,
                                                                    int pageNo,
                                                                    int pageSize) {
        long totalCount = getCountByGeneralContractorCompanyId(totalPackageCompanyId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectCompany> resultData = getPageQueryByGeneralContractorCompanyId(totalPackageCompanyId, pageNo - 1,
                                                                                   pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param supervisionCompanyId
     * @param pageNo               页号，从1开始
     * @param pageSize             每页的记录数
     */
    @Override
    public Page<ProjectCompany> getPageBySupervisionCompanyId(String supervisionCompanyId,
                                                              int pageNo,
                                                              int pageSize) {
        long totalCount = getCountBySupervisionCompanyId(supervisionCompanyId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectCompany> resultData = getPageQueryBySupervisionCompanyId(supervisionCompanyId, pageNo - 1,
                                                                             pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectCompany> getPageByProjectId(String projectId,
                                                   int pageNo,
                                                   int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectCompany> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectCompany> getPageQuery(int pageNo,
                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectCompanyMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectCompany> getPageQueryByUserId(String userId,
                                                      int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectCompanyMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_total_package_company_id）+获得指定页面数据
     *
     * @param totalPackageCompanyId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    private List<ProjectCompany> getPageQueryByGeneralContractorCompanyId(String totalPackageCompanyId,
                                                                          int pageNo,
                                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company
                                          WHERE t_general_contractor_company_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectCompanyMapper(), totalPackageCompanyId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_supervision_company_id）+获得指定页面数据
     *
     * @param supervisionCompanyId
     * @param pageNo               页号，从1开始
     * @param pageSize             每页的记录数
     */
    private List<ProjectCompany> getPageQueryBySupervisionCompanyId(String supervisionCompanyId,
                                                                    int pageNo,
                                                                    int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company
                                          WHERE t_supervision_company_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectCompanyMapper(), supervisionCompanyId, pageNo * pageSize, pageSize);
    }

    private List<ProjectCompany> getPageQueryByProjectId(String projectId,
                                                         int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_company
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectCompanyMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectCompanyMapper implements RowMapper<ProjectCompany> {
        @Override
        public ProjectCompany mapRow(ResultSet rs,
                                     int rowNum) throws SQLException {
            ProjectCompany projectCompany = new ProjectCompany();
            projectCompany.setId(rs.getString("id"));
            projectCompany.setUserId(rs.getString("t_user_id"));
            projectCompany.setProjectId(rs.getString("t_project_id"));
            projectCompany.setGeneralContractorCompanyId(rs.getString("t_general_contractor_company_id"));
            projectCompany.setSupervisionCompanyId(rs.getString("t_supervision_company_id"));
            projectCompany.setCreateDatetime(rs.getTimestamp("create_datetime"));
            projectCompany.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectCompany;
        }
    }

}