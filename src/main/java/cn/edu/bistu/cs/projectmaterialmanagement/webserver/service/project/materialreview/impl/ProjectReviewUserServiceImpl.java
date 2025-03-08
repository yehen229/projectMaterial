package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.materialreview.IProjectReviewUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectReviewUserServiceImpl implements IProjectReviewUserService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectReviewUserServiceImpl.class);

    private final IProjectReviewUserRepository projectReviewUserRepository;

    public ProjectReviewUserServiceImpl(IProjectReviewUserRepository projectReviewUserRepository) {
        this.projectReviewUserRepository = projectReviewUserRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectReviewUser projectReviewUser) {
        return projectReviewUserRepository.add(projectReviewUser);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectReviewUser projectReviewUser) {
        return projectReviewUserRepository.delete(projectReviewUser);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectReviewUser projectReviewUser) {
        return projectReviewUserRepository.update(projectReviewUser);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectReviewUserRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectReviewUserRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectReviewId删除记录
     *
     * @param projectReviewId
     */
    @Override
    public int deleteByProjectReviewId(String projectReviewId) {
        return projectReviewUserRepository.deleteByProjectReviewId(projectReviewId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectReviewUserRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectReviewUserRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectReviewId得到数量
     *
     * @param projectReviewId
     */
    @Override
    public int getCountByProjectReviewId(String projectReviewId) {
        return projectReviewUserRepository.getCountByProjectReviewId(projectReviewId);
    }

    /**
     * 根据id得到ProjectReviewUser
     *
     * @param id
     */
    @Override
    public ProjectReviewUser getById(String id) {
        return projectReviewUserRepository.getById(id);
    }

    @Override
    public ProjectReviewUser getByUserIdAndProjectReviewId(String userId, String projectReviewId) {
        return projectReviewUserRepository.getByUserIdAndProjectReviewId(userId, projectReviewId);
    }

    /**
     * 根据userId得到ProjectReviewUser
     *
     * @param userId
     */
    @Override
    public List<ProjectReviewUser> getByUserId(String userId) {
        return projectReviewUserRepository.getByUserId(userId);
    }

    /**
     * 根据projectReviewId得到ProjectReviewUser
     *
     * @param projectReviewId
     */
    @Override
    public List<ProjectReviewUser> getByProjectReviewId(String projectReviewId) {
        return projectReviewUserRepository.getByProjectReviewId(projectReviewId);
    }

    @Override
    public List<ProjectReviewUser> getByProjectReviewIdAndNotReviewed(String projectReviewId) {
        return projectReviewUserRepository.getByProjectReviewIdAndNotReviewed(projectReviewId, PROJECT_REVIEW_RESULT_UNKNOWN);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPage(int pageNo, int pageSize) {
        return projectReviewUserRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPageByUserId(String userId, int pageNo, int pageSize) {
        return projectReviewUserRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectReviewId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPageByProjectReviewId(String projectReviewId, int pageNo, int pageSize) {
        return projectReviewUserRepository.getPageByProjectReviewId(projectReviewId, pageNo, pageSize);
    }
    /**
     * 获得指定页面数据
     *
     * @param projectReviewId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPageByProjectReviewIdAndUserAndResult(String projectReviewId, String reviewUser, int reviewResult, int pageNo, int pageSize) {
        return projectReviewUserRepository.getPageByProjectReviewIdAndUserAndResult(projectReviewId, reviewUser, reviewResult, pageNo, pageSize);
    }

    @Override
    public int getCountByProjectReviewIdAndResult(String projectReviewId, int nReviewResult) {
        return projectReviewUserRepository.getCountByProjectReviewIdAndResult(projectReviewId, nReviewResult);
    }
    @Override
    public int getCountByProjectId(String projectId)
    {
        return projectReviewUserRepository.getCountByProjecId(projectId);
    }
    @Override
    public int getCountByProjectIdAndResult(String projectId, int nReviewResult){
        return projectReviewUserRepository.getCountByProjectIdAndResult(projectId,nReviewResult);
    }


}