package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Brandexcel {
    private String materialsdiv;
    private String materialsgroup;
    private String materialssection;
    private String position;//position,定位：合资、国产等定位：合资、国产等
    private String name;
    private String factory_id;//factory_id,品牌对应厂家，可为空

    public String getMaterialsdiv() {
        return materialsdiv;
    }

    public void setMaterialsdiv(String materialsdiv) {
        this.materialsdiv = materialsdiv;
    }

    public String getMaterialssection() {
        return materialssection;
    }

    public void setMaterialssection(String materialssection) {
        this.materialssection = materialssection;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }

    public String getMaterialsgroup() {
        return materialsgroup;
    }

    public void setMaterialsgroup(String materialsgroup) {
        this.materialsgroup = materialsgroup;
    }
}