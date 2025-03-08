package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetest;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewUserService;
import org.springframework.stereotype.Service;


@Service
public class ProjectMaterialRetestBusinessServiceImpl implements IProjectMaterialRetestBusinessService {

    private final IProjectMaterialRetestService projectMaterialRetestService;
    private final IProjectMaterialRetestFileService projectMaterialRetestFileService;
    private final IProjectMaterialRetestBatchService projectMaterialRetestBatchService;
    private final IBuyMaterialService buyMaterialService;
    private final IUserService userService;
    private final IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService;
    public ProjectMaterialRetestBusinessServiceImpl(IProjectMaterialRetestService projectMaterialRetestService,
                                                    IProjectMaterialRetestFileService projectMaterialRetestFileService,
                                                    IProjectMaterialRetestBatchService projectMaterialRetestBatchService,
                                                    IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService
                                                    , IUserService userService,
                                                    IBuyMaterialService buyMaterialService


    ){
        this.projectMaterialRetestService = projectMaterialRetestService;
        this.projectMaterialRetestFileService = projectMaterialRetestFileService;
        this.projectMaterialRetestBatchService = projectMaterialRetestBatchService;
        this.projectMaterialAcceptanceReviewUserService = projectMaterialAcceptanceReviewUserService;
        this.userService = userService;
        this.buyMaterialService = buyMaterialService;
    }
    @Override
    public ProjectMaterialRetestView getViewBypProjectMaterialRetestUserId(String userId,  String projectMaterialRetestId) {
        ProjectMaterialRetest projectMaterialRetest = projectMaterialRetestService.getById(projectMaterialRetestId);
        User user = userService.getById(userId);
        String buyMaterialId = projectMaterialRetest.getBuyMaterialId();
        BuyMaterial buyMaterial = buyMaterialService.getById(buyMaterialId);
        ProjectMaterialRetestView projectMaterialRetestView = new ProjectMaterialRetestView();
        projectMaterialRetestView.setProjectMaterialRetest(projectMaterialRetest);
        projectMaterialRetestView.setProjectMaterialRetestBatch(projectMaterialRetestBatchService.getById(projectMaterialRetest.getProjectMaterialRetestBatchId()));
        projectMaterialRetestView.setUser(user);
        projectMaterialRetestView.setBuyMaterial(buyMaterial);
        return projectMaterialRetestView;
    }


}

