package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialSelectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BuyMaterialSelectRepositoryImpl implements IBuyMaterialSelectRepository {
    private final JdbcTemplate jdbcTemplate;

    public BuyMaterialSelectRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(BuyMaterialSelect buyMaterialSelect) {
        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        
                        INSERT INTO t_buy_material_select(id,
                                        t_user_id,
                                        t_project_id,
                                        create_datetime 
                                        )
                                        VALUES(?,?,?,?)
                                        """,
                newId,
                buyMaterialSelect.getUserId(),
                buyMaterialSelect.getProjectId(),
                new Date()) > 0)
            return newId;
        return null;

    }


    /**
     * RowMapper
     */
    private static final class BuyMaterialSelectMapper implements RowMapper<BuyMaterialSelect> {
        @Override
        public BuyMaterialSelect mapRow(ResultSet rs,
                                        int rowNum) throws SQLException {
            BuyMaterialSelect buyMaterialSelect = new BuyMaterialSelect();
            buyMaterialSelect.setId(rs.getString("id"));
            buyMaterialSelect.setUserId(rs.getString("t_user_id"));
            buyMaterialSelect.setProjectId(rs.getString("t_project_id"));
            buyMaterialSelect.setCreateDatetime(rs.getTimestamp("create_datetime"));
            buyMaterialSelect.setDeletedAt(rs.getTimestamp("deleted_at"));
            return buyMaterialSelect;
        }
    }
}

