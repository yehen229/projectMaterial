package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import javax.servlet.http.HttpServletResponse;

public interface IQrcodeService {
    //    生成文件件
    void createQRCode2Stream(String content, HttpServletResponse response);

//
}
