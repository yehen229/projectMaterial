package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.OnematerialUnpass;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.Unpassmaterialmessage;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.Unpassonlymaterial;

import java.util.List;

public interface IStatisticalanalysisRepository {

    List<Unpassmaterialmessage> getUnpassmaterialmessage();

    List<Unpassonlymaterial> getUnpassOnlymaterial();

    Page<Unpassonlymaterial> getPage(int pageNo, int pageSize);

    List<OnematerialUnpass> getunpassreviewbyprojectidmaterialid_companyid(String projectid, String companyid);
}
