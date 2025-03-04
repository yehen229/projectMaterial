package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptance;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMaterialAcceptanceServiceImpl implements IProjectMaterialAcceptanceService {
    private final IProjectMaterialAcceptanceRepository projectMaterialAcceptanceRepository;

    public ProjectMaterialAcceptanceServiceImpl(IProjectMaterialAcceptanceRepository projectMaterialAcceptanceRepository) {
        this.projectMaterialAcceptanceRepository = projectMaterialAcceptanceRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialAcceptance projectMaterialAcceptance) {
        return projectMaterialAcceptanceRepository.add(projectMaterialAcceptance);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialAcceptance projectMaterialAcceptance) {
        return projectMaterialAcceptanceRepository.delete(projectMaterialAcceptance);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialAcceptance projectMaterialAcceptance) {
        return projectMaterialAcceptanceRepository.update(projectMaterialAcceptance);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialAcceptanceRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectMaterialAcceptanceRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectMaterialId删除记录
     *
     * @param projectMaterialId
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return projectMaterialAcceptanceRepository.deleteByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId删除记录
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return projectMaterialAcceptanceRepository.deleteByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId删除记录
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return projectMaterialAcceptanceRepository.deleteByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialAcceptanceRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectMaterialAcceptanceRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectMaterialId得到数量
     *
     * @param projectMaterialId
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        return projectMaterialAcceptanceRepository.getCountByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到数量
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return projectMaterialAcceptanceRepository.getCountByProjectMaterialBrandPrivateId(
                projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到数量
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return projectMaterialAcceptanceRepository.getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 根据id得到ProjectMaterialAcceptance
     *
     * @param id
     */
    @Override
    public ProjectMaterialAcceptance getById(String id) {
        return projectMaterialAcceptanceRepository.getById(id);
    }

    /**
     * 根据userId得到ProjectMaterialAcceptance
     *
     * @param userId
     */
    @Override
    public List<ProjectMaterialAcceptance> getByUserId(String userId) {
        return projectMaterialAcceptanceRepository.getByUserId(userId);
    }

    /**
     * 根据projectMaterialId得到ProjectMaterialAcceptance
     *
     * @param projectMaterialId
     */
    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialId(String projectMaterialId) {
        return projectMaterialAcceptanceRepository.getByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到ProjectMaterialAcceptance
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return projectMaterialAcceptanceRepository.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到ProjectMaterialAcceptance
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return projectMaterialAcceptanceRepository.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPage(int pageNo,
                                                   int pageSize) {
        return projectMaterialAcceptanceRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByUserId(String userId,
                                                           int pageNo,
                                                           int pageSize) {
        return projectMaterialAcceptanceRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                     int pageNo,
                                                                                     int pageSize) {
        return projectMaterialAcceptanceRepository.getPageByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId,
                pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialId(String projectMaterialId,
                                                                      int pageNo,
                                                                      int pageSize) {
        return projectMaterialAcceptanceRepository.getPageByProjectMaterialId(projectMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                                  int pageNo,
                                                                                  int pageSize) {
        return projectMaterialAcceptanceRepository.getPageByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,
                                                                                          pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                                 int pageNo,
                                                                                 int pageSize) {
        return projectMaterialAcceptanceRepository.getPageByProjectMaterialBrandPublicId(projectMaterialBrandPublicId,
                                                                                         pageNo, pageSize);
    }


}