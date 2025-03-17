package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

public class Stastisprojectidandcount {
    int count;
//    设计部门
    String designProjectId;
    String designtCompanyId;
    String designtMaterialId;
//    总包
    String zongbaoProjectId;
    String zongbaoMaterialId;
//    监理
    String jianliProjectId;
    String jianliMaterialId;
//   监理与工程部
    String jianliandgongchengbuProjectId;
    String jianliandgongchengbuMaterialId;

    public String getJianliandgongchengbuProjectId() {
        return jianliandgongchengbuProjectId;
    }

    public void setJianliandgongchengbuProjectId(String jianliandgongchengbuProjectId) {
        this.jianliandgongchengbuProjectId = jianliandgongchengbuProjectId;
    }

    public String getJianliandgongchengbuMaterialId() {
        return jianliandgongchengbuMaterialId;
    }

    public void setJianliandgongchengbuMaterialId(String jianliandgongchengbuMaterialId) {
        this.jianliandgongchengbuMaterialId = jianliandgongchengbuMaterialId;
    }

    public String getJianliProjectId() {
        return jianliProjectId;
    }

    public void setJianliProjectId(String jianliProjectId) {
        this.jianliProjectId = jianliProjectId;
    }

    public String getJianliMaterialId() {
        return jianliMaterialId;
    }

    public void setJianliMaterialId(String jianliMaterialId) {
        this.jianliMaterialId = jianliMaterialId;
    }

    public String getZongbaoProjectId() {
        return zongbaoProjectId;
    }

    public void setZongbaoProjectId(String zongbaoProjectId) {
        this.zongbaoProjectId = zongbaoProjectId;
    }

    public String getZongbaoMaterialId() {
        return zongbaoMaterialId;
    }

    public void setZongbaoMaterialId(String zongbaoMaterialId) {
        this.zongbaoMaterialId = zongbaoMaterialId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDesignProjectId() {
        return designProjectId;
    }

    public void setDesignProjectId(String designProjectId) {
        this.designProjectId = designProjectId;
    }

    public String getDesigntCompanyId() {
        return designtCompanyId;
    }

    public void setDesigntCompanyId(String designtCompanyId) {
        this.designtCompanyId = designtCompanyId;
    }

    public String getDesigntMaterialId() {
        return designtMaterialId;
    }

    public void setDesigntMaterialId(String designtMaterialId) {
        this.designtMaterialId = designtMaterialId;
    }
}
