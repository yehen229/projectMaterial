package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifySectionRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyDivisionService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyGroupService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifySectionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialClassifySectionServiceImpl implements IMaterialClassifySectionService {
    private final IMaterialClassifySectionRepository materialClassifySectionRepository;
    private final IMaterialClassifyGroupService materialClassifyGroupService;
    private final IMaterialClassifyDivisionService materialClassifyDivisionService;

    public MaterialClassifySectionServiceImpl(IMaterialClassifySectionRepository materialClassifySectionRepository, IMaterialClassifyGroupService materialClassifyGroupService, IMaterialClassifyDivisionService materialClassifyDivisionService) {
        this.materialClassifySectionRepository = materialClassifySectionRepository;
        this.materialClassifyGroupService = materialClassifyGroupService;
        this.materialClassifyDivisionService = materialClassifyDivisionService;
    }

    private List<MaterialClassifySectionView> getMaterialClassifyGroupChildren(MaterialClassifyGroup materialClassifyGroup) {
        if (materialClassifyGroup == null) return null;
        List<MaterialClassifySection> materialClassifySectionList = getByMaterialClassifyGroupId(materialClassifyGroup.getId());
        if (materialClassifySectionList == null || materialClassifySectionList.isEmpty()) return null;

        List<MaterialClassifySectionView> materialClassifySectionViewList = new ArrayList<>();
        for (MaterialClassifySection materialClassifySection : materialClassifySectionList) {
            MaterialClassifySectionView materialClassifySectionView = getViewById(materialClassifySection.getId());
            if (materialClassifySectionView != null)
                materialClassifySectionViewList.add(materialClassifySectionView);
        }
        return materialClassifySectionViewList;
    }

    private MaterialClassifyGroupTreeItem getmaterialClassifyGroupTreeItem(MaterialClassifyGroup materialClassifyGroup) {
        MaterialClassifyGroupTreeItem materialClassifyGroupTreeItem = new MaterialClassifyGroupTreeItem();
        materialClassifyGroupTreeItem.setMaterialClassifyGroupView(materialClassifyGroupService.getMaterialClassifyGroupView(materialClassifyGroup));
        materialClassifyGroupTreeItem.setChildren(getMaterialClassifyGroupChildren(materialClassifyGroup));
        return materialClassifyGroupTreeItem;
    }

    private List<MaterialClassifyGroupTreeItem> getMaterialClassifyDivisionChildren(MaterialClassifyDivision materialClassifyDivision) {
        if (materialClassifyDivision == null) return null;
        List<MaterialClassifyGroup> materialClassifyGroupList = materialClassifyGroupService.getByMaterialClassifyDivisionId(materialClassifyDivision.getId());
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
        MaterialClassifyDivisionTreeItem divisionTreeItem = new MaterialClassifyDivisionTreeItem();
        divisionTreeItem.setMaterialClassifyDivision(materialClassifyDivision);
        divisionTreeItem.setChildren(getMaterialClassifyDivisionChildren(materialClassifyDivision));
        return divisionTreeItem;
    }

    /**
     * 增加
     */
    @Override
    public String add(MaterialClassifySection materialClassifySection) {
        return materialClassifySectionRepository.add(materialClassifySection);
    }

    /**
     * 删除
     */
    @Override
    public int delete(MaterialClassifySection materialClassifySection) {
        return materialClassifySectionRepository.delete(materialClassifySection);
    }

    /**
     * 更新
     */
    @Override
    public int update(MaterialClassifySection materialClassifySection) {
        return materialClassifySectionRepository.update(materialClassifySection);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return materialClassifySectionRepository.deleteById(id);
    }

    /**
     * 根据materialClassifyGroupId删除记录
     *
     * @param materialClassifyGroupId
     */
    @Override
    public int deleteByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return materialClassifySectionRepository.deleteByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return materialClassifySectionRepository.getCount();
    }

    /**
     * 根据materialClassifyGroupId得到数量
     *
     * @param materialClassifyGroupId
     */
    @Override
    public int getCountByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return materialClassifySectionRepository.getCountByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    /**
     * 根据id得到MaterialClassifySection
     *
     * @param id
     */
    @Override
    public MaterialClassifySection getById(String id) {
        return materialClassifySectionRepository.getById(id);
    }

    @Override
    public MaterialClassifySectionView getViewById(String id) {
        MaterialClassifySection materialClassifySection = getById(id);
        if (materialClassifySection == null) return null;
        MaterialClassifySectionView materialClassifySectionView = new MaterialClassifySectionView();
        materialClassifySectionView.setMaterialClassifySection(materialClassifySection);
        MaterialClassifyGroup materialClassifyGroup = materialClassifyGroupService.getById(materialClassifySection.getMaterialClassifyGroupId());
        materialClassifySectionView.setMaterialClassifyGroup(materialClassifyGroup);
        materialClassifySectionView.setMaterialClassifyDivision(materialClassifyDivisionService.getById(materialClassifyGroup.getMaterialClassifyDivisionId()));
        return materialClassifySectionView;
    }

    /**
     * 根据materialClassifyGroupId得到MaterialClassifySection
     *
     * @param materialClassifyGroupId
     */
    @Override
    public List<MaterialClassifySection> getByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return materialClassifySectionRepository.getByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifySection> getPage(int pageNo, int pageSize) {
        return materialClassifySectionRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    @Override
    public Page<MaterialClassifySection> getPageByMaterialClassifyGroupId(String materialClassifyGroupId, int pageNo, int pageSize) {
        return materialClassifySectionRepository.getPageByMaterialClassifyGroupId(materialClassifyGroupId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifySectionView> getPageView(int pageNo, int pageSize) {
        Page<MaterialClassifySection> materialClassifySectionPage = getPage(pageNo, pageSize);
        return convertMaterialClassifySectionPage2PageView(materialClassifySectionPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    @Override
    public Page<MaterialClassifySectionView> getPageViewByMaterialClassifyGroupId(String materialClassifyGroupId, int pageNo, int pageSize) {
        Page<MaterialClassifySection> materialClassifySectionPage = getPageByMaterialClassifyGroupId(materialClassifyGroupId, pageNo, pageSize);
        return convertMaterialClassifySectionPage2PageView(materialClassifySectionPage, pageNo, pageSize);
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

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private MaterialClassifySectionView getMaterialClassifySectionViewByMaterialClassifySectionId(String id) {
        MaterialClassifySection materialClassifySection = getById(id);
        if (materialClassifySection == null) return null;
        MaterialClassifySectionView materialClassifySectionView = new MaterialClassifySectionView();
        materialClassifySectionView.setMaterialClassifySection(materialClassifySection);
        if (materialClassifySection.getMaterialClassifyGroupId() != null) {
            MaterialClassifyGroup materialClassifyGroup = materialClassifyGroupService.getById(materialClassifySection.getMaterialClassifyGroupId());
            materialClassifySectionView.setMaterialClassifyGroup(materialClassifyGroup);
            if (materialClassifyGroup != null) {
                MaterialClassifyDivision materialClassifyDivision = materialClassifyDivisionService.getById(materialClassifyGroup.getMaterialClassifyDivisionId());
                materialClassifySectionView.setMaterialClassifyDivision(materialClassifyDivision);
            }
        }

        return materialClassifySectionView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param materialClassifySectionPage 页面对象
     */
    private Page<MaterialClassifySectionView> convertMaterialClassifySectionPage2PageView(Page<MaterialClassifySection> materialClassifySectionPage, int pageNo, int pageSize) {
        if (materialClassifySectionPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifySectionView> list = new ArrayList<>();
        for (MaterialClassifySection materialClassifySection : materialClassifySectionPage.getResult()) {
            MaterialClassifySectionView materialClassifySectionView = getMaterialClassifySectionViewByMaterialClassifySectionId(materialClassifySection.getId());
            if (materialClassifySectionView != null) list.add(materialClassifySectionView);
        }
        return new Page<>(startIndex, materialClassifySectionPage.getTotalCount(), pageSize, list);
    }

}