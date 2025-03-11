package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhoto;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandPublicService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialPhotoService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("qrcode/v1")
@EnableMethodSecurity
public class QrcodeController {
    private final IBuyMaterialService buyMaterialService;

    private final IUserService userService;

    private final IUseMaterialService useMaterialService;

    private final IProjectMaterialService projectMaterialService;

    private final IMaterialService materialService;

    private final IProjectService projectService;
    private final IProjectBusinessService projectBusinessService;
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;

    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;
    private final IProjectBrandService projectBrandService;
    private final IBrandService brandService;
    private final IBrandPublicService brandPublicService;

    private final IMaterialPhotoService materialPhotoService;


    QrcodeController( IBuyMaterialService buyMaterialService, IUserService userService, IUseMaterialService useMaterialService, IProjectMaterialService projectMaterialService, IMaterialService materialService, IProjectService projectService, IProjectBusinessService projectBusinessService, IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService, IProjectMaterialBrandPublicService projectMaterialBrandPublicService, IProjectBrandService projectBrandService, IBrandService brandService, IBrandPublicService brandPublicService, IMaterialPhotoService materialPhotoService) {

        this.buyMaterialService = buyMaterialService;
        this.userService = userService;
        this.useMaterialService = useMaterialService;
        this.projectMaterialService = projectMaterialService;
        this.materialService = materialService;
        this.projectService = projectService;
        this.projectBusinessService = projectBusinessService;
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;
        this.projectBrandService = projectBrandService;
        this.brandService = brandService;
        this.brandPublicService = brandPublicService;
        this.materialPhotoService = materialPhotoService;
    }


