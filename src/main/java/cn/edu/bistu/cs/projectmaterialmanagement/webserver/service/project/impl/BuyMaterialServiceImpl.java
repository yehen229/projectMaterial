package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IBuyMaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyMaterialServiceImpl implements IBuyMaterialService {
    private final IBuyMaterialRepository buyMaterialRepository;

    public BuyMaterialServiceImpl(IBuyMaterialRepository buyMaterialRepository) {
        this.buyMaterialRepository = buyMaterialRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(BuyMaterial buyMaterial) {
        return buyMaterialRepository.add(buyMaterial);
    }

    /**
     * 删除
     */
    @Override
    public int delete(BuyMaterial buyMaterial) {
        return buyMaterialRepository.delete(buyMaterial);
    }

    /**
     * 更新
     */
    @Override
    public int update(BuyMaterial buyMaterial) {
        return buyMaterialRepository.update(buyMaterial);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return buyMaterialRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return buyMaterialRepository.deleteByUserId(userId);
    }

    /**
     * 根据useMaterialId删除记录
     *
     * @param useMaterialId
     */
    @Override
    public int deleteByUseMaterialId(String useMaterialId) {
        return buyMaterialRepository.deleteByUseMaterialId(useMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId删除记录
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return buyMaterialRepository.deleteByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId删除记录
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return buyMaterialRepository.deleteByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return buyMaterialRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return buyMaterialRepository.getCountByUserId(userId);
    }

    /**
     * 根据useMaterialId得到数量
     *
     * @param useMaterialId
     */
    @Override
    public int getCountByUseMaterialId(String useMaterialId) {
        return buyMaterialRepository.getCountByUseMaterialId(useMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到数量
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return buyMaterialRepository.getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到数量
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return buyMaterialRepository.getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 根据id得到BuyMaterial
     *
     * @param id
     */
    @Override
    public BuyMaterial getById(String id) {
        return buyMaterialRepository.getById(id);
    }

    /**
     * 根据userId得到BuyMaterial
     *
     * @param userId
     */
    @Override
    public List<BuyMaterial> getByUserId(String userId) {
        return buyMaterialRepository.getByUserId(userId);
    }

    /**
     * 根据useMaterialId得到BuyMaterial
     *
     * @param useMaterialId
     */
    @Override
    public List<BuyMaterial> getByUseMaterialId(String useMaterialId) {
        return buyMaterialRepository.getByUseMaterialId(useMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到BuyMaterial
     *
     * @param projectMaterialBrandPrivateId
     */
    @Override
    public List<BuyMaterial> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return buyMaterialRepository.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到BuyMaterial
     *
     * @param projectMaterialBrandPublicId
     */
    @Override
    public List<BuyMaterial> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return buyMaterialRepository.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BuyMaterial> getPage(int pageNo,
                                     int pageSize) {
        return buyMaterialRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BuyMaterial> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        return buyMaterialRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<BuyMaterial> getPageByUseMaterialId(String useMaterialId,
                                                    int pageNo,
                                                    int pageSize) {
        return buyMaterialRepository.getPageByUseMaterialId(useMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<BuyMaterial> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                    int pageNo,
                                                                    int pageSize) {
        return buyMaterialRepository.getPageByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId, pageNo,
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
    public Page<BuyMaterial> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                   int pageNo,
                                                                   int pageSize) {
        return buyMaterialRepository.getPageByProjectMaterialBrandPublicId(projectMaterialBrandPublicId, pageNo,
                                                                           pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BuyMaterialView> getPageView(int pageNo,
                                             int pageSize) {
        Page<BuyMaterial> buyMaterialPage = getPage(pageNo, pageSize);
        return convertBuyMaterialPage2PageView(buyMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BuyMaterialView> getPageViewByUserId(String userId,
                                                     int pageNo,
                                                     int pageSize) {
        Page<BuyMaterial> buyMaterialPage = getPageByUserId(userId, pageNo, pageSize);
        return convertBuyMaterialPage2PageView(buyMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<BuyMaterialView> getPageViewByUseMaterialId(String useMaterialId,
                                                            int pageNo,
                                                            int pageSize) {
        Page<BuyMaterial> buyMaterialPage = getPageByUseMaterialId(useMaterialId, pageNo, pageSize);
        return convertBuyMaterialPage2PageView(buyMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<BuyMaterialView> getPageViewByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                            int pageNo,
                                                                            int pageSize) {
        Page<BuyMaterial> buyMaterialPage = getPageByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,
                                                                                   pageNo, pageSize);
        return convertBuyMaterialPage2PageView(buyMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<BuyMaterialView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                           int pageNo,
                                                                           int pageSize) {
        Page<BuyMaterial> buyMaterialPage = getPageByProjectMaterialBrandPublicId(projectMaterialBrandPublicId, pageNo,
                                                                                  pageSize);
        return convertBuyMaterialPage2PageView(buyMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<BuyMaterial> getPageByProjectId(String projectId,
                                                Integer pageNo,
                                                Integer pageSize) {
        return buyMaterialRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    @Override
    public Page<BuyMaterial> getPageReCheckIsRequiredByProjectId(String projectId,
                                                                 Integer pageNo,
                                                                 Integer pageSize) {
        return buyMaterialRepository.getPageReCheckIsRequiredByProjectId(projectId, pageNo, pageSize);
    }

    @Override
    public Page<String> getBoughtMaterialIdPageByProjectId(String projectId,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        return buyMaterialRepository.getBoughtMaterialIdPageByProjectId(projectId, pageNo, pageSize);
    }

    @Override
    public List<String> getBoughtProjectMaterialBrandPrivateIdListPageByProjectIdAndMaterialId(String projectId,
                                                                                               String materialId) {
        return buyMaterialRepository.getBoughtProjectMaterialBrandPrivateIdListPageByProjectIdAndMaterialId(projectId,
                                                                                                            materialId);
    }

    @Override
    public List<String> getBoughtProjectMaterialBrandPublicIdListPageByProjectIdAndMaterialId(String projectId,
                                                                                              String materialId) {
        return buyMaterialRepository.getBoughtProjectMaterialBrandPublicIdListPageByProjectIdAndMaterialId(projectId,
                                                                                                           materialId);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private BuyMaterialView getBuyMaterialViewByBuyMaterialId(String id) {
        BuyMaterial buyMaterial = getById(id);
        if (buyMaterial == null) return null;
        BuyMaterialView buyMaterialView = new BuyMaterialView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param buyMaterialPage 页面对象
     */
    private Page<BuyMaterialView> convertBuyMaterialPage2PageView(Page<BuyMaterial> buyMaterialPage,
                                                                  int pageNo,
                                                                  int pageSize) {
        if (buyMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterialView> list = new ArrayList<>();
        for (BuyMaterial buyMaterial : buyMaterialPage.getResult()) {
            BuyMaterialView buyMaterialView = getBuyMaterialViewByBuyMaterialId(buyMaterial.getId());
            if (buyMaterialView != null) list.add(buyMaterialView);
        }
        return new Page<>(startIndex, buyMaterialPage.getTotalCount(), pageSize, list);
    }

}