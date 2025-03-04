package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final IProjectRepository projectRepository;
    private final IUserService userService;
    private final ICompanyService companyService;

    public ProjectServiceImpl(IProjectRepository projectRepository,
                              IUserService userService,
                              ICompanyService companyService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.companyService = companyService;
    }

    /**
     * 增加
     */
    @Override
    public String add(Project project) {
        return projectRepository.add(project);
    }

    /**
     * 删除
     */
    @Override
    public int delete(Project project) {
        return projectRepository.delete(project);
    }

    /**
     * 更新
     */
    @Override
    public int update(Project project) {
        return projectRepository.update(project);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectRepository.deleteByUserId(userId);
    }

    /**
     * 根据companyConstructionId删除记录
     *
     * @param companyConstructionId
     */
    @Override
    public int deleteByCompanyConstructionId(String companyConstructionId) {
        return projectRepository.deleteByCompanyConstructionId(companyConstructionId);
    }

    /**
     * 根据companyDesignId删除记录
     *
     * @param companyDesignId
     */
    @Override
    public int deleteByCompanyDesignId(String companyDesignId) {
//        return projectRepository.deleteByCompanyDesignId(companyDesignId);
    return 0;
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectRepository.getCountByUserId(userId);
    }

    /**
     * 根据companyConstructionId得到数量
     *
     * @param companyConstructionId
     */
    @Override
    public int getCountByCompanyConstructionId(String companyConstructionId) {
        return projectRepository.getCountByCompanyConstructionId(companyConstructionId);
    }

    /**
     * 根据companyDesignId得到数量
     *
     * @param companyDesignId
     */
    @Override
    public int getCountByCompanyDesignId(String companyDesignId) {
//        return projectRepository.getCountByCompanyDesignId(companyDesignId);
    return 0;
    }

    /**
     * 根据id得到Project
     *
     * @param id
     */
    @Override
    public Project getById(String id) {
        return projectRepository.getById(id);
    }

    /**
     * 根据userId得到Project
     *
     * @param userId
     */
    @Override
    public List<Project> getByUserId(String userId) {
        return projectRepository.getByUserId(userId);
    }

    @Override
    public List<Project> getAllList() {
        return projectRepository.getAllList();
    }





    /**
     * 根据companyConstructionId得到Project
     *
     * @param companyConstructionId
     */
    @Override
    public List<Project> getByCompanyConstructionId(String companyConstructionId) {
        return projectRepository.getByCompanyConstructionId(companyConstructionId);
    }

    /**
     * 根据companyDesignId得到Project
     *
     * @param companyDesignId
     */
    @Override
    public List<Project> getByCompanyDesignId(String companyDesignId) {
//        return projectRepository.getByCompanyDesignId(companyDesignId);
    return null;
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Project> getPage(int pageNo,
                                 int pageSize) {
        return projectRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Project> getPageByUserId(String userId,
                                         int pageNo,
                                         int pageSize) {
        return projectRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param companyConstructionId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<Project> getPageByCompanyConstructionId(String companyConstructionId,
                                                        int pageNo,
                                                        int pageSize) {
        return projectRepository.getPageByCompanyConstructionId(companyConstructionId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param companyDesignId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    @Override
    public Page<Project> getPageByCompanyDesignId(String companyDesignId,
                                                  int pageNo,
                                                  int pageSize) {
        return projectRepository.getPageByCompanyDesignId(companyDesignId, pageNo, pageSize);
    }

    @Override
    public Page<Project> getPageByProjectName(String projectName,
                                              int pageNo,
                                              int pageSize) {
        return projectRepository.getPageByProjectName(projectName, pageNo, pageSize);
    }

    @Override
    public Page<Project> getPageByProjectId(String projectId,
                                            int pageNo,
                                            int pageSize) {
        return projectRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }

    @Override
    public Page<Project> getPageByKeyword(String keyword,
                                          int pageNo,
                                          int pageSize) {
        return projectRepository.getPageByKeyword(keyword, pageNo, pageSize);
    }

    @Override
    public Page<Project> getNotEndedProjectPageOfGeneralContractorCompany(String generalContractorCompanyId,
                                                                          Integer pageNo,
                                                                          Integer pageSize) {
        return projectRepository.getNotEndedProjectPageOfGeneralContractorCompany(generalContractorCompanyId, pageNo,
                                                                                  pageSize);
    }


}