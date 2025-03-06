package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialSelectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialBrandSelectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IBuyMaterialSelectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialBrandSelectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyMaterialSelectServiceImpl implements IBuyMaterialSelectService {

    private final IBuyMaterialSelectRepository buyMaterialSelectRepository;

    public BuyMaterialSelectServiceImpl(IBuyMaterialSelectRepository buyMaterialSelectRepository) {
        this.buyMaterialSelectRepository = buyMaterialSelectRepository;
    }

    @Override
    public String add(BuyMaterialSelect buyMaterialSelect) {
        return buyMaterialSelectRepository.add(buyMaterialSelect);
    }



}