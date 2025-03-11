package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.ProjectLogView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogRepository;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class LogServiceImpl implements ILogService {
    private final IProjectService projectService;
    private final IUserService userService;
    private final ICompanyUserService companyUserService;
    private static final Logger log =
            LoggerFactory.getLogger(LogServiceImpl.class);

    private final ILogRepository logRepository;

    public LogServiceImpl(ILogRepository logRepository, IProjectService projectService, IUserService userService, ICompanyUserService companyUserService) {
        this.userService = userService;
        this.companyUserService = companyUserService;
        this.projectService = projectService;
        this.logRepository = logRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(Log log) {
        return logRepository.add(log);
    }

    /**
     * 删除
     */
//	@Override
//	public int delete(Log log){
//		return logRepository.delete(log);
//	}

    /**
     * 根据id删除记录
     * @param id
     */
//	@Override
//	public int deleteById(String id){
//		return logRepository.deleteById(id);
//	}

    /**
     * 根据userId删除记录
     * @param userId
     */
//	@Override
//	public int deleteByUserId(String userId){
//		return logRepository.deleteByUserId(userId);
//	}


    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return logRepository.getCount();
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return logRepository.getCountByUserId(userId);
    }

    /**
     * 根据ProjectId得到数量
     *
     * @param projectId
     * @return
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return logRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据id得到Log
     *
     * @param id
     */
    @Override
    public Log getById(String id) {
        return logRepository.getById(id);
    }

    /**
     * 根据userId得到Log
     *
     * @param userId
     */
    @Override
    public List<Log> getByUserId(String userId) {
        return logRepository.getByUserId(userId);
    }

    @Override
    public List<Log> getByProjectId(String projectId) {
        return logRepository.getByProjectId(projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectLogView> getPage(int pageNo, int pageSize) {
        Page<Log> page = logRepository.getPage(pageNo, pageSize);
        return convertProjectOplogPage2PageView(page, pageNo, pageSize);
//		return logRepository.getPage(pageNo,pageSize);

    }

    @Override
    public Page<ProjectLogView> getbynamePage(String projectName, int pageNo, int pageSize) {
//		1.获取全部的List<Log>数据
        List<Log> alllog = logRepository.getAllLog();
//		2.通过log获取完整的ProjectLogViewList 视图
        List<ProjectLogView> projectLogViewList = new ArrayList<>();
        for (Log log : alllog) {
            ProjectLogView projectLogView = new ProjectLogView();
            projectLogView.setLog(log);
//			1.通过t_user_id得到用户信息
            projectLogView.setUser(userService.getById(log.getT_user_id()));

//			2.通过t_project_id得到项目信息
            projectLogView.setProject(projectService.getById(log.getT_project_id()));
//          3.获取单位信息
            projectLogView.setCompany(companyUserService.getCompanyByUserId(log.getT_user_id()));

            if (projectLogView != null) projectLogViewList.add(projectLogView);
        }
//      3.通过projectName过滤
        List<ProjectLogView> projectLogViewListByName = new ArrayList<>();
        for (ProjectLogView projectLogView : projectLogViewList) {
            if (projectLogView.getUser().getRealName().contains(projectName )) {
                projectLogViewListByName.add(projectLogView);
            }
        }

//       对过滤完的进行分页
        long totalCount = projectLogViewListByName.size();
//        int start = Page.getStartOfPage(pageNo,  pageSize);

        // 计算分页后的结果
        List<ProjectLogView> pageData = new ArrayList<>();
//        int end = start + pageSize;
        for (int i = (pageNo-1)*pageSize;  i < totalCount&&i<=pageNo*pageSize; i++) {
            pageData.add(projectLogViewListByName.get(i));
        }

        return new Page<>(0, totalCount, pageSize, pageData);

    }

    private Page<ProjectLogView> convertProjectOplogPage2PageView(Page<Log> pagelog,
                                                                  int pageNo,
                                                                  int pageSize) {
        if (pagelog == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectLogView> list = new ArrayList<>();
        for (Log log : pagelog.getResult()) {
            ProjectLogView projectLogView = new ProjectLogView();
            projectLogView.setLog(log);
//			1.通过t_user_id得到用户信息
            projectLogView.setUser(userService.getById(log.getT_user_id()));

//			2.通过t_project_id得到项目信息
            if (!log.getT_project_id().equals("123"))
                projectLogView.setProject(projectService.getById(log.getT_project_id()));
            else {
                Project project = new Project();
//                project.setName("无");
                projectLogView.setProject(project);
            }
//           3.获取单位信息
            if (!log.getT_project_id().equals("123"))
                projectLogView.setCompany(companyUserService.getCompanyByUserId(log.getT_user_id()));
            else {
                Company company = new Company();
//                company.setName("无");
                projectLogView.setCompany(company);
            }

            if (projectLogView != null) list.add(projectLogView);
        }
        return new Page<>(startIndex, pagelog.getTotalCount(), pageSize, list);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Log> getPageByUserId(String userId, int pageNo, int pageSize) {
        return logRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    @Override
    public Page<Log> getPageByProjectId(String projectId, int pageNo, int pageSize) {
        return null;
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private LogView getLogViewByLogId(String id) {
        Log log = getById(id);
        if (log == null) return null;
        LogView logView = new LogView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param logPage 页面对象
     */
    private Page<LogView> convertLogPage2PageView(Page<Log> logPage, int pageNo, int pageSize) {
        if (logPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<LogView> list = new ArrayList<>();
        for (Log log : logPage.getResult()) {
            LogView logView = getLogViewByLogId(log.getId());
            if (logView != null) list.add(logView);
        }
        return new Page<>(startIndex, logPage.getTotalCount(), pageSize, list);
    }

}