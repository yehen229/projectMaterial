package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUserFileView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectAppearanceReviewUserFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectAppearanceReviewUserFileService;
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
public class ProjectAppearanceReviewUserFileServiceImpl implements IProjectAppearanceReviewUserFileService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectAppearanceReviewUserFileServiceImpl.class);

    private final IProjectAppearanceReviewUserFileRepository projectAppearanceReviewUserFileRepository;

    public ProjectAppearanceReviewUserFileServiceImpl(IProjectAppearanceReviewUserFileRepository projectAppearanceReviewUserFileRepository) {
        this.projectAppearanceReviewUserFileRepository = projectAppearanceReviewUserFileRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return projectAppearanceReviewUserFileRepository.add(projectAppearanceReviewUserFile);
    }

    @Override
    public String copyFile(String projectAppearanceReviewUserFileId,
                           String newProjectAppearanceReviewUserFileId) {
        ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = getById(
                projectAppearanceReviewUserFileId);
        if (projectAppearanceReviewUserFile == null) {
            return null;
        }
        String fileName = ConfigConstant.FilePath + File.separator + projectAppearanceReviewUserFile.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            return null;

        String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));

        if (FileUtils.copyFile(
                ConfigConstant.FilePath + File.separator + projectAppearanceReviewUserFile.getFilePath(),
                ConfigConstant.FilePath + File.separator + projectAppearanceReviewUserFile.getFilePath())) {
            ProjectAppearanceReviewUserFile projectAppearanceReviewUserFileTemp = new ProjectAppearanceReviewUserFile();
            projectAppearanceReviewUserFileTemp.setProjectAppearanceReviewUserId(
                    newProjectAppearanceReviewUserFileId);
            projectAppearanceReviewUserFileTemp.setFilePath(newFileName);
            return projectAppearanceReviewUserFileRepository.add(projectAppearanceReviewUserFileTemp);
        }

        return null;
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return projectAppearanceReviewUserFileRepository.delete(projectAppearanceReviewUserFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile) {
        return projectAppearanceReviewUserFileRepository.update(projectAppearanceReviewUserFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectAppearanceReviewUserFileRepository.deleteById(id);
    }

    /**
     * 根据projectAppearanceReviewUserId删除记录
     *
     * @param projectAppearanceReviewUserId
     */
    @Override
    public int deleteByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        return projectAppearanceReviewUserFileRepository.deleteByProjectAppearanceReviewUserId(
                projectAppearanceReviewUserId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectAppearanceReviewUserFileRepository.getCount();
    }

    /**
     * 根据projectAppearanceReviewUserId得到数量
     *
     * @param projectAppearanceReviewUserId
     */
    @Override
    public int getCountByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        return projectAppearanceReviewUserFileRepository.getCountByProjectAppearanceReviewUserId(
                projectAppearanceReviewUserId);
    }

    /**
     * 根据id得到ProjectAppearanceReviewUserFile
     *
     * @param id
     */
    @Override
    public ProjectAppearanceReviewUserFile getById(String id) {
        return projectAppearanceReviewUserFileRepository.getById(id);
    }

    @Override
    public ProjectAppearanceReviewUserFileView getViewById(String id) {
        ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = getById(id);
        if (projectAppearanceReviewUserFile == null) return null;
        ProjectAppearanceReviewUserFileView projectAppearanceReviewUserFileView = new ProjectAppearanceReviewUserFileView();
        return null;
    }

    /**
     * 根据projectAppearanceReviewUserId得到ProjectAppearanceReviewUserFile
     *
     * @param projectAppearanceReviewUserId
     */
    @Override
    public List<ProjectAppearanceReviewUserFile> getByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId) {
        return projectAppearanceReviewUserFileRepository.getByProjectAppearanceReviewUserId(
                projectAppearanceReviewUserId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserFile> getPage(int pageNo,
                                                         int pageSize) {
        return projectAppearanceReviewUserFileRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectAppearanceReviewUserId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserFile> getPageByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId,
                                                                                        int pageNo,
                                                                                        int pageSize) {
        return projectAppearanceReviewUserFileRepository.getPageByProjectAppearanceReviewUserId(
                projectAppearanceReviewUserId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserFileView> getPageView(int pageNo,
                                                                 int pageSize) {
        Page<ProjectAppearanceReviewUserFile> projectAppearanceReviewUserFilePage = getPage(pageNo, pageSize);
        return convertProjectAppearanceReviewUserFilePage2PageView(projectAppearanceReviewUserFilePage, pageNo,
                                                                   pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectAppearanceReviewUserId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUserFileView> getPageViewByProjectAppearanceReviewUserId(String projectAppearanceReviewUserId,
                                                                                                int pageNo,
                                                                                                int pageSize) {
        Page<ProjectAppearanceReviewUserFile> projectAppearanceReviewUserFilePage = getPageByProjectAppearanceReviewUserId(
                projectAppearanceReviewUserId, pageNo, pageSize);
        return convertProjectAppearanceReviewUserFilePage2PageView(projectAppearanceReviewUserFilePage, pageNo,
                                                                   pageSize);
    }

    @Override
    public void downloadFileById(String projectAppearanceReviewUserFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = getById(projectAppearanceReviewUserFileId);
        if (projectAppearanceReviewUserFile == null) throw new RuntimeException("文件不存在");


        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + projectAppearanceReviewUserFile.getFilePath();
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
     * @param projectAppearanceReviewUserFilePage 页面对象
     */
    private Page<ProjectAppearanceReviewUserFileView> convertProjectAppearanceReviewUserFilePage2PageView(Page<ProjectAppearanceReviewUserFile> projectAppearanceReviewUserFilePage,
                                                                                                          int pageNo,
                                                                                                          int pageSize) {
        if (projectAppearanceReviewUserFilePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUserFileView> list = new ArrayList<>();
        for (ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile : projectAppearanceReviewUserFilePage.getResult()) {
            ProjectAppearanceReviewUserFileView projectAppearanceReviewUserFileView = getViewById(
                    projectAppearanceReviewUserFile.getId());
            if (projectAppearanceReviewUserFileView != null) list.add(projectAppearanceReviewUserFileView);
        }
        return new Page<>(startIndex, projectAppearanceReviewUserFilePage.getTotalCount(), pageSize, list);
    }

}