package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectDesignCompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectDesignCompanyRepositoryImpl implements IProjectDesignCompanyRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectDesignCompanyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(ProjectDesignCompany projectDesignCompany) {
        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_design_company(id,
                                        t_project_id,
                                        t_design_company_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectDesignCompany.getProjectId(),
                                projectDesignCompany.getDesignCompanyId()) > 0)
            return newId;
        return null;
    }

    @Override
    public int delete(ProjectDesignCompany projectDesignCompany) {
        if (projectDesignCompany == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_design_company
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectDesignCompany.getId());
    }

    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_design_company
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_design_company
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """, new Date(),
                                   projectId);
    }

    @Override
    public int deleteByCompanyDesignId(String companyDesignId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_design_company
                                           SET deleted_at=? 
                                           WHERE t_design_company_id=?
                                           """, new Date(),
                                   companyDesignId);
    }

    @Override
    public int update(ProjectDesignCompany projectDesignCompany) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_design_company
                                           SET t_project_id=?,
                                           t_design_company_id=?
                                           WHERE id=?
                                           """,
                                   projectDesignCompany.getProjectId(),
                                   projectDesignCompany.getDesignCompanyId(),
                                   projectDesignCompany.getId());
    }

    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByCompanyDesignId(String companyDesignId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company
                                                        WHERE t_design_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, companyDesignId);
        return i == null ? 0 : i;
    }

    @Override
    public ProjectDesignCompany getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company 
                                                        WHERE id=?
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_design_company 
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectDesignCompanyMapper(), id);
    }

    @Override
    public ProjectDesignCompany getByProjectIdAndDesignCompanyId(String projectId,
                                                                 String companyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company 
                                                        WHERE t_project_id=? AND t_design_company_id=? AND deleted_at IS NULL
                                                        """, Integer.class, projectId, companyId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_design_company
                                                   WHERE t_project_id=? AND t_design_company_id=?
                                                   """,
                                           new ProjectDesignCompanyMapper(), projectId, companyId);
    }

    @Override
    public List<ProjectDesignCompany> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_design_company 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectDesignCompanyMapper(), projectId);
    }

    @Override
    public List<ProjectDesignCompany> getByCompanyDesignId(String companyDesignId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_design_company
                                                        WHERE t_design_company_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, companyDesignId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_design_company 
                                          WHERE t_design_company_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectDesignCompanyMapper(), companyDesignId);
    }

    @Override
    public Page<ProjectDesignCompany> getPage(int pageNo,
                                              int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectDesignCompany> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectDesignCompany> getPageByCompanyDesignId(String companyDesignId,
                                                               int pageNo,
                                                               int pageSize) {
        long totalCount = getCountByCompanyDesignId(companyDesignId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectDesignCompany> resultData = getPageQueryByCompanyDesignId(companyDesignId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectDesignCompany> getPageByProjectId(String projectId,
                                                         int pageNo,
                                                         int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectDesignCompany> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    private List<ProjectDesignCompany> getPageQuery(int pageNo,
                                                    int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_design_company
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectDesignCompanyMapper(), pageNo * pageSize, pageSize);
    }

    private List<ProjectDesignCompany> getPageQueryByCompanyDesignId(String companyDesignId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_design_company
                                          WHERE t_design_company_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectDesignCompanyMapper(), companyDesignId, pageNo * pageSize, pageSize);
    }

    private List<ProjectDesignCompany> getPageQueryByProjectId(String projectId,
                                                               int pageNo,
                                                               int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_design_company
                                          WHERE t_design_company_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectDesignCompanyMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectDesignCompanyMapper implements RowMapper<ProjectDesignCompany> {
        @Override
        public ProjectDesignCompany mapRow(ResultSet rs,
                                           int rowNum) throws SQLException {
            ProjectDesignCompany projectDesignCompany = new ProjectDesignCompany();
            projectDesignCompany.setId(rs.getString("id"));
            projectDesignCompany.setProjectId(rs.getString("t_project_id"));
            projectDesignCompany.setDesignCompanyId(rs.getString("t_design_company_id"));
            projectDesignCompany.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectDesignCompany;
        }
    }
}
