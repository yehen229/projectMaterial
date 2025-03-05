package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProjectMaterialBusinessService {
    String addForm(ProjectMaterialForm projectMaterialForm);

    int updateForm(ProjectMaterialForm projectMaterialForm);

    void deleteByProjectId(String id);

    MaterialClassifySection getById(String id);

    ProjectMaterialView getViewById(String projectMaterialId);

    Page<ProjectMaterialView> getPageView(Integer pageNo,
                                          Integer pageSize);

    Page<ProjectMaterialView> getPageViewByProjectId(String projectId,
                                                     Integer pageNo,
                                                     Integer pageSize);

    Page<ProjectMaterialView> getSearchViewByCurrentUserAndProjectId(String projectId,
                                                                   String name,
                                                                   String location,
                                                                   String itemMark,
                                                                   String technology,
                                                                   String installation,
                                                                   String brand,
                                                                   String brandPrivate,
                                                                   Integer pageNo,
                                                                   Integer pageSize);

    Page<ProjectMaterialView> getPageViewByCurrentUserAndProjectId(String projectId,
                                                                   Integer pageNo,
                                                                   Integer pageSize);

    Page<ProjectMaterialView> getProjectMaterialPageViewByProjectIdAndCompanyId(String projectId,
                                                                                String companyId,
                                                                                Integer pageNo,
                                                                                Integer pageSize);

    void getProjectMaterialExcel(String projectId, HttpServletRequest request, HttpServletResponse response);

    List<MaterialBrand> getByMaterialId(String materialId);

    int addProjectMaterialExcel(MultipartFile multipartFile) throws Exception;

}
