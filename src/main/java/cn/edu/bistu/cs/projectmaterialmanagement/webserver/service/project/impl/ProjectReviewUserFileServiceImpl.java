package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUserFileView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectReviewUserFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectReviewUserFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
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
public class ProjectReviewUserFileServiceImpl implements IProjectReviewUserFileService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectReviewUserFileServiceImpl.class);

    private final IProjectReviewUserFileRepository projectReviewUserFileRepository;
    private final IProjectReviewUserService projectReviewUserService;

    public ProjectReviewUserFileServiceImpl(IProjectReviewUserFileRepository projectReviewUserFileRepository,
                                            IProjectReviewUserService projectReviewUserService) {
        this.projectReviewUserFileRepository = projectReviewUserFileRepository;
        this.projectReviewUserService = projectReviewUserService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectReviewUserFile projectReviewUserFile) {
        return projectReviewUserFileRepository.add(projectReviewUserFile);
    }

    @Override
    public String copyProjectReviewUserFile(String projectReviewUserFileId,
                                            String newProjectReviewUserId) {
        ProjectReviewUserFile projectReviewUserFile = getById(projectReviewUserFileId);
        if (projectReviewUserFile == null) {
            return null;
        }
        String fileName = ConfigConstant.FilePath + File.separator + projectReviewUserFile.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            return null;

        String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));

        if (FileUtils.copyFile(ConfigConstant.FilePath + File.separator + projectReviewUserFile.getFilePath(),
                               ConfigConstant.FilePath + File.separator + projectReviewUserFile.getFilePath())) {
            ProjectReviewUserFile projectReviewUserFileTemp = new ProjectReviewUserFile();
            projectReviewUserFileTemp.setProjectReviewUserId(newProjectReviewUserId);
            projectReviewUserFileTemp.setFilePath(newFileName);
            return projectReviewUserFileRepository.add(projectReviewUserFileTemp);
        }

        return null;

    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectReviewUserFile projectReviewUserFile) {
        return projectReviewUserFileRepository.delete(projectReviewUserFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectReviewUserFile projectReviewUserFile) {
        return projectReviewUserFileRepository.update(projectReviewUserFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectReviewUserFileRepository.deleteById(id);
    }

    /**
     * 根据projectReviewUserId删除记录
     *
     * @param projectReviewUserId
     */
    @Override
    public int deleteByProjectReviewUserId(String projectReviewUserId) {
        return projectReviewUserFileRepository.deleteByProjectReviewUserId(projectReviewUserId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectReviewUserFileRepository.getCount();
    }

    /**
     * 根据projectReviewUserId得到数量
     *
     * @param projectReviewUserId
     */
    @Override
    public int getCountByProjectReviewUserId(String projectReviewUserId) {
        return projectReviewUserFileRepository.getCountByProjectReviewUserId(projectReviewUserId);
    }

    /**
     * 根据id得到ProjectReviewUserFile
     *
     * @param id
     */
    @Override
    public ProjectReviewUserFile getById(String id) {
        return projectReviewUserFileRepository.getById(id);
    }

    @Override
    public ProjectReviewUserFileView getViewById(String id) {
        ProjectReviewUserFile projectReviewUserFile = getById(id);
        if (projectReviewUserFile == null) return null;
        ProjectReviewUserFileView projectReviewUserFileView = new ProjectReviewUserFileView();
        projectReviewUserFileView.setProjectReviewUserFile(projectReviewUserFile);
        projectReviewUserFileView.setProjectReviewUser(
                projectReviewUserService.getById(projectReviewUserFile.getProjectReviewUserId()));
        return projectReviewUserFileView;
    }

    /**
     * 根据projectReviewUserId得到ProjectReviewUserFile
     *
     * @param projectReviewUserId id
     */
    @Override
    public List<ProjectReviewUserFile> getByProjectReviewUserId(String projectReviewUserId) {
        return projectReviewUserFileRepository.getByProjectReviewUserId(projectReviewUserId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewUserFile> getPage(int pageNo,
                                               int pageSize) {
        return projectReviewUserFileRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectReviewUserId
     * @param pageNo              页号，从1开始
     * @param pageSize            每页的记录数
     */
    @Override
    public Page<ProjectReviewUserFile> getPageByProjectReviewUserId(String projectReviewUserId,
                                                                    int pageNo,
                                                                    int pageSize) {
        return projectReviewUserFileRepository.getPageByProjectReviewUserId(projectReviewUserId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewUserFileView> getPageView(int pageNo,
                                                       int pageSize) {
        Page<ProjectReviewUserFile> projectReviewUserFilePage = getPage(pageNo, pageSize);
        return convertProjectReviewUserFilePage2PageView(projectReviewUserFilePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectReviewUserId
     * @param pageNo              页号，从1开始
     * @param pageSize            每页的记录数
     */
    @Override
    public Page<ProjectReviewUserFileView> getPageViewByProjectReviewUserId(String projectReviewUserId,
                                                                            int pageNo,
                                                                            int pageSize) {
        Page<ProjectReviewUserFile> projectReviewUserFilePage = getPageByProjectReviewUserId(projectReviewUserId,
                                                                                             pageNo, pageSize);
        return convertProjectReviewUserFilePage2PageView(projectReviewUserFilePage, pageNo, pageSize);
    }

    /**
     * 下载文件
     *
     * @param projectReviewUserFileId
     * @param request
     * @param response
     */
    @Override
    public void downloadFileById(String projectReviewUserFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        ProjectReviewUserFile projectReviewUserFile = getById(projectReviewUserFileId);
        if (projectReviewUserFile == null) throw new RuntimeException("文件不存在");


        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + projectReviewUserFile.getFilePath();
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
     * @param projectReviewUserFilePage 页面对象
     */
    private Page<ProjectReviewUserFileView> convertProjectReviewUserFilePage2PageView(Page<ProjectReviewUserFile> projectReviewUserFilePage,
                                                                                      int pageNo,
                                                                                      int pageSize) {
        if (projectReviewUserFilePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUserFileView> list = new ArrayList<>();
        for (ProjectReviewUserFile projectReviewUserFile : projectReviewUserFilePage.getResult()) {
            ProjectReviewUserFileView projectReviewUserFileView = getViewById(
                    projectReviewUserFile.getId());
            if (projectReviewUserFileView != null) list.add(projectReviewUserFileView);
        }
        return new Page<>(startIndex, projectReviewUserFilePage.getTotalCount(), pageSize, list);
    }

}