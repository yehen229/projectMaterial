package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialSelect;

import java.util.List;

/**
 * BuyMaterial Service Interface
 */
public interface IBuyMaterialSelectRepository {

    String add(BuyMaterialSelect buyMaterialSelect);


}