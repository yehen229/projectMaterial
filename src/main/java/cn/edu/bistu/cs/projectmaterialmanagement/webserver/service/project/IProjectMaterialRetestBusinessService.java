package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestView;



public interface IProjectMaterialRetestBusinessService {
    ProjectMaterialRetestView getViewBypProjectMaterialRetestUserId(String projectMaterialRetestUserId);
}
