package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewModeView;

import java.util.List;

public interface IProjectAppearanceReviewModeService {
    String add(ProjectAppearanceReviewMode projectAppearanceReviewMode);

    int delete(ProjectAppearanceReviewMode projectAppearanceReviewMode);

    int update(ProjectAppearanceReviewMode projectAppearanceReviewMode);

    int deleteById(String id);

    int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId);


    int deleteByUserId(String userId);

    int getCount();

    int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId);


    int getCountByUserId(String userId);

    ProjectAppearanceReviewMode getById(String id);

    List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId);


    List<ProjectAppearanceReviewMode> getByUserId(String userId);

    Page<ProjectAppearanceReviewMode> getPage(int pageNo,
                                              int pageSize);

    Page<ProjectAppearanceReviewMode> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                        int pageNo,
                                                                        int pageSize);

    Page<ProjectAppearanceReviewMode> getPageByUserId(String userId,
                                                      int pageNo,
                                                      int pageSize);

    Page<ProjectAppearanceReviewModeView> getPageView(int pageNo,
                                                      int pageSize);

    Page<ProjectAppearanceReviewModeView> getPageViewByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                                int pageNo,
                                                                                int pageSize);


    Page<ProjectAppearanceReviewModeView> getPageViewByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize);

}