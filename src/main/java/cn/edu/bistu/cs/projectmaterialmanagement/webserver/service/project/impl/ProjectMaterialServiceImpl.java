package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectMaterialServiceImpl implements IProjectMaterialService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectMaterialServiceImpl.class);

    private final IProjectMaterialRepository projectMaterialRepository;
    private final IProjectService projectService;
    private final IMaterialService materialService;

    public ProjectMaterialServiceImpl(IProjectMaterialRepository projectMaterialRepository,
                                      IProjectService projectService,
                                      IMaterialService materialService) {
        this.projectMaterialRepository = projectMaterialRepository;
        this.projectService = projectService;
        this.materialService = materialService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterial projectMaterial) {
        return projectMaterialRepository.add(projectMaterial);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterial projectMaterial) {
        return projectMaterialRepository.delete(projectMaterial);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterial projectMaterial) {
        return projectMaterialRepository.update(projectMaterial);
    }

    @Override
    public int update(String id,
                      String materialId,
                      BigDecimal materialCount,
                      String materialUnit) {
        return projectMaterialRepository.update(id, materialId, materialCount, materialUnit);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialRepository.deleteById(id);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectMaterialRepository.deleteByProjectId(projectId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialRepository.getCount();
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectMaterialRepository.getCountByProjectId(projectId);
    }

    @Override
    public List<ProjectMaterial> getByMaterialId(String materialId) {
        return projectMaterialRepository.getByMaterialId(materialId);
    }


    /**
     * 根据id得到ProjectMaterial
     *
     * @param id
     */
    @Override
    public ProjectMaterial getById(String id) {
        return projectMaterialRepository.getById(id);
    }
    @Override
    public ProjectMaterial getByIdAndCompanyId(String companyId,String id){
        return projectMaterialRepository.getByIdAndCompanyId(companyId,id);
    }

    @Override
    public ProjectMaterial getByProjectIdAndMaterialOriginId(String projectId,
                                                             String materialOriginId) {
        return projectMaterialRepository.getByProjectIdAndMaterialOriginId(projectId, materialOriginId);
    }

    @Override
    public ProjectMaterial getByProjectIdAndMaterialId(String projectId,
                                                       String materialId) {
        return projectMaterialRepository.getByProjectIdAndMaterialId(projectId, materialId);
    }

    /**
     * 根据projectId得到ProjectMaterial
     *
     * @param projectId
     */
    @Override
    public List<ProjectMaterial> getByProjectId(String projectId) {
        return projectMaterialRepository.getByProjectId(projectId);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterial> getPage(int pageNo,
                                         int pageSize) {
        return projectMaterialRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectMaterial> getPageByProjectId(String projectId,
                                                    int pageNo,
                                                    int pageSize) {
        return projectMaterialRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterial> getPageByProjectIdForDispatchView(String projectId,
                                                                   String name,
                                                                   String location,
                                                                   String itemMark,
                                                                   String technology,
                                                                   String installation,
                                                                   String brand,
                                                                   String brandPrivate,
                                                    int pageNo,
                                                    int pageSize) {
        return projectMaterialRepository.getPageByProjectIdWithParams(projectId, name, location, itemMark, technology, installation, brand, brandPrivate, pageNo, pageSize);
    }



    @Override
    public Page<ProjectMaterial> getReviewedAndApprovedUseMaterialViewPageByProjectId(String projectId,
                                                                                      Integer pageNo,
                                                                                      Integer pageSize) {
        return projectMaterialRepository.getReviewedAndApprovedUseMaterialViewPageByProjectId(projectId, pageNo,
                                                                                              pageSize);
    }

    @Override
    public Page<ProjectMaterial> getPageByProjectIdAndCompanyId(String projectId,
                                                                String companyId,
                                                                Integer pageNo,
                                                                Integer pageSize) {
        return projectMaterialRepository.getPageByProjectIdAndCompanyId(projectId, companyId, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterial> getPageBySearchParams(String projectId,
                                                                String companyId,
                                                                String name,
                                                                String location,
                                                                String itemMark,
                                                                String technology,
                                                                String installation,
                                                                String brandPublic,
                                                                String brandPrivate,
                                                                Integer pageNo,
                                                                Integer pageSize) {
        return projectMaterialRepository.getSearchPageByProjectIdAndCompanyId(projectId, companyId, name, location, itemMark, technology, installation, brandPublic, brandPrivate, pageNo, pageSize);
    }



    @Override
    public Page<ProjectMaterial> getSearchPageByProjectIdAndCompanyId(String projectId,
                                                                String companyId,
                                                                String name,
                                                                String location,
                                                                String itemMark,
                                                                String technology,
                                                                String installation,
                                                                String brand,
                                                                String brandPrivate,
                                                                Integer pageNo,
                                                                Integer pageSize) {
        return projectMaterialRepository.getSearchPageByProjectIdAndCompanyId(projectId, companyId, name, location, itemMark, technology, installation, brand, brandPrivate, pageNo, pageSize);
    }

    /**
     * 根据projectId和companyId得到ProjectMaterial
     *
     * @param projectId
     * @param companyId
     */
    @Override
    public List<ProjectMaterial> getListByProjectId(String projectId, String companyId) {
        return projectMaterialRepository.getByProjectIdandCompanyId(projectId, companyId);
    }

}