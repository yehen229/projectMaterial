package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import camundajar.impl.scala.Int;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialRepositoryImpl implements IProjectMaterialRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Project project;

    public ProjectMaterialRepositoryImpl(JdbcTemplate jdbcTemplate, Project project) {
        this.jdbcTemplate = jdbcTemplate;
        this.project = project;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterial projectMaterial) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                         INSERT INTO t_project_material(id,
                                         t_project_id,
                                         t_company_id,
                                         t_material_id,
                                         t_material_origin_id,
                                         material_count,
                                         material_unit)
                                         VALUES(?,?,?,?,?,?,?)
                                        """,
                                newId,
                                projectMaterial.getProjectId(),
                                projectMaterial.getCompanyId(),
                                projectMaterial.getMaterialId(),
                                projectMaterial.getMaterialOriginId(),
                                projectMaterial.getMaterialCount(),
                                projectMaterial.getMaterialUnit()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterial projectMaterial) {
        if (projectMaterial == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectMaterial.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material
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
    public int update(ProjectMaterial projectMaterial) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material
                                           SET t_project_id=?,
                                           t_company_id=?,
                                           t_material_id=?,
                                           t_material_origin_id=?,
                                           material_count=?,
                                           material_unit=?
                                           WHERE id=?
                                           """,
                                   projectMaterial.getProjectId(),
                                   projectMaterial.getCompanyId(),
                                   projectMaterial.getMaterialId(),
                                   projectMaterial.getMaterialOriginId(),
                                   projectMaterial.getMaterialCount(),
                                   projectMaterial.getMaterialUnit(),
                                   projectMaterial.getId());
    }

    @Override
    public int update(String id,
                      String materialId,
                      BigDecimal materialCount,
                      String materialUnit) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material 
                                           SET t_material_id=?,
                                           material_count=?,
                                           material_unit=?
                                           WHERE id=?
                                           """,
                                   materialId,
                                   materialCount,
                                   materialUnit,
                                   id);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE deleted_at IS NULL    
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectId得到总数量
     */
    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i;
    }
    /**
     * 根据搜索信息得到总数量,不需要companyID
     */
    @Override
    public int getCountBySearchNoCompanyId(String projectId,
                                      String name,
                                      String location,
                                      String itemMark,
                                      String technology,
                                      String installation,
                                      String brand,
                                      String brandPrivate
                                      ) {
        Integer i = 0;
        if(name != ""){
            name = "%" + name.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.name LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class,  projectId, name);
        } else if(location != ""){
            location = "%" + location.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.location LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, location);
        } else if(itemMark != ""){
            itemMark = "%" + itemMark.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.item_mark LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, itemMark);
        } else if(technology != ""){
            technology = "%" + technology.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL And t_material.technology LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, technology);
        } else if(installation != ""){
            installation = "%" + installation.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.installation LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, installation);
        } else if(brand != "") {
            brand = "%" + brand.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                      SELECT DISTINCT count(DISTINCT t_project_material.id) FROM t_project_material
                                                      LEFT JOIN t_project_material_brand_public ON t_project_material.id=t_project_material_brand_public.t_project_material_id
                                                      LEFT JOIN t_brand_public ON t_brand_public.id=t_project_material_brand_public.t_brand_public_id
                                                      LEFT JOIN t_brand ON t_brand.id=t_brand_public.t_brand_id
                                                      WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                                      AND t_project_material_brand_public.deleted_at IS NULL
                                                      AND t_brand_public.deleted_at IS NULL
                                                      AND t_brand.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, brand);
        } else if(brandPrivate != "") {
            brandPrivate = "%" + brandPrivate.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                      SELECT DISTINCT count(DISTINCT t_project_material.id) FROM t_project_material
                                                      LEFT JOIN t_project_material_brand_private ON t_project_material.id=t_project_material_brand_private.t_project_material_id
                                                      LEFT JOIN t_project_brand ON t_project_brand.id=t_project_material_brand_private.t_project_brand_id
                                                      LEFT JOIN t_brand ON t_brand.id=t_project_brand.t_brand_id
                                                      WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                                      AND t_project_material_brand_private.deleted_at IS NULL
                                                      AND t_project_brand.deleted_at IS NULL
                                                      AND t_brand.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, brandPrivate);
        } else {
            i = jdbcTemplate.queryForObject("""
                                                      SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId);
        }
        return i;
    }



    @Override
    public int getCountByProjectIdAndCompanyId(String projectId,
                                               String companyId
                                               ) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_project_id=? AND t_company_id=? AND deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId);


        return i;
    }

    /*
    * 根据搜索信息得到总数量，需要companyID
     */
    @Override
    public int getCountBySearch(String projectId,
                                String companyId,
                                String name,
                                String location,
                                String itemMark,
                                String technology,
                                String installation,
                                String brand,
                                String brandPrivate
    ) {
        Integer i = 0;
        if(name != ""){
            name = "%" + name.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.name LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class,  projectId, companyId, name);
        } else if(location != ""){
            location = "%" + location.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.location LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId, location);
        } else if(itemMark != ""){
            itemMark = "%" + itemMark.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.item_mark LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId, itemMark);
        } else if(technology != ""){
            technology = "%" + technology.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL And t_material.technology LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId, technology);
        } else if(installation != ""){
            installation = "%" + installation.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT t_project_material.id)
                                                        FROM t_project_material
                                                        left join t_material ON t_material.id=t_project_material.t_material_id
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.installation LIKE ?
                                                        AND t_material.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId, installation);
        } else if(brand != "") {
            brand = "%" + brand.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                      SELECT DISTINCT count(DISTINCT t_project_material.id) FROM t_project_material
                                                      LEFT JOIN t_project_material_brand_public ON t_project_material.id=t_project_material_brand_public.t_project_material_id
                                                      LEFT JOIN t_brand_public ON t_brand_public.id=t_project_material_brand_public.t_brand_public_id
                                                      LEFT JOIN t_brand ON t_brand.id=t_brand_public.t_brand_id
                                                      WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                                      AND t_project_material_brand_public.deleted_at IS NULL
                                                      AND t_brand_public.deleted_at IS NULL
                                                      AND t_brand.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId, brand);
        } else if(brandPrivate != "") {
            brandPrivate = "%" + brandPrivate.trim() + "%";
            i = jdbcTemplate.queryForObject("""
                                                      SELECT DISTINCT count(DISTINCT t_project_material.id) FROM t_project_material
                                                      LEFT JOIN t_project_material_brand_private ON t_project_material.id=t_project_material_brand_private.t_project_material_id
                                                      LEFT JOIN t_project_brand ON t_project_brand.id=t_project_material_brand_private.t_project_brand_id
                                                      LEFT JOIN t_brand ON t_brand.id=t_project_brand.t_brand_id
                                                      WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                                      AND t_project_material_brand_private.deleted_at IS NULL
                                                      AND t_project_brand.deleted_at IS NULL
                                                      AND t_brand.deleted_at IS NULL
                                                        """,
                    Integer.class, projectId, companyId, brandPrivate);
        } else {
                i = jdbcTemplate.queryForObject("""
                                                      SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL
                                                        """,
                        Integer.class, projectId, companyId);
        }
        return i;
    }

    @Override
    public int getCountOfReviewedAndApprovedUseMaterialByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        from t_project_material
                                                        left join t_project_review on t_project_review.t_project_id=t_project_material.t_project_id 
                                                        left join t_use_material_brand_select on t_use_material_brand_select.t_project_id=t_project_material.t_project_id
                                                        left join t_project_appearance_review_mode on t_project_appearance_review_mode.t_use_material_brand_select_id=t_use_material_brand_select.id
                                                        left join t_project_appearance_review on t_project_appearance_review.t_project_appearance_review_mode_id = t_project_appearance_review_mode.id
                                                        where t_project_material.t_project_id=?
                                                        and t_project_review.review_result=1 and t_project_review.review_status=2
                                                        and t_project_appearance_review.review_result=1 and t_project_appearance_review.review_status=2
                                                        AND t_project_material.deleted_at IS NULL
                                                        AND t_project_review.deleted_at IS NULL
                                                        AND t_use_material_brand_select.deleted_at IS NULL
                                                        AND t_project_appearance_review_mode.deleted_at IS NULL
                                                        AND t_project_appearance_review.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i;
    }


    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterial getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialMapper(), id);
    }

    @Override
    public ProjectMaterial getByProjectIdAndMaterialOriginId(String projectId,
                                                             String materialOriginId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material 
                                                        WHERE t_project_id=? AND t_material_origin_id=? AND deleted_at IS NULL
                                                        """, Integer.class, projectId,materialOriginId);
        if (i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material
                                                   WHERE t_project_id=? AND t_material_origin_id=?  AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialMapper(), projectId,materialOriginId);
    }

    @Override
    public ProjectMaterial getByProjectIdAndMaterialId(String projectId,
                                                       String materialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material 
                                                        WHERE t_project_id=?  AND t_material_id=? AND deleted_at IS NULL
                                                        """, Integer.class, projectId,materialId);
        if (i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material
                                                   WHERE t_project_id=?  AND t_material_id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialMapper(), projectId,materialId);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectMaterial> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialMapper(), projectId);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterial> getPage(int pageNo,
                                         int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterial> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectMaterial> getPageByProjectId(String projectId,
                                                    int pageNo,
                                                    int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterial> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectMaterial> getPageByProjectIdWithParams(String projectId,
                                                               String name,
                                                               String location,
                                                               String itemMark,
                                                               String technology,
                                                               String installation,
                                                               String brand,
                                                               String brandPrivate,
                                                    int pageNo,
                                                    int pageSize) {
        long totalCount = getCountBySearchNoCompanyId(projectId, name, location, itemMark, technology, installation, brand, brandPrivate);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterial> resultData = getPageQueryByProjectIdWithParams(projectId, name, location, itemMark, technology, installation, brand, brandPrivate, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }



    @Override
    public Page<ProjectMaterial> getReviewedAndApprovedUseMaterialViewPageByProjectId(String projectId,
                                                                                      Integer pageNo,
                                                                                      Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedUseMaterialByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterial> resultData = getPageOfReviewedAndApprovedUseMaterialQueryByProjectId(projectId,
                                                                                                   pageNo - 1,
                                                                                                   pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<ProjectMaterial> getByMaterialId(String materialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_material_id=? AND deleted_at IS NULL
                                                        """,
                Integer.class, materialId);
        if (i == 0)
            return null;

        return  jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material 
                                          WHERE t_material_id=? AND deleted_at IS NULL
                                          """,
                new ProjectMaterialMapper(), materialId);
    }

    @Override
    public Page<ProjectMaterial> getPageByProjectIdAndCompanyId(String projectId,
                                                                String companyId,

                                                                Integer pageNo,
                                                                Integer pageSize) {
            long totalCount = getCountByProjectIdAndCompanyId(projectId, companyId);
            if (totalCount < 1) return new Page<>();
            int startIndex = Page.getStartOfPage(pageNo, pageSize);
            List<ProjectMaterial> resultData = getPageQueryByProjectIdAndCompanyId(projectId, companyId,
                    pageNo - 1,
                    pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectMaterial> getSearchPageByProjectIdAndCompanyId(String projectId,
                                                                String companyId,
                                                                String name,
                                                                String location,
                                                                String itemMark,
                                                                String technology,
                                                                String installation,
                                                                String brand,
                                                                String brandPrivate,
                                                                Integer pageNo,
                                                                Integer pageSize) {
        long totalCount = getCountBySearch(projectId, companyId, name, location, itemMark, technology, installation, brand, brandPrivate);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterial> resultData = getPageQueryBySearch(projectId, companyId,
                    name,
                    location,
                    itemMark,
                    technology,
                    installation,
                    brand,
                    brandPrivate,
                    pageNo - 1,
                    pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterial> getPageQuery(int pageNo,
                                               int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectMaterial> getPageQueryByProjectId(String projectId,
                                                          int pageNo,
                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material
                                          WHERE t_project_id=? AND deleted_at IS NULL 
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialMapper(), projectId, pageNo * pageSize, pageSize);
    }

    private List<ProjectMaterial> getPageQueryByProjectIdAndCompanyId(String projectId,
                                                                      String companyId,
                                                                      int pageNo,
                                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material
                                          WHERE t_project_id=? AND t_company_id=? AND deleted_at IS NULL 
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialMapper(), projectId, companyId, pageNo * pageSize, pageSize);
    }

    private List<ProjectMaterial> getPageQueryBySearch(String projectId,
                                                                      String companyId,
                                                                      String name,
                                                                      String location,
                                                                      String itemMark,
                                                                      String technology,
                                                                      String installation,
                                                                      String brand,
                                                                      String brandPrivate,
                                                                      int pageNo,
                                                                      int pageSize) {
        if(name != "") {
            name = "%" + name.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.name LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, name, pageNo * pageSize, pageSize);
        } else if(location != "") {
            location = "%" + location.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.location LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, location, pageNo * pageSize, pageSize);
        } else if(itemMark != "") {
            itemMark = "%" + itemMark.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.item_mark LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, itemMark, pageNo * pageSize, pageSize);
        } else if(technology != "") {
            technology = "%" + technology.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.technology LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, technology, pageNo * pageSize, pageSize);
        } else if(installation != "") {
            installation = "%" + installation.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_material.installation LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, installation, pageNo * pageSize, pageSize);
        } else if(brand != "") {
            brand = "%" + brand.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_project_material_brand_public ON t_project_material.id=t_project_material_brand_public.t_project_material_id
                                          LEFT JOIN t_brand_public ON t_brand_public.id=t_project_material_brand_public.t_brand_public_id
                                          LEFT JOIN t_brand ON t_brand.id=t_brand_public.t_brand_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ? 
                                            AND t_project_material_brand_public.deleted_at IS NULL
                                            AND t_brand_public.deleted_at IS NULL
                                            AND t_brand.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, brand, pageNo * pageSize, pageSize);
        } else if(brandPrivate != "") {
            brandPrivate = "%" + brandPrivate.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_project_material_brand_private ON t_project_material.id=t_project_material_brand_private.t_project_material_id
                                          LEFT JOIN t_project_brand ON t_project_brand.id=t_project_material_brand_private.t_project_brand_id
                                          LEFT JOIN t_brand ON t_brand.id=t_project_brand.t_brand_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.t_company_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                            AND t_project_material_brand_private.deleted_at IS NULL
                                            AND t_project_brand.deleted_at IS NULL
                                            AND t_brand.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, brandPrivate, pageNo * pageSize, pageSize);
        }
        else {
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material
                                          WHERE t_project_id=? AND t_company_id=? AND deleted_at IS NULL 
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, companyId, pageNo * pageSize, pageSize);
        }

    }
