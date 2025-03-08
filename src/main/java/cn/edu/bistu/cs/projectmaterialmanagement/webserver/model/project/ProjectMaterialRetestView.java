package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMaterialRetestView {
    private ProjectMaterialRetest projectMaterialRetest;//t_project_material_retest
    private BuyMaterial buyMaterial;//外键：t_buy_material_id,关联表为：t_buy_material表,
    private User user;//外键：t_user_id,关联表为：t_user表,
    private ProjectMaterialRetestBatch projectMaterialRetestBatch;//外键：t_project_material_retest_batch_id,关联表为：t_project_material_retest_batch表,

    public ProjectMaterialRetest getProjectMaterialRetest() {
        return projectMaterialRetest;
    }

    public void setProjectMaterialRetest(ProjectMaterialRetest projectMaterialRetest) {
        this.projectMaterialRetest = projectMaterialRetest;
    }

    public BuyMaterial getBuyMaterial() {
        return buyMaterial;
    }

    public void setBuyMaterial(BuyMaterial buyMaterial) {
        this.buyMaterial = buyMaterial;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public ProjectMaterialRetestBatch getProjectMaterialRetestBatch() {
        return projectMaterialRetestBatch;
    }

    public void setProjectMaterialRetestBatch(ProjectMaterialRetestBatch projectMaterialRetestBatch) {
        this.projectMaterialRetestBatch = projectMaterialRetestBatch;
    }
}