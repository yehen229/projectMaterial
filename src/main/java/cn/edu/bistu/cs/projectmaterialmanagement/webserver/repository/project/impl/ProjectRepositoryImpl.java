package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl.ProjectServiceImpl;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(Project project) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                        INSERT INTO t_project(id,
                        t_user_id,
                        name,
                        location,
                        total_tax_included,
                        total_tax_not_included,
                        building_area_above_ground,
                        building_area_under_ground,
                        t_company_construction_id,
                                            
                        note,
                        create_datetime)
                        VALUES(?,?,?,?,?,?,?,?,?,?,?)
                        """,
                newId,
                project.getUserId(),
                project.getName(),
                project.getLocation(),
                project.getTotalTaxIncluded(),
                project.getTotalTaxNotIncluded(),
                project.getBuildingAreaAboveGround(),
                project.getBuildingAreaUnderGround(),
                project.getCompanyConstructionId(),

                project.getNote(),
                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(Project project) {
        if (project == null) return 0;
        return jdbcTemplate.update("""
                        UPDATE t_project
                        SET deleted_at=? 
                        WHERE id=?
                        """,
                new Date(),
                project.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                        UPDATE t_project
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
                        UPDATE t_project
                        SET deleted_at=? 
                        WHERE t_user_id=?
                        """,
                new Date(),
                userId);

    }

    /**
     * 根据companyConstructionId删除记录
     */
    @Override
    public int deleteByCompanyConstructionId(String companyConstructionId) {
        return jdbcTemplate.update("""
                        UPDATE t_project
                        SET deleted_at=? 
                        WHERE t_company_construction_id=?
                        """,
                new Date(),
                companyConstructionId);

    }

//    @Override
//    public int deleteByCompanyDesignId(String companyDesignId) {
//        return 0;
//    }

    /**
     * 根据companyDesignId删除记录
     */
//    @Override
//    public int deleteByCompanyDesignId(String companyDesignId) {
//        return jdbcTemplate.update("""
//                        UPDATE t_project
//                        SET deleted_at=?
//                        WHERE t_company_design_id=?
//                        """,
//                new Date(),
//                companyDesignId);
//
//    }

    /**
     * 更新project
     * 注意：create_datetime不能更新，t_user_id不能更新
     */
    @Override
    public int update(Project project) {
        return jdbcTemplate.update("""
                        UPDATE t_project
                        SET 
                        name=?,
                        location=?,
                        total_tax_included=?,
                        total_tax_not_included=?,
                        building_area_above_ground=?,
                        building_area_under_ground=?,
                        t_company_construction_id=?,
                               
                        note=?
                        WHERE id=?
                        """,

                project.getName(),
                project.getLocation(),
                project.getTotalTaxIncluded(),
                project.getTotalTaxNotIncluded(),
                project.getBuildingAreaAboveGround(),
                project.getBuildingAreaUnderGround(),
                project.getCompanyConstructionId(),

                project.getNote(),
                project.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
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
                        FROM t_project
                        WHERE t_user_id=? AND deleted_at IS NULL
                        """,
                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键CompanyConstructionId得到总数量
     */
    @Override
    public int getCountByCompanyConstructionId(String companyConstructionId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE t_company_construction_id=? AND deleted_at IS NULL
                        """,
                Integer.class, companyConstructionId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*)
                        FROM t_project
                        LEFT JOIN t_project_company ON t_project.id = t_project_company.t_project_id
                        WHERE t_project_company.t_general_contractor_company_id=?
                        AND t_project.id NOT IN (
                        SELECT t_project_id
                        FROM t_project_end
                        WHERE t_project_end.deleted_at IS NULL
                        )
                        AND t_project.deleted_at IS NULL
                        AND t_project_company.deleted_at IS NULL
                        """,
                Integer.class, generalContractorCompanyId);
        return i == null ? 0 : i;
    }

//    @Override
//    public int getCountByCompanyDesignId(String companyDesignId) {
//        return 0;
//    }

    /**
     * 根据外键CompanyDesignId得到总数量
     */
//    @Override
//    public int getCountByCompanyDesignId(String companyDesignId) {
//        Integer i = jdbcTemplate.queryForObject("""
//                        SELECT count(*)
//                        FROM t_project
//                        WHERE t_company_design_id=? AND deleted_at IS NULL
//                        """,
//                Integer.class, companyDesignId);
//        return i == null ? 0 : i;
//    }

    /**
     * 根据id得到记录
     */
    @Override
    public Project getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                SELECT count(*) 
                FROM t_project 
                WHERE id=? AND deleted_at IS NULL
                """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                        SELECT * 
                        FROM t_project
                        WHERE id=? AND deleted_at IS NULL
                        """,
                new ProjectMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<Project> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE t_user_id=? AND deleted_at IS NULL
                        """,
                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project 
                        WHERE t_user_id=? AND deleted_at IS NULL
                        """,
                new ProjectMapper(), userId);
    }


    /**
     * 根据companyConstructionId得到记录
     */
    @Override
    public List<Project> getByCompanyConstructionId(String companyConstructionId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE t_company_construction_id=? AND deleted_at IS NULL
                        """,
                Integer.class, companyConstructionId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project  
                        WHERE t_company_construction_id=? AND deleted_at IS NULL
                        """,
                new ProjectMapper(), companyConstructionId);
    }

