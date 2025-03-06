package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectOpHistoryRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectOpHistoryServiceImpl implements IProjectOpHistoryService {
    private final IProjectOpHistoryRepository projectOpHistoryRepository;
    private final IProjectService projectService;
    private final IUserService userService;

    public ProjectOpHistoryServiceImpl(IProjectOpHistoryRepository projectOpHistoryRepository,
                                       IProjectService projectService,
                                       IUserService userService) {
        this.projectOpHistoryRepository = projectOpHistoryRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectOpHistory projectOpHistory) {
        return projectOpHistoryRepository.add(projectOpHistory);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectOpHistory projectOpHistory) {
        return projectOpHistoryRepository.delete(projectOpHistory);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectOpHistory projectOpHistory) {
        return projectOpHistoryRepository.update(projectOpHistory);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectOpHistoryRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectOpHistoryRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectOpHistoryRepository.deleteByProjectId(projectId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectOpHistoryRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectOpHistoryRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectOpHistoryRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据id得到ProjectOpHistory
     *
     * @param id
     */
    @Override
    public ProjectOpHistory getById(String id) {
        return projectOpHistoryRepository.getById(id);
    }

    /**
     * 根据userId得到ProjectOpHistory
     *
     * @param userId
     */
    @Override
    public List<ProjectOpHistory> getByUserId(String userId) {
        return projectOpHistoryRepository.getByUserId(userId);
    }

    /**
     * 根据projectId得到ProjectOpHistory
     *
     * @param projectId
     */
    @Override
    public List<ProjectOpHistory> getByProjectId(String projectId) {
        return projectOpHistoryRepository.getByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistory> getPage(int pageNo,
                                          int pageSize) {
        return projectOpHistoryRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistory> getPageByUserId(String userId,
                                                  int pageNo,
                                                  int pageSize) {
        return projectOpHistoryRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectOpHistory> getPageByProjectId(String projectId,
                                                     int pageNo,
                                                     int pageSize) {

        return projectOpHistoryRepository.getPageByProjectId(projectId, pageNo, pageSize);
//        转换视图格式

    }


}