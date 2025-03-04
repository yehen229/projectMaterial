package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialBrandSelectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialBrandSelectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UseMaterialBrandSelectServiceImpl implements IUseMaterialBrandSelectService {
    private final IUseMaterialBrandSelectRepository useMaterialBrandSelectRepository;

    public UseMaterialBrandSelectServiceImpl(IUseMaterialBrandSelectRepository useMaterialBrandSelectRepository) {
        this.useMaterialBrandSelectRepository = useMaterialBrandSelectRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(UseMaterialBrandSelect useMaterialBrandSelect) {
        return useMaterialBrandSelectRepository.add(useMaterialBrandSelect);
    }

    /**
     * 删除
     */
    @Override
    public int delete(UseMaterialBrandSelect useMaterialBrandSelect) {
        return useMaterialBrandSelectRepository.delete(useMaterialBrandSelect);
    }

    /**
     * 更新
     */
    @Override
    public int update(UseMaterialBrandSelect useMaterialBrandSelect) {
        return useMaterialBrandSelectRepository.update(useMaterialBrandSelect);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return useMaterialBrandSelectRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return useMaterialBrandSelectRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return useMaterialBrandSelectRepository.deleteByProjectId(projectId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return useMaterialBrandSelectRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return useMaterialBrandSelectRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return useMaterialBrandSelectRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据id得到UseMaterialBrandSelect
     *
     * @param id
     */
    @Override
    public UseMaterialBrandSelect getById(String id) {
        return useMaterialBrandSelectRepository.getById(id);
    }

    /**
     * 根据userId得到UseMaterialBrandSelect
     *
     * @param userId
     */
    @Override
    public List<UseMaterialBrandSelect> getByUserId(String userId) {
        return useMaterialBrandSelectRepository.getByUserId(userId);
    }

    /**
     * 根据projectId得到UseMaterialBrandSelect
     *
     * @param projectId
     */
    @Override
    public List<UseMaterialBrandSelect> getByProjectId(String projectId) {
        return useMaterialBrandSelectRepository.getByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelect> getPage(int pageNo, int pageSize) {
        return useMaterialBrandSelectRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelect> getPageByUserId(String userId, int pageNo, int pageSize) {
        return useMaterialBrandSelectRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelect> getPageByProjectId(String projectId, int pageNo, int pageSize) {
        return useMaterialBrandSelectRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelectView> getPageView(int pageNo, int pageSize) {
        Page<UseMaterialBrandSelect> useMaterialBrandSelectPage = getPage(pageNo, pageSize);
        return convertUseMaterialBrandSelectPage2PageView(useMaterialBrandSelectPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelectView> getPageViewByUserId(String userId, int pageNo, int pageSize) {
        Page<UseMaterialBrandSelect> useMaterialBrandSelectPage = getPageByUserId(userId, pageNo, pageSize);
        return convertUseMaterialBrandSelectPage2PageView(useMaterialBrandSelectPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelectView> getPageViewByProjectId(String projectId, int pageNo, int pageSize) {
        Page<UseMaterialBrandSelect> useMaterialBrandSelectPage = getPageByProjectId(projectId, pageNo, pageSize);
        return convertUseMaterialBrandSelectPage2PageView(useMaterialBrandSelectPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private UseMaterialBrandSelectView getUseMaterialBrandSelectViewByUseMaterialBrandSelectId(String id) {
        UseMaterialBrandSelect useMaterialBrandSelect = getById(id);
        if (useMaterialBrandSelect == null) return null;
        UseMaterialBrandSelectView useMaterialBrandSelectView = new UseMaterialBrandSelectView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param useMaterialBrandSelectPage 页面对象
     */
    private Page<UseMaterialBrandSelectView> convertUseMaterialBrandSelectPage2PageView(Page<UseMaterialBrandSelect> useMaterialBrandSelectPage, int pageNo, int pageSize) {
        if (useMaterialBrandSelectPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialBrandSelectView> list = new ArrayList<>();
        for (UseMaterialBrandSelect useMaterialBrandSelect : useMaterialBrandSelectPage.getResult()) {
            UseMaterialBrandSelectView useMaterialBrandSelectView = getUseMaterialBrandSelectViewByUseMaterialBrandSelectId(useMaterialBrandSelect.getId());
            if (useMaterialBrandSelectView != null) list.add(useMaterialBrandSelectView);
        }
        return new Page<>(startIndex, useMaterialBrandSelectPage.getTotalCount(), pageSize, list);
    }

}