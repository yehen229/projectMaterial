package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifyGroupRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyDivisionService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyGroupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialClassifyGroupServiceImpl implements IMaterialClassifyGroupService {
    private final IMaterialClassifyGroupRepository materialClassifyGroupRepository;
    private final IMaterialClassifyDivisionService materialClassifyDivisionService;

    public MaterialClassifyGroupServiceImpl(IMaterialClassifyGroupRepository materialClassifyGroupRepository, IMaterialClassifyDivisionService materialClassifyDivisionService) {
        this.materialClassifyGroupRepository = materialClassifyGroupRepository;
        this.materialClassifyDivisionService = materialClassifyDivisionService;
    }

    /**
     * 增加
     */
    @Override
    public String add(MaterialClassifyGroup materialClassifyGroup) {
        return materialClassifyGroupRepository.add(materialClassifyGroup);
    }

    /**
     * 删除
     */
    @Override
    public int delete(MaterialClassifyGroup materialClassifyGroup) {
        return materialClassifyGroupRepository.delete(materialClassifyGroup);
    }

    /**
     * 更新
     */
    @Override
    public int update(MaterialClassifyGroup materialClassifyGroup) {
        return materialClassifyGroupRepository.update(materialClassifyGroup);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return materialClassifyGroupRepository.deleteById(id);
    }

    /**
     * 根据materialClassifyDivisionId删除记录
     *
     * @param materialClassifyDivisionId
     */
    @Override
    public int deleteByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return materialClassifyGroupRepository.deleteByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return materialClassifyGroupRepository.getCount();
    }

    /**
     * 根据materialClassifyDivisionId得到数量
     *
     * @param materialClassifyDivisionId
     */
    @Override
    public int getCountByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return materialClassifyGroupRepository.getCountByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    /**
     * 根据id得到MaterialClassifyGroup
     *
     * @param id
     */
    @Override
    public MaterialClassifyGroup getById(String id) {
        return materialClassifyGroupRepository.getById(id);
    }

    /**
     * 根据materialClassifyDivisionId得到MaterialClassifyGroup
     *
     * @param materialClassifyDivisionId
     */
    @Override
    public List<MaterialClassifyGroup> getByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return materialClassifyGroupRepository.getByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifyGroup> getPage(int pageNo, int pageSize) {
        return materialClassifyGroupRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    @Override
    public Page<MaterialClassifyGroup> getPageByMaterialClassifyDivisionId(String materialClassifyDivisionId, int pageNo, int pageSize) {
        return materialClassifyGroupRepository.getPageByMaterialClassifyDivisionId(materialClassifyDivisionId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifyGroupView> getPageView(int pageNo, int pageSize) {
        Page<MaterialClassifyGroup> materialClassifyGroupPage = getPage(pageNo, pageSize);
        return convertMaterialClassifyGroupPage2PageView(materialClassifyGroupPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    @Override
    public Page<MaterialClassifyGroupView> getPageViewByMaterialClassifyDivisionId(String materialClassifyDivisionId, int pageNo, int pageSize) {
        Page<MaterialClassifyGroup> materialClassifyGroupPage = getPageByMaterialClassifyDivisionId(materialClassifyDivisionId, pageNo, pageSize);
        return convertMaterialClassifyGroupPage2PageView(materialClassifyGroupPage, pageNo, pageSize);
    }

    @Override
    public MaterialClassifyGroupView getMaterialClassifyGroupView(MaterialClassifyGroup materialClassifyGroup) {
        if (materialClassifyGroup == null) return null;
        MaterialClassifyGroupView materialClassifyGroupView = new MaterialClassifyGroupView();
        materialClassifyGroupView.setMaterialClassifyGroup(materialClassifyGroup);
        materialClassifyGroupView.setMaterialClassifyDivision(materialClassifyDivisionService.getById(materialClassifyGroup.getMaterialClassifyDivisionId()));
        return materialClassifyGroupView;
    }


    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    @Override
    public MaterialClassifyGroupView getMaterialClassifyGroupViewById(String id) {
        MaterialClassifyGroup materialClassifyGroup = getById(id);
        return getMaterialClassifyGroupView(materialClassifyGroup);
    }

    @Override
    public MaterialClassifyTree getTree() {
        List<MaterialClassifyDivision> materialClassifyDivisionList = materialClassifyDivisionService.getAllList();
        if (materialClassifyDivisionList == null || materialClassifyDivisionList.isEmpty()) return null;
        MaterialClassifyTree tree = new MaterialClassifyTree();

        List<MaterialClassifyDivisionTreeItem> children = new ArrayList<>();
        for (MaterialClassifyDivision materialClassifyDivision : materialClassifyDivisionList) {
            MaterialClassifyDivisionTreeItem divisionTreeItem = getMaterialClassifyDivisionTreeItem(materialClassifyDivision);
            if (divisionTreeItem != null)
                children.add(divisionTreeItem);
        }
        tree.setChildren(children);
        return tree;
    }

    private MaterialClassifyGroupTreeItem getmaterialClassifyGroupTreeItem(MaterialClassifyGroup materialClassifyGroup) {
        MaterialClassifyGroupTreeItem materialClassifyGroupTreeItem = new MaterialClassifyGroupTreeItem();
        materialClassifyGroupTreeItem.setMaterialClassifyGroupView(getMaterialClassifyGroupView(materialClassifyGroup));
        materialClassifyGroupTreeItem.setChildren(null);
        return materialClassifyGroupTreeItem;
    }

    private List<MaterialClassifyGroupTreeItem> getMaterialClassifyDivisionChildren(MaterialClassifyDivision materialClassifyDivision) {
        if (materialClassifyDivision == null) return null;
        List<MaterialClassifyGroup> materialClassifyGroupList = getByMaterialClassifyDivisionId(materialClassifyDivision.getId());
        if (materialClassifyGroupList == null || materialClassifyGroupList.isEmpty()) return null;

        List<MaterialClassifyGroupTreeItem> materialClassifyGroupTreeItemList = new ArrayList<>();
        for (MaterialClassifyGroup materialClassifyGroup : materialClassifyGroupList) {
            MaterialClassifyGroupTreeItem materialClassifyGroupTreeItem = getmaterialClassifyGroupTreeItem(materialClassifyGroup);
            materialClassifyGroupTreeItemList.add(materialClassifyGroupTreeItem);
        }
        return materialClassifyGroupTreeItemList;

    }

    private MaterialClassifyDivisionTreeItem getMaterialClassifyDivisionTreeItem(MaterialClassifyDivision materialClassifyDivision) {
        if (materialClassifyDivision == null) return null;
        List<MaterialClassifyGroupTreeItem> children = getMaterialClassifyDivisionChildren(materialClassifyDivision);
        if (children == null || children.isEmpty()) return null;

        MaterialClassifyDivisionTreeItem divisionTreeItem = new MaterialClassifyDivisionTreeItem();
        divisionTreeItem.setMaterialClassifyDivision(materialClassifyDivision);
        divisionTreeItem.setChildren(children);
        return divisionTreeItem;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param materialClassifyGroupPage 页面对象
     */
    private Page<MaterialClassifyGroupView> convertMaterialClassifyGroupPage2PageView(Page<MaterialClassifyGroup> materialClassifyGroupPage, int pageNo, int pageSize) {
        if (materialClassifyGroupPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifyGroupView> list = new ArrayList<>();
        for (MaterialClassifyGroup materialClassifyGroup : materialClassifyGroupPage.getResult()) {
            MaterialClassifyGroupView materialClassifyGroupView = getMaterialClassifyGroupView(materialClassifyGroup);
            if (materialClassifyGroupView != null) list.add(materialClassifyGroupView);
        }
        return new Page<>(startIndex, materialClassifyGroupPage.getTotalCount(), pageSize, list);
    }

}