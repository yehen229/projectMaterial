package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAppearanceReviewUserServiceImpl implements IProjectAppearanceReviewUserService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectAppearanceReviewUserServiceImpl.class);

    private final IProjectAppearanceReviewUserRepository projectAppearanceReviewUserRepository;

    public ProjectAppearanceReviewUserServiceImpl(IProjectAppearanceReviewUserRepository projectAppearanceReviewUserRepository) {
        this.projectAppearanceReviewUserRepository = projectAppearanceReviewUserRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return projectAppearanceReviewUserRepository.add(projectAppearanceReviewUser);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return projectAppearanceReviewUserRepository.delete(projectAppearanceReviewUser);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return projectAppearanceReviewUserRepository.update(projectAppearanceReviewUser);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectAppearanceReviewUserRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectAppearanceReviewUserRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectAppearanceReviewId删除记录
     *
     * @param projectAppearanceReviewId
     */
    @Override
    public int deleteByProjectAppearanceReviewId(String projectAppearanceReviewId) {
        return projectAppearanceReviewUserRepository.deleteByProjectAppearanceReviewId(projectAppearanceReviewId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectAppearanceReviewUserRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectAppearanceReviewUserRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectAppearanceReviewId得到数量
     *
     * @param projectAppearanceReviewId
     */
    @Override
    public int getCountByProjectAppearanceReviewId(String projectAppearanceReviewId) {
        return projectAppearanceReviewUserRepository.getCountByProjectAppearanceReviewId(projectAppearanceReviewId);
    }

    /**
     * 根据id得到ProjectAppearanceReviewUser
     *
     * @param id
     */
    @Override
    public ProjectAppearanceReviewUser getById(String id) {
        return projectAppearanceReviewUserRepository.getById(id);
    }

    @Override
    public ProjectAppearanceReviewUser getByUserIdAndUseMaterialBrandSelectId(String userId,
                                                                              String useMaterialBrandSelectId,
                                                                              String projectAppearanceReviewModeId) {
        return projectAppearanceReviewUserRepository.getByUserIdAndUseMaterialBrandSelectId(userId,
                                                                                            useMaterialBrandSelectId,
                                                                                            projectAppearanceReviewModeId);
    }

    /**
     * 根据userId得到ProjectAppearanceReviewUser
     *
     * @param userId
     */
    @Override
    public List<ProjectAppearanceReviewUser> getByUserId(String userId) {
        return projectAppearanceReviewUserRepository.getByUserId(userId);
    }

    /**
     * 根据projectAppearanceReviewId得到ProjectAppearanceReviewUser
     *
     * @param projectAppearanceReviewId
     */
    @Override
    public List<ProjectAppearanceReviewUser> getByProjectAppearanceReviewId(String projectAppearanceReviewId) {
        return projectAppearanceReviewUserRepository.getByProjectAppearanceReviewId(projectAppearanceReviewId);
    }

    @Override
    public List<ProjectAppearanceReviewUser> getByAppearanceModeIdAndNotReviewed(String projectAppearanceReviewModeId) {
        return projectAppearanceReviewUserRepository.getByAppearanceModeIdAndNotReviewed(projectAppearanceReviewModeId,
                                                                                         IProjectReviewUserService.PROJECT_REVIEW_RESULT_UNKNOWN);
    }

    @Override
    public List<ProjectAppearanceReviewUser> getByAppearanceModeId(String projectAppearanceReviewModeId) {
        return projectAppearanceReviewUserRepository.getByAppearanceModeId(projectAppearanceReviewModeId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUser> getPage(int pageNo,
                                                     int pageSize) {
        return projectAppearanceReviewUserRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUser> getPageByUserId(String userId,
                                                             int pageNo,
                                                             int pageSize) {
        return projectAppearanceReviewUserRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectAppearanceReviewId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUser> getPageByProjectAppearanceReviewId(String projectAppearanceReviewId,
                                                                                int pageNo,
                                                                                int pageSize) {
        return projectAppearanceReviewUserRepository.getPageByProjectAppearanceReviewId(projectAppearanceReviewId,
                                                                                        pageNo, pageSize);
    }

    @Override
    public Page<ProjectAppearanceReviewUser> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                    Integer pageNo,
                                                                                    Integer pageSize) {
        return projectAppearanceReviewUserRepository.getPageByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId,
                pageNo, pageSize);
    }
    @Override
    public int getCountByProjectId(String projectId){
        return projectAppearanceReviewUserRepository.getCountByProjectId(projectId);
    }
    @Override
    public int getCountByProjectIdAndResult(String projectId,int nReviewResult){
        return projectAppearanceReviewUserRepository.getCountByProjectIdAndResult(projectId,nReviewResult);
    }


}