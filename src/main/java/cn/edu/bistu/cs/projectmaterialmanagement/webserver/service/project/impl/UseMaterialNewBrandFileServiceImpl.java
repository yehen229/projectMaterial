package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFileView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialNewBrandFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialNewBrandFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialNewBrandService;
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
public class UseMaterialNewBrandFileServiceImpl implements IUseMaterialNewBrandFileService {
    public final IUseMaterialNewBrandService useMaterialNewBrandService;
    private final IUseMaterialNewBrandFileRepository useMaterialNewBrandFileRepository;

    public UseMaterialNewBrandFileServiceImpl(IUseMaterialNewBrandFileRepository useMaterialNewBrandFileRepository,
                                              IUseMaterialNewBrandService useMaterialNewBrandService) {
        this.useMaterialNewBrandFileRepository = useMaterialNewBrandFileRepository;
        this.useMaterialNewBrandService = useMaterialNewBrandService;
    }

    /**
     * 增加
     */
    @Override
    public String add(UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return useMaterialNewBrandFileRepository.add(useMaterialNewBrandFile);
    }

    @Override
    public String copyFile(String useMaterialNewBrandFileId,
                           String newUseMaterialNewBrandFileId) {

        UseMaterialNewBrandFile useMaterialNewBrandFile = getById(useMaterialNewBrandFileId);
        if (useMaterialNewBrandFile == null) {
            return null;
        }
        String fileName = ConfigConstant.FilePath + File.separator + useMaterialNewBrandFile.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            return null;

        String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));

        if (FileUtils.copyFile(ConfigConstant.FilePath + File.separator + useMaterialNewBrandFile.getFilePath(),
                               ConfigConstant.FilePath + File.separator + useMaterialNewBrandFile.getFilePath())) {
            UseMaterialNewBrandFile projectReviewUserFileTemp = new UseMaterialNewBrandFile();
            projectReviewUserFileTemp.setUseMaterialNewBrandId(newUseMaterialNewBrandFileId);
            projectReviewUserFileTemp.setFilePath(newFileName);
            return add(projectReviewUserFileTemp);
        }

        return null;

    }

    /**
     * 删除
     */
    @Override
    public int delete(UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return useMaterialNewBrandFileRepository.delete(useMaterialNewBrandFile);
    }

    /**
     * 更新
     */
    @Override
    public int update(UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return useMaterialNewBrandFileRepository.update(useMaterialNewBrandFile);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return useMaterialNewBrandFileRepository.deleteById(id);
    }

    /**
     * 根据useMaterialNewBrandId删除记录
     *
     * @param useMaterialNewBrandId
     */
    @Override
    public int deleteByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        return useMaterialNewBrandFileRepository.deleteByUseMaterialNewBrandId(useMaterialNewBrandId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return useMaterialNewBrandFileRepository.getCount();
    }

    /**
     * 根据useMaterialNewBrandId得到数量
     *
     * @param useMaterialNewBrandId
     */
    @Override
    public int getCountByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        return useMaterialNewBrandFileRepository.getCountByUseMaterialNewBrandId(useMaterialNewBrandId);
    }

    /**
     * 根据id得到UseMaterialNewBrandFile
     *
     * @param id
     */
    @Override
    public UseMaterialNewBrandFile getById(String id) {
        return useMaterialNewBrandFileRepository.getById(id);
    }

    @Override
    public UseMaterialNewBrandFileView getViewById(String id) {
        UseMaterialNewBrandFile useMaterialNewBrandFile = getById(id);
        if (useMaterialNewBrandFile == null) return null;
        UseMaterialNewBrandFileView useMaterialNewBrandFileView = new UseMaterialNewBrandFileView();
        useMaterialNewBrandFileView.setUseMaterialNewBrandFile(useMaterialNewBrandFile);
        useMaterialNewBrandFileView.setUseMaterialNewBrand(
                useMaterialNewBrandService.getById(useMaterialNewBrandFile.getUseMaterialNewBrandId()));
        return useMaterialNewBrandFileView;
    }

    /**
     * 根据useMaterialNewBrandId得到UseMaterialNewBrandFile
     *
     * @param useMaterialNewBrandId
     */
    @Override
    public List<UseMaterialNewBrandFile> getByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        return useMaterialNewBrandFileRepository.getByUseMaterialNewBrandId(useMaterialNewBrandId);
    }

    @Override
    public List<UseMaterialNewBrandFileView> getViewListByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        List<UseMaterialNewBrandFile> useMaterialNewBrandFileList = getByUseMaterialNewBrandId(useMaterialNewBrandId);
        if (useMaterialNewBrandFileList == null || useMaterialNewBrandFileList.isEmpty()) return null;
        List<UseMaterialNewBrandFileView> list = new ArrayList<>();
        for (UseMaterialNewBrandFile useMaterialNewBrandFile : useMaterialNewBrandFileList) {
            UseMaterialNewBrandFileView useMaterialNewBrandFileView = getViewById(
                    useMaterialNewBrandFile.getId());
            if (useMaterialNewBrandFileView != null) list.add(useMaterialNewBrandFileView);
        }
        return list;
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandFile> getPage(int pageNo,
                                                 int pageSize) {
        return useMaterialNewBrandFileRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialNewBrandId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandFile> getPageByUseMaterialNewBrandId(String useMaterialNewBrandId,
                                                                        int pageNo,
                                                                        int pageSize) {
        return useMaterialNewBrandFileRepository.getPageByUseMaterialNewBrandId(useMaterialNewBrandId, pageNo,
                                                                                pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandFileView> getPageView(int pageNo,
                                                         int pageSize) {
        Page<UseMaterialNewBrandFile> useMaterialNewBrandFilePage = getPage(pageNo, pageSize);
        return convertUseMaterialNewBrandFilePage2PageView(useMaterialNewBrandFilePage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param useMaterialNewBrandId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandFileView> getPageViewByUseMaterialNewBrandId(String useMaterialNewBrandId,
                                                                                int pageNo,
                                                                                int pageSize) {
        Page<UseMaterialNewBrandFile> useMaterialNewBrandFilePage = getPageByUseMaterialNewBrandId(
                useMaterialNewBrandId, pageNo, pageSize);
        return convertUseMaterialNewBrandFilePage2PageView(useMaterialNewBrandFilePage, pageNo, pageSize);
    }


    /**
     * 下载文件
     *
     * @param useMaterialNewBrandFileId 文件Id
     * @param request
     * @param response
     */
    @Override
    public void downloadFileById(String useMaterialNewBrandFileId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        UseMaterialNewBrandFile useMaterialNewBrandFile = getById(useMaterialNewBrandFileId);
        if (useMaterialNewBrandFile == null) throw new RuntimeException("文件不存在");


        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + useMaterialNewBrandFile.getFilePath();
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
     * @param useMaterialNewBrandFilePage 页面对象
     */
    private Page<UseMaterialNewBrandFileView> convertUseMaterialNewBrandFilePage2PageView(Page<UseMaterialNewBrandFile> useMaterialNewBrandFilePage,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        if (useMaterialNewBrandFilePage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrandFileView> list = new ArrayList<>();
        for (UseMaterialNewBrandFile useMaterialNewBrandFile : useMaterialNewBrandFilePage.getResult()) {
            UseMaterialNewBrandFileView useMaterialNewBrandFileView = getViewById(
                    useMaterialNewBrandFile.getId());
            if (useMaterialNewBrandFileView != null) list.add(useMaterialNewBrandFileView);
        }
        return new Page<>(startIndex, useMaterialNewBrandFilePage.getTotalCount(), pageSize, list);
    }

}