package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectDesignCompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectDesignCompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDesignCompanyServiceImpl implements IProjectDesignCompanyService {
    private final IProjectDesignCompanyRepository projectDesignCompanyRepository;
    private final ICompanyService companyService;

    public ProjectDesignCompanyServiceImpl(IProjectDesignCompanyRepository projectDesignCompanyRepository,
                                           ICompanyService companyService) {
        this.projectDesignCompanyRepository = projectDesignCompanyRepository;
        this.companyService = companyService;
    }

    @Override
    public String add(ProjectDesignCompany projectDesignCompany) {
        return projectDesignCompanyRepository.add(projectDesignCompany);
    }

    @Override
    public int delete(ProjectDesignCompany projectDesignCompany) {
        return projectDesignCompanyRepository.delete(projectDesignCompany);
    }

    @Override
    public int deleteById(String id) {
        return projectDesignCompanyRepository.deleteById(id);
    }

    @Override
    public int deleteByProjectId(String projectId) {
        return projectDesignCompanyRepository.deleteByProjectId(projectId);
    }

    @Override
    public int deleteByCompanyDesignId(String companyDesignId) {
        return projectDesignCompanyRepository.deleteByCompanyDesignId(companyDesignId);
    }

    @Override
    public int update(ProjectDesignCompany projectDesignCompany) {
        return projectDesignCompanyRepository.update(projectDesignCompany);
    }

    @Override
    public int getCount() {
        return projectDesignCompanyRepository.getCount();
    }

    @Override
    public int getCountByProjectId(String projectId) {
        return projectDesignCompanyRepository.getCountByProjectId(projectId);
    }

    @Override
    public int getCountByCompanyDesignId(String companyDesignId) {
        return projectDesignCompanyRepository.getCountByCompanyDesignId(companyDesignId);
    }

    @Override
    public ProjectDesignCompany getById(String id) {
        return projectDesignCompanyRepository.getById(id);
    }

    @Override
    public ProjectDesignCompany getByProjectIdAndDesignCompanyId(String projectId,
                                                                 String companyId) {
        return projectDesignCompanyRepository.getByProjectIdAndDesignCompanyId(projectId, companyId);
    }

    @Override
    public List<ProjectDesignCompany> getByProjectId(String projectId) {
        return projectDesignCompanyRepository.getByProjectId(projectId);
    }

    @Override
    public List<Company> getCompanyByProjectId(String projectId) {
        List<ProjectDesignCompany> projectDesignCompanies = projectDesignCompanyRepository.getByProjectId(projectId);
        if (projectDesignCompanies == null || projectDesignCompanies.isEmpty())
            return null;

        return projectDesignCompanies.stream().map(
                projectDesignCompany -> companyService.getById(projectDesignCompany.getDesignCompanyId())).toList();

    }

    @Override
    public List<ProjectDesignCompany> getByCompanyDesignId(String companyDesignId) {
        return projectDesignCompanyRepository.getByCompanyDesignId(companyDesignId);
    }

    @Override
    public Page<ProjectDesignCompany> getPage(int pageNo,
                                              int pageSize) {
        return projectDesignCompanyRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ProjectDesignCompany> getPageByCompanyDesignId(String companyDesignId,
                                                               int pageNo,
                                                               int pageSize) {
        return projectDesignCompanyRepository.getPageByCompanyDesignId(companyDesignId, pageNo, pageSize);
    }

    @Override
    public Page<ProjectDesignCompany> getPageByProjectId(String projectId,
                                                         int pageNo,
                                                         int pageSize) {
        return projectDesignCompanyRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }
}
