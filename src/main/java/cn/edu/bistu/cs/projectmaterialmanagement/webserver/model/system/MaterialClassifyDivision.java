package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MaterialClassifyDivision {
    private String id;//id,主键
    private String name;//name
    private String note;//note
    private Date deletedAt;//deleted_at


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
}