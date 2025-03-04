package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Role {
    private String id;//id,主键
    private String name;//name,角色名称角色名称
    private String note;//note,角色说明角色说明
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isAdmin() {
        return name.equals("Admin");
    }

    public boolean isEmployee() {
        return name.equals("Employee");
    }

    public boolean isManager() {
        return name.equals("Manager");
    }
}