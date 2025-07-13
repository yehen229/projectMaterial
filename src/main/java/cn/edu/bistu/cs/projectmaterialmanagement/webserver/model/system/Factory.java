package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class Factory {
    private String use_material_id;
    private String brand_id;
    private String factory_id;

    /**
     * 获取
     * @return brand_id
     */
    public String getBrand_id() {
        return brand_id;
    }

    /**
     * 设置
     * @param brand_id
     */
    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * 获取
     * @return factory_id
     */
    public String getFactory_id() {
        return factory_id;
    }

    /**
     * 设置
     * @param factory_id
     */
    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }

    /**
     * 获取
     * @return use_material_id
     */
    public String getUse_material_id() {
        return use_material_id;
    }

    /**
     * 设置
     * @param use_material_id
     */
    public void setUse_material_id(String use_material_id) {
        this.use_material_id = use_material_id;
    }

}
