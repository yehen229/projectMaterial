package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectEndRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectEndService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectEndServiceImpl implements IProjectEndService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectEndServiceImpl.class);

    private final IProjectEndRepository projectEndRepository;

    public ProjectEndServiceImpl(IProjectEndRepository projectEndRepository) {
        this.projectEndRepository = projectEndRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectEnd projectEnd) {
        return projectEndRepository.add(projectEnd);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectEnd projectEnd) {
        return projectEndRepository.delete(projectEnd);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectEnd projectEnd) {
        return projectEndRepository.update(projectEnd);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectEndRepository.deleteById(id);
    }

    /**
     * 根据projecctId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectEndRepository.deleteByProjectId(projectId);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return projectEndRepository.deleteByUserId(userId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectEndRepository.getCount();
    }

    /**
     * 根据projecctId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectEndRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return projectEndRepository.getCountByUserId(userId);
    }

    /**
     * 根据id得到ProjectEnd
     *
     * @param id
     */
    @Override
    public ProjectEnd getById(String id) {
        return projectEndRepository.getById(id);
    }

    /**
     * 根据projecctId得到ProjectEnd
     *
     * @param projectId
     */
    @Override
    public List<ProjectEnd> getByProjectId(String projectId) {
        return projectEndRepository.getByProjectId(projectId);
    }

    /**
     * 根据userId得到ProjectEnd
     *
     * @param userId
     */
    @Override
    public List<ProjectEnd> getByUserId(String userId) {
        return projectEndRepository.getByUserId(userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEnd> getPage(int pageNo,
                                    int pageSize) {
        return projectEndRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projecctId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<ProjectEnd> getPageByProjectId(String projecctId,
                                               int pageNo,
                                               int pageSize) {
        return projectEndRepository.getPageByProjectId(projecctId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEnd> getPageByUserId(String userId,
                                            int pageNo,
                                            int pageSize) {
        return projectEndRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEndView> getPageView(int pageNo,
                                            int pageSize) {
        Page<ProjectEnd> projectEndPage = getPage(pageNo, pageSize);
        return convertProjectEndPage2PageView(projectEndPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projecctId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<ProjectEndView> getPageViewByProjectId(String projecctId,
                                                       int pageNo,
                                                       int pageSize) {
        Page<ProjectEnd> projectEndPage = getPageByProjectId(projecctId, pageNo, pageSize);
        return convertProjectEndPage2PageView(projectEndPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEndView> getPageViewByUserId(String userId,
                                                    int pageNo,
                                                    int pageSize) {
        Page<ProjectEnd> projectEndPage = getPageByUserId(userId, pageNo, pageSize);
        return convertProjectEndPage2PageView(projectEndPage, pageNo, pageSize);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectEndView getProjectEndViewByProjectEndId(String id) {
        ProjectEnd projectEnd = getById(id);
        if (projectEnd == null) return null;
        ProjectEndView projectEndView = new ProjectEndView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectEndPage 页面对象
     */
    private Page<ProjectEndView> convertProjectEndPage2PageView(Page<ProjectEnd> projectEndPage,
                                                                int pageNo,
                                                                int pageSize) {
        if (projectEndPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEndView> list = new ArrayList<>();
        for (ProjectEnd projectEnd : projectEndPage.getResult()) {
            ProjectEndView projectEndView = getProjectEndViewByProjectEndId(projectEnd.getId());
            if (projectEndView != null) list.add(projectEndView);
        }
        return new Page<>(startIndex, projectEndPage.getTotalCount(), pageSize, list);
    }

}