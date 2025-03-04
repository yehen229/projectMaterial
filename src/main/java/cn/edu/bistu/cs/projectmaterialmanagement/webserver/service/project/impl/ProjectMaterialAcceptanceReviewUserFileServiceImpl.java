package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUserFileView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialAcceptanceReviewUserFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialAcceptanceReviewUserFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMaterialAcceptanceReviewUserFileServiceImpl implements IProjectMaterialAcceptanceReviewUserFileService {
    private final IProjectMaterialAcceptanceReviewUserFileRepository projectMaterialAcceptanceReviewUserFileRepository;

    public ProjectMaterialAcceptanceReviewUserFileServiceImpl(IProjectMaterialAcceptanceReviewUserFileRepository projectMaterialAcceptanceReviewUserFileRepository) {
        this.projectMaterialAcceptanceReviewUserFileRepository = projectMaterialAcceptanceReviewUserFileRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return projectMaterialAcceptanceReviewUserFileRepository.add(projectMaterialAcceptanceReviewUserFile);
    }

    @Override
    public String copyFile(String projectMaterialAcceptanceReviewUserFileId,
                           String newProjectMaterialAcceptanceReviewUserFileId) {
        ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = getById(
                projectMaterialAcceptanceReviewUserFileId);
        if (projectMaterialAcceptanceReviewUserFile == null) {
            return null;
        }
        String fileName = ConfigConstant.FilePath + File.separator + projectMaterialAcceptanceReviewUserFile.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            return null;

        String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));

        if (FileUtils.copyFile(
                ConfigConstant.FilePath + File.separator + projectMaterialAcceptanceReviewUserFile.getFilePath(),
                ConfigConstant.FilePath + File.separator + projectMaterialAcceptanceReviewUserFile.getFilePath())) {
            ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFileTemp = new ProjectMaterialAcceptanceReviewUserFile();
            projectMaterialAcceptanceReviewUserFileTemp.setProjectMaterialAcceptanceReviewUserId(
                    newProjectMaterialAcceptanceReviewUserFileId);
            projectMaterialAcceptanceReviewUserFileTemp.setFilePath(newFileName);
            return projectMaterialAcceptanceReviewUserFileRepository.add(projectMaterialAcceptanceReviewUserFileTemp);
        }

        return null;

    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return projectMaterialAcceptanceReviewUserFileRepository.delete(projectMaterialAcceptanceReviewUserFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        return projectMaterialAcceptanceReviewUserFileRepository.update(projectMaterialAcceptanceReviewUserFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialAcceptanceReviewUserFileRepository.deleteById(id);
    }

    /**
     * 根据projectMaterialAcceptanceReviewUserId删除记录
     *
     * @param projectMaterialAcceptanceReviewUserId
     */
    @Override
    public int deleteByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        return projectMaterialAcceptanceReviewUserFileRepository.deleteByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialAcceptanceReviewUserFileRepository.getCount();
    }

    /**
     * 根据projectMaterialAcceptanceReviewUserId得到数量
     *
     * @param projectMaterialAcceptanceReviewUserId
     */
    @Override
    public int getCountByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        return projectMaterialAcceptanceReviewUserFileRepository.getCountByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId);
    }

    /**
     * 根据id得到ProjectMaterialAcceptanceReviewUserFile
     *
     * @param id
     */
    @Override
    public ProjectMaterialAcceptanceReviewUserFile getById(String id) {
        return projectMaterialAcceptanceReviewUserFileRepository.getById(id);
    }

    /**
     * 根据projectMaterialAcceptanceReviewUserId得到ProjectMaterialAcceptanceReviewUserFile
     *
     * @param projectMaterialAcceptanceReviewUserId
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewUserFile> getByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId) {
        return projectMaterialAcceptanceReviewUserFileRepository.getByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserFile> getPage(int pageNo,
                                                                 int pageSize) {
        return projectMaterialAcceptanceReviewUserFileRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceReviewUserId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserFile> getPageByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                        int pageNo,
                                                                                                        int pageSize) {
        return projectMaterialAcceptanceReviewUserFileRepository.getPageByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserFileView> getPageView(int pageNo,
                                                                         int pageSize) {
        Page<ProjectMaterialAcceptanceReviewUserFile> projectMaterialAcceptanceReviewUserFilePage = getPage(pageNo,
                                                                                                            pageSize);
        return convertProjectMaterialAcceptanceReviewUserFilePage2PageView(projectMaterialAcceptanceReviewUserFilePage,
                                                                           pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectMaterialAcceptanceReviewUserId
     * @param pageNo                                页号，从1开始
     * @param pageSize                              每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserFileView> getPageViewByProjectMaterialAcceptanceReviewUserId(String projectMaterialAcceptanceReviewUserId,
                                                                                                                int pageNo,
                                                                                                                int pageSize) {
        Page<ProjectMaterialAcceptanceReviewUserFile> projectMaterialAcceptanceReviewUserFilePage = getPageByProjectMaterialAcceptanceReviewUserId(
                projectMaterialAcceptanceReviewUserId, pageNo, pageSize);
        return convertProjectMaterialAcceptanceReviewUserFilePage2PageView(projectMaterialAcceptanceReviewUserFilePage,
                                                                           pageNo, pageSize);
    }

    @Override
    public void downloadFileById(String projectMaterialAcceptanceReviewUserFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = getById(projectMaterialAcceptanceReviewUserFileId);
        if (projectMaterialAcceptanceReviewUserFile == null) throw new RuntimeException("文件不存在");
        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + projectMaterialAcceptanceReviewUserFile.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            throw new BusinessException("参数错误");
        try {
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
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectMaterialAcceptanceReviewUserFileView getProjectMaterialAcceptanceReviewUserFileViewByProjectMaterialAcceptanceReviewUserFileId(String id) {
        ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = getById(id);
        if (projectMaterialAcceptanceReviewUserFile == null) return null;
        ProjectMaterialAcceptanceReviewUserFileView projectMaterialAcceptanceReviewUserFileView = new ProjectMaterialAcceptanceReviewUserFileView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialAcceptanceReviewUserFilePage 页面对象
     */
    private Page<ProjectMaterialAcceptanceReviewUserFileView> convertProjectMaterialAcceptanceReviewUserFilePage2PageView(Page<ProjectMaterialAcceptanceReviewUserFile> projectMaterialAcceptanceReviewUserFilePage,
                                                                                                                          int pageNo,
                                                                                                                          int pageSize) {
        if (projectMaterialAcceptanceReviewUserFilePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUserFileView> list = new ArrayList<>();
        for (ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile : projectMaterialAcceptanceReviewUserFilePage.getResult()) {
            ProjectMaterialAcceptanceReviewUserFileView projectMaterialAcceptanceReviewUserFileView = getProjectMaterialAcceptanceReviewUserFileViewByProjectMaterialAcceptanceReviewUserFileId(
                    projectMaterialAcceptanceReviewUserFile.getId());
            if (projectMaterialAcceptanceReviewUserFileView != null)
                list.add(projectMaterialAcceptanceReviewUserFileView);
        }
        return new Page<>(startIndex, projectMaterialAcceptanceReviewUserFilePage.getTotalCount(), pageSize, list);
    }

}