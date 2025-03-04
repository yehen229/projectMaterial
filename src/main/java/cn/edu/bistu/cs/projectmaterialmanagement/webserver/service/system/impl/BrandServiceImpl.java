package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifySectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements IBrandService {

    private static final Logger log =
            LoggerFactory.getLogger(BrandServiceImpl.class);

    private final IBrandRepository brandRepository;
    private final IMaterialClassifySectionService materialClassifySectionService;

    public BrandServiceImpl(IBrandRepository brandRepository,
                            IMaterialClassifySectionService materialClassifySectionService) {
        this.brandRepository = brandRepository;
        this.materialClassifySectionService = materialClassifySectionService;
    }

    /**
     * 增加
     */
    @Override
    public String add(Brand brand) {
        return brandRepository.add(brand);
    }

    /**
     * 删除
     */
    @Override
    public int delete(Brand brand) {
        return brandRepository.delete(brand);
    }

    /**
     * 更新
     */
    @Override
    public int update(Brand brand) {
        return brandRepository.update(brand);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return brandRepository.deleteById(id);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return brandRepository.getCount();
    }

    /**
     * 根据id得到Brand
     *
     * @param id
     */
    @Override
    public Brand getById(String id) {
        return brandRepository.getById(id);
    }

    @Override
    public Brand getByNameAndMaterialClassifySectionIdAndPosition(String brandName,
                                                                  String materialClassifySectionId,
                                                                  String position) {
        return brandRepository.getByNameAndMaterialClassifySectionIdAndPosition(brandName, materialClassifySectionId,
                                                                                position);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Brand> getPage(int pageNo,
                               int pageSize) {
        return brandRepository.getPage(pageNo, pageSize);
    }

    @Override
    public BrandView getViewById(String brandId) {
        BrandView brandView = new BrandView();
        brandView.setBrand(getById(brandId));
        brandView.setMaterialClassifySectionView(
                materialClassifySectionService.getViewById(getById(brandId).getMaterialClassifySectionId()));
        return brandView;
    }

}