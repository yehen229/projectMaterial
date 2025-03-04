package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialView;

import java.util.List;

public interface IUseMaterialBusinessService {

    UseMaterialNewBrandView getViewByUseMaterialNewBrandId(String useMaterialBrandId);

    UseMaterialView getViewByUseMaterialId(String useMaterialId);

    List<UseMaterialView> getViewListByUseMaterialBrandSelectId(String useMaterialBrandSelectId);

    UseMaterialBrandSelectView getViewByUseMaterialBrandSelectId(String useMaterialBrandSelectId);

    Page<UseMaterialView> getPageView(int pageNo,
                                      int pageSize);

    Page<UseMaterialView> getPageViewByProjectMaterialId(String projectMaterialId,
                                                         int pageNo,
                                                         int pageSize);

    Page<UseMaterialView> getPageViewByUserId(String userId,
                                              int pageNo,
                                              int pageSize);

    Page<UseMaterialView> getPageViewByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                     int pageNo,
                                                                     int pageSize);

    Page<UseMaterialView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                    int pageNo,
                                                                    int pageSize);

    Page<UseMaterialView> getReviewedAndApprovedPageViewByProjectId(String projectId,
                                                                    Integer pageNo,
                                                                    Integer pageSize);

}
