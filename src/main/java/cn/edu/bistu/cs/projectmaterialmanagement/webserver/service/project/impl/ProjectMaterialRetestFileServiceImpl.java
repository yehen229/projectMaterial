package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatchFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRetestBatchFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ProjectMaterialRetestFileServiceImpl implements IProjectMaterialRetestFileService {
    private final IProjectMaterialRetestBatchFileRepository projectMaterialRetestFileRepository;

    public ProjectMaterialRetestFileServiceImpl(IProjectMaterialRetestBatchFileRepository projectMaterialRetestFileRepository) {
        this.projectMaterialRetestFileRepository = projectMaterialRetestFileRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectMaterialRetestBatchFile projectMaterialRetestFile) {
        return projectMaterialRetestFileRepository.add(projectMaterialRetestFile);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectMaterialRetestBatchFile projectMaterialRetestFile) {
        return projectMaterialRetestFileRepository.delete(projectMaterialRetestFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectMaterialRetestBatchFile projectMaterialRetestFile) {
        return projectMaterialRetestFileRepository.update(projectMaterialRetestFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectMaterialRetestFileRepository.deleteById(id);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectMaterialRetestFileRepository.getCount();
    }

    /**
     * 根据id得到ProjectMaterialRetestFile
     *
     * @param id
     */
    @Override
    public ProjectMaterialRetestBatchFile getById(String id) {
        return projectMaterialRetestFileRepository.getById(id);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialRetestBatchFile> getPage(int pageNo,
                                                        int pageSize) {
        return projectMaterialRetestFileRepository.getPage(pageNo, pageSize);
    }

    @Override
    public void downloadFileById(String projectMaterialRetestFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        ProjectMaterialRetestBatchFile projectMaterialRetestFile = getById(
                projectMaterialRetestFileId);
        if (projectMaterialRetestFile == null) throw new RuntimeException("文件不存在");


        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + projectMaterialRetestFile.getFilePath();
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

    @Override
    public List<ProjectMaterialRetestBatchFile> getByRetestBatchId(String retestBatchId) {
        return projectMaterialRetestFileRepository.getByRetestBatchId(retestBatchId);
    }

}