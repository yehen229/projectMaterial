package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseMaterialServiceImpl implements IUseMaterialService {
    private final IUseMaterialRepository useMaterialRepository;

    public UseMaterialServiceImpl(IUseMaterialRepository useMaterialRepository) {
        this.useMaterialRepository = useMaterialRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(UseMaterial useMaterial) {
        return useMaterialRepository.add(useMaterial);
    }

    /**
     * 删除
     */
    @Override
    public int delete(UseMaterial useMaterial) {
        return useMaterialRepository.delete(useMaterial);
    }

    /**
     * 更新
     */
    @Override
    public int update(UseMaterial useMaterial) {
        return useMaterialRepository.update(useMaterial);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return useMaterialRepository.deleteById(id);
    }

    @Override
    public int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return useMaterialRepository.deleteByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }

    /**
     * 根据projectMaterialId删除记录
     *
     * @param projectMaterialId
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return useMaterialRepository.deleteByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return useMaterialRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectMaterialBrandPrivateId删除记录
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return useMaterialRepository.deleteByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId删除记录
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return useMaterialRepository.deleteByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return useMaterialRepository.getCount();
    }

    @Override
    public int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return useMaterialRepository.getCountByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }

    /**
     * 根据projectMaterialId得到数量
     *
     * @param projectMaterialId
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        return useMaterialRepository.getCountByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return useMaterialRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到数量
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return useMaterialRepository.getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到数量
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return useMaterialRepository.getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 根据id得到UseMaterial
     *
     * @param id
     */
    @Override
    public UseMaterial getById(String id) {
        return useMaterialRepository.getById(id);
    }

    @Override
    public List<UseMaterial> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return useMaterialRepository.getByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }

    /**
     * 根据projectMaterialId得到UseMaterial
     *
     * @param projectMaterialId
     */
    @Override
    public List<UseMaterial> getByProjectMaterialId(String projectMaterialId) {
        return useMaterialRepository.getByProjectMaterialId(projectMaterialId);
    }

    /**
     * 根据userId得到UseMaterial
     *
     * @param userId
     */
    @Override
    public List<UseMaterial> getByUserId(String userId) {
        return useMaterialRepository.getByUserId(userId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到UseMaterial
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public List<UseMaterial> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return useMaterialRepository.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到UseMaterial
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public List<UseMaterial> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return useMaterialRepository.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    @Override
    public List<UseMaterial> getReviewedAndApprovedListByProjectId(String projectId) {
        return useMaterialRepository.getReviewedAndApprovedListByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterial> getPage(int pageNo,
                                     int pageSize) {
        return useMaterialRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<UseMaterial> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                               int pageNo,
                                                               int pageSize) {
        return useMaterialRepository.getPageByUseMaterialBrandSelectId(useMaterialBrandSelectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByProjectMaterialId(String projectMaterialId,
                                                        int pageNo,
                                                        int pageSize) {
        return useMaterialRepository.getPageByProjectMaterialId(projectMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        return useMaterialRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                    int pageNo,
                                                                    int pageSize) {
        return useMaterialRepository.getPageByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId, pageNo,
                                                                            pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                   int pageNo,
                                                                   int pageSize) {
        return useMaterialRepository.getPageByProjectMaterialBrandPublicId(projectMaterialBrandPublicId, pageNo,
                                                                           pageSize);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectId(String projectId,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        return useMaterialRepository.getReviewedAndApprovedPageByProjectId(projectId, pageNo, pageSize);
    }


}