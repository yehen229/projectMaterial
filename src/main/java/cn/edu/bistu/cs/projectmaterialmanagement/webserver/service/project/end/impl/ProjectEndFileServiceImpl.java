package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFileView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.end.IProjectEndFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.IProjectEndFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.IProjectEndService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectEndFileServiceImpl implements IProjectEndFileService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectEndFileServiceImpl.class);

    private final IProjectEndFileRepository projectEndFileRepository;
    private final IProjectEndService projectEndService;

    public ProjectEndFileServiceImpl(IProjectEndFileRepository projectEndFileRepository,
                                     IProjectEndService projectEndService) {
        this.projectEndFileRepository = projectEndFileRepository;
        this.projectEndService = projectEndService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectEndFile projectEndFile) {
        return projectEndFileRepository.add(projectEndFile);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectEndFile projectEndFile) {
        return projectEndFileRepository.delete(projectEndFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectEndFile projectEndFile) {
        return projectEndFileRepository.update(projectEndFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectEndFileRepository.deleteById(id);
    }

    /**
     * 根据projectEndId删除记录
     *
     * @param projectEndId
     */
    @Override
    public int deleteByProjectEndId(String projectEndId) {
        return projectEndFileRepository.deleteByProjectEndId(projectEndId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectEndFileRepository.getCount();
    }

    /**
     * 根据projectEndId得到数量
     *
     * @param projectEndId
     */
    @Override
    public int getCountByProjectEndId(String projectEndId) {
        return projectEndFileRepository.getCountByProjectEndId(projectEndId);
    }

    /**
     * 根据id得到ProjectEndFile
     *
     * @param id
     */
    @Override
    public ProjectEndFile getById(String id) {
        return projectEndFileRepository.getById(id);
    }

    @Override
    public ProjectEndFileView getViewById(String id) {
        ProjectEndFile projectEndFile = getById(id);
        if (projectEndFile == null) return null;
        ProjectEndFileView projectEndFileView = new ProjectEndFileView();
        projectEndFileView.setProjectEnd(projectEndService.getById(projectEndFile.getProjectEndId()));
        projectEndFileView.setProjectEndFile(projectEndFile);
        return projectEndFileView;
    }

    /**
     * 根据projectEndId得到ProjectEndFile
     *
     * @param projectEndId
     */
    @Override
    public List<ProjectEndFile> getByProjectEndId(String projectEndId) {
        return projectEndFileRepository.getByProjectEndId(projectEndId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEndFile> getPage(int pageNo,
                                        int pageSize) {
        return projectEndFileRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectEndId
     * @param pageNo       页号，从1开始
     * @param pageSize     每页的记录数
     */
    @Override
    public Page<ProjectEndFile> getPageByProjectEndId(String projectEndId,
                                                      int pageNo,
                                                      int pageSize) {
        return projectEndFileRepository.getPageByProjectEndId(projectEndId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectEndFileView> getPageView(int pageNo,
                                                int pageSize) {
        Page<ProjectEndFile> projectEndFilePage = getPage(pageNo, pageSize);
        return convertProjectEndFilePage2PageView(projectEndFilePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectEndId
     * @param pageNo       页号，从1开始
     * @param pageSize     每页的记录数
     */
    @Override
    public Page<ProjectEndFileView> getPageViewByProjectEndId(String projectEndId,
                                                              int pageNo,
                                                              int pageSize) {
        Page<ProjectEndFile> projectEndFilePage = getPageByProjectEndId(projectEndId, pageNo, pageSize);
        return convertProjectEndFilePage2PageView(projectEndFilePage, pageNo, pageSize);
    }

    @Override
    public void downloadFileById(String projectEndFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        ProjectEndFile projectEndFile = getById(projectEndFileId);
        if (projectEndFile == null) throw new RuntimeException("文件不存在");


        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + projectEndFile.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            throw new BusinessException("参数错误");

        try {
            //2.下载文件
            InputStream inputStream = new FileInputStream(fileName);


            String outFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            response.flushBuffer();
            response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition",
                               "attachment;filename=" + outFileName + ";" + "filename*=utf-8''" + outFileName);
            ServletOutputStream outputStream = response.getOutputStream();


            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }


            inputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 将页面转换为视图页面
     *
     * @param projectEndFilePage 页面对象
     */
    private Page<ProjectEndFileView> convertProjectEndFilePage2PageView(Page<ProjectEndFile> projectEndFilePage,
                                                                        int pageNo,
                                                                        int pageSize) {
        if (projectEndFilePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectEndFileView> list = new ArrayList<>();
        for (ProjectEndFile projectEndFile : projectEndFilePage.getResult()) {
            ProjectEndFileView projectEndFileView = getViewById(projectEndFile.getId());
            if (projectEndFileView != null) list.add(projectEndFileView);
        }
        return new Page<>(startIndex, projectEndFilePage.getTotalCount(), pageSize, list);
    }

}