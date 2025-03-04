package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReview;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;

import java.util.List;

public interface IProjectAppearanceReviewService {
    String add(ProjectAppearanceReview projectAppearanceReview);

    int delete(ProjectAppearanceReview projectAppearanceReview);

    int update(ProjectAppearanceReview projectAppearanceReview);

    int deleteById(String id);

    int deleteByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    int deleteByUseMaterialId(String useMaterialId);

    int getCount();

    int getCountByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    int getCountByUseMaterialId(String useMaterialId);

    ProjectAppearanceReview getById(String id);

    ProjectAppearanceReviewView getViewById(String id);


    UseMaterial getUseMaterialById(String id);

    List<ProjectAppearanceReview> getByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId);

    List<ProjectAppearanceReview> getByUseMaterialId(String useMaterialId);

    Page<ProjectAppearanceReview> getPage(int pageNo,
                                          int pageSize);

    Page<ProjectAppearanceReview> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                         int pageNo,
                                                                         int pageSize);

    Page<ProjectAppearanceReview> getPageByUseMaterialId(String useMaterialId,
                                                         int pageNo,
                                                         int pageSize);

    Page<ProjectAppearanceReviewView> getPageView(int pageNo,
                                                  int pageSize);

    Page<ProjectAppearanceReviewView> getPageViewByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                 int pageNo,
                                                                                 int pageSize);

    Page<ProjectAppearanceReviewView> getPageViewByUseMaterialId(String useMaterialId,
                                                                 int pageNo,
                                                                 int pageSize);

}