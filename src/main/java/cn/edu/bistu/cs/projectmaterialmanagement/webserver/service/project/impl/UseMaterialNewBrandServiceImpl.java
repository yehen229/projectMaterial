package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialNewBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialNewBrandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UseMaterialNewBrandServiceImpl implements IUseMaterialNewBrandService {
    private final IUseMaterialNewBrandRepository useMaterialNewBrandRepository;

    public UseMaterialNewBrandServiceImpl(IUseMaterialNewBrandRepository useMaterialNewBrandRepository) {
        this.useMaterialNewBrandRepository = useMaterialNewBrandRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(UseMaterialNewBrand useMaterialNewBrand) {
        return useMaterialNewBrandRepository.add(useMaterialNewBrand);
    }

    /**
     * 删除
     */
    @Override
    public int delete(UseMaterialNewBrand useMaterialNewBrand) {
        return useMaterialNewBrandRepository.delete(useMaterialNewBrand);
    }

    /**
     * 更新
     */
    @Override
    public int update(UseMaterialNewBrand useMaterialNewBrand) {
        return useMaterialNewBrandRepository.update(useMaterialNewBrand);
    }

    @Override
    public int updateProjectMaterialBrandPrivateId(String id,
                                                   String projectMaterialBrandPrivateId) {
        return useMaterialNewBrandRepository.updateProjectMaterialBrandPrivateId(id, projectMaterialBrandPrivateId);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return useMaterialNewBrandRepository.deleteById(id);
    }

    /**
     * 根据useMaterialId删除记录
     *
     * @param useMaterialId
     */
    @Override
    public int deleteByUseMaterialId(String useMaterialId) {
        return useMaterialNewBrandRepository.deleteByUseMaterialId(useMaterialId);
    }

    /**
     * 根据materialClassifyDivisionId删除记录
     *
     * @param materialClassifyDivisionId
     */
    @Override
    public int deleteByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return useMaterialNewBrandRepository.deleteByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    /**
     * 根据materialClassifyGroupId删除记录
     *
     * @param materialClassifyGroupId
     */
    @Override
    public int deleteByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return useMaterialNewBrandRepository.deleteByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    /**
     * 根据materialClassifySectionId删除记录
     *
     * @param materialClassifySectionId
     */
    @Override
    public int deleteByMaterialClassifySectionId(String materialClassifySectionId) {
        return useMaterialNewBrandRepository.deleteByMaterialClassifySectionId(materialClassifySectionId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return useMaterialNewBrandRepository.getCount();
    }

    /**
     * 根据useMaterialId得到数量
     *
     * @param useMaterialId
     */
    @Override
    public int getCountByUseMaterialId(String useMaterialId) {
        return useMaterialNewBrandRepository.getCountByUseMaterialId(useMaterialId);
    }

    /**
     * 根据materialClassifyDivisionId得到数量
     *
     * @param materialClassifyDivisionId
     */
    @Override
    public int getCountByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return useMaterialNewBrandRepository.getCountByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    /**
     * 根据materialClassifyGroupId得到数量
     *
     * @param materialClassifyGroupId
     */
    @Override
    public int getCountByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return useMaterialNewBrandRepository.getCountByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    /**
     * 根据materialClassifySectionId得到数量
     *
     * @param materialClassifySectionId
     */
    @Override
    public int getCountByMaterialClassifySectionId(String materialClassifySectionId) {
        return useMaterialNewBrandRepository.getCountByMaterialClassifySectionId(materialClassifySectionId);
    }

    /**
     * 根据id得到UseMaterialNewBrand
     *
     * @param id
     */
    @Override
    public UseMaterialNewBrand getById(String id) {
        return useMaterialNewBrandRepository.getById(id);
    }

    /**
     * 根据useMaterialId得到UseMaterialNewBrand
     *
     * @param useMaterialId
     */
    @Override
    public List<UseMaterialNewBrand> getByUseMaterialId(String useMaterialId) {
        return useMaterialNewBrandRepository.getByUseMaterialId(useMaterialId);
    }

    /**
     * 根据materialClassifyDivisionId得到UseMaterialNewBrand
     *
     * @param materialClassifyDivisionId
     */
    @Override
    public List<UseMaterialNewBrand> getByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return useMaterialNewBrandRepository.getByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    /**
     * 根据materialClassifyGroupId得到UseMaterialNewBrand
     *
     * @param materialClassifyGroupId
     */
    @Override
    public List<UseMaterialNewBrand> getByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return useMaterialNewBrandRepository.getByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    /**
     * 根据materialClassifySectionId得到UseMaterialNewBrand
     *
     * @param materialClassifySectionId
     */
    @Override
    public List<UseMaterialNewBrand> getByMaterialClassifySectionId(String materialClassifySectionId) {
        return useMaterialNewBrandRepository.getByMaterialClassifySectionId(materialClassifySectionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPage(int pageNo,
                                             int pageSize) {
        return useMaterialNewBrandRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByUseMaterialId(String useMaterialId,
                                                            int pageNo,
                                                            int pageSize) {
        return useMaterialNewBrandRepository.getPageByUseMaterialId(useMaterialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                         int pageNo,
                                                                         int pageSize) {
        return useMaterialNewBrandRepository.getPageByMaterialClassifyDivisionId(materialClassifyDivisionId, pageNo,
                                                                                 pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                                      int pageNo,
                                                                      int pageSize) {
        return useMaterialNewBrandRepository.getPageByMaterialClassifyGroupId(materialClassifyGroupId, pageNo,
                                                                              pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifySectionId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByMaterialClassifySectionId(String materialClassifySectionId,
                                                                        int pageNo,
                                                                        int pageSize) {
        return useMaterialNewBrandRepository.getPageByMaterialClassifySectionId(materialClassifySectionId, pageNo,
                                                                                pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandView> getPageView(int pageNo,
                                                     int pageSize) {
        Page<UseMaterialNewBrand> useMaterialNewBrandPage = getPage(pageNo, pageSize);
        return convertUseMaterialNewBrandPage2PageView(useMaterialNewBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandView> getPageViewByUseMaterialId(String useMaterialId,
                                                                    int pageNo,
                                                                    int pageSize) {
        Page<UseMaterialNewBrand> useMaterialNewBrandPage = getPageByUseMaterialId(useMaterialId, pageNo, pageSize);
        return convertUseMaterialNewBrandPage2PageView(useMaterialNewBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandView> getPageViewByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                                 int pageNo,
                                                                                 int pageSize) {
        Page<UseMaterialNewBrand> useMaterialNewBrandPage = getPageByMaterialClassifyDivisionId(
                materialClassifyDivisionId, pageNo, pageSize);
        return convertUseMaterialNewBrandPage2PageView(useMaterialNewBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandView> getPageViewByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                                              int pageNo,
                                                                              int pageSize) {
        Page<UseMaterialNewBrand> useMaterialNewBrandPage = getPageByMaterialClassifyGroupId(materialClassifyGroupId,
                                                                                             pageNo, pageSize);
        return convertUseMaterialNewBrandPage2PageView(useMaterialNewBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialClassifySectionId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandView> getPageViewByMaterialClassifySectionId(String materialClassifySectionId,
                                                                                int pageNo,
                                                                                int pageSize) {
        Page<UseMaterialNewBrand> useMaterialNewBrandPage = getPageByMaterialClassifySectionId(
                materialClassifySectionId, pageNo, pageSize);
        return convertUseMaterialNewBrandPage2PageView(useMaterialNewBrandPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private UseMaterialNewBrandView getUseMaterialNewBrandViewByUseMaterialNewBrandId(String id) {
        UseMaterialNewBrand useMaterialNewBrand = getById(id);
        if (useMaterialNewBrand == null) return null;
        UseMaterialNewBrandView useMaterialNewBrandView = new UseMaterialNewBrandView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param useMaterialNewBrandPage 页面对象
     */
    private Page<UseMaterialNewBrandView> convertUseMaterialNewBrandPage2PageView(Page<UseMaterialNewBrand> useMaterialNewBrandPage,
                                                                                  int pageNo,
                                                                                  int pageSize) {
        if (useMaterialNewBrandPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrandView> list = new ArrayList<>();
        for (UseMaterialNewBrand useMaterialNewBrand : useMaterialNewBrandPage.getResult()) {
            UseMaterialNewBrandView useMaterialNewBrandView = getUseMaterialNewBrandViewByUseMaterialNewBrandId(
                    useMaterialNewBrand.getId());
            if (useMaterialNewBrandView != null) list.add(useMaterialNewBrandView);
        }
        return new Page<>(startIndex, useMaterialNewBrandPage.getTotalCount(), pageSize, list);
    }

}