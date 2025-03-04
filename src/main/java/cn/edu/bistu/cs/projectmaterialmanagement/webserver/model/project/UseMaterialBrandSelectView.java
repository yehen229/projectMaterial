package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseMaterialBrandSelectView {
    private UseMaterialBrandSelect useMaterialBrandSelect;//t_use_material_brand_select
    private User user;//外键：t_user_id,关联表为：t_user表,
    private Project project;//外键：t_project_id,关联表为：t_project表,
    private List<UseMaterialView> useMaterialViewList;

    public UseMaterialBrandSelect getUseMaterialBrandSelect() {
        return useMaterialBrandSelect;
    }

    public void setUseMaterialBrandSelect(UseMaterialBrandSelect useMaterialBrandSelect) {
        this.useMaterialBrandSelect = useMaterialBrandSelect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<UseMaterialView> getUseMaterialViewList() {
        return useMaterialViewList;
    }

    public void setUseMaterialViewList(List<UseMaterialView> useMaterialViewList) {
        this.useMaterialViewList = useMaterialViewList;
    }
}