package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyDivisionService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyGroupService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifySectionService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UseMaterialBusinessServiceImpl implements IUseMaterialBusinessService {
    private final IUseMaterialNewBrandFileService useMaterialNewBrandFileService;
    private final IUseMaterialNewBrandService useMaterialNewBrandService;
    private final IUseMaterialBrandSelectService useMaterialBrandSelectService;
    private final IUseMaterialService useMaterialService;
    private final IUserService userService;
    private final IProjectMaterialService projectMaterialService;
    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;
    private final IMaterialClassifyDivisionService materialClassifyDivisionService;
    private final IMaterialClassifyGroupService materialClassifyGroupService;
    private final IMaterialClassifySectionService materialClassifySectionService;
    private final IProjectService projectService;
    private final IProjectMaterialBusinessService projectMaterialBusinessService;
    private final IMaterialService materialService;

    public UseMaterialBusinessServiceImpl(IUseMaterialNewBrandFileService useMaterialNewBrandFileService,
                                          IUseMaterialNewBrandService useMaterialNewBrandService,
                                          IUseMaterialBrandSelectService useMaterialBrandSelectService,
                                          IUseMaterialService useMaterialService,
                                          IUserService userService,
                                          IProjectMaterialService projectMaterialService,
                                          IProjectMaterialBrandPublicService projectMaterialBrandPublicService,
                                          IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService,
                                          IMaterialClassifyDivisionService materialClassifyDivisionService,
                                          IMaterialClassifyGroupService materialClassifyGroupService,
                                          IMaterialClassifySectionService materialClassifySectionService,
                                          IProjectService projectService,
                                          IProjectMaterialBusinessService projectMaterialBusinessService,
                                          IMaterialService materialService) {
        this.useMaterialNewBrandFileService = useMaterialNewBrandFileService;
        this.useMaterialNewBrandService = useMaterialNewBrandService;
        this.useMaterialBrandSelectService = useMaterialBrandSelectService;
        this.useMaterialService = useMaterialService;
        this.userService = userService;
        this.projectMaterialService = projectMaterialService;
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
        this.materialClassifyDivisionService = materialClassifyDivisionService;
        this.materialClassifyGroupService = materialClassifyGroupService;
        this.materialClassifySectionService = materialClassifySectionService;
        this.projectService = projectService;
        this.projectMaterialBusinessService = projectMaterialBusinessService;
        this.materialService = materialService;
    }


    @Override
    public UseMaterialNewBrandView getViewByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        UseMaterialNewBrand useMaterialNewBrand = useMaterialNewBrandService.getById(useMaterialNewBrandId);
        if (useMaterialNewBrand == null) return null;
        UseMaterialNewBrandView useMaterialNewBrandView = new UseMaterialNewBrandView();
        useMaterialNewBrandView.setMaterialClassifySection(
                materialClassifySectionService.getById(useMaterialNewBrand.getMaterialClassifySectionId()));
        useMaterialNewBrandView.setMaterialClassifyGroup(
                materialClassifyGroupService.getById(useMaterialNewBrand.getMaterialClassifyGroupId()));
        useMaterialNewBrandView.setMaterialClassifyDivision(
                materialClassifyDivisionService.getById(useMaterialNewBrand.getMaterialClassifyDivisionId()));
        useMaterialNewBrandView.setUseMaterialNewBrand(useMaterialNewBrand);
        useMaterialNewBrandView.setUseMaterialNewBrandFileViewList(
                useMaterialNewBrandFileService.getViewListByUseMaterialNewBrandId(useMaterialNewBrandId));
        useMaterialNewBrandView.setUseMaterial(useMaterialService.getById(useMaterialNewBrand.getUseMaterialId()));
        return useMaterialNewBrandView;
    }

    @Override
    public UseMaterialView getViewByUseMaterialId(String useMaterialId) {
        UseMaterial useMaterial = useMaterialService.getById(useMaterialId);
        if (useMaterial == null) return null;
        UseMaterialView useMaterialView = new UseMaterialView();
        useMaterialView.setUseMaterial(useMaterial);
        useMaterialView.setUser(userService.getById(useMaterial.getUserId()));
        useMaterialView.setProjectMaterialView(
                projectMaterialBusinessService.getViewById(useMaterial.getProjectMaterialId()));
        useMaterialView.setProjectMaterialBrandPrivateView(
                projectMaterialBrandPrivateService.getViewById(useMaterial.getProjectMaterialBrandPrivateId()));
        useMaterialView.setProjectMaterialBrandPublicView(
                projectMaterialBrandPublicService.getViewById(useMaterial.getProjectMaterialBrandPublicId()));

        String materialId=useMaterial.getMaterialId();
        if(materialId!=null && !materialId.isEmpty())
            useMaterialView.setMaterial(materialService.getById(materialId));

        List<UseMaterialNewBrand> useMaterialNewBrandList = useMaterialNewBrandService.getByUseMaterialId(
                useMaterialId);
        if (useMaterialNewBrandList != null && !useMaterialNewBrandList.isEmpty())
            useMaterialView.setUseMaterialNewBrandView(
                    getViewByUseMaterialNewBrandId(useMaterialNewBrandList.getFirst().getId()));
        return useMaterialView;
    }

    @Override
    public List<UseMaterialView> getViewListByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        List<UseMaterial> useMaterialList = useMaterialService.getByUseMaterialBrandSelectId(useMaterialBrandSelectId);
        if (useMaterialList == null || useMaterialList.isEmpty()) return null;
        return useMaterialList.stream().map(useMaterial -> getViewByUseMaterialId(useMaterial.getId())).collect(
                Collectors.toList());
    }

    @Override
    public UseMaterialBrandSelectView getViewByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(useMaterialBrandSelectId);
        if (useMaterialBrandSelect == null) return null;
        UseMaterialBrandSelectView useMaterialBrandSelectView = new UseMaterialBrandSelectView();
        useMaterialBrandSelectView.setProject(projectService.getById(useMaterialBrandSelect.getProjectId()));
        useMaterialBrandSelectView.setUseMaterialBrandSelect(useMaterialBrandSelect);
        useMaterialBrandSelectView.setUseMaterialViewList(
                getViewListByUseMaterialBrandSelectId(useMaterialBrandSelectId));
        useMaterialBrandSelectView.setUser(userService.getById(useMaterialBrandSelect.getUserId()));
        return useMaterialBrandSelectView;

    }


    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialView> getPageView(int pageNo,
                                             int pageSize) {
        Page<UseMaterial> useMaterialPage = useMaterialService.getPage(pageNo, pageSize);
        return convertUseMaterialPage2PageView(useMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<UseMaterialView> getPageViewByProjectMaterialId(String projectMaterialId,
                                                                int pageNo,
                                                                int pageSize) {
        Page<UseMaterial> useMaterialPage = useMaterialService.getPageByProjectMaterialId(projectMaterialId, pageNo,
                                                                                          pageSize);
        return convertUseMaterialPage2PageView(useMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialView> getPageViewByUserId(String userId,
                                                     int pageNo,
                                                     int pageSize) {
        Page<UseMaterial> useMaterialPage = useMaterialService.getPageByUserId(userId, pageNo, pageSize);
        return convertUseMaterialPage2PageView(useMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<UseMaterialView> getPageViewByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                            int pageNo,
                                                                            int pageSize) {
        Page<UseMaterial> useMaterialPage = useMaterialService.getPageByProjectMaterialBrandPrivateId(
                projectMaterialBrandPrivateId,
                pageNo, pageSize);
        return convertUseMaterialPage2PageView(useMaterialPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<UseMaterialView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                           int pageNo,
                                                                           int pageSize) {
        Page<UseMaterial> useMaterialPage = useMaterialService.getPageByProjectMaterialBrandPublicId(
                projectMaterialBrandPublicId, pageNo,
                pageSize);
        return convertUseMaterialPage2PageView(useMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<UseMaterialView> getReviewedAndApprovedPageViewByProjectId(String projectId,
                                                                           Integer pageNo,
                                                                           Integer pageSize) {
        Page<UseMaterial> useMaterialPage = useMaterialService.getReviewedAndApprovedPageByProjectId(projectId, pageNo,
                                                                                                     pageSize);
        return convertReviewedAndApprovedUseMaterialPage2PageView(useMaterialPage, pageNo, pageSize);

    }

    private Page<UseMaterialView> convertReviewedAndApprovedUseMaterialPage2PageView(Page<UseMaterial> useMaterialPage,
                                                                                     int pageNo,
                                                                                     int pageSize) {
        if (useMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialView> list = new ArrayList<>();
        for (UseMaterial useMaterial : useMaterialPage.getResult()) {
            UseMaterialView useMaterialView = getReviewedAndApprovedViewByUseMaterialId(useMaterial.getId());
            if (useMaterialView != null) list.add(useMaterialView);
        }
        return new Page<>(startIndex, useMaterialPage.getTotalCount(), pageSize, list);
    }

    private UseMaterialView getReviewedAndApprovedViewByUseMaterialId(String useMaterialId) {
        UseMaterial useMaterial = useMaterialService.getById(useMaterialId);
        if (useMaterial == null) return null;
        UseMaterialView useMaterialView = new UseMaterialView();
        useMaterialView.setUseMaterial(useMaterial);
        useMaterialView.setUser(userService.getById(useMaterial.getUserId()));
        useMaterialView.setProjectMaterialView(
                projectMaterialBusinessService.getViewById(useMaterial.getProjectMaterialId()));

        //品牌可以是共有品牌，可以是私有品牌，也可以是新品牌
        if (useMaterial.getProjectMaterialBrandPrivateId() != null)
            useMaterialView.setProjectMaterialBrandPrivateView(
                    projectMaterialBrandPrivateService.getViewById(useMaterial.getProjectMaterialBrandPrivateId()));
        else if (useMaterial.getProjectMaterialBrandPublicId() != null)
            useMaterialView.setProjectMaterialBrandPublicView(
                    projectMaterialBrandPublicService.getViewById(useMaterial.getProjectMaterialBrandPublicId()));
        else {
            //用户选择了新品牌
            List<UseMaterialNewBrand> useMaterialNewBrandList = useMaterialNewBrandService.getByUseMaterialId(
                    useMaterial.getId());
            if (useMaterialNewBrandList != null && !useMaterialNewBrandList.isEmpty()) {
                UseMaterialNewBrand useMaterialNewBrand = useMaterialNewBrandList.getFirst();
                useMaterialView.setProjectMaterialBrandPrivateView(projectMaterialBrandPrivateService.getViewById(
                        useMaterialNewBrand.getProjectMaterialBrandPrivateId()));

            }

        }
        List<UseMaterialNewBrand> useMaterialNewBrandList = useMaterialNewBrandService.getByUseMaterialId(
                useMaterialId);
        if (useMaterialNewBrandList != null && !useMaterialNewBrandList.isEmpty())
            useMaterialView.setUseMaterialNewBrandView(
                    getViewByUseMaterialNewBrandId(useMaterialNewBrandList.get(0).getId()));
        return useMaterialView;
    }


    /**
     * 将页面转换为视图页面
     *
     * @param useMaterialPage 页面对象
     */
    private Page<UseMaterialView> convertUseMaterialPage2PageView(Page<UseMaterial> useMaterialPage,
                                                                  int pageNo,
                                                                  int pageSize) {
        if (useMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialView> list = new ArrayList<>();
        for (UseMaterial useMaterial : useMaterialPage.getResult()) {
            UseMaterialView useMaterialView = getViewByUseMaterialId(useMaterial.getId());
            if (useMaterialView != null) list.add(useMaterialView);
        }
        return new Page<>(startIndex, useMaterialPage.getTotalCount(), pageSize, list);
    }

}
