package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewModeView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewModeRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectAppearanceReviewModeServiceImpl implements IProjectAppearanceReviewModeService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectAppearanceReviewModeServiceImpl.class);

    private final IProjectAppearanceReviewModeRepository projectAppearanceReviewModeRepository;

    public ProjectAppearanceReviewModeServiceImpl(IProjectAppearanceReviewModeRepository projectAppearanceReviewModeRepository) {
        this.projectAppearanceReviewModeRepository = projectAppearanceReviewModeRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return projectAppearanceReviewModeRepository.add(projectAppearanceReviewMode);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return projectAppearanceReviewModeRepository.delete(projectAppearanceReviewMode);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return projectAppearanceReviewModeRepository.update(projectAppearanceReviewMode);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectAppearanceReviewModeRepository.deleteById(id);
    }

    @Override
    public int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return projectAppearanceReviewModeRepository.deleteByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }


    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectAppearanceReviewModeRepository.deleteByUserId(userId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectAppearanceReviewModeRepository.getCount();
    }

    @Override
    public int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return projectAppearanceReviewModeRepository.getCountByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }


    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectAppearanceReviewModeRepository.getCountByUserId(userId);
    }

    /**
     * 根据id得到ProjectAppearanceReviewMode
     *
     * @param id
     */
    @Override
    public ProjectAppearanceReviewMode getById(String id) {
        return projectAppearanceReviewModeRepository.getById(id);
    }

    @Override
    public List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return projectAppearanceReviewModeRepository.getByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }


    /**
     * 根据userId得到ProjectAppearanceReviewMode
     *
     * @param userId
     */
    @Override
    public List<ProjectAppearanceReviewMode> getByUserId(String userId) {
        return projectAppearanceReviewModeRepository.getByUserId(userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewMode> getPage(int pageNo,
                                                     int pageSize) {
        return projectAppearanceReviewModeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ProjectAppearanceReviewMode> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                               int pageNo,
                                                                               int pageSize) {
        return projectAppearanceReviewModeRepository.getPageByUseMaterialBrandSelectId(useMaterialBrandSelectId, pageNo,
                                                                                       pageSize);
    }


    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewMode> getPageByUserId(String userId,
                                                             int pageNo,
                                                             int pageSize) {
        return projectAppearanceReviewModeRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewModeView> getPageView(int pageNo,
                                                             int pageSize) {
        Page<ProjectAppearanceReviewMode> projectAppearanceReviewModePage = getPage(pageNo, pageSize);
        return convertProjectAppearanceReviewModePage2PageView(projectAppearanceReviewModePage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectAppearanceReviewModeView> getPageViewByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                                       int pageNo,
                                                                                       int pageSize) {
        Page<ProjectAppearanceReviewMode> projectAppearanceReviewModePage = getPageByUseMaterialBrandSelectId(
                useMaterialBrandSelectId, pageNo, pageSize);
        return convertProjectAppearanceReviewModePage2PageView(projectAppearanceReviewModePage, pageNo, pageSize);
    }


    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewModeView> getPageViewByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        Page<ProjectAppearanceReviewMode> projectAppearanceReviewModePage = getPageByUserId(userId, pageNo, pageSize);
        return convertProjectAppearanceReviewModePage2PageView(projectAppearanceReviewModePage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectAppearanceReviewModeView getProjectAppearanceReviewModeViewByProjectAppearanceReviewModeId(String id) {
        ProjectAppearanceReviewMode projectAppearanceReviewMode = getById(id);
        if (projectAppearanceReviewMode == null) return null;
        ProjectAppearanceReviewModeView projectAppearanceReviewModeView = new ProjectAppearanceReviewModeView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectAppearanceReviewModePage 页面对象
     */
    private Page<ProjectAppearanceReviewModeView> convertProjectAppearanceReviewModePage2PageView(Page<ProjectAppearanceReviewMode> projectAppearanceReviewModePage,
                                                                                                  int pageNo,
                                                                                                  int pageSize) {
        if (projectAppearanceReviewModePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewModeView> list = new ArrayList<>();
        for (ProjectAppearanceReviewMode projectAppearanceReviewMode : projectAppearanceReviewModePage.getResult()) {
            ProjectAppearanceReviewModeView projectAppearanceReviewModeView = getProjectAppearanceReviewModeViewByProjectAppearanceReviewModeId(
                    projectAppearanceReviewMode.getId());
            if (projectAppearanceReviewModeView != null) list.add(projectAppearanceReviewModeView);
        }
        return new Page<>(startIndex, projectAppearanceReviewModePage.getTotalCount(), pageSize, list);
    }

}