//    @Override
//    public List<Project> getByCompanyDesignId(String companyDesignId) {
//        return null;
//    }

    /**
     * 根据companyDesignId得到记录
     */
//    @Override
//    public List<Project> getByCompanyDesignId(String companyDesignId) {
//        Integer i = jdbcTemplate.queryForObject("""
//                        SELECT count(*)
//                        FROM t_project
//                        WHERE t_company_design_id=? AND deleted_at IS NULL
//                        """,
//                Integer.class, companyDesignId);
//        if (i == null || i == 0)
//            return null;
//
//        return jdbcTemplate.query("""
//                        SELECT *
//                        FROM t_project
//                        WHERE t_company_design_id=? AND deleted_at IS NULL
//                        """,
//                new ProjectMapper(), companyDesignId);
//    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Project> getPage(int pageNo,
                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<Project> getPageByUserId(String userId,
                                         int pageNo,
                                         int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param companyConstructionId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<Project> getPageByCompanyConstructionId(String companyConstructionId,
                                                        int pageNo,
                                                        int pageSize) {
        long totalCount = getCountByCompanyConstructionId(companyConstructionId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByCompanyConstructionId(companyConstructionId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param companyDesignId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    @Override
    public Page<Project> getPageByCompanyDesignId(String companyDesignId,
                                                  int pageNo,
                                                  int pageSize) {
//        long totalCount = getCountByCompanyDesignId(companyDesignId);
        long totalCount = 0;
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByCompanyDesignId(companyDesignId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Project> getPageByProjectId(String projectId,
                                            int pageNo,
                                            int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Project> getPageByProjectName(String projectName,
                                              int pageNo,
                                              int pageSize) {
        long totalCount = getCountByProjectName(projectName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByProjectName(projectName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    public Page<Project> getPageByProjectLocation(String projectLocation, int pageNo, int pageSize) {
        long totalCount = getCountByProjectLocation(projectLocation); // 这个使用的是 project 的 location
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByProjectLocation(projectLocation, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    public Page<Project> getPageByParams(String name,
                                         String location,
                                         BigDecimal totalTaxIncluded,
                                         BigDecimal totalTaxNotIncluded,
                                         BigDecimal buildingAreaAboveGround,
                                         BigDecimal buildingAreaUnderGround,
                                         String companyConstructionId,
                                         String companyDesignId,
                                         String note,
                                         Date createDatetime,
                                         Date endDatetime,
                                         int pageNo,
                                         int pageSize) {
        long totalCount = getCountByParams(name, location, totalTaxIncluded, totalTaxNotIncluded, buildingAreaAboveGround, buildingAreaUnderGround, companyConstructionId, companyDesignId, note, createDatetime, endDatetime); // 这个使用的是 project 的 location
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByParams(name, location, totalTaxIncluded, totalTaxNotIncluded, buildingAreaAboveGround, buildingAreaUnderGround, companyConstructionId, companyDesignId, note, createDatetime, endDatetime, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }



    private List<Project> getPageQueryByProjectLocation(String projectLocation, int pageNo, int pageSize) {
        String likeProjectLocation = "%" + projectLocation + "%";
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE location like ? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), likeProjectLocation, pageNo * pageSize, pageSize);
    }

    //
    private List<Project> getPageQueryByParams(String name,
                                               String location,
                                               BigDecimal totalTaxIncluded,
                                               BigDecimal totalTaxNotIncluded,
                                               BigDecimal buildingAreaAboveGround,
                                               BigDecimal buildingAreaUnderGround,
                                               String companyConstructionId,
                                               String companyDesignId,
                                               String note,
                                               Date createDatetime,
                                               Date endDatetime,
                                               int pageNo,
                                               int pageSize) {
        String likeProjectLocation = "%" + location + "%";
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE location like ? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), likeProjectLocation, pageNo * pageSize, pageSize);
    }

    @Override
    public Page<Project> getPageByKeyword(String keyword,
                                          int pageNo,
                                          int pageSize) {
        long totalCount = getCountByKeyword(keyword);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<Project> resultData = getPageQueryByKeyword(keyword, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Project> getNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId,
                                                                          Integer pageNo,
                                                                          Integer pageSize) {
        long totalCount = getCountNotEndedProjectPageOfGeneralContractorCompany(generalContractorCompanyId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryNotEndedProjectPageOfGeneralContractorCompany(generalContractorCompanyId,
                pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    private List<Project> getPageQueryNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId,
                                                                                    int pageNo,
                                                                                    int pageSize) {

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        LEFT JOIN t_project_company ON t_project.id = t_project_company.t_project_id
                                      WHERE t_project_company.t_general_contractor_company_id=?
                                      AND t_project.id NOT IN (
                                      SELECT t_project_id
                                      FROM t_project_end
                                      WHERE t_project_end.deleted_at IS NULL
                                      )
                                      AND t_project.deleted_at IS NULL
                                      AND t_project_company.deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), generalContractorCompanyId, pageNo * pageSize, pageSize);
    }

    private int getCountByAdvancedSearch(String name, String location, double withTaxMin, double withTaxMax, double outTaxMin, double outTaxMax, double areaAboveGroundMin, double areaAboveGroundMax, double areaUnderGroundMin, double areaUnderGroundMax, String companyConstructionName, String companyDesignName, Date startDatetime, Date endDatetime) {
        String likeName = "%" + name + "%";
        String likeLocation = "%" + location + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*)
                        FROM t_project
                        WHERE name LIKE ?
                        AND location LIKE ?
                        AND total_tax_included >= ?
                        AND total_tax_included <= ?
                        AND total_tax_not_included >= ?
                        AND total_tax_not_included <= ?
                        AND building_area_above_ground >= ?
                        AND building_area_above_ground <= ?
                        AND building_area_under_ground >= ?
                        AND building_area_under_ground <= ?
                        AND t_company_construction_id = ?
                        AND create_datetime >= ?
                        AND create_datetime <= ?
                        AND deleted_at IS NULL
                        """,
                Integer.class, likeName, likeLocation, withTaxMin, withTaxMax, outTaxMin, outTaxMax, areaAboveGroundMin, areaAboveGroundMax, areaUnderGroundMin, areaUnderGroundMax, companyConstructionName, companyDesignName, startDatetime, endDatetime);
        log.info("符合目标数为" + i);
        return i == null ? 0 : i;
    }

    @Override
    public Page<Project> getPageByAdvancedSearch(String name, String location, double withTaxMin, double withTaxMax, double outTaxMin, double outTaxMax, double areaAboveGroundMin, double areaAboveGroundMax, double areaUnderGroundMin, double areaUnderGroundMax, String companyConstructionName, String companyDesignName, Date startDatetime, Date endDatetime, Integer pageNo, Integer pageSize) {
        long totalCount = getCountByAdvancedSearch(name, location, withTaxMin, withTaxMax, outTaxMin, outTaxMax, areaAboveGroundMin, areaAboveGroundMax, areaUnderGroundMin, areaUnderGroundMax, companyConstructionName, companyDesignName, startDatetime, endDatetime);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Project> resultData = getPageQueryByAdvancedSearch(name, location, withTaxMin, withTaxMax, outTaxMin, outTaxMax, areaAboveGroundMin, areaAboveGroundMax, areaUnderGroundMin, areaUnderGroundMax, companyConstructionName, companyDesignName, startDatetime, endDatetime, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<Project> getPageQueryByAdvancedSearch(String name, String location, double withTaxMin, double withTaxMax, double outTaxMin, double outTaxMax, double areaAboveGroundMin, double areaAboveGroundMax, double areaUnderGroundMin, double areaUnderGroundMax, String companyConstructionName, String companyDesignName, Date startDatetime, Date endDatetime, Integer pageNo, Integer pageSize) {
        String likeName = "%" + name + "%";
        String likeLocation = "%" + location + "%";
        return jdbcTemplate.query("""
                        SELECT *
                        FROM t_project
                        WHERE name LIKE ?
                        AND location LIKE ?
                        AND total_tax_included >= ?
                        AND total_tax_included <= ?
                        AND total_tax_not_included >= ?
                        AND total_tax_not_included <= ?
                        AND building_area_above_ground >= ?
                        AND building_area_above_ground <= ?
                        AND building_area_under_ground >= ?
                        AND building_area_under_ground <= ?
                        AND t_company_construction_id = ?
                        AND create_datetime >= ?
                        AND create_datetime <= ?
                        AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), likeName, likeLocation, withTaxMin, withTaxMax, outTaxMin, outTaxMax, areaAboveGroundMin, areaAboveGroundMax, areaUnderGroundMin, areaUnderGroundMax, companyConstructionName, companyDesignName, startDatetime, endDatetime, pageNo * pageSize, pageSize
        );
    }

    @Override
    public List<Project> getAllList() {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE deleted_at IS NULL
                        """,
                Integer.class);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project 
                        WHERE deleted_at IS NULL
                        """,
                new ProjectMapper());
    }

    /**
     * 根据主键id得到符合条件的项目数量（0||1）
     */
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE id=? AND deleted_at IS NULL
                        """,
                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    public int getCountByProjectName(String projectName) {
        projectName = "%" + projectName + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE name Like ? AND deleted_at IS NULL
                        """,
                Integer.class, projectName);
        return i == null ? 0 : i;
    }

    //地点查询

    public int getCountByProjectLocation(String projectLocation) {

        String likeProjectLocation = "%" + projectLocation + "%";

        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*)
                        FROM t_project
                        WHERE location like ? AND deleted_at IS NULL
                        """,
                Integer.class, likeProjectLocation);
        return i == null ? 0 : i;
    }

    //参数高级搜索,暂未完善

    public int getCountByParams(String name,
                                String location,
                                BigDecimal totalTaxIncluded,
                                BigDecimal totalTaxNotIncluded,
                                BigDecimal buildingAreaAboveGround,
                                BigDecimal buildingAreaUnderGround,
                                String companyConstructionId,
                                String companyDesignId,
                                String note,
                                Date createDatetime,
                                Date endDatetime) {

        String likeProjectLocation = "%" + location + "%";

        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*)
                        FROM t_project
                        WHERE location like ? AND deleted_at IS NULL
                        """,
                Integer.class, likeProjectLocation);
        return i == null ? 0 : i;
    }

    // 关键字查询
    public int getCountByKeyword(String keyword) {
        // 在 keyword 前后添加百分号以实现模糊查询
        String likeKeyword = "%" + keyword + "%";

        // 使用新的 likeKeyword 替换原来的 keyword
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_project
                        WHERE name LIKE ? AND deleted_at IS NULL
                        """,
                Integer.class, likeKeyword);
        return i == null ? 0 : i;
    }

    // 高级搜索
    public int getCountByAdvancedSearch(String name, String location) {
        name = "%" + name + "%";
        location = "%" + location + "%";

        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*)
                        FROM t_project
                        WHERE name LIKE ?
                        AND location LIKE ?
                        AND deleted_at is null
                        """,
                Integer.class, name, location);
        return i == null ? 0 : i;
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<Project> getPageQuery(int pageNo,
                                       int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<Project> getPageQueryByUserId(String userId,
                                               int pageNo,
                                               int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE t_user_id=? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_company_construction_id）+获得指定页面数据
     *
     * @param companyConstructionId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    private List<Project> getPageQueryByCompanyConstructionId(String companyConstructionId,
                                                              int pageNo,
                                                              int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE t_company_construction_id=? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), companyConstructionId, pageNo * pageSize, pageSize);
    }

    //
//    /**
//     * 根据外键（t_company_design_id）+获得指定页面数据
//     *
//     * @param companyDesignId
//     * @param pageNo          页号，从1开始
//     * @param pageSize        每页的记录数
//     */
    private List<Project> getPageQueryByCompanyDesignId(String companyDesignId,
                                                        int pageNo,
                                                        int pageSize) {
        return jdbcTemplate.query("""
                         SELECT *
                         FROM t_project
                        where deleted_at IS NULL
                         LIMIT ?,?
                         """,
                new ProjectMapper(), pageNo * pageSize, pageSize);
    }

    private List<Project> getPageQueryByProjectId(String projectId,
                                                  int pageNo,
                                                  int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE id=? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), projectId, pageNo * pageSize, pageSize);
    }

    private List<Project> getPageQueryByProjectName(String projectName,
                                                    int pageNo,
                                                    int pageSize) {
        projectName = "%" + projectName + "%";
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE name LIKE ? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), projectName, pageNo * pageSize, pageSize);
    }

    private List<Project> getPageQueryByKeyword(String keyword,
                                                int pageNo,
                                                int pageSize) {
        String likeKeyword = "%" + keyword + "%";
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_project
                        WHERE name like ? AND deleted_at IS NULL
                        LIMIT ?,?
                        """,
                new ProjectMapper(), likeKeyword, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet rs,
                              int rowNum) throws SQLException {
            Project project = new Project();
            project.setId(rs.getString("id"));
            project.setUserId(rs.getString("t_user_id"));
            project.setName(rs.getString("name"));
            project.setLocation(rs.getString("location"));
            project.setTotalTaxIncluded(rs.getBigDecimal("total_tax_included"));
            project.setTotalTaxNotIncluded(rs.getBigDecimal("total_tax_not_included"));
            project.setBuildingAreaAboveGround(rs.getBigDecimal("building_area_above_ground"));
            project.setBuildingAreaUnderGround(rs.getBigDecimal("building_area_under_ground"));
            project.setCompanyConstructionId(rs.getString("t_company_construction_id"));
//            project.setCompanyDesignId(rs.getString("t_company_design_id"));
            project.setNote(rs.getString("note"));
            project.setCreateDatetime(rs.getTimestamp("create_datetime"));
            project.setEndDatetime(rs.getTimestamp("end_datetime"));
            project.setDeletedAt(rs.getTimestamp("deleted_at"));
            return project;
        }
    }
}