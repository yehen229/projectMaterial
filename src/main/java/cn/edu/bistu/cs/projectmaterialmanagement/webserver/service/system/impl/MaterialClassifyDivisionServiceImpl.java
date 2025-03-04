package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyDivision;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifyDivisionRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyDivisionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialClassifyDivisionServiceImpl implements IMaterialClassifyDivisionService {
    private final IMaterialClassifyDivisionRepository materialClassifyDivisionRepository;


    public MaterialClassifyDivisionServiceImpl(IMaterialClassifyDivisionRepository materialClassifyDivisionRepository) {
        this.materialClassifyDivisionRepository = materialClassifyDivisionRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(MaterialClassifyDivision materialClassifyDivision) {
        return materialClassifyDivisionRepository.add(materialClassifyDivision);
    }

    /**
     * 删除
     */
    @Override
    public int delete(MaterialClassifyDivision materialClassifyDivision) {
        if (materialClassifyDivision != null)
            return materialClassifyDivisionRepository.delete(materialClassifyDivision);
        return 0;
    }

    /**
     * 更新
     */
    @Override
    public int update(MaterialClassifyDivision materialClassifyDivision) {
        return materialClassifyDivisionRepository.update(materialClassifyDivision);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return materialClassifyDivisionRepository.deleteById(id);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return materialClassifyDivisionRepository.getCount();
    }

    /**
     * 根据id得到MaterialClassifyDivision
     *
     * @param id
     */
    @Override
    public MaterialClassifyDivision getById(String id) {
        return materialClassifyDivisionRepository.getById(id);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifyDivision> getPage(int pageNo, int pageSize) {
        return materialClassifyDivisionRepository.getPage(pageNo, pageSize);
    }

    @Override
    public List<MaterialClassifyDivision> getAllList() {
        return materialClassifyDivisionRepository.getAllList();
    }


}