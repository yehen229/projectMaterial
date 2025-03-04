package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUserFile;

import java.util.List;

/**
 * ProjectAppearanceReviewUserFile Service Interface
 */
public interface IProjectAppearanceReviewUserFileRepository {

    String add(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile);

    int delete(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile);

    int deleteById(String id);

    int deleteByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    int update(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile);

    int getCount();

    int getCountByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    ProjectAppearanceReviewUserFile getById(String id);

    List<ProjectAppearanceReviewUserFile> getByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId);

    Page<ProjectAppearanceReviewUserFile> getPage(int pageNo, int pageSize);

    Page<ProjectAppearanceReviewUserFile> getPageByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId, int pageNo, int pageSize);

}