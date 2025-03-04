package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewMode;

import java.util.List;

/**
 * ProjectAppearanceReviewMode Service Interface
 */
public interface IProjectAppearanceReviewModeRepository {

    String add(ProjectAppearanceReviewMode projectAppearanceReviewMode);

    int delete(ProjectAppearanceReviewMode projectAppearanceReviewMode);

    int deleteById(String id);

    int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId);


    int deleteByUserId(String userId);

    int update(ProjectAppearanceReviewMode projectAppearanceReviewMode);

    int getCount();

    int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId);


    int getCountByUserId(String userId);

    ProjectAppearanceReviewMode getById(String id);

    List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId);


    List<ProjectAppearanceReviewMode> getByUserId(String userId);

    List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectIdAndUserId(String useMaterialBrandSelectId,
                                                                             String userId);

    List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectIdAndUserIdAndAffectAppearance(String useMaterialBrandSelectId,
                                                                                                String userId,
                                                                                                int affectAppearance);


    Page<ProjectAppearanceReviewMode> getPage(int pageNo,
                                              int pageSize);

    Page<ProjectAppearanceReviewMode> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                        int pageNo,
                                                                        int pageSize);


    Page<ProjectAppearanceReviewMode> getPageByUserId(String userId,
                                                      int pageNo,
                                                      int pageSize);

}