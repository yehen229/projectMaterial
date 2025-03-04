package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetest;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestService;
import org.springframework.stereotype.Service;


@Service
public class ProjectMaterialRetestBusinessServiceImpl implements IProjectMaterialRetestBusinessService {

    private final IProjectMaterialRetestService projectMaterialRetestService;
    private final IProjectMaterialRetestFileService projectMaterialRetestFileService;
    public ProjectMaterialRetestBusinessServiceImpl(IProjectMaterialRetestService projectMaterialRetestService,
                                                   IProjectMaterialRetestFileService projectMaterialRetestFileService

    ){
        this.projectMaterialRetestService = projectMaterialRetestService;
        this.projectMaterialRetestFileService = projectMaterialRetestFileService;
    }
    @Override
    public ProjectMaterialRetestView getViewBypProjectMaterialRetestUserId(String projectMaterialRetestUserId) {
        ProjectMaterialRetest projectMaterialRetest = projectMaterialRetestService.getById(projectMaterialRetestUserId);
        if(projectMaterialRetest == null) return null;
        ProjectMaterialRetestView projectMaterialRetestView = new ProjectMaterialRetestView();
        projectMaterialRetestView.setProjectMaterialRetest(projectMaterialRetest);
        projectMaterialRetestView.setProjectMaterialRetestFileList(projectMaterialRetestFileService.getByRetestId(projectMaterialRetest.getId()));
        return projectMaterialRetestView;
    }
}

