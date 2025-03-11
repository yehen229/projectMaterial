package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IStatisticalanalysisRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StatisticalanalysisRepositoryImpl implements IStatisticalanalysisRepository {
    private final JdbcTemplate jdbcTemplate;

    public StatisticalanalysisRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate  = jdbcTemplate;
    }

    @Override
    public List<Unpassmaterialmessage> getUnpassmaterialmessage() {
        Integer i = jdbcTemplate.queryForObject("""                                    
                    		SELECT  count(*) FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id  AND  t_project_material.t_project_id=t_project_review_mode.t_project_id AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id  AND t_project_review.review_result=2  AND t_project_review.id  =t_project_review_user.t_project_review_id""",
                Integer.class);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""     
                        SELECT 
                          t_project_material.t_material_id,
                          t_project_material.t_project_id,
                          t_project_material.t_company_id,
                          t_project_review_user.review_content, 
                          t_project_review_user.t_user_id
                         FROM
                                t_project_material,
                                 t_project_review_mode,
                                t_project_review,
                             t_project_review_user
                         WHERE
                             t_project_material.t_company_id = t_project_review_mode.t_company_id 
                             AND  t_project_material.t_project_id = t_project_review_mode.t_project_id
                             AND  t_project_review.t_project_review_mode_id = t_project_review_mode.id  
                             AND t_project_review.review_result   = 2
                             AND t_project_review.id   = t_project_review_user.t_project_review_id;
                                  """,
                new StatisticalanalysisMapper());

    }


    public List<Unpassonlymaterial> getUnpassOnlymaterial() {
        Integer i = jdbcTemplate.queryForObject("""                                                 
                        SELECT DISTINCT count(DISTINCT\s
                   				t_project_material.t_material_id,
                   				t_project_material.t_project_id,
                   				t_project_material.t_company_id)
                   FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                   WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                   	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                   	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                   	AND t_project_review.review_result=2
                   	AND t_project_review.id =t_project_review_user.t_project_review_id
                   """,
                Integer.class);
        if (i == null || i == 0)
            return null;
        return jdbcTemplate.query("""     
                        SELECT DISTINCT\s
                        				t_project_material.t_material_id,
                        				t_project_material.t_project_id,
                        				t_project_material.t_company_id
                        FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                        WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                        	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                        	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                        	AND t_project_review.review_result=2
                        	AND t_project_review.id =t_project_review_user.t_project_review_id
                                  """,
                new StatisticalanalysisMaterilaMapper());

    }

    @Override
    public Page<Unpassonlymaterial> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Unpassonlymaterial> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<OnematerialUnpass> getunpassreviewbyprojectidmaterialid_companyid(String projectid, String companyid) {

        Integer i = jdbcTemplate.queryForObject("""                
                        select count(*)
                      	FROM  t_project_review_mode,t_project_review,t_project_review_user
                       WHERE t_project_review_mode.t_project_id= ?
                       	AND t_project_review_mode.t_company_id= ?
                       	AND t_project_review_mode.id =t_project_review.t_project_review_mode_id
                        	AND t_project_review.review_result=2
                       	AND t_project_review.id =t_project_review_user.t_project_review_id
                                                                                                            	ORDER BY  t_project_review_user.review_datetime ASC
                                                        """,
                Integer.class, projectid,companyid);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                  select t_project_review_user.t_user_id,t_project_review_user.review_content,t_project_review_user.review_datetime,t_project_review_user.id
                        	FROM  t_project_review_mode,t_project_review,t_project_review_user
                        WHERE t_project_review_mode.t_project_id=?
                        	AND t_project_review_mode.t_company_id=?
                        	AND t_project_review_mode.id =t_project_review.t_project_review_mode_id
                        	AND t_project_review.review_result=2
                        	AND t_project_review.id =t_project_review_user.t_project_review_id
                        	ORDER BY  t_project_review_user.review_datetime ASC
                    """,
                new Getunpassreviewbyprojectidmaterialid_companyidMapper(), projectid,companyid);
    }

    private List<Unpassonlymaterial> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                        SELECT DISTINCT\s
                        t_project_material.t_material_id,
                        t_project_material.t_project_id,
                        t_project_material.t_company_id
                        FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                            WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                               	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                               	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                           	AND t_project_review.review_result=2
                               	AND t_project_review.id =t_project_review_user.t_project_review_id                   
                             LIMIT ?,?
                             """, new StatisticalanalysisMaterilaMapper(),pageNo * pageSize, pageSize);
    }
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT DISTINCT count(DISTINCT\s
                   				t_project_material.t_material_id,
                   				t_project_material.t_project_id,
                   				t_project_material.t_company_id)
                                           FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                                           WHERE t_project_material.t_company_id=
                        t_project_review_mode.t_company_id\s
                   	AND  t_project_material.
                        t_project_id=t_project_review_mode.t_project_id
                   	AND
                        t_project_review.t_project_review_mode_id=
                        t_project_review_mode.id
                   	AND t_project_review.review_result=2
                   	AND t_project_review.id =t_project_review_user.t_project_review_id
                                                        """,
                Integer.class);
        return i == null ? 0 : i;
    }


    private static final class StatisticalanalysisMapper implements RowMapper<Unpassmaterialmessage> {
        @Override
        public Unpassmaterialmessage mapRow(ResultSet rs,
                                            int rowNum) throws SQLException {
            Unpassmaterialmessage unpassmaterialmessage = new Unpassmaterialmessage();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            unpassmaterialmessage.setCompanyId(rs.getString("t_company_id"));
            unpassmaterialmessage.setReviewConternt(rs.getString("review_content"));
            unpassmaterialmessage.setUserId(rs.getString("t_user_id"));
            return unpassmaterialmessage;
        }
    }
    private static final class StatisticalanalysisMaterilaMapper implements RowMapper<Unpassonlymaterial> {
        @Override
        public Unpassonlymaterial mapRow(ResultSet rs,
                                            int rowNum) throws SQLException {
            Unpassonlymaterial unpassmaterialmessage = new Unpassonlymaterial();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            unpassmaterialmessage.setCompanyId(rs.getString("t_company_id"));
            return unpassmaterialmessage;
        }
    }

    private static final class Getunpassreviewbyprojectidmaterialid_companyidMapper implements RowMapper<OnematerialUnpass> {
        @Override
        public OnematerialUnpass mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            OnematerialUnpass onematerialUnpass = new OnematerialUnpass();

            onematerialUnpass.setUserid(rs.getString("t_user_id"));
            onematerialUnpass.setReviewcotent(rs.getString("review_content"));
            onematerialUnpass.setId(rs.getString("id"));

            return onematerialUnpass;
        }
    }


}