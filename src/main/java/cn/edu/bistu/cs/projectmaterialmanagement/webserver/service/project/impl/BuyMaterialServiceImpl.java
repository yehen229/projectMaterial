package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IBuyMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyMaterialServiceImpl implements IBuyMaterialService {
    private final IBuyMaterialRepository buyMaterialRepository;
    private final IUseMaterialService useMaterialService;
    private final IProjectMaterialService projectMaterialService;
    private final IProjectService projectService;
    private final IMaterialService materialService;

    public BuyMaterialServiceImpl(IBuyMaterialRepository buyMaterialRepository, IUseMaterialService useMaterialService, IProjectMaterialService projectMaterialService, IProjectService projectService, IMaterialService materialService) {
        this.buyMaterialRepository = buyMaterialRepository;
        this.useMaterialService = useMaterialService;
        this.projectMaterialService = projectMaterialService;
        this.projectService = projectService;
        this.materialService = materialService;
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

    @Override
    public BuyMaterial getByqrcode(String id) {
        return buyMaterialRepository.getByqrcode(id);
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

    //    根据二维码文本获取数据
    public List<BuyMaterial> getByQrcodeId(String qrcode) {

        return buyMaterialRepository.getByqrcodeId(qrcode);
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

    @Override
    public Page<BuyMaterialQrcodeShowView> getQrcodePageView(int pageNo,
                                                             int pageSize) {
//        先获取买材料的页面数据
        Page<BuyMaterial> buyMaterialPage = getPage(pageNo, pageSize);

        return convertBuyMaterialPage2QrCodePageVie(buyMaterialPage, pageNo, pageSize);
    }

    @Override
    public List<BuyMaterial> getallinfo() {
        return buyMaterialRepository.getallinfo();
    }

    @Override
    public Page<BuyMaterialQrcodeShowView> getQrcodePageViewbyinputkey(String projectname, int batch, String materialname, int pageNo, int pageSize) {
        //        先获取买材料的页面数据
//        Page<BuyMaterial> buyMaterialPage = getPage(pageNo, pageSize);
        List<BuyMaterial> buyMaterialPage = getallinfo();

        return filterAndConvertToQrcodeViewPage(buyMaterialPage, projectname, batch, materialname, pageNo, pageSize);
    }

    private Page<BuyMaterialQrcodeShowView> filterAndConvertToQrcodeViewPage(List<BuyMaterial> buyMaterialPage, String projectname, int batch, String materialname, int pageNo, int pageSize) {

        if (buyMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

//
        List<BuyMaterialQrcodeShowView> list = new ArrayList<>();

        for (BuyMaterial buyMaterial : buyMaterialPage) {
//      获取每一个id
            BuyMaterialQrcodeShowView buyQrcodeMaterialView = getQrcodeBuyMaterialViewByBuyMaterialId(buyMaterial.getId());


            if (buyQrcodeMaterialView != null) {
//                list.add(buyQrcodeMaterialView);
                BuyMaterial viewBuyMaterial = buyQrcodeMaterialView.getBuyMaterial();

                Material material = buyQrcodeMaterialView.getMaterial();
                if (material == null) continue;

                Project project = buyQrcodeMaterialView.getProject();

                if (projectname != null && project.getName() != null && batch != -1 && viewBuyMaterial.getBatch() != -1 && materialname != null && material.getName() != null) {
                    //                7.projectname  batch aterialname都查
//                    if (projectname.contains(project.getName()) && batch == viewBuyMaterial.getBatch() && materialname.contains(material.getName())) {
                    if (project.getName().contains(projectname) && batch == viewBuyMaterial.getBatch() && material.getName().contains(materialname)) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }

                } else if (batch != -1 && viewBuyMaterial.getBatch() != -1 && materialname != null && material.getName() != null) {
                    //                6.只查batch  与materialname
                    if (batch == viewBuyMaterial.getBatch() && material.getName().contains(materialname)) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }
                } else if (projectname != null && project.getName() != null && materialname != null && material.getName() != null) {
                    //                5.只查projectname 与 materialname
                    if (project.getName().contains(projectname) && material.getName().contains(materialname)) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }
                } else if (projectname != null && project.getName() != null && batch != -1 && viewBuyMaterial.getBatch() != -1) {
                    //                4.只查projectname 与 batch
                    if (project.getName().contains(projectname) && batch == viewBuyMaterial.getBatch()) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }
                } else if (materialname != null && material.getName() != null) {
                    //                3.只查materialname
                    if (material.getName().contains(materialname)) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }
                } else if (projectname != null && project.getName() != null) {
                    //                1.只查projectname
                    if (project.getName().contains(projectname)) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }
                } else if (batch != -1 && viewBuyMaterial.getBatch() != -1 && viewBuyMaterial.getBatch() != -1) {
                    //                2.只查batch
                    if (batch == viewBuyMaterial.getBatch()) {
                        list.add(buyQrcodeMaterialView);
                        continue;
                    }
                }
            }
        }

        List<BuyMaterialQrcodeShowView> returnlist = new ArrayList<>();
        for (int i = startIndex, k = 1; i < list.size(); i++, k++) {
            returnlist.add(list.get(i));
            if (k == pageSize) {
                break;
            }
        }

        return new Page<>(startIndex, list.size(), pageSize, returnlist);

    }

    private Page<BuyMaterialQrcodeShowView> convertBuyMaterialPage2QrCodePageVie(Page<BuyMaterial> buyMaterialPage, int pageNo, int pageSize) {

        if (buyMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<BuyMaterialQrcodeShowView> list = new ArrayList<>();

        for (BuyMaterial buyMaterial : buyMaterialPage.getResult()) {
//            获取每一个id
            BuyMaterialQrcodeShowView buyQrcodeMaterialView = getQrcodeBuyMaterialViewByBuyMaterialId(buyMaterial.getId());

            if (buyQrcodeMaterialView != null) list.add(buyQrcodeMaterialView);
        }
        return new Page<>(startIndex, buyMaterialPage.getTotalCount(), pageSize, list);

    }

    //通过id将 BuyMaterialQrcodeShowView 中数据查找出来
    private BuyMaterialQrcodeShowView getQrcodeBuyMaterialViewByBuyMaterialId(String id) {
        BuyMaterial buyMaterial = getById(id);
        if (buyMaterial == null) return null;

        BuyMaterialQrcodeShowView buyMaterialQrcodeShowView = new BuyMaterialQrcodeShowView();
//        将buymaterial表注入数据
        buyMaterialQrcodeShowView.setBuyMaterial(buyMaterial);
//        将Project表注入数据
        buyMaterialQrcodeShowView.setProject(getProject(id));
//        将material表注入数据
        buyMaterialQrcodeShowView.setMaterial(getMaterial(id));
        return buyMaterialQrcodeShowView;
    }

    //    通过buymaterial的id获取项目表的数据
    private Project getProject(String id) {
        // 1.       id来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialRepository.getById(id);
        //        2. 通过t_buy_material中的t_use_material_id到t_use_material表，
        String useMaterialId = buyMaterial.getUseMaterialId();
        UseMaterial useMaterialinfo = useMaterialService.getById(useMaterialId);
//        3.通过t_use_material表中的t_project_material_id到t_project_material表
        String projectMaterialId = useMaterialinfo.getProjectMaterialId();
        ProjectMaterial projectMaterialServiceById = projectMaterialService.getById(projectMaterialId);
//        4.在t_project_material表中获取t_project_id
        String projectId = projectMaterialServiceById.getProjectId();
        Project projectinfo = projectService.getById(projectId);

        return projectinfo;
    }

    //    通过buymaterial中的id获取材料表中的数据
    private Material getMaterial(String id) {
        // 1.       qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialRepository.getById(id);

        //材料数量
        BigDecimal materialCount = buyMaterial.getMaterialCount();
//        数量单位
        String materialUnit = buyMaterial.getMaterialUnit();
//        获取批次
        int batch = buyMaterial.getBatch();


//        2. 通过t_buy_material中的t_use_material_id到t_use_material表，
        String useMaterialId = buyMaterial.getUseMaterialId();
        UseMaterial useMaterialinfo = useMaterialService.getById(useMaterialId);
//      3.在t_use_material表中通过t_project_material_id到t_project_material表，
        String t_project_material_id = useMaterialinfo.getProjectMaterialId();
        ProjectMaterial projectMaterialInfo = projectMaterialService.getById(t_project_material_id);

//        4.在t_project_material表通过t_material_id到t_material表，
        String materialId = projectMaterialInfo.getMaterialId();
        Material materialInfo = materialService.getById(materialId);

        return materialInfo;
    }

//    private BuyMaterialView getBuyMaterialViewByBuyMaterialId(String id) {
//        BuyMaterial buyMaterial = getById(id);
//        if (buyMaterial == null) return null;
//
//        BuyMaterialView buyMaterialView = new BuyMaterialView();
//        buyMaterialView.setBuyMaterial(buyMaterial);
//
//
//        return buyMaterialView;
//    }

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

    @Override
    public List<BuyMaterial> getByBuyMaterialBatchId(String buyMaterialBatchId) {
        return buyMaterialRepository.getByBuyMaterialBatchId(buyMaterialBatchId);
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
        buyMaterialView.setBuyMaterial(buyMaterial);


        return buyMaterialView;
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