private List<ProjectMaterial> getPageQueryByProjectIdWithParams(String projectId,
                                                                      String name,
                                                                      String location,
                                                                      String itemMark,
                                                                      String technology,
                                                                      String installation,
                                                                      String brand,
                                                                      String brandPrivate,
                                                                      int pageNo,
                                                                      int pageSize) {
        if(name != "") {
            name = "%" + name.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.name LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, name, pageNo * pageSize, pageSize);
        } else if(location != "") {
            location = "%" + location.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.location LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, location, pageNo * pageSize, pageSize);
        } else if(itemMark != "") {
            itemMark = "%" + itemMark.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.item_mark LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, itemMark, pageNo * pageSize, pageSize);
        } else if(technology != "") {
            technology = "%" + technology.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.technology LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, technology, pageNo * pageSize, pageSize);
        } else if(installation != "") {
            installation = "%" + installation.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_material ON t_material.id=t_project_material.t_material_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_material.installation LIKE ? AND t_material.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, installation, pageNo * pageSize, pageSize);
        } else if(brand != "") {
            brand = "%" + brand.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_project_material_brand_public ON t_project_material.id=t_project_material_brand_public.t_project_material_id
                                          LEFT JOIN t_brand_public ON t_brand_public.id=t_project_material_brand_public.t_brand_public_id
                                          LEFT JOIN t_brand ON t_brand.id=t_brand_public.t_brand_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                              AND t_project_material_brand_public.deleted_at IS NULL
                                              AND t_brand_public.deleted_at IS NULL
                                              AND t_brand.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, brand, pageNo * pageSize, pageSize);
        } else if(brandPrivate != "") {
            brandPrivate = "%" + brandPrivate.trim() + "%";
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          LEFT JOIN t_project_material_brand_private ON t_project_material.id=t_project_material_brand_private.t_project_material_id
                                          LEFT JOIN t_project_brand ON t_project_brand.id=t_project_material_brand_private.t_project_brand_id
                                          LEFT JOIN t_brand ON t_brand.id=t_project_brand.t_brand_id
                                          WHERE t_project_material.t_project_id=? AND t_project_material.deleted_at IS NULL AND t_brand.name LIKE ?
                                              AND t_project_material_brand_private.deleted_at IS NULL
                                              AND t_project_brand.deleted_at IS NULL
                                              AND t_brand.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, brandPrivate, pageNo * pageSize, pageSize);
        }
        else {
            return jdbcTemplate.query("""
                                          SELECT DISTINCT t_project_material.* 
                                          FROM t_project_material
                                          WHERE t_project_id=? AND deleted_at IS NULL 
                                          LIMIT ?,?
                                          """,
                    new ProjectMaterialMapper(), projectId, pageNo * pageSize, pageSize);
        }

    }

    private List<ProjectMaterial> getPageOfReviewedAndApprovedUseMaterialQueryByProjectId(String projectId,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        return jdbcTemplate.query("""
                                           SELECT * 
                                          from t_project_material
                                          left join t_project_review on t_project_review.t_project_id=t_project_material.t_project_id 
                                          left join t_use_material_brand_select on t_use_material_brand_select.t_project_id=t_project_material.t_project_id
                                          left join t_project_appearance_review_mode on t_project_appearance_review_mode.t_use_material_brand_select_id=t_use_material_brand_select.id
                                          left join t_project_appearance_review on t_project_appearance_review.t_project_appearance_review_mode_id = t_project_appearance_review_mode.id
                                          where t_project_material.t_project_id=?
                                          and t_project_review.review_result=1 and t_project_review.review_status=2
                                          and t_project_appearance_review.review_result=1 and t_project_appearance_review.review_status=2
                                          AND t_project_material.deleted_at IS NULL
                                          AND t_project_review.deleted_at IS NULL
                                          AND t_use_material_brand_select.deleted_at IS NULL
                                          AND t_project_appearance_review_mode.deleted_at IS NULL
                                          AND t_project_appearance_review.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据projectId和companyId得到记录
     */
    @Override
    public List<ProjectMaterial> getByProjectIdandCompanyId(String projectId, String companyId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material
                                                        WHERE t_project_id=? AND t_company_id=? AND deleted_at IS NULL
                                                        """,
                Integer.class, projectId, companyId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material 
                                          WHERE t_project_id=? AND t_company_id=? AND deleted_at IS NULL
                                          """,
                new ProjectMaterialMapper(), projectId, companyId);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialMapper implements RowMapper<ProjectMaterial> {
        @Override
        public ProjectMaterial mapRow(ResultSet rs,
                                      int rowNum) throws SQLException {
            ProjectMaterial projectMaterial = new ProjectMaterial();
            projectMaterial.setId(rs.getString("id"));
            projectMaterial.setProjectId(rs.getString("t_project_id"));
            projectMaterial.setCompanyId(rs.getString("t_company_id"));
            projectMaterial.setMaterialId(rs.getString("t_material_id"));
            projectMaterial.setMaterialOriginId(rs.getString("t_material_origin_id"));
            projectMaterial.setMaterialCount(rs.getBigDecimal("material_count"));
            projectMaterial.setMaterialUnit(rs.getString("material_unit"));
            projectMaterial.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectMaterial;
        }
    }

}