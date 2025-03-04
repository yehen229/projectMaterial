package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFileView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IUseMaterialNewBrandFileService {
    String add(UseMaterialNewBrandFile useMaterialNewBrandFile);

    String copyFile(String useMaterialNewBrandFileId,
                    String newuseMaterialNewBrandFileId);


    int delete(UseMaterialNewBrandFile useMaterialNewBrandFile);

    int update(UseMaterialNewBrandFile useMaterialNewBrandFile);

    int deleteById(String id);

    int deleteByUseMaterialNewBrandId(String useMaterialNewBrandId);

    int getCount();

    int getCountByUseMaterialNewBrandId(String useMaterialNewBrandId);

    UseMaterialNewBrandFile getById(String id);

    UseMaterialNewBrandFileView getViewById(String id);

    List<UseMaterialNewBrandFile> getByUseMaterialNewBrandId(String useMaterialNewBrandId);

    List<UseMaterialNewBrandFileView> getViewListByUseMaterialNewBrandId(String useMaterialNewBrandId);


    Page<UseMaterialNewBrandFile> getPage(int pageNo,
                                          int pageSize);

    Page<UseMaterialNewBrandFile> getPageByUseMaterialNewBrandId(String useMaterialNewBrandId,
                                                                 int pageNo,
                                                                 int pageSize);

    Page<UseMaterialNewBrandFileView> getPageView(int pageNo,
                                                  int pageSize);

    Page<UseMaterialNewBrandFileView> getPageViewByUseMaterialNewBrandId(String useMaterialNewBrandId,
                                                                         int pageNo,
                                                                         int pageSize);

    void downloadFileById(String useMaterialNewBrandFileId,
                          HttpServletRequest request,
                          HttpServletResponse response);

}