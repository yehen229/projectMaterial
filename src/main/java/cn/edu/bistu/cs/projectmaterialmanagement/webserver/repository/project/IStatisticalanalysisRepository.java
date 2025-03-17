package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;

import java.util.List;

public interface IStatisticalanalysisRepository {

    List<Unpassmaterialmessage> getUnpassmaterialmessage();

    List<Unpassonlymaterial> getUnpassOnlymaterial();

    Page<Unpassonlymaterial> getPage(int pageNo, int pageSize);
    Page<Unpassonlymaterial> getunpassbeforezongbaoPage(int pageNo, int pageSize);

    List<OnematerialUnpass> getunpassreviewbyprojectidmaterialid_companyid(String projectid, String companyid);
    List<OnematerialUnpassbeforeZongbao> getunpassreviewbeforezongbao(String projectid, String materialid);

    Page<Unpassonlymaterial> getUnpassReviewJianli(int pageNo, int pageSize);

    List<OnematerialUnpassjianli> getunpassreviewcontentjianli(String projectid, String materialid);

    Page<Unpassonlymaterial> getUnpassReviewJianliAndGongchengbu(int pageNo, int pageSize);

    List<OnematerialUnpassjianliandgongchengbu> getunpassreviewcontentjianliandgongchengbu(String projectid, String materialid);

//    获取设计部审核不通过或通过的次数
    List<Stastisprojectidandcount> getdesignunpassorpassList(int result);
    Integer getdesignunpassorpassCount(String projectid,int result);

//    获取总包审核审核不通过或通过的次数
    List<Stastisprojectidandcount> getzongbaounpassorpassList(int result);
    Integer getzongbaounpassorpassCount(String projectid, int result);

//    监理审核不通过或通过的次数
    List<Stastisprojectidandcount> getjianliunpassorpassList(int result);
    Integer getjianliunpassorpassCount(String projectid, int result);
//    监理与工程部
    List<Stastisprojectidandcount> getjianliandgongchengbuunpassorpassList(int result);
    Integer getjianliandgongchengbuunpassorpassCount(String projectid, int result);


}
