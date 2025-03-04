package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFileView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialVerificationDocumentFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialVerificationDocumentFileService;
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
public class ProjectMaterialVerificationDocumentFileServiceImpl implements IProjectMaterialVerificationDocumentFileService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectMaterialVerificationDocumentFileServiceImpl.class);

    private final IProjectMaterialVerificationDocumentFileRepository projectMaterialVerificationDocumentFileRepository;

    public ProjectMaterialVerificationDocumentFileServiceImpl(IProjectMaterialVerificationDocumentFileRepository projectMaterialVerificationDocumentFileRepository) {
        this.projectMaterialVerificationDocumentFileRepository = projectMaterialVerificationDocumentFileRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return projectMaterialVerificationDocumentFileRepository.add(projectMaterialVerificationDocumentFile);
    }

    @Override
    public String copyFile(String ProjectMaterialVerificationDocumentFileId,
                           String newProjectMaterialVerificationDocumentFileId) {
        return "";
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return projectMaterialVerificationDocumentFileRepository.delete(projectMaterialVerificationDocumentFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile) {
        return projectMaterialVerificationDocumentFileRepository.update(projectMaterialVerificationDocumentFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialVerificationDocumentFileRepository.deleteById(id);
    }

    /**
     * 根据projectMaterialVerificationDocumentId删除记录
     *
     * @param projectMaterialVerificationDocumentId
     */
    @Override
    public int deleteByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId) {
        return projectMaterialVerificationDocumentFileRepository.deleteByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialVerificationDocumentFileRepository.getCount();
    }

    /**
     * 根据projectMaterialVerificationDocumentId得到数量
     *
     * @param projectMaterialVerificationDocumentId
     */
    @Override
    public int getCountByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId) {
        return projectMaterialVerificationDocumentFileRepository.getCountByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId);
    }

    /**
     * 根据id得到ProjectMaterialVerificationDocumentFile
     *
     * @param id
     */
    @Override
    public ProjectMaterialVerificationDocumentFile getById(String id) {
        return projectMaterialVerificationDocumentFileRepository.getById(id);
    }

    @Override
    public ProjectMaterialVerificationDocumentFileView getViewById(String id) {
        ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile = getById(id);
        if (projectMaterialVerificationDocumentFile == null) return null;
        ProjectMaterialVerificationDocumentFileView projectMaterialVerificationDocumentFileView = new ProjectMaterialVerificationDocumentFileView();
        return null;
    }

    /**
     * 根据projectMaterialVerificationDocumentId得到ProjectMaterialVerificationDocumentFile
     *
     * @param projectMaterialVerificationDocumentId
     */
    @Override
    public List<ProjectMaterialVerificationDocumentFile> getByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId) {
        return projectMaterialVerificationDocumentFileRepository.getByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentFile> getPage(int pageNo,
                                                                 int pageSize) {
        return projectMaterialVerificationDocumentFileRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialVerificationDocumentId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentFile> getPageByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId,
                                                                                                        int pageNo,
                                                                                                        int pageSize) {
        return projectMaterialVerificationDocumentFileRepository.getPageByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentFileView> getPageView(int pageNo,
                                                                         int pageSize) {
        Page<ProjectMaterialVerificationDocumentFile> projectMaterialVerificationDocumentFilePage = getPage(pageNo,
                                                                                                            pageSize);
        return convertProjectMaterialVerificationDocumentFilePage2PageView(projectMaterialVerificationDocumentFilePage,
                                                                           pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialVerificationDocumentId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocumentFileView> getPageViewByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId,
                                                                                                                int pageNo,
                                                                                                                int pageSize) {
        Page<ProjectMaterialVerificationDocumentFile> projectMaterialVerificationDocumentFilePage = getPageByProjectMaterialVerificationDocumentId(
                projectMaterialVerificationDocumentId, pageNo, pageSize);
        return convertProjectMaterialVerificationDocumentFilePage2PageView(projectMaterialVerificationDocumentFilePage,
                                                                           pageNo, pageSize);
    }

    @Override
    public void downloadFileById(String projectMaterialVerificationDocumentFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile = getById(
                projectMaterialVerificationDocumentFileId);
        if (projectMaterialVerificationDocumentFile == null) throw new RuntimeException("文件不存在");


        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + projectMaterialVerificationDocumentFile.getFilePath();
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
     * @param projectMaterialVerificationDocumentFilePage 页面对象
     */
    private Page<ProjectMaterialVerificationDocumentFileView> convertProjectMaterialVerificationDocumentFilePage2PageView(Page<ProjectMaterialVerificationDocumentFile> projectMaterialVerificationDocumentFilePage,
                                                                                                                          int pageNo,
                                                                                                                          int pageSize) {
        if (projectMaterialVerificationDocumentFilePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocumentFileView> list = new ArrayList<>();
        for (ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile : projectMaterialVerificationDocumentFilePage.getResult()) {
            ProjectMaterialVerificationDocumentFileView projectMaterialVerificationDocumentFileView = getViewById(
                    projectMaterialVerificationDocumentFile.getId());
            if (projectMaterialVerificationDocumentFileView != null)
                list.add(projectMaterialVerificationDocumentFileView);
        }
        return new Page<>(startIndex, projectMaterialVerificationDocumentFilePage.getTotalCount(), pageSize, list);
    }

}