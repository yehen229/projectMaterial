package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocument;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialVerificationDocumentRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialVerificationDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialVerificationDocumentServiceImpl implements IProjectMaterialVerificationDocumentService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectMaterialVerificationDocumentServiceImpl.class);

    private final IProjectMaterialVerificationDocumentRepository projectMaterialVerificationDocumentRepository;

    public ProjectMaterialVerificationDocumentServiceImpl(IProjectMaterialVerificationDocumentRepository projectMaterialVerificationDocumentRepository) {
        this.projectMaterialVerificationDocumentRepository = projectMaterialVerificationDocumentRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return projectMaterialVerificationDocumentRepository.add(projectMaterialVerificationDocument);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return projectMaterialVerificationDocumentRepository.delete(projectMaterialVerificationDocument);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return projectMaterialVerificationDocumentRepository.update(projectMaterialVerificationDocument);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialVerificationDocumentRepository.deleteById(id);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectMaterialVerificationDocumentRepository.deleteByUserId(userId);
    }

    /**
     * 根据projectMaterialId删除记录
     *
     * @param buyMaterialId
     */
    @Override
    public int deleteByBuyMaterialId(String buyMaterialId) {
        return projectMaterialVerificationDocumentRepository.deleteByBuyMaterialId(buyMaterialId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialVerificationDocumentRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectMaterialVerificationDocumentRepository.getCountByUserId(userId);
    }

    /**
     * 根据projectMaterialId得到数量
     *
     * @param buyMaterialId
     */
    @Override
    public int getCountByBuyMaterialId(String buyMaterialId) {
        return projectMaterialVerificationDocumentRepository.getCountByBuyMaterialId(buyMaterialId);
    }

    /**
     * 根据id得到ProjectMaterialVerificationDocument
     *
     * @param id
     */
    @Override
    public ProjectMaterialVerificationDocument getById(String id) {
        return projectMaterialVerificationDocumentRepository.getById(id);
    }

    /**
     * 根据userId得到ProjectMaterialVerificationDocument
     *
     * @param userId
     */
    @Override
    public List<ProjectMaterialVerificationDocument> getByUserId(String userId) {
        return projectMaterialVerificationDocumentRepository.getByUserId(userId);
    }

    /**
     * 根据projectMaterialId得到ProjectMaterialVerificationDocument
     *
     * @param buyMaterialId
     */
    @Override
    public List<ProjectMaterialVerificationDocument> getByBuyMaterialId(String buyMaterialId) {
        return projectMaterialVerificationDocumentRepository.getByBuyMaterialId(buyMaterialId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocument> getPage(int pageNo,
                                                             int pageSize) {
        return projectMaterialVerificationDocumentRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocument> getPageByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return projectMaterialVerificationDocumentRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param buyMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocument> getPageByBuyMaterialId(String buyMaterialId,
                                                                            int pageNo,
                                                                            int pageSize) {
        return projectMaterialVerificationDocumentRepository.getPageByBuyMaterialId(buyMaterialId, pageNo,
                                                                                    pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentView> getPageView(int pageNo,
                                                                     int pageSize) {
        Page<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentPage = getPage(pageNo, pageSize);
        return convertProjectMaterialVerificationDocumentPage2PageView(projectMaterialVerificationDocumentPage, pageNo,
                                                                       pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentView> getPageViewByUserId(String userId,
                                                                             int pageNo,
                                                                             int pageSize) {
        Page<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentPage = getPageByUserId(userId,
                                                                                                            pageNo,
                                                                                                            pageSize);
        return convertProjectMaterialVerificationDocumentPage2PageView(projectMaterialVerificationDocumentPage, pageNo,
                                                                       pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param buyMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentView> getPageViewByBuyMaterialId(String buyMaterialId,
                                                                                    int pageNo,
                                                                                    int pageSize) {
        Page<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentPage = getPageByBuyMaterialId(
                buyMaterialId, pageNo, pageSize);
        return convertProjectMaterialVerificationDocumentPage2PageView(projectMaterialVerificationDocumentPage, pageNo,
                                                                       pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialVerificationDocumentView getProjectMaterialVerificationDocumentViewByProjectMaterialVerificationDocumentId(String id) {
        ProjectMaterialVerificationDocument projectMaterialVerificationDocument = getById(id);
        if (projectMaterialVerificationDocument == null) return null;
        ProjectMaterialVerificationDocumentView projectMaterialVerificationDocumentView = new ProjectMaterialVerificationDocumentView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialVerificationDocumentPage 页面对象
     */
    private Page<ProjectMaterialVerificationDocumentView> convertProjectMaterialVerificationDocumentPage2PageView(Page<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentPage,
                                                                                                                  int pageNo,
                                                                                                                  int pageSize) {
        if (projectMaterialVerificationDocumentPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocumentView> list = new ArrayList<>();
        for (ProjectMaterialVerificationDocument projectMaterialVerificationDocument : projectMaterialVerificationDocumentPage.getResult()) {
            ProjectMaterialVerificationDocumentView projectMaterialVerificationDocumentView = getProjectMaterialVerificationDocumentViewByProjectMaterialVerificationDocumentId(
                    projectMaterialVerificationDocument.getId());
            if (projectMaterialVerificationDocumentView != null) list.add(projectMaterialVerificationDocumentView);
        }
        return new Page<>(startIndex, projectMaterialVerificationDocumentPage.getTotalCount(), pageSize, list);
    }

}