    @GetMapping("/kk")
    public Page<BuyMaterialView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return buyMaterialService.getPageView(pageNo, pageSize);
    }

    @GetMapping("getUserInfo")
    public User test(String qrcode) {
//        qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialService.getById(qrcode);

//        通过用户id获取用户信息
        String userId = buyMaterial.getUserId();
        User byId = userService.getById(userId);

        return byId;
    }


    //    通过单个qrcode获取材料信息
    @GetMapping("getMaterialInfo")
    public MaterialInfo test1(String qrcode) {


        // 1.       qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialService.getByqrcode(qrcode);

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

//        获取项目id
        String projectId = projectMaterialInfo.getProjectId();

        MaterialInfo end = new MaterialInfo();

        end.setBatch(batch);
        end.setMaterialCount(materialCount);
        end.setMaterialUnit(materialUnit);
        end.setMaterialInfo(materialInfo);

        return end;
    }

    @GetMapping("getprojectInfo")
    public Project test2(String qrcode) {
        // 1.       qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialService.getByqrcode(qrcode);
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

    @GetMapping("getCompanyInfo")
    public ProjectView test3(String qrcode) {
        // 1.       qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialService.getByqrcode(qrcode);
        //        2. 通过t_buy_material中的t_use_material_id到t_use_material表，
        String useMaterialId = buyMaterial.getUseMaterialId();
        UseMaterial useMaterialinfo = useMaterialService.getById(useMaterialId);
//        3.通过t_use_material表中的t_project_material_id到t_project_material表
        String projectMaterialId = useMaterialinfo.getProjectMaterialId();
        ProjectMaterial projectMaterialServiceById = projectMaterialService.getById(projectMaterialId);
//        4.在t_project_material表中获取t_project_id
        String projectId = projectMaterialServiceById.getProjectId();

        ProjectView projectViewById = projectBusinessService.getProjectViewById(projectId);
        return projectViewById;
    }
    //

    //    @GetMapping("getListMaterialInfo")
//    public List<Material> test2(String qrcode) {
//
////        List<BuyMaterial> buyMaterialServiceByQrcodeId = buyMaterialService.getByQrcodeId(qrcode);
////
////
////        List<Material> materialList = new ArrayList<>();
////
////        for (int i = 0; i < buyMaterialServiceByQrcodeId.size(); i++) {
////            BuyMaterial item = buyMaterialServiceByQrcodeId.get(i);
//////            System.out.println(item);
////            materialList.add(test1(item.get()));
////        }
////        return materialList;
//        return;
//
//    }
//
//    获取
    @GetMapping("getQrcodeImage")
    public Page<BuyMaterialQrcodeShowView> test3(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 4 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return buyMaterialService.getQrcodePageView(pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-key")
    Page<BuyMaterialQrcodeShowView> getPageViewByKeyword(
            @RequestParam(value = "projectname", required = false) String projectname,
            @RequestParam(value = "batch", required = false) Integer batch,
            @RequestParam(value = "materialname", required = false) String materialname,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 4 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        batch = batch == null ? -1 : batch;
        return buyMaterialService.getQrcodePageViewbyinputkey(projectname, batch, materialname, pageNo, pageSize);
    }


    //通过qrcode获取材料照片信息
    @GetMapping("getbranandpositon")
    public Brand test4(String qrcode) {
//        1.根据qrcode找到私有品牌或者公有品牌id
        BuyMaterial buyMaterial = buyMaterialService.getByqrcode(qrcode);
        Brand brandInfo = new Brand();
//        私有品牌不为空则从私有品牌入手
        if (buyMaterial.getProjectMaterialBrandPrivateId() != null) {
//            获取私有品牌id
            String privateId = buyMaterial.getProjectMaterialBrandPrivateId();
//           2. 私有品牌 根据id到 t_project_material_brand_private获取t_project_brand_id
            ProjectMaterialBrandPrivate brandPrivateServiceById = projectMaterialBrandPrivateService.getById(privateId);
            String projectBrandId = brandPrivateServiceById.getProjectBrandId();
//            3.根据id 到t_project_brand获取t_brand_id
            ProjectBrand projectBrandServiceById = projectBrandService.getById(projectBrandId);
            String brandId = projectBrandServiceById.getBrandId();
//            4.根据id到t_brand表获取name与position
            brandInfo = brandService.getById(brandId);
        }
        //        公有品牌不为空则从私有品牌入手
        if (buyMaterial.getProjectMaterialBrandPublicId() != null) {
//            获取私有品牌id
            String publicId = buyMaterial.getProjectMaterialBrandPublicId();
//           2. 公有品牌 根据id到 t_project_material_brand_public获取t_brand_public_id
            ProjectMaterialBrandPublic projectMaterialBrandPublicServiceById = projectMaterialBrandPublicService.getById(publicId);
            String projectBrandId = projectMaterialBrandPublicServiceById.getBrandPublicId();
//            3.根据id 到t_brand_public获取t_brand_id
            BrandPublic brandPublicServiceById = brandPublicService.getById(projectBrandId);
            String brandId = brandPublicServiceById.getBrandId();
//            4.根据id到t_brand表获取name与position
            brandInfo = brandService.getById(brandId);
        }
        return brandInfo;

    }

    @GetMapping("getmaterialPhotoByqrcode")
    public List<MaterialPhoto> test5(String qrcode) {

        // 1.       qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialService.getByqrcode(qrcode);

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

//        4.在t_project_material表通过t_material_id到t_material_photo表，
        String materialId = projectMaterialInfo.getMaterialId();
        List<MaterialPhoto> materialPhotoList = materialPhotoService.getByMaterialId(materialId);

        return materialPhotoList;

    }

    @GetMapping("getfilebyqrcode")
    public List<ProjectMaterialVerificationDocumentView> test6(@RequestParam("qrcode") String qrcode) {
        // 1.       qrcode来找到t_buy_material表的相应数据
        BuyMaterial buyMaterial = buyMaterialService.getByqrcode(qrcode);
        String buyMaterialid = buyMaterial.getId();
//        System.out.println(buyMaterialid);
        return projectBusinessService.getListMaterialVerificationDocumentViewByBuyMaterialId(buyMaterialid);
    }
}
