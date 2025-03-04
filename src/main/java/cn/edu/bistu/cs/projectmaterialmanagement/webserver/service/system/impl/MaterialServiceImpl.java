package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements IMaterialService {

    private static final Logger log =
            LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final IMaterialRepository materialRepository;

    public MaterialServiceImpl(IMaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(Material material) {
        return materialRepository.add(material);
    }

    /**
     * 删除
     */
    @Override
    public int delete(Material material) {
        return materialRepository.delete(material);
    }

    /**
     * 更新
     */
    @Override
    public int update(Material material) {
        return materialRepository.update(material);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return materialRepository.deleteById(id);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return materialRepository.getCount();
    }

    /**
     * 根据id得到Material
     *
     * @param id
     */
    @Override
    public Material getById(String id) {
        return materialRepository.getById(id);
    }

    @Override
    public List<Material> getByMaterialClassifySectionId(String materialClassifySectionId) {
        return materialRepository.getByMaterialClassifySectionId(materialClassifySectionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Material> getPage(int pageNo, int pageSize) {
        return materialRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<Material> getPageByMaterialClassifySectionId(String classifySectionId, Integer pageNo, Integer pageSize) {
        return materialRepository.getPageByMaterialClassifySectionId(classifySectionId, pageNo, pageSize);
    }

    @Override
    public Page<Material> getPageByItemMark(String itemMark, Integer pageNo, Integer pageSize) {
        return materialRepository.getPageByItemMark(itemMark, pageNo, pageSize);
    }

    @Override
    public Page<Material> getPageByLocation(String location, Integer pageNo, Integer pageSize) {
        return materialRepository.getPageByLocation(location, pageNo, pageSize);
    }

    @Override
    public Page<Material> getPageByName(String name, Integer pageNo, Integer pageSize) {
        return materialRepository.getPageByName(name, pageNo, pageSize);
    }

}